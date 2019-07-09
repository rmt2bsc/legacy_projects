<table width="35%" border="0">
 <tr> 
	<td align="right" width="30%"> 
		<font size="2">
			<b>Account Number:</b>
		 </font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#customer.AccountNo"/> 
	</td>
</tr>    
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Account Name:</b>
		</font>
	 </td>
	 <td width="65%">
		 <beanlib:InputControl value="#customer.Name"/> 
	 </td>
</tr>   
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Order Number:</b>
		</font>
	 </td>
	 <td width="65%">
		<beanlib:InputControl value="#salesorder.SoId"/> 
	</td>
</tr>   			
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Order Date:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#salesorder.DateCreated" format="MM-dd-yyyy H:m:s"/>
	</td>
</tr>   
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Order Status:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#salesorderstatus.Description"/>
	   <beanlib:InputControl type="hidden" value="#salesorderstatus.SoStatusId" name="OrderStatusId"/>
	</td>
</tr>   
<tr> 
	<td align="right" width="35%" valign="top"> 
		<font size="2">
			<b>Order/Invoice Reason:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#xact.Reason"/>
	   <beanlib:InputControl type="hidden" value="#xact.XactId" name="XactId"/>
	</td>
</tr>   
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Invoice No.:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#invoice.InvoiceNo"/>
	</td>
</tr> 			
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Invoice Date:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#invoice.DateCreated" format="MM-dd-yyyy H:m:s"/>
	</td>
</tr> 
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Reference No.:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#xact.EntityRefNo"/>
	</td>
</tr> 			  			 			
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Confirmation No.:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#xact.ConfirmNo"/>
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
		 <%String test = "";%>
	<tr>
		 <td>
			  <beanlib:InputControl value="#srvcItem.ItemId"/>
		 </td>
		 <td>
			  <beanlib:InputControl value="#srvcItem.ItemName"/>                                  
		 </td>
		 <td  align="center" valign="bottom">
			  <beanlib:InputControl value="#srvcItem.OrderQty" format="#,##0"/>                                  
		 </td>
		 <td  align="right" valign="bottom">
			  <beanlib:InputControl value="#srvcItem.RetailPrice" format="#,##0.00;(#,##0.00)"/>                                  
		 </td>
	 </tr>
 </beanlib:LoopRows>
 <beanlib:LoopRows bean="merchItem" list="<%=SalesConst.CLIENT_DATA_MERCHANDISE_ITEMS%>">
	<tr>
		 <td>
			  <beanlib:InputControl value="#merchItem.ItemId"/>                                  										 
		 </td>
		 <td>
			  <beanlib:InputControl value="#merchItem.ItemName"/>                                  
		 </td>
		 <td  align="center" valign="bottom">
    		  <beanlib:InputControl value="#merchItem.OrderQty" format="#,##0"/>    
		 </td>
		 <td  align="right" valign="bottom">
			  <beanlib:InputControl value="#merchItem.RetailPrice" format="#,##0.00;(#,##0.00)"/>                                  
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
		 <beanlib:InputControl value="#salesorder.OrderTotal" format="$#,##0.00;($#,##0.00)"/>
	 </td>
 </tr>
</table>					