<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.ProjectConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Bulk Timesheet Invoice</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>

  <%
    String criteria = "ts_status_id = " + ProjectConst.TIMESHEET_STATUS_APPROVED;
    String pageTitle = "Client Bulk Timesheet Invoice";
  %>
  <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
  <db:datasource id="listDso" 
                 classId="com.api.DataSourceApi" 
                 connection="con"
	  			 query="VwClientTimesheetStatusSummaryView"
	  			 where="<%=criteria%>"
	 			 order="name"
				 type="xml"/>

  <body bgcolor="#FFFFFF" text="#000000">
     <h3>
        <strong><%=pageTitle%></strong>
     </h3>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/Project.BulkTimesheetInvoice">
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:60%; height:480px; overflow:auto">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
				   <th width="5%" class="clsTableListHeader">&nbsp;</th>
   				   <th width="5%" class="clsTableListHeader">&nbsp;</th>
				   <th width="65%" class="clsTableListHeader" style="text-align:left">Client Name</th>
				   <th width="25%" class="clsTableListHeader" style="text-align:center">Timesheet Count</th>
			   </tr>

   			   <db:LoopRows dataSource="listDso">
			     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
			        <td>
			            <db:InputControl dataSource="listDso" type="checkbox" name="selClients" property="ClientId"/>
			        </td>
			        <td>
						<gen:Evaluate expression="<%=listDso.getColumnValue("TsStatusId")%>">
							 <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_APPROVED%>">
							     <img src="<%=APP_ROOT%>/images/flag_green.gif" alt="Approved Timesheets are available for invoicing" style="border:none"/>
							 </gen:When>
							 <gen:WhenElse>&nbsp;</gen:WhenElse>
						</gen:Evaluate>	
			        </td>
			        <td>
			           <font size="2">
  			              <db:ElementValue dataSource="listDso" property="Name"/>
  			           </font>
			        </td>
			        <td align="center">
			            <font size="2">
			               <db:ElementValue dataSource="listDso" property="TimesheetCount"/>
			            </font>
			        </td>
			     </tr>
			   </db:LoopRows>
			   <% if (pageContext.getAttribute("ROW") == null) {
				     out.println("<tr><td colspan=6 align=center>Data Not Found</td></tr>");
				  }
			   %>

            </table>
        </div>
        <br>

        <!-- Display command buttons -->
        <table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="2">
			        <input name="<%=ProjectConst.REQ_BULKINVOICESUBMIT%>" type="button" value="Invoice Client" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
					<input name="<%=ProjectConst.REQ_BACK%>" type="button" value="Timesheets" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
				</td>
			</tr>
        </table>
		<input name="clientAction" type="hidden">
     </form>
     <db:Dispose/>
 </body>
</html>
