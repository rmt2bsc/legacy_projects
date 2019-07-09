import com.util.SystemException;
import com.api.db.DatabaseException;
import com.util.NotFoundException;
import com.bean.db.DataSourceColumn;
//import com.util.RMT2DatasourceParser;
import com.util.RMT2String;
import com.util.RMT2Utility;
import com.api.db.orm.DataSourceFactory;
import com.api.db.orm.DataSourceAdapter;

import com.bean.db.ObjectMapperAttrib;
import com.api.DataSourceApi;
import com.api.DaoApi;
import com.bean.db.DatabaseConnectionBean;
import com.api.db.JndiDatabaseConnectionImpl;

import com.api.db.DynamicSqlApi;
import com.api.db.DynamicSqlImpl;
import com.api.db.DynamicSqlFactory;

import com.bean.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.ParseException;
import java.sql.*;
import java.util.*;
import com.util.RMT2BeanUtility;
import java.beans.Beans;
//import com.bean.GlAccounts;
//import com.bean.GlAccountCategory;
//import com.bean.PurchaseOrder;
//import com.bean.PurchaseOrderItems;
//import com.api.AcctManagerApi;
//import com.api.PurchasesApi;
//import com.factory.AcctManagerFactory;
//import com.factory.PurchasesFactory;
//import com.factory.InventoryFactory;
//import com.taglib.RMT2WebDbMenu;
//import com.taglib.RMT2WebMenus;
//import com.util.GLAcctException;
//import com.api.RMT2DBTransApi;
//import com.factory.RMT2DBTransFactory;
//import com.bean.Person;
//import com.bean.Business;
//import com.bean.Address;
//import com.bean.ItemMaster;
//import com.bean.Creditor;
//import com.bean.Customer;
//import com.util.ContactPersonException;
//import com.util.ContactBusinessException;
//import com.util.ContactAddressException;
//import com.util.ItemMasterException;
//import com.util.CreditorException;
//import com.util.CustomerException;
//import com.factory.ContactsApiFactory;
//import com.factory.ContactsFactory;
//import com.api.ContactPersonApi;
//import com.api.ContactBusinessApi;
//import com.api.ContactAddressApi;
//
//import com.api.GLBasicApi;
//import com.api.GLCreditorApi;
//import com.api.GLCustomerApi;
//import com.api.InventoryApi;
//import com.factory.GlAccountsFactory;
//
//import com.api.XactManagerApi;
//import com.factory.XactManagerApiFactory;
//import com.factory.XactFactory;
//
//import com.bean.XactXlatBean;
//import com.bean.Xact;
//import com.bean.XactPostDetails;
//
//import com.util.XactException;
//
//import com.bean.SalesOrder;
//import com.bean.SalesOrderItems;
//import com.util.SystemException;
//import com.util.SalesOrderException;
//import com.util.PurchaseOrderException;
//import com.api.SalesOrderApi;
//import com.factory.SalesFactory;
//
//import com.factory.ProjectManagerFactory;
//import com.api.ProjectManagerApi;
//import com.util.ProjectException;
//import com.bean.ProjEmployee;
//import com.bean.ProjClient;
//import com.bean.ProjProject;
////import com.bean.ProjRole;
//import com.bean.ProjEvent;
////import com.bean.ProjEventDetails;
//
//import com.util.EMailException;
//import com.bean.EMailBean;
//import com.api.EMailManagerApi;
//import com.factory.EMailFactory;

import javax.activation.MimetypesFileTypeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


//import org.farng.mp3.MP3File;
//import org.farng.mp3.id3.AbstractID3v2;
//import org.farng.mp3.id3.ID3v2_2;
//import org.farng.mp3.id3.ID3v2_2Frame;
//import org.farng.mp3.id3.FrameBodyTPE1;  // Artist
//import org.farng.mp3.id3.FrameBodyTALB;  // Album
//import org.farng.mp3.id3.FrameBodyTIT2;  // Track Title
//import org.farng.mp3.id3.FrameBodyTRCK;  // Track Number
//import org.farng.mp3.id3.FrameBodyCOMM;  // Notes
//import org.farng.mp3.id3.FrameBodyTCON;  // Genre
//import org.farng.mp3.id3.FrameBodyTYER;  // Year Released
//import org.farng.mp3.id3.FrameBodyTLEN;  // Recording Time
//import org.farng.mp3.id3.FrameBodyCOMM;  // Comments


//import com.factory.UserSecurityManagerFactory;
//import com.api.UserSecurityManagerApi;
//import com.apiimpl.UserSecurityManagerApiImpl;
//import com.bean.UserLogin;
//import com.util.UserSecurityException;

import java.io.*;


public class testapp {

  public Connection con;
  public ResourceBundle prop;
  int rc;



  public testapp() {
      List value;
      String temp = RMT2String.replaceAll("Tony's Seafood Troy's Burgers", "''", "'");
      value = RMT2Utility.cleanUpPhoneNo("(214)333-3333");
      value = RMT2Utility.cleanUpPhoneNo("(214) 333 - 3333 Ext1234");
      value = RMT2Utility.cleanUpPhoneNo("(214) 333 - 3333 x1234");
      value = RMT2Utility.cleanUpPhoneNo("(214)333-3333");
      value = RMT2Utility.cleanUpPhoneNo("2143333333 Ext1234");
      value = RMT2Utility.cleanUpPhoneNo("2143333333Ext1234");
      value = RMT2Utility.cleanUpPhoneNo("(214)3333333 X1234");
//    String str[] = {"1", "2", "3"};
//    int intTemp[] = new int[str.length];
//
//    intTemp[1] = Integer.parseInt(str[2]);
//    int  rc = 0;
//    Object obj = new Object();
//    RMT2Utility.isArray(obj);
//    Object objs[] = new Object[5];
//    RMT2Utility.isArray(objs);
//    objs[0] = new Object();
//    objs[1] = new Object();
//    objs[2] = new Object();
//    objs[3] = new Object();
//    objs[4] = new Object();
//    RMT2Utility.isArray(objs);
///*
//    try {
//	    StringBuffer emailContent = new StringBuffer(100);
//	    String  emailContentDelta = null;
//	    String  temp = null;
//	    FileReader fr = new FileReader("C:\\projects\\TAPTravel\\Source\\webapp\\forms\\tap\\marketing\\PowerTeamOffer.html");
//	    BufferedReader buf = new BufferedReader(fr);
//
//	    while( (temp = buf.readLine()) != null) {
//	    	emailContent.append(temp);
//	    }
//    }
//    catch (FileNotFoundException e) {
//    	System.out.println("FileNotFoundException occurred");
//    }
//    catch (IOException e) {
//    	System.out.println("IOException occurred");
//    }
//
//
//   */
//
//
//
//
//    try {
//      // Finding File-Application Associations
//      MimetypesFileTypeMap typMap = new MimetypesFileTypeMap();
//      String kfds = typMap.getContentType("C:\\data\\doom3_Complete_Cheats.txt");
//
//      Person per = new Person();
//      RMT2BeanUtility beanUtil = new RMT2BeanUtility(per);
//    	per = (Person) beanUtil.initializeBean();
//
//      RMT2DBConnectionApiImpl  test  = new RMT2DBConnectionApiImpl();
//      test.initConnections();
//      int key = RMT2SqlConst.getSelectObjectKey("SELECT");
//      Properties     dbParms = new Properties();
//      prop = ResourceBundle.getBundle("SystemParms");
//      RMT2DBConnectionBean con = test.getDBConnection();
//
//
//      DataSourceApi dso = con.getDataObject("UserLoginView");
//
////      testElapsedTime();
////      testTAPEmail(con);
////        testUserSecurity(con);
////      testAV(con);
////      testFileIO();
////      testMail(con);
////      testProject(con);
////      testStorProcException(con);
////      testCancelSalesOrder(con);
////      testSalesInvoice(con);
////      testSalesOrder(con);
////      testTransactions(con);
////      testAccounting(con);
////      testItem(con);
////     testPurchases(con);
//      testContacts(con);
////      testStoreFunctionCall(con);
////        DatasourceTest(con);
//
//
//     }
//     catch (Exception e) {
//        System.out.println(e.getMessage());
//     }
//
  }
//
//public void testXML() {
//	try {
//	    Object result;
//	    String str;
//	    String data = RMT2Utility.GetTextFileContents("C:/BookInventory.xml");
//	    com.api.xml.DocumentDataSource doc = com.api.xml.DocumentDataSource.getInstance(data, this.request);
//	    result = doc.get("//book[@year = 2005]/title", "title");
//	    str = result.toString();
//
//	    org.w3c.dom.NodeList list = doc.getNodes();
//	    com.api.xml.NodeListDataSource node = com.api.xml.NodeListDataSource.getInstance(list, this.request);
//	    result = node.get("title");
//
//	    result = doc.get("title");
//	    str = result.toString();
//
//	    list = doc.getNodes();
//	    node = com.api.xml.NodeListDataSource.getInstance();
//	    org.w3c.dom.Node n = list.item(0);
//	    result = node.get(list, "xtitle");
//	    str = str;
//	}
//	catch (Exception e) {
//	    logger.log(Level.ERROR, e.getMessage());
//	}
//
//
//
//
//}
//
//    public void testElapsedTime() {
//    	java.util.Date beg = new GregorianCalendar(2005, 7, 30, 7, 11, 0).getTime();
//    	ElapsedTime et = RMT2Utility.getDateDiff(beg, new java.util.Date());
//    	System.out.println("Elapsed Time : " + et.toString());
//    	System.out.println("Elapsed Time in verbose: " + et.toString(true));
//    	return;
//    }
//
//
//    public void testTAPEmail(RMT2DBConnectionBean con) throws Exception {
//
//
//
//    	System.setProperty("count", "2100");
//    	String s = System.getProperty("count");
//
//    	RMT2DynamicSqlApi dynamSql = null;
//    	int grpId1 = 3;
//    	int count = 0;
//    	 	dynamSql = RMT2DynamicSQLFactory.create(con);
//  	    ResultSet rs = dynamSql.executeDirect("select count(*) itemsToProcess  from tap_revenue_leads where batch = " + grpId1 + "  and date_mailed is null   ");
//  	    if (rs != null) {
//  	      try  {
//  		    	if (rs.next()) {
//  		    		count = rs.getInt("itemsToProcess");
//  		    	}
//  	      }
//  		    catch (java.sql.SQLException e) {
//  		    	System.out.println("\n[TapJoinAction.waitForResults] " + e.getMessage() + "\n");
//  		    }
//  	    } // end if
//
//
//      DataSourceApi dso = null;
//      DataSourceApi grpDso = null;
//      EMailManagerApi api = null;
//      EMailBean email  = null;
//      String file = null;  //"C:\\projects\\TAPTravel\\Source\\webapp\\forms\\tap\\marketing\\PowerTeamOffer.html";
//      String emailContent = null;
//      String emailContentDelta = null;
//      String fromAddress = null;   //"taptravel@verizon.net";
//      String toAddress = null;
//      String toFirstName = null;
//      String toLastName = null;
//      int  grpId = 0;
//      int totalProcessed = 0;
//      String HTML_CT = "text/html";
//      String TEXT_CT = "text/plain";
//      StringBuffer errorMsg = new StringBuffer(100);
//      StringBuffer headMsg = new StringBuffer(100);
//      int errorCount = 0;
//      int grpErrorCount = 0;
//
//
//      ResourceBundle  prop =  ResourceBundle.getBundle("TAP_config");
//      file = prop.getString("EMailOfferDoc");
//      fromAddress = prop.getString("sender");
//      String emailSubject = prop.getString("email_subject");
//      String reportRecipient = prop.getString("report_recipient");
//      int  sessionCount = 0;
//
//
//      headMsg.append("Started: " + new java.util.Date());
//      try {
//      	emailContent = RMT2Utility.GetTextFileContents(file);
//  	    dso = con.getDataObject("TapRevenueLeadsView");
//  	    api = EMailFactory.createApi(null);
//
//  	    for (int grpNdx = 0; grpNdx < 1; grpNdx++) {
//  	    	try {
//  		    	grpId = 1;  //Integer.parseInt(_groups[grpNdx]);
//
//  		    	grpDso = con.getDataObject("TapRevenueLeadsGrpView");
//  		    	grpDso.setDatasourceSql(RMT2SqlConst.WHERE_KEY, null);
//  		    	grpDso.setDatasourceSql(RMT2SqlConst.WHERE_KEY, "id = " + grpId);
//  		    	grpDso.executeQuery(false, true);
//  		    	grpDso.nextRow();
//
//  		    	dso.setDatasourceSql(RMT2SqlConst.WHERE_KEY, null);
//  		    	dso.setDatasourceSql(RMT2SqlConst.WHERE_KEY, "batch = " + grpId);
//  		    	dso.executeQuery(false, true);
//
//
//  		    		grpErrorCount = 0;
//  			    	while (dso.nextRow()) {
//  			    		sessionCount++;
//  			    		try {
//	  			    		toAddress = dso.getColumnValue("Email");
//	  			    		toFirstName = dso.getColumnValue("Firstname");
//	  			    		toLastName = dso.getColumnValue("Lastname");
//
//	  			        email = EMailFactory.create();
//	  			        email.setToAddress(toAddress);
//	  			        email.setFromAddress(fromAddress);
//	  			        email.setSubject("Test Join TAP Travel Offer!!!");
//
//	  			        emailContentDelta = emailContent;
//	  			        email.setBody(emailContentDelta, HTML_CT);
//
//	  			        api.setEmailBean(email);
//	  			        api.sendMail();
//
//	  			        dso.setColumnValue("DateMailed", new java.util.Date());
//	  			        dso.updateRow();
//
//	  			        totalProcessed++;
//	  			        email = null;
//
//	  			        if (sessionCount > 20) {
//	  			        	sessionCount = 0;
//	  			        	api.closeTransport();
//	  			        	api = null;
//	  			        	api = EMailFactory.createApi(null);
//	  			        }
//
//  			    		} // end try
//  			    		catch (EMailException e) {
//  	  		      	System.out.println(e.getMessage());
//  	  		      	errorCount++;
//  	  		      	grpErrorCount++;
//  	  		      	errorMsg.append(errorCount);
//  	  		      	errorMsg.append(". [Error] ");
//  	  		      	errorMsg.append(toAddress);
//  	  		      	errorMsg.append(" ");
//  	  		      	errorMsg.append(toLastName);
//  	  		      	errorMsg.append(", ");
//  	  		      	errorMsg.append(toFirstName);
//  	  		      	errorMsg.append(" Error Message: ");
//  	  		      	errorMsg.append(e.getMessage());
//  	  		      	errorMsg.append("<br>");
//  	  		      }
//
//  		    	} // end while
//
//  	    	} // end try
//  	      catch (NumberFormatException e) {
//            // TODO: Write error to batch log including Group Id that could not be processed
//  	      	grpErrorCount++;
//  	      }
//
//  	      grpDso.setColumnValue("Processed", "Y");
//        	grpDso.setColumnValue("Errors", grpErrorCount > 0 ? "Y" : "N");
//        	grpDso.updateRow();
//  	    } // end for
//
//  	    dso.commitTrans();
//  	    dso.close();
//  	    grpDso.close();
//
//  	    headMsg.append("<br>Ended: " + new java.util.Date());
//  	    headMsg.append("<br>Total number of emails processed: " + totalProcessed);
//  	    headMsg.append("<br>Total number of errors: " + errorCount);
//  	    headMsg.append("<br><br>");
//
//  	    try {
//          email = EMailFactory.create();
//          email.setToAddress(fromAddress);
//          email.setFromAddress(fromAddress);
//          email.setSubject("TAP Travel Mass EMail Report");
//
//          emailContentDelta = headMsg.toString() + errorMsg.toString();
//          email.setBody(emailContentDelta, HTML_CT);
//
//          api.setEmailBean(email);
//          api.sendMail();
//  	    }
//  	    catch (EMailException e) {
//  	    	System.out.println(e.getMessage());
//  	    	grpErrorCount++;
//  	    	grpErrorCount++;
//  	    }
//
//  	    return;
//
//      } // end try
//
//      catch (SystemException e) {
//      	dso.rollbackTrans();
//      	throw new Exception(e);
//      }
//      catch (DatabaseException e) {
//      	dso.rollbackTrans();
//      	throw new Exception(e);
//      }
//      catch (NotFoundException e) {
//      	dso.rollbackTrans();
//      	throw new Exception(e);
//      }
//      finally {
//      	dso = null;
//      }
//
//    }
//
//
//
//    public void testUserSecurity(RMT2DBConnectionBean con)  throws Exception {
//      RMT2DBTransApi tranApi = null;
//      UserSecurityManagerApi api;
//      UserLogin obj;
//
//      int   rc;
//
//      try {
//        tranApi = RMT2DBTransFactory.create(con);
//        api = UserSecurityManagerFactory.createApi(con);
//
//        obj = api.verifyLogin("admin", "admin");
//        rc = obj.getTotalLogons();
//        rc++;
//        obj.setTotalLogons(rc);
//        api.maintainUser(obj);
//        tranApi.commitTrans();
//
//        obj = UserSecurityManagerFactory.createUserLogin();
//        obj.setLogin("admin");
//        obj.setPassword("admin");
//        obj.setDescription("This is a test");
//        api.maintainUser(obj);
//        tranApi.commitTrans();
//        System.exit(1);
//
//
//        obj = api.findUserByLoginId("admin");
//        obj.setLogin("admin");
//        obj.setPassword("admin");
//        api.maintainUser(obj);
//        tranApi.commitTrans();
//        System.exit(1);
//      }
//      catch (UserSecurityException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//        throw e;
//      }
//      catch (SystemException e) {
//         String ls_tempMsg = e.getMessage();
//      }
//      catch (Exception e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//
//    }
//
//    public void testFileIO()  throws Exception {
//
//      File file, file2[];
//      String ls_fileName;
//      String ls_path;
//      String ls_parentPath;
//      String value;
//      boolean lb_rc;
//      int   rc;
//      GregorianCalendar tm;
//
//
//      try {
//        file = new File("\\\\Rmtdaldev02\\Albums\\Albums\\Jeff Lorber\\Wizard Island");
////        file = new File("\\\\Rmtdaldev02\\Albums");
//        lb_rc = file.exists();
//        lb_rc = file.isAbsolute();
//        lb_rc = file.isDirectory();
//        lb_rc = file.isFile();
//        ls_fileName = file.getName();
//        ls_path = file.getPath();
//
//
//        file2 = file.listFiles();
//        rc = file2.length;
//        for (int ndx = 0; ndx < rc; ndx++) {
///*
//          lb_rc = file2[ndx].exists();
//          lb_rc = file2[ndx].isAbsolute();
//          lb_rc = file2[ndx].isDirectory();
//          lb_rc = file2[ndx].isFile();
//*/
//
///*
//import org.farng.mp3.id3.FrameBodyTPE1;  // Artist
//import org.farng.mp3.id3.FrameBodyTALB;  // Album
//import org.farng.mp3.id3.FrameBodyTIT2;  // Track Title
//import org.farng.mp3.id3.FrameBodyTDRC;  // Recording Time
//import org.farng.mp3.id3.FrameBodyTRCK;  // Track Number
//import org.farng.mp3.id3.FrameBodyTDOR;  // Year Released
//import org.farng.mp3.id3.FrameBodyCOMM;  // Notes
//import org.farng.mp3.id3.FrameBodyTCON;  // Genre
//import org.farng.mp3.id3.FrameBodyTIME;
//import org.farng.mp3.id3.FrameBodyTORY;
//
//*/
//          ls_fileName = file2[ndx].getName();
//          ls_path = file2[ndx].getPath();
//          ls_parentPath = file2[ndx].getParent();
//          File sourceFile = new File(ls_path);
//          MP3File mp3file = new MP3File(sourceFile);
//          ID3v2_2 tag = (ID3v2_2) mp3file.getID3v2Tag();
//          ID3v2_2Frame frame = null;
//
//          // Get Comment
//          frame = (ID3v2_2Frame) tag.getFrame("COMM");
//          if (frame != null) {
//            value = ( (FrameBodyCOMM) frame.getBody() ).getText();
//          }
//
//          // Get Artist
//          frame = (ID3v2_2Frame) tag.getFrame("TPE1");
//          if (frame != null) {
//            value = ( (FrameBodyTPE1) frame.getBody() ).getText();
//          }
//
//          // Get Album
//          frame = (ID3v2_2Frame) tag.getFrame("TALB");
//          if (frame != null) {
//            value = ( (FrameBodyTALB) frame.getBody() ).getText();
//          }
//
//          // Get Track Title
//          frame = (ID3v2_2Frame) tag.getFrame("TIT2");
//          if (frame != null) {
//            value = ( (FrameBodyTIT2) frame.getBody() ).getText();
//          }
//
//          // Get Track Number
//          frame = (ID3v2_2Frame) tag.getFrame("TRCK");
//          if (frame != null) {
//            value = ( (FrameBodyTRCK) frame.getBody() ).getText();
//          }
//
//          // Get Genre
//          frame = (ID3v2_2Frame) tag.getFrame("TCON");
//          if (frame != null) {
//            value = ( (FrameBodyTCON) frame.getBody() ).getText();
//          }
//
//          // Get Year Released
//          frame = (ID3v2_2Frame) tag.getFrame("TYER");
//          if (frame != null) {
//            value = ( (FrameBodyTYER) frame.getBody() ).getText();
//          }
//
//          // Get Recording Time
//          frame = (ID3v2_2Frame) tag.getFrame("TLEN");
//          long ms;
//          int  hr;
//          int  min;
//          int  sec;
//          if (frame != null) {
//            value = ( (FrameBodyTLEN) frame.getBody() ).getText();
//            try {
//              ms = new Long(value).longValue();
//            }
//            catch (NumberFormatException e) {
//              ms = 0;
//            }
//            tm = new GregorianCalendar();
//            tm.setTime(new java.util.Date(ms));
//            hr = tm.get(Calendar.HOUR);
//            min = tm.get(Calendar.MINUTE);
//            sec = tm.get(Calendar.SECOND);
//          }
//
//
//        }
//
//
//        System.exit(1);
//      }
//      catch (NullPointerException e) {
//      }
//      catch (Exception e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//
//  }
//
//
//
//
//  public void testMail(RMT2DBConnectionBean con)  throws Exception {
//
//      RMT2DBTransApi tranApi = null;
//      EMailManagerApi api;
//      EMailBean obj;
//      java.util.Date dt = new java.util.Date();
//      String tmp = dt.toString();
//
//      int   rc;
//
//      try {
//        tranApi = RMT2DBTransFactory.create(con);
//        obj = EMailFactory.create();
//        obj.setFromAddress("royterrell@verizon.net");
//        obj.setToAddress("rmt2bsc2@verizon.net");
//        obj.setSubject("Test");
//        obj.setBody("This is Core JSP Programming", null);
//        obj.setAttachments("C:\\jdk1.3\\javamail-1.3.1\\docs\\JavaMail-1.2.pdf");
//
//        api = EMailFactory.createApi( obj);
//        api.sendMail();
//      }
//      catch (EMailException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//        throw e;
//      }
//      catch (SystemException e) {
//         String ls_tempMsg = e.getMessage();
//      }
//      catch (Exception e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//
//  }
//
//
//
//  public void testStoreFunctionCall(RMT2DBConnectionBean con) {
//    RMT2DBTransApi tranApi = null;
//    try {
//      tranApi = RMT2DBTransFactory.create(con);
//      RMT2DynamicSqlApi dynaApi = RMT2DynamicSQLFactory.create(con);
////      dynaApi.addParm("an_id", 1, "100", false);
//      ResultSet rs ;
//      ResultSetMetaData rsmd;
//      try {
////        dynaApi.addParm("result", Types.INTEGER,  "0", true);
//        dynaApi.addParm("id", Types.INTEGER,  "2", false);
//        rs = dynaApi.execute("select connector.ufn_get_acct_usage_count(?) count");
//        rs.next();
//        int count = rs.getInt(1);
//        Object lo_result = dynaApi.getOutParm("result");
//        String rsstr = lo_result.toString();
//      }
//      catch (DatabaseException e) {
//         rc = e.getErrorCode();
//         String ls_temp = e.getMessage();
//      }
//      rs = dynaApi.executeDirect("exec test_proc_query");
//      rsmd = rs.getMetaData();
//      int lnTemp = rsmd.getColumnCount();
//      while(rs.next()) {
//        String desc = rs.getString("description");
//        int lnId = rs.getInt("id");
//      }
//      rs = dynaApi.getRs();
//      rsmd = rs.getMetaData();
//      lnTemp = rsmd.getColumnCount();
//
//
//    }
//
//    catch (SQLException e) {
//      System.out.println(e.getMessage());
//    }
////    catch (ClassNotFoundException e) {
////      System.out.println(e.getMessage());
////    }
//
//    catch (DatabaseException e) {
//      String ls_temp = e.getMessage();
//      System.out.println(e.getMessage());
//    }
//
//    catch (Exception e) {
//      String ls_temp = e.getMessage();
//      System.out.println(e.getMessage());
//    }
//
//  }
//
//
//
//
//  public void testSalesInvoice(RMT2DBConnectionBean con) {
//
//      RMT2DBTransApi tranApi = null;
//      SalesOrderApi api;
//      SalesOrder so;
//      int   rc;
//
//      try {
//        tranApi = RMT2DBTransFactory.create(con);
//        api = SalesFactory.createApi(con);
//        so = api.findSalesOrderById(10008);
//        //rc = api.invoiceSalesOrder(0, so.getId());
//
//        tranApi.commitTrans();
//      }
//      catch (DatabaseException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//      catch (SystemException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//      catch (Exception e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//
//  }
//
//
//  public void testSalesOrder(RMT2DBConnectionBean con) {
//
//      RMT2DBTransApi tranApi = null;
//      SalesOrderApi api;
//      ArrayList items = new ArrayList();
//      SalesOrderItems item;
//      SalesOrder so;
//      int   customerId = 1010;
//      int   rc;
//
//      try {
//        tranApi = RMT2DBTransFactory.create(con);
//        api = SalesFactory.createApi(con);
//
//        // Get an extended sales order by customer
//        ArrayList cso = api.findExtendedSalesOrderByCustomer(1009);
//        int len = cso.size();
//
//        // Create a sales order
//        item = SalesFactory.createSalesOrderItem(0, 7, 3);
//        items.add(item);
//        item = SalesFactory.createSalesOrderItem(0, 8, 121);
//        items.add(item);
//        //rc = api.maintainSalesOrder(0, customerId, items);
//
//           //  Maintain a Sales ORder.
//        so = api.findSalesOrderById(10002);
//        items = api.findSalesOrderItems(10002);
//        item = (SalesOrderItems) items.get(1);
//        item.setOrderQty(140);
//        items.remove(0);
//        //rc = api.maintainSalesOrder(so.getId(), so.getCustomerId(), items);
//
//        tranApi.commitTrans();
//      }
////      catch (XactException e) {
////        String ls_temp = e.getMessage();
////        System.out.println(e.getMessage());
////      }
//      catch (DatabaseException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//      catch (SystemException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//      catch (Exception e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//
//  }
//
//
//
//  public void testCancelSalesOrder(RMT2DBConnectionBean con) {
//
//      RMT2DBTransApi tranApi = null;
//      SalesOrderApi api;
//      ArrayList items = new ArrayList();
//      SalesOrderItems item;
//      SalesOrder so;
//      int   customerId = 1010;
//      int   rc;
//
//      try {
//        tranApi = RMT2DBTransFactory.create(con);
//        api = SalesFactory.createApi(con);
//        rc = api.cancelSalesOrder(10002);
//        tranApi.rollbackTrans();
//
//        tranApi.commitTrans();
//      }
//      catch (DatabaseException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//      catch (SystemException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//      catch (Exception e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//
//  }
//
//
//
//
//
//  public void testContacts(RMT2DBConnectionBean con) {
//
//      RMT2DBTransApi tranApi = null;
//      try {
//        tranApi = RMT2DBTransFactory.create(con);
//        Person per = ContactsFactory.createPerson();
//        per.addCriteria("Id", "1000003");
//        //per.setResultsetType(RMT2BaseBean.RESULTSET_TYPE_XML);
//        //DaoApi dso =   RMT2DataSourceFactory.createDao(con, "PersonView");
//        DaoApi dso =   RMT2DataSourceFactory.createDao(con);
//        Object rc[] = dso.retrieve(per);
//        //String xml = (String) rc[0];
//
//        Business bus2 = ContactsFactory.createBusiness();
//        bus2.addCriteria("Id", "2000202");
//        //bus2.setResultsetType(RMT2BaseBean.RESULTSET_TYPE_XML);
//        dso.setDataSourceName( "BusinessView");
//        rc = dso.retrieve(bus2);
//        //xml = (String) rc[0];
//
//
//
//           // Handle Business Entites
//        ContactBusinessApi bapi =  ContactsApiFactory.createBusinessApi(con);
//        Business bus = ContactsFactory.createBusiness();
//        bus.setBusType(24);
//        bus.setServType(35);
//        bus.setLongname("Epic Records");
//        bus.setShortname("Epic");
//        bus.setContactFirstname("Hallie");
//        bus.setContactLastname("Berry");
//        bus.setContactPhone("2144545455");
//        bus.setContactExt("33");
//        bus.setTaxId("984753049");
//        bus.setWebsite("www.rpiv.com");
//        int li_busid = bapi.maintainBusiness(bus);
//
//        bus = null;
//        bus = bapi.findBusById(2000004);
//        bus.setLongname("Taliant Software");
//        bus.setShortname("Taliant");
//        bapi.maintainBusiness(bus);
//
//        bus = null;
//        bus = bapi.findBusById(2000005);
//        bus.setLongname("Burlington Resources");
//        bus.setShortname(null);
//        bapi.maintainBusiness(bus);
//
//            //  Handle Person Entities
//        ContactPersonApi papi = ContactsApiFactory.createPersonApi(con);
//        Person person = ContactsFactory.createPerson();
//        person.setFirstname("Halle");
//        person.setMidname(null);
//        person.setLastname("Berry");
//        person.setMaidenname(null);
//        person.setGeneration(null);
//        person.setTitle(244);
//        person.setGenderId(23);
//        person.setMaritalStatus(18);
//        person.setBirthDate(RMT2Utility.stringToDate("5/15/1966"));
//        person.setRaceId(226);
//        person.setSsn("564344534");
//        person.setEmail("halleberry@gte.net");
//        int li_id = papi.maintainPerson(person);
//
//        person = null;
//        person = papi.findPerById(1000004);
//        person.setMaritalStatus(18);
//        papi.maintainPerson(person);
//
//        person = null;
//        person = papi.findPerById(1000005);
//        person.setMaritalStatus(18);
//        papi.maintainPerson(person);
//
//           //  Handle Address Entites
//        ContactAddressApi aapi = ContactsApiFactory.createAddressApi(con);
//        Address address = ContactsFactory.createAddress();
//        address.setPersonId(li_id);
//        address.setBusinessId(li_busid);
//        address.setAddr1("610 Hoover");
//        address.setAddr2(null);
//        address.setAddr3(null);
//        address.setAddr4(null);
//        address.setZip(61326);
//        address.setZipext(null);
//        address.setPhoneHome("2143305884");
//        address.setPhoneWork("2149545495");
//        address.setPhoneExt("400");
//        address.setPhoneMain("2149995495");
//        address.setPhoneCell("2149545495");
//        address.setPhoneFax("2146665495");
//        address.setPhonePager("2147775495");
//        int li_addr = aapi.maintainAddress(address);
//
//        tranApi.commitTrans();
//      }
//      catch (ContactPersonException e) {
//         String ls_tempMsg = e.getMessage();
//      }
//      catch (ContactBusinessException e) {
//         String ls_tempMsg = e.getMessage();
//      }
//      catch (ContactAddressException e) {
//         String ls_tempMsg = e.getMessage();
//      }
//      catch (DatabaseException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//      catch (SystemException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//      catch (Exception e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//
//  }
//
//  public void testPurchases(RMT2DBConnectionBean con) {
//      RMT2DBTransApi tranApi = null;
//      PurchasesApi poApi = null;
//      PurchaseOrder po = null;
//      PurchaseOrderItems item = null;
//      ArrayList list = new ArrayList();
//      DaoApi nullDs = RMT2DataSourceFactory.createDao(con);
//      java.util.Date today = new java.util.Date();
//      int uid = 0;
//      int rc = 0;
//
//      try {
//        tranApi = RMT2DBTransFactory.create(con);
//        String key= null;
//        int newId = 0;
///*
//        poApi =  PurchasesFactory.createApi(con);
//        po = poApi.findPurchaseOrder(45);
//        list = poApi.findPurchaseOrderItems(45);
//        item = (PurchaseOrderItems) list.get(0);
//        rc = nullDs.deleteRow(item);
//        rc = nullDs.deleteRow(po);
//        tranApi.commitTrans();
//
//        po.setTotal(533.45);
//        po.setDateCreated(today);
//        po.setDateUpdated(today);
//        po.setUserId("rterrell");
//        rc = nullDs.updateRow(po);
//        tranApi.commitTrans();
//  */
//        po = PurchasesFactory.createPurchaseOrder();
//        po.setStatus(1);
//        po.setVendorId(22011);
//        po.setTotal(166);
//        po.setNull("total");
//        po.setDateCreated(today);
//        po.setDateUpdated(today);
//        po.setUserId("rterrell");
//        uid = nullDs.insertRow(po, true);
//
//
//        item = PurchasesFactory.createPurchaseOrderItem(0);
//        item.setPurchaseOrderId(uid);
//        item.setItemMasterId(7);
//        item.setQty(21);
//        item.setDateCreated(today);
//        item.setDateUpdated(today);
//        item.setUserId("rterrell");
//        list.add(item);
//        uid = nullDs.insertRow(item, true);
//        tranApi.commitTrans();
//
//        DataSourceAdapter.packageDSO(nullDs, item);
//        uid = nullDs.insertRow(item, true);
//
////        poApi.maintainPurchaseOrder(0, 22011, list);
////        tranApi.commitTrans();
//      }
//      catch (DatabaseException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//      catch (Exception e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//  }
//
//  public void testItem(RMT2DBConnectionBean con) {
//
//    RMT2DBTransApi tranApi = null;
//    ItemMaster  item;
//    GlAccounts  acct;
//    try {
//      tranApi = RMT2DBTransFactory.create(con);
//
//      int newId = 0;
//
//      GLBasicApi acctApi = AcctManagerFactory.createBasic(con);
//      InventoryApi itemApi = InventoryFactory.createInventoryApi(con);
//
//
//             //  Create an Item Mater GL Account
//      acctApi = AcctManagerFactory.createBasic(con);
//
//      // Perform updates
//      item = itemApi.findItemById(145);
//      item.setVendorId(0);
//      item.setItemTypeId(1);
//      item.setVendorItemNo(null);
//      item.setItemSerialNo(null);
//      item.setQtyOnHand(1);
//      item.setMarkup(1);
//      item.setUnitCost(2345.99);
//      item.setRetailPrice(2345.99);
//      itemApi.maintainItemMaster(item, null);
//
//      item = itemApi.findItemById(146);
//      item.setVendorId(0);
//      item.setItemTypeId(1);
//      item.setQtyOnHand(1);
//      item.setMarkup(1);
//      item.setUnitCost(500.99);
//      item.setRetailPrice(500.99);
//      itemApi.maintainItemMaster(item, null);
//
//      item = itemApi.findItemById(147);
//      item.setVendorId(0);
//      item.setItemTypeId(1);
//      item.setQtyOnHand(1);
//      item.setMarkup(1);
//      item.setUnitCost(2500.99);
//      item.setRetailPrice(2500.99);
//      itemApi.maintainItemMaster(item, null);
//
//      tranApi.commitTrans();
//
//
//      // Perform Adds
//      itemApi = InventoryFactory.createInventoryApi(con);
//      item = GlAccountsFactory.createItemMaster();
//      item.setVendorId(22012);
//      item.setDescription("Doom 2 PC Game");
//      item.setVendorItemNo("4325438");
//      item.setItemSerialNo("DPC84");
//      item.setQtyOnHand(100);
//      item.setUnitCost(16.65);
//      item.setMarkup(3);
//      item.setItemTypeId(2);
//      rc = itemApi.maintainItemMaster(item, null);
//
//      acctApi = AcctManagerFactory.createBasic(con);
//      acct = GlAccountsFactory.create();
//      acct.setAcctTypeId(4);
//      acct.setAcctCatId(401);
//      acct.setName("Doom 1");
//      itemApi = InventoryFactory.createInventoryApi(con);
//      item = GlAccountsFactory.createItemMaster();
//      item.setVendorId(22012);
//      item.setDescription("Doom PC Game");
//      item.setVendorItemNo("25438");
//      item.setItemSerialNo("DPC94");
//      item.setQtyOnHand(30);
//      item.setUnitCost(12.65);
//      item.setMarkup(3);
//      item.setItemTypeId(2);
//      rc = itemApi.maintainItemMaster(item, null);
//
//      acctApi = AcctManagerFactory.createBasic(con);
//      acct = GlAccountsFactory.create();
//      acct.setAcctTypeId(4);
//      acct.setAcctCatId(401);
//      acct.setName("Zip Disk 250");
//      itemApi = InventoryFactory.createInventoryApi(con);
//      item = GlAccountsFactory.createItemMaster();
//      item.setVendorId(22012);
//      item.setDescription("Zip250, Single");
//      item.setVendorItemNo("FD25438");
//      item.setItemSerialNo("943-DPC94");
//      item.setQtyOnHand(130);
//      item.setUnitCost(5.65);
//      item.setMarkup(3);
//      item.setItemTypeId(2);
//      rc = itemApi.maintainItemMaster(item, null);
//
//      acctApi = AcctManagerFactory.createBasic(con);
//      acct = GlAccountsFactory.create();
//      acct.setAcctTypeId(4);
//      acct.setAcctCatId(401);
//      acct.setName("Zip Disk 150");
//      itemApi = InventoryFactory.createInventoryApi(con);
//      item = GlAccountsFactory.createItemMaster();
//      item.setVendorId(22012);
//      item.setDescription("Zip150, Single");
//      item.setVendorItemNo("FD150");
//      item.setItemSerialNo("942-DPC94");
//      item.setQtyOnHand(130);
//      item.setUnitCost(3.65);
//      item.setMarkup(3);
//      item.setItemTypeId(2);
//      rc = itemApi.maintainItemMaster(item, null);
//
//      acctApi = AcctManagerFactory.createBasic(con);
//      acct = GlAccountsFactory.create();
//      acct.setAcctTypeId(4);
//      acct.setAcctCatId(401);
//      acct.setName("Zip Disk 750");
//      itemApi = InventoryFactory.createInventoryApi(con);
//      item = GlAccountsFactory.createItemMaster();
//      item.setVendorId(22012);
//      item.setDescription("Zip750, Single");
//      item.setVendorItemNo("FD750");
//      item.setItemSerialNo("942-DPC94");
//      item.setQtyOnHand(230);
//      item.setUnitCost(7.65);
//      item.setMarkup(3);
//      item.setItemTypeId(2);
//      rc = itemApi.maintainItemMaster(item, null);
//
//      acctApi = AcctManagerFactory.createBasic(con);
//      acct = GlAccountsFactory.create();
//      acct.setAcctTypeId(4);
//      acct.setAcctCatId(401);
//      acct.setName("Power Pack 1100");
//      itemApi = InventoryFactory.createInventoryApi(con);
//      item = GlAccountsFactory.createItemMaster();
//      item.setVendorId(22012);
//      item.setDescription("AC Power Pack 1100");
//      item.setVendorItemNo("FD150");
//      item.setItemSerialNo("GF-942-DPC94");
//      item.setQtyOnHand(20);
//      item.setUnitCost(7.65);
//      item.setMarkup(3);
//      item.setItemTypeId(2);
//      rc = itemApi.maintainItemMaster(item, null);
//
//      acctApi = AcctManagerFactory.createBasic(con);
//      acct = GlAccountsFactory.create();
//      acct.setAcctTypeId(4);
//      acct.setAcctCatId(401);
//      acct.setName("Ledger Book");
//      itemApi = InventoryFactory.createInventoryApi(con);
//      item = GlAccountsFactory.createItemMaster();
//      item.setVendorId(22012);
//      item.setDescription("Accounting Ledger, Single Leaf");
//      item.setVendorItemNo("FD150");
//      item.setItemSerialNo("94294");
//      item.setQtyOnHand(10);
//      item.setUnitCost(2.55);
//      item.setMarkup(3);
//      item.setItemTypeId(2);
//      rc = itemApi.maintainItemMaster(item, null);
//
//      acctApi = AcctManagerFactory.createBasic(con);
//      acct = GlAccountsFactory.create();
//      acct.setAcctTypeId(4);
//      acct.setAcctCatId(401);
//      acct.setName("ASound NIC");
//      itemApi = InventoryFactory.createInventoryApi(con);
//      item = GlAccountsFactory.createItemMaster();
//      item.setVendorId(22012);
//      item.setDescription("ASOUND Dual Speed 10/100 Ethernet PCI Adapter");
//      item.setVendorItemNo("54320");
//      item.setItemSerialNo("LR9C2002269");
//      item.setQtyOnHand(150);
//      item.setUnitCost(4.25);
//      item.setMarkup(3);
//      item.setItemTypeId(2);
//      rc = itemApi.maintainItemMaster(item, null);
//
//      acctApi = AcctManagerFactory.createBasic(con);
//      acct = GlAccountsFactory.create();
//      acct.setAcctTypeId(4);
//      acct.setAcctCatId(401);
//      acct.setName("D-Link");
//      itemApi = InventoryFactory.createInventoryApi(con);
//      item = GlAccountsFactory.createItemMaster();
//      item.setVendorId(22012);
//      item.setDescription("D-Link Fast Ethernet 10/100 mbps Network Adapter");
//      item.setVendorItemNo("DFE-530TX");
//      item.setItemSerialNo("0206E1C71106");
//      item.setQtyOnHand(20);
//      item.setUnitCost(3.10);
//      item.setMarkup(3);
//      item.setItemTypeId(2);
//      rc = itemApi.maintainItemMaster(item, null);
//
//
//      itemApi = InventoryFactory.createInventoryApi(con);
//      item = GlAccountsFactory.createItemMaster();
//      item.setDescription("Professional Service");
//      item.setQtyOnHand(1);
//      item.setUnitCost(333.10);
//      item.setItemTypeId(1);
//      rc = itemApi.maintainItemMaster(item, null);
//
//      itemApi = InventoryFactory.createInventoryApi(con);
//      item = GlAccountsFactory.createItemMaster();
//      item.setDescription("New Clone Construction");
//      item.setQtyOnHand(1);
//      item.setUnitCost(5000.00);
//      item.setItemTypeId(1);
//      rc = itemApi.maintainItemMaster(item, null);
//
//      tranApi.commitTrans();
//    }
//    catch (ItemMasterException e) {
//      String ls_temp = e.getMessage();
//      System.out.println(e.getMessage());
//    }
//    catch (DatabaseException e) {
//      String ls_temp = e.getMessage();
//      System.out.println(e.getMessage());
//    }
//    catch (Exception e) {
//      String ls_temp = e.getMessage();
//      System.out.println(e.getMessage());
//    }
//  }
//
//
//  public void testStorProcException(RMT2DBConnectionBean con) throws SystemException {
//
//    RMT2DBTransApi tranApi = null;
//    String tmp = "This is a test %s for Roy Terrell.  %s is a required field";
////    tmp = RMT2Utility.replaceAll(tmp, "to determine the health", "%s");
//    try {
//      tranApi = RMT2DBTransFactory.create(con);
//      RMT2DynamicSqlApi  dynaApi = RMT2DynamicSQLFactory.create(con);
//      dynaApi.execute("test_proc_upd");
//    }
//    catch (DatabaseException e) {
//      String str = e.getMessage();
//      //ArrayList list = RMT2Utility.getTokens(e.getErrorMsg(), "]");
//      //throw new SystemException(e.getMessage(), e.getErrorCode(), "testapp", "testStorProcException");
//      ArrayList list = new ArrayList();
//      list.add("to determine the health");
//      list.add("Description");
//      SystemException t;
//      t = new SystemException(con, 1000, list);
//      str = t.getMessage();
//      throw t;
//    }
//
//
//  }
//
//
//
//  public void testAccounting(RMT2DBConnectionBean con) {
//
//    RMT2DBTransApi tranApi = null;
//    try {
//      tranApi = RMT2DBTransFactory.create(con);
//
//      int newId = 0;
//
//      GLCreditorApi credApi = AcctManagerFactory.createCreditor(con);
//      GLCustomerApi custApi = AcctManagerFactory.createCustomer(con);
//      GLBasicApi acctApi = AcctManagerFactory.createBasic(con);
//      InventoryApi itemApi = InventoryFactory.createInventoryApi(con);
//
//
//
//      newId = 0;
//      GlAccounts acct = new GlAccounts();
//      acct.setAcctTypeId(1);
//      acct.setAcctCatId(101);
//      acct.setName("Audio Video");
//      newId = acctApi.maintainAccount(acct);
//      tranApi.commitTrans();
//
//      Creditor cred;
//      cred = credApi.createCreditor();
//      cred.setGlAccountId(20);
//      cred.setBusinessId(2000003);
//      cred.setCreditorTypeId(1);
//      cred.setCreditLimit(67546545.99);
//      cred.setApr(8.5);
//      newId = credApi.maintainCreditor(cred, null);
//
//      cred = credApi.findCreditorById(22005);
//      cred.setCreditLimit(777777.77);
//      newId = credApi.maintainCreditor(cred, null);
//
//
//      acct = new GlAccounts();
//      acct.setAcctTypeId(6);
//      acct.setAcctCatId(601);
//      acct.setName("Sales Returns and Allowances");
//      newId = acctApi.maintainAccount(acct);
//
//      acct = new GlAccounts();
//      acct.setAcctTypeId(7);
//      acct.setAcctCatId(701);
//      acct.setName("Purchases Discounts");
//      newId = acctApi.maintainAccount(acct);
//
//      acct = new GlAccounts();
//      acct.setAcctTypeId(7);
//      acct.setAcctCatId(701);
//      acct.setName("Purchases Retruns and Allowances");
//      newId = acctApi.maintainAccount(acct);
//
//
//
//      ItemMaster item = itemApi.findItemById(5);
//      item.setDescription("Dell Inspiron 1100 AC Wall Adapter");
//      rc = itemApi.maintainItemMaster(item, null);
//
//
//             //  Create an Item Mater GL Account
//      acctApi = AcctManagerFactory.createBasic(con);
//      acct = GlAccountsFactory.create();
//      acct.setAcctTypeId(4);
//      acct.setAcctCatId(401);
//      acct.setName("Sprint 7300 PCS");
//
//
//      itemApi = InventoryFactory.createInventoryApi(con);
//      item = GlAccountsFactory.createItemMaster();
//      item.setVendorId(22012);
//      item.setDescription("Sa,sumg 8432 R-Link Two Way Cellular");
//      item.setVendorItemNo("5438");
//      item.setItemSerialNo("RTE84");
//      item.setQtyOnHand(670);
//      item.setUnitCost(46.);
//      item.setMarkup(3);
//         //  No need to set a value for id, gl_account_id, retail_price, and active since
//         //  they are primitive types and their values will be initialized automatically
//         //  upon object instantiation.
//
//
//      rc = itemApi.maintainItemMaster(item, null);
//
//      Customer cust = custApi.createCustomer();
//      cust.setGlAccountId(10);
//      cust.setPersonId(1000008);
//      cust.setBusinessId(2000008);
//      cust.setCreditLimit(55000000.00);
//      rc = custApi.maintainCustomer(cust, null);
//
////      cust = custApi.findCustomerById(1005);
////      cust.setCreditLimit(7777.77);
////      cust.setBusinessId(0);
////      rc = custApi.maintainCustomer(cust, null);
//
//
//
//      acctApi = AcctManagerFactory.createBasic(con);
//      acctApi.findById(10);
//      acctApi.deleteAccount(10);
//
//
//      GLCreditorApi cApi = AcctManagerFactory.createCreditor(con);
//      cApi.deleteCreditor(22012);
//
//      acct.setId(2);
//      acct.setName("Accounts Payable");
//      newId = acctApi.maintainAccount(acct);
//
//      GlAccountCategory catg = new GlAccountCategory();
//      catg.setAcctTypeId(3);
//      catg.setDescription("Test Category 6");
//      newId = acctApi.maintainAcctCatg(catg);
//
//      catg.setId(3);
//      catg.setDescription("I changed the value");
//      acctApi.maintainAcctCatg(catg);
//
//      tranApi.commitTrans();
//    }
//    catch (GLAcctException e) {
//      String ls_temp = e.getMessage();
//      System.out.println(e.getMessage());
//    }
//    catch (CustomerException e) {
//      String ls_temp = e.getMessage();
//      System.out.println(e.getMessage());
//    }
//    catch (ItemMasterException e) {
//      String ls_temp = e.getMessage();
//      System.out.println(e.getMessage());
//    }
//    catch (CreditorException e) {
//      String ls_temp = e.getMessage();
//      System.out.println(e.getMessage());
//    }
//    catch (DatabaseException e) {
//      String ls_temp = e.getMessage();
//      System.out.println(e.getMessage());
//    }
//    catch (Exception e) {
//      String ls_temp = e.getMessage();
//      System.out.println(e.getMessage());
//    }
//  }
//
//
///*
//  public void testTransactions(RMT2DBConnectionBean con) {
//
//      RMT2DBTransApi tranApi = null;
//      XactManagerApi xactApi;
//      XactXlatBean target;
//      XactXlatBean offset;
//      Xact xact;
//      XactPostDetails xactDetails;
//      ArrayList list;
//
//
//
//      try {
//        tranApi = RMT2DBTransFactory.create(con);
//
//        xactApi = XactManagerApiFactory.create(con, 11, 500.05);
//
//        xactApi.createCustomerActivity(1009, 103, 2224.99);
//
//            //  Test findxxx methods.
//        xact = xactApi.findXactById(8);
//        list = xactApi.findXactDetByXactId(8);
//        for (int x = 0; x < list.size(); x++) {
//          xactDetails = (XactPostDetails) list.get(x);
//          double amt = xactDetails.getPostAmount();
//        }
//
//        target = XactFactory.createXactXlat(10, 500.05);
//        xactApi.setTargetEntry(target);
//        offset = XactFactory.createXactXlat(37, 100.01);
//        xactApi.setOffsetEntry(offset);
//        offset = XactFactory.createXactXlat(38, 100.01);
//        xactApi.setOffsetEntry(offset);
//        offset = XactFactory.createXactXlat(41, 100.01);
//        xactApi.setOffsetEntry(offset);
//        offset = XactFactory.createXactXlat(42, 100.01);
//        xactApi.setOffsetEntry(offset);
//        offset = XactFactory.createXactXlat(43, 100.01);
//        xactApi.setOffsetEntry(offset);
//
//
//        xactApi.createBaseXact();
//
//        tranApi.commitTrans();
//      }
//      catch (XactException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//      catch (DatabaseException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//      catch (SystemException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//      catch (Exception e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//      }
//
//  }
//*/
//
//
//
//
//
//
//
//
//
//
//
  //Main method
  public static void main(String[] args) {
    testapp test = new testapp();
  }
//
//
//
//  public void DatasourceTest(RMT2DBConnectionBean con) {
//
//     try {
//     DataSourceApi api = RMT2DataSourceFactory.create(con, "TestView");
//     api.executeQuery();
//     }
//     catch (DatabaseException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//     }
//     catch (SystemException e) {
//        String ls_temp = e.getMessage();
//        System.out.println(e.getMessage());
//     }
//
//
//    /*
////    String sql = "select id, quote_id, client_order_id, flat_rate from orders where id = 1355";
//    String sql = "select id, firstname, midname, lastname, shortname from personal";
//
//    try {
//        // Test XML Parser
//      RMT2DatabaseObjectMapperAttrib dsrc = new RMT2DatabaseObjectMapperAttrib();
//      RMT2DatasourceParser doc = new RMT2DatasourceParser("OrdersDataSource", 1, test.prop, dsrc);
//      doc.parseDocument();
//      String dsName = dsrc.getName();
//      Hashtable sqlObj = dsrc.getSelectStatement();
//      Enumeration sqlKeys = sqlObj.keys();
//      String sqlStatement = null;
//      Integer keyValue = null;
//      while (sqlKeys.hasMoreElements()) {
//        String sqlClause = null;
//        keyValue = (Integer) sqlKeys.nextElement();
//        sqlClause = (String) sqlObj.get(keyValue);
//        sqlStatement += sqlClause;
//      }
//
//      Hashtable colDefObj = dsrc.getColumnDef();
//      Enumeration colKeys = colDefObj.keys();
//      RMT2DataSourceColumn def = new RMT2DataSourceColumn();
//      while (colKeys.hasMoreElements()) {
//        String colValue = (String) colKeys.nextElement();
//        def = (RMT2DataSourceColumn) colDefObj.get(colValue);
//        String nameStr = def.getDbName();
//        nameStr = nameStr;
//      }
//
//
////      RMT2TagQueryBean queryData = new RMT2TagQueryBean("PersonalView",  //"select id, firstname, midname, lastname, shortname from personal where id > 1000300",
//      RMT2TagQueryBean queryData = new RMT2TagQueryBean("GeneralCodesGroupView",
////       RMT2TagQueryBean queryData = new RMT2TagQueryBean("UserLocationView",
//                                                        "xml",
//                                                        null,
//                                                        null);
//
//      RMT2DataSourceApiImpl ds = new RMT2DataSourceApiImpl(test.con, "appdev", queryData, test.prop);
//      long pkey = ds.getNextSequence("general_codes_group", "group_id");
////      ds.setDatasourceSql(RMT2SqlConst.WHERE_KEY, "id = 1000002");
////      ds.setDatasourceSql(RMT2SqlConst.ORDERBY_KEY, "lastname asc");
//      ds.executeQuery(false, true);
//      String xId = null;
//      try {
//        String temp = null;
////        ds.lastRow();
//        int li_row = ds.getRs().getRow();
//  //      ds.firstRow();
////        temp = ds.getColumnValue("Description");
//        ds.nextRow();
//        temp = ds.getColumnValue("Description");
////        ds.previousRow();
////        temp = ds.getColumnValue("Description");
//        ds.setColumnValue("Description", "Test SQL Server");
//        ds.updateRow();
//        ds.commitTrans();
////        ds.updateRow();
//
//          // Test Deleting Row
//        RMT2DataSourceApiImpl ds2 = new RMT2DataSourceApiImpl(test.con, "appdev", queryData, test.prop);
//        ds2.setDatasourceSql(RMT2SqlConst.WHERE_KEY, "id >= 1000700");
//        ds2.executeQuery(false, true);
//        while (ds2.nextRow()) {
//          ds2.deleteRow();
//        }
//        ds2.commitTrans();
//
//          // Test Inserting Row
//        ds2.createRow();
//        ds2.setColumnValue("Id", new Integer(1000700));
//        ds2.setColumnValue("FirstName", "Billy");
//        ds2.setColumnValue("LastName", "Cobham");
//        ds2.setColumnValue("ShortName", "Cobham, Billy");
//        ds2.setColumnValue("CreateDate", "2002-01-01");
//        ds2.insertRow();
//
//          // Try setting the second insert row
//        ds2.createRow();
//        ds2.setColumnValue("Id", new Integer(1000701));
//        ds2.setColumnValue("FirstName", "Dennis");
//        ds2.setColumnValue("LastName", "Chambers");
//        ds2.setColumnValue("ShortName", "Chambers, Dennis");
//        ds2.setColumnValue("CreateDate", "2002-01-02");
//        ds2.insertRow();
//
//        ds2.commitTrans();
//
//        String idx = ds2.getColumnValue("Id");
//
//        xId = (String) ds.getDataSource().getColumnAttribute("Id", "dataValue");
//      }
//      catch (Exception e) {
//        System.out.println(e.getMessage());
//      }
//      String xdate = ds.getColumnValue("CreateDate");
//      try {
//           // Test Date formatting
//        SimpleDateFormat sdate = new SimpleDateFormat("MMMM dd, yyyy");
//        SimpleDateFormat sdate2 = new SimpleDateFormat("yyyy-MM-dd");
//        // Parse the previous string back into a Date.
//        ParsePosition pos = new ParsePosition(0);
//        java.util.Date date2 = sdate2.parse("1966-05-17");
//        xdate = sdate.format(date2);
//
//        date2 = RMT2Utility.stringToDate("1966-05-17");
//        xdate = RMT2Utility.formatDate(date2, "MM/dd/yyyy");
//
//        date2 = RMT2Utility.stringToDate("Jan 17, 1966");
//        xdate = date2.toString();
//        date2 = RMT2Utility.stringToDate("1966-JAN-17");
//        xdate = date2.toString();
//        date2 = RMT2Utility.stringToDate("1966-05-17");
//        xdate = date2.toString();
//        date2 = RMT2Utility.stringToDate("05-17-1966");
//        xdate = date2.toString();
//        date2 = RMT2Utility.stringToDate("1966/05/17");
//        xdate = date2.toString();
//        date2 = RMT2Utility.stringToDate("05/17/1996");
//        xdate = date2.toString();
//
//           // Test Decimal Formatting
//        DecimalFormat decFormatter = new DecimalFormat("#,##0.00;(#,##0.00)");
//        String strDec = decFormatter.format(123456.94);
//        Number dec = decFormatter.parse(strDec);
//        DecimalFormat decFormatter2 = new DecimalFormat();
//        dec = decFormatter2.parse(strDec);
//        double num = dec.doubleValue();
//
//        dec = RMT2Utility.stringToNumber("$196,605.17");
//        num = dec.doubleValue();
//        dec = RMT2Utility.stringToNumber("19%");
//        num = dec.doubleValue();
//        dec = RMT2Utility.stringToNumber("19.09");
//        num = dec.doubleValue();
//        dec = RMT2Utility.stringToNumber("(1909)");
//        num = dec.doubleValue();
//        dec = RMT2Utility.stringToNumber("-19.09");
//        num = dec.doubleValue();
//        num = num;
//
//        DecimalFormat decFormatter3 = new DecimalFormat();
//        dec = decFormatter3.parse("12%");
//        num = dec.doubleValue();
//        num = num;
//
//
//      }
//      catch (Exception e) {
//        System.out.println(e.getMessage());
//      }
//
//        // Test setting datasource's column values
//      ds.setColumnValue("FirstName", "Roy");
//      ds.setColumnValue("CreateDate", "1966/05/17");
//      ds.setColumnValue("LastName", "Terrell");
//      ds.updateRow();
//      ds.commitTrans();
//
//      String xname = (String) ds.getDataSource().getColumnAttribute("firstname", "DataValue");
//      Hashtable hash = ds.getDataSource().getTables();
//      Enumeration enum = hash.keys();
//      RMT2TableUsageBean dsTable = null;
//      while (enum.hasMoreElements()) {
//        xname =  enum.nextElement().toString();
//      }
//      enum = hash.keys();
//      for (int ndx = 1; ndx <= hash.size(); ndx++) {
//        dsTable = (RMT2TableUsageBean) hash.get(new Integer(ndx));
//        xname = dsTable.getName();
//        xname = dsTable.getDbName();
//        boolean bname = dsTable.isUpdateable();
//      }
//
//      Integer i = (Integer) ds.getDataSource().getColumnAttribute("id", "javaType");
//      i = (Integer) ds.getDataSource().getColumnAttribute("firstname", "javaType");
//      String s = (String) ds.getDataSource().getColumnAttribute("firstname", "dbName");
//
////      sql = ds.assembleSql();
////      System.out.println("SQL: " + sql);
////      ds.parseSqlDataSource(sql, "sql");
//      Hashtable cols = ds.getDataSource().getColumnDef();
//      Enumeration keys = cols.keys();
//      int count = 0;
//      while (keys.hasMoreElements()) {
//        String key = (String) keys.nextElement();
//        RMT2DataSourceColumn obj = (RMT2DataSourceColumn) cols.get(key);
//        System.out.println(obj.getName());
//        count++;
//      }
//      System.out.println("Count: " + count);
//
//
//    }
//
//    catch (NoSuchElementException e) {
//      System.out.println(e.getMessage());
//    }
//    catch (NotFoundException e) {
//      System.out.println(e.getMessage());
//    }
//    catch (DatabaseException e) {
//      System.out.println(e.getMessage());
//    }
//
//    catch (SystemException e) {
//      System.out.println(e.getMessage());
//    }
//*/
//  }
//
//
//  public void generalTest() {
///*
////      String driverName = "com.ddtek.jdbc.sqlserver.SQLServerDriver";        //prop.getString("dbdriver");
////      String url = "dbc:datadirect:sqlserver://rmtdallap01:1433;DatabaseName=EGFHDB01";  //prop.getString("dburl");;
////      String userid = prop.getString("userid");
////      String password = prop.getString("password");
////      dbParms.setProperty("user", "sa");  //userid);
////      dbParms.setProperty("password", password);
////      dbParms.setProperty("database", "HomeDB");
////      String         url = "jdbc:sybase:Tds:RMTDALDB01:2638?SERVICENAME=homesyst";
////      Driver         dbDriver = (Driver)Class.forName(driverName).newInstance();
////      this.con = dbDriver.connect(url, dbParms);
//
//
//
//       String userid;
//       String password;
//       String database;
//       String driver;
//       String url;
//       Properties     dbParms = new Properties();
//      prop = ResourceBundle.getBundle("SystemParms");
//      userid = prop.getString("userid");
//      password = prop.getString("password");
//      database = prop.getString("database");
//      driver = prop.getString("dbdriver");
//      url = prop.getString("dburl");
//      dbParms.setProperty("user", userid);
//      dbParms.setProperty("password", password);
//      dbParms.setProperty("database", "CMSDB01");
//      Driver dbDriver = (Driver)Class.forName(driver).newInstance();
//      this.con = dbDriver.connect(url, dbParms);
//
//
//
////      RMT2DBConnectionApiImpl  test  = new RMT2DBConnectionApiImpl();
////      test.initConnections();
////      RMT2DBConnectionBean con = test.getDBConnection();
//
////      RMT2WebMenus menu1 = new RMT2WebMenus();
//  //    menu1.createMenu("TestMenu", "c:\\testdoc.xml");
////      RMT2DataSourceApi dso = con.getDataObject("UserLoginView");   // RMT2DataSourceApiImpl(con.getDbConn(), "admin");
////      dso.executeQuery();
////      RMT2WebDbMenu menu = new RMT2WebDbMenu(dso, "Description", "test.jsp", "Id");
////      String strMenu = menu.createMenu("TestMenu");
//
////      GlAccounts acct = imp.createGlAccount();
////      imp.packageBean(dso, acct);
//
//
//
//
//
//      RMT2SessionBean bean = new RMT2SessionBean();
////      bean.setServerName("Roy Terrell");
//      RMT2BeanUtility beanSpy = new RMT2BeanUtility(bean);
//      Object obj1 = beanSpy.setPropertyValue("serverPort", "1433");
//      String str1 = beanSpy.getPropertyValue("serverPort").toString();
//      RMT2SessionBean obj12 = (RMT2SessionBean)beanSpy.createInstance();
//      int str3 = obj12.getServerPort();
//      RMT2SessionBean bean2 = (RMT2SessionBean)Beans.instantiate(null, "com.bean.RMT2SessionBean");
//      bean2.setServerName("Roy Terrell");
//      String str2 = bean2.getServerName();
//
//      DatabaseMetaData dbmd = con.getDbConn().getMetaData();
//      boolean b_result = dbmd.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY);
//      b_result = dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
//      b_result = dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
//      b_result =  dbmd.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY,
//                                                    ResultSet.CONCUR_READ_ONLY) ;
//      b_result =  dbmd.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY,
//                                                    ResultSet.CONCUR_UPDATABLE) ;
//      b_result =  dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE,
//                                                    ResultSet.CONCUR_READ_ONLY) ;
//      b_result =  dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE,
//                                                    ResultSet.CONCUR_UPDATABLE) ;
//      b_result =  dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE,
//                                                    ResultSet.CONCUR_READ_ONLY) ;
//      b_result =  dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE,
//                                                    ResultSet.CONCUR_UPDATABLE) ;
//      prop = ResourceBundle.getBundle("com.properties.SystemParms");
//    }
//
//    catch (SQLException e) {
//      System.out.println(e.getMessage());
//    }
////    catch (ClassNotFoundException e) {
////      System.out.println(e.getMessage());
////    }
//
//    catch (DatabaseException e) {
//      String ls_temp = e.getMessage();
//      System.out.println(e.getMessage());
//    }
//
//    catch (Exception e) {
//      String ls_temp = e.getMessage();
//      System.out.println(e.getMessage());
//    }
//*/
//  }

}
