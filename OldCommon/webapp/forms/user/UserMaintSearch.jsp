<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <title>User Maintenance</title>
  <head>

  </head>
  <script>
    function handleAction(action) {
      this.UserMaintForm.clientAction.value = action;
      this.UserMaintForm.target = "ListFrame";
      this.UserMaintForm.submit();
    }
  </script>
  <body bgcolor="#FFFFCC">
     <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
     <db:datasource id="employee_type_dso" 
                            classId="com.api.DataSourceApi" 
                            connection="con"
							query="ProjEmployeeTypeView"
							order="description"
							type="xml"/>
       <form name="UserMaintForm" method="POST" action="<%=APP_ROOT%>/usermaintservlet">
         <input type="hidden" name="UserLocationId" value="0">
         <table width="80%" border="0" bgcolor="#FFFFCC" bordercolor="black">
						 <tr>
							 <td><IMG SRC="<%=APP_ROOT%>/images/search_man.gif" width=190 height=190 BORDER=0>
							 </td>
							 <td width="100%">
									 <table  width="100%" border="0">
										 <caption><h2>User Maintenance Search Criteria</h2></caption>
											 <tr>
												 <th align="right"><strong>Login ID:</strong></th>
												 <td width="10%">
													 <input type="text" name="loginId" tabindex="1">
												 </td>
												 <th align="right" width="10%"><strong>Start Date:</strong></th>
												 <td width="8%">
														<input type="text" name="startdate" size="15" tabindex="6" >
												 </td>
											 </tr>
											 <tr>
												 <th width="10%" align="right"><strong>First Name:</strong></th>
												 <td width="15%">
														<input type="text" name="firstname" size="10" tabindex="2">
												 </td>
												 <th align="right" width="10%"><strong>Employee Type:</strong></th>
												 <td width="6%">
														<db:InputControl dataSource="employee_type_dso"
																										 type="select"
																										 name="typeId"
																										 codeProperty="Id"
																										 displayProperty="Description"
																										 tabIndex="5" />
												 </td>
											 </tr>
											 <tr>
												 <th width="10%" align="right"><strong>Last Name:</strong></th>
												 <td width="20%">
													 <input type="text" name="lastname" size="20" tabindex="3">
												 </td>
												 <td width="30%" colspan="2" align="right">
													 <input name="search" type="button" value="Search" style=width:90 onClick="handleAction(this.name)">
													 <input name="reset" type="reset" value="Clear" style=width:90>
												 </td>
											 </tr>
										 </table>
							 </td>
						 </tr>
         </table>
         <input name="clientAction" type="hidden">
       </form>

  </body>
</html>
