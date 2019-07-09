<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.xact.sales.CustomerWithName" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.xact.XactConst" %>
<%@ page import="com.xact.sales.SalesConst" %>
<%@ page import="com.constants.GeneralConst" %>

<gen:InitAppRoot id="APP_ROOT"/>


<html>
  <title>List Customer Sales Transactions</title>
  <head>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	  <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">   
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
  </head>

  
  <%
	 String pageTitle = "Customer Transaction History";
  %>
  
  <body>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/SalesCustomerXactList.Edit">
		  <beanlib:InputControl type="hidden" name="CustomerId" value="#customer.CustomerId"/>
  	  <beanlib:InputControl type="hidden" name="BusinessId" value="#customer.BusinessId"/>
		  <table width="60%" border="0">
			<caption align="left"><h3><%=pageTitle%></h3> </caption>
			<tr>
				<td>
					<%@include file="/forms/xact/sales/CustomerXactHdr.jsp"%>
				</td>
		    </tr>
		  </table>
		   <br>			  
 	    <font size="2" color="blue">Note: Only cash receipt transaction can be reversed on this page</font>
		  <table width="85%" border="3">
			  <tr>
				   <td>
					  <div id="Layer1" style="width:100%; height:550px; z-index:1; overflow:auto; ; background-color: buttonface">
						 <table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
							 <tr>
								 <th width="2%" class="clsTableListHeader">&nbsp;</th>
								 <th width="5%" class="clsTableListHeader" >Trans. Id</th>
								 <th width="5%" class="clsTableListHeader" >&nbsp;</th>
								 <th width="8%" class="clsTableListHeader" style="text-align:left">Trans. Date</th>
								 <th width="10%" class="clsTableListHeader" style="text-align:right">Amount</th>
								 <th width="2%" class="clsTableListHeader" style="text-align:center">&nbsp;</th>
								 <th width="10%" class="clsTableListHeader" style="text-align:left">Transaction Type</th>
 								 <th width="2%" class="clsTableListHeader" style="text-align:center">&nbsp;</th>
 								 <th width="13%" class="clsTableListHeader" style="text-align:left">Confirmation No.</th>
								 <th width="43%" class="clsTableListHeader" style="text-align:left">Reason</th>
							 </tr>

							 <beanlib:LoopRows bean="item" list="<%=XactConst.CLIENT_DATA_SALESPAYMENT_HIST%>">
							     <tr>
  								     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
									 <td align="center" class="clsTableListHeader">
									   <gen:Evaluate expression="#item.XactTypeId">
									      <gen:When expression="2">
									          <gen:Evaluate expression="#item.XactSubtypeId">
									              <gen:When expression="0">
									                  <beanlib:InputControl dataSource="item" type="radio" name="selCbx" value="rowid"/>
												   				  <beanlib:InputControl type="hidden" name="XactId" value="#item.XactId" uniqueName="yes"/>
												   				  <beanlib:InputControl type="hidden" name="CustomerActivityId" value="#item.CustomerActivityId" uniqueName="yes"/>
													 			</gen:When>
										 				</gen:Evaluate>      
									      </gen:When>
									   </gen:Evaluate>
									 </td>   
									 <td align="left">
										<font size="2"> 
										  <beanlib:InputControl value="#item.XactId"/>
										</font>
									 </td>
									 
									 <td align="center">
										 <gen:Evaluate expression="#item.DocumentId">
										   <gen:When expression="0">&nbsp;</gen:When>
										   <gen:WhenElse>
										     <a href="<%=APP_ROOT%>/DocumentViewer.jsp?contentId=<beanlib:InputControl value='#item.DocumentId'/>" target="_blank"><img src="<%=APP_ROOT%>/images/camera2.png" style="border: none" alt="View Reciept"/></a> 
										   </gen:WhenElse>
								     </gen:Evaluate>
									 </td>
									 
									 
									 <td align="left">
										<font size="2"> 
										  <beanlib:InputControl value="#item.XactDate" format="MM-dd-yyyy H:m:s"/>
										</font>
									 </td>
									 <td align="right">
										<font size="2">
										   <beanlib:InputControl value="#item.CustomerActivityAmount" format="$#,##0.00;($#,##0.00)"/>
										</font>		
									 </td>
									 <td>&nbsp;</td>
									 <td align="left" >
										<font size="2">
										   <beanlib:InputControl value="#item.XactTypeName"/>
										</font>
									</td>
 							    <td>&nbsp;</td>
 							    <td align="left" >
										<font size="2">
										   <beanlib:InputControl value="#item.ConfirmNo"/>
										</font>
									</td>
	 							  <td align="left" >
										<font size="2">
										   <beanlib:InputControl value="#item.Reason"/>
										</font>
									</td>
								 </tr>
							 </beanlib:LoopRows>
			
							 <% if (pageContext.getAttribute("ROW") == null) {
										out.println("<tr><td colspan=5 align=center>Data Not Found</td></tr>");
									}
							 %>
						 </table>
					  </div>	
				   </td>
			  </tr>
		   </table>
		   <img src="<%=APP_ROOT%>/images/camera2.png" style="border: none"/><font color="black" size="1">View image of receipt</font>
		  <br>
		  <br>
		  <div id="Layer2" style="width:50%; height:80px; z-index:1">
			  <input type="button" name="<%=SalesConst.REQ_REVERSEPAYMENT%>" value="Reverse" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
			  <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">	
			  <input name="clientAction" type="hidden">		  
		  </div>		 
     </form>
  </body>
</html>
