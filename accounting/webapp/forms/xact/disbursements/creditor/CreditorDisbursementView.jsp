<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.xact.sales.CustomerWithName" %>
<%@ page import="com.bean.Xact"%>
<%@ page import="com.xact.XactConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

  <%
   	 String pageTitle = "Creditor/Vendor Cash Disbursement Transaction View";
  %>
      
<html>
  <head>
    <title><%=pageTitle %></title>  
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
  	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">   
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
  	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>   
  </head>
  
  <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
  <db:datasource id="xactTenderDso" 
								classId="com.api.DataSourceApi" 
								connection="con" 
								query="XactCodesView" 
								where="xact_code_grp_id = 2"
								order="description" 
								type="xml"/>					   
  <body>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/DisbursementsCreditor.XactView">
		   <beanlib:InputControl type="hidden" name="CreditorId" value="#creditor.CreditorId"/>
		   <beanlib:InputControl type="hidden" name="BusinessId" value="#creditor.BusinessId"/>                                  
		   <beanlib:InputControl type="hidden" name="XactId" value="#xact.XactId"/>                                  

		   <table width="85%" border="0">
			 <caption align="left"><h3><%=pageTitle%></h3></caption>
			 <tr>
			   <td colspan="2">   
				  <%@include file="/forms/xact/disbursements/creditor/CreditorXactHdr.jsp"%>
			   </td>
			 </tr>
			 <tr>
			   <th width="20%" class="clsTableListHeader" style="text-align:right">Trans. Id:</th>
			   <td width="80%">
			      <beanlib:InputControl value="#xact.XactId"/>
			   </td>
			 </tr>
			 <tr>
			   <th width="20%" class="clsTableListHeader" style="text-align:right">Amount:</th>
			   <td width="80%">
			      <beanlib:InputControl value="#xact.XactAmount" format="$#,##0.00;($#,##0.00)"/>
			   </td>
			 </tr>
			 <tr>
			   <th class="clsTableListHeader" style="text-align:right">Tender:</th>
			   <td>
				  <db:Lookup dataSource="" 
						 	masterCodeName=""
						 	masterCodeValue="#xact.TenderId"
						 	type="1"
						 	lookupSource="xactTenderDso"
						 	lookupCodeName="XactCodeId"
						 	lookupDisplayName="Description"/>			   
			   </td>
			 </tr>		 
			 <tr>
			   <th class="clsTableListHeader" style="text-align:right">Tender Number:</th>
			   <td>
			      <beanlib:InputControl value="#xact.NegInstrNo"/>
			   </td>
			 </tr>		 		 	
			 <tr>
			   <th class="clsTableListHeader" style="text-align:right">Confirmation No.:</th>
			   <td>
			      <beanlib:InputControl value="#xact.ConfirmNo"/>
			   </td>
			 </tr>		 
			 <tr>
				<th valign="top" class="clsTableListHeader" style="text-align:right">Reason:</th>
				<td align="left">
				     <beanlib:InputControl value="#xact.Reason" format="$#,##0.00;($#,##0.00)"/>
	    	</td>
			 </tr>		 						 
		   </table>  
		   <br>
  	   <input name="<%=XactConst.REQ_REVERSE%>" type="button" value="Reverse" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
       <input name="<%=GeneralConst.REQ_BACK %>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
	     <input name="clientAction" type="hidden">		  
     </form>
     <db:Dispose/>
  </body>
</html>
