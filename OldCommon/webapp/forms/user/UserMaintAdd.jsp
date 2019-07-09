<!-- saved from url=(0022)http://internet.e-mail -->
<%@ taglib uri="/rmt2-taglib" prefix="db" %>


<html>
  <title>User Maintenance Add</title>
  <head>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
  </head>
  <script>
    function handleAction(action) {
      this.UserMaintForm.clientAction.value = action;
      this.UserMaintForm.target = "ListFrame";
      this.UserMaintForm.submit();
    }
  </script>
  <body>
     <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
     <db:datasource id="employee_type_dso" 
                            classId="com.api.DataSourceApi" 
                            connection="con"
							query="ProjEmployeeTypeView"
							order="description"
							type="xml"/>
     <db:datasource id="employee_title_dso" 
                            classId="com.api.DataSourceApi" 
                            connection="con"
							query="ProjEmployeeTitleView"
							order="description"
							type="xml"/>										
     <db:datasource id="managersDso" 
                    classId="com.api.DataSourceApi" 
                    connection="con"
					query="VwEmployeeExtView"
					order="shortname"
					type="xml"/>														
														
       <form name="UserMaintForm" method="POST" action="usermaintservlet">
					 <input type="hidden" name="Id" value="0">
					 <input type="hidden" name="EmployeeId" value="0">
					 <input type="hidden" name="PersonId" value="0">
					 
					 <table  width="70%" border="0">
						 <caption>
						      <h2>User Maintenance Add</h2>
						 </caption>
						 <tr>
							 <th align="right" bgcolor="#FFCC00">
							     <strong>Login ID:</strong>
							 </th>
							 <td width="10%">
							      <input type="text" name="Login" tabindex=1>
							 </td>
							 
							 <th align="right" bgcolor="#FFCC00">
							     <strong>Password:</strong>
							 </th>
							 <td width="10%">
									<input type="password" name="Password" size="30" tabindex=2>
							 </td>							 
						 </tr>
						 <tr>
							 <th width="12%" align="right" bgcolor="#FFCC00">
							     <strong>First Name:</strong>
							 </th>
							 <td width="25%">
									<input type="text" name="Firstname" size="30" tabindex=3>
							 </td>
							 <th align="right" bgcolor="#FFCC00">
							      <strong>Employee Type:</strong>
							 </th>
							 <td width="6%">
									<db:InputControl dataSource="employee_type_dso" 
									                         type="select" 
									                         name="TypeId" 
									                         codeProperty="Id" 
									                         displayProperty="Description"
									                         tabIndex="4"/>
							 </td>
						 </tr>
						 <tr>
							 <th width="12%" align="right" bgcolor="#FFCC00">
							     <strong>Last Name:</strong>
							 </th>
							 <td width="25%">
								 <input type="text" name="Lastname"	size="30" tabindex=5>
							 </td>
							 <th align="right" bgcolor="#FFCC00">
							     <strong>Start Date:</strong>
							 </th>
							 <td width="8%">
									<input type="text"	 name="StartDate" size="15" tabindex=6>
							 </td>
						 </tr>
						 <tr>
							 <th align="right" bgcolor="#FFCC00">
							    <strong>Title:</strong>
							 </th>
							 <td width="12%">
									<db:InputControl dataSource="employee_title_dso"
															 type="select"
															 name="TitleId"
															 codeProperty="Id"
															 displayProperty="Description"
															 tabIndex="7"/>               
							 </td>
							 <th  width="15%" align="right" bgcolor="#FFCC00">Termination Date:</th>
							 <td width="8%">
								 <input type="text"	name="TerminationDate" size="15" tabindex=8>
							 </td>
						 </tr>
						 <tr>
							 <th width="12%" align="right" bgcolor="#FFCC00">
							     <strong>Bill Rate:</strong>
							 </th>
							 <td width="25%">
								 <input type="text" name="BillRate" size="30" tabindex=5>
							 </td>
							 <th align="right" bgcolor="#FFCC00">
							     <strong>Overtime Rate</strong>
							 </th>
							 <td width="8%">
									<input type="text"	 name="OtBillRate" size="15" tabindex=6>
							 </td>
						 </tr>						 
						 
			 <tr>
				 <th align="right" bgcolor="#FFCC00">
				    <strong>Email:</strong>
				 </th>
				 <td width="12%">
					<input type="text" name="email" size="40">               
				 </td>
				 <th  width="15%" align="right" bgcolor="#FFCC00">Manager:</th>
				 <td width="8%">
					<db:InputControl dataSource="managersDso"
									 type="select"
									 name="ManagerId"
									 codeProperty="EmployeeId"
									 displayProperty="Shortname"/>               
				 </td>
			 </tr>			 						 
						 
						 
					 </table>
					 
					 <input name="clientAction" type="hidden">
					 <br>
					 <input name="saveedit" type="button" value="Save" style=width:90 onClick="handleAction(this.name)">
					 <input name="back" type="button" value="Back" style=width:90 onClick="handleAction(this.name)">
       </form>

  </body>
</html>
