<%@ taglib uri="/rmt2-securitytaglib" prefix="auth" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.api.security.authentication.AuthenticationConst" %>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.api.security.authentication.RMT2SessionBean" %>

<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<html>
  <head>
    <title>Accounting</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <%
   String pageTitle = "Accounting and Financial Transaction Application";
  %>
  <body>
  	  <h1><%=pageTitle%></h1> 
  	  
  	  <h2 sytle="color:blue">Main Menu</h2>
		  <br><br>
		  <table width="50%" border="1" cellpadding="0" cellspacing="0">
		    <tr> 
		      <td>
		      <table width="100%" border="0">
		         <caption align="left">Code Tables</caption>
		         <tr>
					      <td width="40%" valign="top" align="center"> 
					          <a href="/accounting/unsecureRequestProcessor/AcctCatgConsole.AcctTypeCatg?clientAction=refresh&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
					            Accounting Category Maintenance
					          </a>
					      </td>
					      <td width="40%"  valign="top" align="center"> 
					          <a href="/accounting/unsecureRequestProcessor/AccountsConsole.Accounts?clientAction=refresh&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
					            Account Maintenance
					          </a>
					      </td>	  			      
					      <td width="20%"  valign="top" align="center">&nbsp;</td>	  
		        </tr>
		      </table>
		      </td>
		    </tr>  
		  </table>
      <br>		  
      
		  <table width="50%" border="1" cellpadding="0" cellspacing="0">
		    <tr> 
		      <td>
		      <table width="100%" border="0">
		      <caption align="left">Entities</caption>
		         <tr>
					      <td width="25%" valign="top" align="center"> 
					          <a href="/accounting/unsecureRequestProcessor/Creditor.Search?clientAction=newsearch&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
					            Creditor Maintenance
					          </a>
					      </td>
					      <td width="25%"  valign="top" align="center"> 
					          <a href="/accounting/unsecureRequestProcessor/Customer.Search?clientAction=newsearch&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
					            Customer Maintenance
					          </a>
					      </td>	  			      
					      <td width="25%"  valign="top" align="center"> 
					          <a href="/accounting/unsecureRequestProcessor/Inventory.Search?clientAction=newsearch&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
					            Inventory
					          </a>
					      </td>	  			      					      
					      <td width="25%"  valign="top" align="center">&nbsp;</td>	  
		        </tr>
		      </table>
		      </td>
		    </tr>  		    
		 </table>
		 <br>
		    
		 <table width="70%" border="1" cellpadding="0" cellspacing="0">
		    <tr> 
		      <td>
		      <table width="100%" border="0">
		      <caption align="left">Transactions</caption>
		         <tr>
					      <td width="10%" valign="top" align="center"> 
					          <a href="/accounting/unsecureRequestProcessor/SalesCustomerSearch.Search?clientAction=newsearch&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
					            Sales
					          </a>
					      </td>
					      <td width="10%"  valign="top" align="center"> 
					          <a href="/accounting/unsecureRequestProcessor/DisbursementsGeneral.Search?clientAction=newsearch&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
					            Cash Disbursements
					          </a>
					      </td>	  					      					      
					      <td width="15%"  valign="top" align="center"> 
					          <a href="/accounting/unsecureRequestProcessor/DisbursementsCreditor.Search?clientAction=newsearch&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
					             Creditor Disbursements
					          </a>
					      </td>	  		
					      <td width="25%"  valign="top" align="center"> 
					          <a href="/accounting/unsecureRequestProcessor/PurchasesCreditor.Search?clientAction=newsearch&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
					            General Credit Purchases
					          </a>
					      </td>	  
					      <td width="20%"  valign="top" align="center"> 
					          <a href="/accounting/unsecureRequestProcessor/PurchasesVendor.Search?clientAction=newsearch&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
					            Merchandise Purchases (Vendor)
					          </a>
					      </td>
 					      <td width="20%"  valign="top" align="center"> 
					          <a href="/accounting/unsecureRequestProcessor/GenericTransaction.Search?clientAction=newsearch&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
					            Consolidated Transactions)
					          </a>
					      </td>	  					      
		        </tr>
		      </table>
		      </td>
		    </tr>  		    		    
		  </table>
		  
		  <table width="70%" border="1" cellpadding="0" cellspacing="0">
		    <tr> 
		      <td>
		      <table width="100%" border="0">
		      <caption align="left">Batch Processing</caption>
		         <tr>
					      <td width="10%" valign="top" align="left"> 
					          <a href="/accounting/unsecureRequestProcessor/Security.Authentication?clientAction=logoff&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
					            Log Off
					          </a>
					      </td>					      
		        </tr>
		        <%-- 
		        <tr>
					      <td width="10%" valign="top" align="left"> 
					          <a href="<%=APP_ROOT%>/DocumentViewer.jsp?contentId=218" target="_blank"> 
					            Test Document Viewer
					          </a>
					      </td>					      
		        </tr>
		        --  %>
		      </table>
		      </td>
		    </tr>  		    		    
		  </table>
		  <!-- 
		     <iframe src="file:\\rmtdaldev04\data\RESUME_PB_J.doc" width="80%" height="500"></iframe>
		  -->
		  <%--
		  <iframe src="<%=APP_ROOT%>/mimeServlet?clientAction=fetch&contentId=218" width="80%" height="500"></iframe>
		  --%> 
		  
  </body>
</html>
