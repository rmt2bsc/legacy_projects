<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<%
  String pageTitle = "Country Edit";
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

	<body bgcolor="#FFFFFF" text="#000000">
	  <h2><strong><%=pageTitle%></strong></h2>
	  <br><br>
		<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/Country.Edit">
				<table width="40%" border="0">
						<tr> 
							<td width="20%" bgcolor="#FFCC00"> 
								<div align="right"><b><font size="2">Id:</font></b></div>
							</td>
							<td width="80%"> 
								<div id="bus_id" align="left"> 
									<beanlib:InputControl type="hidden" name="CountryId" value="#record.CountryId"/>
									<beanlib:InputControl value="#record.CountryId"/> 
								</div>
							</td>
						</tr>
						<tr>
							<td bgcolor="#FFCC00"> 
								<div align="right"><b><font size="2">Country Name:</font></b></div>
							</td>
							<td> 
								<div align="left"> 
									<beanlib:InputControl type="text" size="60" tabIndex="5" name="Name" value="#record.Name"/>
								</div>
							</td>
						</tr>	
						<tr>
							<td bgcolor="#FFCC00"> 
								<div align="right"><b><font size="2">Country Code:</font></b></div>
							</td>
							<td> 
								<div align="left"> 
									<beanlib:InputControl type="text" size="30" tabIndex="5" name="Code" value="#record.Code"/>
								</div>
							</td>
						</tr>
						<tr> 
							<td bgcolor="#FFCC00"> 
								<div align="right"><b><font size="2">Void Indicator:</font></b></div>
							</td>
							<td> 
								<div align="left"> 
									<beanlib:InputControl type="text" size="20" tabIndex= "1" name="CntryVoidInd" value="#record.CntryVoidInd"/>
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
	</body>
</html>
