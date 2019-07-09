<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.xact.sales.CustomerWithName" %>
<%@ page import="com.bean.VwSalesorderItemsBySalesorder" %>
<%@ page import="com.bean.SalesOrder" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.xact.XactConst" %>
<%@ page import="com.xact.sales.SalesConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>


<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Edit Customer Sales Order Details</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	  <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
    <script Language="JavaScript">
			function enableReason() {
			   var obj;
			   
			   obj = event.srcElement;
			   if (obj.checked == true) {
			      ReasonLabel.style.visibility = "visible";
			      this.document.DataForm.Reason.style.visibility = "visible";
			      InvoiceAndReceiveLabel.style.visibility = "visible";
			      this.document.DataForm.InvoiceAndReceive.style.visibility = "visible";
			   }
			   else {
			      ReasonLabel.style.visibility = "hidden";
			      this.document.DataForm.Reason.style.visibility = "hidden";
			      InvoiceAndReceiveLabel.style.visibility = "hidden";
			      this.document.DataForm.InvoiceAndReceive.style.visibility = "hidden";
			   }
			   return;
			}
    </script>
  </head>


   <%          
		 String pageTitle = "Customer Sales Order Edit";
	%>
	
  <body bgcolor="#FFFFFF" text="#000000" onLoad="initPage()">
    <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/SalesCustomerOrderEdit.OrderEdit">
	     <beanlib:InputControl type="hidden" name="CustomerId" value="#customer.CustomerId"/>                                  
		   <beanlib:InputControl type="hidden" name="BusinessId" value="#customer.BusinessId"/>                                  
		   <beanlib:InputControl type="hidden" name="OrderId" value="#salesorder.SoId"/>                                  
		   <beanlib:InputControl type="hidden" name="XactId" value="#xact.XactId"/>                                  
	      
	      
		  <h3><strong><%=pageTitle%></strong></h3>
	  
		  <table width="60%" border="0">
			 <tr> 
				<td align="right" width="30%" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Account Number</b>
					 </font>
				</td>
				<td width="70%">
				   <beanlib:InputControl value="#customer.AccountNo"/> 
				</td>
			</tr>    
			<tr> 
				<td align="right" width="35%" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Account Name</b>
					</font>
				 </td>
				 <td width="65%">
				     <beanlib:InputControl value="#customer.Name"/> 
				 </td>
			</tr>   
			<tr> 
				<td align="right" width="35%" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Order Number</b>
					</font>
				 </td>
				 <td width="65%">
				    <beanlib:InputControl value="#salesorder.SoId"/> 
				</td>
			</tr>   			
			<tr> 
				<td align="right" width="35%" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Date Created</b>
					</font>
				</td>
				<td width="70%">
				   <beanlib:InputControl value="#salesorder.DateCreated" format="MM-dd-yyyy"/>
				</td>
			</tr>    			
		</table>
		<br>
	  
    <div id="OuterLayer"></div>
			<table width="100%" border="3" bgcolor="#AACCCC" >
				<tr> 
					<td align="left" width="50%" bgcolor="#0000CC">
						 <font size="3" color="white">
							<b>Services</b>
						</font>
					 </td>
					<td align="left" width="50%" color="white" bgcolor="#0000CC">
						<font size="3" color="white">
							<b>Merchandise</b>
						</font>
					 </td>
				</tr>
				<tr> 
					<td width="50%"> 
						 <div id="SrvcLayer" style="position:relative; width:100%; height:330px; z-index:1; overflow:auto">
							 <table width="100% border="0">
							    <tr>
								     <th width="6%" align="left" bgcolor="#FFCC00">Del</th>
								     <th width="54%" align="left" bgcolor="#FFCC00">Item Name</th>
									 <th width="25%"  align="center" bgcolor="#FFCC00">Price</th>
									 <th width="15%"  align="left" bgcolor="#FFCC00">Order Qty</th>
								 </tr>
								<beanlib:LoopRows bean="srvcItem" list="<%=SalesConst.CLIENT_DATA_SERVICE_ITEMS%>">
									<tr>
									     <td>
										     <beanlib:InputControl dataSource="srvcItem" type="checkbox" name="selCbxSrvc" value="rowid" uniqueName="yes"/>
											 <beanlib:InputControl dataSource="srvcItem" type="hidden" name="rowIdSrvc" value="rowid"/>
										 </td>
										 <td>
										      <beanlib:InputControl type="hidden" name="SrvcSlsOrdItemId" value="#srvcItem.SalesOrderItemId" uniqueName="yes"/>                                  
										      <beanlib:InputControl type="hidden" name="SrvcItemId" value="#srvcItem.ItemId" uniqueName="yes"/>                                  										 
  											  <beanlib:InputControl value="#srvcItem.ItemName"/>                                  
										 </td>
										 <td  align="right" valign="bottom">
											  <beanlib:InputControl type="text" name="SrvcRetailPrice" value="#srvcItem.RetailPrice" style="text-align:right" format="#,##0.00;(#,##0.00)" uniqueName="yes"/>                                  
										 </td>
										 <td  align="right" valign="bottom">
											  <beanlib:InputControl type="text" name="SrvcOrderQty" value="#srvcItem.OrderQty" size="5" uniqueName="yes"/>                                  
										 </td>
									 </tr>
								 </beanlib:LoopRows>
							  </table>					 
						 </div>  <!-- end SrvcLayer div -->
					</td>
					<td width="50%"> 
						 <div id="MerchLayer" style="position:relative; width:100%; height:330px; z-index:1; overflow:auto">
							 <table width="100% border="1">
							    <tr>
								     <th width="6%"  align="left" bgcolor="#FFCC00">Del</th>
								     <th width="54%"  align="left" bgcolor="#FFCC00">Item Name</th>
									 <th width="25%"  align="center" bgcolor="#FFCC00">Price</th>
									 <th width="15%" align="left" bgcolor="#FFCC00">Order Qty</th>
								 </tr>
								<beanlib:LoopRows bean="merchItem" list="<%=SalesConst.CLIENT_DATA_MERCHANDISE_ITEMS%>">
									<tr>
									     <td>
										     <beanlib:InputControl dataSource="merchItem" type="checkbox" name="selCbxMerch" value="rowid" uniqueName="yes"/>
											 <beanlib:InputControl dataSource="merchItem" type="hidden" name="rowIdMerch" value="rowid"/>
										 </td>
										 <td>
										      <beanlib:InputControl type="hidden" name="MerchSlsOrdItemId" value="#merchItem.SalesOrderItemId" uniqueName="yes"/>                                  
										      <beanlib:InputControl type="hidden" name="MerchItemId" value="#merchItem.ItemId" uniqueName="yes"/>                                  
											  <beanlib:InputControl value="#merchItem.ItemName"/>                                  
										 </td>
										 <td  align="right" valign="bottom">
											  <beanlib:InputControl value="#merchItem.RetailPrice" format="#,##0.00;(#,##0.00)"/>                                  
										 </td>
										 <td  align="right" valign="bottom">
											  <beanlib:InputControl type="text" name="MerchOrderQty" value="#merchItem.OrderQty" size="5" uniqueName="yes"/>                                  
										 </td>
									 </tr>
								 </beanlib:LoopRows>
							  </table>					 
						 </div> <!-- end MerchLayer div -->
					</td>
				</tr>	
			</table>
			
			<!-- Display any messgaes -->
			<table>
				<tr>
				  <td colspan="3">
				     <font color="red">
					     <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
					   </font>
				  </td>
				</tr>
		  </table>
		  				  
			<div id="OrderTotalLayer" style="position:relative; top:50px; width:100%; height:80px; z-index:1">
			  <table width="80%" cellpadding="0" cellspacing="0">
				  <!-- Display command buttons -->
				  <tr>
					<td width="14%" align="right">
					    <font size="2">
						    <b>Order Total:</b>
						</font>
					</td>
					<td width="1%"><img src="/images/clr.gif" width="1"></td>
					<td width="85%" align="left">
   					    <font size="3" color="blue">
   					      <strong>
                    <beanlib:InputControl value="#salesorder.OrderTotal" format="$#,##0.00;($#,##0.00)"/>
                  </strong>
						      <beanlib:InputControl type="hidden" name="XactAmount" value="#salesorder.OrderTotal"/>                                  
					    </font>
					</td>
				 </tr>		
				 
				<gen:Evaluate expression="#salesorder.Invoiced">
				  <gen:When expression="0">
				    <tr>
						<td align="right">
							<font size="2">
								<b>Invoice:</b>
							</font>
						</td>
						<td><img src="/images/clr.gif" width="1"></td>
						<td align="left">
							<font size="3" color="blue">
						  	 <beanlib:InputControl type="checkbox" name="Invoiced" value="#salesorder.Invoiced" checkedValue="1" onClick="enableReason()"/> 
							</font>
						</td>
					</tr>
					<tr>
						<td id="InvoiceAndReceiveLabel" align="right" style="visibility:hidden">
							<font size="2">
								<b>Invoice as Receipt:</b>
							</font>
						</td>
						<td><img src="/images/clr.gif" width="1"></td>
						<td align="left">
							<font size="3" color="blue">
						  	 <beanlib:InputControl type="checkbox" name="InvoiceAndReceive" value="1" style="visibility:hidden"/> 
							</font>
						</td>
					</tr>
				    <tr>
							<td id="ReasonLabel" align="right" style="visibility:hidden">
								<font size="2">
									<b>Invoice Reason:</b>
								</font>
							</td>
							<td><img src="/images/clr.gif" width="1"></td>
							<td align="left">
								<font size="3" color="blue">
								   <beanlib:InputControl type="text" name="Reason" value="#xact.Reason" size="90" maxLength="100" style="visibility:hidden"/>
								</font>
							</td>
						 </tr>						 	
				  </gen:When>
				  <gen:When expression="1">
				    <tr>
							<td id="ReasonLabel" align="right">
								<font size="2">
									<b>Invoice Reason:</b>
								</font>
							</td>
							<td><img src="/images/clr.gif" width="1"></td>
							<td align="left">
								<font size="3" color="black">
								   <beanlib:InputControl value="#xact.Reason"/>
								</font>
							</td>
						 </tr>						 	
				  </gen:When>
		  	</gen:Evaluate>
		  	
	 						 
		  </table>			   
		 </div>	  <!-- end ButtonLayer div -->
     <br>
     
		  <!-- Display command buttons -->
			<div id="ButtonLayer" style="position:relative; top:50px; width:100%; height:30px; z-index:1">
			  <table width="100%" cellpadding="0" cellspacing="0">
				  <tr>
					<td colspan="2">
						<gen:Evaluate expression="#salesorder.Invoiced">
						  <gen:When expression="0">
  						  <input name="<%=GeneralConst.REQ_ADD%>" type="button" value="Add Item" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
   						  <input name="<%=GeneralConst.REQ_SAVE%>" type="button" value="Update" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
						  </gen:When>
				  	</gen:Evaluate>
					  <input name="<%=GeneralConst.REQ_DELETE%>" type="button" value="Delete" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
					  <input name="<%=GeneralConst.REQ_BACK%>" type="button" value="Order List" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
					</td>
				 </tr>		
			  </table>			   
			 </div>	  <!-- end ButtonLayer div -->

		</div>  <!-- end OuterLayer div -->
	 <input name="clientAction" type="hidden">
   </form>
 </body>
</html>
