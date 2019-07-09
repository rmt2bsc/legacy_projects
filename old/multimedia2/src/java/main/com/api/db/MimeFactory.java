package com.api.db;



import com.api.filehandler.FileListenerConfig;
import com.bean.RMT2Base;
import com.bean.db.DatabaseConnectionBean;

import com.util.RMT2BeanUtility;
import com.util.SystemException;

/**
 * A factory for creating instances of MimeContentApi interface.
 * 
 * @author appdev
 *
 */
public class MimeFactory extends RMT2Base {

    /**
     * Default contructor
     */
    private MimeFactory() {
	return;
    }

    /**
     * Creates an instance of the MimeContentApi from the implementation specified in the 
     * home application's MIME configuration file.  In order for this to work, the application's MIME 
     * configufation .properties file must include an entry which identifies the MimeContentApi's 
     * implementation such as <i>mime.handler=com.api.db.DefaultSybASABinaryImpl</i>.
     * 
     * @param className
     *          a fully qualified name of the class to instantiate dynamically.
     * @return {@link com.api.db.MimeContentApi MimeContentApi}
     * @throws SystemException
     *          if <i>className</i> is discovered not to be an instance of MimeContentApi.
     */
    public static MimeContentApi getMimeContentApiInstance(String className) {
	RMT2BeanUtility beanUtil = new RMT2BeanUtility();
	try {
	    Object obj = beanUtil.createBean(className);
	    if (obj instanceof MimeContentApi) {
		return (MimeContentApi) obj;
	    }
	}
	catch (Exception e) {
	    throw new SystemException(e);
	}

	String msg = "File Processor instance must be a derivative of com.api.db.MimeContentApi";
	throw new SystemException(msg);
    }
    
    /**
     * 
     * @param con
     * @param className
     * @return
     */
    public static MimeContentApi getMimeContentApiInstance(DatabaseConnectionBean con, String className) {
	MimeContentApi api = MimeFactory.getMimeContentApiInstance(className);
	api.initApi(con);
	return api;
    }

    /**
     * Creates an instance of the MimeContentApi from the implementation specified in the 
     * home application's MIME configuration file and initializes it with an instance of 
     * FileListenerConfig.
     * 
     * @param config
     * @return
     */
    public static MimeContentApi getMimeContentApiInstance(FileListenerConfig config) {
	MimeContentApi api = getMimeContentApiInstance(config.getHandlerClass());
	api.setConfig(config);
	return api;
    }

}
