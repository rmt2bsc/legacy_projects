<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ page import="com.bean.criteria.TimesheetCriteria" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.ProjectConst" %>
<%@ page import="com.api.DaoApi" %>


<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Timesheet Search Criteria</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	<script type='text/javascript' language='javascript' src="<%=APP_ROOT%>/js/datetimepicker.js"></script>   
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/Timesheet.js"></script>
  </head>

  <%
	  String pageTitle = "Timesheet List Console";
	  String mainCriteria = null;
	  String mode = request.getParameter(ProjectConst.CLIENT_DATA_MODE);
	  if (mode == null) {
	     mode = (String) request.getAttribute(ProjectConst.CLIENT_DATA_MODE);
	  }
	  if (mode.equalsIgnoreCase(ProjectConst.TIMESHEET_MODE_EMPLOYEE)) {
	     pageTitle = "Employee's " + pageTitle;
	  } 
	  else if (mode.equalsIgnoreCase(ProjectConst.TIMESHEET_MODE_MANAGER)) {
	     pageTitle = "Manager's " + pageTitle;
	  }
  %>

  <%@include file="/includes/SessionQuerySetup.jsp"%>

  <%
	  TimesheetCriteria query = (custObj != null && custObj instanceof TimesheetCriteria ? (TimesheetCriteria) baseQueryObj.getCustomObj() : TimesheetCriteria.getInstance());
	  mainCriteria = baseQueryObj.getWhereClause();  
  %>

  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

     <!--  Begin Search Criteria section -->
     <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/Project.TimesheetList">
        <font size="4" style="color:blue">Selection Criteria</font>
        <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:90%; height:55px">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
             <tr>
				   <td width="10%" class="clsTableFormHeader">Status</td>
				   <td width="15%">
					 <beanlib:InputControl dataSource="<%=ProjectConst.CLIENT_DATA_STATUSES%>"
										   type="select"
										   name="qry_ProjTimesheetStatusId"
										   codeProperty="Id"
										   displayProperty="Name"
	                                       selectedValue="<%=query.getQry_ProjTimesheetStatusId()%>"/>								  
				   </td>
				   <td width="10%" class="clsTableFormHeader">Employee</td>
				   <td width="15%">
					 <beanlib:InputControl dataSource="<%=ProjectConst.CLIENT_DATA_EMPLOYEES%>"
										   type="select"
										   name="qry_ProjEmployeeId"
										   codeProperty="EmployeeId"
										   displayProperty="Shortname"
										   selectedValue="<%=query.qetQry_ProjEmployeeId()%>"/>
				   </td>
				   <td width="10%" class="clsTableFormHeader">Client</td>
				   <td width="20%">
					 <beanlib:InputControl dataSource="<%=ProjectConst.CLIENT_DATA_CLIENTS%>"
										   type="select"
										   name="qry_ProjClientId"
										   codeProperty="CustomerId"
										   displayProperty="Name"
										   selectedValue="<%=query.getQry_ProjClientId()%>"/>
				   </td>							   
             </tr>
			 <tr>
				   <td class="clsTableFormHeader">Start Date</td>
				   <td>
				       <input type="text" name="qry_EndPeriod1" size="10" value="<%=query.getQry_EndPeriod1()%>">
					     <a href="javascript:NewCal('qry_EndPeriod1','mmddyyyy')"><img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>				       
				   </td>
				   <td class="clsTableFormHeader">End Date</td>
				   <td>
				       <input type="text" name="qry_EndPeriod2" size="10" value="<%=query.getQry_EndPeriod2()%>">
					     <a href="javascript:NewCal('qry_EndPeriod2','mmddyyyy')"><img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>				       
				   </td>
				   <td class="clsTableFormHeader">&nbsp;</td>
				   <td align="right">&nbsp;</td>					
			 </tr>
          </table>
        </div>
        <a href="javascript:handleAction(MAIN_DETAIL_FRAME, document.SearchForm, '<%=ProjectConst.REQ_SEARCH%>')">
	      <img src="<%=APP_ROOT%>/images/element_find.gif" alt="Search for Timesheet" style="border:none">
	    </a>
        <br><br>
     <!--  Begin Search Criteria section -->

        <font size="4" style="color:blue">Search Results</font>
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:90%; height:480px; overflow:auto">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
				   <th width="18%" class="clsTableListHeader" style="text-align:left" valign="bottom">Employee</th>
				   <th width="8%" class="clsTableListHeader"  style="text-align:left" valign="bottom">Week Ending</th>
				   <th width="1%" class="clsTableListHeader">&nbsp;</th>
				   <th width="10" class="clsTableListHeader" style="text-align:left" valign="bottom">Status</th>
				   <th width="6%" class="clsTableListHeader" style="text-align:center" valign="bottom">Billable Hours</th>
				   <th width="8%" class="clsTableListHeader" style="text-align:center" valign="bottom">Non-Billable Hours</th>
				   <th width="3%" class="clsTableListHeader">&nbsp;</th>
				   <th width="14%" class="clsTableListHeader" style="text-align:left" valign="bottom">Sheet Id</th>
				   <th width="32%" class="clsTableListHeader" style="text-align:left" valign="bottom">Client</th>
			   </tr>

   			   <beanlib:LoopRows bean="item" list="<%=ProjectConst.CLIENT_DATA_TIMESHEETS%>">
			     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
			        <td>
			            <font size="2">
			               <beanlib:ElementValue dataSource="item" property="Shortname"/>
			            </font>
			        </td>
			        <td>
			           <font size="2">
			              <beanlib:ElementValue dataSource="item" property="EndPeriod" format="MM-dd-yyyy"/>
			           </font>
			        </td>
			        <td align="right">&nbsp;</td>
			        <td>
			           <font size="2">
  			              <beanlib:ElementValue dataSource="item" property="StatusName"/>
  			           </font>
			        </td>
			        <td align="center">
			            <font color="blue" size="2">
			               <beanlib:ElementValue dataSource="item" property="BillHrs" format="#,##0.00;($#,##0.00)"/>
			            </font>
			        </td>
			        <td align="center">
			            <font color="blue" size="2">
			               <beanlib:ElementValue dataSource="item" property="NonBillHrs"  format="#,##0.00;($#,##0.00)"/>
			            </font>
			        </td>
			        <td align="right">&nbsp;</td>
			        <td>
				        <gen:Evaluate expression="#item.ProjTimesheetStatusId">
				           <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_DRAFT%>">
				              <gen:Evaluate expression="<%=mode%>">
				                 <gen:When expression="<%=ProjectConst.TIMESHEET_MODE_MANAGER%>">
							       <font size="2">
    	                             <beanlib:ElementValue dataSource="item" property="DisplayValue"/>
			                       </font>
						         </gen:When>
				                 <gen:WhenElse> 
					               <a href="javascript:editTimesheet(MAIN_DETAIL_FRAME, document.SearchForm, 'edit', <%=((DaoApi) item).getColumnValue("TimesheetId")%>)">
					                  <font size="2">
		   		                        <beanlib:ElementValue dataSource="item" property="DisplayValue"/>
				                      </font>
				                   </a>
				                </gen:WhenElse>
				              </gen:Evaluate>	
				            </gen:When>
		                    <gen:WhenElse> 
			                  <a href="javascript:editTimesheet(MAIN_DETAIL_FRAME, document.SearchForm, 'edit', <%=((DaoApi) item).getColumnValue("TimesheetId")%>)">
			                    <font size="2">
   		                          <beanlib:ElementValue dataSource="item" property="DisplayValue"/>
		                        </font>
		                      </a>
		                    </gen:WhenElse>				            		        
				        </gen:Evaluate>			        
			        </td>
			        <td>
			            <font size="2">
			               <beanlib:ElementValue dataSource="item" property="ClientName"/>
			            </font>
			        </td>
			     </tr>
			   </beanlib:LoopRows>
			   <% if (pageContext.getAttribute("ROW") == null) {
				     out.println("<tr><td colspan=10 align=center>Data Not Found</td></tr>");
				  }
			   %>

            </table>
        </div>
        <br>

		<input name="clientAction" type="hidden">
		<input name="Id" type="hidden"> 
		<input name=<%=ProjectConst.CLIENT_DATA_MODE%> type="hidden" value=<%=mode%>> 
     </form>
     
    <gen:Evaluate expression="<%=mode%>">
	   <gen:When expression="<%=ProjectConst.TIMESHEET_MODE_EMPLOYEE%>">
		 <a href="javascript:editTimesheet(MAIN_DETAIL_FRAME, document.SearchForm, 'add', '0')">
			<font size="3">Add a New Timesheet</font>
	     </a>
	   </gen:When>
	   <gen:WhenElse>
          <a href="javascript:handleAction(MAIN_DETAIL_FRAME, document.SearchForm, '<%=ProjectConst.REQ_BULKINVOICE%>')">
	        <img src="<%=APP_ROOT%>/images/client_timesheet_invoice.gif" alt="Bulk Client Timesheet invoicing" style="border:none">
 	      </a>
	   </gen:WhenElse>
	</gen:Evaluate>					    
 </body>
</html>
