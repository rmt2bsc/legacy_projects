<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>


<gen:InitAppRoot id="APP_ROOT"/>

<html>
<head>
<title>Creditor Search</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<%
   String requestType = request.getParameter("requestType");
   String parm = "";
   if (!requestType.equals("")) {
     parm = "?requestType=" + requestType;
   }
%>

<frameset rows="40%,53%,7%" frameborder="yes" framespacing="1">
  <frame name="SearchFrame" scrolling="auto" noresize src="<%=APP_ROOT%>/forms/accounting/GlCreditorSearch.jsp<%=parm%>">
  <frame name="ListFrame" scrolling="auto" src="<%=APP_ROOT%>/forms/accounting/GlCreditorMaintList.jsp<%=parm%>">
  <frame name="ActionFrame" scrolling="no" noresize src="<%=APP_ROOT%>/forms/accounting/GlCreditorSearchFooter.jsp">   
</frameset>
<noframes><body bgcolor="#FFFFFF" text="#000000">
</body></noframes>
</html>
