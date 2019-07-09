<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.api.ContactsConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<%
  String pageTitle = "Personal Contact Edit";     
%>   

<html>
	<head>
		<title><%=pageTitle%></title>
		<meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
	  <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	</head>

    <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
						  
    <db:datasource id="perTitleDso" 
                   classId="com.api.DataSourceApi" 
                   connection="con"
									 query="GeneralCodesView"
									 where="code_grp_id = 26"
									 order="longdesc"
									 type="xml"/>												  
						  
    <db:datasource id="perMaritalStatDso" 
                   classId="com.api.DataSourceApi" 
                   connection="con"
									 query="GeneralCodesView"
									 where="code_grp_id = 5"
									 order="longdesc"
									 type="xml"/>												  
						  
    <db:datasource id="perGenderDso" 
                   classId="com.api.DataSourceApi" 
                   connection="con"
									 query="GeneralCodesView"
 								   where="code_grp_id = 6"
								   order="longdesc"
									 type="xml"/>
						  
    <db:datasource id="perRaceDso" 
                   classId="com.api.DataSourceApi" 
                   connection="con"
									 query="GeneralCodesView"
									 where="code_grp_id = 15"
									 order="longdesc"
									 type="xml"/>												  
    
	<body bgcolor="#FFFFFF" text="#000000">
		<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/Person.Edit">
					<table width="90%" border="0">
						<caption align="left">
							<strong><%=pageTitle%></strong> 
						</caption>
						<tr> 
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>Person Id:</b></font></div>
							</td>
							<td width="19%">
								<beanlib:InputControl type="hidden" name="PersonId" value="#data.PersonId"/>
								<beanlib:InputControl type="hidden"  name="AddrId" value="#data.AddrId"/>  
								<beanlib:InputControl value="#data.PersonId"/> 
							</td>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>Maiden Name:</b></font></div>
							</td>
							<td width="19%">
							    <beanlib:InputControl type="text" size="20" tabIndex="6" name="Maidenname" value="#data.PerMaidenname"/>								 
							</td>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b><font size="2">Marital Status:</font></b></font></div>
							</td>
							<td width="24%">
								<db:InputControl dataSource="perMaritalStatDso"
															   type="select"
															   name="MaritalStatusId"
															   codeProperty="CodeId"
															   displayProperty="Longdesc"
															   selectedValue="#data.PerMaritalStatus"/>						 							
							</td>										
						</tr>
						<tr> 
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>Title</b></font></div>
							</td>
							<td width="24%">
								<db:InputControl dataSource="perTitleDso"
															   type="select"
															   name="Title"
															   codeProperty="CodeId"
															   displayProperty="Longdesc"
															   selectedValue="#data.PerTitle"/>						 														
							</td>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><b><font size="2">Generation:</font></b></div>
							</td>
							<td width="21%">
							    <beanlib:InputControl type="text" size="5" tabIndex="7" name="Generation" value="#data.PerGeneration"/>								 
							</td>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><b><font size="2">Race:</font></b></div>
							</td>
							<td width="21%">
								<db:InputControl dataSource="perRaceDso"
															   type="select"
															   name="RaceId"
															   codeProperty="CodeId"
															   displayProperty="Longdesc"
															   selectedValue="#data.PerRaceId"/>						 																					
							</td>										
						</tr>
						<tr> 
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>First Name:</b></font></div>
							</td>
							<td width="19%">
								<beanlib:InputControl type="text" size="20" tabIndex="2" name="Firstname" value="#data.PerFirstname"/>								 
							</td>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>SSN:</b></font></div>
							</td>
							<td width="24%">
								<beanlib:InputControl type="text" size="10" tabIndex="8" name="Ssn" value="#data.PerSsn"/>
							</td>
							<td width="12%">&nbsp;</td>
	                    </tr>
	                    <tr>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>Middle Name:</b></font></div>
							</td>
							<td width="24%">
								<beanlib:InputControl type="text" size="20" tabIndex="3" name="Midname" value="#data.PerMidname"/>								 
							</td>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><b><font size="2">Birth Date:</font></b></div>
							</td>
							<td width="21%">
								<beanlib:InputControl type="text" size="20" tabIndex="9" name="BirthDate" value="#data.PerBirthDate" format="MM/dd/yyyy"/>								
							</td>										
							<td width="12%">&nbsp;</td>
						</tr>
						<tr>	
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><b><font size="2">Last Name:</font></b></div>
							</td>
							<td width="21%">
							    <beanlib:InputControl type="text" size="20" tabIndex="4" name="Lastname" value="#data.PerLastname"/>								 
							</td>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>Gender:</b></font></div>
							</td>
							<td width="19%">
								<db:InputControl dataSource="perGenderDso"
															   type="select"
															   name="GenderId"
															   codeProperty="CodeId"
															   displayProperty="Longdesc"
															   selectedValue="#data.PerGenderId"/>						 																												
							</td>
							<td width="12%">&nbsp;</td>
						</tr>
	                    <tr> 
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>Email:</b></font></div>
							</td>
							<td colspan="3"> 
								<div align="left">
								    <beanlib:InputControl type="text" size="45" tabIndex="5" name="Email" value="#data.PerEmail"/>								 
								</div>
							</td>
							<td width="12%"> 
								<div align="right"><b><font size="2"></font></b></div>
							</td>
							<td width="21%">&nbsp;</td>
						</tr>
					</table>
					<br>

				<!--  Include address edit section -->
        <%@include file="../address/AddressEdit.jsp"%>  

				<p>&nbsp;</p>
				<p>&nbsp;</p>
				
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
				<br>
				<input type="button" name="<%=GeneralConst.REQ_SAVE%>" value="Save" style=width:90  onClick="handleAction('_self', document.DataForm, this.name)">
				<input type="button" name="<%=GeneralConst.REQ_DELETE%>" value="Delete" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
				<input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
        <input name="clientAction" type="hidden">
			</form>
			<db:Dispose/>
		</body>
</html>
