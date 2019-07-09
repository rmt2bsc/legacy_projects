<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.UserLogin" %>

<%
  UserLogin user = request.getAttribute("user") == null ? new UserLogin() : (UserLogin) request.getAttribute("user");
%>

<db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
<db:datasource id="dsoUserGroup" 
               classId="com.api.DataSourceApi" 
               connection="con"
						   query="UserGroupView"
						   order="description"/>
					 
<table width="50%" border="0">
	 <caption align="left"><strong>User</strong></caption>
	 <tr>
		 <th width="15%" align="right">
		   <font size="2">
				<b>Login ID:</b>
			</font>
		 </th>
		 <td width="35%">
			<beanlib:InputControl type="hidden" name="Username" value="#user.Username"/>				
			<beanlib:InputControl type="hidden" name="LoginId" value="#user.LoginId"/>
			<beanlib:InputControl value="#user.Username"/>        
		 </td>
		 <th width="15%" align="right">
			<font size="2">
				<b>Group:</b>
			 </font>
		 </th>
		 <td width="35%"> 
				 <db:Lookup dataSource="" 
										masterCodeName=""
										masterCodeValue="#user.GrpId"
										name="GroupId"
										type="1"
										lookupSource="dsoUserGroup"
										lookupCodeName="GrpId"
										lookupDisplayName="Description"/>					 
		 </td>				 				 
	 </tr>
	 <tr>
		 <th align="right">
			 <font size="2">
				<b>First Name:</b>
			 </font>
		 </th>
		 <td>
			 <beanlib:InputControl value="#user.Firstname"/>  
		 </td>
		 <th align="right">
			 <font size="2">
				<b>Last Name:</b>
			 </font>
		 </th>
		 <td>
			 <beanlib:InputControl value="#user.Lastname"/>  
		 </td>
	 </tr>
</table>
