<!-- saved from url=(0022)http://internet.e-mail -->
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<jsp:useBean id="user" scope="request" class="com.bean.User"/>

<html>
  <title>User Maintenance</title>
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
		 <input type="hidden" name="Id" value="<jsp:getProperty name="user" property="id"/>">
		 <input type="hidden" name="Login" value="<jsp:getProperty name="user" property="login"/>">
		 <input type="hidden" name="Id" value="<jsp:getProperty name="user" property="id"/>">
		 <input type="hidden" name="EmployeeId" value="<jsp:getProperty name="user" property="employeeId"/>">
		 <input type="hidden" name="Description" value="<jsp:getProperty name="user" property="description"/>">
		 <input type="hidden" name="PersonId" value="<jsp:getProperty name="user" property="personId"/>">
		 <input type="hidden" name="Email" value="<jsp:getProperty name="user" property="email"/>">
		 <input type="hidden" name="Ssn" value="<jsp:getProperty name="user" property="ssn"/>">
		 <input type="hidden" name="RaceId" value="<jsp:getProperty name="user" property="raceId"/>">
		 <input type="hidden" name="BirthDate" value="<jsp:getProperty name="user" property="birthDate"/>">
		 <input type="hidden" name="MaritalStatus" value="<jsp:getProperty name="user" property="maritalStatus"/>">
		 <input type="hidden" name="GenderId" value="<jsp:getProperty name="user" property="genderId"/>">
		 <input type="hidden" name="Title" value="<jsp:getProperty name="user" property="title"/>">
		 <input type="hidden" name="Shortname" value="<jsp:getProperty name="user" property="shortname"/>">
		 <input type="hidden" name="Generation" value="<jsp:getProperty name="user" property="generation"/>">
		 <input type="hidden" name="Maidenname" value="<jsp:getProperty name="user" property="maidenname"/>">
		 <input type="hidden" name="Midname" value="<jsp:getProperty name="user" property="midname"/>">
		 
		 <table  width="70%" border="0">
			 <caption>
			      <h2>User Maintenance Edit</h2>
			 </caption>
			 <tr>
				 <th align="right" bgcolor="#FFCC00">
				     <strong>Login ID:</strong>
				 </th>
				 <td width="10%" bgcolor="lightgrey">
				      <jsp:getProperty name="user" property="login"/>
				 </td>
				 
				 <th align="right" bgcolor="#FFCC00">
				     <strong>Password:</strong>
				 </th>
				 <td width="10%">
						<input type="password" 
								name="Password" 
								value="<jsp:getProperty name="user" property="password"/>" 
								style="border:none" 
								size="30">
				 </td>							 
			 </tr>
			 <tr>
				 <th width="12%" align="right" bgcolor="#FFCC00">
				     <strong>First Name:</strong>
				 </th>
				 <td width="25%">
						<input type="text" 
								name="Firstname" 
								value="<jsp:getProperty name="user" property="firstname"/>" 
								size="30">
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
										 selectedValue="<%=String.valueOf(user.getTypeId())%>"/>
				 </td>
			 </tr>
			 <tr>
				 <th width="12%" align="right" bgcolor="#FFCC00">
				     <strong>Last Name:</strong>
				 </th>
				 <td width="25%">
					 <input type="text"
							name="Lastname"
							value="<jsp:getProperty name="user" property="lastname"/>"
							size="30">
				 </td>
				 <th align="right" bgcolor="#FFCC00">
				     <strong>Start Date:</strong>
				 </th>
				 <td width="8%">
						<input type="text"
							 name="StartDate"
							 value="<%=user.displayDate(user.getStartDate(), "MM-dd-yyyy")%>"
							 size="15">
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
										 selectedValue="<%=String.valueOf(user.getTitleId())%>"/>               
				 </td>
				 <th  width="15%" align="right" bgcolor="#FFCC00">Termination Date:</th>
				 <td width="8%">
					 <input type="text"
							name="TerminationDate"
							value="<%=user.displayDate(user.getTerminationDate(), "MM-dd-yyyy")%>"
							size="15">
				 </td>
			 </tr>
			 
			 <tr>
				 <th align="right" bgcolor="#FFCC00">
				    <strong>Bill Rate:</strong>
				 </th>
				 <td width="12%">
						<input type="text"
							name="BillRate"
							value="<%=user.getBillRate()%>"
							size="15">       
				 </td>
				 <th  width="15%" align="right" bgcolor="#FFCC00">Overtime Bill Rate:</th>
				 <td width="8%">
					 <input type="text"
							name="OtBillRate"
							value="<%=user.getOtBillRate()%>"
							size="15">
				 </td>
			 </tr>			 
			 
			 
			 <tr>
				 <th align="right" bgcolor="#FFCC00">
				    <strong>Email:</strong>
				 </th>
				 <td width="12%">
					<input type="text" name="email" size="40" value="<jsp:getProperty name="user" property="email"/>">               
				 </td>
				 <th  width="15%" align="right" bgcolor="#FFCC00">Manager:</th>
				 <td width="8%">
					<db:InputControl dataSource="managersDso"
									 type="select"
									 name="ManagerId"
									 codeProperty="EmployeeId"
									 displayProperty="Shortname"
									 selectedValue="<%=String.valueOf(user.getManagerId())%>"/>               
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
