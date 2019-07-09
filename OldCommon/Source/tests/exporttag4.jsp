<%@ taglib uri="/rmt2-taglib" prefix="db" %>

<html>
  <title>Testing Dehkf Tag</title>
  <body>
     Executing your second custom tag...<br>
     <db:connection id="con" classId="com.bean.RMT2DBConnectionBean"/>
     <% out.println("Connection: " + con.getName()); %>
     <db:datasource id="dso" classId="com.api.RMT2DataSourceApi" connection="con" 
                    query="PersonalPhonenumberDataSource" 
                    where="id = 1000001" 
                    order="lastname desc"/>
     <db:datasource id="phonetype" classId="com.api.RMT2DataSourceApi" connection="con" 
                    query="PhoneTypeLookup"/>                    
     <table border=1>
       <tr>
         <th bgcolor=yellow>Personal ID</th>
         <th bgcolor=yellow>First Name</th>
         <th bgcolor=yellow>Mid Initial</th>
         <th bgcolor=yellow>Last Name</th>
         <th bgcolor=yellow>Short Name</th>
         <th bgcolor=yellow>Phone Type Id</th>
         <th bgcolor=yellow>Phone Types</th>
         <th bgcolor=yellow>Phone Number</th>
         <th bgcolor=yellow>Create Date</th>
       </tr>

       <db:LoopRows dataSource="dso">
         <tr>
           <td><db:ElementValue dataSource="dso" property="id"/></td>
           <td><db:ElementValue dataSource="dso" property="firstname"/></td>
           <td><db:ElementValue dataSource="dso" property="midname"/></td>
           <td><db:ElementValue dataSource="dso" property="lastname"/></td>
           <td><db:ElementValue dataSource="dso" property="shortname"/></td>
           <td><db:ElementValue dataSource="dso" property="phonetype_id"/></td>
           <td><db:HtmlSelectX dataSource="dso"
                               masterCodeName="phonetype_id"
                               lookupSource="phonetype"
                               lookupCodeName="id"
                               lookupDisplayName="desc"/></td>
           <td><db:ElementValue dataSource="dso" property="phone"/></td>
           <td><db:ElementValue dataSource="dso" property="createdate" dateFormat="MM/dd/yyyy"/></td>
         </tr>
       </db:LoopRows>
       <tr>
         <td colspan=8 align=center bgcolor=lightblue>This shit really does works!</td>
       </tr>
     </table>
  </body>
</html>
