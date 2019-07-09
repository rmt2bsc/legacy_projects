<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.api.ContactsConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>
    
<%
  String pageTitle = "Personal Contact Search";
  RMT2TagQueryBean query = (RMT2TagQueryBean) session.getAttribute(RMT2ServletConst.QUERY_BEAN) == null ? new RMT2TagQueryBean() : (RMT2TagQueryBean) session.getAttribute(RMT2ServletConst.QUERY_BEAN);
  String criteria = query.getWhereClause();
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
    
    <db:datasource id="address" 
								   classId="com.api.DataSourceApi" 
								   connection="con"
								   query="VwPersonAddressView"
								   where="<%=criteria%>"
								   order="per_lastname, per_firstname"/>
				   
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
	    <h2><strong><%=pageTitle%></strong></h2>
			<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/Person.Search">
			    <font size="4" style="color:blue">Search Criteria</font>
				<table width="90%" border="0">
					<tr>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b>Person Id:</b></font></div>
						</td>
						<td width="19%">
						    <input type="hidden" name="<%= ContactsConst.CLIENT_CONTACT_TYPE%>" value="<%=ContactsConst.CONTACT_TYPE_PERSONAL%>">
						    <beanlib:InputControl type="text" name="qry_PersonId" value="#QUERY_BEAN.CustomObj.qry_PersonId" size="10"/>
						</td>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b>SSN:</b></font></div>
						</td>
						<td width="24%">
						    <beanlib:InputControl type="text" name="qry_PerSsn" value="#QUERY_BEAN.CustomObj.qry_PerSsn" size="10"/>
						</td>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><b><font size="2">Birth Date:</font></b></div>
						</td>
						<td width="21%">
						    <beanlib:InputControl type="text" name="qry_PerBirthDate" value="#QUERY_BEAN.CustomObj.qry_PerBirthDate" size="10"/>
						</td>
					</tr>
					<tr>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b>First Name:</b></font></div>
						</td>
						<td width="19%">
						    <beanlib:InputControl type="text" name="qry_PerFirstname" value="#QUERY_BEAN.CustomObj.qry_PerFirstname" size="10"/>
						</td>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b>Middle Name:</b></font></div>
						</td>
						<td width="24%">
						    <beanlib:InputControl type="text" name="qry_PerMidname" value="#QUERY_BEAN.CustomObj.qry_PerMidname" size="10"/>
						</td>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><b><font size="2">Last Name:</font></b></div>
						</td>
						<td width="21%">
						    <beanlib:InputControl type="text" name="qry_PerLastname" value="#QUERY_BEAN.CustomObj.qry_PerLastname" size="10"/>
						</td>
					</tr>
					<tr>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b>Maiden Name:</b></font></div>
						</td>
						<td width="19%">
						    <beanlib:InputControl type="text" name="qry_PerMaidenname" value="#QUERY_BEAN.CustomObj.qry_PerMaidenname" size="10"/>
						</td>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b>Title</b></font></div>
						</td>
						<td width="24%">
									<db:InputControl dataSource="perTitleDso"
																	 type="select"
																	 name="qry_PerTitle"
																	 codeProperty="CodeId"
																	 displayProperty="Longdesc"
																	 selectedValue="#QUERY_BEAN.CustomObj.Qry_PerTitle"/>
						</td>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><b><font size="2">Generation:</font></b></div>
						</td>
						<td width="21%">
						    <beanlib:InputControl type="text" name="qry_PerGeneration" value="#QUERY_BEAN.CustomObj.qry_PerGeneration" size="10"/>
						</td>
					</tr>
					<tr>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b>Gender:</b></font></div>
						</td>
						<td width="19%">
							<db:InputControl dataSource="perGenderDso"
												 type="select"
												 name="qry_PerGenderId"
												 codeProperty="CodeId"
												 displayProperty="Longdesc"
												 selectedValue="#QUERY_BEAN.CustomObj.Qry_PerGenderId"/>
						</td>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b><font size="2">Marital Status:</font></b></font></div>
						</td>
						<td width="24%">
							<db:InputControl dataSource="perMaritalStatDso"
											 type="select"
											 name="qry_PerMaritalStatus"
											 codeProperty="CodeId"
											 displayProperty="Longdesc"
											 selectedValue="#QUERY_BEAN.CustomObj.Qry_PerMaritalStatus"/>
						</td>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><b><font size="2">Race:</font></b></div>
						</td>
						<td width="21%">
							<db:InputControl dataSource="perRaceDso"
															 type="select"
															 name="qry_PerRaceId"
															 codeProperty="CodeId"
															 displayProperty="Longdesc"
															 selectedValue="#QUERY_BEAN.CustomObj.Qry_PerRaceId"/>
						</td>
					</tr>
					<tr>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b>Email:</b></font></div>
						</td>
						<td colspan="3">
							<div align="left">
							  <beanlib:InputControl type="text" name="qry_PerEmail" value="#QUERY_BEAN.CustomObj.qry_PerEmail" size="45"/>
							</div>
						</td>
						<td width="12%">
							<div align="right"><b><font size="2"></font></b></div>
						</td>
						<td width="21%">&nbsp;</td>
					</tr>
				</table>
				<br>
				
				<!--  Include address selection criteria section -->
                <%@include file="../address/AddressCriteria.jsp"%>

				<br>
				<font size="4" style="color:blue">Search Results</font>
          <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:70%; height:350px; overflow:auto">
					<table width="100%" border="0" cellspacing="0">
						<tr bgcolor="#FFCC00">
						  <td width="11%"> 
							<div align="center">
							   <font color="#000000">
							      <b>Id</b>
							   </font>
							</div>
						  </td>
						  <td width="24%" bgcolor="#FFCC00"> 
							<div align="left"> 
							   <font color="#000000">
							      <b>Name</b>
							   </font> 
							</div>
						  </td>
						  <td width="39%"> 
							<div align="left">
							  <font color="#000000"><b>Address</b></font>
							</div>
						  </td>
						  <td width="23%"> 
							<div align="left">
							  <font color="#000000"><b>Phone Numbers</b></font>
							</div>
						  </td>
						</tr>
		
						<db:LoopRows dataSource="address">
						    <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
								<td valign="top">
									<%
									  String urlTarget = APP_ROOT + "/unsecureRequestProcessor/Person.Search?clientAction=edit&Id=" + address.getColumnValue("PersonId") + "&AddrId=" + address.getColumnValue("AddrId");
									%>
								    <a href="<%=urlTarget%>" target='_self'>
										<db:InputControl value="#address.PersonId"/> 
									</a>          
								</td>
			
							    <td valign="top"> 
								   <db:InputControl value="#address.PerShortname"/> 
							    </td>
							    
								<!--  Include address list format section -->
			                    <%@include file="../address/AddressList.jsp"%>
						    </tr>
		                </db:LoopRows>
	                 </table>                
                </div>
				
				<br>

				<input type="button" name="<%=GeneralConst.REQ_SEARCH%>" value="Search" style=width:90  onClick="handleAction('_self', document.DataForm, this.name)">
				<input type="button" name="<%=GeneralConst.REQ_ADD%>" value="Add" style=width:90  onClick="handleAction('_self', document.DataForm, this.name)">
				<input type="button" name="<%=GeneralConst.REQ_RESET%>" value="Reset" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
				<input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
   	    <input name="clientAction" type="hidden">
			</form>
			<db:Dispose/>
	</body>
</html>
