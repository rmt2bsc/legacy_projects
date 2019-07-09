package com.remoteservices.http;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;
import com.api.db.DatabaseException;
import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.RMT2Exception;
import com.util.SystemException;

/**
 * Abstract producer of remote services which the results are only capable of being 
 * managed by Java centric applications.  Native java instances are the only data 
 * type returned to the client as a result of its operation.   This class provides 
 * the template needed to process a client's service request over the Http protocol.
 * 
 * @author Roy Terrell
 * @deprecated Will be removed in future releases.   Use package, com.api.messaging.http.
 * 
 */
public abstract class AbstractInternalServerAction extends AbstractHttpServerAction {
    private Logger logger;

    /**
     * Default Constructor
     * 
     * @throws SystemException
     */
    public AbstractInternalServerAction() throws SystemException {
        super();
        this.logger = Logger.getLogger("AbstractInternalServerAction");
    }

    /**
     * Contructs a AbstractInternalServerAction object by initializing the servlet
     * context and the user's request.
     * 
     * @param _context
     *            The servlet context to be associated with this action handler
     * @param _request
     *            The request object sent by the client to be associated with
     *            this action handler
     * @throws SystemException
     */
    public AbstractInternalServerAction(Context _context, Request _request) throws SystemException, DatabaseException {
        super(_context, _request);
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
        super.init(_context, _request);
    }

    /**
     * Drives the process of servicing the client's request and returns any data to client 
     * as Java instances regardless whether the operation completed successfully or erroneously. 
     * 
     * @param request
     *            The HttpRequest object
     * @param response
     *            The HttpResponse object
     * @param command
     *            Comand issued by the client.
     * @throws SystemException
     *             when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
        try {
            super.processRequest(request, response, command);
        }
        catch (RMT2Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            this.outData = e;
        }
        this.sendClientData();
    }

}