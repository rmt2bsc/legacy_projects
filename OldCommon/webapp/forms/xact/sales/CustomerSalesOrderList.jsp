<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.CustomerWithName" %>
<%@ page import="com.bean.CombinedSalesOrder" %>
<%@ page import="com.constants.SalesConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <title>Transaction Search Results</title>
  <head>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">   
  </head>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>   
  
  <script Language="JavaScript">
		function processAction(_srcObj, _actionName) {
			 // Change the controller that is to handle this request.
			this.document.DataForm.action = "<%=APP_ROOT%>" + _actionName;
			this.document.DataForm.clientAction.value = _srcObj;
			// Submit page
			handleAction(MAIN_DETAIL_FRAME, this.document.DataForm, _srcObj);
		}				
  </script>
  
  <%
	 Object tempObj = request.getAttribute(SalesConst.CLIENT_DATA_CUSTOMER_EXT);
	 CustomerWithName cust = (tempObj == null ? new CustomerWithName() :  (CustomerWithName) tempObj);
	 CombinedSalesOrder cso = null;
	 String requestType = "xact";
	 String pageTitle = "Sales On Account - Customer Order History (" + cust.getName() + ")";
	 String statusColor = "color:black";
  %>
  
  <body>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactSales.CustomerSalesViewOrders">
	     <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" type="hidden" property="Id" name="CustomerId"/>
		   <table width="85%" border="3">
	       <caption align="left">
 					  <h3><%=pageTitle%></h3> 
			   </caption>
			      <tr>
				       <td>
					   
    					  <div id="Layer1" style="width:100%; height:650px; z-index:1; overflow:auto; background-color: buttonface" >
							 <table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
								 <tr>
									 <th class="clsTableListHeader">&nbsp;</th>
									 <th class="clsTableListHeader" style="text-align:left">Order No.</th>
									 <th class="clsTableListHeader"  style="text-align:left">Transaction Id</th>
									 <th class="clsTableListHeader">Order Date</th>
									 <th class="clsTableListHeader" style="text-align:right">Order Amount</th>
									 <th class="clsTableListHeader" style="text-align:center">Order Status</th>
									 <th class="clsTableListHeader" style="text-align:left">Invoice Number</th>
								 </tr>
				   

									 <beanlib:LoopRows bean="beanObj" list="<%=SalesConst.CLIENT_DATA_ORDERLIST%>">
										  <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>

											 <td width="1%" align="center" class="clsTableListHeader">
												 <beanlib:InputControl dataSource="beanObj" type="radio" name="selCbx" value="rowid"/>
												 <beanlib:InputControl dataSource="beanObj" type="hidden" name="OrderId" property="OrderId" uniqueName="yes"/>   
												 <beanlib:InputControl dataSource="beanObj" type="hidden" name="XactId" property="XactId" uniqueName="yes"/>                                                                 
												 <beanlib:InputControl dataSource="beanObj" type="hidden" name="OrderStatusId" property="StatusId" uniqueName="yes"/>                                                                 
											 </td>   
											 <td width="10%" align="left" >
												<font size="2">
												  <beanlib:ElementValue dataSource="beanObj" property="OrderId"/>
												</font>
											 </td>             
											 <td width="10%" align="left">
												<font size="2"> 
												  <beanlib:ElementValue dataSource="beanObj" property="XactId"/>
												</font>
											 </td>
											 <td width="10%" align="center">
												<font size="2"> 
												  <beanlib:ElementValue dataSource="beanObj" property="OrderDate" format="MM-dd-yyyy H:m:s"/>
												</font>
											 </td>
											 <td width="10%" align="right">
												<font size="2">
												   <beanlib:ElementValue dataSource="beanObj" property="XactAmount" format="$#,##0.00;($#,##0.00)"/>
												</font>
											 </td>
											 <%
											     //  Color code sales order statuses.
											     cso =  (CombinedSalesOrder) pageContext.getAttribute("beanObj");
											     switch (cso.getStatusId()) {
												     case SalesConst.STATUS_CODE_QUOTE:
													 case SalesConst.STATUS_CODE_CLOSED:
													     statusColor = "color:black";
														 break;
													 case SalesConst.STATUS_CODE_INVOICED:
													     statusColor = "color:blue";
														 break;													 
													 case SalesConst.STATUS_CODE_CANCELLED:
													     statusColor = "color:red";
														 break;									
													 default:
													     statusColor = "color:black"; 
											      }
											 %>
											 <td width="13%" align="center" style="<%=statusColor%>">
												<font size="2">
												   <beanlib:ElementValue dataSource="beanObj" property="StatusDescription" />
												</font>
											</td>
											 <td width="15%" align="left" >
												<font size="2">
												   <beanlib:ElementValue dataSource="beanObj" property="InvoiceNo"/>
												</font>
											</td>							
										 </tr>
									 </beanlib:LoopRows>
				
								 <% if (pageContext.getAttribute("ROW") == null) {
											out.println("<tr><td colspan=7 align=center>Data Not Found</td></tr>");
										}
								 %>
							 </table>
						  </div>	
					   
					   </td>
				  </tr>
			   </table>
			  <br>
  		      <table>
		        <tr>
		          <td> 
				        <input type="button" name="edit" value="Edit" style="width:90" onClick="changeAction(MAIN_DETAIL_FRAME, document.DataForm, this.name, '<%=APP_ROOT%>/reqeustProcessor/XactSales.CustomerSalesViewOrders')">
 					      <input type="button" name="delete" value="Delete" style="width:90" onClick="changeAction(MAIN_DETAIL_FRAME, document.DataForm, this.name), '<%=APP_ROOT%>/reqeustProcessor/XactSales.CustomerSalesViewOrders')">
 					      <input type="button" name="<%=GeneralConst.REQ_PRINT%>" value="Print Invoice" style="width:90" onClick="handlePrintAction('_blank', document.DataForm, this.name, '<%=APP_ROOT%>/reqeustProcessor/XactSales.SalesOrderInvoiceReport')">
					      <input type="button" name="back" value="Back" style="width:90" onClick="changeAction(MAIN_DETAIL_FRAME, document.DataForm, this.name, '<%=APP_ROOT%>/reqeustProcessor/XactSales.CustomerSalesViewOrders')">	 					
			      </td>
			    </tr>
		      </table>
 		      <input name="clientAction" type="hidden">		  
     </form>
     <db:Dispose/>
  </body>
</html>
