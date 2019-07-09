<table width="90%" border="0">
 <tr> 
	<td align="left" width="20%"> 
		<font size="2">
			<b>Account Number:</b>
		 </font>
	</td>
	<td width="80%">
	   <beanlib:InputControl value="#creditor.AccountNumber"/> 
	</td>
</tr>    
<tr> 
	<td align="left"> 
		<font size="2">
			<b>Account Name:</b>
		</font>
	 </td>
	 <td>
		 <beanlib:InputControl value="#creditorext.Name"/> 
	 </td>
</tr>   
<tr> 
	<td align="left"> 
		<font size="2">
			<b>Balance:</b>
		</font>
	 </td>
	 <td>
	  <beanlib:InputControl value="#creditorbalance" format="$#,##0.00;($#,##0.00)"/> 
	</td>
</tr>   			
</table>					