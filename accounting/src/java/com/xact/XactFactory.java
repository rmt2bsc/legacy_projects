package com.xact;


import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.bean.CustomerActivity;
import com.bean.CreditorActivity;
import com.bean.Xact;
import com.bean.XactCategory;
import com.bean.XactType;
import com.bean.XactTypeItemActivity;
import com.bean.VwXactTypeItemActivity;
import com.bean.VwXactList;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.SystemException;

public class XactFactory extends DataSourceAdapter {
    public static XactManagerApi create(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException {
	XactManagerApi api = new XactManagerApiImpl(_dbo);
	return api;
    }

    public static XactManagerApi create(DatabaseConnectionBean _dbo, Request _request) {
	try {
	    XactManagerApi api = new XactManagerApiImpl(_dbo, _request);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}

    }

    public static XactManagerApi create(DatabaseConnectionBean _dbo, int _xactTypeId, double _amt) throws SystemException, DatabaseException {
	try {
	    XactManagerApi api = new XactManagerApiImpl(_dbo, _xactTypeId, _amt);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }


    public static Xact createXact() {
	try {
	    Xact obj = new Xact();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    public static Xact createXact(int _xactTypeId, double _amt) {
	Xact obj = XactFactory.createXact();
	obj.setXactAmount(_amt);
	obj.setXactTypeId(_xactTypeId);
	return obj;
    }

    public static VwXactList createXactView() {
	VwXactList obj = null;
	try {
	    obj = new VwXactList();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    public static XactType createXactType() {
	try {
	    XactType obj = new XactType();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    public static XactCategory createXactCategory() {
	try {
	    XactCategory obj = new XactCategory();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    public static CustomerActivity createCustomerActivity() {
	CustomerActivity obj = null;
	try {
	    obj = new CustomerActivity();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    public static CustomerActivity createCustomerActivity(int _customerId, int _xactId, double _amount) {
	CustomerActivity obj = null;
	obj = XactFactory.createCustomerActivity();
	obj.setCustomerId(_customerId);
	obj.setXactId(_xactId);
	obj.setAmount(_amount);
	return obj;
    }

    public static CreditorActivity createCreditorActivity() {
	CreditorActivity obj = null;
	try {
	    obj = new CreditorActivity();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    public static CreditorActivity createCreditorActivity(int _customerId, int _xactId, double _amount) {
	CreditorActivity obj = null;
	obj = XactFactory.createCreditorActivity();
	obj.setCreditorId(_customerId);
	obj.setXactId(_xactId);
	obj.setAmount(_amount);
	return obj;
    }

    public static XactTypeItemActivity createXactTypeItemActivity() {
	XactTypeItemActivity xtia = null;
	try {
	    xtia = new XactTypeItemActivity();
	    return xtia;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    public static VwXactTypeItemActivity createXactTypeItemActivityView() {
	VwXactTypeItemActivity obj = null;
	try {
	    obj = new VwXactTypeItemActivity();
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

}
