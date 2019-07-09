<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<html>
<head>
<title>GL Account Category Maintenance</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<gen:InitAppRoot id="APP_ROOT"/>

<frameset rows="7%,33%,60%" frameborder="no" framespacing="1">
	<frame name="HeaderFrame" scrolling="n" noresize src="<%=APP_ROOT%>/forms/codes/acctcatg/CatgConsoleHeader.jsp">	
  <frame name="ListFrame" scrolling="auto" noresize src="<%=APP_ROOT%>/forms/codes/acctcatg/CatgAcctTypeList.jsp">
  <frame name="EditFrame" scrolling="auto" src="<%=APP_ROOT%>/forms/codes/acctcatg/CatgList.jsp?firsttime=yes">
</frameset>
<noframes><body bgcolor="#FFFFFF" text="#000000">
</body></noframes>
</html>
