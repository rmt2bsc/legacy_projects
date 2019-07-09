<%@ taglib uri="/rmt2-securitytaglib" prefix="auth" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.api.security.authentication.AuthenticationConst" %>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.api.security.authentication.RMT2SessionBean" %>
<%@ page import="com.project.ProjectConst" %>

<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<html>
  <head>
    <title>Project Tracker</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <%
      String pageTitle = "Project and Employee Tracking Application";
    %>
			<h1><%=pageTitle%></h1> 
  	  
  	  <h2 sytle="color:blue">Main Menu</h2>
		  <br><br>    
		<table width="70%" border="1" cellpadding="0" cellspacing="0">
		    <tr> 
		      <td>
		      <table width="100%" border="0">
		      <caption align="left">Project Modules</caption>
		         <tr>
		           <td width="25%"  valign="top" align="center"> 
					          <a href="/projecttracker/unsecureRequestProcessor/EmployeeSearch.Search?clientAction=newsearch&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
					            Employees
					          </a>
					      </td>
					      <td width="10%" valign="top" align="center"> 
					          <a href="/projecttracker/unsecureRequestProcessor/TaskMaint.List?clientAction=list&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
					            Tasks
					          </a>
					      </td>
					      <td width="10%"  valign="top" align="center"> 
					          <a href="/projecttracker/unsecureRequestProcessor/ProjectMaint.List?clientAction=list&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
					            Projects
					          </a>
					      </td>	  					      					      
					      <td width="10%"  valign="top" align="center"> 
					          <a href="/projecttracker/unsecureRequestProcessor/Timesheet.Search?clientAction=newsearch&<%=SESSION_BEAN.getAuthUrlParms()%>&<%=ProjectConst.CLIENT_DATA_MODE%>=<%=ProjectConst.TIMESHEET_MODE_EMPLOYEE%>"> 
					            Employee Timesheets
					          </a>
					      </td>	  		
 					      <td width="10%"  valign="top" align="center"> 
					          <a href="/projecttracker/unsecureRequestProcessor/Timesheet.Search?clientAction=newsearch&<%=SESSION_BEAN.getAuthUrlParms()%>&<%=ProjectConst.CLIENT_DATA_MODE%>=<%=ProjectConst.TIMESHEET_MODE_MANAGER%>"> 
					            Manager Timesheets
					          </a>
					      </td>	  		
		        </tr>
		      </table>
		      </td>
		    </tr>  		    		    
        <tr>
			      <td width="10%" valign="top" align="left"> 
		          <a href="/projecttracker/unsecureRequestProcessor/Security.Authentication?clientAction=logoff&<%=SESSION_BEAN.getAuthUrlParms()%>"> 
			            Log Off
		          </a>
			      </td>					      
			 </tr>		    
		  </table>
  </body>
</html>
