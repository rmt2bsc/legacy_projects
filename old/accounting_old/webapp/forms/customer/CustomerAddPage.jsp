<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.gl.customer.CustomerApi" %>
<%@ page import="com.gl.BasicGLApi" %>


<gen:InitAppRoot id="APP_ROOT"/>

<jsp:useBean id="QUERY_BEAN" scope="session" class="com.bean.RMT2TagQueryBean"/>

	
<html>
		<head>
			<title>General Ledger Customer Maintenance Edit</title>
			<meta http-equiv="Pragma" content="no-cache">    
			<meta http-equiv="Expires" content="-1">
			<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
			<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
  		<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	  	<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
		</head>
		


    <%
		  String pageTitle = "Customer Maintenance";
      //String xpathQuery = "//CustomerExt";
    %>
       
		<body bgcolor="#FFFFFF" text="#000000">
			<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/Customer.Edit">
			   <%@include file="CustomerAdd.jsp"%>
			   
			   <!-- Display business contact data for customer -->			
		     <%@include file="../includes/BusinessContactAddSection.jsp"%>
		     <br>
		     
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
					 <td><input type="button" name="<%=GeneralConst.REQ_SAVE %>" value="Save" style="width:90" tabIndex="30" onClick="handleAction('_self', DataForm, this.name)"></td>
					 <td><input type="button" name="<%=GeneralConst.REQ_BACK %>" value="Back" style="width:90" tabIndex="31" onClick="handleAction('_self', DataForm, this.name)"></td>
				</tr>
			</table>
			<input name="clientAction" type="hidden">
		</form>
   </body>
</html>
