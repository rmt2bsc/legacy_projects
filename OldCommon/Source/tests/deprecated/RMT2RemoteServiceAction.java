package com.action;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;

import com.api.ContactAddressApi;

import com.bean.Zipcode;

import com.constants.ContactsConst;
import com.constants.RMT2ServletConst;

import com.action.AbstractActionHandler;

import com.factory.ContactsFactory;

import com.servlet.RMT2RemoteServiceServlet;

import com.util.ActionHandlerException;
import com.util.SystemException;
import com.util.DatabaseException;
import com.util.RemoteServiceException;
import com.util.ContactAddressException;
import com.util.RMT2Utility;


/**
 * This class provides action handlers to respond to remote scripting request from javascript clients. 
 * 
 * @author Roy Terrell
 *
 */
public class RMT2RemoteServiceAction extends AbstractActionHandler {

	private ContactAddressApi addrApi;
    protected static final String XML_HEADER = "<?xml version=\"1.0\" ?>";

  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public RMT2RemoteServiceAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {

    super(_context, _request);
    this.className = "RMT2RemoteServiceAction";
  }
  
  /**
   * Retrieves a subset of zipcode data in xml format.   The client is required to sent zip code key as the CGI parameter, "zipcode".
   * 
   * @return Values for the following properties in xml format: zipcode, city, state, areacode, and county. 
   * @throws RemoteServiceException
   */
  public String getZipCodeData() throws RemoteServiceException {
		  StringBuffer results = new StringBuffer(100);
	 	  String zipcode = request.getParameter("zipcode");
	 	 ContactAddressApi api = null;
	 	 Zipcode zip = null;
	 	
		  try {
			  api = ContactsFactory.createAddressApi(this.dbConn);
			  zip = api.findZipByCode(zipcode);
		  }
		  catch (ContactAddressException e) {
			  throw new RemoteServiceException(e.getMessage());
		  }
		  catch (DatabaseException e) {
			  throw new RemoteServiceException(e.getMessage());
		  }
		  catch (SystemException e) {
			  throw new RemoteServiceException(e.getMessage());
		  }
		  
	      // Return null if we could not obtain zipcode object.
		  if (zip == null) {
			  return null;
		  }
		 
		  // begin to build results in xml
		  results.append(RMT2RemoteServiceAction.XML_HEADER);
		  results.append("<zip>\n");
		 
		  // Set zipcode
		  results.append("\t<zipcode>");
		  results.append(zip.getZip());
		  results.append("</zipcode>\n" );
		 
		  // Set city
		  results.append("\t<city>");
		  results.append(zip.getCity());
		  results.append("</city>\n" );
		 
		  // Set state
		  results.append("\t<state>");
		  results.append(zip.getState());
		  results.append("</state>\n" );
		 
		  // Set area code
		  results.append("\t<areacode>");
		  results.append(zip.getAreaCode());
		  results.append("</areacode>\n" );
		 
	      //	 Set county name
		  results.append("\t<county>");
		  results.append(zip.getCountyName());
		  results.append("</county>\n" );
		 
		  results.append("</zip>" );
		  return results.toString();
	  } // end getZipCodeData


  protected void receiveClientData() throws ActionHandlerException{}
  protected void sendClientData() throws ActionHandlerException{}
  public void add() throws ActionHandlerException{}
  public void edit() throws ActionHandlerException{}
  public void save() throws ActionHandlerException{}
  public void delete() throws ActionHandlerException{}
  
} // end class RMT2RemoteServiceAction