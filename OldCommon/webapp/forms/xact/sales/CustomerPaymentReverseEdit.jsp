<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.CustomerWithName" %>
<%@ page import="com.bean.Xact"%>
<%@ page import="com.constants.XactConst" %>
<%@ page import="com.constants.SalesConst" %>
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
 	 String pageTitle = "Reverse Customer Payment Transaction";
  %>
  
  <body>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactSales.CustomerSalesXactReverse">
	   <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" type="hidden" name="CustomerId" property="Id"/>
	   <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" type="hidden" name="BusinessId" property="BusinessId"/>                                  
	   <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" type="hidden" name="PersonId" property="PersonId"/> 
	   <beanlib:InputControl dataSource="<%=XactConst.CLIENT_DATA_XACT%>" type="hidden" name="XactId" property="Id"/> 

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
    		  <beanlib:ElementValue dataSource="<%=XactConst.CLIENT_DATA_XACT%>" property="XactAmount"/> 
    		  <beanlib:InputControl dataSource="<%=XactConst.CLIENT_DATA_XACT%>" type="hidden" name="XactAmount" property="XactAmount"/> 
		   </td>
		 </tr>
		  <tr>
			<th width="20%" valign="top" class="clsTableListHeader" style="text-align:right">Reason:</th>
			<td width="80%" align="left">
			     <textarea name="Reason" rows="2" cols="50" ></textarea>
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
      	  <input type="button" name="<%=XactConst.REQ_REVERSE%>" value="Apply" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
          <input name="<%=GeneralConst.REQ_BACK%>" type="button" value="Back" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">      	  
          <input name="clientAction" type="hidden">		  
        </td>
      </tr>
    </table>

     </form>
     <db:Dispose/>
  </body>
</html>
