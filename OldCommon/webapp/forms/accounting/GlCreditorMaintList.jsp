<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.CreditorCombine" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<jsp:useBean id="QUERY_BEAN" scope="session" class="com.bean.RMT2TagQueryBean"/>

<html>
  <head>
    <title>General Ledger Creditor/Vendor Account Maintenance Listing</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	<script>
      function executeList(_target, _form, _action) {
		handleAction(_target, _form, _action);
	  }	
	</script>
  </head>

     <%
		 CreditorCombine cc = (CreditorCombine) QUERY_BEAN.getCustomObj();
		 String criteria =  QUERY_BEAN.getWhereClause();
		 String queryView = QUERY_BEAN.getQuerySource();
         int creditorType = cc.getCreditorTypeId();
 		 String requestType =  request.getParameter("requestType") ;
    %>
  
  
  
  <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
  <db:datasource id="dso" 
                          classId="com.api.DataSourceApi" 
                          connection="con"
						  query="<%=queryView%>"
						  where="<%=criteria%>"
						  order="longname"
						  type="xml"/>    

  <body>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/glcreditorservlet">
			 <table  width="100%" border="0" bgcolor="white"  cellpadding="0" cellspacing="0"> 
			     <caption align="left" style="color:blue">Creditor List</caption>
				 <tr>
					 <th class="clsTableListHeader">&nbsp;</th>
					 <th class="clsTableListHeader" style="text-align:left">Account Number</th>
					 <th class="clsTableListHeader" style="text-align:left">Name </th>
					 <th class="clsTableListHeader" style="text-align:left">Type</th>
					 <th class="clsTableListHeader" style="text-align:right">Credit Limit</th>
					 <th class="clsTableListHeader" style="text-align:right">APR</th>
					 <th class="clsTableListHeader">Create Date</th>
					 <th class="clsTableListHeader">Update Date</th>
					 <th class="clsTableListHeader">Update User</th>
				 </tr>

				 <%
						if (request.getParameter("firsttime") != null) {
							out.println("</form>");
							out.println("<tr><td colspan=11 align=\"center\">Data Not Found</td></tr>");
							out.println("</table>");
							out.println("</body>");
							out.println("</html>");
							return;
						}
				 %>
				 <db:LoopRows dataSource="dso">
					      <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>

							 <td width="1%" align="center" class="clsTableListHeader">
								 <db:InputControl dataSource="dso" type="radio" name="selCbx" value="rowid"/>
								 <db:InputControl dataSource="dso" type="hidden" name="CreditorId" property="CreditorId" uniqueName="yes"/>                                  
								 <db:InputControl dataSource="dso" type="hidden" name="BusinessId" property="BusinessId" uniqueName="yes"/>                                  
							 </td>   
							 <td width="10%" align="left">
							    <font size="2">
							      <db:ElementValue dataSource="dso" property="AccountNo"/>
								</font>
							 </td>             
 							 <td width="22%">
							    <font size="2"> 
   							      <db:ElementValue dataSource="dso" property="Longname"/>
								  </font>
							 </td>
							 <td width="5%">
							     <font size="3">
							    <db:ElementValue dataSource="dso" property="CreditorTypeDescription"/>
								</font>
							 </td>
							 <td width="10%" align="right">
							     <font size="2">
							    <db:ElementValue dataSource="dso" property="CreditLimit" format="$#,##0.00;($#,##0.00)"/>
								</font>
							 </td>
							 <td width="5%" align="right" >
							    <font size="2">
							    <db:ElementValue dataSource="dso" property="Apr"/>
								</font>
							 </td>
							 <td width="9%" align="center">
							     <font size="2">
							    <db:ElementValue dataSource="dso" property="DateCreated" format="MM-dd-yyyy"/>
								</font>
							 </td>
							 <td width="9%" align="center" >
							 <font size="2">
								<db:ElementValue dataSource="dso" property="DateUpdated" format="MM-dd-yyyy"/>
								</font>
						     </td>
							<td width="7%" align="center" >
							<font size="2">
							  <db:ElementValue dataSource="dso" property="UserId" />
							  </font>
						   </td>
						 </tr>
					 </db:LoopRows>
					 <input name="requestType" type="hidden" value=<%=requestType%>>
					 <input name="clientAction" type="hidden">

				 <% if (pageContext.getAttribute("ROW") == null) {
							out.println("<tr><td colspan=11 align=center>Data Not Found</td></tr>");
						}
				 %>
			 </table>
     </form>
  </body>
</html>
