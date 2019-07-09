package com.gl.customer;

import com.gl.customer.CustomerException;

import com.bean.Customer;
import com.bean.criteria.CustomerCriteria;

/**
 * An interface that provides the method prototypes required to be implemented 
 * in order to fetch, persist, and validate customer objects.
 * 
 * @author RTerrell
 *
 */
public interface CustomerApi {
    /** Represents customer data */
    static final String CLIENT_DATA_CUSTOMER = "customer";

    /** Represents the customer's balance */
    static final String CLIENT_DATA_CUSTOMER_BAL = "customerbalance";

    void close();

    /**
     * Fetch customer object using its id.
     * 
     * @param id customer's internal unique id
     * @return An arbitrary object.
     * @throws CustomerException
     */
    Object findById(int id) throws CustomerException;

    /**
     * Fetch one or more customers based on a business contact.
     * 
     * @param businessId The internal unique id of the business.
     * @return A List of abitrary objects.
     * @throws CustomerException
     */
    Object findByBusinessId(int businessId) throws CustomerException;

    /**
     * Fetch the business contact information related to a particular customer.
     * 
     * @param customerId the customer's internal unique id.
     * @return An arbitrary object.
     * @throws CustomerException
     */
    Object findCustomerBusiness(int customerId) throws CustomerException;

    /**
     * Fetch the customer/business contact information using custom business 
     * contact selection criteria.
     * 
     * @param criteria selection criteria suited for a particular kind of query.
     * @return A List of abitrary objects.
     * @throws CustomerException
     * @deprecated Use {@link com.gl.customer.CustomerApi#findCustomerBusiness(CustomerCriteria) findCustomerBusiness(CustomerCriteria)}
     */
    Object findCustomerBusiness(String criteria) throws CustomerException;
    
    /**
     * Fetch the customer/business contact information using customer criteria instance 
     * as the source of selection criteria.
     * 
     * @param criteria
     * @return
     * @throws CustomerException
     */
    Object findCustomerBusiness(CustomerCriteria criteria) throws CustomerException;

    /**
     * Fetch Customer using custom selection criteria.
     * 
     * @param criteria
     *          The selection criteria that is to be applied to the query of the 
     *          implementation. 
     * @return An abitrary object.
     * @throws CustomerException
     */
    Object findCustomer(String criteria) throws CustomerException;

    /**
     * Persist customer data changes by either adding a new or updating an 
     * existing customer to an external data provider.
     * 
     * @param custBase
     *          The base customer object
     * @param custExt
     *           An object that serves as an extension to the base customer object.
     * @return int
     * @throws CustomerException
     */
    int maintainCustomer(Customer custBase, Object custExt) throws CustomerException;

    /**
     * Delete a customer record from the system.
     * 
     * @param custId internal unique id of the customer.
     * @return int
     * @throws CustomerException
     */
    int deleteCustomer(int custId) throws CustomerException;

    /**
     * Validate customer object.
     * 
     * @param cust The base customer object
     * @throws CustomerException
     */
    void validateCustomer(Customer cust) throws CustomerException;

    /**
     * Validate customer extension object.
     * 
     * @param custExt
     *          An object that serves as an extension to the base customer object.
     * @throws CustomerException
     */
    void validateCustomerExt(Object custExt) throws CustomerException;

    /**
     * Obtain the customer's balance.
     * 
     * @param id customer's internal unique id
     * @return double as the customer's balance.
     * @throws CustomerException
     */
    double findCustomerBalance(int id) throws CustomerException;
}
