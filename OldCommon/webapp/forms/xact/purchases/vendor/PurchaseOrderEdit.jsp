<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.constants.PurchasesConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>
    
<html>
  <head>
    <title>Sales Order Item Selection</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>
  
   <%    
  	  String pageTitle = "Vendor Purchase Order - Edit";
  	  String msg = request.getAttribute("msg") == null ? "" : (String) request.getAttribute("msg");
	%>
	
  <body bgcolor="#FFFFFF" text="#000000">
       <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactPurchase.VendorDetails">
	     <beanlib:InputControl dataSource="vendor" type="hidden" name="VendorId" property="CreditorId"/>                                  
	     <beanlib:InputControl dataSource="vendor" type="hidden" name="CreditorId" property="CreditorId"/>                                  
		 <beanlib:InputControl dataSource="vendor" type="hidden" name="BusinessId" property="BusinessId"/>                                  
		 <beanlib:InputControl dataSource="po" type="hidden" name="PurchaseOrderId" property="Id"/>                                  
	      
		 <h3><strong><%=pageTitle%></strong></h3>
	    
	     <%@include file="/forms/xact/purchases/vendor/PurchaseOrderHeader.jsp"%>
  		 <br>
	  
		<table width="100%" border="3" bgcolor="#AACCCC" >
				<tr> 
					<td align="left" width="50%" bgcolor="#0000CC">
						 <font size="3" color="white">
							<b>Purchase Order Items</b>
						</font>
					 </td>
				</tr>
				<tr> 
					<td width="50%"> 
						 <div id="Layer2" style="position:relative; width:100%; height:330px; z-index:1; overflow:auto">
							 <table width="100% border="0" cellspacing="0" cellpadding="0">
							    <tr>
								     <th width="3%" align="left" bgcolor="#FFCC00">Del</th>
								     <th width="10%" align="left" bgcolor="#FFCC00">Item No.</th>
								     <th width="37%" align="left" bgcolor="#FFCC00">Item Name</th>
								     <th width="15%"  align="left" bgcolor="#FFCC00">Vendor Item No.</th>
  									 <th width="10%"  align="center" bgcolor="#FFCC00">Order Qty</th>
  									 <th width="10%"  align="right" bgcolor="#FFCC00">Unit Cost</th>
  									 <th width="15%"  align="center" bgcolor="#FFCC00">Qty Received</th>
								 </tr>
								<beanlib:LoopRows bean="item" list="poItems">
									<tr>
									    <td>
										     <beanlib:InputControl dataSource="item" type="checkbox" name="selCbx" value="rowid"/>
											   <beanlib:InputControl dataSource="item" type="hidden" name="rowId" value="rowid"/>
										 </td>
										 <td align="left">
										      <beanlib:InputControl dataSource="item" type="hidden" name="PurchaseOrderId" property="PurchaseOrderId" uniqueName="yes"/>                                  
										      <beanlib:InputControl dataSource="item" type="hidden" name="ItemMasterId" property="ItemMasterId" uniqueName="yes"/>                                  										 
											  <beanlib:ElementValue dataSource="item" property="ItemMasterId"/>                                  
										 </td>
										 <td align="left">
											  <beanlib:ElementValue dataSource="item" property="Description"/>                                  
										 </td>										 
										 <td align="left">
											  <beanlib:ElementValue dataSource="item" property="VendorItemNo"/>                                  
										 </td>										 
										 <td  align="center" valign="bottom">
											  <beanlib:InputControl dataSource="item" type="text" name="QtyOrderd" property="QtyOrderd" size="5" uniqueName="yes"/>                                  
										 </td>										 
										 <td  align="right" valign="bottom">
											  <beanlib:ElementValue dataSource="item" property="ActualUnitCost" format="#,##0.00;(#,##0.00)"/> 
											  <beanlib:InputControl dataSource="item" type="hidden" name="UnitCost" property="ActualUnitCost" uniqueName="yes"/>                                  										 
										 </td>
										 <td align="center">
											  <beanlib:ElementValue dataSource="item" property="QtyReceived"/>   
											  <beanlib:InputControl dataSource="item" type="text" name="QtyReceived" value="" size="5" uniqueName="yes"/>                                  
										 </td>																 
									 </tr>
								 </beanlib:LoopRows>
							  </table>					 
						 </div>  <!-- end SrvcLayer div -->
					</td>
				</tr>	
			</table>
    
            <!--  Setup message area -->
			<table>
				<tr>
				  <td colspan="3">
				     <font color="red">
					     <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
					   </font>
				  </td>
				</tr>
		    </table>
		 	<br>
            
            <!-- Display command buttons -->
            <div id="ButtonLayer" style="position:relative; top:20px; width:100%; height:30px; z-index:1">
			  <table width="100%" cellpadding="0" cellspacing="0">
				  <tr>
					<td colspan="2">     
					      <input name="<%=PurchasesConst.REQ_ADDITEM %>" type="button" value="Add" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
						  <input name="<%=PurchasesConst.REQ_SAVE%>" type="button" value="Save" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
						  <input name="<%=PurchasesConst.REQ_FINALIZE%>" type="button" value="Finalize" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
						  <input name="<%=PurchasesConst.REQ_CANCEL%>" type="button" value="Cancel" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
						  <input name="<%=PurchasesConst.REQ_SEARCH%>" type="button" value="Back" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
					</td>
					 </tr>		
			  </table>			   
		    </div>
	    <input name="clientAction" type="hidden">
   </form>
 </body>
</html>
