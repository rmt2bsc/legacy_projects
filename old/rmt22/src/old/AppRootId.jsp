<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.api.security.authentication.RMT2SessionBean" %>

<%
RMT2SessionBean SESSION_BEAN = (RMT2SessionBean) session.getAttribute("SESSION_BEAN");
String APP_ROOT = null;
if (request == null) {
  APP_ROOT = "/";
}
else {
  APP_ROOT = RMT2Utility.getWebAppContext(request);
}
%>