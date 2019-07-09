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
 
						   
<xml:LoopNodes dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" query="<%=xpathQuery%>" nodeRef="rec">
	<table width="90%" border="0">
		<tr> 
			<td width="10%" class="clsTableFormHeader">Business Id:</td>
			<td width="21%"> 
			   <xml:InputControl value="#rec.businessId"/>	
			</td>
			<td width="12%" class="clsTableFormHeader">Contact First Name:</td>
			<td width="24%"> 
			   <xml:InputControl value="#rec.contactFirstname"/>	
			</td>
		</tr>	
		<tr> 
			<td width="10%" class="clsTableFormHeader"> Business Name:</td>
			<td width="21%"> 
			   <xml:InputControl value="#rec.name"/>							
			</td>
			<td width="12%" class="clsTableFormHeader">Contact Last Name:</td>
			<td width="21%"> 
			   <xml:InputControl value="#rec.contactLastname"/>
			</td>
		</tr>
		<tr> 
			<td width="12%" class="clsTableFormHeader">Business Type:</td>
			<td width="24%"> 
					<xml:InputControl dataSource="<%=BasicGLApi.CLIENT_BUSTYPES %>"
													  query="//general_codes[code_id = {#rec.busType}]"
													  property="longdesc"/>								 						
			</td>
			<td width="10%" class="clsTableFormHeader">Contact Phone:</td>
			<td width="21%"> 
			  <xml:InputControl value="#rec.contactPhone"/>
			</td>							
		</tr>
		<tr> 
			<td width="10%" class="clsTableFormHeader">Service Type:</td>
			<td width="30%">
           <xml:InputControl dataSource="<%=BasicGLApi.CLIENT_BUSSERVTYPES %>"
                             query="//general_codes[code_id = {#rec.servType}]"
  													 property="longdesc"/>		
			</td>
			<td width="12%" class="clsTableFormHeader">Contact Phone Ext.</td>
			<td width="24%"> 
			  <xml:InputControl value="#rec.contactExt"/>
			</td>
		</tr>
		<tr>
			<td width="12%" class="clsTableFormHeader">Tax Id:</td>
			<td width="24%"> 
			   <xml:InputControl value="#rec.taxId"/>										
			</td>
			<td width="12%" class="clsTableFormHeader">Email:</td>
			<td colspan="3" width="21%"> 
			   <xml:InputControl value="#rec.contactEmail"/>													
			</td>			
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td width="12%" class="clsTableFormHeader">Web Site:</td>
			<td colspan="3" width="21%"> 
			   <xml:InputControl value="#rec.website"/>													
			</td>			
		</tr>
	</table>
	<br>

	<table width="90%" border="0">
		<tr> 
			<td class="clsTableFormHeader" width="10%">Address 1:</td>
			<td width="24%">
			   <xml:InputControl type="hidden" name="AddressId" value="#rec.addrId"/>						
			   <xml:InputControl value="#rec.addr1"/>						
			</td>
			<td class="clsTableFormHeader" width="10%">City:</td>
			<td width="24%">
			   <xml:InputControl value="#rec.zipCity" readOnly="yes"/>												
			</td>
		</tr>
		<tr>
			<td class="clsTableFormHeader" width="10%"> Address 2:</td>
			<td width="24%">
			   <xml:InputControl value="#rec.addr2"/>
			</td>
			<td class="clsTableFormHeader" width="10%">State:</td>
			<td width="24%">
			   <xml:InputControl value="#rec.zipState"/>
			</td>								
		</tr>
		<tr>
			<td class="clsTableFormHeader" width="10%">Address 3:</td>
			<td width="24%">
			   <xml:InputControl value="#rec.addr3"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">Main Phone:</td>
			<td width="24%">
			   <xml:InputControl value="#rec.addrPhoneMain"/>						
			</td>
		</tr>              
		<tr> 
			<td class="clsTableFormHeader" width="10%">Address 4:</td>
			<td width="24%">
			   <xml:InputControl value="#rec.addr4"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">Fax Phone:</td>
			<td width="24%">
			   <xml:InputControl value="#rec.addrPhoneFax"/>												
			</td>
		</tr>
		<tr> 
			<td class="clsTableFormHeader" width="10%">Zip:</td>
			<td width="24%">
			   <xml:InputControl value="#rec.addrZip"/>&nbsp;-&nbsp;
			   <xml:InputControl value="#rec.addrZipext"/>						                         
			</td>
			<td width="10%">&nbsp; </td>
			<td width="24%">&nbsp;</td>
		</tr>
	</table>
</xml:LoopNodes>