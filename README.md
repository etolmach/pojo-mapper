# POJO-Mapper

POJO-Mapper is an easy-to-use annotation based tool for mapping the data between the objects.

**Usage**

1. Add the following maven dependency to your project
    ```xml
    <dependency>
        <groupId>com.etolmach</groupId>
        <artifactId>pojo-mapper</artifactId>
        <version>1.2</version>
    </dependency>
    ```

2. Define your source class
    ```java
    public class SourcePojo {
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
    public class DestinationPojo {
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
    MapperBuilder builder = new DefaultMapperBuilder();
    Mapper<SourcePojo, DestinationPojo> mapper = builder.build(SourcePojo.class, DestinationPojo.pojo);
    ```
6. Map source object to destination object
    * Map to existing object:
        ```java
        mapper.map(sourcePojo, targetPojo);
        ```
    * Map to a new instance:
        ```java
        TargetPojo targetPojo = mapper.map(sourcePojo);
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
    MapperBuilder builder = new DefaultMapperBuilder();
    Mapper<SourcePojo, DestinationPojo> sourcePojoMapper = builder.build(SourcePojo.class, DestinationPojo.pojo);  
    Mapper<FooInterface, DestinationPojo> fooInterfaceMapper = builder.build(FooInterface.class, DestinationPojo.pojo);
    // Map the objects
    DestinationPojo destinationPojo = new DestinationPojo();
    sourcePojoMapper.map(sourcePojo, destinationPojo);
    sourcePojoMapper.map(fooInterfaceInstance, destinationPojo);
    ```
* Build multiple mappers with single command
    ```java
    // Build the mappers
    MapperBuilder builder = new DefaultMapperBuilder();
    Map<Class<?>, Mapper<?, DestinationPojo>> mappers = builder.buildAll(DestinationPojo.pojo, SourcePojo.class, FooInterface.class);  
    ```
**Project info** 
* Last version : 1.3
* Code coverage : 97% 
