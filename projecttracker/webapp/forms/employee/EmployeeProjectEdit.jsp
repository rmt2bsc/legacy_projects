<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.bean.criteria.EmployeeCriteria" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
  String pageTitle = "Employee Project Edit";
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
  </head>
 
  
  <body bgcolor="#FFFFCC">
     <h3><strong><%=pageTitle%></strong></h3>  
     <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/EmployeeProjectEdit.Edit">
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
		   
		   <table width="60%" border="0" cellpadding="0" cellspacing="0">
			   <xml:LoopNodes dataSource="employeeProject" query="//VwEmployeeProjectsView/vw_employee_projects" nodeRef="proj">
			      <tr>
			          <th class="clsTableFormHeader">Emp/Proj Id</th>
					  <td>
			 			<xml:InputControl value="#proj.emp_proj_id"/>
			 			<xml:InputControl type="hidden" name="EmpProjId" value="#proj.emp_proj_id"/>
					  </td>	
					  <th class="clsTableFormHeader">Client</th>
			      	  <td>
						 <xml:InputControl value="#proj.client_name"/>
					  </td>
					  <th class="clsTableFormHeader">Project</th>
					  <td>
					    <xml:InputControl value="#proj.project_name"/>
					  </td>
				  </tr>
				  <tr>
				    <th class="clsTableFormHeader">Effective Date</th>
					<td>
					    <xml:InputControl value="#proj.projemp_effective_date" format="MM/dd/yyyy"/>
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
					  <input name="<%=GeneralConst.REQ_SAVE%>" type="button" value="Save" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
					  <input name="<%=GeneralConst.REQ_BACK%>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
			 		</td>
				  </tr>		
		  </table>        
		  <input name="clientAction" type="hidden">
     </form>
     
     
     <br>
  </body>
</html>
