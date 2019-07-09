<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@  page import="com.bean.GlAccountCategory" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
     GlAccountCategory acctCatg = (GlAccountCategory) request.getAttribute("acctCatg");
     acctCatg = (acctCatg == null ? new GlAccountCategory() : acctCatg);
%>

<html>
  <head>
    <title>General Ledger Account Maintenance Listing</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
  </head>

  
  <script>
    function handleAction2(_action) {
      this.DataForm.clientAction.value = _action;
      this.DataForm.target = "EditFrame";
      this.DataForm.submit();
    }
  </script>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  
  <body bgcolor="#FFFFCC">
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/glaccountmaintservlet">
	         <input type="hidden" name="masterAcctTypeId" value=<%= acctCatg.getAcctTypeId() %>>
			 <input type="hidden" name="masterAcctCatgId" value=<%= acctCatg.getId() %>>
			 <table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
			     <caption align="left">
			        <strong>General Ledger Accounts </strong> 
				 </caption>
				 <tr>
					 <th class="clsTableListHeader">&nbsp;</th>
					 <th class="clsTableListHeader">Id</th>
					 <th class="clsTableListHeader" style="text-align:left">GL Account Type </th>
					 <th class="clsTableListHeader" style="text-align:left">GL Account Category</th>
					 <th class="clsTableListHeader" style="text-align:left">Name</th>
					 <th class="clsTableListHeader" style="text-align:center">Seq No</th>
					 <th class="clsTableListHeader">Acct No.</th>
					 <th class="clsTableListHeader" style="text-align:left">Description</th>
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
					 <beanlib:LoopRows bean="beanObj" list="glAccounts">
					      <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>

							 <td width="3%" align="center" class="clsTableListHeader">
									 <beanlib:InputControl dataSource="beanObj" type="radio" name="selCbx" value="rowid"/>
									 <beanlib:InputControl dataSource="beanObj" type="hidden" name="AcctTypeId" property="AcctTypeId" uniqueName="yes"/>                                  
									 <beanlib:InputControl dataSource="beanObj" type="hidden" name="AcctCatId" property="AcctCatId" uniqueName="yes"/>                                  
									 <beanlib:InputControl dataSource="beanObj" type="hidden" name="Id" property="Id" uniqueName="yes"/>                                  
									 <beanlib:InputControl dataSource="beanObj" type="hidden" name="Name" property="Name" uniqueName="yes"/>                                  
									 <beanlib:InputControl dataSource="beanObj" type="hidden" name="Description" property="Description" uniqueName="yes"/>                                  
									 <beanlib:InputControl dataSource="beanObj" type="hidden" name="AcctNo" property="AcctNo" uniqueName="yes"/>                                  
									 <beanlib:InputControl dataSource="beanObj" type="hidden" name="AcctSeq" property="AcctSeq" uniqueName="yes"/>                                  
							 </td>   
							 <td width="5%" align="center" >
							    <font size="2">
							      <beanlib:ElementValue dataSource="beanObj" property="Id"/>
								</font>
							 </td>             
 							 <td width="12%">
							    <font size="2">
   							      <beanlib:ElementValue dataSource="beanObj" property="AcctTypeDescr"/>
								  </font>
							 </td>
							 <td width="16%">
							     <font size="2">
							    <beanlib:ElementValue dataSource="beanObj" property="AcctCatgDescr"/>
								</font>
							 </td>
							 <td width="14%">
							     <font size="2">
							    <beanlib:ElementValue dataSource="beanObj" property="Name"/>
								</font>
							 </td>
							 <td width="4%" align="center">
							    <font size="2">
							    <beanlib:ElementValue dataSource="beanObj" property="AcctSeq"/>
								</font>
							 </td>
							 <td width="6%" >
							     <font size="2">
							    <beanlib:ElementValue dataSource="beanObj" property="AcctNo"/>
								</font>
							 </td>
							 <td width="20%">
							     <font size="2">
							    <beanlib:ElementValue dataSource="beanObj" property="Description"/>
								</font>
							 </td>
							 <td width="7%" align="center" >
							 <font size="2">
								<beanlib:ElementValue dataSource="beanObj" property="DateCreated" format="MM-dd-yyyy"/>
								</font>
						     </td>
							<td width="7%" align="center" >
							<font size="2">
							  <beanlib:ElementValue dataSource="beanObj" property="DateUpdated" format="MM-dd-yyyy"/>
							  </font>
						   </td>
						   <td width="6%" align="center">
							   <font size="2">
								 <beanlib:ElementValue dataSource="beanObj" property="UserId"/>
							   </font>
						   </td>
						 </tr>
					 </beanlib:LoopRows>
					 <input name="clientAction" type="hidden">

				 <% if (pageContext.getAttribute("ROW") == null) {
							out.println("<tr><td colspan=11 align=center>Data Not Found</td></tr>");
						}
				 %>
			 </table>
     </form>
  </body>
</html>
