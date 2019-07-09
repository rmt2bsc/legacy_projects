<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.bean.criteria.EmployeeCriteria" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
  String pageTitle = "Employee Project List";
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
  </head>
 
  
  <body bgcolor="#FFFFCC">
     <h3><strong><%=pageTitle%></strong></h3>  
     <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/EmployeeProjectList.List">
	     <font size="3" style="color:blue">Employee</font>
	       <table width="60%" border="0">
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
		   
		   <font size="3" style="color:blue">Select a project to view/edit details</font>
		   <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:80%; height:430px; overflow:auto">
				   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
						   <th width="2%" class="clsTableListHeader">&nbsp;</th>
						   <th width="6%" class="clsTableListHeader" valign="bottom">Emp/Proj Id</th>
						   <th width="20%" class="clsTableListHeader"  style="text-align:left" valign="bottom">Client Name</th>
						   <th width="22%" class="clsTableListHeader" style="text-align:left" valign="bottom">Project Name</th>
						   <th width="10%" class="clsTableListHeader" style="text-align:left" valign="bottom">Start Date</th>
						   <th width="10%" class="clsTableListHeader" style="text-align:left" valign="bottom">End Date</th>
						   <th width="10%" class="clsTableListHeader" style="text-align:left" valign="bottom">Pay Rate</th>
						   <th width="10" class="clsTableListHeader" valign="bottom">OT Pay Rate</th>
						   <th width="10%" class="clsTableListHeader" valign="bottom">Flat Rate</th>
					   </tr>
					   
					   <xml:LoopNodes dataSource="employeeProjects" query="//VwEmployeeProjectsView/vw_employee_projects" nodeRef="proj">
					      <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
					          <td class="clsTableListHeader">
					 			<xml:InputControl type="radio" name="SelCbx" value="#proj.emp_proj_id"/>
							  </td>	
							  <td class="clsTableListHeader">
					 			<xml:InputControl value="#proj.emp_proj_id"/>
							  </td>	
					      	  <td align="left">
								 <xml:InputControl value="#proj.client_name"/>
							  </td>
							  <td>
							    <xml:InputControl value="#proj.project_name"/>
							  </td>
							<td>
							    <xml:InputControl value="#proj.projemp_effective_date" format="MM/dd/yyyy"/>
							</td>
							<td>
							    <xml:InputControl value="#proj.projemp_end_date" format="MM/dd/yyyy"/>
							</td>
							<td>
							    <xml:InputControl value="#proj.pay_rate" format="#,##0.00"/>
							</td>
							<td>
							    <xml:InputControl value="#proj.ot_pay_rate" format="#,##0.00"/>
							</td>
							<td align="center">
								<xml:InputControl value="#proj.flat_rate" format="#,##0.00"/>
							</td>
					   </xml:LoopNodes>
					   <% 
		                  if (pageContext.getAttribute("ROW") == null) {
					         out.println("<tr><td colspan=9 align=center>Data Not Found</td></tr>");
					      }
         		       %>
			    </table>
		   </div>
	     <br>
	  
				<!-- Display command buttons -->         
				<table width="100%" cellpadding="0" cellspacing="0">
				  <tr>
						<td colspan="2">
						  <input name="<%=GeneralConst.REQ_ADD%>" type="button" value="Add" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
						  <input name="<%=GeneralConst.REQ_EDIT%>" type="button" value="Edit" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
						  <input name="<%=GeneralConst.REQ_BACK%>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
				 		</td>
					  </tr>		
			  </table>        
			  
			  
			  
			  <input name="clientAction" type="hidden">
     </form>
     
     
     <br>
  </body>
</html>
