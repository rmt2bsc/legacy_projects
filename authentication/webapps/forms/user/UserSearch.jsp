<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>User Search</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	  <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Contact.js"></script>
  </head>
 
  
  <body bgcolor="#FFFFCC">
     <font size="6" style="color:black;font-weight:bold">User Search</font>  
     <%@include file="/forms/user/UserSearchCriteria.jsp"%>
     <br>
     <%@include file="/forms/user/UserSearchList.jsp"%>
     <db:Dispose/>
  </body>
</html>
