package com.gl;

import java.util.List;

import com.gl.GLException;

import com.bean.GlAccounts;
import com.bean.GlAccountCategory;
import com.bean.GlAccountTypes;

import com.api.BaseDataSource;

/**
 * Interface method specifications for querying and managing general ledger accounts, 
 * account types, and account categories.
 * 
 * @author RTerrell
 *
 */
public interface BasicGLApi extends BaseDataSource {
    static final String ACCTNAME_ACCTRCV = "Accounts Receivable";

    static final String ACCTNAME_ACCTPAY = "Accounts Payable";

    static final String CLIENT_BUSSERVTYPES = "businessservicetypes";

    static final String CLIENT_BUSTYPES = "businesstypes";

    static final String CLIENT_CREDITORBAL = "creditorbalance";

    static final String CLIENT_CUSTOMERBAL = "customerbalance";

    static final int ACCTTYPE_ASSET = 1;

    static final int ACCTTYPE_LIABILITY = 2;

    static final int ACCTTYPE_OWNEREQUITY = 3;

    static final int ACCTTYPE_REVENUE = 4;

    static final int ACCTTYPE_EXPENSE = 5;

    /**
     * Find an account using a unique id.
     * 
     * @param id The unique id that identifies an account.
     * @return {@link com.bean.GlAccounts GlAccounts}
     * @throws GLException
     */
    GlAccounts findById(int id) throws GLException;

    /**
     * Find an account using an account number.
     * 
     * @param acctNo The account number.
     * @return A List containing arbitrary instances of account objects.
     * @throws GLException
     */
    List findByAcctNo(String acctNo) throws GLException;

    /**
     * Find an account by its code.
     * 
     * @param code The account code.
     * @return An arbitrary object representing an account.
     * @throws GLException
     */
    Object findByCode(String code) throws GLException;

    /**
     * Find an one or more accounts which its name begins with <i>name</i>.
     * 
     * @param acctName The account name
     * @return A List containing arbitrary instances of account objects.
     * @throws GLException
     */
    List findByAcctName(String acctName) throws GLException;

    /**
     * Find an account by which the account's name equates to <i>name</i>.
     * 
     * @param acctName The account name
     * @return {@link com.bean.GlAccounts GlAccounts}
     * @throws GLException
     */
    GlAccounts findByAcctNameExact(String acctName) throws GLException;

    /**
     * Find one or more accounts using an account id.
     * 
     * @param acctTypeId The id of the account type.
     * @return A List containing arbitrary instances of account objects.
     * @throws GLException
     */
    List findByAcctType(int acctTypeId) throws GLException;

    /**
     * Find one or more accounts using an category id.
     * 
     * @param acctCatgId The id of the category.
     * @return A List containing arbitrary instances of account objects.
     * @throws GLException
     */
    List findByAcctCatg(int acctCatgId) throws GLException;

    /**
     * Find one or more accounts by category name.
     * 
     * @param catgName The name of the category.
     * @return A List containing arbitrary instances of account objects.
     * @throws GLException
     */
    List findByAcctCatgName(String catgName) throws GLException;

    /**
     * Find one or more accounts using custom selection criteria.
     * 
     * @param criteria The selection criteria as a String.
     * @return A List containing arbitrary instances of account objects.
     * @throws GLException
     */
    List findGlAcct(String criteria) throws GLException;

    /**
     * Find an account of extended format by its unique id.
     * 
     * @param id The unique id that identifies an account.
     * @return An arbitrary object representing an account.
     * @throws GLException
     */
    Object findByIdExt(int id) throws GLException;

    /**
     * Find one or more accounts of extended format by its account number.
     * 
     * @param acctNo The unique account number that identifies an account
     * @return List of arbitrary objects representing accounts.
     * @throws GLException
     */
    List findByAcctNoExt(String acctNo) throws GLException;

    /**
     * Find one or more accounts of extended format which the account name 
     * begins with <i>acctName</i>.
     * 
     * @param acctName The name of the account. 
     * @return List of arbitrary objects representing accounts.
     * @throws GLException
     */
    List findByAcctNameExt(String acctName) throws GLException;

    /**
     * Find one or more accounts of extended format by account type.
     *  
     * @param acctTypeId The id of the account type.
     * @return List of arbitrary objects representing accounts.
     * @throws GLException
     */
    List findByAcctTypeExt(int acctTypeId) throws GLException;

    /**
     * Find one or more accounts of extended format by account category.
     * 
     * @param acctCatgId The id of the category.
     * @return List of arbitrary objects representing accounts.
     * @throws GLException
     */
    List findByAcctCatgExt(int acctCatgId) throws GLException;

    /**
     * Find one or more accounts of extended format by account type and category.
     * 
     * @param acctTypeid The id of the account type.
     * @param acctCatgId The id of the category.
     * @return List of arbitrary objects representing accounts.
     * @throws GLException
     */
    List findCombinedGlAcctTypeCatg(int acctTypeid, int acctCatgId) throws GLException;

    /**
     * Find a GlAccountTypes object by it's unique id.
     * 
     * @param acctTypeId The id of the account type.
     * @return {@link com.bean.GlAccountTypes GlAccountTypes}
     * @throws GLException
     */
    GlAccountTypes findAcctTypeById(int acctTypeId) throws GLException;

    /**
     * Find one or more account types based on custom selection criteria.
     * 
     * @param criteria A String of selection criteria.
     * @return List of arbitrary objects representing account types.
     * @throws GLException
     */
    List findAcctType(String criteria) throws GLException;

    /**
     * Find a GlAccountCategory object by it's unique id.
     * 
     * @param acctCatgId The unique id of the category.
     * @return {@link com.bean.GlAccountCategory GlAccountCategory}
     * @throws GLException
     */
    GlAccountCategory findAcctCatgById(int acctCatgId) throws GLException;

    /**
     * Find an account category instance of extended format based on its 
     * unique identifier.
     * 
     * @param acctCatgId The unique id of the category.
     * @return An arbitrary object representing an account category in extended format.
     * @throws GLException
     */
    Object findAcctCatgByIdExt(int acctCatgId) throws GLException;

    /**
     * Find an account category instance of extended format based on its 
     * name beginning with <i>catgName</i>.
     * 
     * @param catgName The name of the category.
     * @return List of arbitrary objects representing account categories.
     * @throws GLException
     */
    List findAcctCatgByName(String catgName) throws GLException;

    /**
     * Find an account category instance of extended format based on an account type.
     * 
     * @param acctTypeId The id of the account type.
     * @return List of arbitrary objects representing account types
     * @throws GLException
     */
    List findAcctCatgByAcctType(int acctTypeId) throws GLException;

    /**
     * Find an account category instance of extended format based on its 
     * name equates exactly to <i>catgName</i>.
     * 
     * @param catgName The name of the account.
     * @return An arbitrary object representing an account category.
     * @throws GLException
     */
    GlAccountCategory findAcctCatgByNameExact(String catgName) throws GLException;

    /**
     * Adds a new or updates an existing account and persist the changes 
     * to some external data source.
     * 
     * @param obj The{@link com.bean.GlAccounts GlAccounts} to manage.
     * @return int
     * @throws GLException
     */
    int maintainAccount(GlAccounts obj) throws GLException;

    /**
     * Deletes an account from some external data source.
     * 
     * @param acctId The id of the account.
     * @return int
     * @throws GLException
     */
    int deleteAccount(int acctId) throws GLException;

    /**
     * Determines if the account is in use.
     * 
     * @param acctId The id of the account.
     * @return true when in use.  Otherwise, false is returned.
     * @throws GLException
     */
    boolean isAccountInUse(int acctId) throws GLException;

    /**
     * Adds a new or updates an existing account category and persist the changes 
     * to some external data source.
     * 
     * @param obj The{@link com.bean.GlAccountCategory GlAccountCategory} to manage.
     * @return int
     * @throws GLException
     */
    int maintainAcctCatg(GlAccountCategory obj) throws GLException;

    /**
     * Deletes an account category from some external data source.
     * 
     * @param catgId The id of the account category.
     * @return int
     * @throws GLException
     */
    int deleteAcctCatg(int catgId) throws GLException;
}
