<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.Address" %>
<%@ page import="com.bean.Business" %>
<%@ page import="com.bean.Creditor" %>
<%@ page import="com.bean.Zipcode" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.AccountingConst" %>
<%@ page import="com.constants.XactConst" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<jsp:useBean id="QUERY_BEAN" scope="session" class="com.bean.RMT2TagQueryBean"/>

	
<html>
		<head>
			<title>General Ledger Creditor Maintenance Edit</title>
			<meta http-equiv="Pragma" content="no-cache">
			<meta http-equiv="Expires" content="-1">
			<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
			<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
			<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    	<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
		</head>

    <%
       String pageTitle = "Creditor/Vendor Cash Disbursements";
    %>
		<body bgcolor="#FFFFFF" text="#000000">
			<form name="DataForm" method="post" action="<%=APP_ROOT%>/reqeustProcessor/XactDisburse.DisbursementCreditorConsole">
			   <%@include file="/forms/accounting/creditor/CreditorView.jsp"%>
			   <table>
				  <tr>
					 <td><img src="images/clr.gif" height="10"></td>
					 <td><img src="images/clr.gif" height="10"></td>
					 <td><img src="images/clr.gif" height="10"></td>
				</tr>
				<tr>
	 				 <td><input type="button" name="<%=XactConst.REQ_PAYMENT%>" value="Make Payment" style="width:120"  onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)"></td>
					 <td><input type="button" name="<%=XactConst.REQ_TRANSACTIONS%>" value="Transactions" style="width:120"  onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)"></td>
					 <td><input type="button" name="<%=XactConst.REQ_VENDORITEMS%>" value="Inventory Items" style="width:120" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)"></td>
					 <td><input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)"></td>
				</tr>
			</table>
			<input name="clientAction" type="hidden">
		</form>
		<db:Dispose/>
   </body>
</html>
