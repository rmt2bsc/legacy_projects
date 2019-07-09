<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
  String pageTitle = "New Employee";
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
  	<script Language="JavaScript" src="<%=APP_ROOT%>/js/AjaxApi.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/AjaxRequestConfig.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/AjaxRenderer.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/xpath.js"></script>
    
    <script>
       function setInitPageFocus() {
    	   document.DataForm.UserProfile.focus();
       }
    </script>
    
  	<script>
        function getUserProfiles() {
       		var config = new RequestConfig();
       		config.method = "POST";
       		config.resourceURL = "<%=APP_ROOT%>/dataStreamProcessor/EmployeeSearch.Search"; 
       		config.customParmHandler = setupCall;
       		config.customResponseHandler = populateForm;
       		config.renderHTML = false;
       		config.asynchronous = true;
       		
       		// Make Ajax call.
       		processAjaxRequest(config);
       }    

       function setupCall() {
          var args = "clientAction=add";
          args += "&login_id=" + document.DataForm.UserProfile[document.DataForm.UserProfile.selectedIndex].value;
          //alert("Crtieria: " + args);
          return args;
       }

	   function populateForm(xmlData) {
	   		var renderer = new AjaxRenderer(null, xmlData);
	   		var val = null;

	   		val = renderer.getElementValue("//RS_authentication_user_profile/user_profile/user_name");
	   		document.DataForm.LoginName.value = val[0].text;

	   		val = renderer.getElementValue("//RS_authentication_user_profile/user_profile/login_id");
	   		document.DataForm.LoginId.value = val[0].text;
	   		
	   		val = renderer.getElementValue("//RS_authentication_user_profile/user_profile/first_name");
	   		document.DataForm.Firstname.value = val[0].text;
	   		
	   		val = renderer.getElementValue("//RS_authentication_user_profile/user_profile/last_name");
	   		document.DataForm.Lastname.value = val[0].text;

	   		val = renderer.getElementValue("//RS_authentication_user_profile/user_profile/ssn");
	   		document.DataForm.Ssn.value = val[0].text;

	   		val = renderer.getElementValue("//RS_authentication_user_profile/user_profile/start_date");
	   		document.DataForm.StartDate.value = val[0].text;

	   		val = renderer.getElementValue("//RS_authentication_user_profile/user_profile/email");
	   		document.DataForm.Email.value = val[0].text;
	   		
	   } 
    </script>
  </head>
  <body onload="setInitPageFocus()">
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/NewEmployee.Edit">
         
		 <table width="60%" border="0">
			 <caption align="left">
			      <h2><%=pageTitle %></h2>
			 </caption>
			 <tr>
			    <td colspan="4">
			      <table width="30%" border="1">
		            <tr>
					   <td>
					      <table  width="100%">
					        <tr>
					          <td class="clsTableFormHeader" style="text-align:left">Select a user to create an employee</td>
					        </tr>
					        <tr>
						        <td>
								   <xml:InputControl dataSource="userProfiles"
								                     type="select" 
								                     name="UserProfile" 
								                     query="//RS_authentication_user_profile/user_profile"
								                     codeProperty="login_id"
																		 displayProperty="short_name"
																		 onChange="javascript:getUserProfiles()"
													 					 tabIndex="1"/>
							 	</td>
						 	</tr>
					      </table>
					   </td>
					</tr>
		         </table>
			    </td>
			 </tr>
			 <tr>
			    <td colspan="4">&nbsp;</td>
 	         </tr>
			 <xml:LoopNodes dataSource="employee" query="//VwEmployeeExtView/vw_employee_ext" nodeRef="emp">
			 <tr>
				 <th class="clsTableFormHeader" width="20%">Employee ID:</th>
				 <td width="30%">
				     <xml:InputControl type="text" name="EmpId" value="#emp.employee_id" readOnly="true" style="background-color:silver"/>
				 </td>
				 
				 <th class="clsTableFormHeader" width="20%">Login Name:</th>
				 <td width="30%">
				     <xml:InputControl  type="text" name="LoginName" value="#emp.login_name" size="30" readOnly="true" style="background-color:silver" />
				     <xml:InputControl  type="hidden" name="LoginId"/>
				 </td>							 
			 </tr>
			 <tr>
				 <th class="clsTableFormHeader">First Name:</th>
				 <td>
				     <xml:InputControl type="text" name="Firstname" value="#emp.firstname" size="30" readOnly="true" style="background-color:silver"/>
				 </td>
				 <th class="clsTableFormHeader">Last Name:</th>
				 <td>
				     <xml:InputControl type="text" name="Lastname" value="#emp.lastname" size="30" readOnly="true" style="background-color:silver"/>
				 </td>
			 </tr>
			 
			 <tr>
				 <th class="clsTableFormHeader">Company Name:</th>
				 <td>
				     <xml:InputControl type="text" name="CompanyName" value="#emp.company_name" size="40" tabIndex="2"/>
				 </td>
				 <th class="clsTableFormHeader">SSN:</th>
				 <td>
				     <xml:InputControl type="text" name="Ssn" value="#emp.ssn" size="30" readOnly="true" style="background-color:silver"/>
				 </td>
			 </tr>			 
			 
			 <tr>
				 <th class="clsTableFormHeader">Start Date:</th>
				 <td>
				     <xml:InputControl type="text" name="StartDate" value="#emp.start_date"  size="15" readOnly="true" style="background-color:silver"/>
				 </td>
				 <th class="clsTableFormHeader" width="15%">Termination Date:</th>
				 <td>
				     <xml:InputControl type="text" name="TerminationDate" value="#emp.termination_date" format="MM-dd-yyyy" size="15" tabIndex="3"/>
				     <a href="javascript:NewCal('TerminationDate','mmddyyyy')"><img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>
				 </td>
			 </tr>
			 <tr>
				 <th class="clsTableFormHeader">Email:</th>
				 <td>
				    <xml:InputControl type="text" name="Email" value="#emp.email" size="40" readOnly="true" style="background-color:silver"/>
				 </td>
				 <th class="clsTableFormHeader">Is Manager:</th>
				 <td> 
				     <select name="IsManager" tabIndex="4">
				        <option value="1">Yes</option>
				        <option value="0" selected="selected">No</option>
				     </select>
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
														 selectedValue="#emp.manager_id"
														 tabIndex="5"/>
				 </td>
				 <th class="clsTableFormHeader">Employee Type:</th>
				 <td>
				    <xml:InputControl dataSource="lookupData"
				                     type="select" 
				                     name="EmpTypeId" 
				                     query="//lookup/ProjEmployeeTypeView/proj_employee_type"
				                     codeProperty="emp_type_id"
														 displayProperty="description"
														 selectedValue="#emp.type_id"
														 tabIndex="6"/>
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
														 selectedValue="#emp.title_id"
														 tabIndex="7"/>
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
		 <input name="<%=GeneralConst.REQ_SAVE %>" type="button" value="Save" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)" tabIndex="20" >
		 <input name="<%=GeneralConst.REQ_BACK %>" type="button" value="Back" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)" tabIndex="23">
   </form>
   <db:Dispose/>
  </body>
</html>
