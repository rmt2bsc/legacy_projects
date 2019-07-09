<%@ page import="com.bean.criteria.CustomerCriteria" %>

 <font size="4" style="color:blue">Selection Criteria</font>
 <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:60%; height:55px">
		  
			<table width="40%" border="0">
				<tr> 
					<td width="10%" bgcolor="#FFCC00"> 
						<div align="right"><b><font size="2">Account Number :</font></b></div>
					</td>
					<td width="21%"> 
					  <beanlib:InputControl type="text" name="qry_AccountNo" value="#QUERY_BEAN.CustomObj.qry_AccountNo"/>
					</td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
				   <td colspan="2" align="left"><strong>-- OR --</strong></td>
				</tr>
			</table>
			<br>
		  
			<table width="90%" border="0">
			  <tr>
				    <td colspan="6"><font color="blue">Business Contact Information</font></td>
				</tr>
				<tr> 
					<td width="10%" bgcolor="#FFCC00"> 
						<div align="right"><b><font size="2">Name:</font></b></div>
					</td>
					<td width="21%"> 
						<div align="left"> 
						  <beanlib:InputControl type="text" name="qry_Name" value="#QUERY_BEAN.CustomObj.qry_Name" size="30"/>
						</div>
					</td>
					<td width="12%" bgcolor="#FFCC00"> 
						<div align="right"><b><font size="2">Tax Id:</font></b></div>
					</td>
					<td width="24%"> 
						<div align="left"> 
						   <beanlib:InputControl type="text" name="qry_TaxId" value="#QUERY_BEAN.CustomObj.qry_TaxId"/>
						</div>
					</td>
					<td width="12%" bgcolor="#FFCC00"> 
						<div align="right"><b><font size="2">Phone:</font></b></div>
					</td>
					<td width="21%"> 
						<div align="left"> 
						  <beanlib:InputControl type="text" name="qry_PhoneMain" value="#QUERY_BEAN.CustomObj.qry_PhoneMain"/>
						</div>
					</td>
				</tr>
			</table>
			<br>
</div>	
<br>  
<!-- Display Search Criteria command buttons -->         
<table width="100%" cellpadding="0" cellspacing="0">
  <tr>
		<td colspan="2">
		  <input name="<%=GeneralConst.REQ_SEARCH%>" type="button" value="Search" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
		  <input name="<%=GeneralConst.REQ_RESET%>" type="reset" value="Clear" style="width:90">
		</td>
  </tr>		
</table>

 <br>         
<font size="4" style="color:blue">Search Results</font>
<div style="border-style:groove; border-color:#999999; background-color:buttonface; width:70%; height:420px; overflow:auto">
<table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
		 <tr>
			 <th width="2%" class="clsTableListHeader">&nbsp;</th>
			 <th width="8%" class="clsTableListHeader"><div align="center">Id</div></th>
			 <th width="10%" class="clsTableListHeader"><div align="left">Account No.</div></th>
			 <th width="30%" class="clsTableListHeader"><div align="left">Customer Name </div></th>
			 <th width="10%" class="clsTableListHeader"><div align="left">Balance </div></th>
			 <th width="10%" class="clsTableListHeader"><div align="left">Credit Limit</div></th>
			 <th width="10%" class="clsTableListHeader">Create Date</th>
			 <th width="10%" class="clsTableListHeader">Update Date</th>
			 <th width="10%" class="clsTableListHeader">Update User</th>
		 </tr>

		 <beanlib:LoopRows bean="item" list="<%=GeneralConst.CLIENT_DATA_LIST %>">
		  <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>

			 <td align="center" class="clsTableListHeader">
				 <beanlib:InputControl type="radio" name="selCbx" value="rowid"/>
			 </td>   
			 <td align="center" >
				<font size="2">
				  <beanlib:InputControl value="#item.CustomerId"/>
				  <beanlib:InputControl type="hidden" name="CustomerId" value="#item.CustomerId" uniqueName="yes"/>         
				</font>
			 </td>             			 
			 <td align="left" >
				<font size="2">
				  <beanlib:InputControl value="#item.AccountNo"/>
				</font>
			 </td>             
			 <td>
				<font size="2"> 
				  <beanlib:InputControl value="#item.Name"/>
				  </font>
			 </td>
			 <td>
				<font size="2"> 
				  <beanlib:InputControl value="#item.Balance" format="#,##0.00"/>
				  </font>
			 </td>
			 <td>
				 <font size="2">
				<beanlib:InputControl value="#item.CreditLimit" format="$#,##0.00;($#,##0.00)"/>
				</font>
			 </td>
			 <td align="center">
				 <font size="2">
				<beanlib:InputControl value="#item.DateCreated" format="MM-dd-yyyy"/>
				</font>
			 </td>
			 <td align="center" >
			 <font size="2">
				<beanlib:InputControl value="#item.DateUpdated" format="MM-dd-yyyy"/>
				</font>
			 </td>
			 <td align="center" >
				<font size="2">
				   <beanlib:InputControl value="#item.UserId" />
				 </font>
			 </td>
		 </tr>
	  </beanlib:LoopRows>
	  <% if (pageContext.getAttribute("ROW") == null) {
			out.println("<tr><td colspan=7 align=center>Data Not Found</td></tr>");
		 }
	  %>
	</table>
 </div>
