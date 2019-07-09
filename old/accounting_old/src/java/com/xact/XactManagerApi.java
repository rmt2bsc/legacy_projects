package com.xact;

import java.util.List;

import com.api.BaseDataSource;

import com.bean.Xact;
import com.bean.XactCategory;
import com.bean.XactCodes;
import com.bean.XactCodeGroup;
import com.bean.XactType;
import com.bean.VwXactList;

import com.xact.XactException;

/**
 * The base transaction interface that erquiers the implementation of 
 * various transaction access methods to locate transaction base, category, 
 * type, type codes, and type item activity related data.   Also provides 
 * various methods to create and manage transactions, and to maintain 
 * historical occurrences.
 * 
 * @author Roy Terrell
 *
 */
public interface XactManagerApi extends BaseDataSource {

    /**
     * Retrieves transaction data using _xactId
     * 
     * @param xactId
     * @return {@link Xact}
     * @throws XactException
     */
    Xact findXactById(int xactId) throws XactException;

    /**
     * Retrieves customer transaction history using custId.  The transaction list is sorted in descending 
     * order by transaction id.
     * 
     * @param custId The id of the target customer.
     * @return ArrayList of {@link VwCustomerXactHist} objects.
     * @throws XactException
     */
    List findCustomerXactHist(int custId) throws XactException;

    /**
     * Retrieves a list of trasactions based on the id of a particular creditor.   The transaction list is sorted in descending 
     * order by transaction id.
     * 
     * @param credId
     * @return ArrayList of (@link VwCreditorXactHistView} objects.
     * @throws XactException
     */
    List findCreditorXactHist(int credId) throws XactException;

    /**
     * Retrieves a list of transacstion category data based on custom criteria supplied by the user.
     * 
     * @param criteria The selection criteria to limit result set
     * @return An ArrayList of {@link XactCategoryView} objects
     * @throws XactException
     */
    List findXactCatg(String criteria) throws XactException;

    /**
     * Retrieves a transaction category object using category id.
     * 
     * @param catgId The id of the transacton category to retrieve.
     * @return {@link XactCategoryView} object
     * @throws XactException
     */
    XactCategory findXactCatgById(int catgId) throws XactException;

    /**
     * Retrieves a list of transaction code group data based custom criteria supplied by the user.  User is responsible for 
     * setting the base view and class so that the API will know what data to retrieve.
     * 
     * @param criteria The selection criteria to applied to the query of the data source.
     * @return ArrayList of unknown objects.  The implementer of this method is responsible for applying the proper casting of 
     * each element contained in the returned results.
     * @throws XactException
     */
    List findXactCodeGroup(String criteria) throws XactException;

    /**
     * Retrieves a transaction code group record using its primary key value.
     * @param id The primary key valur of the code group to retrieve.
     * @return {@link XactCodeGroup} object
     * @throws XactException
     */
    XactCodeGroup findXactCodeGroupById(int id) throws XactException;

    /**
     * Retrieves a list of transaction code data based custom criteria supplied by the user.  User is responsible for 
     * setting the base view and class so that the API will know what data to retrieve.
     * 
     * @param _criteria The selection criteria to applied to the query of the data source.
     * @return ArrayList of unknown objects.  The implementer of this method is responsible for applying the proper casting of 
     * each element contained in the returned results.
     * @throws XactException
     */
    List findXactCode(String _criteria) throws XactException;

    /**
     * Retrieves a specific transaction code object using the primary key value.
     * 
     * @param id The primary key value of the transaction code record.
     * @return {@link XactCodes} object.
     * @throws XactException
     */
    XactCodes findXactCodeById(int id) throws XactException;

    /**
     * Retrieves one or more transaction code objects using a transaction code group.
     * 
     * @param gropuId The id of the transaction code group.
     * @return ArrayList of {@link XactCodes}
     * @throws XactException
     */
    List findXactCodeByGroupId(int gropuId) throws XactException;

    /**
     * Retrieves an ArrayList of transaction type objects based custom criteria supplied by the user.
     * 
     * @param criteria The selection criteria to apply to the query of the data source.
     * @return ArrayList of {@link XactType}
     * @throws XactException
     */
    List findXactType(String criteria) throws XactException;

    /**
     * Retrieves a specific transaction type object using its primary key.
     * 
     * @param id The id of the transaction object to retrieve.
     * @return {@link XactType} object.
     * @throws XactException
     */
    XactType findXactTypeById(int id) throws XactException;

    /**
     * Retrieves one or more transaction type objects using a transaction category id.
     * 
     * @param catgId The id of the transaction category id.
     * @return ArrayList of {@link XactType} objects.
     * @throws XactException
     */
    List findXactTypeByCatgId(int catgId) throws XactException;

    /**
     * Retrieves one or more transaction type item activity objects related to xactId.
     * 
     * @param xactId The id of the transction.
     * @return ArrayList of one or more {@link XactTypeItemActivity} obejcts. 
     * @throws XactException
     */
    List findXactTypeItemsActivityByXactId(int xactId) throws XactException;

    /**
     * Retrieves one or more transaction type item activity view records related to transaction id.
     * 
     * @param xactId The id of the transction.
     * @return ArrayList of one or more {@link VwXactTypeItemActivity} obejcts.
     * @throws XactException
     */
    List findVwXactTypeItemActivityByXactId(int xactId) throws XactException;

    /**
     * Retrieves one or more transaction type items related to a transaction type id.
     * 
     * @param xactTypeId The id of the transaction type.
     * @return ArrayList of {@link XactTypeItem}
     * @throws XactException
     */
    List findXactTypeItemsByXactTypeId(int xactTypeId) throws XactException;

    /**
     * Retrieves one or more  transactions list view records related to a transcation id
     * 
     * @param _xactId The transaction id.
     * @return {@link VwXactList}
     * @throws XactException
     */
    VwXactList findXactListViewByXactId(int _xactId) throws XactException;

    /**
     * Retrieves one or more  transactions list view records related to a transcation type id
     * 
     * @param xactTypeId The id of the transaction type.
     * @return An ArrayList of {@link VwXactList}
     * @throws XactException
     */
    List findXactListViewByXactTypeId(int xactTypeId) throws XactException;

    /**
     * Gets transaction item collection.
     * 
     * @return ArrayList of {@link XactTypeItemActivity} objects
     */
    List getXactItems();

    /**
     * Sets the collection of transaction items.
     * 
     * @param value ArrayList of {@link XactTypeItemActivity} objects
     */
    void setXactItems(List value);

    /**
     * Gets transaction bean object.
     * 
     * @return {@link Xact} object.
     */
    Xact getXactBean();

    /**
     * Sets the transaction bean object.
     * 
     * @param value the transcation object.
     */
    void setXactBean(Xact value);

    /**
     * Driver for creating and applying a transaction to the database.
     * 
     * @param _xact The transaction object to be managed.
     * @param _xactItems A list of transaction Type Item Activity objects
     * @return The id of the newly formed transction. 
     * @throws XactException
     */
    int maintainXact(Xact _xact, List _xactItems) throws XactException;

    /**
     * Reverses the base transaction by multiplying a -1 to the  the transaction amount and providing a generic reason.   
     * As a result of this operation, the internal transaction amount is permanently changed by offsetting the previous transaction.
     * 
     * @param _xact The transaction that is being reversed.
     * @param __xactItems Transaction items to be reversed.
     * @return New id of the reversed transaction.
     * @throws XactException
     */
    int reverseXact(Xact _xact, List _xactItems) throws XactException;

    /**
     * Creates Customer activity (subsidiary) entry based on related transaction (_xactId).
     *  
     * @param _customerId The id of the customer
     * @param _xactId The id of the transaction
     * @param _amount The transaction amount
     * @return String - Confrimation number.
     * @throws XactException
     */
    String createCustomerActivity(int _customerId, int _xactId, double _amount) throws XactException;

    /**
     * Creates Creditor activity (subsidiary) entry based on related transaction (_xactId).
     * 
     * @param _creditorId The id of the creditor
     * @param _xactId The id of the transaction
     * @param _amount The transaction amount
     * @return The confirmation number.
     * @throws XactException
     */
    String createCreditorActivity(int _creditorId, int _xactId, double _amount) throws XactException;

    /**
     * Determines if _xact can modified or adjusted which will generally require a new transaction to be created.  Typical target 
     * transactions would be reversals, cancellations, and returns
     * 
     * @param _xact The transaction that is to be managed
     * @return true indicating it is eligible to be changed, and false indicating change is not allowd.
     * @throws XactException when _xact is invalid or null.
     */
    boolean isXactModifiable(Xact _xact) throws XactException;

    /**
     * This method flags the transaction, _xact, as finalized  by setting the transaction sub type property to XactConst.XACT_TYPE_FINAL
     * 
     * @param _xact Transaction object that is to finalized.
     * @throws XactException If transactio id or transaction type id are invalid, or when a database error occurs.
     */
    void finalizeXact(Xact _xact) throws XactException;

    /**
     * This method drives the process of setting up and applying database updates of new transactions. 
     * 
     * @return  the ID of the transaction created during this process
     * @throws XactException If a validation error occurs.
     * @deprecated use maintainXact(Xact, ArrayList) to manage a transaction.
     */
    //    int createBaseXact() throws XactException;
    /**
     * Drives the process of creating transaction type item activity entries by cycling
     *  through all elements of the transaction type item array.   
     *  
     * @return ArrayList of valid {@link XactTypeItemActivity}objects
     * @throws XactException If a validation error occurs.
     * @deprecated use maintainXact(Xact, ArrayList) to manage a transaction.
     */
    //    List createBaseXactItems() throws XactException;
}
