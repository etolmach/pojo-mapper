# POJO-Mapper
[![Master Branch Build Status](https://travis-ci.com/etolmach/pojo-mapper.svg?branch=master)](https://travis-ci.com/etolmach/pojo-mapper/builds) 
[![codecov](https://codecov.io/gh/etolmach/pojo-mapper/branch/master/graph/badge.svg)](https://codecov.io/gh/etolmach/pojo-mapper)
[![Requirements Status](https://requires.io/github/etolmach/pojo-mapper/requirements.svg?branch=master)](https://requires.io/github/etolmach/pojo-mapper/requirements/?branch=master)

POJO-Mapper is an easy-to-use annotation based tool for mapping the data between the objects.

**Usage**

1. Add the following maven dependency to your project
    ```xml
    <dependency>
        <groupId>com.etolmach</groupId>
        <artifactId>pojo-mapper</artifactId>
        <version>1.1</version>
    </dependency>
    ```

2. Define your source class
    ```java
    public class Source {
        private String stringField;
        private char primitiveCharField;
        private int primitiveIntField;
        private Double doubleField;
        private BigDecimal bigDecimalField;
     
        // Setters & getters
    }

    ```

3. Define your destination class
    ```java
    @Data
    public class Destination {
         private String testStringField;
         private char testPrimitiveCharField;
         private int testPrimitiveIntField;
         private Double testDoubleField;
         private BigDecimal testBigDecimalField;
      
         // Setters & getters
    } 
    ```
 
4. Annotate your fields or setters. The framework supports field-method mappings:

    * field-to-field mapping
        ```java
        @FieldMapping(name = "stringField")
        private String testStringField;
        ```
    * method-to-field mapping
        ```java
        @MethodMapping(name = "getPrimitiveIntField")
        private int testPrimitiveIntField;
        ```
    * field-to-method mapping
         ```java
         @FieldMapping(name = "doubleField")
         public void setTestDoubleField(Double testDouble){  
              this.testDoubleField = testDouble;
         }
         ```
    * method-to-method mapping
         ```java
         @MethodMapping(name = "getBigDecimalField")
         public void setTestBigDecimalField(BigDecimal testBigDecimal){  
              this.testBigDecimal = testBigDecimal;
         }
         ```
 5. Build a mapper
    ```java
    MapperBuilder builder = new BaseMapperBuilder();
    Mapper<Source, Destination> mapper = builder.build(Source.class, Destination.class);
    ```
6. Map source object to destination object
    * Map to existing object:
        ```java
        mapper.map(source, destination);
        ```
    * Map to a new instance:
        ```java
        Destination destination = mapper.map(source);
        ```

**Advanced usage**

* Specify the source class or interface explicitly by using ```source``` attribute
    ```java
    @FieldMapping(name = "stringField", source=Pojo.class)
    private String testStringField;
    @MethodMapping(name = "getBigDecimalField", source=FooInterface.class)
    private BigDecimal testBigDecimalField;
    ```
    This will allow you to map different fields from different source objects:
    ```java
    // Build the mappers
    MapperBuilder builder = new BaseMapperBuilder();
    Mapper<Source, Destination> sourceMapper = builder.build(Source.class, Destination.class);  
    Mapper<FooInterface, Destination> fooInterfaceMapper = builder.build(FooInterface.class, Destination.class);
    // Map the objects
    Destination destination = new Destination();
    sourceMapper.map(sourcePojo, destination);
    fooInterfaceMapper.map(fooInterfaceInstance, destination);
    ```
* Build multiple mappers with single command
    ```java
    // Build the mappers
    MapperBuilder builder = new BaseMapperBuilder();
    Map<Class<?>, Mapper<?, Destination>> mappers = builder.buildAll(Destination.pojo, Source.class, FooInterface.class);  
    ```

* Specify a type converter class which extends org.apache.commons.jxpath.util.TypeConverter
     ```java
     @FieldMapping(name = "stringField", converter = BasicTypeConverter.class)
     private String testStringField;
     @MethodMapping(name = "getDoubleField", converter = YourCustomDoubleToBigDecimalConverter.class)
     private BigDecimal testBigDecimalField;
     ```
