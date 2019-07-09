<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.constants.PurchasesConst" %>

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
      String msg = (request.getAttribute("msg") == null ? "" : (String) request.getAttribute("msg"));
   %>
   
  <body bgcolor="#FFFFFF" text="#000000" onLoad="initPage()">
       <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactPurchase.VendorDetails">
   	        <beanlib:InputControl dataSource="vendor" type="hidden" name="VendorId" property="CreditorId"/>                                  
					  <beanlib:InputControl dataSource="vendor" type="hidden" name="CreditorId" property="CreditorId"/>                                  
					  <beanlib:InputControl dataSource="vendor" type="hidden" name="BusinessId" property="BusinessId"/>                                  
		        <beanlib:InputControl dataSource="po" type="hidden" name="PurchaseOrderId" property="Id"/>                                  

						<h3><strong>Purchase Order Confirmation</strong></h3>
		  
		  			<%@include file="/forms/purchases/PurchaseOrderHeader.jsp"%>
		  			<br>
		  
		        <!-- Display message and command buttons -->
						<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
										<td colspan="2">
												<font color="red"><%=msg%></font>
										</td>
								 </tr>								
								<tr>
										<td colspan="2">
												<input name="<%=PurchasesConst.REQ_SEARCH%>" type="button" value="Finish" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
										</td>
								 </tr>		
					</table>			   
          <input name="clientAction" type="hidden">
	     </form>
	</body>
</html>
