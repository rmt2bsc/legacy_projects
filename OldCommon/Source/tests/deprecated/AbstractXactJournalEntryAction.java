package com.action;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import com.util.SystemException;
import com.util.DatabaseException;
import com.util.XactException;


/**
 * Abstracct class that provides default implementations for the XactJounalEntryAction interface.    It handles the management of
 *  transaction journal entries.   To create a concrete XactJournalEntryAction as a subclass of AbstractXactJournalEntryAction you
 *  need to provide implementations for the following methods:
 *  
 *  public void getXactList() throws XactException;
 *  public void getXactDetails() throws XactException;
 *  public void validateXactDetails() throws XactException;
 * 
 * @author Roy Terrell
 *
 */
public abstract class AbstractXactJournalEntryAction extends XactAction implements XactJournalEntryAction {

	/**
	 * Default constructor.
	 *
	 */
	public AbstractXactJournalEntryAction() {
		 super();
	}
	
  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public AbstractXactJournalEntryAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
      super(_context, _request);
  }
  
  /**
   * Initializes this object using _conext and _request.  This is needed in the 
   * event this object is inistantiated using the default constructor.
   * 
   * @throws SystemException
   */
  public void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
      super.init(_context, _request);
  }

  /**
   * Adds transaction journal entries. 
   *  
   * @throws XactException 
   */
   public void addXact() throws XactException {
	   super.addXact();
       return;
   }
   
  
  /**
   * Saves transaction journal entry details. 
   *
   * @return int - the transaction id. 
   * @throws XactException
   * @throws DatabaseException
   */
	public int saveXact() throws XactException, DatabaseException {
		this.validateJournalEntryDetails();
		return super.saveXact();
	}

   /**
	 * Action handler to drive the reversal of a transaction journal entry.
	 *  
	 * @return int - the transaction id. 
	 * @throws XactException
	 * @throws DatabaseException
	 */
	  public int reverseXact() throws XactException, DatabaseException {
	      return super.reverseXact();
	  }
 
} // end AbstractXactJournalEntryAction