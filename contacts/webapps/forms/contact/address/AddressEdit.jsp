<table width="90%" border="0">
	<tr> 
		<td bgcolor="#FFCC00" width="10%"> 
			<div align="right"><b><font size="2">Address 1:</font></b></div>
		</td>
		<td width="24%">
			<beanlib:InputControl type="text" size="50" tabIndex="9" name="Addr1" value="#data.Addr1"/>
		</td>
		<td bgcolor="#FFCC00" width="10%"> 
			<div align="right"><b><font size="2">City:</font></b></div>
		</td>
		<td width="24%">
			<beanlib:InputControl value="#data.ZipCity"/>   
		</td>
	</tr>
	<tr>
		<td bgcolor="#FFCC00" width="10%"> 
			<div align="right"><b><font size="2">Address 2:</font></b></div>
		</td>
		<td width="24%">
			<beanlib:InputControl type="text" size="50"  tabIndex="10" name="Addr2" value="#data.Addr2"/>
		</td>
		<td bgcolor="#FFCC00" width="10%"> 
			<div align="right"><b><font size="2">State:</font></b></div>
		</td>
		<td width="24%">
			<beanlib:InputControl value="#data.ZipState"/> 
		</td>								
	</tr>
	<tr>
		<td bgcolor="#FFCC00" width="10%"> 
			<div align="right"><b><font size="2">Address 3:</font></b></div>
		</td>
		<td width="24%">
			<beanlib:InputControl type="text" size="50"  tabIndex="11" name="Addr3" value="#data.Addr3"/>
		</td>
		<td bgcolor="#FFCC00" width="10%"> 
			<div align="right"><b><font size="2">Main Phone:</font></b></div>
		</td>
		<td width="24%">
			<beanlib:InputControl type="text" size="20"  tabIndex="15" name="PhoneMain" value="#data.AddrPhoneMain"/>
		</td>
	</tr>              
	<tr> 
		<td bgcolor="#FFCC00" width="10%"> 
			<div align="right"><b><font size="2">Address 4:</font></b></div>
		</td>
		<td width="24%">
			<beanlib:InputControl type="text" size="50"  tabIndex="12" name="Addr4" value="#data.Addr4"/>
		</td>
		<td bgcolor="#FFCC00" width="10%"> 
			<div align="right"><b><font size="2">Fax Phone:</font></b></div>
		</td>
		<td width="24%">
			<beanlib:InputControl type="text" size="20"  tabIndex="16" name="PhoneFax" value="#data.AddrPhoneFax"/>
		</td>
	</tr>
	<tr> 
		<td bgcolor="#FFCC00" width="10%"> 
			<div align="right"><b><font size="2">Zip:</font></b></div>
		</td>
		<td width="24%">
			<beanlib:InputControl type="text" size="10"  tabIndex="13" name="Zip" value="#data.AddrZip"/>
			<beanlib:InputControl type="text" size="5"  tabIndex="14" name="Zipext" value="#data.AddrZipext"/>
		</td>
		<td width="10%">&nbsp; </td>
		<td width="24%">&nbsp;</td>
	</tr>
</table>