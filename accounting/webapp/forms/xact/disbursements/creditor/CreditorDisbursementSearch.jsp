<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.criteria.CreditorCriteria" %>
<%@ page import="com.constants.GeneralConst" %>


<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Creditor/Vendor Disbursements Search</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	  <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
  </head>

  <%
	String pageTitle = "Creditor/Vendor Disbursements Search";
  %>
				      
  <body bgcolor="#FFFFFF" text="#000000">
     <h3><%=pageTitle%></h3>
 	 <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/DisbursementsCreditor.Search">
	 <%@include file="/forms/creditor/CreditorSearchCriteriaAndList.jsp"%>
   <br>
	 <!-- Display command buttons -->         
	 <table width="100%" cellpadding="0" cellspacing="0">
		 <tr>
			<td colspan="2">
			  <input name="search" type="button" value="Search" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
			  <input name="reset" type="reset" value="Clear" style="width:90">
			  <input type="button" name="edit" value="Disbursement Console" style="width:150" onClick="handleAction('_self', document.SearchForm, this.name)">
			  <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
			</td>
 	     </tr>		
	 </table>
	 <input name="clientAction" type="hidden">
	 </form>
 </body>
</html>
