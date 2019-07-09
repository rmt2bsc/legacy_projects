<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Cash Disbursement/Purchases main Menu</title>
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
  </head>

     <%
		 String pageTitle = request.getParameter("title")  == null ? "Choose One" :  (String) request.getParameter("title") ;
 		 String xactType =  (String) request.getParameter("xactType") ;
    %>
	
  <body>
		<table width="30%" border="0">
		<caption><h3><%=pageTitle%></h3></caption>
			<tr> 
				<td width="33%"><a href="<%=APP_ROOT%>/reqeustProcessor/XactDisburse.DisbursementSearch?clientAction=newsearch" target="windowFrame">Manage General Disbursements</a></td>
			</tr>    
			<tr> 
				<td width="33%"><a href="<%=APP_ROOT%>/reqeustProcessor/XactDisburse.DisbursementCreditorSearch?clientAction=newsearch" target="windowFrame">Manage Creditor/Vendor Disbursements</a></td>
			</tr>    			
		</table>
 </body>
</html>
