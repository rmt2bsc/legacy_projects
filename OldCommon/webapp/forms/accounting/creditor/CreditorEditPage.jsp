<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.Address" %>
<%@ page import="com.bean.Business" %>
<%@ page import="com.bean.Creditor" %>
<%@ page import="com.bean.Zipcode" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.AccountingConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<jsp:useBean id="QUERY_BEAN" scope="session" class="com.bean.RMT2TagQueryBean"/>

	
<html>
		<head>
			<title>General Ledger Creditor Maintenance Edit</title>
			<meta http-equiv="Pragma" content="no-cache">
			<meta http-equiv="Expires" content="-1">
			<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
			<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
		</head>
		
		<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
		<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
		<script>												  
			function handleRequest(_srcObj, _actionName) {
			     // Change the controller that is to handle this request.
			    this.document.DataForm.action = "<%=APP_ROOT%>" + _actionName;
			    // Submit page
			    handleAction(MAIN_DETAIL_FRAME, this.document.DataForm, _srcObj);
			}
       </script>

       <%
		  String pageTitle = "Creditor/Vendor Maintenance";
       %>
 
		<body bgcolor="#FFFFFF" text="#000000">
			<form name="DataForm" method="post" action="<%=APP_ROOT%>/reqeustProcessor/Accounting.CreditorEdit">
			   <%@include file="CreditorEdit.jsp"%>
			   <table>
				  <tr>
					 <td><img src="images/clr.gif" height="10"></td>
					 <td><img src="images/clr.gif" height="10"></td>
					 <td><img src="images/clr.gif" height="10"></td>
				</tr>
				<tr>
					 <td><input type="button" name="<%=GeneralConst.REQ_SAVE %>" value="Save" style="width:90"  onClick="handleAction(MAIN_DETAIL_FRAME, DataForm, this.name)"></td>
					 <td><input type="button" name="<%=GeneralConst.REQ_BACK %>" value="Back" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, DataForm, this.name)"></td>
				</tr>
			</table>
			<input name="clientAction" type="hidden">
		</form>
   </body>
   <db:Dispose/>
</html>
