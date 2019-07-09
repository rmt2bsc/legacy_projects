package com.bean;

import java.io.InputStream;
import java.io.IOException;
//import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Enumeration;
import java.util.logging.Level;

import javax.servlet.ServletContext;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.core.framework.PlatformServletContext;
import org.eclipse.birt.core.framework.IPlatformContext;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;

import com.bean.RMT2Base;

import com.util.RMT2Utility;
import com.util.SystemException;



/**
 * 
 * @author roy.terrell
 *
 */
public class RMT2BirtEngine extends RMT2Base {

	private static IReportEngine birtEngine = null;
	//private static Properties configProps = new Properties();
    private static ResourceBundle configProps = null;
	private final static String configFile = "BirtConfig";
//    private final static String configFile = "BirtConfig.properties";
	
	public static synchronized void initBirtConfig() {
		loadEngineProps();
	}
	
	public static synchronized IReportEngine getBirtEngine(ServletContext sc) {
		 if (birtEngine != null) {
			 return birtEngine;
		 }
		 
		  EngineConfig config = new EngineConfig();
		  if( configProps != null)	{
			  //String logLevel = configProps.getProperty("logLevel");
              String logLevel = configProps.getString("logLevel");
			  Level level = Level.OFF;
			  if ("SEVERE".equalsIgnoreCase(logLevel))   {
				  level = Level.SEVERE;
			  }
			  else if ("WARNING".equalsIgnoreCase(logLevel))   {
				  level = Level.WARNING;
			  }
			  else if ("INFO".equalsIgnoreCase(logLevel))   {
				  level = Level.INFO;
			  } 
			  else if ("CONFIG".equalsIgnoreCase(logLevel))  {
				  level = Level.CONFIG;
			  } 
			  else if ("FINE".equalsIgnoreCase(logLevel))   {
				  level = Level.FINE;
			  }
			  else if ("FINER".equalsIgnoreCase(logLevel))  {
				  level = Level.FINER;
			  }
			  else if ("FINEST".equalsIgnoreCase(logLevel))  {
				  level = Level.FINEST;
			  }
			  else if ("OFF".equalsIgnoreCase(logLevel))  {
				  level = Level.OFF;
			  }
			  //config.setLogConfig(configProps.getProperty("logDirectory"), level);
              config.setLogConfig(configProps.getString("logDirectory"), level);
		  }
	
		  config.setEngineHome("");
		  IPlatformContext context = new PlatformServletContext( sc );
		  config.setPlatformContext( context );

		  try	  {
			  Platform.startup( config );
		  }
		  catch ( BirtException e )  {
			  e.printStackTrace( );
		  }
		  IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
		  birtEngine = factory.createReportEngine( config );
		  return birtEngine;
	}
	
	public static synchronized void destroyBirtEngine() {
		if (birtEngine == null) {
			return;
		}
		birtEngine.shutdown();
		Platform.shutdown();
		birtEngine = null;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	private static void loadEngineProps() {
        try {
            configProps = RMT2Utility.loadAppConfigProperties(configFile);    
        }
        catch (SystemException e) {
            e.printStackTrace();
        }
/*        
		try {
			//Config File must be in classpath
			ClassLoader cl = Thread.currentThread ().getContextClassLoader();
			InputStream in = null;
			in = cl.getResourceAsStream (configFile);
			configProps.load(in);
			in.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
*/        
	}
	
	/**
	 * Determines if BIRT configuration file has been loaded.
	 * 
	 * @return true for loaded and false when not loaded.
	 */
	public static boolean isConfigPropsLoaded() {
		//return (RMT2BirtEngine.configProps.size() > 0 ? true : false);
        return (RMT2BirtEngine.configProps == null ? false : true);
	}
	
	/**
	 * Gets the value of property that resides in the BIRT Configuaration Properties file.
	 * 
	 * @param property The key to the value to retrieve.
	 * @return The value of the property or null if property does not exist.
	 */
	public static String getConfigProperty(String property) {
        return RMT2BirtEngine.configProps.getString(property);
		//return RMT2BirtEngine.configProps.getProperty(property);
	}

} // end class
