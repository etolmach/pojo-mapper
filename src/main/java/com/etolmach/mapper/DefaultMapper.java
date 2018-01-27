package com.etolmach.mapper;

import com.etolmach.mapper.exceptions.CannotInjectValueException;
import com.etolmach.mapper.exceptions.CannotInstantiateDestinationObject;
import com.etolmach.mapper.exceptions.CannotRetrieveSourceValueException;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author etolmach
 */
public class DefaultMapper<S, D> implements Mapper<S, D> {

    // destMember -> srcMember
    private final Map<Member, Member> map;

    private final Class<S> srcClass;
    private final Class<D> destClass;

    DefaultMapper(Class<S> srcClass, Class<D> destClass, Map<Member, Member> map) {
        this.srcClass = srcClass;
        this.destClass = destClass;
        this.map = map;
    }

    public D map(S srcObject) throws CannotInjectValueException, CannotRetrieveSourceValueException, CannotInstantiateDestinationObject {
        try {
            D destObject = destClass.newInstance();
            return map(srcObject, destObject);
        } catch (ReflectiveOperationException e) {
            throw new CannotInstantiateDestinationObject(destClass, e);
        }
    }

    public D map(S srcObject, D destObject) throws CannotRetrieveSourceValueException, CannotInjectValueException {
        for (Map.Entry<Member, Member> entry : map.entrySet()) {
            // Retrieve the value from the source
            Object value = retrieveValue(srcObject, entry.getValue());
            // Inject the value to the destination
            injectValue(destObject, entry.getKey(), value);
        }
        return destObject;
    }

    private void injectValue(D destObject, Member destMember, Object value) throws CannotInjectValueException {
        try {
            if (destMember instanceof Field) {
                ((Field) destMember).set(destObject, value);
            } else if (destMember instanceof Method) {
                // Current version supports  single-parameter destination methods only
                ((Method) destMember).invoke(destObject, value);
            }
        } catch (ReflectiveOperationException e) {
            throw new CannotInjectValueException(srcClass, destMember, e);
        }
    }

    private Object retrieveValue(Object srcObject, Member srcMember) throws CannotRetrieveSourceValueException {
        Object value = null;
        try {
            if (srcMember instanceof Field) {
                value = ((Field) srcMember).get(srcObject);
            } else if (srcMember instanceof Method) {
                // Current version supports parameter-less source methods only
                value = ((Method) srcMember).invoke(srcObject);
            }
        } catch (ReflectiveOperationException e) {
            throw new CannotRetrieveSourceValueException(srcClass, srcMember, e);
        }
        return value;
    }
}
