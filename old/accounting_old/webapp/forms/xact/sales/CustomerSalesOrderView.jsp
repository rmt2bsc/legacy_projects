<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.SalesOrderStatus" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.xact.sales.SalesConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst"%>

<gen:InitAppRoot id="APP_ROOT"/>
      
<html>
  <head>
    <title>View-Only Sales Order Details</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	  <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>

   <%
      SalesOrderStatus sos = request.getAttribute(SalesConst.CLIENT_DATA_STATUS) == null ? new SalesOrderStatus() : (SalesOrderStatus)  request.getAttribute("salesorderstatus");
   %>
  <body bgcolor="#FFFFFF" text="#000000">
    <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/SalesCustomerOrderView.OrderView">
      <beanlib:InputControl type="hidden" name="CustomerId" value="#customer.CustomerId"/>                                  
      <beanlib:InputControl type="hidden" name="OrderId" value="#salesorder.SoId"/>                                           
      <h3><strong>Sales Order View</strong></h3>
	  
	    <blockquote>
		     <font face="Verdana, Arial, Helvetica, sans-serif" size="2">
		       <gen:Evaluate expression="<%=GeneralConst.REQ_COMMAND%>">
		          <gen:When expression="<%=SalesConst.REQ_CANCEL%>">
		             *** This Sales Order has been successfully cancelled ***
		          </gen:When>
		          <gen:When expression="<%=SalesConst.REQ_REFUND%>">
		             *** This Sales Order has been successfully refunded ***
		          </gen:When>
		          <gen:WhenElse>
		             *** This Sales Order has been invoiced and is not editable ***
		          </gen:WhenElse>
		       </gen:Evaluate>
    		 </font>   
	    </blockquote>
	  
      <%@include file="/forms/xact/sales/SalesOrderDetails.jsp"%>  
      <br>	  
      
	   <!-- Display any messgaes -->
	   <table>
		 <tr>
  		   <td>
		     <font color="red">
			     <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
		     </font>
	 	   </td>
		</tr>
	  </table>
	  <br>         

	  <!-- Display command buttons -->	         
	  <table width="100%" cellpadding="0" cellspacing="0">
  	 <tr>
			<td colspan="2">
			     <gen:Evaluate expression="#salesorderstatus.SoStatusId">
			        <gen:When expression="2">
			           <input type="button" name="<%=SalesConst.REQ_CANCEL%>" value="Cancel" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
   				       <input type="button" name="<%=SalesConst.REQ_CLOSE%>" value="Close" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
			        </gen:When>
			        <gen:When expression="3">
			           <input type="button" name="<%=SalesConst.REQ_REFUND%>" value="Refund" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
			        </gen:When>			        
			     </gen:Evaluate>
			   <input type="button" name="<%=GeneralConst.REQ_PRINT%>" value="Print" style="width:90" onClick="handleAction('_blank', document.DataForm, this.name)">
			   <input name="<%=SalesConst.REQ_VIEWORDERS%>" type="button" value="Order List" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
			</td>
		 </tr>		
	  </table>			   
 	   <input name="clientAction" type="hidden">
    </form>
 </body>
</html>
