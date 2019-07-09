<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<jsp:useBean id="acctType" scope="request" class="com.bean.GlAccountTypes"/>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>General Ledger Account Category Listing</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
  </head>
  
  <script>
    function handleAction2(action) {
      this.GlCatgMaintListForm.clientAction.value = action;
      this.GlCatgMaintListForm.target = "EditFrame";
      this.GlCatgMaintListForm.submit();
    }
  </script>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js">
  </script>
  
  <body bgcolor="#FFFFCC">
     <form name="GlCatgMaintListForm" method="POST" action="<%=APP_ROOT%>/glacctcatgmaintservlet">
	         <input type="hidden" name="masterAcctTypeId" value=<%= acctType.getId() %>>
			 <table  width="85%" cellpadding="0" cellspacing="0" border="0" bgcolor="white"> 
				 <tr>
					 <th class="clsTableListHeader">&nbsp;</th>
					 <th class="clsTableListHeader">Id</th>
					 <th class="clsTableListHeader" style="text-align:left">Category Description</th>
					 <th class="clsTableListHeader">Create Date</th>
					 <th class="clsTableListHeader">Update Date</th>
					 <th class="clsTableListHeader">Update User</th>
				 </tr>

				 <%
						if (request.getParameter("firsttime") != null) {
							out.println("</form>");
							out.println("<caption><strong>GL Account Category Listing for: (Account Type Not Selected)</strong></caption>");
							out.println("<tr><td colspan=6 align=center>Data Not Found</td></tr>");
							out.println("</table>");
							out.println("</body>");
							out.println("</html>");
							return;
						}
				 %>
				 <caption>
						 <strong>GL Account Category Listing for: <%=acctType.getDescription()%></strong>
				 </caption>

					 <beanlib:LoopRows bean="beanObj" list="acctCatg">
						 <tr>
						     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
							 <td bgcolor="#FFCC00" width="1%" align="center">
									 <beanlib:InputControl dataSource="beanObj" type="radio" name="selCbx" value="rowid"/>
									 <beanlib:InputControl dataSource="beanObj" type="hidden" name="AcctTypeId" property="AcctTypeId" uniqueName="yes"/>                                  
									 <beanlib:InputControl dataSource="beanObj" type="hidden" name="Id" property="Id" uniqueName="yes"/>                                  
									 <beanlib:InputControl dataSource="beanObj" type="hidden" name="Description" property="Description" uniqueName="yes"/>                                  
							 </td>
							 <td width="5%" align="center">
							    <beanlib:ElementValue dataSource="beanObj" property="Id"/>
							 </td>             
							 <td width="30%" align="left">
							    <beanlib:ElementValue dataSource="beanObj" property="Description"/>
							 </td>
							 <td width="20%" align="center">
									 <beanlib:ElementValue dataSource="beanObj" property="DateCreated" format="MM-dd-yyyy"/>
							 </td>
							 <td width="20%" align="center">
									 <beanlib:ElementValue dataSource="beanObj" property="DateUpdated" format="MM-dd-yyyy"/>
							 </td>
							 <td width="20%" align="center">
									 <beanlib:ElementValue dataSource="beanObj" property="UserId"/>
							 </td>
						 </tr>
					 </beanlib:LoopRows>
					 <input name="clientAction" type="hidden">

				 <% if (pageContext.getAttribute("ROW") == null) {
							out.println("<tr><td colspan=6 align=center>Data Not Found</td></tr>");
						}
				 %>
			 </table>
     </form>
  </body>
</html>
