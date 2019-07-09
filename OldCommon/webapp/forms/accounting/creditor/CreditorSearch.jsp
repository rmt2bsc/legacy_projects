<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.XactQuery" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.bean.criteria.CreditorCriteria" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Customer Search Criteria</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	  <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Contact.js"></script>
  </head>

  <%
	String pageTitle = "Creditor/Vendor Search";
  %>
				      
  <body bgcolor="#FFFFFF" text="#000000" onLoad="initForm()">
     <h3><%=pageTitle%></h3>
 	 <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/Accounting.CreditorSearch">
	 <%@include file="/forms/accounting/creditor/CreditorSearchCriteriaAndList.jsp"%>

	 <!-- Display command buttons -->         
	 <table width="100%" cellpadding="0" cellspacing="0">
		 <tr>
			<td colspan="2">
			  <input name="<%=GeneralConst.REQ_SEARCH %>" type="button" value="Search" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
			  <input name="reset" type="reset" value="Clear" style="width:90">
			  <input type="button" name="<%=GeneralConst.REQ_EDIT %>" value="Edit" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
        <input type="button" name="<%=GeneralConst.REQ_ADD %>" value="Add" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
			</td>
 	     </tr>		
	 </table>
	 <input name="clientAction" type="hidden">
	 </form>
 </body>
</html>
