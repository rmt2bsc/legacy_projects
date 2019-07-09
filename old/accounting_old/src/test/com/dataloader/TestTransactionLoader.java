package com.dataloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import com.loader.ExpenseReceiptTransLoader;
import com.loader.TransactLegendLoader;

/**
 * @author RTerrell
 *
 */
public class TestTransactionLoader {
    private static final String docPath = "C:\\MyStuff\\data\\xml\\rmt2\\";
    
    public TestTransactionLoader() {
        return;
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Testing");
        TestTransactionLoader testLoader = new TestTransactionLoader();
        testLoader.testURI();
//        testLoader.testLegendLoader(docPath + "ExpenseLog_Legend.xml");
//        testLoader.testExpenseReceiptTranLoader("RevenueExpenseLog_Consolidated.xml");
    }

    public void testURI() {
    	try {
//			URI uri = new URI("/com/resources/xsl/generic/GenericXactSearch.xsl");
//			File file = new File(uri);
//			InputStream in = file.toURL().openStream();
			InputStream in2 = TestTransactionLoader.class.getClass().getResourceAsStream("/com/resources/xsl/generic/GenericXactSearch.xsl");
			int i = in2.available();
			System.out.println("Total bytes: " + i);
			File file = new File("c:/2MyStuff/source/accounting/src/java/com/resources/xsl/generic/GenericXactSearch.xsl");
			InputStream in = file.toURL().openStream();
			URL url = getClass().getResource("/com/resources/xsl/generic/GenericXactSearch.xsl");

			//			InputStream in = url.openStream();
			i = in.available();
			System.out.println("Total bytes: " + i);
		}
//		catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    }
    public Map testLegendLoader(String doc) {
        TransactLegendLoader legendTest = new TransactLegendLoader(doc); 
        legendTest.parseDocument();
        Map map = legendTest.getLegend();
        System.out.println("Size of legend: " + map.size());
        return map;
    }
    
    public void testExpenseReceiptTranLoader(String doc) {
        ExpenseReceiptTransLoader loader = new ExpenseReceiptTransLoader(null, null, doc);
        loader.parseDocument();
    }
}
