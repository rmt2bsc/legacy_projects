<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.bean.criteria.ContactCriteria" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.api.ContactsConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<%
  String pageTitle = "Business Contacts Search";
  RMT2TagQueryBean query = session.getAttribute(RMT2ServletConst.QUERY_BEAN) == null ? new RMT2TagQueryBean() : (RMT2TagQueryBean) session.getAttribute(RMT2ServletConst.QUERY_BEAN);
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
									  query="VwBusinessAddressView"
									  where="<%=criteria%>"
									  order="bus_longname"/>
				  
    <db:datasource id="busTypeDso"
                   classId="com.api.DataSourceApi"
                   connection="con"
							 	   query="GeneralCodesView"
								   where="code_grp_id = 7"
								   order="longdesc"
								   type="xml"/>

    <db:datasource id="busServTypeDso"
                   classId="com.api.DataSourceApi"
                   connection="con"
								   query="GeneralCodesView"
								   where="code_grp_id = 8"
								   order="longdesc"
								   type="xml"/>

	<body bgcolor="#FFFFFF" text="#000000">
	    <h2><strong><%=pageTitle%></strong></h2>
		<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/Business.Search">
		    <font size="4" style="color:blue">Search Criteria</font>
			<table width="90%" border="0">
				<tr>
					<td width="10%" bgcolor="#FFCC00">
						<div align="right"><b><font size="2">Business Id:</font></b></div>
					</td>
					<td width="21%">
						<div id="bus_id" align="left">
						    <input type="hidden" name="<%= ContactsConst.CLIENT_CONTACT_TYPE%>" value="<%=ContactsConst.CONTACT_TYPE_BUSINESS%>">
						    <beanlib:InputControl type="text" name="qry_BusinessId" value="#QUERY_BEAN.CustomObj.qry_BusinessId" size="10"/>
						</div>
					</td>
					<td width="12%" bgcolor="#FFCC00">
						<div align="right"><b><font size="2">Tax Id:</font></b></div>
					</td>
					<td width="24%">
						<div align="left">
						  <beanlib:InputControl type="text" name="qry_BusTaxId" value="#QUERY_BEAN.CustomObj.qry_BusTaxId" size="10"/>
						</div>
					</td>
					<td width="12%" bgcolor="#FFCC00">
						<div align="right"><b><font size="2">Web Site:</font></b></div>
					</td>
					<td width="21%">
						<div align="left">
						  <beanlib:InputControl type="text" name="qry_BusWebsite" value="#QUERY_BEAN.CustomObj.qry_BusWebsite" size="20"/>
						</div>
					</td>
				</tr>
				<tr>
					<td width="10%" bgcolor="#FFCC00">
						<div align="right"><b><font size="2">Name:</font></b></div>
					</td>
					<td width="21%">
						<div align="left">
						  <beanlib:InputControl type="text" name="qry_BusLongname" value="#QUERY_BEAN.CustomObj.qry_BusLongname" size="20"/>
						</div>
					</td>
					<td width="12%" bgcolor="#FFCC00">
						<div align="right"><b><font size="2">Contact First Name:</font></b></div>
					</td>
					<td width="24%">
						<div align="left">
						  <beanlib:InputControl type="text" name="qry_BusContactFirstname" value="#QUERY_BEAN.CustomObj.qry_BusContactFirstname" size="20"/>
						</div>
					</td>
					<td width="12%" bgcolor="#FFCC00">
						<div align="right"><b><font size="2">Contact Last Name:</font></b></div>
					</td>
					<td width="21%">
						<div align="left">
						  <beanlib:InputControl type="text" name="qry_BusContactLastname" value="#QUERY_BEAN.CustomObj.qry_BusContactLastname" size="20"/>
						</div>
					</td>
				</tr>
				<tr>
					<td width="10%" bgcolor="#FFCC00">
						<div align="right"><b><font size="2">Contact Phone:</font></b></div>
					</td>
					<td width="21%">
						<div align="left">
						  <beanlib:InputControl type="text" name="qry_BusContactPhone" value="#QUERY_BEAN.CustomObj.qry_BusContactPhone" size="20"/>
						</div>
					</td>
					<td width="12%" bgcolor="#FFCC00">
						<div align="right"><b><font size="2">Business Type:</font></b></div>
					</td>
					<td width="24%">
						<div id="bus_bus_type" align="left">
							<db:InputControl dataSource="busTypeDso"
															 type="select"
															 name="qry_BusEntityTypeId"
															 codeProperty="CodeId"
															 displayProperty="Longdesc"
															 selectedValue="#QUERY_BEAN.CustomObj.Qry_BusEntityTypeId"/>
						</div>
					</td>
					<td width="12%">
						<div align="right"><b></b></div>
					</td>
					<td width="21%">
						</div>
					</td>
				</tr>
				<tr>
					<td width="10%" bgcolor="#FFCC00">
						<div align="right"><b><font size="2">Service Type:</font></b></div>
					</td>
					<td colspan="2">
						<div id="bus_serv_type" align="left">
							<db:InputControl dataSource="busServTypeDso"
															 type="select"
															 name="qry_BusServTypeId"
															 codeProperty="CodeId"
															 displayProperty="Longdesc"
															 selectedValue="#QUERY_BEAN.CustomObj.Qry_BusServTypeId"/>
					</td>
					<td width="24%">&nbsp;</td>
					<td width="12%">&nbsp;</td>
					<td width="21%">&nbsp;</td>
				</tr>
			</table>
			<br>

			<!--  Include address selection criteria section -->
            <%@include file="../address/AddressCriteria.jsp"%>
               
            <br>
			<font size="4" style="color:blue">Search Results</font>
            <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:80%; height:380px; overflow:auto">
							<table width="100%" border="0" cellspacing="0">
								<tr bgcolor="#FFCC00"> 
								  <td width="8%"> 
										<div align="center"> <font color="#000000" size="2"><b>Id</b></font> </div>
								  </td>
								  <td width="19%" bgcolor="#FFCC00"> 
										<div align="left"> <font color="#000000" size="2"><b>Name</b></font></div>
								  </td>
								  <td width="24%"> 
										<div align="left"> <font color="#000000" size="2"><b>Address</b></font></div>
								  </td>
								  <td width="15%"> 
										<div align="left"><b><font size="2">Phone Numbers</font></b></div>
								  </td>								  								  
								  <td width="21%"> 
										<div align="left"> <font color="#000000" size="2"><b>Contact Name</b></font></div>
								  </td>
								  <td width="11%"> 
										<div align="left"><b><font size="2">Contact Phone</font></b></div>
								  </td>

								</tr>
								<db:LoopRows dataSource="address"> 
									<gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
										  <td valign="top">
								         <%
		 									      String urlTarget = APP_ROOT + "/unsecureRequestProcessor/Business.Search?clientAction=edit&Id="  + address.getColumnValue("BusinessId") + "&AddrId=" + address.getColumnValue("AddrId");
	  										 %>
										     <a href="<%=urlTarget%>" target='_self'>
												   <db:InputControl value="#address.BusinessId"/> 
											   </a>          
										  </td>
										  <td  valign="top"> 
												<db:InputControl value="#address.BusLongname"/>
										  </td>
										  
										  <!--  Include address list format section -->
                      <%@include file="../address/AddressList.jsp"%>
                      
                      <td  valign="top"> 
												<db:InputControl value="#address.BusContactFirstname"/>&nbsp;
												<db:InputControl value="#address.BusContactLastname"/>
										  </td>
										  <td  valign="top"> 
												<db:InputControl value="#address.BusContactPhone"/>
										  </td>
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
