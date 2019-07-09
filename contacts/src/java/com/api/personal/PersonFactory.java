package com.api.personal;

import com.controller.Request;

import com.api.db.orm.DataSourceAdapter;

import com.bean.OrmBean;

import com.bean.db.DatabaseConnectionBean;
import com.bean.Person;
import com.bean.VwPersonAddress;

/**
 * A factory class that is used to create Person Api and Person objects.
 * 
 * @author RTerrell
 * 
 */
public class PersonFactory extends DataSourceAdapter {

    /**
     * Creates a new PersonApi object that is assoiciated with a
     * DatabaseConnectionBean object.
     * 
     * @param dbo
     *            {@link DatabaseConnectionBean}
     * @return PersonApi
     */
    public static PersonApi createPersonApi(DatabaseConnectionBean dbo) {
        try {
            PersonApi api = new PersonBeanImpl(dbo);
            api.setBaseView("PersonView");
            api.setBaseClass("com.bean.Person");
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a new PersonApi object that is assoiciated with a
     * DatabaseConnectionBean object and a HttpServletRequest object.
     * 
     * @param dbo
     *            {@link DatabaseConnectionBean}
     * @param request
     *            HttpServletRequest
     * @return PersonApi
     */
    public static PersonApi createPersonApi(DatabaseConnectionBean dbo, Request request) {
        try {
            PersonApi api = new PersonBeanImpl(dbo, request);
            api.setBaseView("PersonView");
            api.setBaseClass("com.bean.Person");
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Uses {@link PersonXmlImpl} to create a new PersonApi object which is
     * associated with a DatabaseConnectionBean object.
     * 
     * @param dbo
     *            {@link DatabaseConnectionBean}
     * @return PersonApi
     */
    public static PersonApi createPersonXmlApi(DatabaseConnectionBean dbo) {
        try {
            PersonApi api = new PersonXmlImpl(dbo);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Uses {@link PersonXmlImpl} to create a new PersonApi object which is
     * associated with a DatabaseConnectionBean object and a HttpServletRequest
     * object.
     * 
     * @param dbo
     *            {@link DatabaseConnectionBean}
     * @param request
     *            HttpServletRequest
     * @return PersonApi
     */
    public static PersonApi createPersonXmlApi(DatabaseConnectionBean dbo, Request request) {
        try {
            PersonApi api = new PersonXmlImpl(dbo, request);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a new Person object.
     * 
     * @return {@link Person}
     */
    public static Person createPerson() {
        try {
            return new Person();
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a new Person object by obtaining its property values from the
     * HttpServletRequest.
     * 
     * @param request
     * @return {@link Person}
     */
    public static Person createPerson(Request request) {
        try {
            Person obj = createPerson();
            packageBean(request, obj);
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a new Person object which its data is expected to be handled as
     * XML. By default, the person object created will not be configured to
     * serialized any query results to a file.
     * 
     * @return
     */
    public static Person createXmlPerson() {
        try {
            Person obj = createPerson();
            obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
            obj.setSerializeXml(false);
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a new VwPersonAddress object.
     * 
     * @return {@link VwPersonAddress}
     */
    public static VwPersonAddress createPersonAddress() {
        try {
            VwPersonAddress obj = new VwPersonAddress();
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a new VwPersonAddress object which its data is expected to be
     * handled as XML. By default, the VwPersonAddress object created will not
     * be configured to serialized any query results to a file.
     * 
     * @return
     */
    public static VwPersonAddress createXmlPersonAddress() {
        try {
            VwPersonAddress obj = createPersonAddress();
            obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
            obj.setSerializeXml(false);
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }
}
