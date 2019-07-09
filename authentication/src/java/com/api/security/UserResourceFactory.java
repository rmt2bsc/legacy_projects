package com.api.security;

import com.api.db.orm.DataSourceAdapter;

import com.bean.OrmBean;
import com.bean.UserResource;


/**
 * A factory that creates new instances of UserResource.
 * 
 * @author roy.terrell
 * 
 */
public class UserResourceFactory extends DataSourceAdapter {
    /**
     * Create a new instance of a UserResource class.
     * 
     * @return {@link UserLogin}
     */
    public static UserResource createUserResource() {
	try {
	    return new UserResource();
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a new UserResource object which its data is expected to 
     * be handled as XML.   By default, the UserResource object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return {@link UserLogin}
     */
    public static UserResource createXmlUserResource() {
	try {
        UserResource obj = createUserResource();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

  
}
