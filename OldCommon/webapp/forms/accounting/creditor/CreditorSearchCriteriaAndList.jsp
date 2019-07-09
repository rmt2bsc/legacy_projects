		<jsp:useBean id="QUERY_BEAN" scope="session" class="com.bean.RMT2TagQueryBean"/>
		
    <%
		   CreditorCriteria cc = (CreditorCriteria) QUERY_BEAN.getCustomObj();
		   request.setAttribute("SearchCriteria", cc);
		   String creditorTypeStr = cc.getQry_CreditorTypeId();
    %>


    <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
    <db:datasource id="creditorTypesDso" classId="com.api.DataSourceApi" connection="con" query="CreditorTypeView" type="xml"/>    
		 

    <font size="3" style="color:blue">Selection Criteria</font>
	<div style="border-style:groove;border-color:#999999; background-color:buttonface; width:90%; height:55px">
		<table width="40%" border="0">
			<tr> 
				<td width="33%" bgcolor="#FFCC00"> 
					<div align="right"><font size="2"><b>Creditor Type:</b></font></div>
				</td>
				<td width="67%">
					<db:InputControl dataSource="creditorTypesDso" 
				                   type="select" 
							     				 name="qry_CreditorTypeId" 
									         codeProperty="Id" 
									         displayProperty="Description" 
									         selectedValue="<%=cc.getQry_CreditorTypeId()%>"/>
				</td>
			</tr>    
			<tr> 
				<td width="10%" bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Account Number :</font></b></div>
				</td>
				<td width="21%"> 
				  <input type="text" name="qry_AccountNo" value="<%=cc.getQry_AccountNo() == null ? "" : cc.getQry_AccountNo() %>">
				</td>
			</tr>				
		</table>
		<br>
	 
		<table width="100%" border="0">
			<tr> 
				<td width="14%" bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Name:</font></b></div>
				</td>
				<td width="19%"> 
					<div align="left"> 
					    <input type="text" name="qry_Longname" value="<%=cc.getQry_Longname() == null ? "" : cc.getQry_Longname() %>">
					</div>
				</td>
				<td width="14%" bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Tax Id:</font></b></div>
				</td>
				<td width="19%"> 
					<div align="left"> 
 					    <input type="text" name="qry_TaxId" value="<%=cc.getQry_TaxId() == null ? "" : cc.getQry_TaxId()%>">
					</div>
				</td>
				<td width="14%" bgcolor="#FFCC00"> 
					<div align="right">
					   <b><font size="2">Web Site:</font></b>
					</div>
				</td>
				<td width="19%"> 
					<div align="left"> 
					    <input type="text" name="qry_Website" value="<%=cc.getQry_Website() == null ? "" : cc.getQry_Website()%>">
					</div>
				</td>
			</tr>
			<tr> 
				<td bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Contact First Name:</font></b></div>
				</td>
				<td> 
					<div align="left"> 
					    <input type="text" name="qry_ContactFirstname" value="<%=cc.getQry_ContactFirstname() == null ? "" : cc.getQry_ContactFirstname()%>">
					</div>
				</td>
				<td bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Contact Last Name:</font></b></div>
				</td>
				<td> 
					<div align="left">
					    <input type="text" name="qry_ContactLastname" value="<%=cc.getQry_ContactLastname() == null ? "" : cc.getQry_ContactLastname()%>"> 
					</div>
				</td>
				<td bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Contact Phone:</font></b></div>
				</td>
				<td> 
					<div align="left"> 
					    <input type="text" name="qry_ContactPhone" value="<%=cc.getQry_ContactPhone() == null ? "" : cc.getQry_ContactPhone() %>"> 
					</div>
				</td>
			</tr>
		</table>
	  </div>	  
	 <br>         
		 
   <font size="3" style="color:blue">Search Results</font>
   <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:90%; height:350px; overflow:auto">
   		<table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
			 <tr>
				 <th width="3%" class="clsTableListHeader">&nbsp;</th>
				 <th width="7%" class="clsTableListHeader">Id</th>
				 <th width="25%" class="clsTableListHeader" style="text-align:left">Name</th>
				 <th width="15%" class="clsTableListHeader">Acct No.</th>
				 <th width="15%" class="clsTableListHeader" style="text-align:left">Type</th>
				 <th width="15%" class="clsTableListHeader" style="text-align:right">Credit Limit</th>
				 <th width="15%" class="clsTableListHeader" style="text-align:right">Balance</th>
				 
				 
			 </tr>
			 <beanlib:LoopRows bean="beanObj" list="<%=GeneralConst.CLIENT_DATA_LIST %>">
			     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
				 <td  align="center" class="clsTableListHeader">
					 <beanlib:InputControl dataSource="beanObj" type="radio" name="selCbx" value="rowid"/>
					 <beanlib:InputControl dataSource="beanObj" type="hidden" name="CreditorId" property="CreditorId" uniqueName="yes"/>                                  
					 <beanlib:InputControl dataSource="beanObj" type="hidden" name="BusinessId" property="BusinessId" uniqueName="yes"/>                                  
				 </td>   
				 <td  align="center" >
					<font size="2">
					  <beanlib:ElementValue dataSource="beanObj" property="CreditorId"/>
					</font>
				 </td>             
				 <td>
					 <font size="2">
						<beanlib:ElementValue dataSource="beanObj" property="Name"/>
					</font>
				 </td>							 
				 <td>
					 <font size="2">
						<beanlib:ElementValue dataSource="beanObj" property="AccountNo"/>
					 </font>
				 </td>					 
				 <td>
					 <font size="2">
						<beanlib:ElementValue dataSource="beanObj" property="CreditorTypeDescription"/>
					 </font>
				 </td>					 
				 <td align="right">
					<font size="2">
					  <beanlib:ElementValue dataSource="beanObj" property="CreditLimit" format="$#,##0.00;($#,##0.00)"/>
					</font>
				 </td>
				 <td align="right">
					 <font size="2">
						<beanlib:ElementValue dataSource="beanObj" property="Balance" format="$#,##0.00;($#,##0.00)"/>
					 </font>
				 </td>

				 </tr>
			 </beanlib:LoopRows>
			 <% if (pageContext.getAttribute("ROW") == null) {
					out.println("<tr><td colspan=11 align=center>Data Not Found</td></tr>");
				}
			 %>
		 </table>
      </div>
  <db:Dispose/>