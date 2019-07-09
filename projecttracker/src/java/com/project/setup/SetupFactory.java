package com.project.setup;

import com.api.db.DatabaseException;

import com.api.db.orm.DataSourceAdapter;

import com.bean.OrmBean;
import com.bean.ProjClient;
import com.bean.ProjProject;
import com.bean.ProjProjectTask;
import com.bean.ProjTask;
import com.bean.VwProjectClient;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.SystemException;

/**
 * A factory class for creating instances that are related to the setup and 
 * maintenance of project entities.
 * 
 * @author RTerrell
 *
 */
public class SetupFactory extends DataSourceAdapter {

    /**
     * Instantiates an project setup Api using RMT2DBConectionBean
     *  
     * @param _dbo Database connection bean
     * @return {@link SetupApi}
     */
    public static SetupApi createApi(DatabaseConnectionBean _dbo) {
	try {
	    SetupApi api = new SetupImpl(_dbo);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Instantiates an project setup Api using RMT2DBConectionBean and HttpServletRequest
     * 
     * @param _dbo
     * @param _request
     * @return {@link SetupApi}
     */
    public static SetupApi createApi(DatabaseConnectionBean _dbo, Request _request) {
	try {
	    SetupApi api = new SetupImpl(_dbo, _request);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }
    
    /**
     * Instantiates an project setup XML Api using RMT2DBConectionBean
     *  
     * @param _dbo Database connection bean
     * @return {@link SetupApi}
     */
    public static SetupApi createXmlApi(DatabaseConnectionBean _dbo) {
	try {
	    SetupApi api = new SetupXmlImp(_dbo);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Instantiates an project setup XML Api using RMT2DBConectionBean and HttpServletRequest
     * 
     * @param _dbo
     * @param _request
     * @return {@link SetupApi}
     */
    public static SetupApi createXmlApi(DatabaseConnectionBean _dbo, Request _request) {
	try {
	    SetupApi api = new SetupXmlImp(_dbo, _request);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new Proj Client object.
     * 
     * @return {@link com.bean.ProjClient ProjClient}
     */
    public static ProjClient createClient() {
	try {
	    return new ProjClient();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new XML ProjClient object.
     * 
     * @return {@link com.bean.ProjClient ProjClient}
     */
    public static ProjClient createXmlClient() {
	ProjClient obj = SetupFactory.createClient();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

    /**
     * Creaes a new ProjProject object
     * 
     * @return {@link com.bean.ProjProject ProjProject}
     */
    public static ProjProject createProject() {
	try {
	    return new ProjProject();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creaes a new XML Project object
     * 
     * @return {@link com.bean.ProjProject ProjProject}
     */
    public static ProjProject createXmlProject() {
	ProjProject obj = SetupFactory.createProject();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

    /**
     * Creates a new Task object.
     * 
     * @return {@link com.bean.ProjTask ProjTask}
     */
    public static ProjTask createTask() {
	try {
	    return new ProjTask();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new XML Task object.
     * 
     * @return {@link com.bean.ProjTask ProjTask}
     */
    public static ProjTask createXmlTask() {
	ProjTask obj = SetupFactory.createTask();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

    /**
     * Creates a new Project Task object.
     * 
     * @return {@link com.bean.ProjProjectTask ProjProjectTask}
     */
    public static ProjProjectTask createProjectTask() {
	try {
	    return new ProjProjectTask();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new XML Project Task object.
     * 
     * @return {@link com.bean.ProjProjectTask ProjProjectTask}
     */
    public static ProjProjectTask createXmlProjectTask() {
	ProjProjectTask obj = SetupFactory.createProjectTask();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }
    
    
    /**
     * Creates a new Project Client View object.
     * 
     * @return {@link com.bean.VwProjectClient VwProjectClient}
     */
    public static VwProjectClient createVwProjectClient() {
	try {
	    return new VwProjectClient();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new XML Project Client View object.
     * 
     * @return {@link com.bean.VwProjectClient VwProjectClient}
     */
    public static VwProjectClient createXmlVwProjectClient() {
	VwProjectClient obj = SetupFactory.createVwProjectClient();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }
    
    
}
