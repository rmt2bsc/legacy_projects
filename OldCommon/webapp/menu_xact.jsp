<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.XactConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.api.security.authentication.AuthenticationConst" %>
<%@ page import="com.bean.RMT2SessionBean" %>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>
<%
   String login = SESSION_BEAN == null ? "!@#$%^&(*)" : SESSION_BEAN.getLoginId();
%>

<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Xact.js"></script>
<script>
	function handleAction(selection) {
		switch (selection.name) {
        case "m_logoff":
              parent.menuFrame.location.href = MENU_MAIN;
              menuForm.action = "<%=APP_ROOT%>/reqeustProcessor/Security.Authentication?clientAction=logoff";
              menuForm.target = "_top";
              break;		      		
				case "m_user":
						parent.menuFrame.location.href = MENU_MAIN;
						menuForm.action = "/authentication/requestProcessor/User.Search?clientAction=newsearch&<%=AuthenticationConst.AUTH_PROP_USERID%>=<%=login%>";
						menuForm.target = "windowFrame";
						break;
				case "m_codes":
						parent.menuFrame.location.href = MENU_MAIN;
						menuForm.action = "<%=APP_ROOT%>/reqeustProcessor/GeneralCodeGroup.GeneralCodeGroupList?clientAction=list";
						menuForm.target = "windowFrame";
						break;
				case "m_contacts":
						parent.menuFrame.location.href = MENU_MAIN;
						menuForm.action = "<%=APP_ROOT%>/contactservlet?clientAction=start";
						menuForm.target = "windowFrame";
						break;
        case "m_inventory":
            parent.menuFrame.location.href = MENU_INVENTORY;
            return;												
				case "m_gl_maint":
						parent.menuFrame.location.href = MENU_GL_MAINT;
						return;	
				case "m_projects":
			      parent.menuFrame.location.href = MENU_PROJECTS; 
            return;					
				 }

		menuForm.submit();
	}
</script>
 
<body bgcolor="#CCCCCC" text="#000000">
<form name="menuForm" method="post" action="">
  <table width="9%" border="0" bgcolor="#CCCCCC" cellpadding="0" cellspacing="0" height="20%">
    <tr> 
      <td height="1" valign="top" align="center"> 
           <img name="m_logoff" src="<%=APP_ROOT%>/images/logoff.gif" style="border: none" onClick="javascript:handleAction(this)" alt="Logoff User">
      </td>
    </tr>      
    <tr> 
      <td height="1" valign="top"> 
        <input type="button" name="m_contacts" value="Contacts" style="width:120" onClick="handleAction(this)">
      </td>
    </tr>
    <tr> 
      <td height="1" valign="top"> 
        <input type="button" name="m_gl_maint" value="General Ledger" style="width:120" onClick="handleAction(this)">
      </td>
    </tr>
    <tr> 
      <td height="1" valign="top"> 
        <input type="button" name="m_inventory" value="Inventory" style=width:120  onClick="handleAction(this)">
      </td>
    </tr>    
    <tr>
      <td height="1" valign="top">
        <input type="button" name="m_projects" value="Transactions" style="width:120">
      </td>   
    </tr>
	<tr> 
	  <td height="41" align="center">
		 <a href="<%=APP_ROOT%>/forms/xact/XactSearchFrameset.jsp?jspOrigin=general" target="windowFrame"> 
			 <img src="images/rmt2_xact_general.gif" style="border:none" alt="Manage general financial transactions">
		 </a>       
	  </td>
	</tr>    
	<tr> 
	  <td height="41" align="center">
		 <a href="<%=APP_ROOT%>/reqeustProcessor/XactSales.CustomerSalesSearch?clientAction=<%=GeneralConst.REQ_NEWSEARCH%>" target="windowFrame"> 
			 <img src="images/menuitem_sales.gif" style="border:none" alt="Manage Customer sales and payment transactions">
		 </a>       
	  </td>
	</tr>    	
	<tr> 
	  <td height="41" align="center">
		 <a href="<%=APP_ROOT%>/forms/xact/purchases/PurchasesMenu.jsp?title=Choose The Type of Purchase Transaction to Pursue" target="windowFrame"> 
			 <img src="images/menuitem_purchases.gif" style="border:none" alt="Manage Vendor and General Creditor Purchase Transactions">
		 </a>       
	  </td>
	</tr>    		
	<tr> 
	  <td height="41" align="center">
		 <a href="<%=APP_ROOT%>/forms/xact/disbursements/DisbursementTypeMenu.jsp?title=Choose Cash Disbursement Type"  target="windowFrame"> 
			 <img src="images/menuitem_cashout.gif" style="border:none" alt="Manage Cash Disbursements transactions">
		 </a>       
	  </td>
	</tr>    			
    <tr>
      <td height="1" valign="top"> 
			     <input type="button" name="m_projects" value="Projects" style="width:120" onClick="handleAction(this)">
      </td>
    </tr>
    <tr>
      <td height="1" valign="top">
        <input type="button" name="m_codes" value="General Codes" style="width:120" onClick="handleAction(this)">
      </td>
    </tr>
    <tr>
      <td height="1" valign="top">
        <input type="button" name="m_user" value="Security" style="width:120" onClick="handleAction(this)">
      </td>
    </tr>
  </table>
</form>
</body>
</html>
