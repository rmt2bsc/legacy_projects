<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-securitytaglib" prefix="auth" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.api.security.authentication.AuthenticationConst" %>


<html>
<gen:InitAppRoot id="APP_ROOT"/>
<%
   String pageTitle = "Contacts Security";
%>
	<head>
		<title>Contacts Login</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	</head>
	
	<body bgcolor="#FFFFFF" text="#000000">
		<table width="99%" border="0">
		  <tr>
		    <td colspan="2" height="51">
		      <div align="center"><b><font size="7" color="#0000FF"><%=pageTitle%></font></b></div>
		    </td>
		  </tr>
		  <tr>
		    <td width="37%">
		      <div align="right"><img src="<%=APP_ROOT%>/images/lockkey01.gif" width="74" height="75"></div>
		    </td>
		    <td width="63%"><b><font size="6"> User Authentication</font></b></td>
		  </tr>
		</table>
		
		<table align="center">
				<tr>
				  <td colspan="3">
				     <font color="red" style="font-weight: bold">
					     <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
					   </font>
				  </td>
				</tr>
		</table>
		  
		<h1 align="center">&nbsp;</h1>
		<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/Security.Authentication">
		  <table width="25%" align="center" border="3" bordercolor="#0000FF">
		    <tr>
		      <td bordercolor="#0000FF">
		        <table width="100%" border="0" name="tblLogin" align="center" height="88">
		          <tr>
		            <td width="39%">
		              <div align="right"><font color="#0000FF"><b><font color="#333333">User
		                ID:</font></b></font></div>
		            </td>
		            <td width="61%"> <font color="#0000FF">
		              <input type="text" name="<%=AuthenticationConst.AUTH_PROP_USERID %>" size="20" maxlength="10">
		              </font></td>
		          </tr>
		          <tr>
		            <td width="39%">
		              <div align="right"><font color="#0000FF"><b><font color="#333333">Password:</font></b></font></div>
		            </td>
		            <td width="61%"> <font color="#0000FF">
		              <input type="password" name="<%=AuthenticationConst.AUTH_PROP_PASSWORD %>" maxlength="20" size="20">
		              </font></td>
		          </tr>
		          <tr>
		            <td  align="center" colspan="2">
		              <div align="right"> <font color="#0000FF">
		                <input type="button" name="login" value="Login" onClick="handleAction('_top', document.DataForm, this.name)">
		                <input type="reset" name="Clear" value="Clear">
	                  </div>	                  
		            </td>
		          </tr>
		        </table>
		      </td>
		    </tr>
		  </table>
		  <auth:RolesExist roles="comadmin">
		    <p>Testing security custom tag</p>
      </auth:RolesExist>
		  <input name="clientAction" type="hidden" value="login">
		  <input name="<%=AuthenticationConst.AUTH_PROP_MAINAPP%>" type="hidden" value="contacts">
		</form>
	</body>
</html>
