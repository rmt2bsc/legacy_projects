<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.project.ProjectConst" %>
<%@ page import="com.bean.ProjTask" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>
   <%
  	  String pageTitle = "Timesheet Task Maintenance Edit";    
 	 %>
<html>
  <head>
    <title><%=pageTitle%></title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	  <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
  	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>
  

	
  <body bgcolor="#FFFFFF" text="#000000">
    <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/TaskMaint.Edit">
	  <h3><strong><%=pageTitle%></strong></h3>
  	<br>
		<table width="50%" border="0" cellspacing="2" cellpadding="0">
				<tr> 
					 <td width="25%" class="clsTableFormHeader">
						   <font size="3"><b>Task Id</b></font>
					 </td>
					 <td align="left" width="75%" >
					     <beanlib:InputControl type="hidden" name="TaskId" value="#task.TaskId"/>                                  
					 	   <beanlib:InputControl value="#task.TaskId"/>                                  
					 </td>
				</tr>
				<tr> 
					 <td class="clsTableFormHeader">
						<font size="3"><b>Description</b></font>
					 </td>
					 <td align="left">
						  <beanlib:InputControl type="text" name="Description" value="#task.Description" size="60"/>                                  
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
					                <input type="radio" name="Billable" value="1" 
     					                 <gen:Evaluate expression="#task.billable">
    					                    <gen:When expression="1">checked</gen:When>
		    			                 </gen:Evaluate>>
					            </td>
								<th>No</th>
					            <td>
					                <input type="radio" name="Billable" value="0" 
     					                 <gen:Evaluate expression="#task.billable">
    					                    <gen:When expression="0">checked</gen:When>
		    			                 </gen:Evaluate>>					                
					            </td>					            
					        </tr>
					     </table>
					 </td>
				</tr>
		</table>

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
		 
    <!-- Display command buttons -->
    <div id="ButtonLayer" style="position:relative; top:20px; width:100%; height:30px; z-index:1">
			  <table width="100%" cellpadding="0" cellspacing="0">
				  <tr>
							<td colspan="2">     
								  <input name="<%=ProjectConst.REQ_SAVE%>" type="button" value="Apply" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
								  <input name="<%=ProjectConst.REQ_BACK%>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
							</td>
					 </tr>		
			  </table>			   
     </div>
	    <input name="clientAction" type="hidden">
   </form>
 </body>
</html>
