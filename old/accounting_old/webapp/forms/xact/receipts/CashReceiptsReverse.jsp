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
  </head>
  
  <%
 	 String pageTitle = "Reverse Customer Cash Receipt Transaction";
  %>
  
  <body>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/CashReceipts.Reverse">
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
		   <th width="20%" class="clsTableListHeader" style="text-align:right">Amount:</th>
		   <td width="80%">
    		  <beanlib:InputControl value="#xact.XactAmount"/> 
    		  <beanlib:InputControl type="hidden" name="XactAmount" value="#xact.XactAmount"/> 
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
	  
    <!--  Command buttons -->
    <table>
      <tr>
        <td>
      	  <input type="button" name="<%=XactConst.REQ_REVERSE%>" value="Apply" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
          <input type="button" name="<%=GeneralConst.REQ_BACK%>"  value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">      	  
          <input name="clientAction" type="hidden">		  
        </td>
      </tr>
    </table>

     </form>
  </body>
</html>
