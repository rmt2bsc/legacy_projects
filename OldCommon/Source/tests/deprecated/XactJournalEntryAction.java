package com.action;

import com.util.DatabaseException;
import com.util.XactException;

/**
 * Interface that specifies the methods necessary to process Transaction Journal Entries.
 * 
 * @author Roy Terrell
 *
 */
public interface XactJournalEntryAction {
	
  /**
   * Retrieves a list of transactions pertaining to one of the following transaction journal entry types:  Sales on Account, 
   * Cash Receipts, Cash Disbursements, or Purchases.
   *  
   * @throws XactException
   */
  public void getJournalEntryList() throws XactException;
  

  /**
   * Gets the transaction journal entry details from the client.
   * 
   * @return int
   * @throws XactException
   */
  public int getJournalEntryDetails() throws XactException;
  
  /**
   * Obtains all available items pertaining to a journal entry transaction.
   * 
   * @throws XactException
   */
  public void getAvailJournalEntryItems() throws XactException;
    
  /**
   * Handler for adding transaction journal entries. 
   *  
   * @throws XactException 
   */
   public void addXact() throws XactException;
  
  /**
   * Saves transaction journal entry details. 
   *
   * @return int - the transaction id. 
   * @throws XactException
   * @throws DatabaseException
   */
	public int saveXact() throws XactException, DatabaseException;

   /**
	 * Action handler to drive the reversal of a transaction journal entry.
	 *  
	 * @return int - the transaction id. 
	 * @throws XactException
	 * @throws DatabaseException
	 */
	 public int reverseXact() throws XactException, DatabaseException;

 	/**
 	 * Validates the transaction.
 	 * 
 	 * @param _details Object containing Sales on Account, Cash Receipts, Cash Disbursements, or Purchases data. 
 	 * @throws XactException
 	 */
    public void validateJournalEntryDetails() throws XactException;
   

} // end XactJournalEntryAction