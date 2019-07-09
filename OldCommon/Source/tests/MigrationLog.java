package com.hmamg.digiquote.migration;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;

import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;



public class MigrationLog {
	
	private String logDir;
	private Hashtable contactLog;
	private Hashtable appointmentLog;
	
	private Logger log = Logger.getLogger(MigrationLog.class.getName());
	
	
	public MigrationLog(String _logDir){
		this.logDir = _logDir;
		
		this.loadLogData();
	}
	
	
	private void loadLogData() {
		Document doc = null;
		String docName = null;

		// Initialize migration collections
		this.contactLog = new Hashtable();		
		this.appointmentLog = new Hashtable();
		
		// Load Document
		try {
			docName = "log_contact.xml";
			doc = this.getXMLDocument(docName);
			this.loadContactLog(doc.getRootElement());
			
			docName = "log_appointment.xml";
			doc = this.getXMLDocument(docName);
			this.loadAppointmentLog(doc.getRootElement());
		}
	    catch (JDOMException e) {
		    // indicates a well-formedness error
	    	System.out.println(docName + " is not well-formed.");
	    	System.out.println(e.getMessage());
	    }  
	    catch (IOException e) { 
	    	System.out.println("Could not access " + docName + "  because this  may be the first time Migration Utility is executed ");
	    }  		
	}
	
	
	private Document getXMLDocument(String _docName) throws JDOMException, IOException {
		Document doc = null;
		SAXBuilder docBuilder = new SAXBuilder();
		doc = docBuilder.build(this.logDir + _docName);
		return doc;
	}
	
	
	private void loadContactLog(Element _root) {
		String oldKey = null;
		String newKey = null;
		Element contact = null;
		List list = null;

	    
	    // Build Contact migration collection
	    list = _root.getChildren("contact");
	    for (int ndx = 0; ndx < list.size(); ndx++) {
	    	contact = (Element) list.get(ndx);
	    	oldKey = contact.getChildText("oldkey");
	    	newKey = contact.getChildText("newkey");
	    	this.contactLog.put(oldKey, newKey);
	    }
	}
	
	private void loadAppointmentLog(Element _root) {
		String oldKey = null;
		String newKey = null;
		Element appt = null;
		List list = null;

	    // Build Contact migration collection
	    list = _root.getChildren("appointment");
	    for (int ndx = 0; ndx < list.size(); ndx++) {
	    	appt = (Element) list.get(ndx);
	    	oldKey = appt.getChildText("oldkey");
	    	newKey = appt.getChildText("newkey");
	    	this.appointmentLog.put(oldKey, newKey);
	    }
	}

	public void logMigratedContact(String _oldKey, String _newKey) {
		Document doc = null;
		String docName = null;
		Element element = null;
		Element oldElement = null;
		Element newElement = null;
		File file = null;
		
		docName = "log_contact.xml";
		try {
			doc = this.getXMLDocument(docName);
			element = new Element("contact");
			oldElement = new Element("oldkey");
			oldElement.setText(_oldKey);
			newElement = new Element("newkey");
			newElement.setText(_newKey);
			element.addContent(oldElement);
			element.addContent(newElement);
			doc.getRootElement().addContent(element);
			
			try {
			      XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
			      file = new File(this.logDir + "log_contact.xml");
			      FileOutputStream fos = new FileOutputStream(file);
			      out.output(doc, fos);
			    }
			    catch (IOException e) {
			      System.err.println(e);
			    }

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	public boolean isContactMigrated(String _oldKey) {
		if (this.contactLog.get(_oldKey) != null) {
			return true;
		}
		return false;
	}
	
	public boolean isAppointmentMigrated(String _oldKey) {
		if (this.appointmentLog.get(_oldKey) != null) {
			return true;
		}
		return false;
	}

}
