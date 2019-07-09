package com.xact.generic;

import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.bean.OrmBean;
import com.bean.SalesOrderItems;
import com.bean.VwGenericXactList;
import com.bean.XactType;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.SystemException;
import com.xact.XactFactory;

public class GenericXactFactory extends DataSourceAdapter {

    public static GenericXactManagerApi create(DatabaseConnectionBean dbo) {
	try {
	    GenericXactManagerApi api = new GenericXactImpl(dbo);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    public static GenericXactManagerApi create(DatabaseConnectionBean dbo, Request request) {
	try {
	    GenericXactManagerApi api = new GenericXactImpl(dbo, request);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }
    
    public static GenericXactManagerApi createXmlApi(DatabaseConnectionBean dbo, Request request) {
	try {
	    GenericXactManagerApi api = new GenericXactXmlImpl(dbo, request);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    public static VwGenericXactList createGerericXact() {
	VwGenericXactList obj = null;
	try {
	    obj = new VwGenericXactList();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }
    
    /**
     * Creates a consolidated transaction type object.
     * 
     * @return {@link com.bean.XactType XactType}
     */
    public static XactType createXactType() {
	try {
	    XactType obj = XactFactory.createXactType();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }
    
    

    /**
     * Creates a consolidated transaction object that is capable of representing
     * itself as XML.
     * 
     * @return {@link com.bean.VwAllXactList VwAllXactList}
     */
    public static VwGenericXactList createXmlGerericXact() {
	VwGenericXactList obj = createGerericXact();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	return obj;
    }

    /**
     * Creates a consolidated transaction type object that is capable of
     * representing itself as XML.
     * 
     * @return {@link com.bean.XactType XactType}
     */
    public static XactType createXmlXactType() {
	try {
	    XactType obj = XactFactory.createXactType();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }
    
   
}
