<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.Address" %>
<%@ page import="com.bean.Business" %>
<%@ page import="com.bean.Creditor" %>
<%@ page import="com.bean.Zipcode" %>
<%@ page import="com.constants.RMT2ServletConst" %>
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
         // Retrieve data to be maintained from the request object.
         Address addr = (Address) request.getAttribute("ADDRESS");
		 Business bus = (Business) request.getAttribute("BUSINESS");
		 Creditor cred = (Creditor) request.getAttribute("CREDITOR");
		 Zipcode zip = (Zipcode) request.getAttribute("ZIP");
 		 String strOrigin = QUERY_BEAN != null ? QUERY_BEAN.getRequestOrigin() : "";
		 String ORIGIN_TRANSACT = "TRANSACTION";
		 Double balance = (Double) request.getAttribute("BALANCE");
		 String pageTitle = "Creditor/Vendor Maintenance";
 		 String requestType =  request.getParameter("requestType") ;
 		 if (requestType.equals("xact")) {
		    pageTitle = "Creditor/Vendor Payment Maintenance";
		 }		 
		 pageTitle += cred.getId() <= 0 ? " Add" : " Edit";
     %>
     
    <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
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
 
     <db:datasource id="credTypeDso" 
                          classId="com.api.DataSourceApi" 
                          connection="con"
						  query="CreditorTypeView"
						  order="description"
						  type="xml"/>
 
 
		<body bgcolor="#FFFFFF" text="#000000">
			<form name="DataForm" method="post" action="<%=APP_ROOT%>/glcreditorservlet">

				<table width="80%" border="0">			
					 <caption align="left"><h3><%=pageTitle%></h3> </caption>
					<tr>
						<td width="12%" class="clsTableFormHeader">Account Number:</td>
						<td width="21%"><%= cred.getAccountNumber() %></td>
						<td width="12%" class="clsTableFormHeader">Credit Limit:</td>
						<td width="21%"> <%= cred.getCreditLimit() %></td>
						<td width="12%" class="clsTableFormHeader">Balance:</td>
						<td width="21%"> <%= balance.doubleValue()%></td>					
					</tr>								
				</table>
				<br>
			
				<table width="90%" border="0">
					<tr> 
						<td width="10%" class="clsTableFormHeader">Business Id:</td>
						<td width="21%"> 
							<%= bus.getId() %>
							<input type="hidden" name="Id" value="<%= cred.getId() %>">
							<input type="hidden" name="VendorId" value="<%= cred.getId() %>">
							<input type="hidden" name="GlAccountId" value="<%= cred.getGlAccountId() %>">
							<input type="hidden" name="BusinessId" value="<%= bus.getId() %>">
							<input type="hidden" name="AddressId" value="<%= addr.getId() %>">
							<input type="hidden" name="AccountNumber" value="<%= cred.getAccountNumber()  == null ? "" : cred.getAccountNumber() %>">
						</td>
						<td width="12%" class="clsTableFormHeader">Contact First Name:</td>
						<td width="24%"> 
							<input type="text" name="ContactFirstname" value="<%= bus.getContactFirstname() == null ? "" : bus.getContactFirstname() %>">
						</td>
					</tr>	
					<tr> 
						<td width="10%" class="clsTableFormHeader"> Business Name:</td>
						<td width="21%"> 
						   <input type="text" name="Longname" value="<%= bus.getLongname() == null ? "" : bus.getLongname() %>" size="40">
						</td>
						<td width="12%" class="clsTableFormHeader">Contact Last Name:</td>
						<td width="21%"> 
							<input type="text" name="ContactLastname" value="<%= bus.getContactLastname() == null ? "" : bus.getContactLastname() %>">
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
						<td width="10%" class="clsTableFormHeader">Contact Phone:</td>
						<td width="21%"> 
							<input type="text" name="ContactPhone" value="<%= bus.getContactPhone()  == null ? "" : bus.getContactPhone() %>">
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
						<td width="12%" class="clsTableFormHeader">Contact Phone Ext.</td>
						<td width="24%"> 
							<input type="text" name="ContactExt" value="<%= bus.getContactExt() == null ? "" : bus.getContactExt() %>">
						</td>
					</tr>
					
					<tr>
						<td width="12%" class="clsTableFormHeader">Account Number:</td>
						<td width="21%"><%= cred.getAccountNumber()  == null ? "" : cred.getAccountNumber() %></td>
						<td width="12%" class="clsTableFormHeader">Creditor Type:</td>
						<td width="24%"> 
							 <db:InputControl dataSource="credTypeDso"
													 type="select"
													 name="CreditorTypeId"
													 codeProperty="Id"
													 displayProperty="Description"
													 selectedValue="<%=String.valueOf(cred.getCreditorTypeId()) %>"/>
						</td>
					</tr>								
					
					<tr>
						<td width="12%" class="clsTableFormHeader">Credit Limit:</td>
						<td width="21%"> 
							<input type="text" name="CreditLimit" value="<%= cred.getCreditLimit() %>">
						</td>
						<td width="12%" class="clsTableFormHeader">Balance:</td>
						<td width="24%"><%= balance.doubleValue()%></td>						
					</tr>									
					<tr>
						<td width="12%" class="clsTableFormHeader">APR:</td>
						<td width="24%"> 
							 <input type="text" name="Apr" value="<%= cred.getApr() %>">
						</td>
						<td width="12%" class="clsTableFormHeader">Tax Id:</td>
						<td width="24%"> 
							<input type="text" name="TaxId" value="<%= bus.getTaxId() == null ? "" : bus.getTaxId() %>">
						</td>
					</tr>
					<tr>
						<td width="12%" class="clsTableFormHeader">Web Site:</td>
						<td colspan="3" width="21%"> 
							<input type="text" name="Website" size="40"  value="<%= bus.getWebsite() == null ? "" : bus.getWebsite() %>">
						</td>
					</tr>					
				</table>
				<br>

				<table width="90%" border="0">
					<tr> 
						<td class="clsTableFormHeader" width="10%">Address 1:</td>
						<td width="24%">
							<input type="text" name="Addr1" size="40" value="<%= addr.getAddr1() == null ? "" : addr.getAddr1() %>">
						</td>
						<td class="clsTableFormHeader" width="10%">City:</td>
						<td width="24%"><%= zip.getCity() == null ? "" : zip.getCity() %></td>
					</tr>
					<tr>
						<td class="clsTableFormHeader" width="10%"> Address 2:</td>
						<td width="24%">
							<input type="text" name="Addr2" size="40" value="<%= addr.getAddr2() == null ? "" : addr.getAddr2() %>">
						</td>
						<td class="clsTableFormHeader" width="10%">State:</td>
						<td width="24%"><%= zip.getState() == null ? "" : zip.getState() %></td>								
					</tr>
					<tr>
						<td class="clsTableFormHeader" width="10%">Address 3:</td>
						<td width="24%">
							<input type="text" name="Addr3" size="40" value="<%= addr.getAddr3() == null ? "" : addr.getAddr3() %>">
						</td>
						<td class="clsTableFormHeader" width="10%">Main Phone:</td>
						<td width="24%">
							<input type="text" name="PhoneMain" value="<%= addr.getPhoneMain() == null ? "" : addr.getPhoneMain() %>">
						</td>
					</tr>              
					<tr> 
						<td class="clsTableFormHeader" width="10%">Address 4:</td>
						<td width="24%">
							<input type="text" name="Addr4" size="40" value="<%= addr.getAddr4() == null ? "" : addr.getAddr4() %>">
						</td>
						<td class="clsTableFormHeader" width="10%">Fax Phone:</td>
						<td width="24%">
							<input type="text" name="PhoneFax" value="<%= addr.getPhoneFax() == null ? "" : addr.getPhoneFax()  %>">
						</td>
					</tr>
					<tr> 
						<td class="clsTableFormHeader" width="10%">Zip:</td>
						<td width="24%">
						   <input type="text" name="Zip" value="<%= String.valueOf(addr.getZip()).equals("0") ? "" :  String.valueOf(addr.getZip()) %>" size="10">&nbsp;-&nbsp;
						   <input type="text" name="Zipext" value="<%= addr.getZipext() == null ? "" : addr.getZipext() %>" size="10">
						</td>
						<td width="10%">&nbsp; </td>
						<td width="24%">&nbsp;</td>
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
					 <td><input type="button" name="save" value="Save" style="width:90"  onClick="handleAction(MAIN_DETAIL_FRAME, DataForm, this.name)"></td>
					 <td><input type="button" name="back" value="Back" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, DataForm, this.name)"></td>
	 				 <td>
						  <%
							   if (requestType.equalsIgnoreCase(RMT2ServletConst.ORIGIN_TRANSACT)) {
						   %>
								<input type="button" name="creditorpayment" value="Make Payment" style="width:120"  onClick='handleRequest(this.name, xactController)'>
								<input type="button" name="creditorxact" value="Transactions" style="width:120"  onClick='handleRequest(this.name, xactController)'>
								<input type="button" name="vendoritemview" value="Inventory Items" style="width:120"  onClick='handleRequest(this.name, inventory)'>
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
