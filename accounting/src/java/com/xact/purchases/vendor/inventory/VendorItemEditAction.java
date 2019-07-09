package com.xact.purchases.vendor.inventory;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.db.orm.DataSourceAdapter;

import com.bean.VendorItems;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;
import com.inventory.InventoryFactory;
import com.inventory.ItemMasterException;

import com.util.SystemException;

/**
 * This class provides action handlers to manage vendor item edit requests.
 * 
 * @author Roy Terrell
 *
 */
public class VendorItemEditAction extends VendorItemAssocAction implements ICommand {
    private static final String COMMAND_BACK = "PurchasesVendor.ItemEdit.back";

    private static final String COMMAND_SAVE = "PurchasesVendor.ItemEdit.venditemsave";

    private VendorItems vi;

    /**
     * Default constructor.
     * 
     * @throws SystemException
     */
    public VendorItemEditAction() throws SystemException {
	super();
	logger = Logger.getLogger("VendorItemEditAction");
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param context 
     *          The servlet context to be associated with this action handler
     * @param request 
     *          The request object sent by the client to be associated with this 
     *          action handler
     * @throws SystemException
     */
    public VendorItemEditAction(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
    }

    /**
     * Initializes this object using _conext and request.  This is needed in the 
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
	super.init(context, request);
    }

    /**
     * Processes the client's request using request, response, and command.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);
	if (command.equalsIgnoreCase(VendorItemEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(VendorItemEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Applies {@link VendorItems} changes to the database.   The changes are gathered 
     * from the client's request object and packaged into a VendorItems object 
     * which is passed to the InventoryApi to update the database.   
     *  
     * @throws ItemMasterException
     */
    public void save() throws ActionHandlerException {
	super.save();
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = InventoryFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.api.maintainVendorItem(this.vi);
	    tx.commitUOW();
	    this.setUpVendorItemEdit(vi.getCreditorId(), vi.getItemId());
	    this.msg = "Vendor Item changes were saved successfully";
	    return;
	}
	catch (ItemMasterException e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    tx.rollbackUOW();
	    throw new ActionHandlerException(this.msg);
	}
	finally {
		this.api = null;
		tx.close();
		tx = null;
	}
    }

    /**
     * Navigate back to the vendor purchases search page.
     */
    protected void doBack() throws ActionHandlerException {
	super.receiveClientData();
	this.sendClientData();
	return;
    }

    /**
     * Obtains vendor item details from the UI components.  Also calls ancestor method.
     */
    protected void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();

	try {
	    this.vi = InventoryFactory.createVendorItem();
	    DataSourceAdapter.packageBean(this.request, vi);
	}
	catch (SystemException e) {
	    this.msg = "Problem gathering Vendor Item request parameters.";
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
    }

}