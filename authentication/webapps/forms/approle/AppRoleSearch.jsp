<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ page import="com.bean.criteria.AppRoleCriteria" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.api.security.user.SecurityConst" %>


<gen:InitAppRoot id="APP_ROOT"/>

  <%
	  String pageTitle = "Application Role Search";
	  String mainCriteria = null;
  %>
<html>
  <head>
    <title><%=pageTitle %></title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/Timesheet.js"></script>
  </head>



  <%@include file="/includes/SessionQuerySetup.jsp"%>

  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

     <!--  Begin Search Criteria section -->
     <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/AppRole.Search">
        <font size="4" style="color:blue">Selection Criteria</font>
        <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:60%; height:55px">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
             <tr>
						   <td width="20%" class="clsTableFormHeader">Application</td>
						   <td width="30%">
							 <beanlib:InputControl dataSource="<%=SecurityConst.APP_LIST%>"
																	   type="select"
																	   name="qry_ApplicationId"
																	   codeProperty="AppId"
																	   displayProperty="Name"
								                     selectedValue="#QUERY_BEAN.CustomObj.Qry_ApplicationId"/>								  
						   </td>
						   <td width="20%" class="clsTableFormHeader">Role</td>
						   <td width="30%">
							 <beanlib:InputControl dataSource="<%=SecurityConst.ROLE_LIST%>"
																	   type="select"
																	   name="qry_RoleId"
																	   codeProperty="RoleId"
																	   displayProperty="Name"
																	   selectedValue="#QUERY_BEAN.CustomObj.Qry_RoleId"/>
						   </td>
             </tr>
						 <tr>
							   <td class="clsTableFormHeader">App/Role Name</td>
							   <td>
							       <beanlib:InputControl type="text" name="qry_AppRoleName" value="#QUERY_BEAN.CustomObj.qry_AppRoleName"/>
							   </td>
							   <td class="clsTableFormHeader">App/Role Code</td>
							   <td>
							       <beanlib:InputControl type="text" name="qry_AppRoleCode" value="#QUERY_BEAN.CustomObj.qry_AppRoleCode"/>
							   </td>
						 </tr>
          </table>
        </div>
        <a href="javascript:handleAction('_self', document.SearchForm, '<%=GeneralConst.REQ_SEARCH%>')">
	      <img src="<%=APP_ROOT%>/images/element_find.gif" alt="Search for Application-Roles" style="border:none">
	    </a>
        <br><br>
     <!--  Begin Search Resultssection -->

        <font size="4" style="color:blue">Search Results</font>
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:90%; height:480px; overflow:auto">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
				   <th width="2%" class="clsTableListHeader">&nbsp;</th>
				   <th width="15%" class="clsTableListHeader" style="text-align:left" valign="bottom">Name</th>
				   <th width="10%" class="clsTableListHeader"  style="text-align:left" valign="bottom">Code</th>
				   <th width="15%" class="clsTableListHeader" style="text-align:left" valign="bottom">Application</th>
				   <th width="15%" class="clsTableListHeader" style="text-align:left" valign="bottom">Role</th>
				   <th width="43%" class="clsTableListHeader" style="text-align:left" valign="bottom">Description</th>
			   </tr>

   			 <beanlib:LoopRows bean="item" list="<%=GeneralConst.CLIENT_DATA_LIST%>">
			   <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
			   			<td class="clsTableListHeader" valign="center">
								 	<beanlib:InputControl type="radio" name="<%=GeneralConst.CLIENTROW_PROPERTY%>" value="rowid"/>
								 	<beanlib:InputControl type="hidden" name="AppRoleId" value="#item.AppRoleId" uniqueName="yes"/>
							</td>	
			        <td>
			            <font size="2">
			               <beanlib:InputControl value="#item.AppRoleName"/>
			            </font>
			        </td>
			        <td>
			           <font size="2">
			              <beanlib:InputControl value="#item.AppRoleCode"/>
			           </font>
			        </td>
			        <td>
			           <font size="2">
  			              <beanlib:InputControl value="#item.AppName"/>
  			           </font>
			        </td>
			        <td>
			           <font size="2">
  			              <beanlib:InputControl value="#item.RoleName"/>
  			           </font>
			        </td>			        
			        <td>
			            <font color="blue" size="2">
			               <beanlib:InputControl value="#item.AppRoleDescription"/>
			            </font>
			        </td>
			     </tr>
			   </beanlib:LoopRows>
			   <% if (pageContext.getAttribute("ROW") == null) {
				     out.println("<tr><td colspan=10 align=center>Data Not Found</td></tr>");
				  }
			   %>

            </table>
        </div>
        <br>

		<input name="clientAction" type="hidden">
    </form>
     
    <table width="30%" border="0">
         <tr>
			      <td width="20%"  valign="top" align="center"> 
			          <a href="javascript:handleAction('_self', document.SearchForm, '<%=GeneralConst.REQ_ADD%>')"> 
			            <img name="m_res" src="<%=APP_ROOT%>/images/cm-approles-add.gif"  width="48px" height="70px" style="border: none" alt="Add Application Role">
			          </a>
			      </td>	  			      			      			 			      
			      <td width="20%"  valign="top" align="center"> 
			          <a href="javascript:handleAction('_self', document.SearchForm, '<%=GeneralConst.REQ_EDIT%>')"> 
			            <img name="m_res" src="<%=APP_ROOT%>/images/cm-approles-edit.gif"  width="48px" height="70px" style="border: none" alt="Edit Application Role">
			          </a>
			      </td>	  			      			      			 			      			      
			      <td width="60%" valign="top" align="center"> 
    		    	  <a href="/authentication/index.jsp"> 
        	   			<img name="m_back" src="<%=APP_ROOT%>/images/cm-back.gif" width="48px" height="70px" style="border: none" alt="Security Main Menu">
          			</a>
      			</td>	        				
        </tr>
    </table>
 </body>
</html>
