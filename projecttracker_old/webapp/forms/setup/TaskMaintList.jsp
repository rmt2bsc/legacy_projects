<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.project.ProjectConst" %>
<%@ page import="com.bean.ProjTask" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>


  <%
	  String pageTitle = "Timesheet Task Maintenance List";
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
     <h3><strong><%=pageTitle%></strong></h3>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/TaskMaint.List">
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:35%; height:480px; overflow:auto">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					 <th width="2%" class="clsTableListHeader">&nbsp;</th>
					 <th width="18" class="clsTableListHeader">Id</th>
					 <th width="1%" class="clsTableListHeader">&nbsp;</th>
					 <th width="60%" class="clsTableListHeader" style="text-align:left">Description</th>
					 <th width="1%" class="clsTableListHeader">&nbsp;</th>
					 <th width="18%" class="clsTableListHeader">Billable</th>
					 <th width="1%" class="clsTableListHeader">&nbsp;</th>
				 </tr>

				 <beanlib:LoopRows bean="item" list="list">
				 <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
					<td class="clsTableListHeader">
					 	<beanlib:InputControl type="radio" name="Id" value="#item.TaskId"/>
					</td>	
					<td>
						<beanlib:InputControl value="#item.TaskId"/>
					</td>
					<td>&nbsp;</td>
					<td>
						<beanlib:InputControl value="#item.Description"/>
					</td>
					<td>&nbsp;</td>
					<td align="center">
							<gen:Evaluate expression="#item.billable">
							  <gen:When expression="1">Yes</gen:When>
							  <gen:WhenElse>No</gen:WhenElse>   
							</gen:Evaluate>
					</td>
					<td>&nbsp;</td>
				 </tr>
				 </beanlib:LoopRows>		
     			   <% 
		            if (pageContext.getAttribute("ROW") == null) {
					         out.println("<tr><td colspan=7 align=center>Data Not Found</td></tr>");
					      }
         		 %>
            </table>
        </div>
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

     <!-- Display command buttons -->
     <table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="2">
			    <input name="<%=ProjectConst.REQ_ADD%>" type="button" value="Add" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
					<input name="<%=ProjectConst.REQ_EDIT%>" type="button" value="Edit" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
					<input name="<%=ProjectConst.REQ_DELETE%>" type="button" value="Delete" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
					<input name="<%=ProjectConst.REQ_BACK%>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
				</td>
			</tr>
        </table>
		<input name="clientAction" type="hidden">
     </form>

 </body>
</html>
