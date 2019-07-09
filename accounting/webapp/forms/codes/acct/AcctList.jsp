<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@  page import="com.bean.GlAccountCategory" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>

<gen:InitAppRoot id="APP_ROOT"/>


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
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/AccountsConsole.Accounts">
	     <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:90%; height:375px; overflow:auto">
	       <beanlib:InputControl type="hidden" name="masterAcctTypeId" value="#acctCatg.AcctTypeId"/>
	       <beanlib:InputControl type="hidden" name="masterAcctCatgId" value="#acctCatg.AcctCatgId"/>
				 <table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
				     <caption align="left">
				        <strong>General Ledger Accounts </strong> 
					 </caption>
					 <tr>
						 <th width="3%" class="clsTableListHeader">&nbsp;</th>
						 <th width="5%" class="clsTableListHeader">Id</th>
						 <th width="9%" class="clsTableListHeader" style="text-align:left">GL Account Type </th>
						 <th width="13%" class="clsTableListHeader" style="text-align:left">GL Account Category</th>
						 <th width="11%" class="clsTableListHeader" style="text-align:left">Name</th>
						 <th width="4%" class="clsTableListHeader" style="text-align:center">Seq No</th>
						 <th width="6%" class="clsTableListHeader" style="text-align:left">Acct No.</th>
						 <th width="29%" class="clsTableListHeader" style="text-align:left">Description</th>
						 <th width="7%" class="clsTableListHeader">Create Date</th>
						 <th width="7%" class="clsTableListHeader">Update Date</th>
						 <th width="6%" class="clsTableListHeader">Update User</th>
					 </tr>
	
					 <%
							if (request.getParameter("firsttime") != null) {
							  out.println("<input name=\"clientAction\" type=\"hidden\">");
								out.println("</form>");
								out.println("<caption><strong>GL Account Category Listing for: (Account Type Not Selected)</strong></caption>");
								out.println("<tr><td colspan=11 align=center>Data Not Found</td></tr>");
								out.println("</table>");
								out.println("</div>");
								out.println("<br>");
								out.println("<input type=\"button\" name=\"back\" value=\"Back\" style=\"width:90\" onClick=\"handleAction('_top', document.DataForm, this.name)\">");
								out.println("</body>");
								out.println("</html>");
								return;
							}
					 %>
						 <beanlib:LoopRows bean="beanObj" list="<%=GeneralConst.CLIENT_DATA_LIST %>">
						   <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
	
								 <td align="center" class="clsTableListHeader">
										 <beanlib:InputControl type="radio" name="selCbx" value="rowid"/>
										 <beanlib:InputControl type="hidden" name="AcctId" value="#beanObj.Id" uniqueName="yes"/>                                  
								 </td>   
								 <td align="center" >
								    <font size="2">
								      <beanlib:InputControl value="#beanObj.Id"/>
									</font>
								 </td>             
	 							 <td>
								    <font size="2">
	   							      <beanlib:InputControl value="#beanObj.Accttypedescr"/>
									  </font>
								 </td>
								 <td>
								     <font size="2">
								    <beanlib:InputControl value="#beanObj.Acctcatgdescr"/>
									</font>
								 </td>
								 <td>
								     <font size="2">
								    <beanlib:InputControl value="#beanObj.Name"/>
									</font>
								 </td>
								 <td align="center">
								    <font size="2">
								    <beanlib:InputControl value="#beanObj.AcctSeq"/>
									</font>
								 </td>
								 <td>
								     <font size="2">
								    <beanlib:InputControl value="#beanObj.AcctNo"/>
									</font>
								 </td>
								 <td>
								     <font size="2">
								    <beanlib:InputControl value="#beanObj.Description"/>
									</font>
								 </td>
								 <td align="center" >
								 <font size="2">
									<beanlib:InputControl value="#beanObj.DateCreated" format="MM-dd-yyyy"/>
									</font>
							     </td>
								<td align="center" >
								<font size="2">
								  <beanlib:InputControl value="#beanObj.DateUpdated" format="MM-dd-yyyy"/>
								  </font>
							   </td>
							   <td align="center">
								   <font size="2">
									 <beanlib:InputControl value="#beanObj.UserId"/>
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
			 </div>
 			 <br>
		   <input type="button" name="<%=GeneralConst.REQ_EDIT%>" value="Edit" style=width:90 onClick="handleAction2(this.name)">
			 <input type="button" name="<%=GeneralConst.REQ_ADD%>" value="Add" style=width:90 onClick="handleAction2(this.name)">
			 <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style=width:90 onClick="handleAction('_top', document.DataForm, this.name)">
     </form>
  </body>
</html>
