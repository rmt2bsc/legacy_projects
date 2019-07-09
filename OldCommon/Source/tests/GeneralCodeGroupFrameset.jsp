<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<frameset rows="*,80" frameborder="yes" framespacing="4" onLoad="setButtonText()">
  <frame name="ListFrame" scrolling="auto" src="<%=APP_ROOT%>/forms/codes/GeneralCodesGroupMaint.jsp">
  <frame name="ActionFrame" scrolling="NO" noresize src="<%=APP_ROOT%>/forms/codes/GeneralCodeGroupFooter.jsp">
</frameset>
<noframes><body bgcolor="#FFFFFF" text="#000000">

</body></noframes>
</html>
