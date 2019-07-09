<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
<head>
<title>Customer Search</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<%
   String requestType = request.getParameter("requestType");
   String parm = "";
   if (!requestType.equals("")) {
     parm = "?requestType=" + requestType;
   }
%>

<frameset rows="53%,40%,7%" frameborder="yes" framespacing="1">
  <frame name="SearchFrame" scrolling="auto" noresize src="<%=APP_ROOT%>/forms/accounting/GlCustomerSearch.jsp<%=parm%>">
  <frame name="ListFrame" scrolling="auto" src="<%=APP_ROOT%>/forms/accounting/GlCustomerMaintList.jsp<%=parm%>">
  <frame name="ActionFrame" scrolling="no" noresize src="<%=APP_ROOT%>/forms/accounting/GlCustomerSearchFooter.jsp">   
</frameset>
<noframes><body bgcolor="#FFFFFF" text="#000000">
</body></noframes>
</html>
