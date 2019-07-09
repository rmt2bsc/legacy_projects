<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.XactQuery" %>
<%@ page import="com.bean.CustomerCombine" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<jsp:useBean id="QUERY_BEAN" scope="session" class="com.bean.RMT2TagQueryBean"/>

<html>
  <head>
    <title>Customer Search Criteria</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
  </head>

     <%
		 CustomerCombine cc = (CustomerCombine) QUERY_BEAN.getCustomObj();
		 request.setAttribute("SearchCriteria", cc);
         String customerType = cc.getCustomerType() == null ?  "" :  cc.getCustomerType();		

		 String pageTitle = "Customer Search";
		 String requestType =  request.getParameter("requestType") ;
		 if (requestType.equals("xact")) {
		    pageTitle = "Sales On Account - Customer Search";
		 }
    %>
	
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Contact.js"></script>
  <script Language="JavaScript">
     function initForm() {
		enableCustomerCriteriaControls(SearchForm, SearchForm.CustomerType.value);
	}
  </script>
      
  <body bgcolor="#FFFFFF" text="#000000" onLoad="initForm()">
       <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/glcustomerservlet">
		 
	     <h3><%=pageTitle%></h3>
	    
		<table width="30%" border="0">  
			<tr> 
				<td width="33%" bgcolor="#FFCC00"> 
					<div align="right"><font size="2"><b>Customer Type:</b></font></div>
				</td>
				<td width="67%">
					<select name="CustomerType"  onChange="enableCustomerCriteriaControls(SearchForm, SearchForm.CustomerType.value)">
						<option value="1" <%= customerType.equals("1") ? " selected" : "" %>>Business</option>              
						<option value="2" <%= customerType.equals("2") ? " selected" : "" %>>Personal</option>
					</select>
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
    	    <caption align="left" style="color:blue">Business Criteria</caption>
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
		
		<table width="90%" border="0">
			<caption align="left" style="color:blue">Personal Criteria</caption>
			<tr> 
				<td bgcolor="#FFCC00" width="12%"> 
					<div align="right"><font size="2"><b>First Name:</b></font></div>
				</td>
				<td width="19%">
					<beanlib:InputControl dataSource="SearchCriteria" type="text" name="PersonFirstname" property="PersonFirstname" readOnly="no"/>
				</td>
				<td bgcolor="#FFCC00" width="12%"> 
					<div align="right"><font size="2"><b>Middle Name:</b></font></div>
				</td>
				<td width="24%">
						<beanlib:InputControl dataSource="SearchCriteria" type="text" name="PersonMidname" property="PersonMidname" readOnly="no"/>
				</td>
				<td bgcolor="#FFCC00" width="12%"> 
					<div align="right"><b><font size="2">Last Name:</font></b></div>
				</td>
				<td width="21%">
						<beanlib:InputControl dataSource="SearchCriteria" type="text" name="PersonLastname" property="PersonLastname" readOnly="no"/>
				</td>
			</tr>
			<tr> 
				<td bgcolor="#FFCC00" width="12%"> 
					<div align="right"><font size="2"><b>SSN:</b></font></div>
				</td>
				<td width="19%">
				     <beanlib:InputControl dataSource="SearchCriteria" type="text" name="PersonSsn" property="PersonSsn" readOnly="no"/>
				</td>
				<td bgcolor="#FFCC00" width="12%"> 
					<div align="right"><font size="2"><b><font size="2">Birth Date:</font></b></font></div>
				</td>
				<td width="24%">
					<beanlib:InputControl dataSource="SearchCriteria" type="text" name="PersonBirthDate" property="PersonBirthDate" readOnly="no"/>
			  </td>
				<td width="12%"> 
					<div align="right"><b><font size="2">&nbsp;</font></b></div>
				</td>
				<td width="21%">&nbsp;</td>
			</tr>
			<tr> 
				<td bgcolor="#FFCC00" width="12%"> 
					<div align="right"><font size="2"><b>Email:</b></font></div>
				</td>
				<td colspan="3"> 
					<div align="left">
						<beanlib:InputControl dataSource="SearchCriteria" type="text" name="PersonEmail" property="PersonEmail" readOnly="no"/>
					</div>
				</td>
				<td width="12%"> 
					<div align="right"><b><font size="2"></font></b></div>
				</td>
				<td width="21%">&nbsp;</td>
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
