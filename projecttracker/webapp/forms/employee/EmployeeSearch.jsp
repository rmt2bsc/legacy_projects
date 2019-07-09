<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.bean.criteria.EmployeeCriteria" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
  String pageTitle = "Employee Search";
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
     <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/EmployeeSearch.Search">
	     <font size="4" style="color:blue">Selection Criteria</font>  
	     <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:60%; height:55px">
			     <br>
					 <table  width="100%" border="0">
					     <xml:LoopNodes dataSource="#QUERY_BEAN.CustomObj" query="//EmployeeCriteria" nodeRef="rec">
									 <tr>
											 <th align="right"><strong>Employee Id:</strong></th>
											 <td width="10%">
											   <xml:InputControl type="text" name="qry_EmployeeId" value="#rec.qry_EmployeeId"/>
											 </td>
											 <th align="right" width="10%"><strong>Login Name:</strong></th>
											 <td width="8%">
											    <xml:InputControl type="text" name="qry_LoginName" value="#rec.qry_LoginName"/>
											 </td>
									 </tr>
									 <tr>
											 <th width="10%" align="right"><strong>First Name:</strong></th>
											 <td width="15%">
											    <xml:InputControl type="text" name="qry_Firstname" value="#rec.qry_Firstname"/>
											 </td>
											 <th width="10%" align="right"><strong>Last Name:</strong></th>
											 <td width="20%">
											   <xml:InputControl type="text" name="qry_Lastname" value="#rec.qry_Lastname"/>
											 </td>
									 </tr>
									 <tr>
											 <th width="10%" align="right"><strong>Company</strong></th>
											 <td width="15%">
											    <xml:InputControl type="text" name="qry_Company" value="#rec.qry_Company"/>
											 </td>
											 <th width="10%" align="right"><strong>Manager:</strong></th>
											 <td width="20%">
											   <xml:InputControl dataSource="lookupData"
											                     type="select" 
											                     name="qry_ManagerId" 
											                     query="//lookup/VwEmployeeExtView/vw_employee_ext"
											                     codeProperty="employee_id"
																					 displayProperty="shortname"
																					 selectedValue="#rec.qry_ManagerId"/>
											 </td>
									 </tr>
									 <tr>
											 <th width="10%" align="right"><strong>Employee Type:</strong></th>
											 <td width="15%">
											    <xml:InputControl dataSource="lookupData"
											                     type="select" 
											                     name="qry_TypeId" 
											                     query="//lookup/ProjEmployeeTypeView/proj_employee_type"
											                     codeProperty="emp_type_id"
																					 displayProperty="description"
																					 selectedValue="#rec.qry_TypeId"/>
											 </td>
											 <th width="10%" align="right"><strong>Employee Title:</strong></th>
											 <td width="20%">
											   <xml:InputControl dataSource="lookupData"
											                     type="select" 
											                     name="qry_TitleId" 
											                     query="//lookup/ProjEmployeeTitleView/proj_employee_title"
											                     codeProperty="emp_title_id"
																					 displayProperty="description"
																					 selectedValue="#rec.qry_TitleId"/>
											 </td>
									 </tr>
									 <tr>
											 <th width="10%" align="right"><strong>Project:</strong></th>
											 <td width="15%">
											    <xml:InputControl type="text" name="qry_Project" value="#rec.qry_Project"/>
											 </td>
											 <th width="10%" align="right"><strong>SSN/EIN:</strong></th>
											 <td width="20%">
											   <xml:InputControl type="text" name="qry_Ssn" value="#rec.qry_Ssn"/>
											 </td>
									 </tr>
							 </xml:LoopNodes>
			     </table>
	     </div>       
	     <br>
	     <!-- Display command buttons -->         
				<table width="100%" cellpadding="0" cellspacing="0">
				  <tr>
						<td colspan="2">
						  <input name="<%=GeneralConst.REQ_SEARCH%>" type="button" value="Search" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
							  <input type="reset" name="clear" value="Clear" style="width:90">
				 		</td>
					  </tr>		
			  </table>        
	      <br><br>
	
	     <font size="3" style="color:blue">Search Results</font>
		   <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:75%; height:430px; overflow:auto">
				   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
						   <th width="2%" class="clsTableListHeader">&nbsp;</th>
						   <th width="10%" class="clsTableListHeader" valign="bottom">Emp Id</th>
						   <th width="18%" class="clsTableListHeader"  style="text-align:left" valign="bottom">Employee Name</th>
						   <th width="21%" class="clsTableListHeader" style="text-align:left" valign="bottom">Company Name</th>
						   <th width="10%" class="clsTableListHeader" style="text-align:left" valign="bottom">Login Name</th>
						   <th width="10%" class="clsTableListHeader" style="text-align:left" valign="bottom">Employee Type</th>
						   <th width="22%" class="clsTableListHeader" style="text-align:left" valign="bottom">Title/Role</th>
						   <th width="7%" class="clsTableListHeader" valign="bottom">Manager</th>
					   </tr>
					   
					   <xml:LoopNodes dataSource="employeeList" query="//VwEmployeeExtView/vw_employee_ext" nodeRef="emp">
					      <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
					          <td class="clsTableListHeader">
					 							<xml:InputControl type="radio" name="SelCbx" value="#emp.employee_id"/>
										</td>	
					      		<td align="center">
										    <xml:InputControl name="EmpId" value="#emp.employee_id"/>
										</td>
										<td>
										    <xml:InputControl value="#emp.shortname"/>
										</td>
										<td>
										    <xml:InputControl value="#emp.company_name"/>
										</td>
										<td>
										    <xml:InputControl value="#emp.login_name"/>
										</td>
										<td>
										    <xml:InputControl value="#emp.employee_type"/>
										</td>
										<td>
										    <xml:InputControl value="#emp.employee_title"/>
										</td>
										<td align="center">
												<gen:Evaluate expression="#emp.is_manager">
												   <gen:When expression="1">Yes</gen:When>
												   <gen:When expression="0">No</gen:When>
												   <gen:WhenElse>No</gen:WhenElse>
												</gen:Evaluate>
										</td>
					   </xml:LoopNodes>
					    <% 
		            if (pageContext.getAttribute("ROW") == null) {
					         out.println("<tr><td colspan=10 align=center>Data Not Found</td></tr>");
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
