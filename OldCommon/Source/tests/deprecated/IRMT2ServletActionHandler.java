package com.action;

import com.util.DatabaseException;
import com.util.ActionHandlerException;

/**
 * Interface that specifies the methods necessary to basic action handlers for client requests which provides services for handling search criteria, search results,
 * single item select and edit, single item add, saving a single item, and the deleting of a single item.
 *
 * @author Roy Terrell
 * @deprecated
 */
public interface IRMT2ServletActionHandler {

	/**
	 * Displays the Search Console for the first time.
	 * <p>
	 * The developer is responsible for passing any required data to the client via the request and session objects
	 *
     *@throws ActionHandlerException
	 */
    public void startSearchConsole() throws ActionHandlerException;

	  /**
	   * Uses data from the client's request object to retrieve existing data for the client
	   * from any given data source such as a flat file, databse, XML Document, and ect.
	   * <p>
	   * The developer is responsible for passing any required data to the client via the request and session objects
	   *
	   * @throws ActionHandlerException
	   */
	  public void edit() throws ActionHandlerException;

	 /**
	   * Preapres the client for adding an entity.
       * <p>
	   * The developer is responsible for passing any required data to the client via the request and session objects.
	   *
	   * @throws ActionHandlerException
	   */
	  public void add() throws ActionHandlerException;


      /**
       * Processes the client's request by attemting to persist its data.
       * <p>
	   * The developer is responsible for passing any required data to the client via the request and session objects.
	   *
	   * @throws ActionHandlerException
	   * @throws DatabaseException
       */
      public void save() throws ActionHandlerException, DatabaseException;


      /**
       * Processes the client's request by attemting to delete its data.
       * <p>
	   * The developer is responsible for passing any required data to the client via the request and session objects.
	   *
	   * @throws ActionHandlerException
	   * @throws DatabaseException
       */
      public void delete() throws ActionHandlerException, DatabaseException;

} // end IRMT2ServletActionHandler