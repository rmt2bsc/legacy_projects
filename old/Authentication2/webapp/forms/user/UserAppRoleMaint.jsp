<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.api.security.authentication.AuthenticationConst" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>User Application-Role Maintenance</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	  <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/dropdown.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/AjaxApi.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/AjaxRequestConfig.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/AjaxRenderer.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/xpath.js"></script>
    <script>
        function changeRoles() {
            
       		var config = new RequestConfig();
       		config.method = "POST";
       		config.resourceURL = "<%=APP_ROOT%>/dataStreamProcessor/UserAppRole.Edit"; 
       		config.customParmHandler = setupRoleParms;
       		config.customResponseHandler = changeRolesCallback;
       		config.renderHTML = false;
       		config.asynchronous = true;
       		
       		// Make Ajax call.
       		processAjaxRequest(config);
       }    

       function setupRoleParms() {
          var args = "clientAction=RQ_authentication_user_app_roles";
          var appId = getSelectedRadio(document.DataForm.ApplicationId);
          args += "&ApplicationId=" + appId;
          args += "&UserAppLoginId=" + document.DataForm.Username.value;
          args += "&UID=admin&appcode=authentication";
          return args;
       }

	   function changeRolesCallback(xmlData) {
	        // Get XML based on entire document
	   		var renderer = new AjaxRenderer(null, xmlData);
	   		renderer.buildSelectOptions(document.DataForm.AssignedRoleId, "//role_data/assigned_roles/app_role_id", "//role_data/assigned_roles/app_role_name");
	   		renderer.buildSelectOptions(document.DataForm.RevokedRoleId, "//role_data/revoked_roles/app_role_id", "//role_data/revoked_roles/app_role_name");
	   } 
    </script>   
  </head>

   <%
      String pageTitle = "User Application-Role Maintenance";
   %>
	
  <body bgcolor="#FFFFFF" text="#000000">
	 <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/UserAppRole.Edit">
	    <h3><strong><%=pageTitle%></strong></h3>
	    <%@include file="UserHeader.jsp"%>  
	    <br>
		<table width="40%" border="0">
		   <caption align="left"><strong>Applications</strong></caption>
			<tr> 
				<td align="right" bgcolor="#FFCC00"> 
   				   <beanlib:InputControl dataSource="apps"
																	 type="radiolookup"
																	 name="ApplicationId"
																	 codeProperty="AppId"
																	 displayProperty="Name"
																	 selectedValue="#selectedApp"
																	 cols="3"
																	 border="yes"
																	 onClick="changeRoles()"/>
				</td>
			</tr>    
		</table>
		<br>
		<table width="60%" border="0">
			<tr> 
				<td align="left" width="40%" >
				   <strong>Revoked Roles</strong>
			  </td>
				<td width="20%">&nbsp;</td>
				<td align="left" width="40%">
			     <strong>Assigned Roles</strong>
			  </td>
			</tr>
			<tr> 
				<td> 
					 <beanlib:InputControl dataSource="revokedRoles"
																	type="select"
																	name="RevokedRoleId"
																	codeProperty="AppRoleId"
																	displayProperty="AppRoleName"
																	multiSelect="Yes"
																	size="25"
																	style="width:400"/>
				</td>
				<td width="20%" align="center" valign="middle">
				   <table width="50%" align="center" border="0">
				      <tr>
					     <td align="center">
                  <input type="button" name="assigned_cb" value="Assign =&gt;"  style="width:90px" onclick="moveSelected(DataForm.RevokedRoleId, DataForm.AssignedRoleId, true)">
						   </td>
					  </tr>
					  <tr><td>&nbsp;</td></tr>
					  <tr>
					     <td align="center">
						    <input type="button" name="revoke_cb" value="&lt;= Revoke" style="width:90px" onclick="moveSelected(DataForm.AssignedRoleId, DataForm.RevokedRoleId, true)">
						 </td>
					  </tr>
				   </table>  
				</td>
				<td> 
					 <beanlib:InputControl dataSource="assignedRoles"
																type="select"
																name="AssignedRoleId"
																codeProperty="AppRoleId"
																displayProperty="AppRoleName"
																multiSelect="Yes"
																size="25"
																style="width:400"/>				
				</td>
			</tr>			
		</table>
		<br>
	  
	   <!-- Display command buttons -->
	  <table width="100%" cellpadding="0" cellspacing="0">
		  <tr>
			<td colspan="2">
			    <input name="<%=GeneralConst.REQ_SAVE%>" type="button" value="Save" style="width:90" onClick="selectAllOpts(document.DataForm.AssignedRoleId); handleAction('_self', document.DataForm, this.name)">
			    <input name="<%=GeneralConst.REQ_BACK%>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
			</td>
		 </tr>		
	  </table>
	 <input name="clientAction" type="hidden">
   </form>
   <db:Dispose/>
 </body>
</html>
