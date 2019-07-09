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
			<td width="10%" class="clsTableFormHeader">Account Number:</td>
			<td width="15%">
			    <input type="hidden" name="CreditorId">
			    <input type="hidden" name="AccountNumber">
			</td>
			<td width="10%" class="clsTableFormHeader">Credit Limit:</td>
			<td width="15%"> 
			   <input type="text" name="CreditLimit" value="0.00" tabIndex="1">
			</td>
			<td width="10%" class="clsTableFormHeader">Balance:</td>
			<td width="15%">0.00</td>					
			<td width="10%" class="clsTableFormHeader">Creditor Type:</td>
			<td width="15%"> 
				 <db:InputControl dataSource="credTypeDso"
													 type="select"
													 name="CreditorTypeId"
													 tabIndex="2"
													 codeProperty="CreditorTypeId"
													 displayProperty="Description"/>
			</td>
			
		</tr>								
	</table>