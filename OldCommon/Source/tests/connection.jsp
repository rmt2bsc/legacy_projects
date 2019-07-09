<%@ taglib uri="/rmt2-taglib" prefix="db" %>

<html>
  <title>Testing Dklakou Tag</title>
  <body>
     Executing your second custom tag...<br>
     <db:connection id="con" classId="com.bean.RMT2DBConnectionBean"/>
     <% out.println("This shit really does works!"); %>
  </body>

</html>
  