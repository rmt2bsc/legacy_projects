<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.xact.purchases.vendor.inventory.VendorItemConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

  <%
	  String pageTitle = "Vendor Item Inventory Association Maintenance";
	  String overrideInd = null;
  %>
  
<html>
  <head>
    <title><%=pageTitle%></title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	  <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>
       

  
				
  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

     <!--  Begin Inventory Available Section -->
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/PurchasesVendor.ItemAssoc">
        <beanlib:InputControl type="hidden" name="VendorId" value="#vendor.CreditorId"/>
        <beanlib:InputControl type="hidden" name="CreditorId" value="#vendor.CreditorId"/>
        <beanlib:InputControl type="hidden" name="BusinessId" value="#vendor.BusinessId"/>

        <table width="100%" cellpadding="0" cellspacing="0">
           <tr>
             <td>
								<font size="4" style="color:blue">Available Inventory Items</font>
								<div style="border-style:groove;border-color:#999999; background-color:buttonface; width:40%; height:300px; overflow:auto">
										<table width="100%" cellpadding="0" cellspacing="0"  border="0">
											 <tr>
													<th width="3%" class="clsTableListHeader">&nbsp;</th>
													<th width="15%" class="clsTableFormHeader" style="text-align:center">Item No.</th>
													<th width="65%" class="clsTableFormHeader" style="text-align:left">Item Description</th>
													<th width="12%" class="clsTableFormHeader" style="text-align:right">Unit Cost</th>
													<th width="5%" class="clsTableFormHeader" style="text-align:right">&nbsp;</th>
											 </tr>

											 <beanlib:LoopRows bean="availBean" list="availList"> 
												 <tr>
														<gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
														<td align="center" class="clsTableListHeader">
															 <beanlib:InputControl type="checkbox" name="UnAssignedtems" value="#availBean.ItemId"/>
														</td>   
														<td align="center">
															<font size="2">
																 <beanlib:InputControl value="#availBean.ItemId"/>
															</font>
														</td>
														<td>
															<font size="2">
																 <beanlib:InputControl value="#availBean.Description"/>
															</font>
														</td>
														<td  align="right">
															<font size="2">
																 <beanlib:InputControl value="#availBean.UnitCost" format="$#,##0.00;($#,##0.00)"/>
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
												 <input name="<%= VendorItemConst.REQ_VEND_ITEM_ASSIGN %>" type="button" value="Assign" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
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

										<beanlib:LoopRows bean="assignBean" list="assignList"> 
											 <tr>
													<gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
													<td class="clsTableListHeader">
														 <beanlib:InputControl type="checkbox" name="AssignedItems" value="#assignBean.ItemId"/>
													<td>
														 <font size="2">
																<beanlib:InputControl value="#assignBean.ItemId"/> 
														 </font>
													</td>
													<td>
														 <font size="2">
																<beanlib:InputControl value="#assignBean.Description"/> 
														 </font>
													</td>
													<td align="right">&nbsp;</td>
													<td>
														 <font size="2">
																<beanlib:InputControl value="#assignBean.ItemSerialNo"/> 
														 </font>
													</td>
													<td>
														 <font size="2">
																<beanlib:InputControl value="#assignBean.VendorItemNo"/> 
														 </font>
													</td>			        
													<td align="right">
															<font size="2">
																 <beanlib:InputControl value="#assignBean.UnitCost" format="$#,##0.00;($#,##0.00)"/> 
															</font>
													</td>
													<td align="center">
													    <gen:Evaluate expression="#assignBean.OverrideRetail">
													       <gen:When expression="1">
													         <font size="2">Yes</font>
													       </gen:When>
													       <gen:WhenElse><font size="2">No</font></gen:WhenElse>
													    </gen:Evaluate>
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
											   <input name="<%= VendorItemConst.REQ_VEND_ITEM_UNASSIGN %>" type="button" value="Unassign" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
												 <input name="<%= VendorItemConst.REQ_VEND_ITEM_EDIT %>" type="button" value="Edit" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
												 <input name="<%= VendorItemConst.REQ_VEND_ITEM_OVERRIDE %>" type="button" value="Override" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
												 <input name="<%= VendorItemConst.REQ_VEND_ITEM_OVERRIDEREMOVE %>" type="button" value="Remove Override" style="width:120" onClick="handleAction('_self', document.DataForm, this.name)">
												 <input name="<%= GeneralConst.REQ_SEARCH %>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
											</td>
									 </tr>		
								 </table>            
							</td>
					 </tr>
		    <input name="clientAction" type="hidden">
     </form>
   </body>
</html>
