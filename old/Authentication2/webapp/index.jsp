<%@ taglib uri="/rmt2-securitytaglib" prefix="auth" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.api.config.HttpSystemPropertyConfig" %>
<%@ page import="com.api.security.pool.AppPropertyPool" %>
<%@ page import="com.api.security.authentication.AuthenticationConst"%>

<gen:InitAppRoot id="APP_ROOT"/>

<%
  // Prevent JSP from caching.
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>

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
     <auth:UserResourceRouter success="/home.jsp" 
                              failure="/login.jsp" 
                              appName="<%=AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_APP_NAME)%>"
                              userName="<%=request.getParameter(AuthenticationConst.AUTH_PROP_USERID)%>"/>
  </body>
</html>
