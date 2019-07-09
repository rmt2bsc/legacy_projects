package com.loader.transactions;

import java.util.HashMap;
import java.util.Map;

/**
 * Transaction Loader constants.
 * 
 * @author appdev
 *
 */
public class LoaderConst {
    /**
     * General Expense item type hash
     */
    public static Map<String, Integer> EXPITEM_TYPES = new HashMap<String, Integer>();
    static {
	// rent
	EXPITEM_TYPES.put("RT", new Integer(1));
	// Salary
	EXPITEM_TYPES.put("SAL", new Integer(2));
	// Utilities-Electrtic
	EXPITEM_TYPES.put("UE", new Integer(3));
	// Utilities-Gas
	EXPITEM_TYPES.put("UG", new Integer(3));
	// Utilities-Water
	EXPITEM_TYPES.put("UW", new Integer(3));
	// Depreciation
	EXPITEM_TYPES.put("DEP", new Integer(4));

	// Travel
	EXPITEM_TYPES.put("H", new Integer(5));
	// Cab, Taxi
	EXPITEM_TYPES.put("CB", new Integer(5));

	// Food
	EXPITEM_TYPES.put("F", new Integer(6));
	// Airline
	EXPITEM_TYPES.put("A", new Integer(7));
	// medical
	EXPITEM_TYPES.put("MD", new Integer(8));
	// Car Rental
	EXPITEM_TYPES.put("CR", new Integer(9));
	// Laundry
	EXPITEM_TYPES.put("CC", new Integer(10));
	// Entertainment
	EXPITEM_TYPES.put("E", new Integer(11));
	// Gas (Automobile)
	EXPITEM_TYPES.put("G", new Integer(12));
	// Insurance
	EXPITEM_TYPES.put("I", new Integer(13));

	// General Leases
	EXPITEM_TYPES.put("L", new Integer(14));
	// Computer Leases
	EXPITEM_TYPES.put("CE", new Integer(14));
	// Car Leases
	EXPITEM_TYPES.put("CL", new Integer(14));

	// Miscellaneous
	EXPITEM_TYPES.put("M", new Integer(15));
	// Office Equipment
	EXPITEM_TYPES.put("OC", new Integer(16));
	// parking
	EXPITEM_TYPES.put("P", new Integer(17));

	// Supplies
	EXPITEM_TYPES.put("S", new Integer(18));
	// Supplies-Computer
	EXPITEM_TYPES.put("CSP", new Integer(18));

	// Telephone-Cellular
	EXPITEM_TYPES.put("TM", new Integer(19));
	// Telephone-Land
	EXPITEM_TYPES.put("TL", new Integer(20));
	// Trade Magazines, Books
	EXPITEM_TYPES.put("B", new Integer(21));
	// Internet Service
	EXPITEM_TYPES.put("ISP", new Integer(22));
	// Bank Service Charges
	EXPITEM_TYPES.put("BSC", new Integer(23));
	// Toll
	EXPITEM_TYPES.put("TOL", new Integer(24));

	// Tools-Computer Hardware
	EXPITEM_TYPES.put("CH", new Integer(25));
	// Tools-Computer Software	
	EXPITEM_TYPES.put("CS", new Integer(25));
	// Tools-Musical Equipment
	EXPITEM_TYPES.put("ME", new Integer(25));

	// Returns and Allowances-Sales
	EXPITEM_TYPES.put("SRA", new Integer(50));
	// Returns and Allowances-Purchases
	EXPITEM_TYPES.put("PRA", new Integer(50));
	// Returns and Allowances-General Expenses
	EXPITEM_TYPES.put("ERA", new Integer(50));

	// Discount
	EXPITEM_TYPES.put("DIS", new Integer(52));
    }

}