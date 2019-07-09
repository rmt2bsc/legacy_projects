<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Cash Disbursement/Purchases main Menu</title>
	<link rel=STYLESHEET type="text/css" href="/css/RMT2General.css">   
  </head>

     <%
		 String pageTitle = request.getParameter("title")  == null ? "Choose One" :  (String) request.getParameter("title") ;
 		 String xactType =  (String) request.getParameter("xactType") ;
    %>
	
  <body>
		<table width="60%" border="0">
		<caption align="left"><h3><%=pageTitle%></h3></caption>
			<tr> 
				<td width="33%"><a href="<%=APP_ROOT%>/reqeustProcessor/XactPurchase.VendorSearch?clientAction=newsearch" target="windowFrame">Vendor Purchase Orders</a></td>
			</tr>    
			<tr> 
				<td width="33%"> <a href="<%=APP_ROOT%>/reqeustProcessor/XactPurchase.CreditorSearch?clientAction=newsearch" target="windowFrame">Expense Credit Purchases</a></td>
			</tr>    			
		</table>
 </body>
</html>
