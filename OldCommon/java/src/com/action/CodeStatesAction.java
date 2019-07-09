package com.action;

import javax.servlet.ServletContext; 

import javax.servlet.http.HttpServletRequest;

import com.bean.RMT2Base;
import com.bean.State;

import com.api.db.DatabaseException;
import com.api.db.DbSqlConst;
import com.api.DataSourceApi;

import com.action.AbstractActionHandler;  

import com.bean.RMT2TagQueryBean;

import com.util.SystemException;
import com.util.NotFoundException;
import com.util.BusinessException;
import com.util.RMT2Utility;

import com.constants.RMT2ServletConst;



/**
 * This class provided action handlers to add, edit, validate, and save state/providence data. 
 * @author roy Terrell
 *
 */
public class CodeStatesAction extends AbstractActionHandler {

  private String className;
  private String methodName;
  

  /**
   * Main contructor for this action handler.
   * 
   * @param _context The servlet context to be associated with this action handler
   * @param _request The request object sent by the client to be associated with this action handler
   * @throws SystemException
   */
  public CodeStatesAction(ServletContext _context, HttpServletRequest _request) throws SystemException {

    super(_context, _request);
    this.className = "CodeStatesAction";
    this.pageTitle = "States/Province Code Maintenance";
  }

  /**
   * This method accepts the primary key value of a selected state/province as Http input, which is required to be refered to as "selCbx" from the JSP page,
   * and retrieves the details of the selected state from the database.   The results are passed back to the client for editing as an object of type 
   * {@link State} via the request object.  
    * <p>
    * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object to be handle at the 
    * discretion of the client: 
	 * <p>
	 * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 * <tr>
	 *   <td><strong>Attribute</strong></td>
	 *   <td><strong>Type</strong></td>
	 *   <td><strong>Description</strong></td>
	 *  </tr>
	 * <tr>
	 *   <td>states</td>
	 *   <td>{@link State}</td>
	 *   <td>The State/Providence object</td>
	 * </tr>
	 *</table>
	 * 
   * @return int - Generally returs 1= Success.   Otherwise, one of the following exceptions are thrown.
   * @throws NotFoundException
   * @throws DatabaseException
   * @throws SystemException
   */
  public int editAction() throws NotFoundException, DatabaseException, SystemException {

    this.methodName = "editAction()";
    StringBuffer sql = new StringBuffer(100);
    int stateId = 0;
    int countryId = 0;
    String stateIdParm = request.getParameter("selCbx");

      // If form submitted without a selection being made, throw an exception
    if (stateIdParm == null) {
				throw new SystemException(this.getDbConn(), 146, null);
    }

       // Get Account Category data which will be passed to the response page
    DataSourceApi dso = this.getDbConn().getDataObject("StatesCodeView");
    dso.setDatasourceSql(DbSqlConst.WHERE_KEY, "id = " + stateIdParm);
    dso.executeQuery();

    State stateBean = new State();
    if (dso.nextRow()) {
      stateBean.setStateId(stateIdParm);
      stateBean.setCountryId(new Double(dso.getColumnValue("CountryId")).intValue());
    }
    else {
		  this.msgArgs.clear();
		  this.msgArgs.add(stateIdParm);
        throw new DatabaseException(this.getDbConn(), 149, this.msgArgs);
    }
    dso.close();

    RMT2TagQueryBean query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
    query.setPageTitle("Edit - " + this.pageTitle);
    this.request.setAttribute("states", stateBean);

    return RMT2Base.SUCCESS;

  }

/**
 * Applies new and modified code groups and code details  to the database for all like code tables.
 * 
 * @return  int - Generally returs 1= Success.   Otherwise, one of the following exceptions are thrown.
 * @throws NotFoundException
 * @throws DatabaseException
 * @throws BusinessException
 * @throws SystemException
 */
  public int saveAction() throws NotFoundException, DatabaseException, BusinessException, SystemException {

    this.methodName = "saveAction";
    String  temp;
    DataSourceApi dso = this.getDbConn().getDataObject("StatesCodeView");
    State stateBean = new State();

    try {
      temp = this.request.getParameter("id");
      if (temp == null || temp.length() <= 0) {
        temp = "0";
      }
      stateBean.setStateId(temp);

      temp = this.request.getParameter("countryId");
      if (temp == null || temp.length() <= 0) {
        temp = "0";
      }
      stateBean.setCountryId(new Double(temp).intValue());
//      stateBean.setShortname(this.request.getParameter("shortname"));
//      stateBean.setLongname(this.request.getParameter("longname"));

        // Validate Account Category Bean
      this.validateState(stateBean);

      if (stateBean.getStateId() == null) {

          // Clear out the where clause
        dso.setDatasourceSql(DbSqlConst.WHERE_KEY, null);

          // Retrieve an empty result set
        dso.setDatasourceSql(DbSqlConst.WHERE_KEY, "id = -1");

          // Create non-scrollable updateable resultset
        dso.executeQuery(false, true);

          //  Create a new row
        dso.createRow();

        dso.setColumnValue("CountryId", new Double(stateBean.getCountryId()));
//        dso.setColumnValue("Shortname", stateBean.getShortname());
//        dso.setColumnValue("Longname", stateBean.getLongname());

        RMT2Utility.doRowTimeStamp(request, dso, true);
        dso.insertRow();
      }  // end if

         // Determine if an update needs to be applied to the database
      if (stateBean.getStateId().length() > 0) {
        dso.setDatasourceSql(DbSqlConst.WHERE_KEY, null);
        dso.setDatasourceSql(DbSqlConst.WHERE_KEY, "id = " + stateBean.getStateId());

          // Create non-scrollable updateable resultset
        dso.executeQuery(false, true);
        if (!dso.nextRow()) {
						throw new NotFoundException(this.getDbConn(), 148, null);
        }
        dso.setColumnValue("CountryId", new Double(stateBean.getCountryId()));
//        dso.setColumnValue("Shortname", stateBean.getShortname());
//        dso.setColumnValue("Longname", stateBean.getLongname());
        RMT2Utility.doRowTimeStamp(request, dso, false);
        dso.updateRow();
      }

      dso.commitUOW();
      dso.close();
      return RMT2Base.SUCCESS;
    }  // end try

    catch (DatabaseException e) {
      dso.rollbackUOW();
      throw e;
    }
    catch (NumberFormatException e) {
				throw new SystemException(this.getDbConn(), 124, null);
    }
  }

  /**
   * Creates a new State Bean to be used for adding a new State to the system.
    * <p>
    * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object to be handle at the 
    * discretion of the client: 
	 * <p>
	 * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 * <tr>
	 *   <td><strong>Attribute</strong></td>
	 *   <td><strong>Type</strong></td>
	 *   <td><strong>Description</strong></td>
	 *  </tr>
	 * <tr>
	 *   <td>states</td>
	 *   <td>{@link State}</td>
	 *   <td>The State/Providence object</td>
	 * </tr>
	 *</table> 
   * @return  int
   * @throws SystemException
   */
  public int addAction() throws SystemException {

    State  stateBean = new State();
    stateBean.setCountryId(0);
    RMT2TagQueryBean query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
    query.setPageTitle("New - " + this.pageTitle);
    this.request.setAttribute("states", stateBean);

    return RMT2Base.SUCCESS;
  }

/**
 * Validates the account bean.   If an error occurs throw a BusinessException exception.
 * 
 * @param _data {@link State} - Details accoring states and providences.
 * @return int - Returns 1=success, -1=Failure
 * @throws BusinessException
 */
  protected int validateState(State _data) throws BusinessException {

    this.methodName = "validateCategory";
    if (_data.getCountryId() <= 0) {
      throw new BusinessException(this.getDbConn(), 150, null);
    }

    return RMT2Base.SUCCESS;
 }

  protected void receiveClientData() throws ActionHandlerException{}
  protected void sendClientData() throws ActionHandlerException{}
  public void add() throws ActionHandlerException{}
  public void edit() throws ActionHandlerException{}
  public void save() throws ActionHandlerException{}
  public void delete() throws ActionHandlerException{}
  
  
  
}