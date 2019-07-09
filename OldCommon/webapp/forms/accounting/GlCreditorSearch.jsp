<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.bean.XactQuery" %>
<%@ page import="com.bean.CreditorCombine" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<jsp:useBean id="QUERY_BEAN" scope="session" class="com.bean.RMT2TagQueryBean"/>

<html>
  <head>
    <title>Creditor Search Criteria</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
  </head>

     <%
		 CreditorCombine cc = (CreditorCombine) QUERY_BEAN.getCustomObj();
		 request.setAttribute("SearchCriteria", cc);
         int creditorTypeId = cc.getCreditorTypeId();
		 String creditorTypeStr = String.valueOf(creditorTypeId);
		 String pageTitle = "Creditor/Vendor Search";
 		 String requestType =  request.getParameter("requestType") ;
 		 if (requestType.equals("xact")) {
		    pageTitle = "Creditor/Vendor Payment Search";
		 }
    %>
	
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
     
  <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
  <db:datasource id="creditorTypesDso" 
                          classId="com.api.DataSourceApi" 
                          connection="con"
						  query="CreditorTypeView"
						  type="xml"/>    
  <body bgcolor="#FFFFFF" text="#000000" onLoad="initPage()">
       <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/glcreditorservlet">
		 
	     <h3><strong><%=pageTitle%></strong></h3>
	  
		<table width="30%" border="0">
			<tr> 
				<td width="33%" bgcolor="#FFCC00"> 
					<div align="right"><font size="2"><b>Creditor Type:</b></font></div>
				</td>
				<td width="67%">
					<db:InputControl dataSource="creditorTypesDso" 
					                          type="select" 
											  name="CreditorTypeId" 
											  codeProperty="Id" 
											  displayProperty="Description" 
											  selectedValue="<%=creditorTypeStr%>"/>

				</td>
			</tr>    
		</table>
		<br>
	  
		<table width="30%" border="0">
			<tr> 
				<td width="10%" bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Account Number :</font></b></div>
				</td>
				<td width="21%"> 
				  <beanlib:InputControl dataSource="SearchCriteria" type="text" name="AccountNo" property="AccountNo" readOnly="no"/>
				</td>
			</tr>
		</table>
		<br>
	  

		<table width="90%" border="0">
			<tr> 
				<td width="10%" bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Name:</font></b></div>
				</td>
				<td width="21%"> 
					<div align="left"> 
					  <beanlib:InputControl dataSource="SearchCriteria" type="text" name="BusinessLongname" property="BusinessLongname" readOnly="no"/>
					</div>
				</td>
				<td width="12%" bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Tax Id:</font></b></div>
				</td>
				<td width="24%"> 
					<div align="left"> 
						<beanlib:InputControl dataSource="SearchCriteria" type="text" name="BusinessTaxId" property="BusinessTaxId" readOnly="no"/>
					</div>
				</td>
				<td width="12%" bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Web Site:</font></b></div>
				</td>
				<td width="21%"> 
					<div align="left"> 
						<beanlib:InputControl dataSource="SearchCriteria" type="text" name="BusinessWebsite" property="BusinessWebsite" readOnly="no"/>
					</div>
				</td>
			</tr>
			<tr> 
				<td width="10%" bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Contact First Name:</font></b></div>
				</td>
				<td width="21%"> 
					<div align="left"> 
					  <beanlib:InputControl dataSource="SearchCriteria" type="text" name="BusinessContactFirstname" property="BusinessContactFirstname" readOnly="no"/>
					</div>
				</td>
				<td width="12%" bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Contact Last Name:</font></b></div>
				</td>
				<td width="24%"> 
					<div align="left"> 
					  <beanlib:InputControl dataSource="SearchCriteria" type="text" name="BusinessContactLastname" property="BusinessContactLastname" readOnly="no"/>
					</div>
				</td>
				<td width="12%" bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Contact Phone:</font></b></div>
				</td>
				<td width="21%"> 
					<div align="left"> 
					  <beanlib:InputControl dataSource="SearchCriteria" type="text" name="BusinessContactPhone" property="BusinessContactPhone" readOnly="no"/>
					</div>
				</td>
			</tr>
		</table>
		<br>
	  
	  <table width="100%" cellpadding="0" cellspacing="0">
          <!-- Display command buttons -->
		  <tr>
			<td colspan="2">
			  <input name="search" type="button" value="Search" style="width:90" onClick="handleAction('ListFrame', document.SearchForm, this.name)">
			  <input name="reset" type="reset" value="Clear" style="width:90">
			</td>
		 </tr>		
	  </table>
	  <input name="requestType" type="hidden" value=<%=requestType%>>
	 <input name="clientAction" type="hidden">
   </form>
 </body>
</html>
