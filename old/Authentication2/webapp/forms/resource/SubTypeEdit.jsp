<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.api.security.authentication.AuthenticationConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Resource Sub-Type Edit</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>
  
   <%
  	  String pageTitle = "Security - Resource Sub-Type Edit";    
	%>
   <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
	 <db:datasource id="resTypeDso" 
                  classId="com.api.DataSourceApi" 
                  connection="con"
								  query="UserResourceTypeView"
								  order="description"
								  type="xml"/>												  
						  
  <body bgcolor="#FFFFFF" text="#000000">
       <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/ResourceSubtype.Edit">
		  <h3><strong><%=pageTitle%></strong></h3>
  		<br>
	  
		<table width="50%" border="0" cellspacing="2" cellpadding="0">
				<tr> 
					 <td width="25%" class="clsTableFormHeader">
						   <font size="3"><b>Sub-Type Id</b></font>
					 </td>
					 <td align="left" width="75%" >
					     <beanlib:InputControl type="hidden" name="RsrcSubtypeId" value="#record.RsrcSubtypeId"/>                                  
					 	   <beanlib:InputControl value="#record.RsrcSubtypeId"/>                                  
					 </td>
				</tr>
				<tr> 
					 <td class="clsTableFormHeader">
						<font size="3"><b>Name</b></font>
					 </td>
					 <td align="left">
						<beanlib:InputControl type="text" name="Name" value="#record.Name" size="60" maxLength="25"/>                                  
					 </td>
				</tr>				
				<tr> 
					 <td class="clsTableFormHeader">
						<font size="3"><b>Resource Type</b></font>
					 </td>
					 <td align="left">
							<db:InputControl dataSource="resTypeDso"
															 type="select"
															 name="RsrcTypeId"
															 codeProperty="RsrcTypeId"
															 displayProperty="Description"
															 selectedValue="#record.RsrcTypeId"/>						 
					 </td>
				</tr>								
				<tr> 
					 <td class="clsTableFormHeader">
						<font size="3"><b>Description</b></font>
					 </td>
					 <td align="left">
						<beanlib:InputControl type="text" name="Description" value="#record.Description" size="60" maxLength="25"/>                                  
					 </td>
				</tr>
		</table>
		
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
				    <a id="<%=GeneralConst.REQ_SAVE%>" href="javascript:handleAction('_self', document.DataForm, 'save')">
				       <img name="m_add" src="<%=APP_ROOT%>/images/cm-ressubtype-save.gif"  width="48px" height="70px" style="border: none" alt="Save Resource Type">
				    </a>
				</td>
				<td width="10%">
				    <a id="<%=GeneralConst.REQ_DELETE%>" href="javascript:handleAction('_self', document.DataForm, 'delete')">
				       <img name="m_delete" src="<%=APP_ROOT%>/images/cm-ressubtype-delete.gif"  width="48px" height="70px" style="border: none" alt="Delete Resource Type">
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
   <db:Dispose/>
 </body>
</html>
