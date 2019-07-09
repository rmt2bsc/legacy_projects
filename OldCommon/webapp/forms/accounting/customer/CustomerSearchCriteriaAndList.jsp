<%@ page import="com.bean.criteria.CustomerCriteria" %>
 <%
	 CustomerCriteria query = (CustomerCriteria) QUERY_BEAN.getCustomObj();
	 //request.setAttribute("SearchCriteria", query);
	 String customerType = query.getQry_CustomerType() == null ?  "" :  query.getQry_CustomerType();		
	 String criteria =  QUERY_BEAN.getWhereClause();
	 String queryView = QUERY_BEAN.getQuerySource();
%>

  <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
  <db:datasource id="dso" 
				classId="com.api.DataSourceApi" 
				connection="con"
				query="<%=queryView%>"
				where="<%=criteria%>"
				order="name"
				type="xml"/>   
				
 <font size="4" style="color:blue">Selection Criteria</font>
 <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:90%; height:55px">
			<table width="30%" border="0">  
				<tr> 
					<td width="33%" bgcolor="#FFCC00"> 
						<div align="right"><font size="2"><b>Customer Type:</b></font></div>
					</td>
					<td width="67%">
						<select name="qry_CustomerType"  onChange="enableCustomerCriteriaControls(SearchForm, SearchForm.qry_CustomerType.value)">
							<option value="1" <%= customerType.equals("1") ? " selected" : "" %>>Business</option>              
							<option value="2" <%= customerType.equals("2") ? " selected" : "" %>>Personal</option>
						</select>
					</td>
				</tr>    
			</table>
		  
			<table width="30%" border="0">
				<tr> 
					<td width="10%" bgcolor="#FFCC00"> 
						<div align="right"><b><font size="2">Account Number :</font></b></div>
					</td>
					<td width="21%"> 
						<input type="text" name="qry_AccountNo" value="<%=query.getQry_AccountNo() == null ? "" : query.getQry_AccountNo()%>">
					</td>
				</tr>
			</table>
			<br>
		  
	
			<table width="90%" border="0">
				<caption align="left" style="color:blue">Business Criteria</caption>
				<tr> 
					<td width="10%" bgcolor="#FFCC00"> 
						<div align="right"><b><font size="2">Name:</font></b></div>
					</td>
					<td width="21%"> 
						<div align="left"> 
						  <input type="text" name="qry_Longname" value="<%=query.getQry_Longname() == null ? "" : query.getQry_Longname()%>">
						</div>
					</td>
					<td width="12%" bgcolor="#FFCC00"> 
						<div align="right"><b><font size="2">Tax Id:</font></b></div>
					</td>
					<td width="24%"> 
						<div align="left"> 
						  <input type="text" name="qry_TaxId" value="<%=query.getQry_TaxId() == null ? "" : query.getQry_TaxId()%>">
						</div>
					</td>
					<td width="12%" bgcolor="#FFCC00"> 
						<div align="right"><b><font size="2">Web Site:</font></b></div>
					</td>
					<td width="21%"> 
						<div align="left"> 
						  <input type="text" name="qry_Website" value="<%=query.getQry_Website() == null ? ""  : query.getQry_Website()%>">
						</div>
					</td>
				</tr>
				<tr> 
					<td width="10%" bgcolor="#FFCC00"> 
						<div align="right"><b><font size="2">Contact First Name:</font></b></div>
					</td>
					<td width="21%"> 
						<div align="left"> 
						  <input type="text" name="qry_ContactFirstname" value="<%=query.getQry_ContactFirstname() == null ? "" : query.getQry_ContactFirstname()%>">
						</div>
					</td>
					<td width="12%" bgcolor="#FFCC00"> 
						<div align="right"><b><font size="2">Contact Last Name:</font></b></div>
					</td>
					<td width="24%"> 
						<div align="left"> 
						  <input type="text" name="qry_ContactLastname" value="<%=query.getQry_ContactLastname() == null ? "" : query.getQry_ContactLastname()%>">
						</div>
					</td>
					<td width="12%" bgcolor="#FFCC00"> 
						<div align="right"><b><font size="2">Contact Phone:</font></b></div>
					</td>
					<td width="21%"> 
						<div align="left"> 
						  <input type="text" name="qry_ContactPhone" value="<%=query.getQry_ContactPhone() == null ? "" : query.getQry_ContactPhone()%>">
						</div>
					</td>
				</tr>
			</table>
			<br>
			
			<table width="90%" border="0">
				<caption align="left" style="color:blue">Personal Criteria</caption>
				<tr> 
					<td bgcolor="#FFCC00" width="12%"> 
						<div align="right"><font size="2"><b>First Name:</b></font></div>
					</td>
					<td width="19%">
					  <input type="text" name="qry_Firstname" value="<%=query.getQry_Firstname() == null ? "" : query.getQry_Firstname()%>">
					</td>
					<td bgcolor="#FFCC00" width="12%"> 
						<div align="right"><font size="2"><b>Middle Name:</b></font></div>
					</td>
					<td width="24%">
					    <input type="text" name="qry_Midname" value="<%=query.getQry_Midname() == null ? "" : query.getQry_Midname()%>">
					</td>
					<td bgcolor="#FFCC00" width="12%"> 
						<div align="right"><b><font size="2">Last Name:</font></b></div>
					</td>
					<td width="21%">
					   <input type="text" name="qry_Lastname" value="<%=query.getQry_Lastname() == null ? "" : query.getQry_Lastname()%>">
					</td>
				</tr>
				<tr> 
					<td bgcolor="#FFCC00" width="12%"> 
						<div align="right"><font size="2"><b>SSN:</b></font></div>
					</td>
					<td width="19%">
					   <input type="text" name="qry_Ssn" value="<%=query.getQry_Ssn() == null ? ""  : query.getQry_Ssn()%>">
					</td>
				<td bgcolor="#FFCC00" width="12%"> 
					<div align="right"><font size="2"><b><font size="2">Birth Date:</font></b></font></div>
				</td>
				<td width="24%">
				  <input type="text" name="qry_BirthDate" value="<%=query.getQry_BirthDate() == null ? "" : query.getQry_BirthDate()%>">
			  </td>
				<td width="12%"> 
					<div align="right"><b><font size="2">&nbsp;</font></b></div>
				</td>
				<td width="21%">&nbsp;</td>
			</tr>
			<tr> 
				<td bgcolor="#FFCC00" width="12%"> 
					<div align="right"><font size="2"><b>Email:</b></font></div>
				</td>
				<td colspan="3"> 
					<div align="left">
					  <input type="text" name="qry_Email" value="<%=query.getQry_Email() == null ? "" : query.getQry_Email()%>">
					</div>
				</td>
				<td width="12%"> 
					<div align="right"><b><font size="2"></font></b></div>
				</td>
				<td width="21%">&nbsp;</td>
			</tr>
		</table>
</div>	  

 <br>         
<font size="4" style="color:blue">Search Results</font>
<div style="border-style:groove; border-color:#999999; background-color:buttonface; width:90%; height:350px; overflow:auto">
<table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
		 <tr>
			 <th class="clsTableListHeader">&nbsp;</th>
			 <th class="clsTableListHeader"><div align="left">Account No.</div></th>
			 <th class="clsTableListHeader"><div align="left">Name </div></th>
			 <th class="clsTableListHeader"><div align="left">Credit Limit</div></th>
			 <th class="clsTableListHeader">Create Date</th>
			 <th class="clsTableListHeader">Update Date</th>
			 <th class="clsTableListHeader">Update User</th>
		 </tr>

		 <db:LoopRows dataSource="dso">
		  <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>

			 <td width="1%" align="center" class="clsTableListHeader">
				 <db:InputControl dataSource="dso" type="radio" name="selCbx" value="rowid"/>
				 <db:InputControl dataSource="dso" type="hidden" name="CustomerId" property="CustomerId" uniqueName="yes"/>         
				 <% 
					if (customerType.equals("1")) {
				  %>                         
					 <db:InputControl dataSource="dso" type="hidden" name="BusinessId" property="BusinessId" uniqueName="yes"/>                            
				  <%
					 }
				  %>
				 <% 
					if (customerType.equals("2")) {
				  %>                         
					 <db:InputControl dataSource="dso" type="hidden" name="PersonId" property="PersonId" uniqueName="yes"/>
				  <%
					 }
				  %>								  

				 <db:InputControl dataSource="dso" type="hidden" name="GlAccountId" property="GlAccountId" uniqueName="yes"/>                                  
			 </td>   
			 <td width="10%" align="left" >
				<font size="2">
				  <db:ElementValue dataSource="dso" property="AccountNo"/>
				</font>
			 </td>             
			 <td width="22%">
				<font size="2"> 
				  <db:ElementValue dataSource="dso" property="Name"/>
				  </font>
			 </td>
			 <td width="10%">
				 <font size="2">
				<db:ElementValue dataSource="dso" property="CreditLimit" format="$#,##0.00;($#,##0.00)"/>
				</font>
			 </td>
			 <td width="9%" align="center">
				 <font size="2">
				<db:ElementValue dataSource="dso" property="DateCreated" format="MM-dd-yyyy"/>
				</font>
			 </td>
			 <td width="9%" align="center" >
			 <font size="2">
				<db:ElementValue dataSource="dso" property="DateUpdated" format="MM-dd-yyyy"/>
				</font>
			 </td>
			 <td width="7%" align="center" >
				<font size="2">
				   <db:ElementValue dataSource="dso" property="UserId" />
				 </font>
			 </td>
		 </tr>
	  </db:LoopRows>
	  <% if (pageContext.getAttribute("ROW") == null) {
			out.println("<tr><td colspan=7 align=center>Data Not Found</td></tr>");
		 }
	  %>
	</table>
 </div>
