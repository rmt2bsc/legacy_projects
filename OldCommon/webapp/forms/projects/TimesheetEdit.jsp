<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.ProjTimesheetStatus" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.ProjectConst" %>
<%@ page import="com.constants.RMT2TagConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.util.RMT2Date" %>
<%@ page import="com.util.RMT2Money" %>
<%@ page import="java.util.Properties" %>
<%@ page import="com.api.DaoApi" %>


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
	<script>
	   function invokeProjectTaskSelection(frame, form, obj) {
	      var sFeatures;
	      var projectCriteria;
	      var url;
	      var keys;
	      var rc;
	      
	      projectCriteria = "proj_client_id = " + DataForm.ProjClientId.value;
	      url = "<%=APP_ROOT%>/forms/projects/ProjectTaskSelect.jsp";
	      url += "?" + projectCriteria;
	      sFeatures = "dialogHeight:550px;status:no";
	      rc = window.showModalDialog(url, "", sFeatures);
	      keys = rc.split("~");

          if (keys == "null") {
             return;
          }
          
	      if (keys[0] == "" || keys[0] == "undefined") {
	         DataForm.selectedProjectId.value = ""; 
	      }
	      else {
	         DataForm.selectedProjectId.value = keys[0]; 
	      }
	      if (keys[1] == "" || keys[1] == "undefined") {
	         DataForm.selectedProjectId.value = ""; 
	      }
	      else {
	         DataForm.selectedTaskId.value = keys[1]; 
	      }	      
	      
	      // Submit form
	      handleAction(frame, form, obj);
	   }
	   
	   function doOldSearch(frame, form, obj) {
	      var root = "<%=APP_ROOT%>/reqeustProcessor/";
	      form.action= root + "Project.TimesheetList";
	      handleAction(frame, form, obj);
	   }
	   function doPrint(frame, form, obj) {
	      var root = "<%=APP_ROOT%>/reqeustProcessor/";
	      if (obj == "<%=ProjectConst.REQ_PRINTTIMESHEET%>") {
	        form.action = root + "Project.EmployeeTimesheetReport";
	      }
	      if (obj == "<%=ProjectConst.REQ_PRINT%>") {
	        form.action = root + "Project.TimesheetDetailsReport";
	      }
        obj = "print";
	      handleAction(frame, form, obj);
	   }
	   
	   function doSubmit(frame, form, obj) {
	      returnValue = confirm("Timeheet will no longer be editable once submitted.  Are you sure?");
	      if (returnValue == false) {
	         return;
	      }
	      handleAction(frame, form, obj);
	   }	   
	</script>
  </head>

  <%
 	  Object obj = request.getAttribute(ProjectConst.CLIENT_DATA_STATUSES);
		ProjTimesheetStatus pts = (obj == null ? new ProjTimesheetStatus() : (ProjTimesheetStatus) obj);
	  String pageTitle = "Timesheet Maintenance";
	  String billableText = null;
	  Properties prop = (Properties) request.getAttribute("messages");
	  String message = (prop == null ? "" : (prop.getProperty("INFO") == null ? "" : prop.getProperty("INFO")));
	  double totHrs = 0;
	  RMT2TagQueryBean querySession = (RMT2TagQueryBean) session.getAttribute(RMT2ServletConst.QUERY_BEAN);
	  String mode = (String) querySession.getKeyValues(ProjectConst.CLIENT_DATA_MODE);
  %>

  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/Project.TimesheetEdit">
       <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:55%; height:120px">     
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr> 
						 <td width="25%" class="clsTableFormHeader">
								 <font size="3"><b>Timesheet for: </b></font>
						 </td>
						 <td align="left" width="75%" >
							<beanlib:InputControl dataSource="<%=ProjectConst.CLIENT_DATA_EMPLOYEES%>" type="hidden" name="ProjEmployeeId" property="EmployeeId"/>                                  
							<beanlib:ElementValue dataSource="<%=ProjectConst.CLIENT_DATA_EMPLOYEES%>" property="Shortname"/>                                  
						 </td>
					</tr>
					<tr>   
						 <td class="clsTableFormHeader">
							<font size="3"><b>Job Title</b></font>
						 </td>
						 <td align="left">
							<beanlib:ElementValue dataSource="<%=ProjectConst.CLIENT_DATA_EMPLOYEES%>" property="EmployeeTitle"/>                                  
						 </td>
					</tr>					
					<tr> 
						 <td class="clsTableFormHeader">
							<font size="3"><b>Client</b></font>
						 </td>
						 <td align="left">
							<beanlib:InputControl dataSource="<%=ProjectConst.CLIENT_DATA_CLIENTS%>" type="hidden" name="ProjClientId" property="CustomerId"/>                                  
							<beanlib:ElementValue dataSource="<%=ProjectConst.CLIENT_DATA_CLIENTS%>" property="Name"/>                                  
						 </td>
					</tr>
					<tr> 
						 <td class="clsTableFormHeader">
							<font size="3"><b>Timesheet Id</b></font>
						 </td>
						 <td align="left">
							<beanlib:ElementValue dataSource="<%=ProjectConst.CLIENT_DATA_TIMESHEETS%>" property="DisplayValue"/>  
							<beanlib:InputControl dataSource="<%=ProjectConst.CLIENT_DATA_TIMESHEETS%>" type="hidden" name="Id" property="Id"/>                                  
						 </td>
					</tr>								
					<tr> 
						 <td class="clsTableFormHeader">
							<font size="3"><b>Status</b></font>
						 </td>
						 <td align="left">
							<beanlib:ElementValue dataSource="<%=ProjectConst.CLIENT_DATA_STATUSES%>" property="Name"/>  
						 </td>
					</tr>																
					<tr> 
						 <td class="clsTableFormHeader">
							<font size="3"><b>Period Ending</b></font>
						 </td>
						 <td align="left">
							<beanlib:ElementValue dataSource="<%=ProjectConst.CLIENT_DATA_TIMESHEETS%>" property="EndPeriod" format="MM-dd-yyyy"/>  
							<beanlib:InputControl dataSource="<%=ProjectConst.CLIENT_DATA_TIMESHEETS%>" type="hidden" name="EndPeriod" property="EndPeriod"/>                                  
						 </td>
					</tr>																								
				</table>
        </div>       
        <br>
        <h3><font color="red"><i>Enter time in tenths or quarters of hours. (ex: .1 = 6 mins, .2 = 12 mins, .25 = 15 mins, .5 = 30 mins, .75 = 45 mins).</i></font> </h3>
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:80%; height:480px; overflow:auto">
	        <table width="100%" border="0" cellpadding="0" cellspacing="0">
		        <tr>
		            <th width="2%" class="clsTableListHeader" style="text-align:left">&nbsp;</th>
		            <th width="16%" class="clsTableListHeader" style="text-align:left">&nbsp;</th>
		            <th width="10%" class="clsTableListHeader" style="text-align:left">&nbsp;</th>
		            <th width="6%" class="clsTableListHeader" style="text-align:center">Sun</th>
		            <th width="6%" class="clsTableListHeader" style="text-align:center">Mon</th>
		            <th width="6%" class="clsTableListHeader" style="text-align:center">Tue</th>
		            <th width="6%" class="clsTableListHeader" style="text-align:center">Wed</th>
		            <th width="6%" class="clsTableListHeader" style="text-align:center">Thur</th>
		            <th width="6%" class="clsTableListHeader" style="text-align:center">Fri</th>
		            <th width="6%" class="clsTableListHeader" style="text-align:center">Sat</th>
		        </tr>
				<tr>
				   <th class="clsTableListHeader" style="text-align:center">&nbsp;</th>
				   <th class="clsTableListHeader" style="text-align:left">Project</th>
				   <th class="clsTableListHeader" style="text-align:left">Task</th>
				 
				   <beanlib:LoopWrapperRows bean="wrapperItem" list="<%=ProjectConst.CLIENT_DATA_DATES%>">
				       <th class="clsTableListHeader">
				           <beanlib:WrapperValue dataSource="wrapperItem" format="MM/dd"/>
				       </th>
				   </beanlib:LoopWrapperRows>

			    </tr>
		
			    <beanlib:LoopMasterDetail masterItemName="projtask" detailDataName="events" dataSet="<%=ProjectConst.CLIENT_DATA_TIME%>">
					<tr> 
						<td class="clsTableListHeader">
						 	<beanlib:InputControl dataSource="projtask" type="checkbox" name="RowId" value="masterrowid" masterDetailLink="yes"/>
						 	<beanlib:InputControl dataSource="projtask" type="hidden" name="ProjectTaskId" property="ProjectTaskId" uniqueName="yes" masterDetailLink="yes"/>
						</td>	
						<td>
						 	<beanlib:ElementValue dataSource="projtask" property="ProjectName"/>
						 	<beanlib:InputControl dataSource="projtask" type="hidden" name="ProjectId" property="ProjectId" uniqueName="yes" masterDetailLink="yes"/>
						</td>	
						<td>
							<beanlib:ElementValue dataSource="projtask" property="TaskName"/>
							<beanlib:InputControl dataSource="projtask" type="hidden" name="TaskId" property="TaskId" uniqueName="yes" masterDetailLink="yes"/>
						</td>
						
						<!-- Load the Actual Hours for the Project-Task -->
						<beanlib:LoopRows bean="item" list="events">
							<td align="center">
							    <%
							       java.util.Date dt = RMT2Date.stringToDate( ((DaoApi) item).getColumnValue("EventDate") );
							       String dow = RMT2Utility.formatDate( dt, "E" );
							       String eventHoursName = dow + "EventHours";
							       String eventIdName = dow + "EventId";
							       
							       totHrs += RMT2Money.stringToNumber(((DaoApi) item).getColumnValue("Hours"), "##0.00").doubleValue();
							    %>
						        <gen:Evaluate expression="<%=pts.getId()%>">
						           <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_DRAFT%>">
								       <beanlib:InputControl dataSource="item" type="text" name="<%=eventHoursName%>" property="Hours" size="5" format="##0.00" uniqueName="yes" masterDetailLink="yes" style="text-align:center"/>
								       <beanlib:InputControl dataSource="item" type="hidden" name="<%=eventIdName%>" property="Id" uniqueName="yes" masterDetailLink="yes"/>
						           </gen:When>
						           <gen:WhenElse> 
								       <beanlib:ElementValue dataSource="item" property="Hours" format="##0.00"/>
								       <beanlib:InputControl dataSource="item" type="hidden" name="<%=eventHoursName%>" property="Hours" uniqueName="yes" masterDetailLink="yes"/>
								       <beanlib:InputControl dataSource="item" type="hidden" name="<%=eventIdName%>" property="Id" uniqueName="yes" masterDetailLink="yes"/>
						           </gen:WhenElse>
						        </gen:Evaluate>
							</td>
				  		</beanlib:LoopRows>			
				    </tr>
				</beanlib:LoopMasterDetail>		
				<% 
					if (pageContext.getAttribute(RMT2TagConst.ROW_ID_MASTERDETAIL) == null) {
				   		out.println("<tr><td colspan=7 align=center>Data Not Found</td></tr>");
					}
				%>
    	    </table>
        </div>

        <table width="80%" border="0">
				   <tr>
              <td colspan="2" align="left">
                 <b>Total Hours for the week: &nbsp; <font color="red"><%=totHrs%></font></b>
              </td>
           </tr>           
        </table>
        <br>

        <!-- Display command buttons -->
        <table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="2">
				    <gen:Evaluate expression="<%=pts.getId()%>">
					    <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_DRAFT%>">
							<input name="<%=ProjectConst.REQ_ADD%>" type="button" value="Add Task" style="width:90" onClick="invokeProjectTaskSelection(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
							<input name="<%=ProjectConst.REQ_DELETE%>" type="button" value="Delete" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
							<input name="<%=ProjectConst.REQ_SAVE%>" type="button" value="Save" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
              <input name="<%=ProjectConst.REQ_FINALIZE%>" type="button" value="Submit" style="width:90" onClick="doSubmit(MAIN_DETAIL_FRAME, document.DataForm, this.name)">							
              <input name="<%=ProjectConst.REQ_OLDSEARCH%>" type="button" value="Timesheets" style="width:90" onClick="doOldSearch(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
						</gen:When>
					    <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_SUBMITTED%>">
					        <gen:Evaluate expression="<%=mode%>">
					           <gen:When expression="<%=ProjectConst.TIMESHEET_MODE_MANAGER%>">
			    							<input name="<%=ProjectConst.REQ_APPROVE%>" type="button" value="Approve" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
												<input name="<%=ProjectConst.REQ_DECLINE%>" type="button" value="Decline" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">					              
					           </gen:When>
					        </gen:Evaluate>
                  <input name="<%=ProjectConst.REQ_PRINTTIMESHEET%>" type="button" value="Print Summary" style="width:100" onClick="doPrint('_blank', document.DataForm, this.name)">
	    						<input name="<%=ProjectConst.REQ_PRINT%>" type="button" value="Print Details" style="width:90" onClick="doPrint('_blank', document.DataForm, this.name)">
									<input name="<%=ProjectConst.REQ_OLDSEARCH%>" type="button" value="Timesheets" style="width:90" onClick="doOldSearch(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
						</gen:When>						
					  <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_APPROVED%>">
				      <gen:Evaluate expression="<%=mode%>">
	 		           <gen:When expression="<%=ProjectConst.TIMESHEET_MODE_MANAGER%>">
			    					<input name="<%=ProjectConst.REQ_INVOICE%>" type="button" value="Invoice" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
			           </gen:When>
			        </gen:Evaluate>					    
				        <input name="<%=ProjectConst.REQ_PRINTTIMESHEET%>" type="button" value="Print Summary" style="width:100" onClick="doPrint('_blank', document.DataForm, this.name)">
	    					<input name="<%=ProjectConst.REQ_PRINT%>" type="button" value="Print Details" style="width:90" onClick="doPrint('_blank', document.DataForm, this.name)">
								<input name="<%=ProjectConst.REQ_OLDSEARCH%>" type="button" value="Timesheets" style="width:90" onClick="doOldSearch(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
						</gen:When>												
				    <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_DECLINED%>">
	    				<input name="<%=ProjectConst.REQ_PRINT%>" type="button" value="Print" style="width:90" onClick="doPrint('_blank', document.DataForm, this.name)">
							<input name="<%=ProjectConst.REQ_OLDSEARCH%>" type="button" value="Timesheets" style="width:90" onClick="doOldSearch(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
						</gen:When>	
				    <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_INVOICED%>">
				      <input name="<%=ProjectConst.REQ_PRINTTIMESHEET%>" type="button" value="Print Summary" style="width:100" onClick="doPrint(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
	    				<input name="<%=ProjectConst.REQ_PRINT%>" type="button" value="Print Details" style="width:90" onClick="doPrint('_blank', document.DataForm, this.name)">
							<input name="<%=ProjectConst.REQ_OLDSEARCH%>" type="button" value="Timesheets" style="width:90" onClick="doOldSearch(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
						</gen:When>																								
					</gen:Evaluate>
				    
				</td>
			</tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				   <font color="red">
				      <%=message%>
				   </font>
				</td>
			</tr>
        </table>
        
		<input name="clientAction" type="hidden">
		<input name="selectedProjectId" type="hidden">
		<input name="selectedTaskId" type="hidden">
		
     </form>
 </body>
</html>
