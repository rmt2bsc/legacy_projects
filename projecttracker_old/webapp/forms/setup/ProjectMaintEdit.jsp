<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.project.ProjectConst" %>
<%@ page import="com.bean.ProjProject" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Project Maintenance Edit</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	<script type='text/javascript' language='javascript' src="<%=APP_ROOT%>/js/datetimepicker.js"></script>   
  </head>
  
   <%
  	  String pageTitle = "Timesheet Project Maintenance Edit";
  	  ProjProject project = request.getAttribute("project") == null ? new ProjProject() : (ProjProject) request.getAttribute("project");
	%>
	
  <body bgcolor="#FFFFFF" text="#000000">
       <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/ProjectMaint.Edit"> 
		  <h3><strong><%=pageTitle%></strong></h3>
  		<br>
	  
		<table width="65%" border="0" cellspacing="2" cellpadding="0">
			<tr> 
				 <td width="25%" class="clsTableFormHeader">
					   <font size="3"><b>Project Id</b></font>
				 </td>
				 <td align="left" width="75%" >
				     <beanlib:InputControl  type="hidden" name="ProjId" value="#project.ProjId"/>                                  
				 	 <beanlib:InputControl  value="#project.ProjId"/>                                  
				 </td>
			</tr>
			<tr> 
				 <td class="clsTableFormHeader">
					<font size="3"><b>Project Name</b></font>
				 </td>
				 <td align="left">
					<beanlib:InputControl  type="text" name="Description" value="#project.Description" size="60"/>                                  
				 </td>
			</tr>

			<tr> 
				 <td class="clsTableFormHeader">
					<font size="3"><b>Effective Date</b></font>
				 </td>
				 <td align="left">
					<beanlib:InputControl  type="text" name="EffectiveDate" value="#project.EffectiveDate" format="MM-dd-yyyy"/>
					<a href="javascript:NewCal('EffectiveDate','mmddyyyy')"><img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>
				 </td>    
			</tr>
			<tr> 
				 <td class="clsTableFormHeader">
					<font size="3"><b>End Date</b></font>
				 </td>
				 <td align="left">
					<beanlib:InputControl  type="text" name="EndDate" value="#project.EndDate" format="MM-dd-yyyy"/>
					<a href="javascript:NewCal('EndDate','mmddyyyy')"><img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>
				 </td>
			</tr>				

			<tr>
			    <td>&nbsp;</td>
			</tr>
			<tr> 
				 <td class="clsTableFormHeader">
					   <font size="3"><b>Client</b></font>
				 </td>
				 <td align="left">
				     <beanlib:InputControl dataSource="clients" 
				                           type="select" 
				                           name="ClientId" 
				                           codeProperty="ClientId" 
				                           displayProperty="Name" 
				                           selectedValue="#project.ClientId"/>
				 </td>
			</tr>
			<tr>
			    <td>&nbsp;</td>
			</tr>
		</table>
		
		<table width="50%" border="0" cellspacing="2" cellpadding="0">
		   <tr>
			 <td width="35%" class="clsTableFormHeader">
				   <font size="3"><b>Date Created</b></font>
			 </td>
			 <td width="65%" align="left">
				<beanlib:InputControl  value="#project.DateCreated" format="MM-dd-yyyy HH:mm:ss"/>                                  
			 </td>					 
		   </tr>
		   <tr>
			 <td class="clsTableFormHeader">
			   <font size="3"><b>Date Last Updated</b></font>
			 </td>
			 <td align="left">
				<beanlib:InputControl  value="#project.DateUpdated" format="MM-dd-yyyy HH:mm:ss"/>                                  
			 </td>					 
		   </tr>
		   <tr>
			 <td class="clsTableFormHeader">
				   <font size="3"><b>Last Update User</b></font>
			 </td>
			 <td align="left">
				<beanlib:InputControl  value="#project.UserId"/>                                  
			 </td>					 
		   </tr>		   
		</table>
		<br>
		
		 <!-- Display any messgaes -->
		 <table>
			 <tr>
	  		   <td>
			     <font color="red">
				     <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
			     </font>
		 	   </td>
			 </tr>
		 </table>
		 
	    <!-- Display command buttons -->
	    <div id="ButtonLayer" style="position:relative; top:20px; width:100%; height:30px; z-index:1">
		  <table width="100%" cellpadding="0" cellspacing="0">
			  <tr>
				<td colspan="2">     
				  <input name="<%=ProjectConst.REQ_SAVE%>" type="button" value="Apply" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
				  <input name="<%=ProjectConst.REQ_BACK%>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
				</td>
			  </tr>		
		  </table>			   
		</div>
	
	<input name="clientAction" type="hidden">
   </form>
 </body>
</html>
