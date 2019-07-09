<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.project.ProjectConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
    String pageTitle = "Client Bulk Timesheet Invoicing";
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
     <h3>
        <strong><%=pageTitle%></strong>
     </h3>
     <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:40%; height:30px; overflow:auto">
     <table width="100%" border="0" cellpadding="0" cellspacing="0">
       <tr>
          <td><img src="<%=APP_ROOT%>/images/ts_draft.png" alt="Draft Status" style="border:none"/>&nbsp;<font size="2" font-family: sans-serif">Draft</font></td>
          <td><img src="<%=APP_ROOT%>/images/ts_approved.png" alt="Approved Status" style="border:none"/>&nbsp;<font size="2" style="font-family: sans-serif">Approved</font></td>
          <td><img src="<%=APP_ROOT%>/images/ts_decline.png" alt="Declined Status" style="border:none"/>&nbsp;<font size="2" style="font-family: sans-serif">Declined</font></td>
          <td><img src="<%=APP_ROOT%>/images/ts_invoiced.png" alt="Invoiced Status" style="border:none"/>&nbsp;<font size="2" style="font-family: sans-serif">Invoiced</font></td>
          <td><img src="<%=APP_ROOT%>/images/ts_unknown.png" alt="Recieved or Submitted Status" style="border:none"/>&nbsp;<font size="2" style="font-family: sans-serif">Received or Submitted</font></td>
       </tr>
     </table>
     </div>
     <br>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/TimesheetInvoice.Bulk">
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:60%; height:480px; overflow:auto">
	        <table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
				   <th width="5%" class="clsTableListHeader">&nbsp;</th>
 				   <th width="5%" class="clsTableListHeader">&nbsp;</th>
				   <th width="65%" class="clsTableListHeader" style="text-align:left">Client Name</th>
				   <th width="25%" class="clsTableListHeader" style="text-align:center">Timesheet Count</th>
			    </tr>
		
				<beanlib:LoopRows bean="item" list="<%=ProjectConst.CLIENT_DATA_CLIENT_TS_SUM%>">
				   <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
				       <td>
				            <!-- Only provide checkboxes for those entries that are approved -->
				       		<gen:Evaluate expression="#item.StatusId">
								 <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_APPROVED%>">
								     <beanlib:InputControl type="checkbox" name="selClients" value="#item.ClientId"/>
								 </gen:When>
								 <gen:WhenElse>&nbsp;</gen:WhenElse>
							</gen:Evaluate>	
				       </td>
				       <td>
				            <!-- Diplay icon that corresponds to a particular timesheet status -->
							<gen:Evaluate expression="#item.StatusId">
								 <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_APPROVED%>">
								     <img src="<%=APP_ROOT%>/images/ts_approved.png" alt="Approved Timesheet Total" style="border:none"/>
								 </gen:When>
								 <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_DRAFT%>">
								     <img src="<%=APP_ROOT%>/images/ts_draft.png" alt="Draft Timesheet Total" style="border:none"/>
								 </gen:When>
								 <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_RECEIVED%>">
								     <img src="<%=APP_ROOT%>/images/ts_unknown.png" alt="Recieved Timesheet Total" style="border:none"/>
								 </gen:When>
 								 <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_SUBMITTED%>">
								     <img src="<%=APP_ROOT%>/images/ts_unknown.png" alt="Submitted Timesheet Total" style="border:none"/>
								 </gen:When>
								 <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_DECLINED%>">
								     <img src="<%=APP_ROOT%>/images/ts_decline.png" alt="Declined Timesheet Total" style="border:none"/>
								 </gen:When>
								 <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_INVOICED%>">
								     <img src="<%=APP_ROOT%>/images/ts_invoiced.png" alt="Invoiced Timesheet Total" style="border:none"/>
								 </gen:When>
								 <gen:WhenElse>&nbsp;</gen:WhenElse>
							</gen:Evaluate>	
				        </td>
				        <td>
				           <font size="2">
	  			              <beanlib:InputControl value="#item.Name"/>
	  			           </font>
				        </td>
				        <td align="center">
				            <font size="2">
				               <beanlib:InputControl value="#item.TimesheetCount"/>
				            </font>
				        </td>
  			       </tr>
			    </beanlib:LoopRows>
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
		           <input name="<%=ProjectConst.REQ_BULKINVOICESUBMIT%>" type="button" value="Invoice Client" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
			   	   <input name="<%=ProjectConst.REQ_BACK%>" type="button" value="Timesheets" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
				</td>
			</tr>
        </table>
        <input name="clientAction" type="hidden">
     </form>
 </body>
</html>
