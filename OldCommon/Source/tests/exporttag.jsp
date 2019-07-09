<%@ taglib uri="/rmt2-taglib" prefix="db" %>

<html>
  <title>Testing Dtasour Tag</title>
  <body>
     Executing your second custom tag...<br>
     <db:connection id="con" classId="com.bean.RMT2DBConnectionBean"/>
     <% out.println("Connection: " + con.getName()); %>
     <db:datasource id="dso" classId="com.api.RMT2DataSourceApi" connection="con" query="select id, quote_id, flat_rate, hourly_rate, user_id from orders" type="sql"/>
     <table border=1>
       <tr>
         <th bgcolor=yellow>Order ID</th>
         <th bgcolor=yellow>Quote ID</th>
         <th bgcolor=yellow>Flat Rate</th>
         <th bgcolor=yellow>Hourly Rate</th>
         <th bgcolor=yellow>User Last Updated</th>
       </tr>
       
       <db:LoopRows dataSource="dso">
         <tr>
           <td><db:ShowValue dataSource="dso" property="id"/></td>
           <td><db:ShowValue dataSource="dso" property="quote_id"/></td>
           <td><db:ShowValue dataSource="dso" property="flat_rate"/></td>
           <td><db:ShowValue dataSource="dso" property="hourly_rate"/></td>
           <td><db:ShowValue dataSource="dso" property="user_id"/></td>
         </tr>
       </db:LoopRows>
       <tr>
         <td colspan=5 align=center bgcolor=lightblue>This shit really does works!</td>
       </tr>
     </table>
  </body>
</html>
  