package com.api.db.orm;

import com.api.config.HttpSystemPropertyConfig;

import com.api.security.pool.AppPropertyPool;

import com.util.RMT2File;
import com.util.RMT2XmlUtility;
import com.util.SystemException;

/**
 * Helper class for managing the configuration of ORM Datasources.
 * 
 * @author Roy Terrell
 *
 */
public class OrmConfigHelper {

    /**
     * Obtains system properties related to the directory path where the ORM Datasource  configuration files can be found.  
     *  The various properties should be configured in the AppParms.properties and/or the application's web.xml.
     * 
     * @throws SystemException
     * @throws NotFoundException
     * 
     * Refactor this method to new class  com.api.db.orm.OrmConfigHelper
     */
    public static String getOrmDatasourceFullDirPath(String docName) throws SystemException {
        String dsDir;
        String msg;
        StringBuffer temp = new StringBuffer(50);
        try {
            String ext = RMT2File.getfileExt(docName);
            if (ext == null || (ext != null && !ext.equalsIgnoreCase(".xml"))) {
                docName += ".xml";
            }

            dsDir = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_DATASOURCE_DIR);
            if (dsDir == null) {
                dsDir = System.getProperty(HttpSystemPropertyConfig.PROPNAME_DATASOURCE_DIR);
            }
            if (dsDir == null) {
                msg = "ORM data source configuration was not found for optional environment variable, webapps_dir";
                RMT2XmlUtility.logger.warn(msg);
            }

            // Build full file path of XML document
            temp.append(dsDir == null ? "" : dsDir);
            docName = temp.toString() + docName;
            return docName;
        }
        catch (NullPointerException e) {
            msg = "One of the XML Datasource config properties in SystemParms.properties are null";
            RMT2XmlUtility.logger.error(msg);
            throw new SystemException(msg, e);
        }
    }
    


}
