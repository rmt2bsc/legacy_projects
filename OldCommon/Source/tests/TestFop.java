package com;

import org.xml.sax.InputSource;
//import org.w3c.dom.NodeList;
//import org.w3c.dom.Node;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;

import javax.xml.xpath.XPathFactory;
//import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathConstants;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;

import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;

import java.net.URI;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


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
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.MimeConstants;

import org.jdom.input.DOMBuilder;
import org.jdom.input.SAXBuilder;
import org.jdom.Element;
import org.jdom.Document;
import org.jdom.xpath.XPath;

import org.apache.xerces.parsers.DOMParser;





public class TestFop {
	//private static String XSL_SOURCE_FILE = "C:/MyStuff/source/webapp/reports/xsl/EmployeeTimesheetReport.xsl";
	private static String XSL_SOURCE_FILE = "C:/MyStuff/source/webapp/reports/SalesOrderInvoiceReport.xsl";
	
	private static String XML_SOURCE_FILE =  "data.xml";
	private static String FO_SOURCE_FILE = "out.fo";
	private static String PDF_SOURCE_FILE = "pdf_results.pdf";
	
	private String newXslSource = "c:/temp/78432183J8382J/changeReportLayout.xsl";
	private String tempArea = "c:/temp/78432183J8382J/";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestFop test = new TestFop();
		test.init();
	}
	
	public TestFop() {
	}
	
	private void init() {
		this.testXPath();
		this.testXMLCoding();
		
		String fileData = null;
		try {
			fileData = this.getInputFile(XSL_SOURCE_FILE);
			fileData = this.changeInputFile(fileData);
			this.saveInputFile(fileData, this.newXslSource);
		}
		catch (Exception e) {
			return;
		}
		
		this.createFODocument();
		this.createPdfDocument();
	}

	public String getInputFile(String filename) throws Exception {
		File sourceFile = new File(filename);
		if (!sourceFile.exists()) {
            throw new Exception("Input File not found: " + filename);
		}
		
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;
		Runtime.getRuntime().gc();
		int fileSize = new Long(sourceFile.length()).intValue();
		long freeMem = Runtime.getRuntime().freeMemory();
		long maxMem = Runtime.getRuntime().maxMemory();
		long totMem = Runtime.getRuntime().totalMemory();
        try {
        	fis = new FileInputStream(sourceFile);
    		baos = new ByteArrayOutputStream();
    		byte buffer[];
            try {
            	buffer = new byte[fileSize];
            }
            catch (OutOfMemoryError e) {
            	throw new Exception("Out of Memory error occured when attempting to allocate memory buffer for file input: " + filename);
            }
            int bytesRead = fis.read(buffer);
            while (bytesRead != -1) {
            	baos.write(buffer);
            	bytesRead = fis.read(buffer);
            }
            return baos.toString();
        }
        catch (IOException e) {
        	throw new Exception("IO Exception: " + e.getMessage());
        }
        finally {
            if (fis != null) {
            	try {
                    fis.close();
                } 
                catch (IOException e) {
                    ;
                }
            }
        }
	}
	
	public void saveInputFile(String data, String filename) throws Exception {
		File destFile = new File(filename);
		if (destFile.isDirectory()) {
            throw new Exception("output path must be a file instead of a directory: " + filename);
		}
		try {
			byte byteResulsts[] = data.getBytes();
			// Use an FileOutputStream to write data to disk.
			FileOutputStream fos = new FileOutputStream (destFile);
			fos.write(byteResulsts);
			fos.flush();
			fos.close();
		}
		catch (FileNotFoundException e) {
			throw new Exception("File was not found: " + filename + ".  Check whether or not the directory structure exists");
		}
		catch (NotSerializableException e) {
			throw new Exception("Object is not serializable...Ensure that object implements java.io.Serializable interface");
		}
		catch (IOException e) {
			throw new Exception("General file IO error occurred");
		}
	}
	
	
	public String changeInputFile(String data) {
		return this.replaceAll(data, "C:/MyStuff/source/webapp/images/", "$IMAGES_DIRECTORY$");
	}
	
	
	
	
	public void createFODocument() {
		
		
		try {
			File xslFile = new File(this.newXslSource);
			File xmlFile = new File(this.tempArea + TestFop.XML_SOURCE_FILE);
			File foFile = new File(this.tempArea + TestFop.FO_SOURCE_FILE);
			
//			StreamSource xslStream = new StreamSource(xslFile);
			URI uri = xslFile.toURI();
			String path = uri.getPath();
			StreamSource xslStream = new StreamSource(new FileInputStream(xslFile), path);
			StreamSource xmlStream = new StreamSource(xmlFile);
			StreamResult foStream = new StreamResult(new FileOutputStream(foFile));
			
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(xslStream); // identity transformer
			transformer.transform(xmlStream, foStream);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
	
	
	public void createPdfDocument() {
		// Construct a FopFactory
		// (reuse if you plan to render multiple documents!)
		FopFactory fopFactory = FopFactory.newInstance();
		OutputStream pdfStream = null;

		try {
			// Set up input and output streams.
			// Note: Using BufferedOutputStream for performance reasons (helpful with FileOutputStreams).
			File foFile = new File(this.tempArea + TestFop.FO_SOURCE_FILE);
			File pdfFile = new File(this.tempArea + TestFop.PDF_SOURCE_FILE);
			pdfStream = new BufferedOutputStream(new FileOutputStream(pdfFile));
		           
			// Setup input and output for XSLT transformation 
			// Setup input stream
			Source source = new StreamSource(foFile);

			// Resulting SAX events (the generated FO) must be piped through to FOP
			// Construct fop with desired output format
			//fopFactory.setStrictValidation(false);
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, pdfStream);
			Result result = new SAXResult(fop.getDefaultHandler());
			FOUserAgent agent = fopFactory.newFOUserAgent();
	        String baseUrl = agent.getBaseURL();

			// Setup JAXP using identity transformer
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(); // identity transformer

			// Start XSLT transformation and FOP processing
			transformer.transform(source, result);

		}
		catch (Throwable e) {
			System.out.println(e.getMessage());
		}
		finally {
			try {
				  //Clean-up
				pdfStream.close();
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}		
	}

	
	public String replace(String _source, String _replacement, String _delim) {

	    String     value = null;
	    int          ndx;

	    if (_delim == null) {
	      _delim = "";
	    }

	      // Determine the position of the first occurrence of the delmimter
	    ndx = _source.indexOf(_delim);
	    if (ndx <= -1) {
					return _source;
			}

	      //  Get portion of string before delimiter
			value = _source.substring(0, ndx);

			  // Add replacement
			value += _replacement;

			  // Get the portion of string beyond the delimiter.
			value += _source.substring(ndx + _delim.length());

			  // Retrun the results
			return value;

	  }


	  /**
	   * Parses _source by replacing the all  occurrences of "_delim" with "_replacement" and returns the results to the caller.
	   * 
	   * @param _source String that is to be parsed for for placeholders
	   * @param _replacement Value that will replace "_delim"
	   * @param _delim The place holder representing the value that is to be replaced.
	   * @return String
	   */
	  public  String replaceAll(String _source, String _replacement, String _delim) {

	    String     newString = null;
	    int          total;
	    int          ndx;

	    ndx = 0;
	    total = 0;

	      // Determine the number of place holders are required to be substituted.
	    while(ndx != -1) {
					ndx = _source.indexOf(_delim, ndx);
					if (ndx > -1) {
							total++;
							ndx++;
					}
			}

	      // Replace each place holder with "_delim".
			newString = _source;
			for (ndx = 1;ndx <= total; ndx++) {
					newString = replace(newString, _replacement, _delim);
			}

			return newString;
	  }
	  
	  public void testXMLCoding() {
		  XMLEncoder out;
		  XMLDecoder in;
		  Person p = new Person();
		  
		  p.setId(100);
		  p.setFirstname("Roy");
		  p.setLastname("Terrell");
		  p.setBirthDate(new java.util.Date());
		  p.setEmail("royroy@.gte.net");
		  p.setGenderId(1);
		  p.setMaritalStatus(2);
		  p.setMidname("Milton");
		  p.setGeneration("Jr");
		  p.setRaceId(6);
		  p.setSsn("345439494");
		  p.setShortname("Terrell, Roy");
		  p.setTitle(1);
		  
		  try {
			  in = new XMLDecoder(new BufferedInputStream(new FileInputStream("c:\\temp\\general_codes.xml")));
			  Object obj = in.readObject();
			  in.close();
			  
			  out = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("c:\\temp\\person.xml")));
			  out.writeObject(p);
			  out.flush();
			  out.close();
		  }
		  catch (Exception e) {
			    System.out.println(e.getMessage());
		  }
	  }
	  
	  public void testXPath() {
		  try {
			  InputSource source = new InputSource(new BufferedInputStream(new FileInputStream("c:\\temp\\general_codes.xml")));
			  SAXBuilder builder = new SAXBuilder();
			  Document doc = builder.build(source);
			  List list = XPath.selectNodes(doc, "/table/row[id>128]");
			  String name;
			  String value;
			  for (int ndx = 0; ndx < list.size(); ndx++) {
				  Element rowElement = (Element) list.get(ndx);
				  List list2 = rowElement.getChildren();
				  for (int ndx2 = 0; ndx2 < list2.size(); ndx2++) {
				     Element element2 = (Element) list2.get(ndx2);
				     name = element2.getName();
				     value = element2.getValue();
				  }
			  }
		  }
		  catch (Exception e) {
			    System.out.println(e.getMessage());
		  }
	  }	  
}
