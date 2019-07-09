<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.criteria.PurchaseOrderCriteria" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.AccountingConst" %>
<%@ page import="com.constants.PurchasesConst" %>

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
	  String pageTitle = "Vendor Purchase Order Item Selector";
  %>

  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

     <form name="DataForm" method="POST" action="<%=APP_ROOT%>//reqeustProcessor/XactPurchase.VendorItemSelect">
           <beanlib:InputControl dataSource="vendor" type="hidden" name="VendorId" property="CreditorId"/>
           <beanlib:InputControl dataSource="po" type="hidden" name="PurchaseOrderId" property="Id"/>
        
            <!-- Begin Header Section -->
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
								 <tr>
										 <td width="15%" class="clsTableFormHeader">Vendor Account Number: </td>
										 <td width="85%">
												 <beanlib:ElementValue dataSource="vendor" property="AccountNo" />
										 </td>
								</tr>
								<tr>
										 <td class="clsTableFormHeader">Vendor Name: </td>
										 <td>
												 <beanlib:ElementValue dataSource="vendor" property="Longname" />
										 </td>
								</tr>
								<tr>
										 <td class="clsTableFormHeader">Purchase Order: </td>
										 <td>
												 <beanlib:ElementValue dataSource="po" property="Id" />
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
																   	  <th width="13%" class="clsTableFormHeader" style="text-align:left">Item No.</th>
																   	  <th width="60%" class="clsTableFormHeader" style="text-align:left">Item Description</th>
																   	  <th width="15%" class="clsTableFormHeader" style="text-align:right">Unit Cost</th>
																   	  <th width="10%" class="clsTableFormHeader">&nbsp;</th>
																   </tr>
																<beanlib:LoopRows bean="item" list="AvailItems">
                                                                      <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
																			<td class="clsTableListHeader">
																					<beanlib:InputControl dataSource="item" type="checkbox" name="selCbx" value="rowid"/>
																					<beanlib:InputControl dataSource="item" type="hidden" name="ItemMasterId" property="ItemMasterId" uniqueName="yes"/>
																			</td>	
																			<td>
																					<beanlib:ElementValue dataSource="item" property="itemMasterId"/>
																			</td>
																			<td>
																					<beanlib:ElementValue dataSource="item" property="Description"/>
																			</td>
																			<td align="right">
																					<beanlib:ElementValue dataSource="item" property="UnitCost" format="#,##0.00;(#,##0.00)"/>
																			</td>
																			<td>&nbsp;</td>
																	 </tr>
																</beanlib:LoopRows>		
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
										 <input name="<%=PurchasesConst.REQ_SEARCH%>" type="button" value="Search" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
										 <input name="<%=PurchasesConst.REQ_SAVENEWITEM%>" type="button" value="Next" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
									</td>
							</tr>
						</table>
						<input name="clientAction" type="hidden">
     </form>
 </body>
</html>
