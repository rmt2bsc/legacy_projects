<%@ page import="com.bean.RMT2TagQueryBean" %>
<%
Object genericCustomQueryObj = null;
RMT2TagQueryBean baseQueryObj = (RMT2TagQueryBean) session.getAttribute("QUERY_BEAN");
if (baseQueryObj != null) {
  // Get custom query object.
  genericCustomQueryObj =  baseQueryObj.getCustomObj();
}
else {
   baseQueryObj = RMT2TagQueryBean.getInstance();
}
Object custObj = baseQueryObj.getCustomObj();
%>