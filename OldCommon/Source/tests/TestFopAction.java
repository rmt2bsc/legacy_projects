package com.action;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Transformer;
import javax.xml.transform.Source;
import javax.xml.transform.Result;

import javax.xml.transform.sax.SAXResult;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.apps.FOUserAgent;

import com.util.RMT2XsltUtility;

public class TestFopAction {
	private static String XSL_SOURCE_FILE = "C:/projects/Common/Source/webapp/reports/xsl/employee_timesheet.xsl";
	private static String XML_SOURCE_FILE =  "C:/projects/Common/Source/webapp/reports/xml/EmployeeClient.xml";
	private static String FO_SOURCE_FILE = "C:/projects/Common/Source/webapp/reports/fo/out.fo";
	private static String PDF_SOURCE_FILE = "C:/tmp/pdf_results.pdf";
    
    private HttpServletRequest request;
    private HttpServletResponse response;
    
	
	public TestFopAction(HttpServletRequest _request, HttpServletResponse _response) {
        this.request = _request;
        this.response = _response;
	}
	
	public void init() {
		this.createFODocument();
		this.createPdfDocument();
	}

	public void createFODocument() {
		
		
		try {
            RMT2XsltUtility xsl = RMT2XsltUtility.getInstance();
            xsl.transformXslt(TestFopAction.XSL_SOURCE_FILE, TestFopAction.XML_SOURCE_FILE, TestFopAction.FO_SOURCE_FILE);
/*            
			File xslFile = new File(TestFopAction.XSL_SOURCE_FILE);
			File xmlFile = new File(TestFopAction.XML_SOURCE_FILE);
			File foFile = new File(TestFopAction.FO_SOURCE_FILE);
			
			StreamSource xslStream = new StreamSource(xslFile);
			StreamSource xmlStream = new StreamSource(xmlFile);
			StreamResult foStream = new StreamResult(new FileOutputStream(foFile));
			
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(xslStream); // identity transformer
			transformer.transform(xmlStream, foStream);
*/            
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
	
	
	public void createPdfDocument() {
		// Construct a FopFactory
		// (reuse if you plan to render multiple documents!)
/*        
		FopFactory fopFactory = FopFactory.newInstance();
        FOUserAgent agent = fopFactory.newFOUserAgent();
        String baseUrl = agent.getBaseURL();
        agent.setBaseURL("C:\\Tomcat 5.5\\webapps\\common\\");
        baseUrl = agent.getBaseURL();
        
		//OutputStream pdfStream = null;
//      Setup a buffer to obtain the content length
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
*/
		try {
            RMT2XsltUtility xsl = RMT2XsltUtility.getInstance();
            File baseDirectory = new File("");

            // This is the path to the application's base directory

            String appPath = baseDirectory.getAbsolutePath();


            xsl.renderPdf(TestFopAction.FO_SOURCE_FILE, request, response);

            /*
			// Set up input and output streams.
			// Note: Using BufferedOutputStream for performance reasons (helpful with FileOutputStreams).
			File foFile = new File(TestFopAction.FO_SOURCE_FILE);
			File pdfFile = new File(TestFopAction.PDF_SOURCE_FILE);
			//pdfStream = new BufferedOutputStream(new FileOutputStream(pdfFile));
		           
			// Setup input and output for XSLT transformation 
			// Setup input stream
			Source source = new StreamSource(foFile);

			// Resulting SAX events (the generated FO) must be piped through to FOP
			// Construct fop with desired output format
			//fopFactory.setStrictValidation(false);
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, agent, pdfStream);
			Result result = new SAXResult(fop.getDefaultHandler());

			// Setup JAXP using identity transformer
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(); // identity transformer

			// Start XSLT transformation and FOP processing
			transformer.transform(source, result);
            
             //Prepare response
            this.response.setContentType("application/pdf");
            this.response.setContentLength(pdfStream.size());
            
            //Send content to Browser
            this.response.getOutputStream().write(pdfStream.toByteArray());
            this.response.getOutputStream().flush();
*/
		}
		catch (Throwable e) {
			System.out.println(e.getMessage());
		}
        /*
		finally {
			try {
				  //Clean-up
				pdfStream.close();
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
        		*/
	}

}
