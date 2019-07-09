package com.apiimpl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.api.XactManagerApi;

import com.api.db.DatabaseException;
import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;
import com.api.db.orm.RdbmsDataSourceImpl;

import com.bean.Xact;
import com.bean.XactCategory;
import com.bean.XactCodes;
import com.bean.XactCodeGroup;
import com.bean.XactType;
import com.bean.XactTypeItemActivity;
import com.bean.VwXactList;
import com.bean.CustomerActivity;
import com.bean.CreditorActivity;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

import com.constants.XactConst;

import com.factory.XactFactory;

import com.util.XactException;
import com.util.SystemException;
import com.util.RMT2Utility;


/**
 * Api Implementation that manages transaction events.
 * 
 * @author Roy Terrell
 *
 */
public class XactManagerApiImpl extends RdbmsDataSourceImpl implements XactManagerApi  {

   private String criteria;
   private Xact xactBean;
   private List  xactItems;
   

   /**
    * Default Constructor.
    * 
    * @throws DatabaseException
    * @throws SystemException
    */
  protected XactManagerApiImpl() throws DatabaseException, SystemException   {
		 super();
   }

  /**
   * Constructor which uses database connection
   * 
   * @param dbConn
   * @throws DatabaseException
   * @throws SystemException
   */
	public XactManagerApiImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException   {
	    super(dbConn);
		 this.setBaseView("XactView");
		 this.setBaseClass("com.bean.Xact");
	}



	/**
	 * Constructor begins the initialization of the DatabaseConnectionBean at the acestor level and creates a 
	 * {@link Xact} bean based on _xactTypeId and _amt.
	 * 
	 * @param dbConn
	 * @param _xactTypeId
	 * @param _amt
	 * @throws DatabaseException
	 * @throws SystemException
	 */
    public XactManagerApiImpl(DatabaseConnectionBean dbConn, int _xactTypeId, double _amt) throws DatabaseException, SystemException   {
        this(dbConn);
		  this.xactBean = XactFactory.createXact(_xactTypeId, _amt);
    }

	/**
	* Constructor begins the initialization of the DatabaseConnectionBean at the acestor level and creates a 
	* {@link Xact} bean based on _xactTypeId and _amt.
	* 
	* @param dbConn
	* @param _xactTypeId
	* @param _amt
	* @throws DatabaseException
	* @throws SystemException
	*/
	public XactManagerApiImpl(DatabaseConnectionBean dbConn, HttpServletRequest _request) throws DatabaseException, SystemException   {
		this(dbConn);
		this.request = _request;
	}

    public Xact findXactById(int value) throws XactException {
		String methodName = "findXactById";
		this.setBaseView("XactView");
		this.setBaseClass("com.bean.Xact");

		this.criteria = "id = " + value;
		try {
		    List list = this.find(this.criteria);
			if (list.size() == 0) {
				return null;
			}
			return (Xact) list.get(0);
		}
		catch (IndexOutOfBoundsException e) {
			this.msgArgs.clear();
			this.msgArgs.add(String.valueOf(value));
			throw new XactException(this.connector, 609, this.msgArgs);
		}
		catch (SystemException e) {
			throw new XactException(e.getMessage(), e.getErrorCode(),
					this.className, methodName);
		}
		finally {
			this.setBaseView("XactPostDetailsView");
			this.setBaseClass("com.bean.XactPostDetails");
		}
	}

    
	  public List findXact(String _criteria, String _orderBy) throws XactException {
		String methodName = "findXact";
		this.criteria = _criteria;
		try {
		    List list = this.find(this.criteria, _orderBy);
			return list;
		}
		catch (IndexOutOfBoundsException e) {
			this.msgArgs.clear();
			this.msgArgs.add(_criteria);
			throw new XactException(this.connector, 600, this.msgArgs);
		}
		catch (SystemException e) {
			throw new XactException(e.getMessage(), e.getErrorCode(),
					this.className, methodName);
		}
	}

	  /**
		 * Retrieves customer transaction history using _custId.
		 * 
		 * @param _custId Customer's internal id.
		 * @throws XactExcepion
		 */
	public List findCustomerXactHist(int _custId) throws XactException {
		String oldView = this.setBaseView("VwCustomerXactHistView");
		String oldClass = this.setBaseClass("com.bean.VwCustomerXactHist");
		String methodName = "findCustomerXactHist";
		String whereClause = null;
		String orderByClause = null;

		whereClause = " customer_id = " + _custId;
		orderByClause = " xact_id desc ";
		try {
		    List list = this.find(whereClause, orderByClause);
			return list;
		}
		catch (SystemException e) {
			throw new XactException(e.getMessage(), e.getErrorCode(),
					this.className, methodName);
		}
		finally {
			this.setBaseView(oldView);
			this.setBaseClass(oldClass);
		}
	}
	  

	  public List  findCreditorXactHist(int _credId) throws XactException {
		   String oldView = this.setBaseView("VwCreditorXactHistView");
		   String oldClass = this.setBaseClass("com.bean.VwCreditorXactHist");
		   String methodName = "findCreditorXactHist";
		   String whereClause = null;
		   String orderByClause = null;
		   
		   whereClause = " creditor_id = " + _credId;
		   orderByClause = " xact_id desc ";
		   try {
		       List list = this.find(whereClause, orderByClause);
			   return list;
		   }
			catch (SystemException e) {
			   throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
			finally {
				this.setBaseView(oldView);
				this.setBaseClass(oldClass);
			}
	  }
	  
   public List findXactCatg(String _criteria) throws XactException {
		 String methodName = "findXactCatg";
		 this.setBaseView("XactCategoryView");
         this.setBaseClass("com.bean.XactCategory");
		 this.criteria = _criteria;
		 try {
		     List list = this.find(this.criteria);
		  	return list;
		 }
		 catch (SystemException e) {
		  		throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
		 }
	 }

   public XactCategory findXactCatgById(int _id) throws XactException {
		 String methodName = "findXactCatgById";
         String oldView = this.setBaseView("XactCategoryView");
         String oldClass = this.setBaseClass("com.bean.XactCategory");
		 this.criteria = "id = " + _id;
		 try {
		     List list = this.find(this.criteria);
		  	  if (list.size() == 0) {
		  		  return null;
		  	  }
		  	  return (XactCategory) list.get(0);
		 }
		 catch (IndexOutOfBoundsException e) {
		     this.msgArgs.clear();
			  this.msgArgs.add(String.valueOf(_id));
			  throw new XactException(this.connector, 615, this.msgArgs);
		 }
		 catch (SystemException e) {
		  	throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
		 }
         finally {
             this.setBaseView(oldView);
             this.setBaseClass(oldClass);
         }
	 }


   public List findXactCodeGroup(String _criteria) throws XactException {
		 String methodName = "findXactCodeGroup";
		 this.criteria = _criteria;
		 try {
		     List list = this.find(this.criteria);
		  	return list;
		 }
		 catch (SystemException e) {
		  		throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
		 }
	 }

   public XactCodeGroup findXactCodeGroupById(int _id) throws XactException {
		 String methodName = "findXactCodeGroupById";

		 this.setBaseView("XactCodeGroupView");
         this.setBaseClass("com.bean.XactCodeGroup");
		 this.criteria = "id = " + _id;
		 try {
		     List list = this.find(this.criteria);
		  	if (list.size() == 0) {
		  		return null;
		  	}
		  	return (XactCodeGroup) list.get(0);
		 }
		 catch (IndexOutOfBoundsException e) {
					this.msgArgs.clear();
					this.msgArgs.add(String.valueOf(_id));
					throw new XactException(this.connector, 616, this.msgArgs);
		 }
		 catch (SystemException e) {
		  		throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
		 }
	 }


   public List findXactCode(String _criteria) throws XactException {
		 String methodName = "findXactCode";
		 this.criteria = _criteria;
		 try {
		     List list = this.find(this.criteria);
		  	return list;
		 }
		 catch (SystemException e) {
		  		throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
		 }
	 }


   public XactCodes findXactCodeById(int _id) throws XactException {
		 String methodName = "findXactCodeById";
		 this.setBaseView("XactCodesView");
         this.setBaseClass("com.bean.XactCodes");
		 this.criteria = "id = " + _id;
		 try {
		     List list = this.find(this.criteria);
		  	 if (list.size() == 0) {
		  	    return null;
		  	 }
		  	 return (XactCodes) list.get(0);
		 }
		 catch (IndexOutOfBoundsException e) {
			  this.msgArgs.clear();
			  this.msgArgs.add(String.valueOf(_id));
			  throw new XactException(this.connector, 617, this.msgArgs);
		 }
		 catch (SystemException e) {
		  	  throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
		 }
	 }

   public List findXactCodeByGroupId(int _id) throws XactException {
		 String methodName = "findXactCodeByGroupId";
		 this.setBaseView("XactCodesView");
         this.setBaseClass("com.bean.XactCodes");
		 this.criteria = "group_id = " + _id;
		 try {
		     List list = this.find(this.criteria);
		  	return list;
		 }
		 catch (SystemException e) {
		  		throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
		 }
	 }

   public List findXactType(String _criteria) throws XactException {
		 String methodName = "findXactType";
		 this.setBaseView("XactTypeView");
         this.setBaseClass("com.bean.XactType");
		 this.criteria = _criteria;
		 try {
		     List list = this.find(this.criteria);
		  	  return list;
		 }
		 catch (SystemException e) {
		  	  throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
		 }
	 }


   public XactType findXactTypeById(int _id) throws XactException {
	  	 String methodName = "findXactTypeById";

		 this.setBaseView("XactTypeView");
		 this.setBaseClass("com.bean.XactType");
		 this.criteria = "id = " + _id;
		 try {
		     List list = this.find(this.criteria);
		 	  if (list.size() == 0) {
		 		  return null;
		 	  }
		 	  return (XactType) list.get(0);
		 }
		 catch (IndexOutOfBoundsException e) {
		     this.msgArgs.clear();
	        this.msgArgs.add(String.valueOf(_id));
		     throw new XactException(this.connector, 618, this.msgArgs);
		 }
		 catch (SystemException e) {
				throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
		 }
	 }


   public List findXactTypeByCatgId(int _id) throws XactException {
		 String methodName = "findXactTypeByCatgId";
		 this.criteria = "xact_category_id = " + _id;
		 try {
		     List list = this.find(this.criteria);
		  	return list;
		 }
		 catch (SystemException e) {
		  		throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
		 }
	 }

   
   public List findXactTypeItemsActivityByXactId(int _xactId) throws XactException {
         String methodName = "findXactTypeItemsByXactId";
         String oldView = null;
         String oldClass = null;

         oldView = this.setBaseView("XactTypeItemActivityView");
         oldClass = this.setBaseClass("com.bean.XactTypeItemActivity");
         this.criteria = "xact_id = " + _xactId;
         try {
             List list = this.find(this.criteria);
              return list;
         }
         catch (SystemException e) {
                throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
         }
         finally {
             this.setBaseView(oldView);
             this.setBaseClass(oldClass);
         }
   }
   
   
   public List findVwXactTypeItemActivityByXactId(int _xactId) throws XactException {
       String methodName = "findVwXactTypeItemActivityByXactId";
       String oldView = null;
       String oldClass = null;
       String orderBy = null;

       oldView = this.setBaseView("VwXactTypeItemActivityView");
       oldClass = this.setBaseClass("com.bean.VwXactTypeItemActivity");
       this.criteria = "xact_id = " + _xactId;
       orderBy = " description asc ";
       try {
           List list = this.find(this.criteria, orderBy);
            return list;
       }
       catch (SystemException e) {
              throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
       }
       finally {
           this.setBaseView(oldView);
           this.setBaseClass(oldClass);
       }
 }
   
   
   /**
    * Retrieves all transaction type item belonging to _xactTypeId
    * 
    * @param _xactId
    * @return ArrayList of XactTypeItem objects
    * @throws XactException
    */
   public List findXactTypeItemsByXactTypeId(int _xactTypeId) throws XactException {
         String methodName = "findXactTypeItemsByXactId";
         String oldView = null;
         String oldClass = null;

         oldView = this.setBaseView("XactTypeItemView");
         oldClass = this.setBaseClass("com.bean.XactTypeItem");
         this.criteria = "xact_type_id = " + _xactTypeId;
         try {
             List list = this.find(this.criteria, " name ");
              return list;
         }
         catch (SystemException e) {
                throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
         }
         finally {
             this.setBaseView(oldView);
             this.setBaseClass(oldClass);
         }
   }


   public VwXactList findXactListViewByXactId(int _xactId) throws XactException {
       String methodName = "findXactListViewByXactId";
       String oldView = this.setBaseView("VwXactListView");
       String oldClass = this.setBaseClass("com.bean.VwXactList");
       this.criteria = "id = " + _xactId;
       try {
           List list = this.find(this.criteria);
            if (list.size() <= 0) {
                return null;
            }
            return (VwXactList) list.get(0);
       }
       catch (SystemException e) {
          throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
       }
       finally {
           this.setBaseView(oldView);
           this.setBaseClass(oldClass);
       }
   }
   
   public List findXactListViewByXactTypeId(int _xactTypeId) throws XactException {
       String methodName = "findXactListViewByXactTypeId";
       String oldView = this.setBaseView("VwXactListView");
       String oldClass = this.setBaseClass("com.bean.VwXactList");
       this.criteria = "xact_type_item_xact_type_id = " + _xactTypeId;
       try {
           List list = this.find(this.criteria);
            return list;
       }
       catch (SystemException e) {
          throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
       }
       finally {
           this.setBaseView(oldView);
           this.setBaseClass(oldClass);
       }
   }
   
   	  
    public Xact getXactBean() {
    	return this.xactBean;
    }
    public void setXactBean(Xact value) {
    	this.xactBean = value;
    }
	 
    public List getXactItems() {
    	return this.xactItems;
    }
    public void setXactItems(List value) {
    	this.xactItems = value;
    }

   public int createBaseXact() throws XactException   {
       int     newXactId  = 0;
       if (this.dynaApi == null) {
           throw new XactException(this.connector, 162, null);
       }

       //  Validate Base Transaction
       this.validateBaseXact();

       // Create transaction by making an entry into the xact table
       newXactId = this.createXact();
       this.xactBean.setId(newXactId);

       //  Successfully return to caller the ID of the transaction just created.
       return newXactId;
   }

   /**
    * Validates the base of the transaction.   The following validations must be satified:  
    * <ul>
    *    <li>Base transaction object is valid</li>
    *    <li>Base transaction type id is greater than zero</li>
    *    <li>Transaction date must have a value</li>
    *    <li>Transaction date is a valid date</li>
    *    <li>Transaction date is not greater than curent date</li>
    *    <li>Transaction tender is entered</li>
    *    <li>Transaction tender's negotiable instrument number is entered,  if applicable.</li>
    *    <li>Transaction amount must be entered</li>
    *    <li>Transaction reason is entered</li>
    * </ul>
    * 
    * @throws XactException
    * @deprecated
    */
  private void validateBaseXact() throws XactException   {
      String method = "validateBaseXact";

      this.preValidateXact(this.xactBean);
      if (this.xactBean == null) {
          throw new XactException("Transaction Base could not be updated since base object is null", -1, this.className, method);
      }

      //  Validate the Transaction Type
      if (this.xactBean.getXactTypeId() <= 0) {
          throw new XactException(this.connector, 601, null);
      }
 
      this.postValidateXact(this.xactBean);
  }


  
  /**
   * This method is responsible for the creation of the transaction.   Returns the transaction id that was  created.
   * 
   * @return
   * @throws XactException
   * @deprecated use maintainXact(Xact, ArrayList) to manage a transaction.
   */
   protected int createXact()  throws XactException  {
	   Object  newObj = null;
	   int          newId = 0;
       String temp  = null;
       java.util.Date today = new java.util.Date();

       try {
           this.dynaApi.clearParms();
           this.dynaApi.addParm("id", Types.INTEGER,  this.xactBean.getId(), true);
           temp  = RMT2Utility.formatDate(this.xactBean.getXactDate(), "MM-dd-yyyy");
           if (temp == null || temp.equals("")) {
               temp  = RMT2Utility.formatDate(today, "MM-dd-yyyy");
           }
           this.dynaApi.addParm("xact_date", Types.VARCHAR,  temp, true);
           this.dynaApi.addParm("xact_amt", Types.NUMERIC,  this.xactBean.getXactAmount(), false);
           this.dynaApi.addParm("xact_type_id", Types.INTEGER,  this.xactBean.getXactTypeId(), false);
           this.dynaApi.addParm("xact_tender_id", Types.INTEGER,  this.xactBean.getTenderId(), false);
           this.dynaApi.addParm("xact_check_no", Types.VARCHAR,  this.xactBean.getNegInstrNo(), false);
           this.dynaApi.addParm("xact_reason", Types.VARCHAR,  this.xactBean.getReason(), false);

           // Call stored procedure to add Transaction
           this.dynaApi.execute("exec usp_add_xact ? ? ? ? ? ? ?");

           //  Get new Transaction Id
           newObj = this.dynaApi.getOutParm("id");
           if (newObj instanceof Integer) {
               newId = ((Integer) newObj).intValue();
               this.xactBean.setId(newId);
           }

           System.out.println("Transaction created successfully");
           return newId;
       }
       catch (DatabaseException e) {
           throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
       }
       catch (SystemException e) {
           throw new XactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
       }
   }

   /**
    * Drives the process of creating entries into the xact_type_item_activity table by cycling
    *  through all elements of the transaction type item array.   Validates that the base transaction 
    *  amount is equal to the sum of all Transaction Type Item Activity amounts.
    *  
    * @return ArrayList of items added to the database.
    * @throws XactException
    * @deprecated use maintainXact(Xact, ArrayList) to manage a transaction.
    */
   public List createBaseXactItems() throws XactException {
       int totalItems = 0;
       double totalItemAmount = 0;
       XactTypeItemActivity xtia = null;

       if (this.dynaApi == null) {
           throw new XactException(this.connector, 162, null);
       }

       // Return to caller if transaction type item array has not been initialized.
       if (this.xactItems == null) {
           return null;
       }
       else {
           totalItems = this.xactItems.size();
       }
       
       // Begin to add each transaction type item to the database.
       for (int ndx = 0; ndx < totalItems; ndx++) {
           xtia = (XactTypeItemActivity) this.xactItems.get(ndx);
           xtia.setXactId(this.xactBean.getId());
           this.validateBaseXactItemActivity(xtia);
           this.createBaseXactItem(xtia);
           totalItemAmount += xtia.getAmount();
       }

       // No need to compare totals if there are no items.
       if (totalItems > 0) {
           // Transaction amount must equal the sum of all item amounts.
           if ( this.xactBean.getXactAmount() != totalItemAmount) {
               throw new XactException(this.connector, 629, null);
           }           
       }

       //  Successfully return the total number of items added.
       return this.xactItems;
   }
   
   /**
    * Creates a entry in the xact_type_item_activity table.
    * 
    * @param _xtia
    * @return int - id of new transaction type item activity object.
    * @throws XactException
    * @deprecated use maintainXact(Xact, ArrayList) to manage a transaction.
    */
   protected int createBaseXactItem(XactTypeItemActivity _xtia) throws XactException {
       String method = "createXactItem";
       Object  newObj = null;
       int  newId = 0;

       try {
           this.dynaApi.clearParms();
           this.dynaApi.addParm("id", Types.INTEGER,  _xtia.getId(), true);
           this.dynaApi.addParm("xact_id", Types.INTEGER,  _xtia.getXactId(), false);
           this.dynaApi.addParm("xact_type_item_id", Types.INTEGER,  _xtia.getXactTypeItemId(), false);
           this.dynaApi.addParm("amount", Types.NUMERIC,  _xtia.getAmount(), false);
           this.dynaApi.addParm("description", Types.VARCHAR,  _xtia.getDescription(), false);

           // Call stored procedure to add Transaction
           this.dynaApi.execute("exec usp_add_xact_type_item_activity ? ? ? ? ?");

           //  Get new Transaction Id
           newObj = this.dynaApi.getOutParm("id");
           if (newObj instanceof Integer) {
               newId = ((Integer) newObj).intValue();
               _xtia.setId(newId);
           }

           System.out.println("Transaction created successfully");
           return newId;
       }
       catch (DatabaseException e) {
           throw new XactException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
       catch (SystemException e) {
           throw new XactException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }

   
   /**
    * Validates a transaction type item object by ensuring that a transaction type item id and item description is provided.
    * Validates the base of the transaction.   The following validations must be satified:  
    * <ul>
    *    <li>Transaction Type Item  Activity object must valid</li>
    *    <li>Transaction Type Item Id must valid (greater than zero)</li>
    *    <li>Transaction Type Item Activity Description cannot be null</li>
    * </ul>
    * 
    * @param _xtia
    * @throws XactException
    * @deprecated use maintainXact(Xact, ArrayList) to manage a transaction.
    */
   private void validateBaseXactItemActivity(XactTypeItemActivity _xtia) throws XactException {
	   // Execute custom pre validation logic.
	   this.preValidateXactItem(_xtia);
	   
       if (_xtia == null) {
           throw new XactException(this.connector, 625, null);
       }
       if (_xtia.getXactTypeItemId() <= 0) {
           throw new XactException(this.connector, 626, null);
       }
//       if (_xtia.getAmount() <= 0) {
//           throw new XactException(this.dbo, 627, null);
//       }
       if (_xtia.getDescription() == null || _xtia.getDescription().length() <= 0) {
           throw new XactException(this.connector, 628, null);
       }
       
       // Execute custom post validation logic.
	   this.postValidateXactItem(_xtia);
	   
       return;
   }
   
   
    
   
   
   
   public int maintainXact(Xact _xact, List _xactItems ) throws XactException {
	   int rc = 0;
       rc = this.createXact(_xact);
       this.createXactItems(_xact, _xactItems);
  	   return rc;
   }

   /**
    * Creates a transaction.
    * 
    * @param _xact The transaction to be added.
    * @return New transaction id.
    * @throws XactException
    */
   protected int createXact(Xact _xact)  throws XactException  {
  	 int rc = 0;
  	 
  	this.preCreateXact(_xact);
   	 this.validateXact(_xact);
   	 rc = this.insertXact(_xact);
   	this.postCreateXact(_xact);
   	 return rc;
   }


   /**
    * Ensures that transaction date has a value before applying the transaction to the database.   If transaction date is null or 
    * does not exist, default transaction date to the current day.   Override this method to execute custom logic before base 
    * transaction is added to the database.
    * 
    * @param _xact The target transaction
    */
   protected void preCreateXact(Xact _xact) {
	   java.util.Date today = new java.util.Date();
	   if (_xact.getXactDate() == null) {
		   _xact.setXactDate(today);
	   }
        if (_xact.getXactSubtypeId() <= 0) {
            _xact.setNull("xactSubtypeId");
        }
	   return;
   }
   
   /**
    * Override this method to execute custom logic after base transaction is added to the database.
    * 
    * @param _xact The target transaction
    */
   protected void postCreateXact(Xact _xact) {
	   return;
   }
   
   
   /**
    * Inserts a properly initialized transaction into the database.
    * 
    * @param _xact The target transaction.
    * @return New transaction id.
    * @throws XactException
    */
   private int insertXact(Xact _xact)  throws XactException  {
	   int rc = 0;
       DaoApi dao = DataSourceFactory.createDao(this.connector);
	   UserTimestamp ut = null;
		 
	   try {
		   ut = RMT2Utility.getUserTimeStamp(this.request);
		   _xact.setDateCreated(ut.getDateCreated());
		   _xact.setDateUpdated(ut.getDateCreated());
		   _xact.setUserId(ut.getLoginId());
		   rc = dao.insertRow(_xact, true);
			 
		   // Set transaction id with the auto-generated key.
		   _xact.setId(rc);
		   return rc;
	   }
	   catch (DatabaseException e) {
		   throw new XactException(e);
	   }
	   catch (SystemException e) {
		   throw new XactException(e);
	   }
   }

   
   /**
    * Validates the base of the transaction.   The following validations must be satified:  
    * <ul>
    *    <li>Base transaction object is valid</li>
    *    <li>Base transaction type id is greater than zero</li>
    * </ul>
    * 
    * @throws XactException
    */
  private void validateXact(Xact _xact) throws XactException   {
      String method = "validateXact";

      // Execute custom pre validations 
      this.preValidateXact(_xact);
      
      if (_xact == null) {
          throw new XactException("Transaction Base could not be updated since base object is null", -1, this.className, method);
      }

      //  Validate the Transaction Type
      if (_xact.getXactTypeId() <= 0) {
          throw new XactException(this.connector, 601, null);
      }
      
      // Execute custom post validations
      this.postValidateXact(_xact);
  }

  
  /**
   * Override this method to execute custom logic before base transaction validations.
   * 
   * @param _xact The transaction object to be validated
   * @throws XactException Validation error occurred.
   */
  protected void preValidateXact(Xact _xact) throws XactException {
	  return;
  }
  
  /**
   * Override this method to execute custom logic after base transaction validations.
   * 
   * @param _xact The transaction object to be validated.
   * @throws XactException Validation error occurred.
   */
  protected void postValidateXact(Xact _xact) throws XactException {
	  return;
  }


  /**
   * Drives the process of creating entries into the xact_type_item_activity table by cycling
   * through all elements of the transaction type item array.   Validates that the base transaction 
   * amount is equal to the sum of all Transaction Type Item Activity amounts.
   * 
   * @param _xact The base transaction that is to be associated with each transaction _xactItems element.
   * @param _xactItems The list of transaction items to apply to the database whch is expected to be of 
   * type {@link XactTypeItemActivity}
   * @return 0 when either _xactItems is null or has no items available.   > 0 to indicate the number of items successfully processed. 
   * @throws XactException
   */
  protected int createXactItems(Xact _xact, List _xactItems) throws XactException {
      int totalItems = 0;
      int succesCount = 0;
      double totalItemAmount = 0;
      XactTypeItemActivity xtia = null;

      // Return to caller if transaction type item array has not been initialized.
      if (_xactItems == null) {
          return 0;
      }
      else {
          totalItems = _xactItems.size();
      }
      
      // Begin to add each transaction type item to the database.
      for (int ndx = 0; ndx < totalItems; ndx++) {
          xtia = (XactTypeItemActivity) _xactItems.get(ndx);
          xtia.setXactId(_xact.getId());
          this.validateXactItem(xtia);
          this.insertXactItem(xtia);
          totalItemAmount += xtia.getAmount();
          succesCount++;
      }

      // Transaction amount must equal the sum of all item amounts.
      if (totalItems > 0) {
          if (Math.abs(_xact.getXactAmount()) != Math.abs(totalItemAmount)) {
              throw new XactException(this.connector, 629, null);
          }    
      }
      

      //  Successfully return the total number of items added.
      return succesCount;
  }

   /**
    * Creates a entry in the xact_type_item_activity table.
    * 
    * @param _xtia
    * @return int - id of new transaction type item activity object.
    * @throws XactException
    */
   protected int insertXactItem(XactTypeItemActivity _xtia) throws XactException {
	   int rc = 0;
       DaoApi dao = DataSourceFactory.createDao(this.connector);
	   UserTimestamp ut = null;
		 
	   try {
		   ut = RMT2Utility.getUserTimeStamp(this.request);
		   _xtia.setDateCreated(ut.getDateCreated());
		   _xtia.setDateUpdated(ut.getDateCreated());
		   _xtia.setUserId(ut.getLoginId());
		   rc = dao.insertRow(_xtia, true);
			 
		   // Set transaction id with the auto-generated key.
		   _xtia.setId(rc);
		   return rc;
	   }
	   catch (DatabaseException e) {
		   throw new XactException(e);
	   }
	   catch (SystemException e) {
		   throw new XactException(e);
	   }
   }
   
   /**
    * Validates a transaction type item object by ensuring that a transaction type item id and item description is provided.
    * Validates the base of the transaction.   The following validations must be satified:  
    * <ul>
    *    <li>Transaction Type Item  Activity object must be valid</li>
    *    <li>Transaction Type Item Id must valid (greater than zero)</li>
    *    <li>Transaction Type Item Activity Description cannot be null</li>
    * </ul>
    * 
    * @param _xtia
    * @throws XactException
    */
   private void validateXactItem(XactTypeItemActivity _xtia) throws XactException {
	   // Execute custom pre validation logic.
	   this.preValidateXactItem(_xtia);
	   
       if (_xtia == null) {
           throw new XactException(this.connector, 625, null);
       }
       if (_xtia.getXactTypeItemId() <= 0) {
           throw new XactException(this.connector, 626, null);
       }
//       if (_xtia.getAmount() <= 0) {
//           throw new XactException(this.dbo, 627, null);
//       }
       if (_xtia.getDescription() == null || _xtia.getDescription().length() <= 0) {
           throw new XactException(this.connector, 628, null);
       }
       
       // Execute custom post validation logic.
	   this.postValidateXactItem(_xtia);
	   
       return;
   }
   

   
   /**
    * Override this method to execute custom logic before base transaction Item Activity validations.
    * 
    * @param _xtia
    * @throws XactException
    */
   protected void preValidateXactItem(XactTypeItemActivity _xtia) throws XactException {
	   return;
   }

   /**
    * Override this method to execute custom logic after base transaction Item Activity validations.
    * @param _xtia
    * @throws XactException
    */
   protected void postValidateXactItem(XactTypeItemActivity _xtia) throws XactException {
	   return;
   }

   
   public String createCustomerActivity(int _customerId, int _xactId, double _amount) throws XactException {
	   String confirmNo = null;
	   UserTimestamp ut = null;
	   Xact xact = null;
       DaoApi dao = DataSourceFactory.createDao(this.connector);
	   CustomerActivity ca = XactFactory.createCustomerActivity(_customerId, _xactId, _amount);
	   
	   try {
		   xact = this.findXactById(_xactId);
		   if (xact == null) {
			   throw new XactException("Problem creating customer activity.  Transaction object invalid");
		   }
		   ut = RMT2Utility.getUserTimeStamp(this.request);
		   ca.setDateCreated(ut.getDateCreated());
		   ca.setDateUpdated(ut.getDateCreated());
		   ca.setUserId(ut.getLoginId());
		   dao.insertRow(ca, true);
		   
		   // Update transaction with confirmation number
		   switch (xact.getXactTypeId()) {
		   		case XactConst.XACT_TYPE_CASHPAY:
		   		case XactConst.XACT_TYPE_CASHSALES:
		   			confirmNo = this.getCustomerConfirmationNo();
                    if (xact.getTenderId() == 0) {
                        xact.setNull("tenderId");    
                    }
                    if (xact.getXactSubtypeId() == 0) {
                        xact.setNull("xactSubtypeId");    
                    }
		   			xact.setConfirmNo(confirmNo);
		   			xact.addCriteria("Id", xact.getId());
		   			dao.updateRow(xact);
		   			break;
		   } // end switch
		   
		   return confirmNo;
	   }
	   catch (DatabaseException e) {
		   throw new XactException(e);
	   }
	   catch (SystemException e) {
		   throw new XactException(e);
	   }
   }
   
  
   public String createCreditorActivity(int _creditorId, int _xactId, double _amount) throws XactException {
	   UserTimestamp ut = null;
	   Xact xact = null;
       DaoApi dao = DataSourceFactory.createDao(this.connector);
	   CreditorActivity ca = XactFactory.createCreditorActivity(_creditorId, _xactId, _amount);
	   
	   try {
		   xact = this.findXactById(_xactId);
		   if (xact == null) {
			   throw new XactException("Problem creating creditor activity.  Transaction object invalid");
		   }
		   ut = RMT2Utility.getUserTimeStamp(this.request);
           if (xact.getTenderId() == 0) {
               xact.setNull("tenderId");    
           }
           if (xact.getXactSubtypeId() == 0) {
               xact.setNull("xactSubtypeId");    
           }
		   ca.setDateCreated(ut.getDateCreated());
		   ca.setDateUpdated(ut.getDateCreated());
		   ca.setUserId(ut.getLoginId());
		   dao.insertRow(ca, true);
		   
		   return null;
	   }
	   catch (DatabaseException e) {
		   throw new XactException(e);
	   }
	   catch (SystemException e) {
		   throw new XactException(e);
	   }
   }

   /**
    * Retrieves a transaction confirmation number pertaining to a customer activity event.   Override this method to provide 
    * custom logic to produce a customer confirmation number
    * 
    * @return confirmation number
    */
   protected String getCustomerConfirmationNo() {
	   return this.createGenericConfirmNo();
   }
   
   /**
    * Retrieves a transaction confirmation number pertaining to a customer activity event.   Override this method to provide 
    * custom logic to produce a creditor confirmation number
    * 
    * @return confirmation number
    */
   protected String getCreditorConfirmationNo() {
	   return this.createGenericConfirmNo();
   }
   
   /**
    * Generates a generic confirmation number using the long number representation of a 
    * java.util.Date object containing the current timestamp at the time of invocation
    * 
    * @return confirmation number
    */
   private String createGenericConfirmNo() {
	   java.util.Date today = new java.util.Date();
	   String confirmNo = String.valueOf(today.getTime());
	   return confirmNo;
   }
   
   /**
    * Reverses a transaction and its detail items.  As a result of this 
    * operation, the original transaction amount is permanently changed 
    * by offsetting the previous transaction.
    * 
    * @param _xact The origianl transaction that is to be reversed.
    * @param __xactItems Transaction items to be reversed.
    * @return New id of the reversed transaction.
    * @throws XactException
    */
   public int reverseXact(Xact _xact, List _xactItems) throws XactException {
       int rc = 0;
       this.reverseBaseXact(_xact);
       this.reverseXactItems(_xactItems);
       this.preReverseXact(_xact, _xactItems);
       _xact.setId(0);
       rc = this.maintainXact(_xact, _xactItems);
       _xact.setId(rc);
       this.postReverseXact(_xact, _xactItems);
       return rc;
   }
   
   
   /**
    * Reverses the base transaction amount.   As a result sof this operation, the internal transaction amount is permanently changed. 
    * The reversal process simply multiplies a -1 to the base transaction amount which basically will zero out the transaction.
    * <p>
    * <p>
    * <b>Note:</b>
    * <p>
    * The transaction type id of the original transaction is carried over to the reverse transaction to maintain history.  The reversal 
    * transction code is identified as "xactSubType".  
    * 
    * @return {@link Xact} - The reversed base transaction object.
    */
	private void reverseBaseXact(Xact _xact) {
	    _xact.setXactSubtypeId(XactConst.XACT_TYPE_REVERSE);
	    _xact.setXactAmount( _xact.getXactAmount() * XactConst.REVERSE_MULTIPLIER);
        String reason = _xact.getReason();
	    _xact.setReason("Reversed Transaction " + _xact.getId() + ": " + reason);
	    return;
	}
   
	   /**
	    * Reverses all items of an existing transaction withou applying changes to the database.   By default each item is expected
	    * to be of type {@link XactTypeItemActivity}.    See documentation on method, Object reverseXactItems(Object, int), about 
	    * overriding to utilize transaction items of a different data type.
	    * 
	    * @param __xactItems Transaction items to be reversed.
	    * @return ArrayList of reversed transaction items.   By default, each item is expected to be of type {@link XactTypeItemActivity}.
	    */
	   private List reverseXactItems(List _xactItems) {
           if (_xactItems == null) {
               return null;
           }
           List newList = new ArrayList();
	       Object item = null;
	       int total = _xactItems.size();
	       
	       for (int ndx = 0; ndx < total; ndx++) {
	           item = this.reverseXactItems(_xactItems.get(ndx));
	           newList.add(item);
	       }
	       return newList;
	   }
	   

	   /**
	    * Reverses transaction item, _obj, and returns the results to the caller.   As a result of this operation, the internal item's transaction 
	    * amount is permanently changed.  The reversal process simply multiplies a -1 to the item's transaction amount which basically 
	    * will zero out the item.  By default, the runtime type of each item is expected to be {@link XactTypeItemActivity}.  Override this 
	    * method to handle an object type other than {@link XactTypeItemActivity}.
	    *   
	    * @param _obj is the transaction item object that is to be reversed.
	    * @return _obj in its reverse state.   By default, _obj is of type {@link XactTypeItemActivity}.
	    */
	   protected Object reverseXactItems(Object _obj) {
	       XactTypeItemActivity newItem = (XactTypeItemActivity) _obj;
	       
	       double amount = newItem.getAmount() * XactConst.REVERSE_MULTIPLIER;
	       newItem.setAmount(amount);
	       
	       // Reset id to make it appear that this is a new entry.
	       newItem.setId(0);
	       return newItem;
	   }

	
   /**
    * Override this method to perform any transaction reversal logic before _xact is applied to the database.   This method is 
    * invoked from the reverseXact method just after the modification of the transaction amount and reason.
    *  
    * @param _xact The transaction that is being reversed.
    */
   protected void preReverseXact(Xact _xact, List _xactItems) {
	   return;
   }
   
   /**
    * Override this method to perform any transaction reversal logic after _xact is applied to the database.   This method is 
    * invoked from the reverseXact method 
    * 
    * @param _xact The transaction that is being reversed.
    */
   protected void postReverseXact(Xact _xact, List _xactItems) {
	   return;
   }

   /**
    * Determines if _xact can modified or adjusted which will generally require a new transaction to be created.  Typical target 
    * transactions would be reversals, cancellations, and returns
    * 
    * @param _xact The transaction that is to be managed
    * @return true indicating it is eligible to be changed, and false indicating change is not allowd.
    * @throws XactException when _xact is invalid or null.
    */
   public boolean isXactModifiable(Xact _xact)  throws XactException {
	   if (_xact == null) {
		   throw new XactException("Transaction object is null"); 
	   }
	   return _xact.getXactSubtypeId() == 0;
   }
   
   /**
    * This method flags the transaction, _xact, as finalized by setting the transaction sub type property to XactConst.XACT_TYPE_FINAL
    * 
    * @param _xact Transaction object that is to finalized.
    * @throws XactException If transactio id or transaction type id are invalid, or when a database error occurs.
    */
   public void finalizeXact(Xact _xact)  throws XactException {
       DaoApi dao = DataSourceFactory.createDao(this.connector);
	   
	   if (_xact.getId() <= 0) {
		   throw new XactException("The finalization of transaction failed due to invalid transaction id");
	   }
	   if (_xact.getXactTypeId() <= 0) {
		   throw new XactException("The finalization of transaction failed due to invalid transaction type id");
	   }
	   
	   if (_xact.getTenderId() == 0) {
           _xact.setNull("tenderId");
       }
	   _xact.setXactSubtypeId(XactConst.XACT_TYPE_FINAL);
	   try {
	       _xact.addCriteria("Id", _xact.getId());
		   dao.updateRow(_xact);   
	   }
  	   catch (DatabaseException e) {
  		 throw new XactException("The finalization of transaction failed due to a database error:  " + e.getMessage());
  	   }
   }

   
}
