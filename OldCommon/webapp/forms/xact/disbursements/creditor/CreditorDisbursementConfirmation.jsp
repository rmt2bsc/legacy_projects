<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.bean.Xact" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.XactConst" %>
<%@ page import="com.constants.AccountingConst" %>
<%@ page import="com.constants.GeneralConst" %>


<gen:InitAppRoot id="APP_ROOT"/>
   
<html>
  <head>
    <title>New Sales Order Confirmation</title>
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
       Xact xact = request.getAttribute(XactConst.CLIENT_DATA_XACT) == null ? new Xact() : (Xact) request.getAttribute(XactConst.CLIENT_DATA_XACT);
   %>
   
  <body bgcolor="#FFFFFF" text="#000000" onLoad="initPage()">
       <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactDisburse.DisbursementCreditorConfirm">
   	      <beanlib:InputControl dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR%>" type="hidden" name="CreditorId" property="Id"/>                                  
		      <beanlib:InputControl dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR%>" type="hidden" name="BusinessId" property="BusinessId"/>                                  

		      <h3><strong>Creditor/Vendor Payment Confirmation</strong></h3>
		      <table>
			     <tr>
				    <td>
						 <font face="Verdana, Arial, Helvetica, sans-serif" size="2">
							  *** Your Payment was successfully applied *** 
						 </font>					
					</td>
				 </tr>
			     <tr>
				    <td>
						 <font face="Verdana, Arial, Helvetica, sans-serif" size="2">
							  Confirmation No: <%=xact.getConfirmNo()%>
						 </font>					
					</td>
				 </tr>
			     <tr>
				    <td>
						 <font face="Verdana, Arial, Helvetica, sans-serif" size="2">
							  Account Number: <beanlib:ElementValue dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR%>" property="Id"/>                                  
						 </font>					
					</td>
				 </tr>			
			     <tr>
				    <td>
						 <font face="Verdana, Arial, Helvetica, sans-serif" size="2">
							  Account Name: <beanlib:ElementValue dataSource="<%=GeneralConst.CLIENT_DATA_BUSINESS%>" property="Longname"/>
						 </font>					
					</td>
				 </tr>							 	 				 
			     <tr>
				    <td>
						 <font face="Verdana, Arial, Helvetica, sans-serif" size="2">
							  Payment Amount: <%=xact.getXactAmount() * -1%>
						 </font>					
					</td>
				 </tr>				 				 
			     <tr>
				    <td>
						 <font face="Verdana, Arial, Helvetica, sans-serif" size="2">
							  Payment Date: <%=xact.getXactDate()%>
						 </font>					
					</td>
				 </tr>				 				 				 
			  </table>
		  <br>
		  
		  <table width="100%" cellpadding="0" cellspacing="0">
			  <!-- Display command buttons -->
			  <tr>
				<td colspan="2">
				  <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Finish" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
				</td>
			 </tr>		
		  </table>			   
      <input name="clientAction" type="hidden">
   </form>
 </body>
</html>
