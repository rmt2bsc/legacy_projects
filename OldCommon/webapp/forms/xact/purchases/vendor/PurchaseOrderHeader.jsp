    <table width="60%" border="0">
			 <tr> 
				<td align="right" width="30%" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Vendor Account Number</b>
					 </font>    
				</td>
				<td width="70%">
				   <beanlib:ElementValue dataSource="vendor" property="AccountNo"/> 
				</td>
			</tr>          
			<tr> 
				<td align="right" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Vendor Name</b>
					</font>
				 </td>
				 <td>
				     <beanlib:ElementValue dataSource="vendor" property="Longname"/> 
				 </td>
			</tr>   
			<tr> 
				<td align="right" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Purchase Order Number</b>
					</font>
				 </td>
				 <td>
				    <beanlib:ElementValue dataSource="po" property="Id"/> 
				</td>
			</tr>   			
			<tr> 
				<td align="right" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Purchase Order Status</b>
					</font>
				 </td>
				 <td>
				    <beanlib:ElementValue dataSource="pos" property="Description"/> 
				</td>
			</tr>   						
			<tr> 
				<td align="right" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Date Created</b>
					</font>
				</td>
				<td>
				   <beanlib:ElementValue dataSource="po" property="DateCreated" format="MM-dd-yyyy HH:mm:ss"/>
				</td>
			</tr>    			
			<tr> 
				<td align="right" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Purchase Order Total</b>
					</font>
				</td>
				<td>
				   <beanlib:ElementValue dataSource="po" property="Total" format="#,##0.00;(#,##0.00)"/>
				</td>
			</tr>    						
			<tr> 
				<td align="right" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Reference No.</b>
					</font>
				</td>
				<td>
				   <beanlib:ElementValue dataSource="po" property="RefNo" format="#,##0.00;(#,##0.00)"/>
				</td>
			</tr>    					
		</table>