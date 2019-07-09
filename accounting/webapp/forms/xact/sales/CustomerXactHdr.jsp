<table width="100%" border="0">
 <tr> 
	<td align="right" width="20%"> 
		<font size="2">
			<b>Account Number:</b>
		 </font>
	</td>
	<td width="80%">
	   <beanlib:InputControl value="#customer.AccountNo"/> 
	</td>
</tr>    
<tr> 
	<td align="right"> 
		<font size="2">
			<b>Account Name:</b>
		</font>
	 </td>
	 <td>
		 <beanlib:InputControl value="#customer.Name"/> 
	 </td>
</tr>   
<tr> 
	<td align="right"> 
		<font size="2">
			<b>Balance:</b>
		</font>
	 </td>
	 <td>
		<strong><beanlib:InputControl value="#salesorder.OrderTotal" format="$#,##0.00;($#,##0.00)"/></strong> 
	</td>
</tr>   			
</table>					