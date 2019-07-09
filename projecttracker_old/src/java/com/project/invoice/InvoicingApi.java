package com.project.invoice;


/**
 * Interface for invoicing generic business entities.   Provides functionality for 
 * invoicing a single item or invoicing multiple items from a single call.
 * 
 * @author RTerrell
 *
 */
public interface InvoicingApi {

    /**
     * Invoices a single business entitiy.  
     * 
     * @param itemId The unique identifier of the business item to invoice.
     * @return int
     * @throws InvoicingException
     */
    int invoiceSingle(int itemId) throws InvoicingException;
    
    /**
     * Invoices one or more business entities as a single transaction.
     * 
     * @param itemId The unique identifiers of the business items to invoice.
     * @return int
     * @throws InvoicingException
     */
    int invoiceMultiple(int itemId[]) throws InvoicingException;
    
    /**
     * Return the detail data that was invoiced.  This is implementation specific.
     * 
     * @return Object
     */
    Object getProcessedData();
}
