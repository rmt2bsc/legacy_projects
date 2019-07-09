/**
 * 
 */
package com.general;


import java.text.DecimalFormat;
import java.text.ParsePosition;

import org.junit.After;
import org.junit.Before;

/**
 * @author appdev
 *
 */
public class NumericTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }
    
    public void testStringToDecimal() {
	String source = "23,323.39";
	DecimalFormat format = new DecimalFormat();
	ParsePosition pos = new ParsePosition(0);
	//		format.setParseBigDecimal(true);
	Number num = format.parse(source, pos);
	if (num != null) {
	    System.out.println("Results: " + num.doubleValue());
	    System.out.println("String Length: " + source.length());
	    System.out.println("Last Pos Parsed: " + pos.getIndex());
	    System.out.println("Error Pos: " + pos.getErrorIndex());
	}

	source = "23323D39";
	pos = new ParsePosition(0);
	num = format.parse(source, pos);
	if (num != null) {
	    System.out.println("Results: " + num.doubleValue());
	    System.out.println("String Length: " + source.length());
	    System.out.println("Last Pos Parsed: " + pos.getIndex());
	    System.out.println("Error Pos: " + pos.getErrorIndex());
	}

	source = "23,323.....39";
	pos = new ParsePosition(0);
	num = format.parse(source, pos);
	if (num != null) {
	    System.out.println("Results: " + num.doubleValue());
	    System.out.println("String Length: " + source.length());
	    System.out.println("Last Pos Parsed: " + pos.getIndex());
	    System.out.println("Error Pos: " + pos.getErrorIndex());
	}

	source = "$323.00";
	pos = new ParsePosition(0);
	num = format.parse(source, pos);
	if (num != null) {
	    System.out.println("Results: " + num.doubleValue());
	    System.out.println("String Length: " + source.length());
	    System.out.println("Last Pos Parsed: " + pos.getIndex());
	    System.out.println("Error Pos: " + pos.getErrorIndex());
	}

	source = "S323.00";
	pos = new ParsePosition(0);
	num = format.parse(source, pos);
	if (num != null) {
	    System.out.println("Results: " + num.doubleValue());
	    System.out.println("String Length: " + source.length());
	    System.out.println("Last Pos Parsed: " + pos.getIndex());
	    System.out.println("Error Pos: " + pos.getErrorIndex());
	}
    }

}
