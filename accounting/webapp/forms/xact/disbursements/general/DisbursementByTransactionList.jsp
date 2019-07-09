<table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
	 <tr>
		 <th width="3%" class="clsTableListHeader">&nbsp;</th>
		 <th width="7%" class="clsTableListHeader">Trans. Id</th>
		 <th width="5%" class="clsTableListHeader">&nbsp;</th>
		 <th width="7%" class="clsTableListHeader">Trans. Date</th>
		 <th width="7%" class="clsTableListHeader" style="text-align:right">Amount</th>
		 <th width="5%" class="clsTableListHeader">&nbsp;</th>
		 <th width="10%" class="clsTableListHeader" style="text-align:left">Date Entered</th>
		 <th width="10%" class="clsTableListHeader" style="text-align:left">Trans. Type</th>				 
		 <th width="15%" class="clsTableListHeader" style="text-align:left">Confirmation No.</th>
		 <th width="31%" class="clsTableListHeader" style="text-align:left">Trans. Source/Reason</th>
	 </tr>

	 <beanlib:LoopRows bean="beanObj" list="<%=DisbursementsConst.CLIENT_DATA_LIST %>">
	  <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
	 	<td align="center" class="clsTableListHeader"> 
	 	   <beanlib:InputControl dataSource="beanObj" type="radio" name="selCbx" value="rowid"/>
		</td>   
		<td align="center" >
		   <font size="2">
				 <beanlib:InputControl type="hidden" name="XactId" value="#beanObj.Id" uniqueName="yes"/> 
				 <beanlib:InputControl type="hidden" name="XactTypeId" value="#beanObj.XactTypeId" uniqueName="yes"/> 
				 <beanlib:InputControl value="#beanObj.Id"/>
		   </font>
		</td>        
		<td align="center">
		    <beanlib:InputControl type="hidden" name="DocumentId" value="#beanObj.DocumentId" uniqueName="yes"/>
				<gen:Evaluate expression="#beanObj.DocumentId">
				   <gen:When expression="0">&nbsp;</gen:When>
				   <gen:WhenElse>
				     <a href="<%=APP_ROOT%>/DocumentViewer.jsp?contentId=<beanlib:InputControl value='#beanObj.DocumentId'/>" target="_blank"><img src="<%=APP_ROOT%>/images/camera2.png" style="border: none" alt="View Cash Disbursement Reciept"/></a> 
				   </gen:WhenElse>
		    </gen:Evaluate>
		</td>     
		<td align="center">
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
		<td align="left" >
			<font size="2">
			   <beanlib:InputControl value="#beanObj.CreateDate" format="MM-dd-yyyy HH:mm:ss" />
			</font>
		</td>						
		<td align="left" >
			<font size="2">
			   <beanlib:InputControl value="#beanObj.XactTypeName"/>
			</font>
		</td>
		<td align="left" >
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