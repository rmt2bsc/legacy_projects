<table width="90%" border="0">
 <tr> 
	<td align="left" width="20%"> 
		<font size="2">
			<b>Account Number:</b>
		 </font>
	</td>
	<td width="80%">
	   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" property="AccountNo"/> 
	</td>
</tr>    
<tr> 
	<td align="left"> 
		<font size="2">
			<b>Account Name:</b>
		</font>
	 </td>
	 <td>
		 <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" property="Name"/> 
	 </td>
</tr>   
<tr> 
	<td align="left"> 
		<font size="2">
			<b>Balance:</b>
		</font>
	 </td>
	 <td>
		<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_SALESORDER%>" property="OrderTotal" format="$#,##0.00;($#,##0.00)"/> 
	</td>
</tr>   			
</table>					