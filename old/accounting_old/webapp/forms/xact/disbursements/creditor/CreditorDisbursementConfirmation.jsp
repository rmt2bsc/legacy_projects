<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.bean.Xact" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.xact.XactConst" %>
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
  </head>
 
   <%
    Xact xact = request.getAttribute(XactConst.CLIENT_DATA_XACT) == null ? new Xact() : (Xact) request.getAttribute(XactConst.CLIENT_DATA_XACT);
    %>
   
  <body bgcolor="#FFFFFF" text="#000000" onLoad="initPage()">
       <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/DisbursementsCreditor.Confirm">
   	      <beanlib:InputControl type="hidden" name="CreditorId" value="#creditor.CreditorId"/>                                  
		      <beanlib:InputControl type="hidden" name="BusinessId" value="#creditor.BusinessId"/>                                  

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
							  Confirmation No: <beanlib:InputControl value="#xact.ConfirmNo"/>
						 </font>					
					</td>
				 </tr>
			     <tr>
				    <td>
						 <font face="Verdana, Arial, Helvetica, sans-serif" size="2">
							  Account Number: <beanlib:InputControl value="#creditor.CreditorId"/>                                  
						 </font>					
					</td>
				 </tr>			
			     <tr>
				    <td>
						 <font face="Verdana, Arial, Helvetica, sans-serif" size="2">
							  Account Name: <beanlib:InputControl value="#creditorext.Name"/>
						 </font>					
					</td>
				 </tr>							 	 				 
			     <tr>
				    <td>
						 <font face="Verdana, Arial, Helvetica, sans-serif" size="2">
							  Payment Amount: (<beanlib:InputControl value="#xact.XactAmount" format="$#,##0.00"/>)
						 </font>					
					</td>
				 </tr>				 				 
			     <tr>
				    <td>
						 <font face="Verdana, Arial, Helvetica, sans-serif" size="2">
							  Payment Date: <beanlib:InputControl value="#xact.XactDate" format="MM-dd-yyyy HH:mm:ss"/>
						 </font>					
					</td>
				 </tr>				 				 				 
			  </table>
		  <br>
		  
		  <table width="100%" cellpadding="0" cellspacing="0">
			  <!-- Display command buttons -->
			  <tr>
				<td colspan="2">
				  <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Finish" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
				</td>
			 </tr>		
		  </table>			   
      <input name="clientAction" type="hidden">
   </form>
 </body>
</html>
