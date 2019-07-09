<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
<head>
<title>GL Account Maintenance</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<frameset rows="7%,30%,63%" frameborder="no" framespacing="1">
  <frame name="HeaderFrame" scrolling="no" src="<%=APP_ROOT%>/forms/codes/acct/AcctConsoleHeader.jsp">						
  <frameset cols="*,*" frameborder="NO" border="0" framespacing="0">
	  <frame name="ListFrame" scrolling="auto" noresize src="<%=APP_ROOT%>/forms/codes/acct/AcctTypeList.jsp">
	  <frame name="ListFrame2" src="<%=APP_ROOT%>/forms/codes/acct/AcctCatgList.jsp?firsttime=yes" scrolling="auto" noresize>
  </frameset>
  <frame name="EditFrame" scrolling="auto" src="<%=APP_ROOT%>/forms/codes/acct/AcctList.jsp?firsttime=yes">
</frameset>

</html>
