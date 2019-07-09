<%@ taglib uri="/rmt2-taglib" prefix="db" %>

<html>
  <title>General Codes Group Maintenance </title>
  <head>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
  </head>
  <script>
    function handleAction2(action) {
      this.GeneralGroupForm.clientAction.value = action;
      if (action == "details") {
        this.GeneralGroupForm.target = "ListFrame";
      }
      else {
        this.GeneralGroupForm.target = "windowFrame";
      }
      this.GeneralGroupForm.submit();
    }
  </script>
  
  <script Language="JavaScript" src="/js/RMT2General.js">
  </script>

  <body bgcolor="#FFFFCC" onLoad="scrollToLastRow(tbl1)">
     <db:connection id="con" classId="com.bean.RMT2DBConnectionBean"/>
     <db:datasource id="dso" 
                           classId="com.api.DataSourceApi" 
                           connection="con"
                           query="GeneralCodesGroupView"
                           order="description"/>
                           
     <table  id="tbl1" width="70%" border="1" bgcolor="white" bordercolor="#999999">
       <caption><strong>General Code Group List</strong></caption>
       <tr bgcolor="#FFCC00">
         <th align="center">&nbsp;</th>
         <th align="center">Id</th>
         <th align="center">Group Description</th>
         <th align="center">Update Date</th>
         <th align="center">Update User</th>
       </tr>
       <form name="GeneralGroupForm" method="POST" action="gencodeservlet">
         <db:LoopRows dataSource="dso">
           <tr>
             <td bgcolor="#FFCC00" width="2%">
                 <db:InputControl dataSource="dso"	type="checkbox" name="selCbx" value="rowid"/>
             </td>
             <td width="4%">
                 <db:InputControl dataSource="dso" type="text" name="Id" property="Id" size="4" readOnly="yes" style="border:none" uniqueName="yes"/>
             </td>
             <td width="30%" align="left">
                 <db:InputControl dataSource="dso"
										 type="text"
										 name="Description"
										 property="Description"
										 size="45%"
										 readOnly="no"
										 style="border:none"
										 uniqueName="yes"
										 onChange="setRowStatus(GeneralGroupForm, this, 'Description')"/>           
				<db:InputControl dataSource="dso" type="hidden" name="rowStatus" uniqueName="yes" value="U"/>
             </td>
             <td width="20%" align="center">
                <db:ElementValue dataSource="dso" property="DateUpdated" format="MM-dd-yyyy"/>
             </td>
             <td width="20%" align="center">
                <db:ElementValue dataSource="dso" property="UserId"/>
             </td>
           </tr>
         </db:LoopRows>
         
         <%
			 String rowCountStr = null;
			 int  rowCount = 0;
			 try {
				 rowCountStr = pageContext.getAttribute("ROW").toString();
				 rowCount = new Integer(rowCountStr).intValue() + 1;
			 }
			 catch (NullPointerException e) {
				 rowCountStr = "0";
			 }
			 out.println("<tr>");
			 out.println("  <td bgcolor=#FFCC00 align=center>");
			 out.println("    <input type=checkbox name=selCbx value=" + rowCount + ">");
			 out.println("  </td>");
			 out.println("  <td width='4%'>");
			 out.println("    <input type=hidden name=Id" + rowCount + " value=0>");
			 out.println("  </td>");
			 out.println("  <td width='30%' align='left'>");
			 out.println("    <input type=\"text\" name=\"Description" + rowCount + "\" size=45%  style=\"border:none\" onChange=\"setRowStatus(GeneralGroupForm, this, 'Description')\">");
			 out.println("    <input type=hidden name=rowStatus" + rowCount + " value=\"U\">");
			 out.println("  <td width=\"20%\" align=\"center\">&nbsp;&nbsp;</td>");
   		     out.println("  <td width=\"20%\" align=\"center\">&nbsp;&nbsp;</td>");
			 out.println("</tr>");
         %>
         
         <input name="clientAction" type="hidden">
         <input name="entity" type="hidden">
       </form>
     </table>
     <% dso.close(); %>
  </body>
</html>
