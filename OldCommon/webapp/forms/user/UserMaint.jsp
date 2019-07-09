<!-- saved from url=(0022)http://internet.e-mail -->
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<jsp:useBean id="QUERY_BEAN" scope="session" class="com.bean.RMT2TagQueryBean"/>

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
  <body bgcolor="#FFFFCC">
     <table  width="100%" border="1" bgcolor="white" bordercolor="#999999">
       <caption><h2>User Maintenance List</h2></caption>
       <tr bgcolor="#FFCC00">
         <th align="center">&nbsp;</th>
         <th align="center">Last Name</th>
         <th align="center">First Name</th>
         <th align="center">Title</th>
         <th align="center">Login ID</th>
         <th align="center">Type</th>
         <th align="center">Start Date</th>
         <th align="center">Termination Date</th>
         <th align="center">Login Count</th>
         <th align="center">Update Date</th>
         <th align="center">Update User</th>
       </tr>

       <form name="UserMaintForm" method="POST" action="usermaintservlet">
        <input name="clientAction" type="hidden">
        <% if (request.getParameter("firsttime") != null) {
             out.println("</form>");
             out.println("</table>");
             out.println("</body>");
             out.println("</html>");
             return;
           }
         %>

        <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
        <db:datasource id="dso" 
                       classId="com.api.DataSourceApi" 
                       connection="con"
					   query="UserView"
					   order="lastname"
					   queryId="UserMaintSearch"/>

         <db:LoopRows dataSource="dso">
           <tr>
             <td bgcolor="#FFCC00" width="2%">
                 <db:InputControl dataSource="dso"	type="radio" name="selCbx" value="rowid"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="Id" property="Id"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="EmployeeId" property="EmployeeId"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="Description" property="Description"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="PersonId" property="PersonId"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="Email" property="Email"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="Ssn" property="Ssn"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="RaceId" property="RaceId"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="BirthDate" property="BirthDate"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="MaritalStatus" property="MaritalStatus"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="GenderId" property="GenderId"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="Title" property="Title"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="Shortname" property="Shortname"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="Generation" property="Generation"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="Maidenname" property="Maidenname"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="Midname" property="Midname"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="ManagerId" property="ManagerId"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="BillRate" property="BillRate"	uniqueName="yes"/>
                 <db:InputControl dataSource="dso"	type="hidden"	name="OtBillRate" property="OtBillRate"	uniqueName="yes"/>
             </td>
             <td width="10%">
                 <db:InputControl dataSource="dso"
										type="text"
										name="Lastname"
										property="Lastname"
										size="10"
										readOnly="yes"
										style="border:none"
										uniqueName="yes"/>
             </td>
             <td width="10%">
                 <db:InputControl dataSource="dso"
										 type="text"
										 name="Firstname"
										 property="Firstname"
										 size="10"
										 readOnly="yes"
										 style="border:none"
										 uniqueName="yes"/>
             </td>
             <td width="12%">
                  <db:InputControl dataSource="dso"
                                           type="text"
                                           name="TitleDescription"
                                           property="TitleDescription"
                                           size="40"
                                           readOnly="yes"
                                           style="border:none"
                                           uniqueName="yes"/>

                  <db:InputControl dataSource="dso"
                                           type="hidden"
                                           name="TitleId"
                                           property="TitleId"
                                           uniqueName="yes"/>                                           
             </td>
             <td width="8%">
                 <db:InputControl dataSource="dso"
										 type="text"
										 name="Login"
										 property="Login"
										 size="5"
										 readOnly="yes"
										 style="border:none"
										 uniqueName="yes"/>
             </td>
             <td width="10%">
                  <db:InputControl dataSource="dso"
                                           type="text"
                                           name="TypeDescription"
                                           property="TypeDescription"
                                           size="10"
                                           readOnly="yes"
                                           style="border:none"
                                           uniqueName="yes"/>
                 
                 <db:InputControl dataSource="dso"
                                           type="hidden"
                                           name="TypeId"
                                           property="TypeId"
                                           uniqueName="yes"/>
             </td>
             <td width="8%">
                 <db:InputControl dataSource="dso"
										 type="text"
										 name="StartDate"
										 property="StartDate"
										 size="8"
										 readOnly="yes"
										 style="border:none"
										 uniqueName="yes"/>
             </td>
             <td width="8%">
                 <db:InputControl dataSource="dso"
										 type="text"
										 name="TerminationDate"
										 property="TerminationDate"
										 size="8"
										 readOnly="yes"
										 style="border:none"
										 uniqueName="yes"/>
             </td>
             <td width="4%">
                 <db:InputControl dataSource="dso"
										 type="text"
										 name="TotalLogons"
										 property="TotalLogons"
										 size="8"
										 readOnly="yes"
										 style="border:none;text-align:center"
										 uniqueName="yes"/>
             </td>
             <td width="8%">
                 <db:InputControl dataSource="dso"
										 type="text"
										 name="Update_date"
										 property="DateUpdated"
										 size="8"
										 readOnly="yes"
										 style="border:none"
										 uniqueName="yes"/>
             </td>
             <td width="8%">
                 <db:InputControl dataSource="dso"
										 type="text"
										 name="User_id"
										 property="UserId"
										 size="8"
										 readOnly="yes"
										 style="border:none"
										 uniqueName="yes"/>
             </td>
           </tr>
         </db:LoopRows>

         <% if (pageContext.getAttribute("ROW") == null) {
              out.println("<tr><td colspan=11 align=center>Data Not Found</td></tr>");
            }
         %>
       </form>
     </table>
  </body>
</html>
