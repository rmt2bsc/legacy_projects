<table width="35%" border="0">
 <tr> 
	<td align="right" width="30%"> 
		<font size="2">
			<b>Account Number:</b>
		 </font>
	</td>
	<td width="70%">
	   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" property="AccountNo"/> 
	</td>
</tr>    
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Account Name:</b>
		</font>
	 </td>
	 <td width="65%">
		 <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" property="Name"/> 
	 </td>
</tr>   
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Order Number:</b>
		</font>
	 </td>
	 <td width="65%">
		<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_SALESORDER%>" property="Id"/> 
	</td>
</tr>   			
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Order Date:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_SALESORDER%>" property="DateCreated" format="MM-dd-yyyy H:m:s"/>
	</td>
</tr>   
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Order Status:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_STATUS%>" property="Description"/>
	   <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_STATUS%>" type="hidden" property="Id" name="OrderStatusId"/>
	</td>
</tr>   
<tr> 
	<td align="right" width="35%" valign="top"> 
		<font size="2">
			<b>Order Reason:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_XACT%>" property="Reason"/>
	   <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_XACT%>" type="hidden" property="Id" name="XactId"/>
	</td>
</tr>   
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Invoice No.:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_INVOICE%>" property="InvoiceNo"/>
	</td>
</tr> 			
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Invoice Date:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_INVOICE%>" property="DateCreated" format="MM-dd-yyyy H:m:s"/>
	</td>
</tr> 
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Reference No.:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_XACT%>" property="EntityRefNo"/>
	</td>
</tr> 			  			 			
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Confirmation No.:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_XACT%>" property="ConfirmNo"/>
	</td>
</tr> 			  			 			
</table>
<br>

<table width="60% border="0"  cellspacing="0" cellpadding="0">
<tr>
	 <th width="15%" align="left" bgcolor="#FFCC00">
		<font size="2">Item No.</font>
	 </th>
	 <th width="45%" align="left" bgcolor="#FFCC00">
		<font size="2">Item Name</font>
	 </th>
	 <th width="15%"  align="center" bgcolor="#FFCC00">
		<font size="2">Order Qty</font>
	 </th>				 
	 <th width="25%"  align="right" bgcolor="#FFCC00">
		<font size="2">Price</font>
	 </th>
 </tr>
<beanlib:LoopRows bean="srvcItem" list="<%=SalesConst.CLIENT_DATA_SERVICE_ITEMS%>">
	<tr>
		 <td>
			  <beanlib:ElementValue dataSource="srvcItem" property="ItemMasterId"/>                                  										 
		 </td>
		 <td>
			  <beanlib:ElementValue dataSource="srvcItem" property="ItemName"/>                                  
		 </td>
		 <td  align="center" valign="bottom">
			  <beanlib:ElementValue dataSource="srvcItem" property="OrderQty" format="#,##0"/>                                  
		 </td>
		 <td  align="right" valign="bottom">
			  <beanlib:ElementValue dataSource="srvcItem" property="RetailPrice" format="#,##0.00;(#,##0.00)"/>                                  
		 </td>
	 </tr>
 </beanlib:LoopRows>
 <beanlib:LoopRows bean="merchItem" list="<%=SalesConst.CLIENT_DATA_MERCHANDISE_ITEMS%>">
	<tr>
		 <td>
			  <beanlib:ElementValue dataSource="merchItem" property="ItemMasterId"/>                                  										 
		 </td>
		 <td>
			  <beanlib:ElementValue dataSource="merchItem" property="ItemName"/>                                  
		 </td>
		 <td  align="center" valign="bottom">
    		  <beanlib:ElementValue dataSource="merchItem" property="OrderQty" format="#,##0"/>    
		 </td>
		 <td  align="right" valign="bottom">
			  <beanlib:ElementValue dataSource="merchItem" property="RetailPrice" format="#,##0.00;(#,##0.00)"/>                                  
		 </td>
	 </tr>
 </beanlib:LoopRows>			
  <tr>
	 <td colspan="4" align="right">
		 <img src="/images/clr.gif" height="10">
	 </td>
  </tr>			 
  <tr>
	 <td colspan="3" align="right">
		 <font size="2"><b>Order Total</b></font>
	 </td>
	 <td  align="right" valign="bottom">
		 <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_SALESORDER%>" property="OrderTotal" format="$#,##0.00;($#,##0.00)"/>
	 </td>
 </tr>
</table>					