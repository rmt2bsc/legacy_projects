package com.api.ip;

import org.apache.log4j.Logger;

import com.controller.Request;

import com.bean.IpLocation;
import com.util.InvalidDataException;
import com.util.RMT2Utility;
import com.util.SystemException;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.db.DatabaseConnectionBean;

/**
 * Class that implements the AddressApi interface which provides functionality
 * to maintian addresses for personal and business contacts.
 * 
 * @author RTerrell
 * 
 */
class IpBeanImpl extends RdbmsDaoImpl implements IpApi {
    private static Logger logger = Logger.getLogger("IpBeanImpl");
    
    protected String criteria;
    
    protected RdbmsDaoQueryHelper daoHelper;

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at
     * the acestor level.
     * 
     * @param dbConn
     * @throws SystemException
     * @throws DatabaseException
     */
    public IpBeanImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(dbConn);
	this.className = "IpBeanImpl";
	this.baseView = "AddressView";
	this.baseBeanClass = "com.bean.Address";
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean and
     * the HttpServletRequest object at the acestor level.
     * 
     * @param dbConn
     * @param req
     * @throws SystemException
     * @throws DatabaseException
     */
    public IpBeanImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
	this.daoHelper = new RdbmsDaoQueryHelper(dbConn);
    }

    /**
     * Initializes the logger for this class instance.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    protected void initApi() throws DatabaseException, SystemException {
	super.initApi();
    }

    /**
     * Releases allocated resources.
     */
    public void close() {
        super.close();
    }

    /* (non-Javadoc)
     * @see com.api.ip.IpApi#getIpDetails(long)
     */
    public IpLocation getIpDetails(long ip) throws IpException {
        IpLocation loc = new IpLocation();
        loc.addCustomCriteria("ip_from <= " + ip);
        loc.addRowLimitClause("first");
        loc.addOrderBy(IpLocation.PROP_IPFROM, IpLocation.ORDERBY_DESCENDING);
        loc = (IpLocation) this.daoHelper.retrieveObject(loc);
        return loc;
    }

    /* (non-Javadoc)
     * @see com.api.ip.IpApi#getIpDetails(java.lang.String)
     */
    public IpLocation getIpDetails(String ip) throws IpException {
        long netAddr = 0;
        try {
            netAddr = RMT2Utility.getNetworkIp(ip);
            return this.getIpDetails(netAddr);
        }
        catch (InvalidDataException e) {
            throw new IpException("IP address details could not be obtained due to invalid IP address value", e);
        }
    }
    
   
}
