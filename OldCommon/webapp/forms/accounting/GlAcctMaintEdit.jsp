<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@  page import="com.bean.GlAccountTypes" %>
<%@  page import="com.bean.GlAccountCategory" %>
<%@  page import="com.bean.GlAccounts" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
     GlAccounts acct = (GlAccounts) request.getAttribute("ACCT");
     acct = (acct == null ? new GlAccounts() : acct);
	 GlAccountTypes acctType = (GlAccountTypes) request.getAttribute("ACCTTYPE");
	 acctType = (acctType == null ? new GlAccountTypes() : acctType);
	 GlAccountCategory acctCatg = (GlAccountCategory) request.getAttribute("ACCTCATG");
	 acctCatg = (acctCatg == null ? new GlAccountCategory() : acctCatg);	 
	 String strMsg = (String) request.getAttribute("MSG");
	 strMsg = strMsg == null ? "" : strMsg;
%>

<html>
  <head>
    <title>General Ledger Account Maintenance</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
  </head>
  <script>
    function handleAction2(action) {
	alert("Action: " + action);
      this.DataForm.clientAction.value = action;
      this.DataForm.target = "EditFrame";
      this.DataForm.submit();
    }
	
	function disableFooterButtons(_flag) {
	   parent.ActionFrame.SubmitSection.edit.disabled = _flag;
	   parent.ActionFrame.SubmitSection.add.disabled = _flag;
	   parent.ActionFrame.SubmitSection.del.disabled = _flag;
	}
  </script>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
  
  <body bgcolor="#FFFFCC" onLoad="disableFooterButtons(true)">
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/glaccountmaintservlet">
	     <input  type="hidden" name="Id" value="<%= acct.getId() %>" >                                  
		 <input  type="hidden" name="AcctTypeId" value="<%= acct.getAcctTypeId() %>" >                                  
		 <input  type="hidden" name="AcctCatId" value="<%= acct.getAcctCatId() %>" >                                  
		 
		 <table  width="100%" border="0"> 
		      <caption align="left"><Strong>General Ledger Account Maintenance</Strong></caption>
			 <tr>
				 <td width="13%" class="clsTableFormHeader">Id:</td>
				 <td width="87%"><%= acct.getId() %></td>
			 </tr>  
			 <tr>
				 <td class="clsTableFormHeader">Account Type:</td>
				 <td><%= acctType.getDescription() %></td>
			 </tr>
			 <tr>
				 <td class="clsTableFormHeader">Account Category:</td>
				 <td><%= acctCatg.getDescription() %></td>
			 </tr>
			 <tr>
				 <td class="clsTableFormHeader">Account Number:</td>
				 <td><%= acct.getAcctNo() == null ? "" : acct.getAcctNo() %></td>
			 </tr>			 			 			 
			 <tr>
				 <td class="clsTableFormHeader">Account Name:</td>
				 <td>
				     <input type="text" name="Name" value="<%= acct.getName() == null ? "" : acct.getName()%>" size="85" maxlength="100">
				  </td>
			 </tr>
			 <tr>
				 <td class="clsTableFormHeader">Account Description:</td>
				 <td>
				     <input type="text" name="Description" value="<%= acct.getDescription() == null ? "" : acct.getDescription() %>" size="125" maxlength="255">
				  </td>
			 </tr>			 
			 <tr>
			    <td>
				    <img src="images/clr.gif" height="20">
				</td>
				<td class="clsErrorText"><%= strMsg %></td>
			 </tr>
			 <tr>
				<td>
				   <input type="button" name="save" value="Save" class="clsGeneralButton" onClick="handleAction('EditFrame', document.DataForm, this.name)">
				</td>
				<td>
                  <input type="button" name="back" value="Back" class="clsGeneralButton"  onClick="disableFooterButtons(false); handleAction('EditFrame', document.DataForm, this.name)">
</td>
			 </tr>   
			 </table>
			 <input type="hidden" name="clientAction">
     </form>
  </body>
</html>
