<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst"%>
<%@ page import="com.bean.GeneralCodesGroup" %>

<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<html>
  <head>
    <title>General Code Edit</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
  	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>
  
   <%
  	  String pageTitle = "General Code Edit";    
 	 %>
	
  <body bgcolor="#FFFFFF" text="#000000">
    <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/GeneralCode.GeneralCodeEdit">
		<h3><strong><%=pageTitle%></strong></h3>
  	<br>
	  
		<table width="50%" border="0" cellspacing="0" cellpadding="0">
				<tr> 
					 <td width="25%" class="clsTableFormHeader">
						   <font size="3"><b>Code Id</b></font>
					 </td>
					 <td align="left" width="75%" >&nbsp;
					     <beanlib:InputControl type="hidden" name="CodeId" value="#record.CodeId"/>                                  
					 	 <beanlib:InputControl value="#record.CodeId"/>                                  
					 </td>
				</tr>		
				<tr> 
					 <td width="25%" class="clsTableFormHeader">
						   <font size="3"><b>Group Id</b></font>
					 </td>
					 <td align="left" width="75%" >&nbsp;
					   <beanlib:InputControl type="hidden" name="CodeGrpId" value="#record.CodeGrpId"/>                                  
					 	 <beanlib:InputControl value="#data.Description"/>                                  
					 </td>
				</tr>
				<tr> 
					 <td class="clsTableFormHeader">
						<font size="3"><b>Short Name</b></font>
					 </td>
					 <td align="left">&nbsp;
						<beanlib:InputControl type="text" name="Shortdesc" value="#record.Shortdesc" size="20"/>                                  
					 </td>
				</tr>
				<tr> 
					 <td class="clsTableFormHeader">
						<font size="3"><b>Long Name</b></font>
					 </td>
					 <td align="left">&nbsp;
						<beanlib:InputControl type="text" name="Longdesc" value="#record.Longdesc" size="50"/>                                  
					 </td>
				</tr>				
		</table>
     <br>
     <font color="red">
	     <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
	   </font>
	   
	   <!-- Display command buttons -->
     <div id="ButtonLayer" style="position:relative; top:20px; width:100%; height:30px; z-index:1">
		  <table width="100%" cellpadding="0" cellspacing="0">
			  <tr>
				<td colspan="2">     
				  <input name="<%=GeneralConst.REQ_SAVE%>" type="button" value="Apply" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
				  <input name="<%=GeneralConst.REQ_DELETE%>" type="button" value="Delete" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
				  <input name="<%=GeneralConst.REQ_LIST%>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
				</td>
			 </tr>		
		  </table>			   
     </div>
     
	   <input name="clientAction" type="hidden">
   </form>
   <br>
   

 </body>
</html>
