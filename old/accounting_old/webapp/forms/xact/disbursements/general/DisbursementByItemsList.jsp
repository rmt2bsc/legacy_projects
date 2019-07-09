<table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
	 <tr>
		 <th width="5%" class="clsTableListHeader" style="text-align:left">Item Id</th>
		 <th width="22%" class="clsTableListHeader" style="text-align:left">Expense Type</th>
		 <th width="15%" class="clsTableListHeader" style="text-align:left">Item Name</th>
		 <th width="7%" class="clsTableListHeader" style="text-align:right">Item Amount</th>
		 <th width="7%" class="clsTableListHeader" style="text-align:right">Trans. Date</th>
		 <th width="9%" class="clsTableListHeader" style="text-align:right">Trans. Amount</th>
		 <th width="2%" class="clsTableListHeader">&nbsp;</th>
		 <th width="12%" class="clsTableListHeader">Confirmation No.</th>
		 <th width="23%" class="clsTableListHeader" style="text-align:left">Trans. Source/Reason</th>
	 </tr>

	 <beanlib:LoopRows bean="beanObj" list="<%=DisbursementsConst.CLIENT_DATA_LIST %>">
	  <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
		<td align="center" >
		   <font size="2">
 			  <beanlib:InputControl value="#beanObj.Id"/>
		   </font>
		</td>
		<td align="left" >
			<font size="2">
			   <beanlib:InputControl value="#beanObj.XactTypeItemName"/>
			</font>
		</td>
		<td align="left" >
			<font size="2">
			   <beanlib:InputControl value="#beanObj.ItemName"/>
			</font>
		</td>             

		<td align="right">
			<font size="2">
			   <beanlib:InputControl value="#beanObj.ItemAmount" format="$#,##0.00;($#,##0.00)"/>
			</font>
		</td>
		<td align="right">
		   <font size="2"> 
		      <beanlib:InputControl value="#beanObj.XactDate" format="MM-dd-yyyy"/>
		   </font>
		</td>
		<td align="right">
			<font size="2">
			   <beanlib:InputControl value="#beanObj.XactAmount" format="$#,##0.00;($#,##0.00)"/>
			</font>
		</td>
		<td align="right">&nbsp;</td>
		<td align="left">
			<font size="2">
			   <beanlib:InputControl value="#beanObj.ConfirmNo"/>
			</font>
		</td>							 
		<td align="left" >
			<font size="2">
			   <beanlib:InputControl value="#beanObj.Reason"/>
			</font>
		</td>							
	 </tr>
	 </beanlib:LoopRows>

	 <% 
	    if (pageContext.getAttribute("ROW") == null) {
		   out.println("<tr><td colspan=9 align=center>Data Not Found</td></tr>");
		}
	 %>
 </table>	  