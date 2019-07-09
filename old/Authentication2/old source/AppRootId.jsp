<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.api.security.authentication.RMT2SessionBean" %>

<%
RMT2SessionBean SESSION_BEAN = (RMT2SessionBean) request.getSession().getAttribute("SESSION_BEAN");
String APP_ROOT = null;
if (request == null) {
  System.out.println("request is null");
  APP_ROOT = "request is null";
}
else {
  APP_ROOT = RMT2Utility.getWebAppContext(request);
  //APP_ROOT = "/common";
}
%>