<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String clientAction = request.getParameter("clientAction");
	String pollService = request.getParameter("pollService");
	pollService = request.getContextPath() + "/" + pollService;
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- Refresh page immediately by setting content to zero seconds -->
<meta http-equiv="refresh" content="1;url=<%=pollService%>?clientAction=<%=clientAction%>"> 
<title>Polling Wait Page</title>
</head>

<body bgcolor="#FFFFFF" text="#000000">
  <br><br>
  <%@include file="/includes/LoadingPageSplash.jsp"%>
</body>
</html>
