package com.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.util.SystemException;
import com.util.GLAcctException;
import com.util.CreditorException;
import com.util.CustomerException;

import com.api.db.DatabaseException;
import com.bean.GlAccounts;
import com.bean.GlAccountCategory;
import com.bean.GlAccountTypes;
import com.bean.ItemMaster;
import com.bean.Customer;
import com.bean.Creditor;
import com.bean.db.DatabaseConnectionBean;

/**
 * 
 * @author RTerrell
 * @deprecated 
 *         Will be removed for future versions.  Use GLBasicApi, 
 *         GLCreditorApi, and GLCustomerApi.
 */
public interface AcctManagerApi extends BaseDataSource {

   abstract GlAccounts createAccount() throws SystemException, GLAcctException;
   abstract GlAccounts createAccount(HttpServletRequest req) throws SystemException, GLAcctException;
   abstract GlAccounts findById(int arg0) throws GLAcctException;
   abstract List findByAcctNo(String arg0) throws GLAcctException;
   abstract List findByAcctName(String arg0) throws GLAcctException;
   GlAccounts  findByAcctNameExact(String value) throws GLAcctException;
   abstract List findByAcctType(int arg0) throws GLAcctException;
   abstract List findByAcctCatg(int arg0) throws GLAcctException;
   abstract List findByAcctCatgName(String arg0) throws GLAcctException;

   GlAccountTypes findAcctTypeById(int value) throws GLAcctException;

   GlAccountCategory findAcctCatgById(int value) throws GLAcctException;
   List findAcctCatgByName(String value) throws GLAcctException;
   GlAccountCategory findAcctCatgByNameExact(String value) throws GLAcctException;

   Customer  findCustomerById(int _id) throws GLAcctException ;
   List  findCustomerByName(String _value) throws GLAcctException ;
   List  findCustomerByAcctNo(String _value) throws GLAcctException ;
   Creditor  findCreditorById(int _id) throws GLAcctException ;
   List  findCreditorByName(String _value) throws GLAcctException;
   List  findCreditorByAcctNo(String _value) throws GLAcctException;

   abstract DatabaseConnectionBean getDbConnectionObject();
   abstract void release();
   int maintainAccount(GlAccounts _base) throws GLAcctException;
   int deleteAccount(GlAccounts acct) throws DatabaseException, GLAcctException;
   boolean isAccountInUse(int acctId) throws GLAcctException;
   int maintainAcctCatg(GlAccountCategory _base) throws GLAcctException;
   void maintainItemMaster(GlAccounts _base, ItemMaster _itemMaster, Object _itemDetail);
   int maintainCreditor(GlAccounts _glBase, Customer _credBase, Object _credDetail) throws CreditorException;
   void maintainCustomer(GlAccounts _glBase, Customer _custBase, Object _custDetail) throws CustomerException;
   void removeCreditor(int _credId);
   void removeCustomer(int _custId);
}
