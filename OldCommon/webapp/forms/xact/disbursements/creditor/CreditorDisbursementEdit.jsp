<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.CustomerWithName" %>
<%@ page import="com.bean.Xact"%>
<%@ page import="com.constants.XactConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.AccountingConst" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>

  <head>
    <title>Transaction Search Results</title>  
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
//	 String temp = (String) request.getAttribute("XactTypeId");
 	 String pageTitle = "Creditor/Vendor Payment";
 	 String xactTenderCriteria = "group_id = 2";
//	 int xactTypeId = Integer.parseInt(temp);
 	 Xact xact = request.getAttribute("xact") == null ? new Xact() :  (Xact) request.getAttribute("xact") ;
  %>
  
  <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
   <db:datasource id="xactTenderDso" 
				classId="com.api.DataSourceApi" 
				connection="con" 
				query="XactCodesView" 
				where="<%= xactTenderCriteria %>"
				order="description" 
				type="xml"/>					   
  <body>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactDisburse.DisbursementCreditorPayment">
	   <beanlib:InputControl dataSource="creditor" type="hidden" name="CreditorId" property="Id"/>
	   <beanlib:InputControl dataSource="creditor" type="hidden" name="BusinessId" property="BusinessId"/>                                  
	   <beanlib:InputControl dataSource="business" type="hidden" name="Website" property="Website"/>                                  
	   <input type="hidden" name="XactId" value="0">                                  
	   <table width="85%" border="0">
		 <caption align="left"><h3><%=pageTitle%></h3></caption>
		 <tr>
		   <td colspan="2">   
			  <%@include file="/forms/xact/disbursements/creditor/CreditorXactHdr.jsp"%>
		   </td>
		 </tr>
		 <tr>
		   <th width="20%" class="clsTableListHeader" style="text-align:right">Amount:</th>
		   <td width="80%"><input type="text" name="XactAmount" value="<%=xact.getXactAmount()%>"></td>
		 </tr>
		 <tr>
		   <th class="clsTableListHeader" style="text-align:right">Tender:</th>
		   <td>
  	   		  <db:InputControl dataSource="xactTenderDso"
											  	   type="select"
												     name="TenderId"
												     codeProperty="Id"
												     displayProperty="Description"
												     selectedValue="<%=String.valueOf(xact.getTenderId()) %>"/>
		   </td>
		 </tr>		 
		 <tr>
		   <th class="clsTableListHeader" style="text-align:right">Tender Number:</th>
		   <td>
		      <input type="text" name="NegInstrNo" value="<%=xact.getNegInstrNo()== null ? "" : xact.getNegInstrNo()%>">
		   </td>
		 </tr>		 		 	
		 <tr>
		   <th class="clsTableListHeader" style="text-align:right">Payment Confirmation:</th>
		   <td><input type="text" name="ConfirmNo"></td>
		 </tr>		 
		  <tr>
			<th valign="top" class="clsTableListHeader" style="text-align:right">Reason:</th>
			<td align="left">
			     <textarea name="Reason" rows="2" cols="50" ></textarea>
    		</td>
		 </tr>		 						 
	   </table>  
	   <br>
  	    <input type="button" name="website" value="Creditor Website" style="width:120" onClick='openNewBrowser(DataForm.Website.value)'>
			  <input type="button" name="<%=GeneralConst.REQ_SAVE%>" value="Apply" style="width:120" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
			  <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style="width:120" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
			  <input name="clientAction" type="hidden">		  
     </form>
     <db:Dispose/>
  </body>
</html>
