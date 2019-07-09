<!-- 
   This include provides base creditor data for viewing and/or editing.
   It is expected to be used as a JSP include directive, meaning that it is inserted
   into another JSP at complie time.   
   
   This include requires the following data to be available on the user's request:
   1.  A entity bean of type, Creditor, which is identified as "customer".
   2.  A Double data type identified as "creditorbalance".
 -->

	<table width="80%" border="0">			
		 <caption align="left"><h3><%=pageTitle%></h3> </caption>
		<tr>
			<td width="12%" class="clsTableFormHeader">Account Number:</td>
			<td width="21%">
			   <beanlib:InputControl value="#creditor.AccountNumber"/>
			   <beanlib:InputControl type="hidden" name="CreditorId" value="#creditor.CreditorId"/>
			</td>
			<td width="12%" class="clsTableFormHeader">Credit Limit:</td>
			<td width="21%"> 
				<beanlib:InputControl value="#creditor.CreditLimit"/>						   
			</td>
			<td width="12%" class="clsTableFormHeader">Balance:</td>
			<td width="21%"> 
			   <beanlib:InputControl value="#creditorbalance" format="$#,##0.00;($#,##0.00)"/>
			</td>					
		</tr>								
	</table>
	<br>
