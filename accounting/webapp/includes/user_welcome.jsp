<%@ page import="com.api.security.authentication.RMT2SessionBean"%>
<%@ page import="com.constants.RMT2ServletConst"%>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<gen:InitSessionBean id="SESSION_BEAN"/>

<%
   // Get User's Session bean
   String userName = sessionBean.getFirstName() + " " + sessionBean.getLastName();
   String userLogin = sessionBean.getLoginId();
%>   

<!-- Display User Welcome -->
<table width="100%" align="right" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td class="user_welcome">
	   Welcome <%= userName %>. If you are not <%= userName %> please <a href="loginservlet?action=logout&login=<%= userLogin %>" target="_blank">Log Out</a>.  <a href="usersecurityservlet?action=profile&login=<%= userLogin %>" target="_blank">Update Profile</a>
    </td>
  </tr>
</table>