<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.bean.CustomerWithName" %>
<%@ page import="com.bean.VwSalesorderItemsBySalesorder" %>
<%@ page import="com.bean.SalesOrder" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.XactConst" %>
<%@ page import="com.constants.SalesConst" %>
<%@ page import="com.constants.GeneralConst" %>
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
    <script Language="JavaScript">
			function enableReason() {
			   var obj;
			   
			   obj = event.srcElement;
			   if (obj.checked == true) {
			      ReasonLabel.style.visibility = "visible";
			      this.document.DataForm.Reason.style.visibility = "visible";
			   }
			   else {
			      ReasonLabel.style.visibility = "hidden";
			      this.document.DataForm.Reason.style.visibility = "hidden";
			   }
			   return;
			}
    </script>
  </head>


   <%          
		 Object tempObj = request.getAttribute("salesorder");
     SalesOrder so = (tempObj == null ? new SalesOrder() :  (SalesOrder) tempObj);		
		 String pageTitle = "";
		 String requestType = "xact";
		
		 if (so.getId() <= 0) {
		   pageTitle = "Sales On Account -  New Order";
		 }
		 else {
		   pageTitle = "Sales On Account -  Edit Order";
		 }
		
	%>
	
  <body bgcolor="#FFFFFF" text="#000000" onLoad="initPage()">
    <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactSales.CustomerSalesOrderEdit">
	     <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" type="hidden" name="CustomerId" property="Id"/>                                  
		   <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" type="hidden" name="BusinessId" property="BusinessId"/>                                  
		   <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" type="hidden" name="PersonId" property="PersonId"/>                                  
		   <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_SALESORDER%>" type="hidden" name="OrderId" property="Id"/>                                  
		   <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_XACT%>" type="hidden" name="XactId" property="Id"/>                                  
	      
		  <h3><strong><%=pageTitle%></strong></h3>
	  
		  <table width="60%" border="0">
			 <tr> 
				<td align="right" width="30%" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Account Number</b>
					 </font>
				</td>
				<td width="70%">
				   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" property="AccountNo"/> 
				</td>
			</tr>    
			<tr> 
				<td align="right" width="35%" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Account Name</b>
					</font>
				 </td>
				 <td width="65%">
				     <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" property="Name"/> 
				 </td>
			</tr>   
			<tr> 
				<td align="right" width="35%" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Order Number</b>
					</font>
				 </td>
				 <td width="65%">
				    <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_SALESORDER%>" property="Id"/> 
				</td>
			</tr>   			
			<tr> 
				<td align="right" width="35%" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Date Created</b>
					</font>
				</td>
				<td width="70%">
				   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_SALESORDER%>" property="DateCreated" format="MM-dd-yyyy"/>
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
										      <beanlib:InputControl dataSource="srvcItem" type="hidden" name="SrvcSlsOrdItemId" property="SalesOrderItemId" uniqueName="yes"/>                                  
										      <beanlib:InputControl dataSource="srvcItem" type="hidden" name="SrvcItemId" property="ItemMasterId" uniqueName="yes"/>                                  										 
											  <beanlib:ElementValue dataSource="srvcItem" property="ItemName"/>                                  
										 </td>
										 <td  align="right" valign="bottom">
											  <beanlib:ElementValue dataSource="srvcItem" property="RetailPrice" format="#,##0.00;(#,##0.00)"/>                                  
										 </td>
										 <td  align="right" valign="bottom">
											  <beanlib:InputControl dataSource="srvcItem" type="text" name="SrvcOrderQty" property="OrderQty" size="5" uniqueName="yes"/>                                  
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
										      <beanlib:InputControl dataSource="merchItem" type="hidden" name="MerchSlsOrdItemId" property="SalesOrderItemId" uniqueName="yes"/>                                  
										      <beanlib:InputControl dataSource="merchItem" type="hidden" name="MerchItemId" property="ItemMasterId" uniqueName="yes"/>                                  
											  <beanlib:ElementValue dataSource="merchItem" property="ItemName"/>                                  
										 </td>
										 <td  align="right" valign="bottom">
											  <beanlib:ElementValue dataSource="merchItem" property="RetailPrice" format="#,##0.00;(#,##0.00)"/>                                  
										 </td>
										 <td  align="right" valign="bottom">
											  <beanlib:InputControl dataSource="merchItem" type="text" name="MerchOrderQty" property="OrderQty" size="5" uniqueName="yes"/>                                  
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
                    <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_SALESORDER%>" property="OrderTotal" format="$#,##0.00;($#,##0.00)"/>
                  </strong>
						      <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_SALESORDER%>" type="hidden" name="XactAmount" property="OrderTotal"/>                                  
					    </font>
					</td>
				 </tr>		
				 
				<gen:Evaluate expression="<%=so.getInvoiced()%>">
				  <gen:When expression="0">
				    <tr>
							<td align="right">
								<font size="2">
									<b>Invoiced:</b>
								</font>
							</td>
							<td><img src="/images/clr.gif" width="1"></td>
							<td align="left">
								<font size="3" color="blue">
							  	 <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_SALESORDER%>" type="checkbox" name="Invoiced" property="Invoiced" value="1" checkedValue="1" onClick="enableReason()"/> 
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
								   <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_XACT%>"  type="text" name="Reason" property="Reason" size="90" maxLength="100" style="visibility:hidden"/>
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
								   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_XACT%>" property="Reason"/>
								</font>
							</td>
						 </tr>						 	
				  </gen:When>
		  	</gen:Evaluate>
		  	
	 						 
		  </table>			   
		 </div>	  <!-- end ButtonLayer div -->

		  <!-- Display command buttons -->
			<div id="ButtonLayer" style="position:relative; top:50px; width:100%; height:30px; z-index:1">
			  <table width="100%" cellpadding="0" cellspacing="0">
				  <tr>
					<td colspan="2">
					  <input name="<%=SalesConst.REQ_VIEWORDERS%>" type="button" value="Order List" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">

						<gen:Evaluate expression="<%=so.getInvoiced()%>">
						  <gen:When expression="0">
  						  <input name="<%=GeneralConst.REQ_ADD%>" type="button" value="Add Item" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
   						  <input name="<%=GeneralConst.REQ_SAVE%>" type="button" value="Update" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
						  </gen:When>
				  	</gen:Evaluate>

					  <input name="<%=GeneralConst.REQ_DELETE%>" type="button" value="Delete" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
					</td>
				 </tr>		
			  </table>			   
			 </div>	  <!-- end ButtonLayer div -->

		</div>  <!-- end OuterLayer div -->
	 <input name="clientAction" type="hidden">
   </form>
 </body>
</html>
