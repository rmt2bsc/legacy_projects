<%@ taglib uri="/rmt2-securitytaglib" prefix="auth" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.api.security.authentication.AuthenticationConst" %>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.api.security.authentication.RMT2SessionBean" %>

<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<html>
  <head>
    <title>Security Administration</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  <h1>Security  Application Main Menu</h1> 
  <br><br>
  <table width="50%" border="1" cellpadding="0" cellspacing="0">
    <caption align="left">
       <font color="blue" size="4">
          <strong>Master Data Look Up</strong>
       </font>
    </caption>
    <tr> 
      <td>
      <table width="100%" border="0">
         <tr>
			      <td width="12%" valign="top" align="center"> 
			          <a href="/authentication/unsecureRequestProcessor/User.Search?clientAction=newsearch&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
			            <img name="m_users" src="<%=APP_ROOT%>/images/mi-users.gif"  width="48px" height="70px" style="border: none" alt="User Maintenance">
			          </a>
			      </td>
			      <td width="12%"  valign="top" align="center"> 
			          <a href="/authentication/unsecureRequestProcessor/Group.Search?clientAction=list&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
			            <img name="m_users" src="<%=APP_ROOT%>/images/mi-usergroups.gif" width="48px" height="70px" style="border: none" alt="User Group Maintenance">
			          </a>
			      </td>	  			      
			      <td width="12%"  valign="top" align="center"> 
			          <a href="/authentication/unsecureRequestProcessor/App.Search?clientAction=list&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
			            <img name="m_apps" src="<%=APP_ROOT%>/images/mi-apps.gif" width="48px" height="70px" style="border: none" alt="Application Maintenance">
			          </a>
			      </td>	  
			      <td width="12%"  valign="top" align="center"> 
			          <a href="/authentication/unsecureRequestProcessor/Roles.Search?clientAction=list&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
			            <img name="m_apps" src="<%=APP_ROOT%>/images/mi-roles.gif" width="48px" height="70px" style="border: none" alt="Security Roles Maintenance">
			          </a>
			      </td>	  			      
			      <td width="12%"  valign="top" align="center"> 
			          <a href="/authentication/unsecureRequestProcessor/ResourceType.Search?clientAction=list&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
			            <img name="m_restype" src="<%=APP_ROOT%>/images/mi-restype.gif"  width="48px" height="70px" style="border: none" alt="Resource Type Maintenance">
			          </a>
			      </td>	  			      			      
			      <td width="12%"  valign="top" align="center"> 
			          <a href="/authentication/unsecureRequestProcessor/ResourceSubtype.Search?clientAction=list&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
			            <img name="m_ressubtype" src="<%=APP_ROOT%>/images/mi-ressubtype.gif"  width="48px" height="70px" style="border: none" alt="Resource Sub-Type Maintenance">
			          </a>
			      </td>	  			      			      			      
			      <td width="12%"  valign="top" align="center"> 
			          <a href="/authentication/unsecureRequestProcessor/Resource.Search?clientAction=list&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
			            <img name="m_res" src="<%=APP_ROOT%>/images/mi-res.gif"  width="48px" height="70px" style="border: none" alt="Resource Maintenance">
			          </a>
			      </td>	  			      			      			      			      
			      <td width="12%"  valign="top" align="center"> 
			          <a href="/authentication/unsecureRequestProcessor/AppRole.Search?clientAction=new&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
			            <img name="m_res" src="<%=APP_ROOT%>/images/mi-approles.gif"  width="48px" height="70px" style="border: none" alt="Resource Maintenance">
			          </a>
			      </td>	  			      			      			 			      
			      <td width="4%"  valign="top" align="center">&nbsp;</td>	  
        </tr>

      </table>
      </td>
    </tr>  
        <tr>
			      <td width="10%" valign="top" align="left"> 
		          <a href="/authentication/unsecureRequestProcessor/Security.Authentication?clientAction=logoff&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
			            Log Off
		          </a>
			      </td>					      
			 </tr>    
			 <tr>
			      <td width="10%" valign="top" align="left"> 
		          <a href="http://localhost:8080/accounting?UID=admin" target="_blank"> 
			            Accounting App
		          </a>
			      </td>					      
			 </tr>
  </table>
  </body>
</html>
