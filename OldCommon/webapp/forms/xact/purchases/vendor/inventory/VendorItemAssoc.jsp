<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.constants.ItemConst" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Transaction Search Criteria</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>
       
  <%
	  String pageTitle = "Vendor Item Inventory Association Maintenance";
	  String overrideInd = null;
  %>
  
				
  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

     <!--  Begin Inventory Available Section -->
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactPurchase.VendorItemAssoc">
        <beanlib:InputControl dataSource="vendor" type="hidden" name="VendorId" property="CreditorId"/>
        <beanlib:InputControl dataSource="vendor" type="hidden" name="CreditorId" property="CreditorId"/>
        <beanlib:InputControl dataSource="vendor" type="hidden" name="BusinessId" property="BusinessId"/>

        <table width="100%" cellpadding="0" cellspacing="0">
           <tr>
             <td>
								<font size="4" style="color:blue">Available Inventory Items</font>
								<div style="border-style:groove;border-color:#999999; background-color:buttonface; width:80%; height:300px; overflow:auto">
										<table width="100%" cellpadding="0" cellspacing="0"  border="0">
											 <tr>
													<th width="3%" class="clsTableListHeader">&nbsp;</th>
													<th width="10%" class="clsTableFormHeader" style="text-align:left">Item No.</th>
													<th width="70%" class="clsTableFormHeader" style="text-align:left">Item Description</th>
													<th width="12%" class="clsTableFormHeader" style="text-align:right">Unit Cost</th>
													<th width="5%" class="clsTableFormHeader" style="text-align:right">&nbsp;</th>
											 </tr>

											 <beanlib:LoopRows bean="availBean" list="availList"> 
												 <tr>
														<gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
														<td align="center" class="clsTableListHeader">
															 <beanlib:InputControl dataSource="availBean" type="checkbox" name="UnAssignedtems" property="Id"/>
														</td>   
														<td>
															<font size="2">
																 <beanlib:ElementValue dataSource="availBean" property="Id"/>
															</font>
														</td>
														<td>
															<font size="2">
																 <beanlib:ElementValue dataSource="availBean" property="Description"/>
															</font>
														</td>
														<td  align="right">
															<font size="2">
																 <beanlib:ElementValue dataSource="availBean" property="UnitCost" format="$#,##0.00;($#,##0.00)"/>
															</font>
														</td>
														<td>&nbsp;</td>
												 </tr>
											 </beanlib:LoopRows> 
										</table>
								</div>
						 </td>
					 </tr>
           <br>
        
           <!-- Display associative command buttons -->  
           <tr>
              <td align="center">
                 <table width="100%" cellpadding="0" cellspacing="0">
									 <tr>
											<td colspan="2">
												 <input name="<%= ItemConst.REQ_VEND_ITEM_ASSIGN %>" type="button" value="Assign" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
											</td>
									 </tr>		
								 </table>            
							</td>
					 </tr>
					 <br>
                     <tr><td>&nbsp;</td></tr>
                     <tr><td>&nbsp;</td></tr>
                     
           <!--  Begin Inventory Assigned section -->
           <tr>
              <td>
								<font size="4" style="color:blue">Assigned Inventory Items</font>
								<div style="border-style:groove; border-color:#999999; background-color:buttonface; width:80%; height:300px; overflow:auto">
									<table width="100%" cellpadding="0" cellspacing="0" border="0">
										<tr>
											 <th width="3%" class="clsTableListHeader">&nbsp;</th>
											 <th width="10%" class="clsTableListHeader" style="text-align:left">Item No.</th>
											 <th width="30%" class="clsTableListHeader"  style="text-align:left">Item Description</th>
											 <th width="2%" class="clsTableListHeader">&nbsp;</th>
											 <th width="18%" class="clsTableListHeader" style="text-align:left">Serial No.</th>
											 <th width="18%" class="clsTableListHeader" style="text-align:left">Vendor Item No.</th>
											 <th width="10%" class="clsTableListHeader" style="text-align:right">Unit Cost</th>
											 <th width="7%" class="clsTableListHeader" style="text-align:center">Override</th>
											 <th width="2%" class="clsTableListHeader">&nbsp;</th>
										</tr>

										<beanlib:LoopRows beanClassId="com.bean.VwVendorItems" bean="assignBean" list="assignList"> 
											 <tr>
													<gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
													<td class="clsTableListHeader">
														 <beanlib:InputControl dataSource="assignBean" type="checkbox" name="AssignedItems" property="ItemMasterId"/>
													<td>
														 <font size="2">
																<beanlib:ElementValue dataSource="assignBean" property="ItemMasterId"/> 
														 </font>
													</td>
													<td>
														 <font size="2">
																<beanlib:ElementValue dataSource="assignBean" property="Description"/> 
														 </font>
													</td>
													<td align="right">&nbsp;</td>
													<td>
														 <font size="2">
																<beanlib:ElementValue dataSource="assignBean" property="ItemSerialNo"/> 
														 </font>
													</td>
													<td>
														 <font size="2">
																<beanlib:ElementValue dataSource="assignBean" property="VendorItemNo"/> 
														 </font>
													</td>			        
													<td align="right">
															<font size="2">
																 <beanlib:ElementValue dataSource="assignBean" property="UnitCost" format="$#,##0.00;($#,##0.00)"/> 
															</font>
													</td>
													<td align="center">
													    <%
													        if (assignBean.getOverrideRetail() == ItemConst.ITEM_OVERRIDE_YES) {
													            overrideInd = "Yes";
													        }
													        else {
													             overrideInd = "No";
													        }
													    %>
															<font size="2">
																 <%=overrideInd%>
															</font>
													</td>													
													<td>&nbsp;</td>
											 </tr>
										</beanlib:LoopRows> 
									</table>
								</div>       
							</td>
					 </tr>
           <br>
        
				   <!-- Display command buttons -->  
				   <tr>
				      <td align="left">
                 <table width="100%" cellpadding="0" cellspacing="0">
									 <tr>   
											<td colspan="2">
											     <input name="<%= ItemConst.REQ_VEND_ITEM_UNASSIGN %>" type="button" value="Unassign" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
												 <input name="<%= ItemConst.REQ_VEND_ITEM_EDIT %>" type="button" value="Edit" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
												 <input name="<%= ItemConst.REQ_VEND_ITEM_OVERRIDE %>" type="button" value="Override" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
												 <input name="<%= ItemConst.REQ_VEND_ITEM_OVERRIDEREMOVE %>" type="button" value="Remove Override" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
												 <input name="<%= ItemConst.REQ_SEARCH %>" type="button" value="Back" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
											</td>
									 </tr>		
								 </table>            
							</td>
					 </tr>
		    <input name="clientAction" type="hidden">
     </form>
   </body>
</html>
