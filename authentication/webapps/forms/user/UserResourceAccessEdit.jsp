<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.bean.UserResource" %>
<%@ page import="com.api.security.authentication.AuthenticationConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>
   <%
      String rsrcTypeId = (String) request.getAttribute("typeid");
      String subTypeCriteria = (rsrcTypeId == null ? "" : "rsrc_type_id = " + rsrcTypeId);
  	  String pageTitle = "Secured User Resource Access Maintenance";    
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
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/dropdown.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/AjaxApi.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/AjaxRequestConfig.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/AjaxRenderer.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/xpath.js"></script>
    
    <script>
        function changeSubTypes() {
       		var config = new RequestConfig();
       		config.method = "POST";
       		config.resourceURL = "<%=APP_ROOT%>/dataStreamProcessor/ResourceSubtype.Search"; 
       		config.customParmHandler = setupSubTypeParms;
       		config.customResponseHandler = changeSubTypeCallback;
       		config.renderHTML = false;
       		config.asynchronous = true;
       		
       		// Make Ajax call.
       		processAjaxRequest(config);
       }    

       function setupSubTypeParms() {
          var args = "clientAction=fetchsubtypes";
          args += "&RESOURCE_TYPE_ID=" + document.DataForm.ResourceTypeId.value;
          return args;
       }

	     function changeSubTypeCallback(xmlData) {
	   		  var renderer = new AjaxRenderer(null, xmlData);
	   			var obj = document.DataForm.ResourceSubtypeId;
	   			renderer.buildSelectOptions(obj, "//RS_authentication_resource_subtype/items/rsrc_subtype_id", "//RS_authentication_resource_subtype/items/rsrc_subtype_name");
	   	 } 
    </script>  
	    
	  <script>
        function getResources() {
       		var config = new RequestConfig();
       		config.method = "POST";
       		config.resourceURL = "<%=APP_ROOT%>/dataStreamProcessor/Resource.Search"; 
       		config.customParmHandler = setupResourceParms;
       		config.customResponseHandler = resourceCallback;
       		config.renderHTML = false;
       		config.asynchronous = true;
       		
       		// Make Ajax call.
       		processAjaxRequest(config);
       }    

       function setupResourceParms() {
          var args = "clientAction=fetchresourceaccess";
          args += "&UserRsrcLoginId=" + document.DataForm.Username.value;
		 			args += "&ResourceTypeId=" + document.DataForm.ResourceTypeId.value;
		  		args += "&ResourceSubtypeId=" + document.DataForm.ResourceSubtypeId.value;
          return args;
       }

	  	 function resourceCallback(xmlData) {
	   			var renderer = new AjaxRenderer(null, xmlData);
	   			var obj1 = document.DataForm.AssignedRsrcId;
	   			renderer.buildSelectOptions(obj1, "//RS_authentication_user_resource_access/rsrc_access_profile/granted_resources/rsrc_id", "//RS_authentication_user_resource_access/rsrc_access_profile/granted_resources/rsrc_name");
	   			var obj2 = document.DataForm.RevokedRsrcId;
	   			renderer.buildSelectOptions(obj2, "//RS_authentication_user_resource_access/rsrc_access_profile/revoked_resources/rsrc_id", "//RS_authentication_user_resource_access/rsrc_access_profile/revoked_resources/rsrc_name");
	   	 } 
    </script>          
  </head>
  


  <body bgcolor="#FFFFFF" text="#000000">
       <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/UserResourceAccess.Edit">
		  <h3><strong><%=pageTitle%></strong></h3>
		  <%@include file="UserHeader.jsp"%>
  		<br>

   <db:datasource id="resTypeDso" 
                  classId="com.api.DataSourceApi" 
                  connection="con"
								  query="UserResourceTypeView"
								  order="description"
								  type="xml"/>												  
   <db:datasource id="resSubTypeDso" 
                  classId="com.api.DataSourceApi" 
                  connection="con"
								  query="UserResourceSubtypeView"
								  where="<%=subTypeCriteria%>"
								  order="name"
								  type="xml"/>												  								  
								  	  
		
		<table width="50%" border="0" cellspacing="2" cellpadding="0">
		    <caption align="left"><font color="blue">Use Resource Type and Sub-Type to filter revoked and assigned resource list</font></caption>
				<tr> 
					 <td class="clsTableFormHeader">
						<font size="3"><b>Resource Type</b></font>
					 </td>
					 <td align="left">
							<db:InputControl dataSource="resTypeDso"
															 type="select"
															 name="ResourceTypeId"
															 codeProperty="RsrcTypeId"
															 displayProperty="Description"
															 selectedValue="#typeid"
															 onChange="javascript:changeSubTypes();"/>
					 </td>
				</tr>								
				<tr> 
					 <td class="clsTableFormHeader">
						<font size="3"><b>Resource Sub-Type</b></font>
					 </td>
					 <td align="left">
							<db:InputControl dataSource="resSubTypeDso"
															 type="select"
															 name="ResourceSubtypeId"
															 codeProperty="RsrcSubtypeId"
															 displayProperty="Name"
															 selectedValue="#subtypeid"
															 onChange="javascript:getResources();"/>						 
					 </td>
				</tr>			
		</table>
        <br>		
		<table width="60%" border="0">
			<tr> 
				<td align="left" width="40%" >
				   <strong>Revoked Resources</strong>
			  </td>
				<td width="20%">&nbsp;</td>
				<td align="left" width="40%">
			     <strong>Assigned Resources</strong>
			  </td>
			</tr>
			<tr> 
				<td> 
					 <beanlib:InputControl dataSource="revokedRsrc"
																	type="select"
																	name="RevokedRsrcId"
																	codeProperty="RsrcId"
																	displayProperty="Name"
																	multiSelect="Yes"
																	size="25"
																	style="width:400"/>
				</td>
				<td width="20%" align="center" valign="middle">
				   <table width="50%" align="center" border="0">
				      <tr>
					     <td align="center">
                  <input type="button" name="assigned_cb" value="Assign =&gt;"  style="width:90px" onclick="moveSelected(DataForm.RevokedRsrcId, DataForm.AssignedRsrcId, true)">
						   </td>
					  </tr>
					  <tr><td>&nbsp;</td></tr>
					  <tr>
					     <td align="center">
						    <input type="button" name="revoke_cb" value="&lt;= Revoke" style="width:90px" onclick="moveSelected(DataForm.AssignedRsrcId, DataForm.RevokedRsrcId, true)">
						 </td>
					  </tr>
				   </table>
				</td>
				<td> 
					 <beanlib:InputControl dataSource="assignedRsrc"
																	type="select"
																	name="AssignedRsrcId"
																	codeProperty="RsrcId"
																	displayProperty="ResrcName"
																	multiSelect="Yes"
																	size="25"
																	style="width:400"/>				
				</td>
			</tr>			
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
		 
     <!-- Display command links -->
     <table width="50%" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10%">
					    <a href="javascript:selectAllOpts(document.DataForm.AssignedRsrcId); javascript:handleAction('_self', document.DataForm, 'save')">
					       <img name="m_add" src="<%=APP_ROOT%>/images/cm-res-save.gif"  width="48px" height="70px" style="border: none" alt="Save Resource Type">
					    </a>
					</td>
		      <td width="10%" valign="top" align="center"> 
	          <a  href="javascript:handleAction('_self', document.DataForm, 'back')"> 
	            <img name="m_back" src="<%=APP_ROOT%>/images/cm-back.gif" width="48px" height="70px" style="border: none" alt="Back to Resource Type List">
	          </a>
		      </td>	        				
					<td width="80%">&nbsp;</td>				
				</tr>
      </table>
	    <input name="clientAction" type="hidden">
   </form>
 </body>
 <db:Dispose/>
</html>
