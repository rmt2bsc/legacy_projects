<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.bean.criteria.StateCriteria" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>
    
<%
  String pageTitle = "U.S. States/Province Search";
  RMT2TagQueryBean query = (RMT2TagQueryBean) session.getAttribute(RMT2ServletConst.QUERY_BEAN) == null ? new RMT2TagQueryBean() : (RMT2TagQueryBean) session.getAttribute(RMT2ServletConst.QUERY_BEAN);
  StateCriteria contactArgs = (StateCriteria) query.getCustomObj();
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
			<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/State.Search">
			    <font size="4" style="color:blue">Search Criteria</font>
				<table width="60%" border="0">
					<tr>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b>State (Abbreviated):</b></font></div>
						</td>
						<td width="19%">
						  <beanlib:InputControl type="text" name="qry_StateCode" value="#QUERY_BEAN.CustomObj.qry_StateCode" size="10"/>
						</td>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b>State Name:</b></font></div>
						</td>
						<td width="24%">
						    <beanlib:InputControl type="text" name="qry_StateName" value="#QUERY_BEAN.CustomObj.qry_StateName" size="10"/>
						</td>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><b><font size="2">Country:</font></b></div>
						</td>
						<td width="21%">
							<db:InputControl dataSource="countryDso"
											 type="select"
											 name="qry_CountryId"
											 codeProperty="CountryId"
											 displayProperty="Name"
											 selectedValue="#QUERY_BEAN.CustomObj.Qry_CountryId"/>						
						</td>
					</tr>
				</table>
				<br>
				
				<br>
				<font size="4" style="color:blue">Search Results&nbsp;&nbsp; <i>Sorted by Country and State Name</i></font>
          <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:50%; height:540px; overflow:auto">
						<table width="100%" border="0" cellspacing="0">
							<tr bgcolor="#FFCC00">
							  <th width="5%" class="clsTableListHeader">&nbsp;</th>
							  <th width="10%"> 
									<div align="center">
									   <font color="#000000"><b>Id</b></font>
									</div>
							  </th>
							  <th width="38%" bgcolor="#FFCC00"> 
									<div align="left"> 
									   <font color="#000000"><b>Name</b></font> 
									</div>
							  </th>
							  <th width="15%"> 
									<div align="center">
									  <font color="#000000"><b>Code</b></font>
									</div>
							  </th>
							  <th width="32%"> 
									<div align="left">
									  <font color="#000000"><b>Country</b></font>
									</div>
							  </th>
			
							<beanlib:LoopRows bean="item" list="<%=GeneralConst.CLIENT_DATA_LIST%>">
							    <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
										<td valign="top" align="center" bgcolor="#FFCC00">
										 	<beanlib:InputControl type="radio" name="<%=GeneralConst.CLIENTROW_PROPERTY%>" value="rowid"/>
										 	<beanlib:InputControl type="hidden" name="StateId" value="#item.StateId" uniqueName="yes"/>
										</td>
								    <td valign="top" align="center"> 
									   <beanlib:InputControl value="#item.StateId"/> 
								    </td>
								    <td valign="top"> 
									   <beanlib:InputControl value="#item.StateName"/> 
								    </td>
								    <td valign="top" align="center"> 
									   <beanlib:InputControl value="#item.StateCode"/> 
								    </td>
								    <td valign="top"> 
									   <beanlib:InputControl value="#item.CountryName"/> 
								    </td>
							    </tr>
			          </beanlib:LoopRows>
		         </table>                
          </div>
				
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
			  	<input type="button" name="<%=GeneralConst.REQ_SEARCH%>" value="Search" style=width:90  onClick="handleAction('_self', document.DataForm, this.name)">
			  	<input type="button" name="<%=GeneralConst.REQ_ADD%>" value="Add" style=width:90  onClick="handleAction('_self', document.DataForm, this.name)">
				  <input type="button" name="<%=GeneralConst.REQ_EDIT%>" value="Edit" style=width:90  onClick="handleAction('_self', document.DataForm, this.name)">
				  <input type="button" name="<%=GeneralConst.REQ_RESET%>" value="Reset" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
				  <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
   	      <input name="clientAction" type="hidden">
			</form>
			<db:Dispose/>
	</body>
</html>
