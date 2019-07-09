<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.constants.ProjectConst" %>
<%@ page import="com.bean.ProjProject" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Project Task Selection</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	<script>
	   function acceptSelection() {
	      var projId;
	      var taskId;
	      
	      projId = getSelectedRadioValue(DataForm.ProjectId);
	      taskId = getSelectedRadioValue(DataForm.TaskId);
   	      returnValue = projId + "~" + taskId;
	      window.close();  
	   }
	   
	   function cancelSelection() {
	      returnValue = "null";
	      window.close();
	   }
	</script>
  </head>

  <%
	  String pageTitle = "Timesheet Project Task Selection";  
	  String projCriteria = request.getParameter("ProjectCriteria") == null ? "" : request.getParameter("ProjectCriteria");
	  String taskCriteria = request.getParameter("TaskCriteria") == null ? "" : request.getParameter("TaskCriteria");
  %>

  <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
  <db:datasource id="projDso" 
                 classId="com.api.DataSourceApi" 
                 connection="con" 
                 query="ProjProjectView" 
                 where="<%=projCriteria%>" 
                 order="description"/>
               
  <db:datasource id="taskDso" 
                 classId="com.api.DataSourceApi" 
                 connection="con" 
                 query="ProjTaskView" 
                 where="<%=taskCriteria%>" 
                 order="description"/>               
    

  <body bgcolor="#FFFFFF" text="#000000">  
     <h3><strong><%=pageTitle%></strong></h3>
     <form name="DataForm" method="POST" action="">
        <font size="4" style="color:blue">Available Projects</font>
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:90%; height:180px; overflow:auto">
            <table width="100%" border="1" cellpadding="0" cellspacing="0">
				<tr>
					 <th width="2%" class="clsTableListHeader">&nbsp;</th>
					 <th width="30%" class="clsTableListHeader" style="text-align:left">Id</th>
					 <th width="68%" class="clsTableListHeader" style="text-align:left">Name</th>
				 </tr>

				 <db:LoopRows dataSource="projDso">
				    <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
					   <td class="clsTableListHeader">
					 	  <db:InputControl dataSource="projDso" type="radio" name="ProjectId" property="Id"/>
					   </td>	
					   <td>
						  <db:ElementValue dataSource="projDso" property="Id"/>
					   </td>
					   <td>
						  <db:ElementValue dataSource="projDso" property="Description"/>
					   </td>
					</tr>
				 </db:LoopRows>		
     			 <% 
		            if (pageContext.getAttribute("ROW") == null) {
					   out.println("<tr><td colspan=3 align=center>Data Not Found</td></tr>");
					}
         		 %>
            </table>
        </div>
        <br>

        <font size="4" style="color:blue">Available Tasks</font>
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:90%; height:180px; overflow:auto">
            <table width="100%" border="1" cellpadding="0" cellspacing="0">
				 <tr>
					 <th width="2%" class="clsTableListHeader">&nbsp;</th>
					 <th width="30%" class="clsTableListHeader" style="text-align:left">Id</th>
					 <th width="68%" class="clsTableListHeader" style="text-align:left">Name</th>
				 </tr>

				 <db:LoopRows dataSource="taskDso">
				    <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
					   <td class="clsTableListHeader">
					 	  <db:InputControl dataSource="taskDso" type="radio" name="TaskId" property="Id"/>
					   </td>	
					   <td>
						  <db:ElementValue dataSource="taskDso" property="Id"/>
					   </td>
					   <td>
						  <db:ElementValue dataSource="taskDso" property="Description"/>
					   </td>
					</tr>
				 </db:LoopRows>		
     			 <% 
		            if (pageContext.getAttribute("ROW") == null) {
					   out.println("<tr><td colspan=3 align=center>Data Not Found</td></tr>");
					}
         		 %>
            </table>
        </div>
        <br>
        
        <!-- Display command buttons -->
        <table width="50%" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="2">
				    <input name="<%=ProjectConst.REQ_SAVE%>" type="button" value="Accept" style="width:90" onClick="acceptSelection()">
					<input name="<%=ProjectConst.REQ_CANCEL%>" type="button" value="Cancel" style="width:90" onClick="cancelSelection()">
				</td>
			</tr>
        </table>
     </form>
     <db:Dispose/>
 </body>
</html>
