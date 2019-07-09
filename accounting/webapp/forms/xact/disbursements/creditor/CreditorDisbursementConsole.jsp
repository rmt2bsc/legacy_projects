<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.xact.XactConst" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.gl.BasicGLApi" %>

<gen:InitAppRoot id="APP_ROOT"/>

<jsp:useBean id="QUERY_BEAN" scope="session" class="com.bean.RMT2TagQueryBean"/>

	
<html>
		<head>
			<title>Creditor/Vendor Cash Disbursements Console</title>
			<meta http-equiv="Pragma" content="no-cache">
			<meta http-equiv="Expires" content="-1">
			<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
			<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
			<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    	<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
		</head>

    <%
       String pageTitle = "Creditor/Vendor Cash Disbursements Console";
    	 String xpathQuery = "//CreditorExt";
    %>
		<body bgcolor="#FFFFFF" text="#000000">
			<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/DisbursementsCreditor.Console">
			   <%@include file="/forms/creditor/CreditorView.jsp"%>
			   <br>
			   
			   <!-- Display business contact data for customer -->			
		     <%@include file="../../../includes/BusinessContactViewSection.jsp"%>
		     <br>			
		   
			   <table>
				  <tr>
					 <td>&nbsp;</td>
					 <td>&nbsp;</td>
					 <td>&nbsp;</td>
				</tr>
				<tr>
	 				 <td><input type="button" name="<%=XactConst.REQ_PAYMENT%>" value="Make Payment" style="width:120"  onClick="handleAction('_self', document.DataForm, this.name)"></td>
					 <td><input type="button" name="<%=XactConst.REQ_TRANSACTIONS%>" value="Transactions" style="width:120"  onClick="handleAction('_self', document.DataForm, this.name)"></td>
					 <td><input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)"></td>
				</tr>
			</table>
			<input name="clientAction" type="hidden">
		</form>
   </body>
</html>
