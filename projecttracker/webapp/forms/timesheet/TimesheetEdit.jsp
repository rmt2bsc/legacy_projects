<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.ProjTimesheetStatus" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.project.ProjectConst" %>
<%@ page import="com.constants.RMT2TagConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.util.RMT2Date" %>
<%@ page import="com.util.RMT2Money" %>
<%@ page import="java.util.Properties" %>
<%@ page import="com.api.DaoApi" %>


<gen:InitAppRoot id="APP_ROOT"/>
<%
  String pageTitle = "Project Timesheet Edit"; 
  double totHrs = 0;
%>

<html>
  <head>
    <title><%=pageTitle %></title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/Timesheet.js"></script>
		<script>
		
			function doPrint(frame, form, obj) {
	      	var root = "<%=APP_ROOT%>/unsecureRequestProcessor/";
	      	if (obj == "<%=ProjectConst.REQ_PRINTSUMMARY%>") {
	        	form.action = root + "Reports.TimesheetSummary";
	      	}
	      	if (obj == "<%=ProjectConst.REQ_EMAILSUMMARY%>") {
	        	form.action = root + "Reports.TimesheetSummaryEmail";
	      	}
	      	if (obj == "<%=ProjectConst.REQ_PRINT%>") {
	        	form.action = root + "Reports.TimesheetDetails";
	      	}
        	obj = "print";
	      	handleAction(frame, form, obj);
	    }
	    
	    function doBackAfterNewWindow(frame, form, obj) {
	      	var root = "<%=APP_ROOT%>/unsecureRequestProcessor/";
        	form.action = root + "Timesheet.Edit";
	      	handleAction(frame, form, obj);
	    }
	   
			function invokeProjectTaskSelection(frame, form, obj) {
		      var sFeatures;
		      var projectCriteria;
		      var url;
		      var keys;
		      var rc;
		      
		      projectCriteria = "client_id=" + DataForm.ClientId.value;
		      url = "<%=APP_ROOT%>/forms/setup/ProjectTaskSelect.jsp";   
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
		         DataForm.selectedTaskId.value = ""; 
		      }
		      else {
		         DataForm.selectedTaskId.value = keys[1]; 
		      }	      
		      
		      // Submit form
		      handleAction(frame, form, obj);
		   }
		</script>
  </head>

  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/Timesheet.Edit">
       <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:55%; height:120px">     
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr> 
						 <td width="25%" class="clsTableFormHeader">
								 <font size="3"><b>Timesheet for: </b></font>
								 <beanlib:InputControl type="hidden" name="ts_mode" value="#timesheetmode"/>
 								 <beanlib:InputControl type="hidden" name="ts_stat" value="#status.TimesheetStatusId"/>
								 timesheetmode
						 </td>
						 <td align="left" width="75%" >
							<beanlib:InputControl type="hidden" name="EmpId" value="#employees.EmployeeId"/>                                  
							<beanlib:InputControl value="#employees.Shortname"/>
						 </td>
					</tr>
					<tr>   
						 <td class="clsTableFormHeader">
							<font size="3"><b>Job Title</b></font>
						 </td>
						 <td align="left">
							<beanlib:InputControl value="#employees.EmployeeTitle"/>                                  
						 </td>
					</tr>					
					<tr> 
						 <td class="clsTableFormHeader">
							<font size="3"><b>Client</b></font>
						 </td>
						 <td align="left">
							<beanlib:InputControl type="hidden" name="ClientId" value="#clients.ClientId"/>                                  
							<beanlib:InputControl value="#clients.Name"/>                                  
						 </td>
					</tr>
					<tr> 
						 <td class="clsTableFormHeader">
							<font size="3"><b>Timesheet Id</b></font>
						 </td>
						 <td align="left">
							<beanlib:InputControl value="#timesheets.DisplayValue"/>  
							<beanlib:InputControl type="hidden" name="TimesheetId" value="#timesheets.TimesheetId"/>                                  
							<beanlib:InputControl type="hidden" name="ProjId" value="#timesheets.ProjId"/>
						 </td>
					</tr>								
					<tr> 
						 <td class="clsTableFormHeader">
							<font size="3"><b>Status</b></font>
						 </td>
						 <td align="left">
							<beanlib:InputControl value="#status.Name"/>  
						 </td>
					</tr>																
					<tr> 
						 <td class="clsTableFormHeader">
							<font size="3"><b>Period Ending</b></font>
						 </td>
						 <td align="left">
							<beanlib:InputControl value="#timesheets.EndPeriod" format="MM-dd-yyyy"/>  
							<beanlib:InputControl type="hidden" name="EndPeriod" value="#timesheets.EndPeriod"/>                                  
						 </td>
					</tr>																								
					<tr> 
						 <td class="clsTableFormHeader">
							<font size="3"><b>Invoice Ref No.</b></font>
						 </td>
						 <td align="left">
							<beanlib:InputControl value="#timesheets.InvoiceRefNo"/>  
						 </td>
					</tr>
				</table>
        </div>       
        <br>
        <h3><font color="red"><i>Enter time in tenths or quarters of hours. (ex: .1 = 6 mins, .2 = 12 mins, .25 = 15 mins, .5 = 30 mins, .75 = 45 mins).</i></font> </h3>
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:80%; height:280px; overflow:auto">
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
				           <beanlib:WrapperValue value="#wrapperItem" format="MM/dd"/>
				       </th>
				   </beanlib:LoopWrapperRows>

			    </tr>
		
			    <beanlib:LoopMasterDetail masterItemName="projtask" detailDataName="events" dataSet="<%=ProjectConst.CLIENT_DATA_TIME%>">
					<tr> 
						<td class="clsTableListHeader">
						  <gen:Evaluate expression="#status.TimesheetStatusId">
			           <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_DRAFT%>">
							       <beanlib:InputControl type="checkbox" name="RowId" value="masterrowid" masterDetailLink="yes"/>
			           </gen:When>
				     </gen:Evaluate>
						 	<beanlib:InputControl type="hidden" name="ProjectTaskId" value="#projtask.ProjectTaskId" uniqueName="yes" masterDetailLink="yes"/>
						</td>	
						<td>
						 	<beanlib:InputControl value="#projtask.ProjectName"/>
						 	<beanlib:InputControl type="hidden" name="ProjectId" value="#projtask.ProjectId" uniqueName="yes" masterDetailLink="yes"/>
						</td>	
						<td>
							<beanlib:InputControl value="#projtask.TaskName"/>
							<beanlib:InputControl type="hidden" name="TaskId" value="#projtask.TaskId" uniqueName="yes" masterDetailLink="yes"/>
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
						        <gen:Evaluate expression="#status.TimesheetStatusId">
						           <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_DRAFT%>">
										       <beanlib:InputControl type="text" name="<%=eventHoursName%>" value="#item.Hours" size="5" format="##0.00" uniqueName="yes" masterDetailLink="yes" style="text-align:center"/>
										       <beanlib:InputControl type="hidden" name="<%=eventIdName%>" value="#item.EventId" uniqueName="yes" masterDetailLink="yes"/>
						           </gen:When>
						           <gen:WhenElse> 
										       <beanlib:InputControl value="#item.Hours" format="##0.00"/>
										       <beanlib:InputControl type="hidden" name="<%=eventHoursName%>" value="#item.Hours" uniqueName="yes" masterDetailLink="yes"/>
										       <beanlib:InputControl type="hidden" name="<%=eventIdName%>" value="#item.EventId" uniqueName="yes" masterDetailLink="yes"/>
						           </gen:WhenElse>
						        </gen:Evaluate>
							</td>
				  		</beanlib:LoopRows>			
				    </tr>
				</beanlib:LoopMasterDetail>		
				<% 
					if (pageContext.getAttribute(RMT2TagConst.ROW_ID_MASTERDETAIL) == null) {
				   		out.println("<tr><td colspan=9 align=center>Data Not Found</td></tr>");
					}
				%>
    	    </table>
        </div>

        <table width="80%" border="0">
				   <tr>
              <td colspan="2" align="left">
                 <b>Total Hours for the week: &nbsp; <font color="red"><%=totHrs%></font></b>
              </td>
              <td colspan="2" align="right">
                 <gen:Evaluate expression="#timesheets.DocumentId">
								   		<gen:When expression="0">
								   		   <font color="red">Signed client timesheet is not available for view</font>
								   		</gen:When>
								 	</gen:Evaluate>      
              </td>
           </tr>  
        </table>
        <br>

        <table width="40%" border="0">
				   <caption align="left"><strong>Reference No.</strong></caption>
				   <tr>
		          <td align="left">
		            <gen:Evaluate expression="#status.TimesheetStatusId">
		               <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_DRAFT%>">
			    				    <beanlib:InputControl type="text" name="ExtRef" value="#timesheets.ExtRef"/>
					  	     </gen:When>
						       <gen:WhenElse> 
							        <beanlib:InputControl value="#timesheets.ExtRef"/>
						       </gen:WhenElse>
						    </gen:Evaluate>
		          </td>
		       </tr>           
        </table>
				<table width="80%" border="0">
				   <caption align="left"><strong>Comments</strong></caption>
				   <tr>
              <td align="left">
                 <gen:Evaluate expression="#status.TimesheetStatusId">
				           <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_DRAFT%>">
								       <beanlib:InputControl type="textarea" name="Comments" value="#timesheets.Comments" rows="7" cols="100"/>
				           </gen:When>
				           <gen:WhenElse> 
								       <beanlib:InputControl value="#timesheets.Comments"/>
				           </gen:WhenElse>
						     </gen:Evaluate>
              </td>
           </tr>           
        </table>
        <br>
        
        <!-- Display command buttons -->
        <table width="35%" cellpadding="0" cellspacing="0">
			<tr>
				<td  valign="middle">
				    <gen:Evaluate expression="#status.TimesheetStatusId">
					    <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_DRAFT%>">
								<input name="<%=ProjectConst.REQ_ADD%>" type="button" value="Add Task" style="width:90" onClick="invokeProjectTaskSelection('_self', document.DataForm, this.name)">
								<input name="<%=ProjectConst.REQ_DELETE%>" type="button" value="Delete" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
								<input name="<%=ProjectConst.REQ_SAVE%>" type="button" value="Save" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
	              <input name="<%=ProjectConst.REQ_FINALIZE%>" type="button" value="Submit" style="width:90" onClick="doSubmit('_self', document.DataForm, this.name)">							
	              <input name="<%=ProjectConst.REQ_BACK%>" type="button" value="Timesheets" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
							</gen:When>
					    <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_SUBMITTED%>">
					        <gen:Evaluate expression="#timesheetmode">
					           <gen:When expression="<%=ProjectConst.TIMESHEET_MODE_MANAGER%>">
			    							<input name="<%=ProjectConst.REQ_APPROVE%>" type="button" value="Approve" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
												<input name="<%=ProjectConst.REQ_DECLINE%>" type="button" value="Decline" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">					              
					           </gen:When>
					        </gen:Evaluate>
                  <input name="<%=ProjectConst.REQ_PRINTSUMMARY%>" type="button" value="Print Summary" style="width:100" onClick="doPrint('_blank', document.DataForm, this.name)">
                  <input name="<%=ProjectConst.REQ_EMAILSUMMARY%>" type="button" value="Email Summary" style="width:100" onClick="doPrint('_self', document.DataForm, this.name)">
	    						<input name="<%=ProjectConst.REQ_PRINT%>" type="button" value="Print Details" style="width:90" onClick="doPrint('_blank', document.DataForm, this.name)">
									<input name="<%=ProjectConst.REQ_BACK%>" type="button" value="Timesheets" style="width:90" onClick="doBackAfterNewWindow('_self', document.DataForm, this.name)">
						</gen:When>						
					  <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_APPROVED%>">
				      <gen:Evaluate expression="#timesheetmode">
	 		           <gen:When expression="<%=ProjectConst.TIMESHEET_MODE_MANAGER%>">
			    					<input name="<%=ProjectConst.REQ_INVOICE%>" type="button" value="Invoice" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
			           </gen:When>
			        </gen:Evaluate>					    
				        <input name="<%=ProjectConst.REQ_PRINTSUMMARY%>" type="button" value="Print Summary" style="width:100" onClick="doPrint('_blank', document.DataForm, this.name)">
				        <input name="<%=ProjectConst.REQ_EMAILSUMMARY%>" type="button" value="Email Summary" style="width:100" onClick="doPrint('_self', document.DataForm, this.name)">
	    					<input name="<%=ProjectConst.REQ_PRINT%>" type="button" value="Print Details" style="width:90" onClick="doPrint('_blank', document.DataForm, this.name)">
								<input name="<%=ProjectConst.REQ_BACK%>" type="button" value="Timesheets" style="width:90" onClick="doBackAfterNewWindow('_self', document.DataForm, this.name)">
						</gen:When>												
				    <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_DECLINED%>">
	    				<input name="<%=ProjectConst.REQ_PRINT%>" type="button" value="Print" style="width:90" onClick="doPrint('_blank', document.DataForm, this.name)">
							<input name="<%=ProjectConst.REQ_BACK%>" type="button" value="Timesheets" style="width:90" onClick="doBackAfterNewWindow('_self', document.DataForm, this.name)">
						</gen:When>	
				    <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_INVOICED%>">
				      <input name="<%=ProjectConst.REQ_PRINTSUMMARY%>" type="button" value="Print Summary" style="width:100" onClick="doPrint('_self', document.DataForm, this.name)">
				      <input name="<%=ProjectConst.REQ_EMAILSUMMARY%>" type="button" value="Email Summary" style="width:100" onClick="doPrint('_self', document.DataForm, this.name)">
	    				<input name="<%=ProjectConst.REQ_PRINT%>" type="button" value="Print Details" style="width:90" onClick="doPrint('_blank', document.DataForm, this.name)">
							<input name="<%=ProjectConst.REQ_BACK%>" type="button" value="Timesheets" style="width:90" onClick="doBackAfterNewWindow('_self', document.DataForm, this.name)">
						</gen:When>																								
					</gen:Evaluate>
				</td>
				<td align="left">
					<gen:Evaluate expression="#timesheets.DocumentId">
				   		<gen:When expression="0">&nbsp;</gen:When>
			   			<gen:WhenElse>
				   			 <a href="<%=APP_ROOT%>/DocumentViewer.jsp?contentId=<beanlib:InputControl value='#timesheets.DocumentId'/>" target="_blank"><img src="<%=APP_ROOT%>/images/camera3.png" style="border: none" alt="View signed client timesheet"/></a>
				 			</gen:WhenElse>
				 	</gen:Evaluate>      
				</td>
			</tr>
        </table>
        <br>
        <table>
					<tr>
					  <td colspan="3">
					     <font color="red">
						     <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
						   </font>
					  </td>
					</tr>
			  </table>
               </tr>               
            </table>
        
		<input name="clientAction" type="hidden">
		<input name="selectedProjectId" type="hidden">
		<input name="selectedTaskId" type="hidden">
		
     </form>
 </body>
</html>
