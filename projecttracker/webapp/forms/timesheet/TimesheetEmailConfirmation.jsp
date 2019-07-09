<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.constants.GeneralConst" %>


<gen:InitAppRoot id="APP_ROOT"/>
   
<html>
  <head>
    <title>Timesheet Email Delivery Confirmation</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	  <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>
 
   
  <body bgcolor="#FFFFFF" text="#000000" onLoad="initPage()">
       <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/Timesheet.Search">
          <beanlib:InputControl name="Id" type="hidden" value="#timesheet.TimesheetId"/>
		      <h3><strong>Timesheet Email Delivery Confirmation</strong></h3>
		      <table>
			     <tr>
				    <td>
						 <font face="Verdana, Arial, Helvetica, sans-serif" size="2">
							  Message: <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
						 </font>					
					  </td>
					</tr>
			  </table>
		  <br>
		  
		  <table width="100%" cellpadding="0" cellspacing="0">
			  <!-- Display command buttons -->
			  <tr>
				<td colspan="2">
				  <input type="button" name="<%=GeneralConst.REQ_EDIT%>" value="Return" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
				</td>
			 </tr>		
		  </table>			   
      <input name="clientAction" type="hidden">
   </form>
 </body>
</html>
