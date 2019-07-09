<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.xact.sales.CustomerWithName" %>
<%@ page import="com.bean.Xact"%>
<%@ page import="com.xact.XactConst" %>
<%@ page import="com.xact.sales.SalesConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst"%>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.xact.sales.CombinedSalesOrder" %>


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

		function updateTotal(ndx) {
			var selected = eval("document.DataForm.selCbx[" + ndx + "].checked");
			var selAmt = eval("document.DataForm.XactAmount" + ndx + ".value");
			var tot = document.DataForm.selInvoiceTot.value;
			if (tot == null || tot.length <= 0) {
				tot = 0;
			}
			if (selected) {
				tot = parseFloat(tot) + parseFloat(selAmt);
			}
			else {
				tot = parseFloat(tot) - parseFloat(selAmt);
			}
			document.DataForm.selInvoiceTot.value = tot;
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
		   <th width="20%" class="clsTableListHeader" style="text-align:right">Payment Amount:</th>
		   <td width="80%">
    		  <beanlib:InputControl type="text" name="XactAmount" value="#xact.XactAmount"/> 
		   </td>
		 </tr>
		  <tr>
			<th width="20%" valign="top" class="clsTableListHeader" style="text-align:right">Reason:</th>
			<td width="80%" align="left">
			     <textarea name="Reason" rows="2" cols="50" ><beanlib:InputControl value="#xact.Reason"/></textarea>
    		</td>
		 </tr>		 						 
	   </table>
	   <br>



       <table width="55%" border="3">
          <caption align="left">
	        <h3><font color="blue">Customer Invoices</font></h3> 
          </caption>
          <tr>
            <td>
			  <div id="Layer1" style="width:100%; height:250px; z-index:1; overflow:auto; background-color: buttonface" >
         		 <table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
				     <tr>
						 <th width="5%" class="clsTableListHeader">&nbsp;</th>
						 <th width="20%" class="clsTableListHeader" >Invoice Number</th>
						 <th width="20%" class="clsTableListHeader">Trans Date</th>
						 <th width="20%" class="clsTableListHeader" style="text-align:right">Order Amount</th>
						 <th width="15%" class="clsTableListHeader" style="text-align:center">Order Status</th>
					 </tr>
					 <beanlib:LoopRows bean="beanObj" list="<%=SalesConst.CLIENT_DATA_ORDERLIST%>">
						  <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
							 <td width="1%" align="center" class="clsTableListHeader">
								 <beanlib:InputControl type="checkbox" name="selCbx" value="rowid" onClick="javascript:updateTotal(this.value)"/>
								 <beanlib:InputControl type="hidden" name="SoId" value="#beanObj.OrderId" uniqueName="true"/>
							 </td>   
							 <td align="center" >
								<font size="2">
								   <beanlib:InputControl dataSource="beanObj" value="#beanObj.InvoiceNo"/>
								</font>
							 </td>
							 <td align="center">
								<font size="2"> 
								  <beanlib:InputControl value="#beanObj.OrderDate" format="MM-dd-yyyy H:m:s"/>
								</font>
							 </td>
							 <td align="right">
								<font size="2">
								   <beanlib:InputControl value="#beanObj.XactAmount" format="$#,##0.00;($#,##0.00)"/>
								   <beanlib:InputControl type="hidden" name="XactAmount" value="#beanObj.XactAmount" uniqueName="true" format="###0.00"/>
								</font>
							 </td>
							 <td align="center">
								<font size="2">
								   <beanlib:InputControl dataSource="beanObj" value="#beanObj.StatusDescription" />
								</font>
							</td>
						 </tr>
					 </beanlib:LoopRows>
					 <% 
					     if (pageContext.getAttribute("ROW") == null) {
							out.println("<tr><td colspan=7 align=center>No Invoices Available</td></tr>");
						 }
					 %>
		         </table>
	          </div>	
   
            </td>
          </tr>
       </table>
	   <br>
	   <font color="blue">Total Amount Selected:&nbsp;</font><input type="text" name="selInvoiceTot" style="border: none; background-color: #FFFFCC"></input>

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
          <input type="button" name="<%=SalesConst.REQ_PAYMENT%>" value="Apply" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
          <input type="button" name="<%=GeneralConst.REQ_BACK%>"  value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
          <input name="clientAction" type="hidden">		  
        </td>
      </tr>
    </table>

     </form>
  </body>
</html>
