	<table width="90%" border="0">
		<tr> 
			<td width="10%" class="clsTableFormHeader">Business Id:</td>
			<td width="21%">0</td>
			<td width="12%" class="clsTableFormHeader">Contact First Name:</td>
			<td width="24%"> 
			   <input type="text" name="ContactFirstname">
			</td>
		</tr>	
		<tr> 
			<td width="10%" class="clsTableFormHeader"> Business Name:</td>
			<td width="21%"> 
			   <input type="text" name="Longname">
			</td>
			<td width="12%" class="clsTableFormHeader">Contact Last Name:</td>
			<td width="21%"> 
			   <input type="text" name="ContactLastname">
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
													 displayProperty="longdesc"/>								
			</td>
			<td width="10%" class="clsTableFormHeader">Contact Phone:</td>
			<td width="21%"> 
			    <input type="text" name="ContactPhone">
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
													 displayProperty="longdesc"/>
			</td>
			<td width="12%" class="clsTableFormHeader">Contact Phone Ext.</td>
			<td width="24%"> 
			    <input type="text" name="ContactExt">
			</td>
		</tr>
		
		<tr>
			<td width="12%" class="clsTableFormHeader">APR:</td>
			<td width="24%"> 
			   <input type="text" name="Apr" size="40">
			</td>
			<td width="12%" class="clsTableFormHeader">Tax Id:</td>
			<td width="24%"> 
			   <input type="text" name="TaxId">
			</td>
		</tr>
		<tr>
			<td width="12%" class="clsTableFormHeader">Web Site:</td>
			<td colspan="3" width="21%"> 
			   <input type="text" name="Website">
			</td>
		</tr>					
	</table>
	<br>

	<table width="90%" border="0">
		<tr> 
			<td class="clsTableFormHeader" width="10%">Address 1:</td>
			<td width="24%">
			   <input type="text" name="Addr1" size="40">
			</td>
			<td class="clsTableFormHeader" width="10%">City:</td>
			<td width="24%">
			   <input type="text" name="City" style="border:none" READONLY>
			</td>
		</tr>
		<tr>
			<td class="clsTableFormHeader" width="10%"> Address 2:</td>
			<td width="24%">
			   <input type="text" name="Addr2" size="40">
			</td>
			<td class="clsTableFormHeader" width="10%">State:</td>
			<td width="24%">
			   <input type="text" name="State" style="border:none" READONLY>
			</td>								
		</tr>
		<tr>
			<td class="clsTableFormHeader" width="10%">Address 3:</td>
			<td width="24%">
			   <input type="text" name="Addr3" size="40">
			</td>
			<td class="clsTableFormHeader" width="10%">Main Phone:</td>
			<td width="24%">
			   <input type="text" name="PhoneMain">
			</td>
		</tr>              
		<tr> 
			<td class="clsTableFormHeader" width="10%">Address 4:</td>
			<td width="24%">
			   <input type="text" name="Addr4" size="40">
			</td>
			<td class="clsTableFormHeader" width="10%">Fax Phone:</td>
			<td width="24%">
			   <input type="text" name="PhoneFax">
			</td>
		</tr>
		<tr> 
			<td class="clsTableFormHeader" width="10%">Zip:</td>
			<td width="24%">
			   <input type="text" name="Zip" size="10">&nbsp;-&nbsp;<input type="text" name=Zipext size="10">
			</td>
			<td width="10%">&nbsp; </td>
			<td width="24%">&nbsp;</td>
		</tr>
	</table>