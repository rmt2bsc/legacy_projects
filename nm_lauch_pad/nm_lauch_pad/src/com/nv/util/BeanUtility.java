package com.nv.util;

import java.beans.Beans;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.MethodDescriptor;
import java.beans.IntrospectionException;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

/**
 * This class provides the ability to create beans, dynamically access bean
 * properties, and invoke bean methods using introspection.
 * 
 * @author roy.terrell
 * 
 */
public class BeanUtility {
    private Object bean;

    private Class classInst;

    private BeanInfo info;

    private BeanInfo addInfo[];

    private PropertyDescriptor pd[];

    private MethodDescriptor md[];

    private Hashtable methods;

    private String currentProperty = null;

    private String msg;

    /**
     * Default constructor
     *
     */
    public BeanUtility() {
        return;
    }

    /**
     * Constructs a RMT2BeanUtility using a bean.
     * 
     * @param _bean
     *            The bean that is to be manipulated.
     * @throws SystemException
     */
    public BeanUtility(Object _bean) throws Exception {
        this();
        this.bean = _bean;
        this.init();
        this.setupBeanHashtables();
    }

    /**
     * Initializes the class instance, BeanInfo class, and PropertyDescriptor
     * arrary.
     * 
     * @throws Exception
     */
    private void init() throws Exception {
        try {
            this.classInst = bean.getClass();
            this.info = Introspector.getBeanInfo(this.classInst);
            this.addInfo = this.info.getAdditionalBeanInfo();
            this.pd = info.getPropertyDescriptors();
            this.md = info.getMethodDescriptors();
        } catch (IntrospectionException e) {
            String msg = (e.getMessage() == null ? "" : e.getMessage());
            throw new Exception("Introspection Exception occurred.  " + msg);
        }
    }

    /**
     * Poopulates the setMethods and getMethods Hashtables.
     * 
     * @return Total number of properties found and zero if no proerties exist.
     * @throws Exception
     */
    private int setupBeanHashtables() throws Exception {
        String propName = null;

        // There is no need to process any further if no properties exist
        if (this.pd == null) {
            return 0;
        }

        // Initialized methods Hashtable
        methods = new Hashtable();

        // Retrieve each property and its get/set methods
        for (int ndx = 0; ndx < pd.length; ndx++) {
            // Ensure that the property name conforms to the naming
            // convention spceifiec by Javabean Introspection
            propName = this.getBeanMethodName(pd[ndx].getName());

            // Get the getter and setter methods associated with the property
            Method getMethod = pd[ndx].getReadMethod();
            Method setMethod = pd[ndx].getWriteMethod();

            // Add methods to the Hastables with the property name being the key
            if (getMethod != null) {
                this.methods.put("get" + propName, getMethod);
            }
            if (setMethod != null) {
                this.methods.put("set" + propName, setMethod);
            }
        }
        return this.methods.size();
    }

    /**
     * Extracts the class name from the fully qualified package name of _bean.
     * 
     * @param _bean
     *            The object to retreive the root class name.
     * @return The name of the bean, null when _bean is invalid or an empty
     *         string ("") when package name of _bean could not be parsed.
     */
    public String getBeanClassName(Object _bean) {
        String temp = null;
        String beanName = null;
        List<String> list = null;

        if (_bean == null) {
            return null;
        }

        temp = _bean.getClass().getName();
        list = GeneralUtil.getTokens(temp, ".");

        if (list == null || list.size() <= 0) {
            return "";
        }
        beanName = (String) list.get(list.size() - 1);
        return beanName;
    }

    /**
     * Formats a string to conform to the method naming conventions of the
     * Javabean specification by converting the first character of the string to
     * upper case.
     * 
     * @param entityName
     *            Source to be converted.
     * @return The method name.
     */
    public String getBeanMethodName(String entityName) {

        StringBuffer propName = new StringBuffer(50);

        propName.append(entityName.substring(0, 1).toUpperCase());
        propName.append(entityName.substring(1));

        return propName.toString();
    }

    /**
     * Determines if methodName exists as a method for this object
     * 
     * @param methodName
     *            The name of the method to search
     * @return true for found and false for not found.
     */
    public boolean methodExist(String methodName) {
        // There is no need to process any further if no properties exist
        if (this.md == null) {
            return false;
        }

        // Retrieve the each property and its get/set methods
        for (int ndx = 0; ndx < md.length; ndx++) {
            // Get the getter and setter methods associated with the property
            Method method = md[ndx].getMethod();

            // Add methods to the Hastables with the property name being the key
            if (method != null) {
                // System.out.println("Method name: " + method.getName());
                if (method.getName().equals(methodName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Querys the property descriptor array (pd) for the property's class type.
     * 
     * @param _propName
     *            Property name.
     * @return The Class of _propertyName or null if property is not found.
     */
    public Class getPropertyType(String _propName) {
        for (int ndx = 0; ndx < pd.length; ndx++) {
            if (_propName.equalsIgnoreCase(pd[ndx].getName())) {
                return pd[ndx].getPropertyType();
            }
        }
        return null;
    }

    /**
     * Packages all the property names of "bean" into an ArrayList and returns
     * the ArrayList to the caller.
     * 
     * @return An ArrayList of property names.
     */
    public List<String> getPropertyNames() {

        List<String> names = new ArrayList<String>();
        for (int ndx = 0; ndx < pd.length; ndx++) {
            names.add(pd[ndx].getName());
        }
        return names;
    }

    /**
     * Obtains the value of the targeted property, "_property".
     * 
     * @param _property
     *            The property to retreive value.
     * @return Object (value of the property retrieved)
     * @throws Exception
     */
    public Object getPropertyValue(String _property) throws Exception {
        String nameOfMethod = null;
        Object value = null;
        Object parms[] = new Object[0];
        Method methodObj = null;

        if (_property == null || _property.length() <= 0) {
            this.msg = "The method argument value is null: " + _property;
            throw new Exception(msg);
        }

        this.currentProperty = _property;

        // Build the method name that is to be searched.
        nameOfMethod = "get" + this.getBeanMethodName(_property);
        // Attempt to locate the method from the hashtable
        methodObj = (Method) this.methods.get(nameOfMethod);

        // throw an exception if the method does not exist
        if (methodObj == null) {
            this.msg = "Method, "
                    + nameOfMethod
                    + ", could not be obtained since it was not mapped to any value in the methods Hashtable";
            throw new Exception(this.msg);
        }

        // Invoke the method
        try {
            value = this.triggerMethod(methodObj, parms);
        } catch (Exception e) {
            this.msg = "Access error occurred trying to discover and invoke method, "
                    + nameOfMethod + ", for property, " + _property;
            throw new Exception(this.msg);
        }

        return value == null ? "" : value;
    }

    /**
     * Sets the property named as, "_property", to the value represented by,
     * "_value".
     * 
     * @param _property
     *            The target property
     * @param _value
     *            The value to set.
     * @return The of the setXXX method invocation which is generally null.
     * @throws Exception
     */
    public Object setPropertyValue(String _property, Object _value)
            throws Exception {
        Object parms[] = new Object[1];
        Object objProper;
        String nameOfMethod = null;
        Method methodObj = null;
        String msg = null;

        if (_property == null || _property.length() <= 0) {
            throw new Exception("Property cannot be null");
        }

        this.currentProperty = _property;

        // Get the data type of the target property.
        Class propType = this.getPropertyType(_property);

        // Exit method and return null to the caller if property does not exist
        if (propType == null) {
            return null;
        }

        // Translate propType to its primitive wrapper class
        objProper = getPrimitiveType(propType, _value);

        // Value was returned as null because property value was actually null
        // or property is of a complex datatype. Assign object as-is.
        if (objProper == null) {
            objProper = _value;
            // System.out.println("RMT2BeanUtility, setPropertyValue(). Caution!
            // Property was null: " + _property);
        }

        // Build method name that is to be searched.
        nameOfMethod = "set" + this.getBeanMethodName(_property);

        // Locate the method from the method hashtable
        methodObj = (Method) this.methods.get(nameOfMethod);

        // Throw an exception if the method does not exist
        if (methodObj == null) {
            msg = "Method, "
                    + nameOfMethod
                    + ", could not be obtained since it was not mapped to any value in the methods Hashtable";
            throw new Exception(msg);
        }

        // Set the parameter value
        parms[0] = objProper;

        // invoke method
        try {
            return this.triggerMethod(methodObj, parms);
        } catch (Exception e) {
            throw new Exception(msg);
        }
    }

    /**
     * Initializes all primitive properties of the target java bean. Sets
     * numbers to zero, boolean to false, Character and String to "". This
     * method was implemented for the purpose of converting null values to empty
     * string values of String properties of a java bean initially containig
     * null values. This prevents the client JSP from displaying the String,
     * "null", for those string properties of a bean that contain null values.
     * 
     * @return The initialized bean.
     */
    public Object initializeBean() {
        Object parms[] = new Object[1];
        // Object objProper;
        String nameOfMethod = null;
        String property = null;
        Method methodObj = null;
        String msg = null;
        List<String> props = null;

        // Retrieve all property names associated with _bean
        props = this.getPropertyNames();

        for (int ndx = 0; ndx < props.size(); ndx++) {
            try {
                // Get and convert the property name to proper Bean
                // specification casing
                property = this.getBeanMethodName(props.get(ndx).toString());

                // Get the data type of the target property.
                Class propType = this.getPropertyType(property);
                if (propType == null) {
                    continue;
                }

                // We want to initialize on those properties that are primitive
                // types (numbers and Strings)
                if (propType.getName().equalsIgnoreCase("boolean"))
                    parms[0] = new Boolean("false");
                else if (propType.getName().equalsIgnoreCase("byte"))
                    parms[0] = new Byte("0");
                else if (propType.getName().equalsIgnoreCase("char"))
                    parms[0] = new Character(' ');
                else if (propType.getName().equalsIgnoreCase("double"))
                    parms[0] = new Double("0");
                else if (propType.getName().equalsIgnoreCase("float"))
                    parms[0] = new Float("0");
                else if (propType.getName().equalsIgnoreCase("int"))
                    parms[0] = new Integer("0");
                else if (propType.getName().equalsIgnoreCase("long"))
                    parms[0] = new Long("0");
                else if (propType.getName().equalsIgnoreCase("short"))
                    parms[0] = new Short("0");
                else if (propType.getName()
                        .equalsIgnoreCase("java.lang.String"))
                    parms[0] = "";

                // Build method name that is to be searched.
                nameOfMethod = "set" + this.getBeanMethodName(property);

                // Locate the method from the method hashtable
                methodObj = (Method) this.methods.get(nameOfMethod);

                // Throw an exception if the method does not exist
                if (methodObj == null) {
                    msg = "Method, "
                            + nameOfMethod
                            + ", could not be obtained since it was not mapped to any value in the methods Hashtable";
                    throw new Exception(msg);
                }

                // invoke method
                this.triggerMethod(methodObj, parms);
            } // end try
            catch (Exception e) {
                continue;
            }
        } // end for

        return this.bean;
    }

    /**
     * Assigns "_value" to is proper wrapper class.
     * 
     * @param obj
     *            The Class Object to determine data type.
     * @param _value
     *            The value to wrap
     * @return The value assigned to the proper primitive wrapper class. Returns
     *         null if Class object is not of the primitive types or if
     *         <i>_value</i> is null.
     * @throws Exception
     */
    private Object getPrimitiveType(Class obj, Object _value) throws Exception {
        String _className = obj.getName();
        if (_value == null) {
            return null;
        }
        String badValue = _value.toString();
        this.msg = "Property: " + this.currentProperty
                + "  Value is not of the correct number format:  " + badValue == null ? "<value not interpreted"
                : badValue;

        try {
            if (_className.equalsIgnoreCase("boolean"))
                return new Boolean(_value.toString());
            else if (_className.equalsIgnoreCase("byte"))
                return new Byte(_value.toString());
            else if (_className.equalsIgnoreCase("char"))
                return new Character(_value.toString().charAt(0));
            else if (_className.equalsIgnoreCase("double"))
                return new Double(_value.toString());
            else if (_className.equalsIgnoreCase("float"))
                return new Float(_value.toString());
            else if (_className.equalsIgnoreCase("int"))
                return new Integer(_value.toString());
            else if (_className.equalsIgnoreCase("long"))
                return new Long(_value.toString());
            else if (_className.equalsIgnoreCase("short"))
                return new Short(_value.toString());
            else if (_className.equalsIgnoreCase("java.lang.String"))
                return _value;
            else if (_className.equalsIgnoreCase("java.util.Date"))
                return GeneralUtil.stringToDate(_value.toString());
            else if (_className.equalsIgnoreCase("java.io.InputStream")) {
                return _value;
            }

            return null;
        } catch (NumberFormatException e) {
            // return the proper number wrapper class provided that
            // the NumberFormatException was thrown because the value
            // was either null or spaces.

            // Try to parse numeric value to its raw number representation
            // and include that raw number in its proper wrapper class.

            if (_value != null && _value.toString().trim().length() > 0) {
                if (_className.equalsIgnoreCase("double")) {
                    double dValue = GeneralUtil.stringToNumber(
                            _value.toString()).doubleValue();
                    return new Double(dValue);
                }
                else if (_className.equalsIgnoreCase("float")) {
                    float fValue = GeneralUtil
                            .stringToNumber(_value.toString()).floatValue();
                    return new Float(fValue);
                }
                else if (_className.equalsIgnoreCase("int")) {
                    int iValue = GeneralUtil.stringToNumber(_value.toString())
                            .intValue();
                    return new Integer(iValue);
                }
                else if (_className.equalsIgnoreCase("long")) {
                    long lValue = GeneralUtil.stringToNumber(_value.toString())
                            .longValue();
                    return new Long(lValue);
                }
                else if (_className.equalsIgnoreCase("short")) {
                    short sValue = GeneralUtil
                            .stringToNumber(_value.toString()).shortValue();
                    return new Short(sValue);
                }
            }

            // Default appropiate wrapper class to 0
            if (_value == null || _value.toString().trim().equals("")) {
                if (_className.equalsIgnoreCase("double"))
                    return new Double(0);
                else if (_className.equalsIgnoreCase("float"))
                    return new Float(0);
                else if (_className.equalsIgnoreCase("int"))
                    return new Integer(0);
                else if (_className.equalsIgnoreCase("long"))
                    return new Long(0);
                else if (_className.equalsIgnoreCase("short"))
                    return new Short("0");
            }

            // otherwise, continue to throw exception
            throw new Exception(this.msg);
        }
    }

    /**
     * Checks if <i>obj</i> is of a primitive data type.
     * 
     * @param obj
     *            The object to check data type.
     * @return true for primitive and false for complex object types.
     * @throws Exception
     */
    public boolean isPrimitiveType(Class obj) throws Exception {
        String clazzName = obj.getName();
        if (clazzName.equalsIgnoreCase("boolean"))
            return true;
        else if (clazzName.equalsIgnoreCase("byte"))
            return true;
        else if (clazzName.equalsIgnoreCase("char"))
            return true;
        else if (clazzName.equalsIgnoreCase("double"))
            return true;
        else if (clazzName.equalsIgnoreCase("float"))
            return true;
        else if (clazzName.equalsIgnoreCase("int"))
            return true;
        else if (clazzName.equalsIgnoreCase("long"))
            return true;
        else if (clazzName.equalsIgnoreCase("short"))
            return true;
        else if (clazzName.equalsIgnoreCase("java.lang.String"))
            return true;
        else if (clazzName.equalsIgnoreCase("java.util.Date"))
            return true;
        else if (clazzName.equalsIgnoreCase("java.lang.Double"))
            return true;
        else if (clazzName.equalsIgnoreCase("java.lang.Integer"))
            return true;
        else if (clazzName.equalsIgnoreCase("java.lang.Float"))
            return true;
        else if (clazzName.equalsIgnoreCase("java.lang.Character"))
            return true;
        else if (clazzName.equalsIgnoreCase("java.lang.Boolean"))
            return true;
        else if (clazzName.equalsIgnoreCase("java.lang.Byte"))
            return true;

        return false;
    }

    /**
     * Attempts to invoke the method, obj, dynamically.
     * 
     * @param obj
     *            Method to dym=namically invoke
     * @param _parms
     *            The method's parmaeters
     * @return The results of the method invocation.
     * @throws Exception
     */
    public Object triggerMethod(Method obj, Object _parms[]) throws Exception {
        try {
            return obj.invoke(this.bean, _parms);
        } catch (IllegalAccessException e) {
            msg = "The underlying method is inaccessible: " + obj.getName();
            throw new Exception(msg, e);
        } catch (IllegalArgumentException e) {
            msg = "The number of actual and formal parameters differ, or an unwrapping conversion failed for method, "
                    + obj.getName();
            throw new Exception(msg, e);
        } catch (InvocationTargetException e) {
            msg = "The underlying method throws an exception: " + obj.getName();
            throw new Exception(msg, e);
        } catch (NullPointerException e) {
            msg = "The specified object representing the method that is to be dynamically invoked is null.";
            throw new Exception(msg, e);
        } catch (ExceptionInInitializerError e) {
            msg = "The initialization provoked by this method failed: "
                    + obj.getName();
            throw new Exception(msg, e);
        }
    }

    /**
     * Creates an instance of the underlying bean, this.bean.
     * 
     * @return Class instance.
     * @throws Exception
     */
    public Object createInstance() throws Exception {
        String _className = this.bean.getClass().getName();
        return this.createClassInstance(_className);
    }

    /**
     * Creates an instance of a class name.
     * 
     * @param _className
     *            The name of the class to create instance.
     * @return Class instance.
     * @throws Exception
     */
    private Object createClassInstance(String _className) throws Exception {
        String msg = null;
        try {
            return Beans.instantiate(null, _className);
        } catch (ClassNotFoundException e) {
            msg = "The the class, " + _className
                    + ", of a serialized object could not be found";
            throw new Exception(msg);
        } catch (IOException e) {
            msg = "an I/O error occured while attempting to create an instance of, "
                    + _className;
            throw new Exception(msg);
        }
    }

    /**
     * Creates an instance of _className using the class loader of an instance
     * of RMT2BeanUtility class and returns that instance to the caller.
     * 
     * @param _className
     *            The name of the class to create instance.
     * @return Class instance.
     * @throws Exception
     */
    public final Object createBean(String _className) throws Exception {
        try {
            Object beanObj = null;
            beanObj = Beans.instantiate(this.getClass().getClassLoader(),
                    _className);
            return beanObj;
        } catch (IOException e) {
            this.msg = "An I/O error occurred while attempting to create a bean from RMT2Utility.    Class in violation: "
                    + _className;
            throw new Exception(this.msg);
        } catch (ClassNotFoundException e) {
            this.msg = "The class of a serialized object could not be found while attempting to create a bean from RMT2Utility.  Class in violation: "
                    + _className;
            throw new Exception(this.msg);
        }
    }

    /**
     * Extracts the root class name from the fully qualified package name of
     * _bean.
     * 
     * @param obj
     *            The object to retreive the root class name.
     * @return The name of the bean, null when _bean is invalid or an empty
     *         string ("") when package name of _bean could not be parsed.
     */
    public String getRootClassName(Object obj) {
        return this.getBeanClassName(obj);
    }

    /**
     * Formats a string to conform to the method naming conventions of the
     * Javabean specification by converting the first character of the string to
     * upper case.
     * 
     * @param entityName
     *            Source to be converted.
     * @return The method name.
     */
    public String formatToMethodName(String entityName) {
        return this.getBeanMethodName(entityName);
    }

    /**
     * Returns the underlying bean instance.
     * 
     * @return Object
     */
    public Object getBeanInstance() {
        return bean;
    }

} // end class
