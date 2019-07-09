<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>General Ledger Account Category Listing</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script>
	    function handleAction2(action) {
	      document.GlCatgMaintListForm.clientAction.value = action;
	      document.GlCatgMaintListForm.target = "EditFrame";
	      document.GlCatgMaintListForm.submit();
	    }
	  </script>
  </head>
  
  <body bgcolor="#FFFFCC">
     <form name="GlCatgMaintListForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/AcctCatgConsole.AcctTypeCatg">
	     <beanlib:InputControl type="hidden" name="masterAcctTypeId" value="#acctType.AcctTypeId"/>
	     <h3>GL Account Category Listing for: <beanlib:InputControl value="#acctType.Description"/></h3>
	     <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:85%; height:375px; overflow:auto">
			 <table  width="100%" cellpadding="0" cellspacing="0" border="0" bgcolor="white"> 
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
						  out.println("<input name=\"clientAction\" type=\"hidden\">");
							out.println("</form>");
							out.println("<caption><strong>GL Account Category Listing for: (Account Type Not Selected)</strong></caption>");
							out.println("<tr><td colspan=6 align=center>Data Not Found</td></tr>");
							out.println("</table>");
							out.println("</div>");
							out.println("<br>");
							out.println("<input type=\"button\" name=\"back\" value=\"Back\" style=\"width:90\" onClick=\"handleAction('_top', document.GlCatgMaintListForm, this.name)\">");
							out.println("</body>");
							out.println("</html>");
							return;
						}
				 %>
					 <beanlib:LoopRows bean="beanObj" list="<%=GeneralConst.CLIENT_DATA_LIST%>">
				     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
							 <td bgcolor="#FFCC00" width="1%" align="center">
									 <beanlib:InputControl type="radio" name="selCbx" value="rowid"/>
									 <beanlib:InputControl type="hidden" name="AcctTypeId" value="#beanObj.AcctTypeId" uniqueName="yes"/>                                  
									 <beanlib:InputControl type="hidden" name="AcctCatgId" value="#beanObj.AcctCatgId" uniqueName="yes"/>                                  
									 <beanlib:InputControl type="hidden" name="Description" value="#beanObj.Description" uniqueName="yes"/>                                  
							 </td>
							 <td width="5%" align="center">
							    <beanlib:InputControl value="#beanObj.AcctCatgId"/>
							 </td>             
							 <td width="30%" align="left">
							    <beanlib:InputControl value="#beanObj.Description"/>
							 </td>
							 <td width="20%" align="center">
									 <beanlib:InputControl value="#beanObj.DateCreated" format="MM-dd-yyyy"/>
							 </td>
							 <td width="20%" align="center">
									 <beanlib:InputControl value="#beanObj.DateUpdated" format="MM-dd-yyyy"/>
							 </td>
							 <td width="20%" align="center">
									 <beanlib:InputControl value="#beanObj.UserId"/>
							 </td>
						 </tr>
					 </beanlib:LoopRows>

				 <% if (pageContext.getAttribute("ROW") == null) {
							out.println("<tr><td colspan=6 align=center>Data Not Found</td></tr>");
						}
				 %>
			 </table>
			 </div>
			 <br>
		   <input type="button" name="<%=GeneralConst.REQ_EDIT%>" value="Edit" style=width:90 onClick="handleAction2(this.name)">
			 <input type="button" name="<%=GeneralConst.REQ_ADD%>" value="Add" style=width:90 onClick="handleAction2(this.name)">
			 <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style=width:90 onClick="handleAction('_top', document.GlCatgMaintListForm, this.name)">
			 <input name="clientAction" type="hidden">
     </form>
  </body>
</html>
