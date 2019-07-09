package com.xml.schema.messages;



/**
 * A factory for creating message payloads.
 * 
 * @author appdev
 *
 */
public class WSMessageFactory {
   
    /**
     * 
     */
    public WSMessageFactory() {
	return;
    }
   
    /**
     * Create an instance of WSMessageBuilder using the implementation, RequestResponseMessageBuilderImpl 
     */
    public static final WSMessageBuilder getBuilderInstance() {
	return new RequestResponseMessageBuilderImpl();
    }
    
  
}
