package com.xact.sales;

import java.util.List;

import com.api.BaseDataSource;

import com.bean.Customer;
import com.bean.SalesOrder;
import com.bean.SalesOrderStatusHist;
import com.bean.Xact;

/**
 * The interface that creates, maintains, and tracks sales order information.
 * 
 * @author Roy Terrell
 *
 */
public interface SalesOrderApi extends BaseDataSource {

    /**
     * Retrieves sales order data using customer criteria.
     * 
     * @param criteria custom selection criteria.
     * @return arbitrary objects.
     * @throws SalesOrderException
     */
    Object findSalesOrder(String criteria) throws SalesOrderException;

    /**
     * Retrieves base sales order data from the database using sales order id
     * 
     * @param soId Id of the sales order
     * @return An arbitrary object
     * @throws SalesOrderException
     */
    Object findSalesOrderById(int soId) throws SalesOrderException;

    /**
     * Retrieves the items of a sales order using sales order id
     * 
     * @param soId
     * @return arbitrary objects
     * @throws SalesOrderException
     * 
     */
    Object findSalesOrderItemsBySalesOrder(int soId) throws SalesOrderException;

    /**
     * Retrieves all sales order related to a given customer.
     * 
     * @param _custId  The id of the customer
     * @return arbitrary objects
     * @throws SalesOrderException
     */
    Object findSalesOrderByCustomer(int _custId) throws SalesOrderException;

    /**
     * Retrieves an list of invoiced sales orders based on customer id and invoiced flag.
     * 
     * @param _custId Id of the customer
     * @param _invoicedFalg Value indicating that the sales order is invoiced.
     * @return arbitrary objects
     * @throws SalesOrderException
     */
    Object findInvoicedSalesOrderByCustomer(int _customerId) throws SalesOrderException;

    /**
     * Retrieves all items related to a sales order.
     * 
     * @param _soId The id of the sales order.
     * @return arbitrary objects.
     * @throws SalesOrderException
     */
    Object findSalesOrderItems(int _soId) throws SalesOrderException;

    /**
     * Retrieves a list of extending sales orders related to a given customer.
     * 
     * @param _customerId
     * @return arbitrary objects
     * @throws SalesOrderException
     */
    Object findExtendedSalesOrderByCustomer(int _customerId) throws SalesOrderException;

    /**
     * Retrieves a list of extending invoiced sales orders related to a given customer.
     * 
     * @param customerId
     * @return arbitrary objects
     * @throws SalesOrderException
     */
    Object findExtendedInvoicedSalesOrderByCustomer(int customerId) throws SalesOrderException;

    /**
     * Retrieves a single sales invoice related to a given sales order.
     * 
     * @param _salesOrderId The id of the sales order to obtain the invoice.
     * @return An arbitrary object.
     * @throws SalesOrderException
     */
    Object findSalesOrderInvoice(int _salesOrderId) throws SalesOrderException;

    /**
     * Retrieves the details of a sales order status by using its primary key identifier.
     * 
     * @param_salesOrderStatusId_id The id of the sales order status.
     * @return An arbitrary object
     * @throws SalesOrderException
     */
    Object findSalesOrderStatus(int _salesOrderStatusId) throws SalesOrderException;

    /**
     * Retrieves the status history of sales order.
     *  
     * @param _id The id of the sales order
     * @return arbitrary objects
     * @throws SalesOrderException
     */
    Object findSalesOrderStatusHistBySOId(int _soId) throws SalesOrderException;

    /**
     * Retrieves the current status of a sales order.
     * 
     * @param _soId The id of the sales order to retreive status.
     * @return Arbitary object
     * @throws SalesOrderException
     */
    Object findCurrentSalesOrderStatusHist(int _soId) throws SalesOrderException;

    /**
     * Retrieves the sales order status history using custom criteria.
     * 
     * @param criteria The custom criteria
     * @return arbitrary object.
     * @throws SalesOrderException
     */
    Object findSalesOrderStatusHist(String criteria) throws SalesOrderException;

    /**
     * Drives the process of creating a new or modifying an existing Sales Order.     The Customer Id must contain a 
     * value > zero, and there must be at least on item to process if we are creating a Sales Order.  _items is an 
     * ArrayList of SalesOrder items that must created prior to invoking this method.
     * 
     * @param _so
     * @param _cust
     * @param _items
     * @return Sales order id.
     * @throws SalesOrderException
     */
    int maintainSalesOrder(SalesOrder _so, Customer _cust, List _items) throws SalesOrderException;

    /**
     * Cancels a sales order.  The sales order must be in "invoiced" status.
     * 
     * @param _salesOrderId The id of the sales order that will be cancelled.
     * @return The id of the cancellation transaction.
     * @throws SalesOrderException
     */
    int cancelSalesOrder(int _salesOrderId) throws SalesOrderException;

    /**
     * Delete a sales order including its items.
     * 
     * @param _salesOrder  Sales Order object to delete.
     * @throws SalesOrderException
     */
    void deleteSalesOrder(SalesOrder _salesOrder) throws SalesOrderException;

    /**
     * Refunds a sales order.   The sales order must be in "closed" status.
     * 
     * @param _salesOrderId The id of the sales order to be refunded
     * @return The id of the refund transacation.
     * @throws SalesOrderException
     */
    int refundSalesOrder(int _salesOrderId) throws SalesOrderException;

    /**
     * Invoices a sales order using _so and _xactId.   When invoicing a sales order, a 
     * positive transaction amount will be posted in the transaction table and the 
     * customer activity table.
     * 
     * @param _so Sales Order object
     * @param _xact New transaction object
     * @return The invoice id.
     * @throws SalesOrderException
     */
    int invoiceSalesOrder(SalesOrder _so, Xact _xact) throws SalesOrderException;

    /**
     * Retrieves the total dollar amount of a given sales order.
     * 
     * @param _orderId The sales order id.
     * @return Amount of the order.
     * @throws SalesOrderException
     */
    double getSalesOrderTotal(int _orderId) throws SalesOrderException;

    /**
     * Changes the status of a sales order.
     * 
     * @param soId  
     *          The id of sales order
     * @param newStatusId 
     *          The id of the status to apply to the sales order
     * @return {@link SalesOrderStatusHist} object of the status before change
     * @throws SalesOrderException 
     *          When newStatusId is considered out of sequence, a database error 
     * 			occurrence, or a System failure.
     */
    SalesOrderStatusHist changeSalesOrderStatus(int soId, int newStatusId) throws SalesOrderException;

}
