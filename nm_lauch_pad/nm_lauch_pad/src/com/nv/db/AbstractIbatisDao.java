package com.nv.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.nv.security.SecurityToken;

/**
 * An abstract common DAO class which functions to initilize and shut down
 * itself as well as any of its descendents as it pertains to the database
 * connection.
 * <p>
 * Upon construction, the descendent is initialized with a proper database
 * connection via an instance of {@link SecurityToken}.
 * 
 * @author rterrell
 *
 */
public abstract class AbstractIbatisDao {

    private static Logger logger;

    protected SecurityToken token;

    protected SqlMapClient con;

    protected String msg;

    /**
     * Create a AbstractIbatisDao without a valid connection to the database.
     */
    protected AbstractIbatisDao() {
        AbstractIbatisDao.logger = Logger.getLogger(AbstractIbatisDao.class);
        AbstractIbatisDao.logger
                .info("Logger initialized for AbstractIbatisDao");
        return;
    }

    /**
     * Create a AbstractIbatisDao initialized with a user's security token.
     */
    public AbstractIbatisDao(SecurityToken token) {
        this();
        this.token = token;
        this.con = token.getDbCon();
        logger.info("DAO connection is initialized");
    }

    /**
     * Release any allocated resources
     */
    public void close() {
        this.con = null;
        this.token = null;
    }

    /**
     * Returns the SQL connectio object that is internal to the iBatis
     * environment.
     * 
     * @return {@link Connection}
     */
    public Connection getInternalConnection() {
        try {
            return this.con.getDataSource().getConnection();
        } catch (SQLException e) {
            return null;
        }
    }
}
