<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<html>
<head>
<title>Business Contact Search Results</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<gen:InitAppRoot id="APP_ROOT"/>

<frameset rows="90%, 10%" frameborder="yes" framespacing="1">
  <frame name="ListFrame" scrolling="YES" noresize src="<%=APP_ROOT%>/forms/contact/ContactBusinessList.jsp">
  <frame name="ActionFrame" scrolling="NO" noresize src="<%=APP_ROOT%>/forms/contact/ContactListFooter.jsp">
</frameset>
<noframes><body bgcolor="#FFFFFF" text="#000000">

</body></noframes>
</html>
