
package com.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.util.NotFoundException;
import com.util.SystemException;
import com.util.GLAcctException;
import com.util.CustomerException;

import com.bean.Customer;
import com.bean.CustomerWithName;


public interface GLCustomerApi extends BaseDataSource {
   Customer  findCustomerById(int _id) throws GLAcctException ;
   List  findCustomerByPersonId(int _value) throws GLAcctException ;
   List  findCustomerByBusinessId(int _value) throws GLAcctException ;
   List  findCustomer(String criteria) throws GLAcctException;
   CustomerWithName findCustomerWithName(int custId) throws GLAcctException;
   
   int getHttpCustomerIdentifier() throws SystemException, NotFoundException;

   Customer createCustomer() throws SystemException;
   Customer createCustomer(HttpServletRequest _req) throws SystemException;
   int maintainCustomer(Customer _custBase, Object _custExt) throws CustomerException;
   int deleteCustomer(int _custId) throws CustomerException;
   void validateCustomer(Customer _cust) throws CustomerException;
   
   double  findCustomerBalance(int _id) throws CustomerException;
}
