<%@ taglib uri="/rmt2-taglib" prefix="db" %>

<html>
  <title>Testing Dteoru Tag</title>
  <body>
     Executing your second custom tag...<br>
     <db:connection id="con" classId="com.bean.RMT2DBConnectionBean"/>
     <% out.println("Connection: " + con.getName()); %>
     <db:datasource id="dso" classId="com.api.RMT2DataSourceApi" connection="con" query="select id, firstname, midname, lastname, shortname from personal where id > 1000300" type="sql"/>
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
           <td><db:ShowValue dataSource="dso" property="id"/></td>
           <td><db:ShowValue dataSource="dso" property="firstname"/></td>
           <td><db:ShowValue dataSource="dso" property="midname"/></td>
           <td><db:ShowValue dataSource="dso" property="lastname"/></td>
           <td><db:ShowValue dataSource="dso" property="shortname"/></td>
         </tr>
       </db:LoopRows>
       <tr>
         <td colspan=5 align=center bgcolor=lightblue>This shit really does works!</td>
       </tr>
     </table>
  </body>
</html>
