package com.gl.creditor;

import java.util.Comparator;

/**
 * Provides functionality to compare the either the Name, Id, or AccountNo properties of two
 * {@link com.bean.CustomerExt CustomerExt} instances.
 *
 *
 * @author RTerrell
 *
 */
public class CreditorExtComparator implements Comparator {
    public static final String SORT_NAME = "Name";

    public static final String SORT_ID = "Id";

    public static final String SORT_ACCTNO = "AccountNo";

    private String sortPropName;

    /**
     * Constructs a CreditorExtComparator object which the default sort property is "Name".
     */
    public CreditorExtComparator() {
	this.sortPropName = CreditorExtComparator.SORT_NAME;
	return;
    }

    /**
     * Constructs a CreditorExtComparator object by assigning the sort property.
     * 
     * @param propName The name of the column to order by.
     */
    public CreditorExtComparator(String propName) {
	this.sortPropName = propName;
	return;
    }

    /**
     * Compares two CreditorExt objects based on a specific property.
     *
     * @param obj1 The target CreditorExt object.
     * @param obj2 The CreditorExt object to compare.
     * @return 0 if the strings are equal or if obj1 and/or obj2 are not instances
     *         of CreditorExt, a negative integer if the target String is less
     *         than the specified String, or a positive integer if the target String
     *         is greater than the specified String
     */
    public int compare(Object obj1, Object obj2) {
	CreditorExt credExt1;
	CreditorExt credExt2;
	if (obj1 instanceof CreditorExt && obj2 instanceof CreditorExt) {
	    credExt1 = (CreditorExt) obj1;
	    credExt2 = (CreditorExt) obj2;
	}
	else {
	    return 0;
	}

	return this.sort(credExt1, credExt2);
    }

    /**
     * Orders <i>cred1</i> and <i>cred2</i> based on the column set forth as either "Name", 
     * "Id", or "AccountNo".
     *  
     * @param cred1 The first customer object
     * @param cred2 The second customer object.
     * @return a negative, zero, or a positive integer as <i>cust1</i> argument is less 
     *         than, equal to, or greater than <i>cust2</i>, respectively.
     */
    private int sort(CreditorExt cred1, CreditorExt cred2) {
	if (this.sortPropName.equals(CreditorExtComparator.SORT_NAME)) {
	    return cred1.getName().toLowerCase().compareTo(cred2.getName().toLowerCase());
	}
	else if (this.sortPropName.equals(CreditorExtComparator.SORT_ID)) {
	    Integer value1 = new Integer(cred1.getCreditorId());
	    Integer value2 = new Integer(cred2.getCreditorId());
	    return value1.compareTo(value2);
	}
	else if (this.sortPropName.equals(CreditorExtComparator.SORT_ACCTNO)) {
	    return cred1.getAccountNo().toLowerCase().compareTo(cred2.getAccountNo().toLowerCase());
	}
	return 0;
    }
}
