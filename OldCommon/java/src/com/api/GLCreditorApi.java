
package com.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.util.SystemException;
import com.util.GLAcctException;
import com.util.CreditorException;

import com.bean.Creditor;
import com.bean.CreditorType;

public interface GLCreditorApi extends BaseDataSource {
    Creditor createCreditor() throws SystemException;
    Creditor createCreditor(HttpServletRequest req) throws SystemException;

   List  findCreditor(String criteria) throws GLAcctException;
   Creditor  findCreditorById(int _id) throws GLAcctException ;
   List  findCreditorByCreditorType(int _value) throws GLAcctException;
   List  findCreditorByAcctNo(String _value) throws GLAcctException;
   List findCreditorBusiness(String _criteria) throws GLAcctException;

   List  findCreditorType(String _criteria) throws GLAcctException;
   CreditorType  findCreditorTypeById(int _id) throws GLAcctException;

   int maintainCreditor(Creditor _credBase, Object _credDetail) throws CreditorException;
   int deleteCreditor(int _credId) throws CreditorException;
   void validateCreditor(Creditor _base, Object _credExt) throws CreditorException;
   
   double  findCreditorBalance(int _id) throws CreditorException;
}
