<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ page import="com.bean.Creditor" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.gl.creditor.CreditorApi" %>
<%@ page import="com.gl.BasicGLApi" %>


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
		  String pageTitle = "Creditor/Vendor Maintenance";
		  Creditor cred = (Creditor) request.getAttribute(CreditorApi.CLIENT_DATA_CREDITOR);
		  String xpathQuery = "//CreditorExt";
       %>
       
		<body bgcolor="#FFFFFF" text="#000000">
			<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/Creditor.Edit">
			   <%@include file="CreditorEdit.jsp"%>
			   <br>
			   
			   <!--  Display Business contact data for creditor -->
			   <%@include file="../includes/BusinessContactEditSection.jsp"%>
			   <br>
			
			   <!--  Display any server messages -->		
			   <table width="90%" border="0">
				 <tr>
					<td colspan="4" class="clsErrorText">
					   <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>  
					</td>
				 </tr>
			   </table>
			   
			   <table>
				  <tr>
					 <td><img src="/accounting/images/clr.gif" height="10"></td>
					 <td><img src="/accounting/images/clr.gif" height="10"></td>
					 <td><img src="/accounting/images/clr.gif" height="10"></td>
				</tr>
				<tr>
					 <td><input type="button" name="<%=GeneralConst.REQ_SAVE %>" value="Save" style="width:90"  tabIndex="30"  onClick="handleAction('_self', DataForm, this.name)"></td>
					 <td><input type="button" name="<%=GeneralConst.REQ_BACK %>" value="Back" style="width:90" tabIndex="31" onClick="handleAction('_self', DataForm, this.name)"></td>
				</tr>
			</table>
			<input name="clientAction" type="hidden">
		</form>
   </body>
   <db:Dispose/>
</html>
