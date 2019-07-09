package com.api.messaging.jms;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;

import com.api.messaging.MessageException;

/**
 * 
 * @author rterrell
 *
 */
public class MessageSelectorBuilder {
    public static final int DATATYPE_STRING = 1;

    public static final int DATATYPE_INT = 2;

    public static final int DATATYPE_BOOL = 3;

    public static final int DATATYPE_BYTE = 4;

    public static final int DATATYPE_DOUBLE = 5;

    public static final int DATATYPE_FLOAT = 6;

    public static final int DATATYPE_LONG = 7;

    public static final int DATATYPE_SHORT = 8;

    public static final int DATATYPE_OBJECT = 9;

    private static Logger logger = Logger.getLogger(MessageSelectorBuilder.class);

    private String propName;

    private int dataType;

    private Object propValue;

    protected MessageSelectorBuilder() {
        return;
    }

    public MessageSelectorBuilder(String key, Object value) {
        this(key, MessageSelectorBuilder.DATATYPE_STRING, value);
    }

    public MessageSelectorBuilder(String key, int dataType, Object value) {
        this.propName = key;
        this.dataType = dataType;
        this.propValue = value;
    }

    public String buildPredicate() {
        if (this.propName == null || this.propName.length() <= 0) {
            logger.log(Level.ERROR, "JMS Selector key name was not initialized for SelectorProperties instance");
            return null;
        }
        if (this.propValue == null) {
            logger.log(Level.ERROR, "JMS Selector value was not initialized for SelectorProperties instance");
            return null;
        }

        // Default predicate to String evaluation if data type is not specified
        if (this.dataType <= 0) {
            return this.buildStringPredicate();
        }

        String predicate;
        switch (this.dataType) {
        case MessageSelectorBuilder.DATATYPE_STRING:
            predicate = this.buildStringPredicate();
            break;

        case MessageSelectorBuilder.DATATYPE_BYTE:
        case MessageSelectorBuilder.DATATYPE_DOUBLE:
        case MessageSelectorBuilder.DATATYPE_FLOAT:
        case MessageSelectorBuilder.DATATYPE_INT:
        case MessageSelectorBuilder.DATATYPE_LONG:
        case MessageSelectorBuilder.DATATYPE_SHORT:
            predicate = this.buildNumericPredicate();
            break;

        case MessageSelectorBuilder.DATATYPE_BOOL:
            predicate = this.buildBooleanPredicate();
            break;

        default:
            predicate = this.buildStringPredicate();
        } // end switch
        return predicate;
    }

    private String buildStringPredicate() {
        StringBuffer criteria = new StringBuffer();
        criteria.append(this.propName);
        if (this.propValue instanceof List) {
            String list = this.createSelectListValues((List) this.propValue, true);
            criteria.append(" in (");
            criteria.append(list);
            criteria.append(") ");
        }
        else {
            criteria.append(" = ");
            criteria.append("\'");
            criteria.append(propValue.toString());
            criteria.append("\'");
        }
        return criteria.toString();
    }

    private String buildNumericPredicate() {
        StringBuffer criteria = new StringBuffer();
        criteria.append(this.propName);
        if (this.propValue instanceof List) {
            String list = this.createSelectListValues((List) this.propValue, false);
            criteria.append(" in (");
            criteria.append(list);
            criteria.append(") ");
        }
        else {
            criteria.append(" = ");
            criteria.append(propValue.toString());
        }
        return criteria.toString();
    }

    private String buildBooleanPredicate() {
        StringBuffer criteria = new StringBuffer();
        criteria.append(this.propName);
        criteria.append(" = ");
        criteria.append(propValue.toString());
        return criteria.toString();
    }

    private String createSelectListValues(List values, boolean quoted) {
        StringBuffer valueList = new StringBuffer();
        for (Object value : values) {
            if (valueList.length() > 0) {
                valueList.append(", ");
            }
            if (quoted) {
                valueList.append("\'");
                valueList.append(value.toString());
                valueList.append("\'");
            }
            else {
                valueList.append(value.toString());
            }
        }
        return valueList.toString();
    }

    /**
     * Creates a message property and assigns the property its value for a given 
     * ObjectMessage type.
     * 
     * @param message
     *           the JMS message object to create the property key/value pair.
     * @param propName
     *           a String that represents the name of the property.
     * @param propVal
     *           a generic value to assign to the property.  This value should be 
     *           of one of the the following java types:  String, Byte, Double, 
     *           Float, Integer, Long, Short, or Boolean.  When the data type of 
     *           <i>propValue</i> cannot be resolved to either of the above types, 
     *           then it will default to Object.     
     * @throws MessageException
     */
    public void createMessageProperty(Message message, String propName, Object propVal) throws MessageException {
        if (message == null) {
            return;
        }

        if (propName == null || propName.length() <= 0) {
            logger.log(Level.ERROR, "Could not set JMS message property because property key name was not initialized for SelectorProperties instance");
            return;
        }
        if (propVal == null) {
            logger.log(Level.ERROR, "Could not set JMS message property because property value was not initialized for SelectorProperties instance");
            return;
        }

        this.verifyDataType(propVal);

        switch (this.dataType) {
        case MessageSelectorBuilder.DATATYPE_STRING:
            this.createStringProperty(message, propName, propVal);
            break;

        case MessageSelectorBuilder.DATATYPE_BYTE:
            this.createByteProperty(message, propName, propVal);
            break;

        case MessageSelectorBuilder.DATATYPE_DOUBLE:
            this.createDoubleProperty(message, propName, propVal);
            break;

        case MessageSelectorBuilder.DATATYPE_FLOAT:
            this.createFloatProperty(message, propName, propVal);
            break;
        case MessageSelectorBuilder.DATATYPE_INT:
            this.createIntProperty(message, propName, propVal);
            break;
        case MessageSelectorBuilder.DATATYPE_LONG:
            this.createLongProperty(message, propName, propVal);
            break;
        case MessageSelectorBuilder.DATATYPE_SHORT:
            this.createShortProperty(message, propName, propVal);
            break;

        case MessageSelectorBuilder.DATATYPE_BOOL:
            this.createBooleanProperty(message, propName, propVal);
            break;

        default:
            this.createStringProperty(message, propName, propVal);
        } // end switch
    }

    private void createStringProperty(Message message, String propName, Object propValue) throws MessageException {
        if (propValue != null) {
            try {
                message.setStringProperty(propName, (String) propValue);
            }
            catch (JMSException e) {
                throw new MessageException(e);
            }
        }
    }

    private void createByteProperty(Message message, String propName, Object propValue) throws MessageException {
        Byte obj = (propValue instanceof Byte ? (Byte) propValue : null);
        if (obj != null) {
            try {
                message.setByteProperty(propName, obj.byteValue());
            }
            catch (JMSException e) {
                throw new MessageException(e);
            }
        }
    }

    private void createDoubleProperty(Message message, String propName, Object propValue) throws MessageException {
        Double obj = (propValue instanceof Double ? (Double) propValue : null);
        if (obj != null) {
            try {
                message.setDoubleProperty(propName, obj.doubleValue());
            }
            catch (JMSException e) {
                throw new MessageException(e);
            }
        }
    }

    private void createFloatProperty(Message message, String propName, Object propValue) throws MessageException {
        Float obj = (propValue instanceof Float ? (Float) propValue : null);
        if (obj != null) {
            try {
                message.setFloatProperty(propName, obj.floatValue());
            }
            catch (JMSException e) {
                throw new MessageException(e);
            }
        }
    }

    private void createIntProperty(Message message, String propName, Object propValue) throws MessageException {
        Integer obj = (propValue instanceof Integer ? (Integer) propValue : null);
        if (obj != null) {
            try {
                message.setIntProperty(propName, obj.intValue());
            }
            catch (JMSException e) {
                throw new MessageException(e);
            }
        }
    }

    private void createLongProperty(Message message, String propName, Object propValue) throws MessageException {
        Long obj = (propValue instanceof Long ? (Long) propValue : null);
        if (obj != null) {
            try {
                message.setLongProperty(propName, obj.longValue());
            }
            catch (JMSException e) {
                throw new MessageException(e);
            }
        }
    }

    private void createShortProperty(Message message, String propName, Object propValue) throws MessageException {
        Short obj = (propValue instanceof Short ? (Short) propValue : null);
        if (obj != null) {
            try {
                message.setShortProperty(propName, obj.shortValue());
            }
            catch (JMSException e) {
                throw new MessageException(e);
            }
        }
    }

    private void createBooleanProperty(Message message, String propName, Object propValue) throws MessageException {
        Boolean obj = (propValue instanceof Boolean ? (Boolean) propValue : null);
        if (obj != null) {
            try {
                message.setBooleanProperty(propName, obj.booleanValue());
            }
            catch (JMSException e) {
                throw new MessageException(e);
            }
        }
    }

    private void verifyDataType(Object data) throws MessageException {
        if (data == null) {
            throw new MessageException("Message selector property value is an invalid");
        }

        if (data instanceof Boolean) {
            this.dataType = MessageSelectorBuilder.DATATYPE_BOOL;
            return;
        }
        if (data instanceof Byte) {
            this.dataType = MessageSelectorBuilder.DATATYPE_BYTE;
            return;
        }
        if (data instanceof Double) {
            this.dataType = MessageSelectorBuilder.DATATYPE_DOUBLE;
            return;
        }
        if (data instanceof Float) {
            this.dataType = MessageSelectorBuilder.DATATYPE_FLOAT;
            return;
        }
        if (data instanceof Integer) {
            this.dataType = MessageSelectorBuilder.DATATYPE_INT;
            return;
        }
        if (data instanceof Long) {
            this.dataType = MessageSelectorBuilder.DATATYPE_LONG;
            return;
        }
        if (data instanceof Short) {
            this.dataType = MessageSelectorBuilder.DATATYPE_SHORT;
            return;
        }
        if (data instanceof String) {
            this.dataType = MessageSelectorBuilder.DATATYPE_STRING;
            return;
        }

        // Otherwise, treat as a generic object
        this.dataType = MessageSelectorBuilder.DATATYPE_OBJECT;
        return;
    }

    /**
     * @return the propName
     */
    public String getPropName() {
        return propName;
    }

    /**
     * @param propName the propName to set
     */
    public void setPropName(String propName) {
        this.propName = propName;
    }

    /**
     * @return the dataType
     */
    public int getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the propValue
     */
    public Object getPropValue() {
        return propValue;
    }

    /**
     * @param propValue the propValue to set
     */
    public void setPropValue(Object propValue) {
        this.propValue = propValue;
    }

}
