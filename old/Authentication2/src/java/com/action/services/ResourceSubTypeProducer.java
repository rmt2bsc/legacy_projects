package com.action.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.remoteservices.http.AbstractExternalServerAction;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.security.user.ResourceApi;
import com.api.security.user.ResourceFactory;

import com.bean.UserResourceSubtype;
import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;

/**
 * Service for retrieving resource sub type data from the database as XML. 
 * The format of the XML returned will parallel the bean, 
 * {@link com.bean.UserResourceSubtype UserResourceSubtype}.  The following 
 * key/value pairs can sent by the client to be used by this service as 
 * selection criteria to query the data source:
 * <p>
 * <table border="1">
 *   <tr>
 *     <th>Argument Name</th>
 *     <th>Data Type</th>
 *     <th>Description</th>
 *   </tr>
 *   <tr>
 *     <td>SUB_TYPE_ID</td>
 *     <td>int</td>
 *     <td>resource sub type id</td>
 *   </tr>
 *   <tr>
 *     <td>SUB_TYPE_NAME</td>
 *     <td>String</td>
 *     <td>resource sub type name</td>
 *   </tr>
 *   <tr>
 *     <td>RESOURCE_TYPE_ID</td>
 *     <td>int</td>
 *     <td>resource type id</td>
 *   </tr>      
 * </table>
 * <p>
 * 
 * @author RTerrell
 * @deprecated Will be removed from future versions.   Use {@link com.services.resources.UserResourceSubTypeService UserResourceSubTypeService}
 * 
 */
public class ResourceSubTypeProducer extends AbstractExternalServerAction {
    private Logger logger;

    /**
     * Inner class tha manages user request arguments for the resource sub type
     * service.
     * 
     * @author appdev
     * 
     */
    protected class Args {
        private int resourceTypeId;
        private int subTypeId;
        private String subTypeName;

        protected Args(String aResourceTypeId, String aSubTypeId,
                String aSubTypeName) {
            try {
                resourceTypeId = Integer.parseInt(aResourceTypeId);
            }
            catch (NumberFormatException e) {
                resourceTypeId = 0;
            }

            try {
                subTypeId = Integer.parseInt(aSubTypeId);
            }
            catch (NumberFormatException e) {
                subTypeId = 0;
            }

            subTypeName = aSubTypeName;
        }

        /**
         * @return the resourceTypeId
         */
        protected int getResourceTypeId() {
            return resourceTypeId;
        }

        /**
         * @return the subTypeId
         */
        protected int getSubTypeId() {
            return subTypeId;
        }

        /**
         * @return the subTypeName
         */
        protected String getSubTypeName() {
            return subTypeName;
        }
    } // End inner class
    
    

    /**
     * Default constructor.
     * 
     * @throws SystemException
     */
    public ResourceSubTypeProducer() throws SystemException {
        this.logger = Logger.getLogger("ResourceSubTypeProducer");
    }

    /**
     * Retrieves {@link com.bean.UserResourceSubtype UserResourceSubtype} data
     * in XML format based using sub type id, sub type name, and resource type
     * id data values for selection criteria.
     * 
     * @throws ServiceHandlerException
     */
    public void processData() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
        ResourceApi api = ResourceFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector());
        UserResourceSubtype urs = ResourceFactory.createXmlUserResourceSubtype();
        Args args = (Args) this.inData;

        // Apply data to the service which the user requested to serve as
        // selection criteria.
        if (args != null) {
            urs.setRsrcTypeId(args.getResourceTypeId());
            urs.setRsrcSubtypeId(args.getSubTypeId());
            urs.setName(args.getSubTypeName());
        }

        try {
            String data = (String) api.getSubType(urs);
            this.outData = data;
            logger.log(Level.DEBUG, data);
        }
        catch (DatabaseException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionHandlerException(e.getMessage());
        }
        finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Accepts the sub type id, sub type name, and resource type id values from
     * the user's request which will be used to retrieve the Resource Sub Type
     * data.
     * 
     * @throws AbstractActionHandler
     */
    public void receiveClientData() throws ActionHandlerException {
        String resourceTypeId = this.request.getParameter("RESOURCE_TYPE_ID");
        String subTypeId = this.request.getParameter("SUB_TYPE_ID");
        String subTypeName = this.request.getParameter("SUB_TYPE_NAME");
        Args args = new Args(resourceTypeId, subTypeId, subTypeName);
        this.inData = args;
    }
}