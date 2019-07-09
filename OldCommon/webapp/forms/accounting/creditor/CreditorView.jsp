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
		 <caption align="left"><h3><%=pageTitle%></h3> </caption>
		<tr>
			<td width="12%" class="clsTableFormHeader">Account Number:</td>
			<td width="21%">
			   <beanlib:ElementValue dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR %>" property="AccountNumber"/>
			   <beanlib:InputControl dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR%>" type="hidden" name="CreditorId" property="Id"/>
			</td>
			<td width="12%" class="clsTableFormHeader">Credit Limit:</td>
			<td width="21%"> 
				<beanlib:ElementValue dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR %>" property="CreditLimit"/>						   
			</td>
			<td width="12%" class="clsTableFormHeader">Balance:</td>
			<td width="21%"> 
			   <beanlib:WrapperValue dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR_BAL %>" format="$#,##0.00;($#,##0.00)"/>
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
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" property="ContactFirstname"/>	
			</td>
		</tr>	
		<tr> 
			<td width="10%" class="clsTableFormHeader"> Business Name:</td>
			<td width="21%"> 
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" property="Longname"/>							
			</td>
			<td width="12%" class="clsTableFormHeader">Contact Last Name:</td>
			<td width="21%"> 
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" property="ContactLastname"/>													
			</td>
		</tr>
		<tr> 
			<td width="12%" class="clsTableFormHeader">Business Type:</td>
			<td width="24%"> 
				 <db:Lookup dataSource=""
							masterCodeName=""
							masterCodeValue="<%=String.valueOf(bus.getBusType()) %>"
							type="1"
							lookupSource="busTypeDso"
							lookupCodeName="Id"
							lookupDisplayName="Longdesc"/>
			</td>
			<td width="10%" class="clsTableFormHeader">Contact Phone:</td>
			<td width="21%"> 
				<beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" property="ContactPhone"/>					
			</td>							
		</tr>
		<tr> 
			<td width="10%" class="clsTableFormHeader">Service Type:</td>
			<td width="30%">
				 <db:Lookup dataSource=""
							masterCodeName=""
							masterCodeValue="<%=String.valueOf(bus.getServType()) %>"
							type="1"
							lookupSource="busServTypeDso"
							lookupCodeName="Id"
							lookupDisplayName="Longdesc"/>
			</td>
			<td width="12%" class="clsTableFormHeader">Contact Phone Ext.</td>
			<td width="24%"> 
			    <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" property="ContactExt"/>					
			</td>
		</tr>
		
		<tr>
			<td width="12%" class="clsTableFormHeader">Account Number:</td>
			<td width="21%">
				<beanlib:ElementValue dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR %>" property="AccountNumber"/>						
			</td>
			<td width="12%" class="clsTableFormHeader">Creditor Type:</td>
			<td width="24%"> 
				 <db:Lookup dataSource=""
							masterCodeName=""
							masterCodeValue="<%=String.valueOf(cred.getCreditorTypeId()) %>"
							type="1"
							lookupSource="credTypeDso"
							lookupCodeName="Id"
							lookupDisplayName="Description"/>
			</td>
		</tr>								
		
		<tr>
			<td width="12%" class="clsTableFormHeader">Credit Limit:</td>
			<td width="21%"> 
			   <beanlib:ElementValue dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR%>" property="CreditLimit"/>													
			</td>
			<td width="12%" class="clsTableFormHeader">Balance:</td>
			<td width="24%">
			   <beanlib:WrapperValue dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR_BAL %>" format="$#,##0.00;($#,##0.00)"/>
			</td>						
		</tr>									
		<tr>
			<td width="12%" class="clsTableFormHeader">APR:</td>
			<td width="24%"> 
			   <beanlib:ElementValue dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR%>" property="Apr"/>						
			</td>
			<td width="12%" class="clsTableFormHeader">Tax Id:</td>
			<td width="24%"> 
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" property="TaxId"/>										
			</td>
		</tr>
		<tr>
			<td width="12%" class="clsTableFormHeader">Web Site:</td>
			<td colspan="3" width="21%"> 
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" property="Website"/>													
			</td>
		</tr>					
	</table>
	<br>
	
	<table width="90%" border="0">
		<tr> 
			<td class="clsTableFormHeader" width="10%">Address 1:</td>
			<td width="24%">
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" property="Addr1"/>						
			</td>
			<td class="clsTableFormHeader" width="10%">City:</td>
			<td width="24%">
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_ZIP%>" property="City"/>												
			</td>
		</tr>
		<tr>
			<td class="clsTableFormHeader" width="10%"> Address 2:</td>
			<td width="24%">
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" property="Addr2"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">State:</td>
			<td width="24%">
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_ZIP%>" property="State"/>																		
			</td>								
		</tr>
		<tr>
			<td class="clsTableFormHeader" width="10%">Address 3:</td>
			<td width="24%">
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" property="Addr3"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">Main Phone:</td>
			<td width="24%">
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" property="PhoneMain"/>						
			</td>
		</tr>              
		<tr> 
			<td class="clsTableFormHeader" width="10%">Address 4:</td>
			<td width="24%">
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" property="Addr4"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">Fax Phone:</td>
			<td width="24%">
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" property="PhoneFax"/>												
			</td>
		</tr>
		<tr> 
			<td class="clsTableFormHeader" width="10%">Zip:</td>
			<td width="24%">
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" property="Zip"/>&nbsp;-&nbsp;
			   <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_ADDERSS%>" property="Zipext"/>						                         
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
