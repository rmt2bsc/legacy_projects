<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
<head>
<title>Transaction Search</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<%
   String jspOrigin = request.getParameter("jspOrigin");
   jspOrigin = "?jspOrigin=" + jspOrigin;
   System.out.println("JSP origin from General Transaction Search frameset: " + jspOrigin);
%>
<frameset rows="40%,53%,7%" frameborder="yes" framespacing="1">
  <frame name="SearchFrame" scrolling="auto" noresize src="<%=APP_ROOT%>/forms/xact/XactSearch.jsp<%=jspOrigin%>">
  <frame name="ListFrame" scrolling="auto" src="<%=APP_ROOT%>/forms/xact/XactList.jsp<%=jspOrigin%>">
  <frame name="ActionFrame" scrolling="no" noresize src="<%=APP_ROOT%>/forms/xact/XactSearchFooter.jsp">   
</frameset>
<noframes><body bgcolor="#FFFFFF" text="#000000">
</body></noframes>
</html>
