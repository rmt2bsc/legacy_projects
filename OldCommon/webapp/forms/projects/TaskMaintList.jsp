<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.constants.ProjectConst" %>
<%@ page import="com.bean.ProjTask" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Project Task Maintenance List</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>

  <%
	  String pageTitle = "Timesheet Task Maintenance List";
	  String billableText = null;
  %>

  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/Project.TaskMaintList">
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:55%; height:480px; overflow:auto">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					 <th width="2%" class="clsTableListHeader">&nbsp;</th>
					 <th width="18" class="clsTableListHeader" style="text-align:left">Id</th>
					 <th width="1%" class="clsTableListHeader">&nbsp;</th>
					 <th width="60%" class="clsTableListHeader" style="text-align:left">Description</th>
					 <th width="1%" class="clsTableListHeader">&nbsp;</th>
					 <th width="18%" class="clsTableListHeader" style="text-align:left">Billable</th>
					 <th width="1%" class="clsTableListHeader">&nbsp;</th>
				 </tr>

				 <beanlib:LoopRows bean="item" beanClassId="com.bean.ProjTask" list="list">
				 <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
					<td class="clsTableListHeader">
					 	<beanlib:InputControl dataSource="item" type="radio" name="Id" property="Id"/>
					</td>	
					<td>
						<beanlib:ElementValue dataSource="item" property="Id"/>
					</td>
					<td>&nbsp;</td>
					<td>
						<beanlib:ElementValue dataSource="item" property="Description"/>
					</td>
					<td>&nbsp;</td>
					<td align="center">
                      <%=(item.getBillable() == 1 ? "Yes" : "No")%>
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

        <!-- Display command buttons -->
        <table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="2">
				    <input name="<%=ProjectConst.REQ_ADD%>" type="button" value="Add" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
					<input name="<%=ProjectConst.REQ_EDIT%>" type="button" value="Edit" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
					<input name="<%=ProjectConst.REQ_DELETE%>" type="button" value="Delete" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
				</td>
			</tr>
        </table>
		<input name="clientAction" type="hidden">
     </form>

 </body>
</html>
