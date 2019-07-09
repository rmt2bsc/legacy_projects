package com.api;

import com.api.db.orm.DataSourceAdapter;

import com.bean.OrmBean;

import com.bean.db.DatabaseConnectionBean;

import com.bean.VwCommonContact;

/**
 * A factory class that is used to create Common Contact related objects.
 * 
 * @author RTerrell
 * 
 */
public class CommonContactFactory extends DataSourceAdapter {

    /**
     * Creates a new Contact object that is assoiciated with 
     * a DatabaseConnectionBean object.
     * 
     * @param dbo {@link DatabaseConnectionBean}
     * @return BusinessApi
     */
    public static Contact createApi(DatabaseConnectionBean dbo) {
	try {
	    Contact api = new CommonContactImpl(dbo);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a new Common Contact instance.
     * 
     * @return {@link VwCommonContact}
     */
    public static VwCommonContact createCommonContactEntity() {
	try {
	    return new VwCommonContact();
	}
	catch (Exception e) {
	    return null;
	}
    }
    
    
    /**
     * Creates a new XML Common Contact instance.
     * 
     * @return {@link VwCommonContact}
     */
    public static VwCommonContact createXmlCommonContactEntity() {
        VwCommonContact obj = CommonContactFactory.createCommonContactEntity();
        obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
        obj.setSerializeXml(false);
        return obj;
    }

    
}
