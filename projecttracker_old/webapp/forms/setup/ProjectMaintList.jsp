<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.project.ProjectConst" %>
<%@ page import="com.bean.ProjProject" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

	<%
	  String pageTitle = "Timesheet Project Maintenance List";
	  String customerCriteria = null;
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
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/ProjectMaint.List">
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:50%; height:480px; overflow:auto">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					 <th width="2%" class="clsTableListHeader">&nbsp;</th>
					 <th width="9" class="clsTableListHeader" style="text-align:left">Id</th>
					 <th width="1%" class="clsTableListHeader">&nbsp;</th>
					 <th width="30%" class="clsTableListHeader" style="text-align:left">Name</th>
					 <th width="1%" class="clsTableListHeader">&nbsp;</th>
					 <th width="30%" class="clsTableListHeader" style="text-align:left">Client</th>
					 <th width="1%" class="clsTableListHeader">&nbsp;</th>
					 <th width="12%" class="clsTableListHeader" style="text-align:left">Effective Date</th>
					 <th width="1%" class="clsTableListHeader">&nbsp;</th>
					 <th width="12%" class="clsTableListHeader" style="text-align:left">End Date</th>
					 <th width="1%" class="clsTableListHeader">&nbsp;</th>
				 </tr>

				 <beanlib:LoopRows bean="item" list="list">
				 <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
					<td class="clsTableListHeader">
					 	<beanlib:InputControl type="radio" name="ProjId" value="#item.ProjId"/>
					</td>	
					<td>
						<beanlib:InputControl value="#item.ProjId"/>
					</td>
					<td>&nbsp;</td>
					<td>
						<beanlib:InputControl value="#item.Description"/>
					</td>
					<td>&nbsp;</td>
					<td>
					  <beanlib:InputControl value="#item.Name"/>
					</td>
					<td>&nbsp;</td>
					<td>
						 <beanlib:InputControl value="#item.EffectiveDate" format="MM-dd-yyyy"/>
					</td>
					<td>&nbsp;</td>
					<td>
						 <beanlib:InputControl value="#item.EndDate" format="MM-dd-yyyy"/>
					</td>
					<td>&nbsp;</td>					
				 </tr>
				 </beanlib:LoopRows>		
     			 <% 
		            if (pageContext.getAttribute("ROW") == null) {
					   out.println("<tr><td colspan=11 align=center>Data Not Found</td></tr>");
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
