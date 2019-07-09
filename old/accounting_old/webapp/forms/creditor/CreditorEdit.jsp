<!-- 
   This include provides base creditor data for viewing and/or editing.
   It is expected to be used as a JSP include directive, meaning that it is inserted
   into another JSP at complie time.   
   
   This include requires the following data to be available on the user's request:
   1.  A entity bean of type, Creditor, which is identified as "customer".
   2.  A Double data type identified as "creditorbalance".
 -->
 
    <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
 
     <db:datasource id="credTypeDso" 
                    classId="com.api.DataSourceApi" 
                    connection="con"
						        query="CreditorTypeView"
						        order="description"
						        type="xml"/>
						  
	<table width="80%" border="0">			
		 <caption align="left"><h3><%=pageTitle%></h3></caption>
		<tr>
			<td width="12%" class="clsTableFormHeader">Account Number:</td>
			<td width="21%">
			   <beanlib:InputControl type="hidden" name="CreditorId" value="#creditor.CreditorId"/>													
			   <beanlib:InputControl type="hidden" name="AccountNumber" value="#creditor.AccountNumber"/>
			   <beanlib:InputControl value="#creditor.AccountNumber"/>
			</td>
			<td width="12%" class="clsTableFormHeader">Credit Limit:</td>
			<td width="21%"> 
				<beanlib:InputControl value="#creditor.CreditLimit" format="$#,##0.00;($#,##0.00)"/>						   
			</td>
			<td width="12%" class="clsTableFormHeader">Balance:</td>
			<td width="21%"> 
			   <beanlib:InputControl value="#creditorbalance" format="$#,##0.00;($#,##0.00)"/>
			</td>					
		</tr>				
		
		<tr>
			<td width="12%" class="clsTableFormHeader">APR:</td>
			<td width="21%">
				<beanlib:InputControl type="text" name="Apr" value="#creditor.Apr" tabIndex="1"/>
			</td>
			<td width="12%" class="clsTableFormHeader">Creditor Type:</td>
			<td width="24%"> 
				 <db:InputControl dataSource="credTypeDso"
													 type="select"
													 name="CreditorTypeId"
													 codeProperty="CreditorTypeId"
													 displayProperty="Description"
                                                     tabIndex="2"
													 selectedValue="#creditor.CreditorTypeId"/>
			</td>
			<td width="12%" class="clsTableFormHeader">Credit Limit:</td>
			<td width="21%"> 
			   <beanlib:InputControl type="text" name="CreditLimit" value="#creditor.CreditLimit" tabIndex="3"/>													
			</td>
		</tr>					
	</table>