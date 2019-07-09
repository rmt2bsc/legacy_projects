/**
 * 
 */
package com.general;


import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.util.RMT2Utility;

/**
 * @author appdev
 *
 */
public class UtilityTest {

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
    
    
    @Test
    public void testConvertInCluaseCriteriaToHash() {
	Map criteriaMap = null;
	String clause = "xxxx in(123, 321, 282, 292, 999)";
	criteriaMap = RMT2Utility.convertCriteriaToHash(clause);
	String clause2 = "zzzz in ( 123, 321, 282, 292, 999)";
	criteriaMap = RMT2Utility.convertCriteriaToHash(clause2);
	criteriaMap.entrySet();
	return;
    }
}
