<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ page import="com.bean.criteria.TimesheetCriteria" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.project.ProjectConst" %>
<%@ page import="com.api.DaoApi" %>


<gen:InitAppRoot id="APP_ROOT"/>

 <%
    String pageTitle = "Timesheet List Console";
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
    
<html>
  <head>
    	<title><%=pageTitle %></title>
    	<meta http-equiv="Pragma" content="no-cache">
    	<meta http-equiv="Expires" content="-1">
    	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
		<script type='text/javascript' language='javascript' src="<%=APP_ROOT%>/js/datetimepicker.js"></script>   
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/Timesheet.js"></script>
  </head>

  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

     <!--  Begin Search Criteria section -->
     <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/Timesheet.Search">
        <font size="4" style="color:blue">Selection Criteria</font>
        <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:60%; height:55px">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
             <tr>
							   <td width="10%" class="clsTableFormHeader">Status</td>
							   <td width="15%">
										 <beanlib:InputControl dataSource="<%=ProjectConst.CLIENT_DATA_STATUSES%>"
																				   type="select"
																				   name="qry_TimesheetStatusId"
																				   codeProperty="TimesheetStatusId"
																				   displayProperty="Name"
						                               selectedValue="#QUERY_BEAN.CustomObj.qry_TimesheetStatusId"/>								  
							   </td>
							   <td width="10%" class="clsTableFormHeader">Employee</td>
							   <td width="15%">
										 <beanlib:InputControl dataSource="<%=ProjectConst.CLIENT_DATA_EMPLOYEES%>"
																				   type="select"
																				   name="qry_EmpId"
																				   codeProperty="EmployeeId"
																				   displayProperty="Shortname"
																				   selectedValue="#QUERY_BEAN.CustomObj.qry_EmpId"/>
							   </td>
							   <td width="10%" class="clsTableFormHeader">Client</td>
							   <td width="20%">
										 <beanlib:InputControl dataSource="<%=ProjectConst.CLIENT_DATA_CLIENTS%>"
																				   type="select"
																				   name="qry_ClientId"
																				   codeProperty="ClientId"
																				   displayProperty="Name"
																				   selectedValue="#QUERY_BEAN.CustomObj.qry_ClientId"/>
							   </td>							   
             </tr>
						 <tr>
							   <td class="clsTableFormHeader">Start Date</td>
							   <td>
							       <beanlib:InputControl type="text" name="qry_EndPeriod1" size="10" value="#QUERY_BEAN.CustomObj.qry_EndPeriod1"/>
								     <a href="javascript:NewCal('qry_EndPeriod1','mmddyyyy')"><img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>				       
							   </td>
							   <td class="clsTableFormHeader">End Date</td>
							   <td>
							       <beanlib:InputControl type="text" name="qry_EndPeriod2" size="10" value="#QUERY_BEAN.CustomObj.qry_EndPeriod2"/>
								     <a href="javascript:NewCal('qry_EndPeriod2','mmddyyyy')"><img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>				       
							   </td>
							   <td class="clsTableFormHeader">&nbsp;</td>
							   <td align="right">&nbsp;</td>					
						 </tr>
          </table>
        </div>
        <a href="javascript:handleAction('_self', document.SearchForm, '<%=ProjectConst.REQ_SEARCH%>')">
	        <img src="<%=APP_ROOT%>/images/element_find.gif" alt="Search for Timesheet" style="border:none">
  	    </a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:handleAction('_self', document.SearchForm, '<%=ProjectConst.REQ_BACK%>')">
	        <img src="<%=APP_ROOT%>/images/prev_page.gif" alt="Back to home page" style="border:none">
  	    </a>
        <br><br>
     <!--  Begin Search Criteria section -->

        <font size="4" style="color:blue">Search Results</font>
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:60%; height:480px; overflow:auto">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
				   <th width="16%" class="clsTableListHeader" style="text-align:left" valign="bottom">Employee</th>
				   <th width="8%" class="clsTableListHeader"  style="text-align:left" valign="bottom">Week Ending</th>
				   <th width="1%" class="clsTableListHeader">&nbsp;</th>
				   <th width="10" class="clsTableListHeader" style="text-align:left" valign="bottom">Status</th>
				   <th width="6%" class="clsTableListHeader" style="text-align:center" valign="bottom">Billable Hours</th>
				   <th width="10%" class="clsTableListHeader" style="text-align:center" valign="bottom">Non-Billable Hours</th>
				   <th width="12%" class="clsTableListHeader" style="text-align:left" valign="bottom">Timesheet Id</th>
				   <th width="5%" class="clsTableListHeader">&nbsp;</th>
				   <th width="10%" class="clsTableListHeader" style="text-align:left" valign="bottom">Invoice Ref</th>
				   <th width="22%" class="clsTableListHeader" style="text-align:left" valign="bottom">Client</th>
			   </tr>

   			   <beanlib:LoopRows bean="item" list="<%=ProjectConst.CLIENT_DATA_TIMESHEETS%>">
			     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
			        <td>
			            <font size="2">
			               <beanlib:InputControl value="#item.LastFirstName"/>
			            </font>
			        </td>
			        <td>
			           <font size="2">
			              <beanlib:InputControl value="#item.EndPeriod" format="MM-dd-yyyy"/>
			           </font>
			        </td>
			        <td align="right">&nbsp;</td>
			        <td>
			           <font size="2">
  			              <beanlib:InputControl value="#item.StatusName"/>
  			           </font>
			        </td>
			        <td align="center">
			            <font color="blue" size="2">
			               <beanlib:InputControl value="#item.BillHrs" format="#,##0.00;($#,##0.00)"/>
			            </font>
			        </td>
			        <td align="center">
			            <font color="blue" size="2">
			               <beanlib:InputControl value="#item.NonBillHrs"  format="#,##0.00;($#,##0.00)"/>
			            </font>
			        </td>
			        <td>
				        <gen:Evaluate expression="#item.TimesheetStatusId"> 
				           <gen:When expression="<%=ProjectConst.TIMESHEET_STATUS_DRAFT%>">
				              <gen:Evaluate expression="#timesheetmode">
				                 <gen:When expression="<%=ProjectConst.TIMESHEET_MODE_MANAGER%>">
							               <font size="2">
    	                             <beanlib:InputControl value="#item.DisplayValue"/>
			                       </font>
						             </gen:When>
				                 <gen:WhenElse> 
					                  <a href="javascript:editTimesheet('_self', document.SearchForm, 'edit',<beanlib:InputControl value='#item.TimesheetId'/>)">
					                    <font size="2">
		   		                        <beanlib:InputControl value="#item.DisplayValue"/>
				                      </font>
				                   </a>
				                </gen:WhenElse>
				              </gen:Evaluate>	
				            </gen:When>
		                <gen:WhenElse> 
			                  <a href="javascript:editTimesheet('_self', document.SearchForm, 'edit', <beanlib:InputControl value='#item.TimesheetId'/>)">
			                    <font size="2">
   		                          <beanlib:InputControl value="#item.DisplayValue"/>
		                        </font>
		                      </a>
		                    </gen:WhenElse>				            		        
				        </gen:Evaluate>			        
			        </td>
			        <td align="center">
				        <gen:Evaluate expression="#item.DocumentId">
					   			<gen:When expression="0">&nbsp;</gen:When>
					   			<gen:WhenElse>
					     				<a href="<%=APP_ROOT%>/DocumentViewer.jsp?contentId=<beanlib:InputControl value='#item.DocumentId'/>" target="_blank"><img src="<%=APP_ROOT%>/images/camera2.png" style="border: none" alt="View signed client timesheet"/></a> 
								  </gen:WhenElse>
			    			</gen:Evaluate>
			        </td>
			        <td align="center">
			            <font size="2">
			               <beanlib:InputControl value="#item.InvoiceRefNo"/>
			            </font>
			        </td>
			        <td>
			            <font size="2">
			               <beanlib:InputControl value="#item.ClientName"/>
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
        <img src="<%=APP_ROOT%>/images/camera2.png" style="border: none"/><font color="black" size="1">Availability of signed client timesheet</font>
        <br>

		<input name="clientAction" type="hidden">
		<input name="Id" type="hidden"> 
		<beanlib:InputControl name="<%=ProjectConst.CLIENT_DATA_MODE%>" type="hidden" value="#timesheetmode"/> 
     </form>
     
    <gen:Evaluate expression="#timesheetmode">
	   <gen:When expression="<%=ProjectConst.TIMESHEET_MODE_EMPLOYEE%>">
		      <a href="javascript:handleAction('_self', document.SearchForm, 'add')">
			       <font size="3">Add a New Timesheet</font>
	        </a>
	   </gen:When>
	   <gen:WhenElse>
          <a href="javascript:handleAction('_self', document.SearchForm, '<%=ProjectConst.REQ_BULKINVOICE%>')">   
	        <img src="<%=APP_ROOT%>/images/client_timesheet_invoice.gif" alt="Bulk Client Timesheet invoicing" style="border:none">
 	      </a>
	   </gen:WhenElse>
	</gen:Evaluate>					    
 </body>
</html>
