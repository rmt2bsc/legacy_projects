<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.Address" %>
<%@ page import="com.bean.Business" %>
<%@ page import="com.bean.Person" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.bean.Zipcode" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<jsp:useBean id="QUERY_BEAN" scope="session" class="com.bean.RMT2TagQueryBean"/>

<html>
		<head>
			<title>General Ledger Customer Maintenance Edit</title>
			<meta http-equiv="Pragma" content="no-cache">
			<meta http-equiv="Expires" content="-1">
			<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
			<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
		</head>
		
		<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2Contact.js"></script>
		<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
		<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
		<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2AcctXact.js"></script>
		<script>		
				function initForm() {
				   enableContactControls(DataForm, DataForm.ContactType.value);
				}
				function viewSaleOrders(_srcObj, _actionName) {
				     // Change the controller that is to handle this request.
				    this.document.DataForm.action = "<%=APP_ROOT%>" + _actionName;
					this.document.DataForm.requestType.value = "orderhist";
					this.document.DataForm.clientAction.value = _srcObj;
				    // Submit page
				    handleAction(MAIN_DETAIL_FRAME, this.document.DataForm, _srcObj);
				}
				function createSale(_srcObj, _actionName) {
				     // Change the controller that is to handle this request.
				    this.document.DataForm.action = "<%=APP_ROOT%>" + _actionName;
					this.document.DataForm.requestType.value = "selectitems";
					this.document.DataForm.clientAction.value = _srcObj;
				    // Submit page
				    handleAction(MAIN_DETAIL_FRAME, this.document.DataForm, _srcObj);
				}				
				function handleCustXact(_srcObj, _actionName) {
				     // Change the controller that is to handle this request.
				    this.document.DataForm.action = "<%=APP_ROOT%>" +  _actionName;
				    // Submit page
				    handleAction(MAIN_DETAIL_FRAME, this.document.DataForm, _srcObj);
				}
       </script>
    <%
         // Retrieve data to be maintained from the request object.
         Address addr = (Address) request.getAttribute("ADDRESS");
		 Business bus = (Business) request.getAttribute("BUSINESS");
		 Person per = (Person) request.getAttribute("PERSON");
		 Customer cust = (Customer) request.getAttribute("CUSTOMER");
		 Zipcode zip = (Zipcode) request.getAttribute("ZIP");
		 String custType = (String) request.getAttribute("CUSTTYPE");
		 String strBirthDate = per.getBirthDate() == null ? "" : RMT2Utility.formatDate(per.getBirthDate(), "MM/dd/yyyy");
		 String strOrigin = QUERY_BEAN != null ? QUERY_BEAN.getRequestOrigin() : "";
		 Double balance = (Double) request.getAttribute("BALANCE");
		 
		 String pageTitle = "Customer Maintenance";
 		 String requestType =  request.getParameter("requestType") ;
 		 if (requestType.equals("xact")) {
		    pageTitle = "Sales On Account - Customer Maintenance";
		 }
		 pageTitle += cust.getId() <= 0 ? " Add" : " Edit";
     %>
     
    <db:connection id="con" classId="com.bean.RMT2DBConnectionBean"/>
	
    <db:datasource id="perTitleDso" 
                          classId="com.api.DataSourceApi" 
                          connection="con"
						  query="GeneralCodesView"
						  where="group_id = 26"
						  order="longdesc"
						  type="xml"/>												  
    
	<db:datasource id="perMaritalStatDso" 
                          classId="com.api.DataSourceApi" 
                          connection="con"
						  query="GeneralCodesView"
						  where="group_id = 5"
						  order="longdesc"
						  type="xml"/>												  
    
	<db:datasource id="perGenderDso" 
                          classId="com.api.DataSourceApi" 
                          connection="con"
						  query="GeneralCodesView"
						  where="group_id = 6"
						  order="longdesc"
						  type="xml"/>
    
	<db:datasource id="perRaceDso" 
                          classId="com.api.DataSourceApi" 
                          connection="con"
						  query="GeneralCodesView"
						  where="group_id = 15"
						  order="longdesc"
						  type="xml"/>												  
	
    <db:datasource id="busTypeDso" 
	                      classId="com.api.DataSourceApi" 
						  connection="con"
						  query="GeneralCodesView"
						  where="group_id = 7"
						  order="longdesc"
						  type="xml"/>    
						  
    <db:datasource id="busServTypeDso" 
                          classId="com.api.DataSourceApi" 
                          connection="con"
						  query="GeneralCodesView"
						  where="group_id = 8"
						  order="longdesc"
						  type="xml"/> 												  
 
	<body bgcolor="#FFFFFF" text="#000000" onLoad="initForm()">
		<form name="DataForm" method="post" action="<%=APP_ROOT%>/glcustomerservlet">
			<table width="90%" border="0">
  				<caption align="left"> <h3><%=pageTitle%></h3> </caption>
				<tr> 
					<td width="19%"  class="clsTableFormHeader"> 
						<div align="right"><font size="2"><b>Customer Type:</b></font></div>
					</td>	
					<td width="81%">
					     <% 
						      if (cust.getId() == 0) {
					      %>
								<select name="ContactType" onChange="enableContactControls(DataForm, DataForm.ContactType.value)">
									<option value="1" <%= custType.equals("1") ? " selected" : "" %>>Business</option>
									<option value="2" <%= custType.equals("2") ? " selected" : "" %>>Personal</option>
								</select>
						<%
						      }
							  else {
							      out.println(custType.equals("1") ? " Business" : custType.equals("2") ? " Personal" : "" );
								  out.println("<input type=\"hidden\" name=\"ContactType\" value=\"" + custType + "\">");
							  }
					    %>
						      
						<input type="hidden" name="Id" value="<%= cust.getId() %>">
						<input type="hidden" name="GlAccountId" value="<%= cust.getGlAccountId() %>">
						<input type="hidden" name="BusinessId" value="<%= bus.getId() %>">
						<input type="hidden" name="AddressId" value="<%= addr.getId() %>">						
						<input type="hidden" name="PersonId" value="<%= per.getId() %>">	
						<input type="hidden" name="AccountNo" value="<%= cust.getAccountNo() %>	">
										
					</td>									 
				</tr>
			</table>
			<br>
			
			<table width="80%" border="0">			
			     <caption align="left"  style="color:blue">Account Details</caption>			
				<tr>
					<td width="12%" class="clsTableFormHeader">Account Number:</td>
					<td width="21%"><%= cust.getAccountNo() %></td>
					<td width="12%" class="clsTableFormHeader">Credit Limit:</td>
					<td width="21%"> 
						<input type="text" name="CreditLimit" value="<%= cust.getCreditLimit() %>">
					</td>
					<td width="12%" class="clsTableFormHeader">Balance:</td>
					<td width="21%"> <%= balance.doubleValue()%></td>					
				</tr>								
			</table>
            <br>
			
			
			<table width="90%" border="0">
			     <caption align="left"  style="color:blue">Personal Data</caption>
				<tr> 
					<td width="10%" class="clsTableFormHeader">Title:</td>
					<td width="21%">
						 <db:InputControl dataSource="perTitleDso"
												 type="select"
												 name="Title"
												 codeProperty="Id"
												 displayProperty="Longdesc"
												 selectedValue="<%=String.valueOf(per.getTitle()) %>"/>
					</td>
					<td width="12%" class="clsTableFormHeader">SSN:</td>
					<td width="24%"> 
						<input type="text" name="Ssn" value="<%= per.getSsn() == null ? "" :  per.getSsn() %>">
					</td>
				</tr>	
				<tr> 
					<td width="10%" class="clsTableFormHeader">First Name:</td>
					<td width="21%"> 
					   <input type="text" name="Firstname" value="<%= per.getFirstname() == null ? "" : per.getFirstname() %>" size="40">
					</td>
					<td width="12%" class="clsTableFormHeader">Birth Date:</td>
					<td width="21%"> 
						<input type="text" name="BirthDate" value="<%= strBirthDate %>">
					</td>
				</tr>
				<tr> 
					<td width="12%" class="clsTableFormHeader">Middle Name:</td>
					<td width="24%"> 
                        <input type="text" name="Midname" value="<%= per.getMidname() == null ? "" : per.getMidname() %>" size="40">
					</td>
					<td width="10%" class="clsTableFormHeader">Gender:</td>
					<td width="21%"> 
						 <db:InputControl dataSource="perGenderDso"
												 type="select"
												 name="GenderId"
												 codeProperty="Id"
												 displayProperty="Longdesc"
												 selectedValue="<%=String.valueOf(per.getGenderId()) %>"/>
					</td>							
				</tr>
				<tr> 
					<td width="10%" class="clsTableFormHeader">Last Name:</td>
					<td width="30%">
                        <input type="text" name="Lastname" value="<%= per.getLastname() == null ? "" : per.getLastname() %>" size="40">										
					</td>
					<td width="12%" class="clsTableFormHeader">Marital Status:</td>
					<td width="24%"> 
					    <db:InputControl dataSource="perMaritalStatDso"
												 type="select"
												 name="MaritalStatus"
												 codeProperty="Id"
												 displayProperty="Longdesc"
												 selectedValue="<%=String.valueOf(per.getMaritalStatus()) %>"/>
					</td>
				</tr>
				
				<tr>
					<td width="12%" class="clsTableFormHeader">Maiden Name:</td>
					<td width="21%">
                        <input type="text" name="Maidenname" value="<%= per.getMaidenname() == null ? "" : per.getMaidenname() %>" size="40">
					</td>
					<td width="12%" class="clsTableFormHeader">Race:</td>
					<td width="24%"> 
						 <db:InputControl dataSource="perRaceDso"
												 type="select"
												 name="RaceId"
												 codeProperty="Id"
												 displayProperty="Longdesc"
												 selectedValue="<%=String.valueOf(per.getRaceId()) %>"/>
					</td>
				</tr>								
				
				<tr>
					<td width="12%" class="clsTableFormHeader">Generation:</td>
					<td width="21%"> 
						<input type="text" name="Generation" value="<%= per.getGeneration() == null ? "" : per.getGeneration() %>">
					</td>
					<td class="clsTableFormHeader">EMail:</td>
					<td width="24%">
						<input type="text" name="Email" value="<%= per.getEmail() == null ? "" : per.getEmail() %>" size="50">
				   </td>
				</tr>									
			</table>
			<br>

			<table width="90%" border="0">
			     <caption align="left" style="color:blue">Business Data</caption>
				<tr> 
					<td width="10%" class="clsTableFormHeader"> Business Name:</td>
					<td width="21%"> 
					   <input type="text" name="Longname" value="<%= bus.getLongname() == null ? "" : bus.getLongname() %>" size="40">
					</td>
					<td width="12%" class="clsTableFormHeader">Contact First Name:</td>
					<td width="24%"> 
						<input type="text" name="ContactFirstname" value="<%= bus.getContactFirstname() == null ? "" : bus.getContactFirstname() %>">
					</td>
				</tr>	
				<tr> 
					<td width="12%" class="clsTableFormHeader">Business Type:</td>
					<td width="24%"> 
						 <db:InputControl dataSource="busTypeDso"
												 type="select"
												 name="BusType"
												 codeProperty="Id"
												 displayProperty="Longdesc"
												 selectedValue="<%=String.valueOf(bus.getBusType()) %>"/>									
					</td>
					<td width="12%" class="clsTableFormHeader">Contact Last Name:</td>
					<td width="21%"> 
						<input type="text" name="ContactLastname" value="<%= bus.getContactLastname() == null ? "" : bus.getContactLastname() %>">
					</td>
				</tr>
				<tr> 
					<td width="10%" class="clsTableFormHeader">Service Type:</td>
					<td width="30%">
						 <db:InputControl dataSource="busServTypeDso"
												 type="select"
												 name="ServType"
												 codeProperty="Id"
												 displayProperty="Longdesc"
												 selectedValue="<%=String.valueOf(bus.getServType()) %>"/>										
					</td>
					<td width="10%" class="clsTableFormHeader">Contact Phone:</td>
					<td width="21%"> 
						<input type="text" name="ContactPhone" value="<%= bus.getContactPhone() == null ? "" : bus.getContactPhone() %>">
					</td>							
				</tr>
				<tr> 
					<td width="12%" class="clsTableFormHeader">Tax Id:</td>
					<td width="24%"> 
						<input type="text" name="TaxId" value="<%= bus.getTaxId() == null ? "" : bus.getTaxId() %>">
					</td>
					<td width="12%" class="clsTableFormHeader">Contact Phone Ext.:</td>
					<td width="24%"> 
						<input type="text" name="ContactExt" value="<%= bus.getContactExt()  == null ? "" : bus.getContactExt() %>">
					</td>
				</tr>
			</table>
			<br>				

			<table width="90%" border="0">
				<tr> 
					<td class="clsTableFormHeader" width="10%">Address 1:</td>
					<td width="24%">
						<input type="text" name="Addr1" size="40" value="<%= addr.getAddr1() %>">
					</td>
					<td class="clsTableFormHeader" width="10%">City:</td>
					<td width="24%"><%= zip.getCity() == null ? "" : zip.getCity() %></td>
				</tr>
				<tr>
					<td class="clsTableFormHeader" width="10%"> Address 2:</td>
					<td width="24%">
						<input type="text" name="Addr2" size="40" value="<%= addr.getAddr2() %>">
					</td>
					<td class="clsTableFormHeader" width="10%">State:</td>
					<td width="24%"><%= zip.getState() == null ? "" :  zip.getState() %></td>								
				</tr>
				<tr>
					<td class="clsTableFormHeader" width="10%">Address 3:</td>
					<td width="24%">
						<input type="text" name="Addr3" size="40" value="<%= addr.getAddr3() %>">
					</td>
					<td class="clsTableFormHeader" width="10%">Main Phone:</td>
					<td width="24%">
						<input type="text" name="PhoneMain" value="<%= addr.getPhoneMain() %>">
					</td>
				</tr>              
				<tr> 
					<td class="clsTableFormHeader" width="10%">Address 4:</td>
					<td width="24%">
						<input type="text" name="Addr4" size="40" value="<%= addr.getAddr4() %>">
					</td>
					<td class="clsTableFormHeader" width="10%">Fax Phone:</td>
					<td width="24%">
						<input type="text" name="PhoneFax" value="<%= addr.getPhoneFax() %>">
					</td>
				</tr>
				<tr> 
					<td class="clsTableFormHeader" width="10%">Zip:</td>
					<td width="24%">
					   <input type="text" name="Zip" value="<%= addr.getZip()%>" size="10">&nbsp;-&nbsp;
					   <input type="text" name="Zipext" value="<%= addr.getZipext() %>" size="10">
					</td>
					<td width="10%">&nbsp; </td>
					<td width="24%">&nbsp;</td>
				</tr>
				<tr>   
				    <td colspan="4"><img src="images/clr.gif" height="20"></td>
				</tr>
				<tr>
					<td colspan="4" class="clsErrorText">
					   <gen:ShowPageMessages dataSource="ERRORS"/>  
					</td>
				</tr>				
			</table>

		   <table>
			  <tr>
				 <td><img src="images/clr.gif" height="10"></td>
				 <td><img src="images/clr.gif" height="10"></td>
				 <td><img src="images/clr.gif" height="10"></td>
			</tr>
			<tr>
				 <td><input type="button" name="save" value="Apply" style="width:90"  onClick="handleAction(MAIN_DETAIL_FRAME, DataForm, this.name)"></td>
 				 <td><input type="button" name="back" value="Back" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, DataForm, this.name)"></td>
				 <td>
				      <%
					       if (requestType.equalsIgnoreCase(RMT2ServletConst.ORIGIN_TRANSACT)) {
					   %>
 				            <input type="button" name="viewjournallist" value="View Orders" style="width:120"  onClick='viewSaleOrders(this.name, xactController)'>
					        <input type="button" name="selectitems" value="Create Sale" style="width:120"  onClick='createSale(this.name, xactController)'>
				            <input type="button" name="customerpayment" value="Receive Payment" style="width:120"  onClick='handleCustXact(this.name, xactController)'>
							<input type="button" name="customerxact" value="Transactions" style="width:120"  onClick='handleCustXact(this.name, xactController)'>
					   <%
					       }
						   else {
						%>
						    &nbsp;
						<%
						    }
						%>
			     </td>
			</tr>
		</table>
        <input name="requestType" type="hidden" value=<%=requestType%>>
	    <input name="clientAction" type="hidden">
	</form>
</body>
</html>
