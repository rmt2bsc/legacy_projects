<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<%
  String pageTitle = "State/Province Edit";
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
			       
     <db:datasource id="countryDso"
                   classId="com.api.DataSourceApi"
                   connection="con"
								   query="CountryView"
								   order="Name"
								   type="xml"/>

	<body bgcolor="#FFFFFF" text="#000000">
	  <h2><strong><%=pageTitle%></strong></h2>
	  <br><br>
		<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/State.Edit">
				<table width="40%" border="0">
						<tr> 
							<td width="20%" bgcolor="#FFCC00"> 
								<div align="right"><b><font size="2">State Id:</font></b></div>
							</td>
							<td width="80%"> 
								<div id="bus_id" align="left"> 
									<beanlib:InputControl type="hidden" name="StateId" value="#record.StateId"/>
									<beanlib:InputControl value="#record.StateId"/> 
								</div>
							</td>
						</tr>
						<tr>
							<td bgcolor="#FFCC00"> 
								<div align="right"><b><font size="2">State Name:</font></b></div>
							</td>
							<td> 
								<div align="left"> 
									<beanlib:InputControl type="text" size="60" tabIndex="5" name="StateName" value="#record.StateName"/>
								</div>
							</td>
						</tr>	
						<tr> 
							<td bgcolor="#FFCC00"> 
								<div align="right"><b><font size="2">State Code:</font></b></div>
							</td>
							<td> 
								<div align="left"> 
									<beanlib:InputControl type="text" size="20" tabIndex= "1" name="AbbrCode" value="#record.AbbrCode"/>
								</div>
							</td>
						</tr>
						<tr>
							<td bgcolor="#FFCC00"> 
								<div align="right"><b><font size="2">Country:</font></b></div>
							</td>
							<td> 
								<div align="left"> 
									<db:InputControl dataSource="countryDso"
   												     		 type="select"
																	 name="CountryId"
													 				 codeProperty="CountryId"
																	 displayProperty="Name"
																	 selectedValue="#record.CountryId"/>
								</div>
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
				<br>				
				<input type="button" name="<%=GeneralConst.REQ_SAVE%>" value="Save" style=width:90  onClick="handleAction('_self', document.DataForm, this.name)">
				<input type="button" name="<%=GeneralConst.REQ_DELETE%>" value="Delete" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
				<input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
        <input name="clientAction" type="hidden">
		</form>
		<db:Dispose/>
	</body>
</html>
