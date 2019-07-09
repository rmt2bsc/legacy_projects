package com.factory;

import com.api.db.orm.DataSourceAdapter;
import com.bean.GlAccounts;
import com.bean.GlAccountTypes;
import com.bean.GlAccountCategory;
import com.bean.Creditor;
import com.bean.Customer;
import com.bean.ItemMaster;

import com.util.SystemException;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author appdev
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * 
 * @deprecated Use AcctManagerFactory
 */
public class GlAccountsFactory extends DataSourceAdapter  {

    /**
     * 
     * @return
     * @throws SystemException
     * @deprecated Use AcctManagerFactory
     */
  public static GlAccounts create()  throws SystemException{
     GlAccounts acct = new GlAccounts();
     return acct;
  }

  
  /**
   * 
   * @param _req
   * @return
   * @throws SystemException
   * @deprecated Use AcctManagerFactory
   */
  public static GlAccounts create(HttpServletRequest _req)   throws SystemException {
     GlAccounts obj = create();
     int rc = packageBean(_req, obj);
     return obj;        
  }

  /**
   * 
   * @return
   * @throws SystemException
   * @deprecated Use AcctManagerFactory
   */
  public static GlAccountTypes createAcctType()  throws SystemException{
     GlAccountTypes acct = new GlAccountTypes();
     return acct;
  }

  /**
   * 
   * @param _req
   * @return
   * @throws SystemException
   * @deprecated Use AcctManagerFactory
   */
  public static GlAccountTypes createAcctType(HttpServletRequest _req)   throws SystemException {
     GlAccountTypes obj = createAcctType();
     int rc = packageBean(_req, obj);
     return obj;      
  }


  /**
   * 
   * @return
   * @throws SystemException
   * @deprecated Use AcctManagerFactory
   */
  public static GlAccountCategory createCatg()  throws SystemException{
     GlAccountCategory acct = new GlAccountCategory();
     return acct;
  }

  /**
   * 
   * @param _req
   * @return
   * @throws SystemException
   * @deprecated Use AcctManagerFactory
   */
  public static GlAccountCategory createCatg(HttpServletRequest _req)   throws SystemException {
     GlAccountCategory obj = createCatg();
     int rc = packageBean(_req, obj);
     return obj;     
  }

  /**
   * 
   * @return
   * @throws SystemException
   * @deprecated Use AcctManagerFactory
   */
  public static Creditor createCreditor()  throws SystemException{
     Creditor acct = new Creditor();
     return acct;
  }

  /**
   * 
   * @param _req
   * @return
   * @throws SystemException
   * @deprecated Use AcctManagerFactory
   */
  public static Creditor createCreditor(HttpServletRequest _req)   throws SystemException {
     Creditor obj = createCreditor();
     int rc = packageBean(_req, obj);
     return obj;     
  }

  /**
   * 
   * @return
   * @throws SystemException
   * @deprecated Use AcctManagerFactory
   */
  public static Customer  createCustomer()  throws SystemException{
     Customer acct = new Customer();
     return acct;
  }

  /**
   * 
   * @param _req
   * @return
   * @throws SystemException
   * @deprecated Use AcctManagerFactory
   */
  public static Customer createCustomer(HttpServletRequest _req)   throws SystemException {
     Customer obj = createCustomer();
     int rc = packageBean(_req, obj);
     return obj;      
  }

  
  /**
   * 
   * @return
   * @throws SystemException
   * @deprecated Use AcctManagerFactory
   */
  public static ItemMaster  createItemMaster()  throws SystemException{
     ItemMaster item = new ItemMaster();
     return item;
  }

  /**
   * 
   * @param _req
   * @return
   * @throws SystemException
   * @deprecated Use AcctManagerFactory
   */
  public static ItemMaster createItemMaster(HttpServletRequest _req)   throws SystemException {
     ItemMaster obj = createItemMaster();
     int rc = packageBean(_req, obj);
     return obj;      
  }

}


