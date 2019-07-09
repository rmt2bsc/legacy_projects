<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<frameset rows="40%,50%,10%" frameborder="yes" framespacing="1">
  <frame name="SearchFrame" scrolling="no" noresize src="<%=APP_ROOT%>/forms/user/UserMaintSearch.jsp">
  <frame name="ListFrame" scrolling="auto" src="<%=APP_ROOT%>/forms/user/UserMaint.jsp?firsttime=yes">
  <frame name="ActionFrame" scrolling="NO" noresize src="<%=APP_ROOT%>/forms/user/UserMaintFooter.jsp">
</frameset>
<noframes><body bgcolor="#FFFFFF" text="#000000">
</body></noframes>
</html>
