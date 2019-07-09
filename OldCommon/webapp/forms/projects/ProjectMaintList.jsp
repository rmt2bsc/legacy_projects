<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.constants.ProjectConst" %>
<%@ page import="com.bean.ProjProject" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Project Maintenance List</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>

  <%
	  String pageTitle = "Timesheet Project Maintenance List";
	  String customerCriteria = null;
  %>

<db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>


  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/Project.ProjectMaintList">
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:97%; height:480px; overflow:auto">
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

				 <beanlib:LoopRows bean="item" beanClassId="com.bean.ProjProject" list="list">
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
					<td>
					   <%
					      String criteria = " customer_id = " + item.getProjClientId();
					      System.out.println("Criteria: " + criteria);
					   %>
					   <db:datasource id="customerDso" 
						              classId="com.api.DataSourceApi" 
									  connection="con" 
									  query="VwCustomerNameView" 
									  where="<%=criteria%>"
									  type="xml"/>
					   <db:LoopRows dataSource="customerDso">
     					   <db:ElementValue dataSource="customerDso" property="Name"/>
                       </db:LoopRows>
					</td>
					<td>&nbsp;</td>
					<td>
						 <beanlib:ElementValue dataSource="item" property="EffectiveDate" format="MM-dd-yyyy"/>
					</td>
					<td>&nbsp;</td>
					<td>
						 <beanlib:ElementValue dataSource="item" property="EndDate" format="MM-dd-yyyy"/>
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
<db:Dispose/>
 </body>
</html>
