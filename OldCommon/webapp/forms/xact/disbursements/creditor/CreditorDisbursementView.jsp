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
  </head>
  
  
  <%
 	 String pageTitle = "Creditor/Vendor Cash Disbursement Transaction View";
 	 String xactTenderCriteria = "group_id = 2";
 	 Xact xact = request.getAttribute(XactConst.CLIENT_DATA_XACT) == null ? new Xact() : (Xact) request.getAttribute(XactConst.CLIENT_DATA_XACT);
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
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactDisburse.DisbursementCreditorXactView">
		   <beanlib:InputControl dataSource="creditor" type="hidden" name="CreditorId" property="Id"/>
		   <beanlib:InputControl dataSource="creditor" type="hidden" name="BusinessId" property="BusinessId"/>                                  
		   <beanlib:InputControl dataSource="xact" type="hidden" name="XactId" property="Id"/>                                  
		   <input type="hidden" name="XactId" value="0">                                  
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
			      <beanlib:ElementValue dataSource="<%=XactConst.CLIENT_DATA_XACT %>" property="Id"/>
			   </td>
			 </tr>
			 <tr>
			   <th width="20%" class="clsTableListHeader" style="text-align:right">Amount:</th>
			   <td width="80%">
			      <beanlib:ElementValue dataSource="<%=XactConst.CLIENT_DATA_XACT %>" property="XactAmount" format="$#,##0.00;($#,##0.00)"/>
			   </td>
			 </tr>
			 <tr>
			   <th class="clsTableListHeader" style="text-align:right">Tender:</th>
			   <td>
				  <db:Lookup dataSource="" 
						 	masterCodeName=""
						 	masterCodeValue="<%=String.valueOf(xact.getTenderId())%>"
						 	type="1"
						 	lookupSource="xactTenderDso"
						 	lookupCodeName="Id"
						 	lookupDisplayName="Description"/>			   
			   </td>
			 </tr>		 
			 <tr>
			   <th class="clsTableListHeader" style="text-align:right">Tender Number:</th>
			   <td>
			      <beanlib:ElementValue dataSource="<%=XactConst.CLIENT_DATA_XACT %>" property="NegInstrNo"/>
			   </td>
			 </tr>		 		 	
			 <tr>
			   <th class="clsTableListHeader" style="text-align:right">Confirmation No.:</th>
			   <td>
			      <beanlib:ElementValue dataSource="<%=XactConst.CLIENT_DATA_XACT %>" property="ConfirmNo"/>
			   </td>
			 </tr>		 
			 <tr>
				<th valign="top" class="clsTableListHeader" style="text-align:right">Reason:</th>
				<td align="left">
				     <beanlib:ElementValue dataSource="<%=XactConst.CLIENT_DATA_XACT %>" property="Reason" format="$#,##0.00;($#,##0.00)"/>
	    	</td>
			 </tr>		 						 
		   </table>  
		   <br>
  	   <input name="<%=XactConst.REQ_REVERSE %>" type="button" value="Reverse" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
       <input name="<%=GeneralConst.REQ_BACK %>" type="button" value="Back" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
	     <input name="clientAction" type="hidden">		  
     </form>
     <db:Dispose/>
  </body>
</html>
