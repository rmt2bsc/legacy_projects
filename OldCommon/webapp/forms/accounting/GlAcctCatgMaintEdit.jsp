<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.GlAccountTypes" %>
<%@ page import="com.bean.GlAccountCategory" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
    GlAccountTypes acctType = (GlAccountTypes) request.getAttribute("ACCTTYPE");
	GlAccountCategory acctCatg = (GlAccountCategory) request.getAttribute("ACCTCATG") == null ? new GlAccountCategory() : (GlAccountCategory) request.getAttribute("ACCTCATG");
%>
	
<html>
  <head>
    <title>GL Account Category Maintenance </title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
  </head>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
  <body bgcolor="#FFFFCC">
     <form name="MainForm" method="POST" action="<%=APP_ROOT%>/glacctcatgmaintservlet">
	          <input name="Id" type="hidden" value=<%= acctCatg.getId() %>>
			  <input name="AcctTypeId" type="hidden" value=<%= acctCatg.getAcctTypeId() %>>
			 <table  width="50%" border="0"> 
			      <caption>
				     <strong>GL Account Category Maintenance Edit</strong>
				  </caption>
				 <tr>
					 <th width="40%" class="clsTableFormHeader"><div align="right">Id:</div></th>
					 <td><%= acctCatg.getId() %></td>
		       </tr>
				 <tr>
					 <th class="clsTableFormHeader"><div align="right">Account Type:</div></th>
					 <td><%= acctType.getDescription() %></td>
		       </tr>				  
				  <tr>
					 <th class="clsTableFormHeader"><div align="right">Category Description:</div></th>
					 <td>
					     <input type="text" name="Description" value="<%= acctCatg.getDescription() == null ? "" : acctCatg.getDescription() %>" size="48%" maxlength="40">
					 </td>
				  </tr>
					 <input name="clientAction" type="hidden">
					 <input name="acctCatg" type="hidden" value="<%=acctCatg.getId()%>">
					 <input name="acctType" type="hidden" value="<%=acctType.getId()%>">
			 </table>
             <table  width="20%" border="0"> 
			      <tr>
				       <td>   
					       <img src="images/clr.gif" height="26">
					   </td>
				  </tr>
			     <tr>
				    <td>
                      <input type="button" name="save" value="Save" class="clsGeneralButton" onClick="handleAction('EditFrame', document.MainForm, this.name)">
</td>
				    <td>
			           <input type="button" name="cancel" value="Cancel" class="clsGeneralButton"  onClick="handleAction('EditFrame', document.MainForm, this.name)">
				    </td>					
				</tr>
			 </table>
     </form>
  </body>
</html>
