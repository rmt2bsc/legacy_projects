    <table width="60%" border="0">
			 <tr> 
				<td align="right" width="30%" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Vendor Account Number</b>
					 </font>    
				</td>
				<td width="70%">
				   <beanlib:InputControl value="#vendor.AccountNo"/> 
				</td>
			</tr>          
			<tr> 
				<td align="right" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Vendor Name</b>
					</font>
				 </td>
				 <td>
				     <beanlib:InputControl value="#vendor.Name"/> 
				 </td>
			</tr>   
			<tr> 
				<td align="right" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Purchase Order Number</b>
					</font>
				 </td>
				 <td>
				    <beanlib:InputControl value="#po.PoId"/> 
				</td>
			</tr>   			
			<tr> 
				<td align="right" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Purchase Order Status</b>
					</font>
				 </td>
				 <td>
				    <beanlib:InputControl value="#pos.Description"/> 
				</td>
			</tr>   						
			<tr> 
				<td align="right" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Date Created</b>
					</font>
				</td>
				<td>
				   <beanlib:InputControl value="#po.DateCreated" format="MM-dd-yyyy HH:mm:ss"/>
				</td>
			</tr>    			
			<tr> 
				<td align="right" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Purchase Order Total</b>
					</font>
				</td>
				<td>
				   <beanlib:InputControl value="#po.Total" format="#,##0.00;(#,##0.00)"/>
				</td>
			</tr>    						
			<tr> 
				<td align="right" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Reference No.</b>
					</font>
				</td>
				<td>
				   <beanlib:InputControl value="#po.RefNo" format="#,##0.00;(#,##0.00)"/>
				</td>
			</tr>    					
		</table>