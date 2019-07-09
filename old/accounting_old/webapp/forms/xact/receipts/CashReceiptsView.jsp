<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.xact.sales.CustomerWithName" %>
<%@ page import="com.bean.Xact"%>
<%@ page import="com.xact.XactConst" %>
<%@ page import="com.xact.sales.SalesConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst"%>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <title>Transaction Search Results</title>
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
        	 //Object tempObj = request.getAttribute("customer");
        	 //CustomerWithName cust = (tempObj == null ? new CustomerWithName() :  (CustomerWithName) tempObj);
         	 String pageTitle = null;
         	 String submitName = null;
         	 Xact xact = request.getAttribute("xact") == null ? new Xact() :  (Xact) request.getAttribute("xact");
         	 int xactTypeId = xact.getXactTypeId();
         	 
         	 // Use temporary transaction type id to determine action to take.
              if (xactTypeId ==  XactConst.XACT_TYPE_CASHPAY) {
        		  pageTitle = "Customer Payment on Account Transaction";
        		  submitName = SalesConst.REQ_PAYMENT;
        	  }
        	  if (xactTypeId ==  XactConst.XACT_TYPE_SALESRETURNS) {
        		  pageTitle = "Customer Refunds, Returns, and Allowances Transaction";
        		  submitName = XactConst.SO_SALESRETURN;
        	  }
        	  if (xactTypeId ==  XactConst.XACT_TYPE_REVERSE) {
        		  pageTitle = "Customer Payment Reversal Transaction";
        		  submitName = XactConst.CR_REVERSE;
        	  }
    %>
  
  <body>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/CashReceipts.Edit">
	   <beanlib:InputControl type="hidden" name="CustomerId" value="#customer.CustomerId"/>
	   <beanlib:InputControl type="hidden" name="BusinessId" value="#customer.BusinessId"/>                                  
	   <beanlib:InputControl type="hidden" name="XactId" value="#xact.XactId"/> 


	   <table width="85%" border="0">
		 <caption align="left"><h3><%=pageTitle%></h3></caption>
		 <tr>
		   <td colspan="2">   
			  <%@include file="/forms/xact/sales/CustomerXactHdr.jsp"%>
		   </td>
		 </tr>
		 <tr>
		   <td colspan="2">&nbsp;</td>
		 </tr>
		 <tr>
		   <th width="20%" style="text-align:right">Transaction Id:</th>
		   <td width="80%">
    		  <beanlib:InputControl value="#xact.XactId"/> 
		   </td>
		 </tr>
		 <tr>
		   <th width="20%" style="text-align:right">Transaction Date:</th>
		   <td width="80%">
    		  <beanlib:InputControl value="#xact.DateCreated" format="MM/dd/yyyy HH:mm:ss"/> 
		   </td>
		 </tr>
		 <tr>
		   <th width="20%" style="text-align:right">Confirmation No.:</th>
		   <td width="80%">
    		  <beanlib:InputControl value="#xact.ConfirmNo"/> 
		   </td>
		 </tr>
		 <tr>
		   <th width="20%" style="text-align:right">Payment Amount:</th>
		   <td width="80%">
    		  <beanlib:InputControl value="#xact.XactAmount" format="$#,##0.00"/> 
		   </td>
		 </tr>
		  <tr>
			<th width="20%" valign="top" style="text-align:right">Reason:</th>
			<td width="80%" align="left">
			     <beanlib:InputControl value="#xact.Reason"/>
    		</td>
		 </tr>		 						 
	   </table>
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
	  
    <table>
      <tr>
        <td>
          <input type="button" name="<%=GeneralConst.REQ_PRINT%>" value="Print" style="width:90" onClick="javascript:window.print()">
          <input type="button" name="email" value="E-Mail" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
          <input type="button" name="<%=GeneralConst.REQ_BACK%>"  value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
          <input name="clientAction" type="hidden">		  
        </td>
      </tr>
    </table>

     </form>
  </body>
</html>
