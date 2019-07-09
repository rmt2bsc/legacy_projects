<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.bean.criteria.EmployeeCriteria" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
  String pageTitle = "Employee Project Add";
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
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/AjaxApi.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/AjaxRequestConfig.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/AjaxRenderer.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/xpath.js"></script>
    <script>
        function changeClient() {
       		var config = new RequestConfig();
       		config.method = "POST";
       		config.resourceURL = "<%=APP_ROOT%>/dataStreamProcessor/Services.Services"; 
       		config.customParmHandler = fetchClientProjectsParms;
       		config.customResponseHandler = clientProjectsCallback;
       		config.renderHTML = false;
       		config.asynchronous = true;

       		// Make Ajax call.
       		processAjaxRequest(config);
       }    

       function fetchClientProjectsParms() {
          var args = "clientAction=RQ_projecttracker_clinet_projects";
          //var clientId = getSelectedRadio(document.DataForm.ClientId.value);
          var clientId = document.DataForm.ClientId.value;
          args += "&ClientId=" + clientId;
          return args;
       }

	   function clientProjectsCallback(xmlData) {
	        // Get XML based on entire document
	   		var renderer = new AjaxRenderer(null, xmlData);
	   		renderer.buildSelectOptions(document.DataForm.ProjId, "//RS_projecttracker_clinet_projects/project_info/proj_id", "//RS_projecttracker_clinet_projects/project_info/description");
	   } 
    </script>   
  </head>
 
  
  <body bgcolor="#FFFFCC">
     <h3><strong><%=pageTitle%></strong></h3>  
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/EmployeeProjectAdd.Add">
	     <font size="3" style="color:blue">Employee</font>
	       <table width="40%" border="0">
			 <xml:LoopNodes dataSource="employee" query="//VwEmployeeExtView/vw_employee_ext" nodeRef="emp">
				 <tr>
					 <th class="clsTableFormHeader" width="20%">Employee ID:</th>
					 <td width="30%">
					     <xml:InputControl type="hidden" name="EmpId" value="#emp.employee_id"/>
					     <xml:InputControl value="#emp.employee_id"/>
					 </td>
				 </tr>
				 <tr>
					 <th class="clsTableFormHeader">First Name:</th>
					 <td>
					     <xml:InputControl value="#emp.firstname" size="30"/>
					 </td>
					 <th class="clsTableFormHeader">Last Name:</th>
					 <td>
					     <xml:InputControl value="#emp.lastname" size="30"/>
					 </td>
				 </tr>
				 
				 <tr>
					 <th class="clsTableFormHeader">Company Name:</th>
					 <td>
					     <xml:InputControl value="#emp.company_name" size="15"/>
					 </td>
					 <th class="clsTableFormHeader">Start Date:</th>
					 <td>
					     <xml:InputControl value="#emp.start_date"  size="15" format="MM/dd/yyyy"/>
					 </td>
				 </tr>			 
			 </xml:LoopNodes>
		   </table>
		   <br>
		   
		   <font size="3" style="color:blue">Project Information</font>
		   <table width="60%" border="0" cellpadding="0" cellspacing="0">
			   <xml:LoopNodes dataSource="employeeProject" query="//VwEmployeeProjectsView/vw_employee_projects" nodeRef="proj">
			      <tr>
			          <th class="clsTableFormHeader">Emp/Proj Id</th>
					  <td>
			 			<xml:InputControl value="#proj.emp_proj_id" value="0"/>
			 			<xml:InputControl type="hidden" name="EmpProjId" value="#proj.emp_proj_id"/>
					  </td>	
					  <th class="clsTableFormHeader">Client</th>
			      	  <td>
			      	     <xml:InputControl dataSource="clientList"
								                     type="select" 
								                     name="ClientId" 
								                     query="//ProjClients/ProjClient"
								                     codeProperty="clientId"
																		 displayProperty="name"
																		 onChange="javascript:changeClient()"/>
					  </td>
					  <th class="clsTableFormHeader">Project</th>
					  <td>
					    <select name="ProjId">
					       <option></option>
					    </select>
					  </td>
				  </tr>
				  <tr>
				    <th class="clsTableFormHeader">Effective Date</th>
					<td>
					    <xml:InputControl type="text" name="EffectiveDate" value="#proj.projemp_effective_date" format="MM/dd/yyyy"/>
					    <a href="javascript:NewCal('EffectiveDate','mmddyyyy')"><img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>
					</td>
					<th class="clsTableFormHeader">End Date</th>
					<td>
					    <xml:InputControl type="text" name="EndDate" value="#proj.projemp_end_date" format="MM/dd/yyyy"/>
					    <a href="javascript:NewCal('EndDate','mmddyyyy')"><img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>
					</td>
					<th class="clsTableFormHeader">Flat Rate</th>
					<td>
						<xml:InputControl type="text" name="FlatRate" value="#proj.flat_rate" format="#,##0.00"/>
					</td>
				  </tr>
				  <tr>
				    <th class="clsTableFormHeader">Pay Rate</th>
					<td>
					    <xml:InputControl type="text" name="HourlyRate" value="#proj.pay_rate" format="#,##0.00"/>
					</td>
					<th class="clsTableFormHeader">OT Pay Rate</th>
					<td>
					    <xml:InputControl type="text" name="HourlyOverRate" value="#proj.ot_pay_rate" format="#,##0.00"/>
					</td>
				  </tr>	
			   </xml:LoopNodes>
		    </table>
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
		  <br>
		  
			<!-- Display command buttons -->         
			<table width="100%" cellpadding="0" cellspacing="0">
			  <tr>
					<td colspan="2">
					  <input name="<%=GeneralConst.REQ_SAVE%>" type="button" value="Save" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
					  <input name="<%=GeneralConst.REQ_BACK%>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
			 		</td>
				  </tr>		
		  </table>        
		  <input name="clientAction" type="hidden">
     </form>
     
     
     <br>
  </body>
</html>
