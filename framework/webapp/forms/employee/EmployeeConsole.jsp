<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
  String pageTitle = "Employee Console";
%>

<html>
  <title><%=pageTitle %></title>
  <head>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">   
    <script type='text/javascript' language='javascript' src="<%=APP_ROOT%>/js/datetimepicker.js"></script>   
  	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
  	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>   
  </head>
  <body>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/EmployeeConsole.Edit">
		 <table width="60%" border="0">
			 <caption align="left">
			      <h2><%=pageTitle %></h2>
			 </caption>
			 <xml:LoopNodes dataSource="employee" query="//VwEmployeeExtView/vw_employee_ext" nodeRef="emp">
			 <tr>
				 <th class="clsTableFormHeader" width="20%">Employee ID:</th>
				 <td width="30%">
				     <xml:InputControl type="hidden" name="EmpId" value="#emp.employee_id"/>
				     <xml:InputControl value="#emp.employee_id"/>
				 </td>
				 
				 <th class="clsTableFormHeader" width="20%">Login Name:</th>
				 <td width="30%">
				     <xml:InputControl value="#emp.login_name" size="30"/>
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
				 <th class="clsTableFormHeader">SSN:</th>
				 <td>
				     <xml:InputControl value="#emp.ssn" size="30"/>
				 </td>
			 </tr>			 
			 
			 <tr>
				 <th class="clsTableFormHeader">Start Date:</th>
				 <td>
				     <xml:InputControl value="#emp.start_date"  size="15" format="MM/dd/yyyy"/>
				 </td>
				 <th class="clsTableFormHeader" width="15%">Termination Date:</th>
				 <td>
				     <xml:InputControl type="text" name="TerminationDate" value="#emp.termination_date" format="MM-dd-yyyy" size="15"/>
				     <a href="javascript:NewCal('TerminationDate','mmddyyyy')"><img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>
				 </td>
			 </tr>
			 <tr>
				 <th class="clsTableFormHeader">Email:</th>
				 <td>
				    <xml:InputControl value="#emp.email" size="40"/>
				 </td>
				 <th class="clsTableFormHeader">Is Manager:</th>
				 <td> 
						  <gen:Evaluate expression="#emp.is_manager">
						     <gen:When expression="1">Yes</gen:When>
						     <gen:WhenElse>No</gen:WhenElse>
						  </gen:Evaluate>			 
				 </td>			
			 </tr>
			 <tr>
			   <th class="clsTableFormHeader">Manager:</th>
				 <td>
				   <xml:InputControl dataSource="lookupData"
				                     type="select" 
				                     name="ManagerId" 
				                     query="//lookup/VwEmployeeExtView/vw_employee_ext"
				                     codeProperty="employee_id"
														 displayProperty="shortname"
														 selectedValue="#emp.manager_id"/>
				 </td>
				 <th class="clsTableFormHeader">Employee Type:</th>
				 <td>
				    <xml:InputControl dataSource="lookupData"
				                     type="select" 
				                     name="EmpTypeId" 
				                     query="//lookup/ProjEmployeeTypeView/proj_employee_type"
				                     codeProperty="emp_type_id"
														 displayProperty="description"
														 selectedValue="#emp.type_id"/>
				 </td>
			 </tr>	
			   <th class="clsTableFormHeader">Employee Title:</th>
				 <td>
				   <xml:InputControl dataSource="lookupData"
				                     type="select" 
				                     name="EmpTitleId" 
				                     query="//lookup/ProjEmployeeTitleView/proj_employee_title"
				                     codeProperty="emp_title_id"
														 displayProperty="description"
														 selectedValue="#emp.title_id"/>
				 </td>
				 <th></th>
				 <td></td>		 
			 <tr>
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
		 
		 <input name="<%=GeneralConst.REQ_CLIENTACTION %>" type="hidden">
		 <br>
		 <input name="<%=GeneralConst.REQ_SAVE %>" type="button" value="Save" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
		 <input name="projects" type="button" value="Projects" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
		 <input name="<%=GeneralConst.REQ_DELETE %>" type="button" value="Delete" disabled style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
		 <input name="<%=GeneralConst.REQ_BACK %>" type="button" value="Back" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
   </form>
   <db:Dispose/>
  </body>
</html>
