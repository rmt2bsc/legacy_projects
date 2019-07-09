<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<jsp:useBean id="SESSION_BEAN" class="com.bean.RMT2SessionBean" scope="session"/>
<jsp:useBean id="QUERY_BEAN" class="com.bean.RMT2TagQueryBean" scope="session"/>


<html>
  <title>General Codes Group Maintenance </title>
  <head>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
  </head>
  <script>
    function handleAction2(action) {
      this.GeneralCodesForm.clientAction.value = action;
      this.GeneralCodesForm.target = "windowFrame";
      this.GeneralCodesForm.submit();
    }
  </script>
  <script Language="JavaScript" src="/js/RMT2General.js">
  </script>
  
  
  <db:connection id="con" classId="com.bean.RMT2DBConnectionBean"/>
  <db:datasource id="dso" classId="com.api.DataSourceApi" connection="con" query="GeneralCodesView"	order="longdesc" queryId="GeneralCodeMaintSearch"/>
												
   <% 
       String groupCriteria = "id = " + (String) request.getParameter("group_id"); 
   %>												
  <db:datasource id="dso2" 
                        classId="com.api.DataSourceApi" connection="con"
						query="GeneralCodesGroupView"
						where="<%=groupCriteria%>"
						order="description"
						type="xml"/>
  
  <body bgcolor="#FFFFCC"  onLoad="scrollToLastRow(tbl1)">
     <form name="GeneralCodesForm" method="POST" action="gencodeservlet">
     <input type="hidden" name="groupId" value="<%=request.getParameter("groupId")%>"/>
     <table  id="tbl1" width="80%" border="1" bgcolor="white" bordercolor="#999999">

			 <caption>
			     <db:LoopRows dataSource="dso2">
  		         <strong>General Code Listing for: <db:ElementValue dataSource="dso2" property="Description"/></strong>
  		     </db:LoopRows>    
			 </caption>
       <tr bgcolor="#FFCC00">
         <th align="center">&nbsp;</th>
         <th align="center">Code Id</th>
         <th align="center">Group Id</th>
         <th align="center">Short Description</th>
         <th align="center">Long Description</th>
         <th align="center">Update Date</th>
         <th align="center">Update User</th>
       </tr>

       <% if (QUERY_BEAN.getWhereClause() == null) {
            out.println("</form>");
            if (pageContext.getAttribute("ROW") == null) {
               out.println("<tr><td colspan=6 align=center>Data Not Found</td></tr>");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
            return;
          }
       %>

         <db:LoopRows dataSource="dso">
           <tr>
             <td bgcolor="#FFCC00" width="1%" align="center">
                 <db:InputControl dataSource="dso"
                                  type="checkbox"
                                  name="selCbx"
                                  value="rowid"/>
             </td>
             <td width="2%">
                <db:InputControl dataSource="dso"
                                 type="text"
                                 name="Id"
                                 property="Id"
                                 size="8%"
                                 style="border:none"
                                 readOnly="yes"
                                 uniqueName="yes"/>
             </td>
             <td width="2%">
                <db:InputControl type="text"
                                 name="GroupId"
                                 dataSource="dso"
                                 property="GroupId"
                                 size="8%"
                                 style="border:none"
                                 readOnly="yes"
                                 uniqueName="yes"/>
             </td>
             <td width="3%" align="left">
                <db:InputControl type="text"
                                 name="Shortdesc"
                                 dataSource="dso"
                                 property="Shortdesc"
                                 size="5%"
                                 style="border:none"
                                 readOnly="no"
                                 uniqueName="yes"
                                 onChange="setRowStatus(GeneralCodesForm, this, 'Shortdesc')"/>             
             
             </td>
             <td width="8%" align="left">
                <db:InputControl type="text"
                                 name="Longdesc"
                                 dataSource="dso"
                                 property="Longdesc"
                                 size="50%"
                                 style="border:none"
                                 readOnly="no"
                                 uniqueName="yes"
                                 onChange="setRowStatus(GeneralCodesForm, this, 'Longdesc')"/>             
             <db:InputControl dataSource="dso" type="hidden" name="rowStatus" uniqueName="yes" value="U"/>                                 
             </td>
             <td width="3%" align="center"><db:ElementValue dataSource="dso" property="DateUpdated" format="MM-dd-yyyy"/></td>
             <td width="2%" align="center"><db:ElementValue dataSource="dso" property="UserId"/></td>
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
						 out.println("  <td width='2%'>");
						 out.println("    <input type=text name=Id" + rowCount + " size=8% value=0 style=\"border:none\">");
						 out.println("  </td>");
						 
						 out.println("  <td width='2%'>");
						 out.println("    <input type=text name=GroupId" + rowCount + " size=8% value=" + request.getParameter("groupId") + " style=\"border:none\">");
						 out.println("  </td>");						 
						 out.println("  <td width='3%'>");
						 out.println("    <input type=\"text\" name=\"Shortdesc" + rowCount + "\" size=5%  style=\"border:none\" onChange=\"setRowStatus(GeneralCodesForm, this, 'Shortdesc')\">");
						 out.println("  </td>");						 
						 out.println("  <td width='8%'>");
						 out.println("    <input type=\"text\" name=\"Longdesc" + rowCount + "\" size=50%  style=\"border:none\" onChange=\"setRowStatus(GeneralCodesForm, this, 'Longdesc')\">");
						 out.println("    <input type=hidden name=rowStatus" + rowCount + " value=\"U\">");
						 out.println("  </td>");						 
						 
						 out.println("  <td width=\"3%\" align=\"center\">&nbsp;&nbsp;</td>");
  					 out.println("  <td width=\"2%\" align=\"center\">&nbsp;&nbsp;</td>");
						 out.println("</tr>");
         %>         
         
         <input name="clientAction" type="hidden">
         <input name="entity" type="hidden">
       </form>
       
       <% if (pageContext.getAttribute("ROW") == null) {
            out.println("<tr><td colspan=6 align=center>Data Not Found</td></tr>");
          }
       %>
     </table>
  </body>
</html>
