<table width="90%" border="0">
 <tr> 
	<td align="left" width="20%"> 
		<font size="2">
			<b>Account Number:</b>
		 </font>
	</td>
	<td width="80%">
	   <beanlib:ElementValue dataSource="creditor" property="AccountNumber"/> 
	</td>
</tr>    
<tr> 
	<td align="left"> 
		<font size="2">
			<b>Account Name:</b>
		</font>
	 </td>
	 <td>
		 <beanlib:ElementValue dataSource="business" property="Longname"/> 
	 </td>
</tr>   
<tr> 
	<td align="left"> 
		<font size="2">
			<b>Balance:</b>
		</font>
	 </td>
	 <td>
	  <beanlib:WrapperValue dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR_BAL %>" format="$#,##0.00;($#,##0.00)"/>
	</td>
</tr>   			
</table>					