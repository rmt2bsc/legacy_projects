<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<html>
<head>
<title>GL Account Category Maintenance</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<gen:InitAppRoot id="APP_ROOT"/>

<frameset rows="40%,50%,10%" frameborder="yes" framespacing="1">
  <frame name="ListFrame" scrolling="auto" noresize src="<%=APP_ROOT%>/forms/accounting/GlAcctCatgAcctTypeList.jsp">
  <frame name="EditFrame" scrolling="auto" src="<%=APP_ROOT%>/forms/accounting/GlAcctCatgMaintList.jsp?firsttime=yes">
  <frame name="ActionFrame" scrolling="NO" noresize src="<%=APP_ROOT%>/forms/accounting/GlAcctCatgMaintFooter.jsp">
</frameset>
<noframes><body bgcolor="#FFFFFF" text="#000000">
</body></noframes>
</html>
