<%@ taglib uri="/rmt2-taglib" prefix="db" %>

<html>
  <title>Testing Dedf Tag</title>
  <body>
     Executing your second custom tag...<br>
     <db:connection id="con" classId="com.bean.RMT2DBConnectionBean"/>
     <% out.println("Connection: " + con.getName()); %>
     <db:datasource id="dso" classId="com.api.RMT2DataSourceApi" connection="con" 
                    query="PersonalDataSource" 
                    where="substr(firstname, 1, 1) = 'B'" 
                    order="lastname desc" 
                    type="xml"/>
     <table border=1>
       <tr>
         <th bgcolor=yellow>Personal ID</th>
         <th bgcolor=yellow>First Name</th>
         <th bgcolor=yellow>Mid Initial</th>
         <th bgcolor=yellow>Last Name</th>
         <th bgcolor=yellow>Short Name</th>
       </tr>

       <db:LoopRows dataSource="dso">
         <tr>
           <td><db:ElementValue dataSource="dso" property="Id"/></td>
           <td><db:ElementValue dataSource="dso" property="FirstName"/></td>
           <td><db:ElementValue dataSource="dso" property="MidName"/></td>
           <td><db:ElementValue dataSource="dso" property="LastName"/></td>
           <td><db:ElementValue dataSource="dso" property="ShortName"/></td>
         </tr>
       </db:LoopRows>
       <tr>
         <td colspan=5 align=center bgcolor=lightblue>This shit really does works!</td>
       </tr>
     </table>
  </body>
</html>
