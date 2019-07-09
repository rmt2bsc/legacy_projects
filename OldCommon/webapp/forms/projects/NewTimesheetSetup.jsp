<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.bean.criteria.TimesheetCriteria" %>
<%@ page import="com.constants.ProjectConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>New Timesheet Setup</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/Timesheet.js"></script>
  </head>

  <%
	  String pageTitle = "New Timesheet Setup";
  %>

  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

       <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/Project.TimesheetNewSetup">
        <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:40%; height:290px">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">   
			<tr>
				<td width="10%" class="clsTableFormHeaderClear">Period End Date</td>
				<td width="15%">
				     <beanlib:WrapperInputControl dataSource="<%=ProjectConst.CLIENT_DATA_DATES%>" type="select" name="EndPeriod"/>
				</td>
			</tr>
			<tr>
			  <td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				 <td width="10%" class="clsTableFormHeaderClear">Client</td>
				 <td width="15%">
				   <beanlib:InputControl dataSource="<%=ProjectConst.CLIENT_DATA_CLIENTS%>" type="select" name="ProjClientId" codeProperty="CustomerId" displayProperty="Name"  size="20"/>
				 </td>
			</tr>
         </table>
         <br>
        </div>
        <br>
 
        <!-- Add the submit button -->
        <table width="30%">
           <tr>
               <td> 
                  <input name="<%=ProjectConst.REQ_ADD%>" type="button" value="Next" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
							 </td>							 
				 </tr>
			</table>
			
		<input name="clientAction" type="hidden">
		<input name="Id" type="hidden">
     </form>
 </body>
</html>
