package com.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.util.SystemException;
import com.util.GLAcctException;

import com.bean.GlAccounts;
import com.bean.GlAccountCategory;
import com.bean.GlAccountTypes;

/**
 * 
 * @author RTerrell
 *
 */
public interface GLBasicApi extends BaseDataSource {
    GlAccounts createAccount() throws SystemException, GLAcctException;
    GlAccounts createAccount(HttpServletRequest req) throws SystemException, GLAcctException;
    GlAccounts findById(int arg0) throws GLAcctException;
    List findByAcctNo(String arg0) throws GLAcctException;
    List findByAcctName(String arg0) throws GLAcctException;
    GlAccounts  findByAcctNameExact(String value) throws GLAcctException;
    List findByAcctType(int arg0) throws GLAcctException;
    List findByAcctCatg(int arg0) throws GLAcctException;
    List findByAcctCatgName(String arg0) throws GLAcctException;
    List findGlAcct(String _criteria) throws GLAcctException;
    List findCombinedGlAcctTypeCatg(int _acctTypeid, int _acctCatgId) throws GLAcctException;

     //   GL Account Code table lookups
    GlAccountTypes findAcctTypeById(int value) throws GLAcctException;
    List findAcctType(String criteria) throws GLAcctException;
    GlAccountCategory findAcctCatgById(int value) throws GLAcctException;
    List findAcctCatgByName(String value) throws GLAcctException;
    List findAcctCatgByAcctType(int value) throws GLAcctException;
    GlAccountCategory findAcctCatgByNameExact(String value) throws GLAcctException;

    int maintainAccount(GlAccounts _base) throws GLAcctException;
    int deleteAccount(int acctId) throws GLAcctException;
    boolean isAccountInUse(int acctId) throws GLAcctException;
    int maintainAcctCatg(GlAccountCategory _base) throws GLAcctException;
}
