     <%
         // Retrieve data to be maintained from the request object.
         Address addr = (Address) request.getAttribute("ADDRESS");
		 Business bus = (Business) request.getAttribute(GeneralConst.CLIENT_DATA_BUSINESS);
		 Creditor cred = (Creditor) request.getAttribute(AccountingConst.CLIENT_DATA_CREDITOR);
		 Zipcode zip = (Zipcode) request.getAttribute("ZIP");
		 Double balance = (Double) request.getAttribute("BALANCE");
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
						  
	<table width="80%" border="0">			
		 <caption align="left"><h3><%=pageTitle%></h3></caption>
		<tr>
			<td width="12%" class="clsTableFormHeader">Account Number:</td>
			<td width="21%">
			   <beanlib:InputControl dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR%>" type="hidden" name="CreditorId" property="Id"/>													
			   <beanlib:ElementValue dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR %>" property="AccountNumber"/>
			</td>
			<td width="12%" class="clsTableFormHeader">Credit Limit:</td>
			<td width="21%"> 
				<beanlib:ElementValue dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR %>" 
									  property="CreditLimit"/>						   
			</td>
			<td width="12%" class="clsTableFormHeader">Balance:</td>
			<td width="21%"> 
			   <beanlib:WrapperValue dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR_BAL %>"
									 format="$#,##0.00;($#,##0.00)"/>
			</td>					
		</tr>								
	</table>
	<br>

	<table width="90%" border="0">
		<tr> 
			<td width="10%" class="clsTableFormHeader">Business Id:</td>
			<td width="21%"> 
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" property="Id"/>	
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" 
									 type="hidden"
									 name="BusinessId"
									 property="Id"/>	
			</td>
			<td width="12%" class="clsTableFormHeader">Contact First Name:</td>
			<td width="24%"> 
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" 
									 type="text"
									 name="ContactFirstname"
									 property="ContactFirstname"/>	
			</td>
		</tr>	
		<tr> 
			<td width="10%" class="clsTableFormHeader"> Business Name:</td>
			<td width="21%"> 
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" 
									 type="text"
									 name="Longname"
									 property="Longname"/>							
			</td>
			<td width="12%" class="clsTableFormHeader">Contact Last Name:</td>
			<td width="21%"> 
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" 
									 type="text"
									 name="ContactLastname"
									 property="ContactLastname"/>													
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
			<td width="21%">
				<beanlib:ElementValue dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR %>" 
									  property="AccountNumber"/>						
			</td>
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
			   <beanlib:InputControl dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR%>" 
									 type="text"
									 name="CreditLimit"
									 property="CreditLimit"/>													
			</td>
			<td width="12%" class="clsTableFormHeader">Balance:</td>
			<td width="24%">
			   <beanlib:WrapperValue dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR_BAL %>"
									 format="$#,##0.00;($#,##0.00)"/>
			</td>						
		</tr>									
		<tr>
			<td width="12%" class="clsTableFormHeader">APR:</td>
			<td width="24%"> 
			   <beanlib:InputControl dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR%>" 
									 type="text"
									 name="Apr"
									 property="Apr"/>						
			</td>
			<td width="12%" class="clsTableFormHeader">Tax Id:</td>
			<td width="24%"> 
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" 
									 type="text"
									 name="TaxId"
									 property="TaxId"/>										
			</td>
		</tr>
		<tr>
			<td width="12%" class="clsTableFormHeader">Web Site:</td>
			<td colspan="3" width="21%"> 
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" 
									 type="text"
									 name="Website"
									 property="Website"/>													
			</td>
		</tr>					
	</table>
	<br>

	<table width="90%" border="0">
		<tr> 
			<td class="clsTableFormHeader" width="10%">Address 1:</td>
			<td width="24%">
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" type="hidden" name="AddressId" property="Id"/>						
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" 
									 type="text"
									 name="Addr1"
									 property="Addr1"
									 size="40"/>						
			</td>
			<td class="clsTableFormHeader" width="10%">City:</td>
			<td width="24%">
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_ZIP%>" 
									 type="text"
									 name="City"
									 property="City"
									 style="border:none"
									 readOnly="yes"/>												
			</td>
		</tr>
		<tr>
			<td class="clsTableFormHeader" width="10%"> Address 2:</td>
			<td width="24%">
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" 
									 type="text"
									 name="Addr2"
									 property="Addr2"
									 size="40"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">State:</td>
			<td width="24%">
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_ZIP%>" 
									 type="text"
									 name="State"
									 property="State"
									 style="border:none"
									 readOnly="yes"/>																		
			</td>								
		</tr>
		<tr>
			<td class="clsTableFormHeader" width="10%">Address 3:</td>
			<td width="24%">
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" 
									 type="text"
									 name="Addr3"
									 property="Addr3"
									 size="40"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">Main Phone:</td>
			<td width="24%">
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" 
									 type="text"
									 name="PhoneMain"
									 property="PhoneMain"/>						
			</td>
		</tr>              
		<tr> 
			<td class="clsTableFormHeader" width="10%">Address 4:</td>
			<td width="24%">
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" 
									 type="text"
									 name="Addr4"
									 property="Addr4"
									 size="40"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">Fax Phone:</td>
			<td width="24%">
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" 
									 type="text"
									 name="PhoneFax"
									 property="PhoneFax"/>												
			</td>
		</tr>
		<tr> 
			<td class="clsTableFormHeader" width="10%">Zip:</td>
			<td width="24%">
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" 
									 type="text"
									 name="Zip"
									 property="Zip"
									 size="10"/>&nbsp;-&nbsp;
			   <beanlib:InputControl dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" 
									 type="text"
									 name="Zipext"
									 property="Zipext"
									 size="10"/>						                         
			</td>
			<td width="10%">&nbsp; </td>
			<td width="24%">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="4" class="clsErrorText">
			   <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>  
			</td>
		</tr>
	</table>
