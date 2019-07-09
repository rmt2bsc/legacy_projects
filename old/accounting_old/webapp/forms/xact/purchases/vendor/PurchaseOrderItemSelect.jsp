<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.criteria.PurchaseOrderCriteria" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.xact.purchases.vendor.VendorPurchasesConst" %>

<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>
<%
  String pageTitle = "Vendor Purchase Order Item Selector";
%>
<html>
  <head>
    <title><%=pageTitle %></title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>

  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/PurchasesVendor.ItemSelect">
           <beanlib:InputControl type="hidden" name="VendorId" value="#vendor.CreditorId"/>
           <beanlib:InputControl type="hidden" name="PurchaseOrderId" value="#po.PoId"/>
        
            <!-- Begin Header Section -->
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
								 <tr>
										 <td width="15%" class="clsTableFormHeader">Vendor Account Number: </td>
										 <td width="85%">
												 <beanlib:InputControl value="#vendor.AccountNo" />
										 </td>
								</tr>
								<tr>
										 <td class="clsTableFormHeader">Vendor Name: </td>
										 <td>
												 <beanlib:InputControl value="#vendor.Name" />
										 </td>
								</tr>
								<tr>
										 <td class="clsTableFormHeader">Purchase Order: </td>
										 <td>
												 <beanlib:InputControl value="#po.PoId" />
										 </td>
								</tr>												
						</table>
						<br>
						
						<!-- Display Items Section -->
						<table width="100%" border="0">
									<caption align="left">
											<font color="blue">Vendor Items Available to Order</font>
									</caption>
									<tr>
											 <td>
														<div style="border-style:groove; border-color:#999999; background-color:white; width:60%; height:480px; overflow:auto">
																<table width="100%" cellpadding="0" cellspacing="0">
																   <tr>
																   	  <th width="2%" class="clsTableFormHeader">&nbsp;</th>
																   	  <th width="13%" class="clsTableFormHeader" style="text-align:center">Item No.</th>
																   	  <th width="60%" class="clsTableFormHeader" style="text-align:left">Item Description</th>
																   	  <th width="15%" class="clsTableFormHeader" style="text-align:right">Unit Cost</th>
																   	  <th width="10%" class="clsTableFormHeader">&nbsp;</th>
																   </tr>
																<beanlib:LoopRows bean="item" list="AvailItems">
                                                                      <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
																			<td class="clsTableListHeader">
																					<beanlib:InputControl type="checkbox" name="selCbx" value="rowid"/>
																					<beanlib:InputControl type="hidden" name="ItemId" value="#item.ItemId" uniqueName="yes"/>
																			</td>	
																			<td align="center">
																					<beanlib:InputControl value="#item.itemId"/>
																			</td>
																			<td>
																					<beanlib:InputControl value="#item.Description"/>
																			</td>
																			<td align="right">
																					<beanlib:InputControl value="#item.UnitCost" format="#,##0.00;(#,##0.00)"/>
																			</td>
																			<td>&nbsp;</td>
																	 </tr>
																</beanlib:LoopRows>		
																 <%
												   	   		  if (pageContext.getAttribute("ROW") == null) {
												   	   		   	out.println("<tr><td colspan=50 align=center><font color=\"red\">Vendor does not have any items to select</font></td></tr>");
													   	   		}
															   %>
														    </table>														   
														</div>
											 </td>
									</tr>												
						</table>
            <br>

						<!-- Display command buttons -->
						<table width="100%" cellpadding="0" cellspacing="0">
							 <tr>
									<td>
										 <input name="<%=GeneralConst.REQ_SEARCH%>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
										 <%
						   	   		  if (pageContext.getAttribute("ROW") != null) {
						   	   	 %>	  
						   	   	      <input name="<%=VendorPurchasesConst.REQ_SAVENEWITEM%>" type="button" value="Next" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">										 		   
							   	   <% 
							   	   		}
									   %>
									</td>
							</tr>
						</table>
						<input name="clientAction" type="hidden">
     </form>
 </body>
</html>
