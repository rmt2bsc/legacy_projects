<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>
    
<%
  String pageTitle = "Postal Zip Code Search";
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
    
    <db:datasource id="timeZoneDso"
                   classId="com.api.DataSourceApi"
                   connection="con"
								   query="TimeZoneView"
								   order="descr"
								   type="xml"/>

	<body bgcolor="#FFFFFF" text="#000000">
	    <h2><strong><%=pageTitle%></strong></h2>
			<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/Zipcode.Search">
			    <font size="4" style="color:blue">Search Criteria</font>
				<table width="90%" border="0">
					<tr>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b>Zip Code:</b></font></div>
						</td>
						<td width="19%">
						  <beanlib:InputControl type="text" name="qry_Zip" value="#QUERY_BEAN.CustomObj.qry_Zip" size="10"/>
						</td>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b>City:</b></font></div>
						</td>
						<td width="24%">
						    <beanlib:InputControl type="text" name="qry_City" value="#QUERY_BEAN.CustomObj.qry_City" size="10"/>
						</td>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><b><font size="2">City Alias::</font></b></div>
						</td>
						<td width="21%">
						    <beanlib:InputControl type="text" name="qry_CityAliasName" value="#QUERY_BEAN.CustomObj.qry_CityAliasName" size="10"/>
						</td>
					</tr>
					<tr>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b>State (Abbreviated):</b></font></div>
						</td>
						<td width="19%">
						    <beanlib:InputControl type="text" name="qry_State" value="#QUERY_BEAN.CustomObj.qry_State" size="10"/>
						</td>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b>Area Code:</b></font></div>
						</td>
						<td width="24%">
						    <beanlib:InputControl type="text" name="qry_AreaCode" value="#QUERY_BEAN.CustomObj.qry_AreaCode" size="10"/>
						</td>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><b><font size="2">County Name:</font></b></div>
						</td>
						<td width="21%">
							<beanlib:InputControl type="text" name="qry_CountyName" value="#QUERY_BEAN.CustomObj.qry_CountyName" size="10"/>
						</td>
					</tr>
					
					<tr>
						<td bgcolor="#FFCC00" width="12%">
							<div align="right"><font size="2"><b>Time Zone:</b></font></div>
						</td>
						<td width="19%">
						    <db:InputControl dataSource="timeZoneDso"
																 type="select"
																 name="qry_TimeZone"
																 codeProperty="TimeZoneId"
																 displayProperty="Descr"
																 selectedValue="#QUERY_BEAN.CustomObj.Qry_TimeZone"/>			
						</td>
						<td colspan="4">&nbsp;</td>
					</tr>
				</table>
				<br>
				
				<br>
				<font size="4" style="color:blue">Search Results&nbsp;&nbsp; <i>Sorted by State, County Name, City, City Alias, and Zip code</i></font>
          <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:85%; height:520px; overflow:auto">
						<table width="100%" border="0" cellspacing="0">
							<tr bgcolor="#FFCC00">
							  <th width="3%" class="clsTableListHeader">&nbsp;</th>
							  <th width="5%"> 
									<div align="left">
									   <font color="#000000"><b>Id</b></font>
									</div>
							  </th>
							  <th width="7%" bgcolor="#FFCC00"> 
									<div align="left"> 
									   <font color="#000000"><b>Zipcode</b></font> 
									</div>
							  </th>
							  <th width="5%"> 
									<div align="left">
									  <font color="#000000"><b>State</b></font>
									</div>
							  </th>
							  <th width="18%"> 
									<div align="left">
									  <font color="#000000"><b>County</b></font>
									</div>
							  </th>
	 						  <th width="22%"> 
									<div align="left">
									  <font color="#000000"><b>City</b></font>
									</div>
							  </th>
	 						  <th width="22%"> 
									<div align="left">
									  <font color="#000000"><b>City Alias</b></font>
									</div>
							  </th>						  
	 						  <th width="10%"> 
									<div align="left">
									  <font color="#000000"><b>Area Code</b></font>
									</div>
							  </th>						  
	  						<th width="8%"> 
									<div align="left">
									  <font color="#000000"><b>Time Zone</b></font>
									</div>
							  </th>						  						  
							</tr>
			
							<beanlib:LoopRows bean="zipitem" list="<%=GeneralConst.CLIENT_DATA_LIST%>">
							    <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
										<td valign="top" align="center" bgcolor="#FFCC00">
										 	<beanlib:InputControl type="radio" name="<%=GeneralConst.CLIENTROW_PROPERTY%>" value="rowid"/>
										 	<beanlib:InputControl type="hidden" name="ZipId" value="#zipitem.ZipId" uniqueName="yes"/>
										</td>
								    <td valign="top"> 
									   <beanlib:InputControl value="#zipitem.ZipId"/> 
								    </td>
								    <td valign="top"> 
									   <beanlib:InputControl value="#zipitem.Zip"/> 
								    </td>
								    <td valign="top"> 
									   <beanlib:InputControl value="#zipitem.State"/> 
								    </td>
								    <td valign="top"> 
									   <beanlib:InputControl value="#zipitem.CountyName"/> 
								    </td>
								    <td valign="top"> 
									   <beanlib:InputControl value="#zipitem.City"/> 
								    </td>
								    <td valign="top"> 
									   <beanlib:InputControl value="#zipitem.CityAliasName"/> 
								    </td>							    
								    <td valign="top"> 
									   <beanlib:InputControl value="#zipitem.AreaCode"/> 
								    </td>							    
								    <td valign="top"> 
									   <beanlib:InputControl value="#zipitem.TimezoneDescr"/> 
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
				  <input type="button" name="<%=GeneralConst.REQ_VIEW%>" value="View" style=width:90  onClick="handleAction('_self', document.DataForm, this.name)">
				  <input type="button" name="<%=GeneralConst.REQ_RESET%>" value="Reset" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
				  <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
   	      <input name="clientAction" type="hidden">
			</form>
			<db:Dispose/>
	</body>
</html>
