<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
<head>
<title>GL Account Maintenance</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<frameset rows="30%,60%,10%" frameborder="yes" framespacing="1">
  <frameset cols="*,*" frameborder="NO" border="0" framespacing="0">
	  <frame name="ListFrame" scrolling="auto" noresize src="<%=APP_ROOT%>/forms/accounting/GlAcctMaintAcctTypeList.jsp">
	  <frame name="ListFrame2" src="<%=APP_ROOT%>/forms/accounting/GlAcctMaintCatgList.jsp?firsttime=yes" scrolling="auto" noresize>
  </frameset>
  <frame name="EditFrame" scrolling="auto" src="<%=APP_ROOT%>/forms/accounting/GlAcctMaintList.jsp?firsttime=yes">
  <frame name="ActionFrame" scrolling="NO" noresize src="<%=APP_ROOT%>/forms/accounting/GlAcctMaintFooter.jsp">
</frameset>
<noframes><body bgcolor="#FFFFFF" text="#000000">
</body></noframes>
</html>
