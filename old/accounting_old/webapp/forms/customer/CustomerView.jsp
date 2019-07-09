<!-- 
   This include provides base customer data for viewing and/or editing.
   It is expected to be used as a JSP include directive, meaning that it is inserted
   into another JSP at complie time.   
   
   This include requires the following data to be available on the user's request:
   1.  A entity bean of type, Customer, which is identified as "customer".
   2.  A Double data type identified as "customerbalance".
 -->
 
<table width="90%" border="0">
	<caption align="left"> <h3><%=pageTitle%></h3> </caption>
	<tr> 
		<td width="19%"  class="clsTableFormHeader"> 
			<div align="right">
			   <font size="2">
				 <b>Account Number:</b>
			   </font>
			</div>
		</td>	
		<td width="81%">
			  <beanlib:InputControl type="hidden" name="CustomerId" value="#customer.CustomerId"/>
		    <beanlib:InputControl value="#customer.AccountNo"/>
		</td>									 
	</tr>
	<tr> 
		<td width="19%"  class="clsTableFormHeader"> 
			<div align="right">
			   <font size="2">
				 <b>Balance:</b>
			   </font>
			</div>
		</td>	
		<td width="81%">
		   <beanlib:InputControl value="#customerbalance" format="$#,##0.00;($#,##0.00)"/>
		</td>									 
	</tr>				
	<tr> 
		<td width="19%"  class="clsTableFormHeader"> 
			<div align="right">
			   <font size="2">
				 <b>Credit Limit:</b>
			   </font>
			</div>
		</td>	
		<td width="81%">
		  <beanlib:InputControl value="#customer.CreditLimit" format="$#,##0.00;($#,##0.00)"/>
		</td>									 
	</tr>				
	<tr> 
		<td width="19%"  class="clsTableFormHeader"> 
			<div align="right">
			   <font size="2">
				 <b>Description:</b>
			   </font>
			</div>
		</td>	
		<td width="81%">
		   <beanlib:InputControl value="#customer.Description"/>
		</td>									 
	</tr>				
</table>