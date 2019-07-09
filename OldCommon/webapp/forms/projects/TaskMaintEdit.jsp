<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.constants.ProjectConst" %>
<%@ page import="com.bean.ProjTask" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Project Task Maintenance Edit</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>
  
   <%
      ProjTask task = request.getAttribute("task") == null ? new ProjTask() : (ProjTask) request.getAttribute("task");
  	  String pageTitle = "Timesheet Task Maintenance Edit";    
	%>
	
  <body bgcolor="#FFFFFF" text="#000000">
       <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/Project.TaskMaintEdit">
		  <h3><strong><%=pageTitle%></strong></h3>
  		<br>
	  
		<table width="50%" border="0" cellspacing="2" cellpadding="0">
				<tr> 
					 <td width="25%" class="clsTableFormHeader">
						   <font size="3"><b>Task Id</b></font>
					 </td>
					 <td align="left" width="75%" >
					     <beanlib:InputControl dataSource="task" type="hidden" name="Id" property="Id"/>                                  
					 	 <beanlib:ElementValue dataSource="task" property="Id"/>                                  
					 </td>
				</tr>
				<tr> 
					 <td class="clsTableFormHeader">
						<font size="3"><b>Description</b></font>
					 </td>
					 <td align="left">
						<beanlib:InputControl dataSource="task" type="text" name="Description" property="Description" size="60"/>                                  
					 </td>
				</tr>
				<tr> 
					 <td class="clsTableFormHeader">
						<font size="3"><b>Billiable?</b></font>
					 </td>
					 <td align="left">
					     <table width="50%" border="0">
					        <tr>
					            <th>Yes</th>
					            <td>
					                <input type="radio" name="Billable" value="1" <%=task.getBillable() == 1 ? "checked" : ""%>>
					            </td>
								<th>No</th>
					            <td>
					                <input type="radio" name="Billable" value="0" <%=task.getBillable() == 0 ? "checked" : ""%>>
					            </td>					            
					        </tr>
					     </table>
					 </td>
				</tr>
		</table>

    <!-- Display command buttons -->
    <div id="ButtonLayer" style="position:relative; top:20px; width:100%; height:30px; z-index:1">
			  <table width="100%" cellpadding="0" cellspacing="0">
				  <tr>
							<td colspan="2">     
								  <input name="<%=ProjectConst.REQ_SAVE%>" type="button" value="Apply" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
								  <input name="<%=ProjectConst.REQ_BACK%>" type="button" value="Back" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
							</td>
					 </tr>		
			  </table>			   
     </div>
	    <input name="clientAction" type="hidden">
   </form>
 </body>
</html>
