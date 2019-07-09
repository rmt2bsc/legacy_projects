package com;

import java.util.Date;
import java.util.Calendar;
import  java.util.GregorianCalendar;

import com.util.SystemException;
import com.util.DatabaseException;
import com.util.NotFoundException;
import com.util.RMT2Utility;  
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import java.util.Properties;


public class sprintapp {   

  int rc;
  private Logger logger;

  public sprintapp() {
	  logger = Logger.getLogger("FDS");
//	  logger = Logger.getRootLogger();
  }


    public void testTime() {
    	Properties props = null;
    	String test = null;
    	try {
    		props = RMT2Utility.loadPropertiesObject("SystemParms");
    		test = props.getProperty("dbdriver");
    		test = props.getProperty("dburl");
    		test = props.getProperty("anything");
    		
    		logger.log(Level.DEBUG, "This is my debug message");
    	    logger.debug("This is my debug message using the direct method.");
    	    logger.log(Level.INFO,"This is my info message.");
    	    logger.log(Level.WARN,"This is my warn message.");
    	    logger.log(Level.ERROR,"This is my error message.");
    	    logger.log(Level.FATAL,"This is my fatal message.");
    	    
    		java.util.Date dates[] = RMT2Utility.getWeekDates(new GregorianCalendar(2006, 8, 1, 7, 11, 0).getTime());
        	System.out.println("Total number of dates Returned : " + dates.length);
        	System.out.println("Test Date Values" );
        	for (int ndx = 0; ndx < dates.length; ndx++) {
        		System.out.println("Date " + (ndx + 1) + ".  "  + dates[ndx].toString() + " ===> " + RMT2Utility.formatDate(dates[ndx], "E MM/dd") );
        	}
    	}
    	catch (SystemException e) {
    		System.out.println(e.getMessage());
    	}
    	return;
    }
    
    public static void main(String[] args) {
    	sprintapp test = new sprintapp();
    	test.testTime();
      }
}
