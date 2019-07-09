package com.nv.db;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.nv.util.GeneralUtil;

/**
 * A class designed to initialize the iBatis environment by loading the
 * application's sql-map-config.xml configuration and establishing an anonymous
 * connection to the database.
 * 
 * @author rterrell
 *
 */
public class IbaistEnvConfig {

    private static final Logger logger = Logger
            .getLogger(IbaistEnvConfig.class);

    private static final String SITE_CONFIG = "config/site.properties";

    /**
     * Loads the iBatis configuration, creates an annonymous connection to the
     * database, and returns the connection to the caller.
     * 
     * @return an instance of {@link SqlMapClient}
     * @throws DatabaseException
     *             I/O errors or general database access errors.
     */
    public static final SqlMapClient getSqlMap() throws DatabaseException {
        SqlMapClient connection = null;
        String msg = null;
        try {
            Reader reader = Resources.getResourceAsReader("sql-map-config.xml");
            Properties appProps = GeneralUtil
                    .loadProperties(IbaistEnvConfig.SITE_CONFIG);
            connection = SqlMapClientBuilder
                    .buildSqlMapClient(reader, appProps);
            return connection;
        } catch (IOException e) {
            msg = "I/O error occurred attempting to obtain the iBatis connection client";
            logger.error(msg);
            throw new DatabaseException(msg, e);
        } catch (Exception e) {
            msg = "A general error occurred attempting to obtain the iBatis connection client";
            logger.error(msg, e);
            throw new DatabaseException(msg, e);
        }
    }
}
