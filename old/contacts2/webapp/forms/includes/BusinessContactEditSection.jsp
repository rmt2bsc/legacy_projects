<!-- 
   This include provides contact data relative to a business entity and its address.
   It is expected to be used as a JSP include directive, meaning that it is inserted
   into another JSP at complie time.   
   
   This include requires the following data to be available on the user's request:
   1.  An XML representation of vw_business_address bean identified as "business".
   2.  An XML representation of genreal_codes bean identified as, "businesstypes".
       This XML document will contain all business types that will be displayed as
       as a HTML dropdown data control.
   3.  An XML representation of general_codes bean idenetified as, "businessservicetypes".
       This XML document will contain all business service types that will be displayed
       as a HTML dropdown data control
 -->
 
 
<xml:LoopNodes dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" query="//vw_business_address" nodeRef="rec">
	<table width="90%" border="0">
		<tr> 
			<td width="10%" class="clsTableFormHeader">Business Id:</td>
			<td width="21%"> 
			   <xml:InputControl value="#rec.business_id"/>	
			   <xml:InputControl type="hidden" name="BusinessId2" value="#rec.business_id"/>	
			</td>
			<td width="12%" class="clsTableFormHeader">Contact First Name:</td>
			<td width="24%"> 
			   <xml:InputControl type="text" name="ContactFirstname" value="#rec.bus_contact_firstname"/>	
			</td>
		</tr>	
		<tr> 
			<td width="10%" class="clsTableFormHeader"> Business Name:</td>
			<td width="21%"> 
			   <xml:InputControl type="text" name="Longname" value="#rec.bus_longname"/>							
			</td>
			<td width="12%" class="clsTableFormHeader">Contact Last Name:</td>
			<td width="21%"> 
			   <xml:InputControl type="text" name="ContactLastname" value="#rec.bus_contact_lastname"/>
			</td>
		</tr>
		<tr> 
			<td width="12%" class="clsTableFormHeader">Business Type:</td>
			<td width="24%"> 
				 <xml:InputControl dataSource="<%=BasicGLApi.CLIENT_BUSTYPES %>"   
													 type="select"
													 name="EntityTypeId"
													 query="//general_codes"
													 codeProperty="code_id"
													 displayProperty="longdesc"
													 selectedValue="#rec.bus_entity_type_id"/>									
			</td>
			<td width="10%" class="clsTableFormHeader">Contact Phone:</td>
			<td width="21%"> 
			  <xml:InputControl type="text" name="ContactPhone" value="#rec.bus_contact_phone"/>
			</td>							
		</tr>
		<tr> 
			<td width="10%" class="clsTableFormHeader">Service Type:</td>
			<td width="30%">
				 <xml:InputControl dataSource="<%=BasicGLApi.CLIENT_BUSSERVTYPES %>"
													 type="select"
													 name="ServTypeId"
													 query="//general_codes"
													 codeProperty="code_id"
													 displayProperty="longdesc"
													 selectedValue="#rec.bus_serv_type_id"/>										
			</td>
			<td width="12%" class="clsTableFormHeader">Contact Phone Ext.</td>
			<td width="24%"> 
			  <xml:InputControl type="text" name="ContactExt" value="#rec.bus_contact_ext"/>
			</td>
		</tr>
		<tr>
			<td width="12%" class="clsTableFormHeader">Tax Id:</td>
			<td width="24%"> 
			   <xml:InputControl type="text" name="TaxId" value="#rec.bus_tax_id"/>										
			</td>
			<td width="12%" class="clsTableFormHeader">Web Site:</td>
			<td colspan="3" width="21%"> 
			   <xml:InputControl type="text" name="Website" value="#rec.bus_website"/>													
			</td>			
		</tr>
	</table>
	<br>

	<table width="90%" border="0">
		<tr> 
			<td class="clsTableFormHeader" width="10%">Address 1:</td>
			<td width="24%">
			   <xml:InputControl type="hidden" name="AddressId" value="#rec.addr_id"/>						
			   <xml:InputControl type="text" name="Addr1" value="#rec.addr1" size="40"/>						
			</td>
			<td class="clsTableFormHeader" width="10%">City:</td>
			<td width="24%">
			   <xml:InputControl type="text" name="City" value="#rec.zip_city" style="border:none" readOnly="yes"/>												
			</td>
		</tr>
		<tr>
			<td class="clsTableFormHeader" width="10%"> Address 2:</td>
			<td width="24%">
			   <xml:InputControl type="text" name="Addr2" value="#rec.addr2" size="40"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">State:</td>
			<td width="24%">
			   <xml:InputControl type="text" name="State" value="#rec.zip_state" style="border:none" readOnly="yes"/>																		
			</td>								
		</tr>
		<tr>
			<td class="clsTableFormHeader" width="10%">Address 3:</td>
			<td width="24%">
			   <xml:InputControl type="text" name="Addr3" value="#rec.addr3" size="40"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">Main Phone:</td>
			<td width="24%">
			   <xml:InputControl type="text" name="PhoneMain" value="#rec.addr_phone_main"/>						
			</td>
		</tr>              
		<tr> 
			<td class="clsTableFormHeader" width="10%">Address 4:</td>
			<td width="24%">
			   <xml:InputControl type="text" name="Addr4" value="#rec.addr4" size="40"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">Fax Phone:</td>
			<td width="24%">
			   <xml:InputControl type="text" name="PhoneFax" value="#rec.addr_phone_fax"/>												
			</td>
		</tr>
		<tr> 
			<td class="clsTableFormHeader" width="10%">Zip:</td>
			<td width="24%">
			   <xml:InputControl type="text" name="Zip" value="#rec.addr_zip" size="10"/>&nbsp;-&nbsp;
			   <xml:InputControl type="text" name="Zipext" value="#rec.addr_zipext" size="10"/>						                         
			</td>
			<td width="10%">&nbsp; </td>
			<td width="24%">&nbsp;</td>
		</tr>
	</table>
</xml:LoopNodes>