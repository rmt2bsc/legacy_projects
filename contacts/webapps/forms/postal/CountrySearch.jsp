<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>
    
<%
  String pageTitle = "Country Search";
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
			<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/Country.Search">
				<br>
				<font size="4" style="color:blue">Search Results</font>
          <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:40%; height:540px; overflow:auto">
						<table width="100%" border="0" cellspacing="0">
							<tr bgcolor="#FFCC00">
							  <th width="5%" class="clsTableListHeader">&nbsp;</th>
							  <th width="10%"> 
									<div align="center">
									   <font color="#000000"><b>Id</b></font>
									</div>
							  </th>
							  <th width="65%" bgcolor="#FFCC00"> 
									<div align="left"> 
									   <font color="#000000"><b>Name</b></font> 
									</div>
							  </th>
							  <th width="20%" bgcolor="#FFCC00"> 
									<div align="left"> 
									   <font color="#000000"><b>Code</b></font> 
									</div>
							  </th>
			        </tr>
							<beanlib:LoopRows bean="item" list="<%=GeneralConst.CLIENT_DATA_LIST%>">
							    <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
										<td valign="top" align="center" bgcolor="#FFCC00">
										 	<beanlib:InputControl type="radio" name="<%=GeneralConst.CLIENTROW_PROPERTY%>" value="rowid"/>
										 	<beanlib:InputControl type="hidden" name="CountryId" value="#item.CountryId" uniqueName="yes"/>
										</td>
								    <td valign="top" align="center"> 
									   <beanlib:InputControl value="#item.CountryId"/> 
								    </td>
								    <td valign="top"> 
									   <beanlib:InputControl value="#item.Name"/> 
								    </td>
								    <td valign="top"> 
									   <beanlib:InputControl value="#item.Code"/> 
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
			  	<input type="button" name="<%=GeneralConst.REQ_ADD%>" value="Add" style=width:90  onClick="handleAction('_self', document.DataForm, this.name)">
				  <input type="button" name="<%=GeneralConst.REQ_EDIT%>" value="Edit" style=width:90  onClick="handleAction('_self', document.DataForm, this.name)">
				  <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
   	      <input name="clientAction" type="hidden">
			</form>
	</body>
</html>
