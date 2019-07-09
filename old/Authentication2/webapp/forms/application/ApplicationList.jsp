<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.Application" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.api.security.authentication.AuthenticationConst" %>

<%@ page import="com.api.DaoApi" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Application List</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>

  <%
	  String pageTitle = "Security - Application List";
	  String billableText = null;
  %>

  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/App.Search">
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:55%; height:480px; overflow:auto">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								 <th width="5%" class="clsTableListHeader">&nbsp;</th>
								 <th width="10%" class="clsTableListHeader">Id</th>
								 <th width="20%" class="clsTableListHeader" style="text-align:left">Name</th> 
								 <th width="50%" class="clsTableListHeader" style="text-align:left">Description</th>
								 <th width="15%" class="clsTableListHeader">Status</th>
							 </tr>
			
							 <beanlib:LoopRows bean="item" list="<%=GeneralConst.CLIENT_DATA_RECORD%>">
							 <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
								<td class="clsTableListHeader">
								 	<beanlib:InputControl type="radio" name="<%=GeneralConst.CLIENTROW_PROPERTY%>" value="rowid"/>
								 	<beanlib:InputControl type="hidden" name="AppId" value="#item.AppId" uniqueName="yes"/>
								</td>	
								<td align="center">
									<beanlib:InputControl value="#item.AppId"/>
								</td>
								<td>
									<beanlib:InputControl value="#item.Name"/>
								</td>
								<td>
									<beanlib:InputControl value="#item.Description"/>
								</td>
								<td align="center">
								    <gen:Evaluate  expression="#item.Active">
									     <gen:When expression="1">
										       Active
									      </gen:When>
										  <gen:When expression="0">
										       Inactive
									      </gen:When>
								   </gen:Evaluate>
								</td>
							 </tr>
							 </beanlib:LoopRows>		
		     			 <% 
				            if (pageContext.getAttribute("ROW") == null) {
							         out.println("<tr><td colspan=5 align=center>Data Not Found</td></tr>");
							      }
		         	 %>
            </table>
        </div>
        <br>

        <!-- Display command links -->
        <table width="50%" cellpadding="0" cellspacing="0">
			<tr>
				<td width="10%">
				    <a id="<%=GeneralConst.REQ_ADD%>" href="javascript:handleAction('_self', document.DataForm, 'add')">
				       <img name="m_add" src="<%=APP_ROOT%>/images/cm-apps-add.gif"  width="48px" height="70px" style="border: none" alt="Add Application">
				    </a>
				</td>
				<td width="10%">
				    <a name="<%=GeneralConst.REQ_EDIT%>" href="javascript:handleAction('_self', document.DataForm, 'edit')">
				       <img name="m_edit" src="<%=APP_ROOT%>/images/cm-apps-edit.gif"  width="48px" height="70px" style="border: none" alt="Edit Application">
				    </a>
				</td>
	      <td width="30%" valign="top" align="center"> 
          <a href="/authentication/index.jsp"> 
            <img name="m_back" src="<%=APP_ROOT%>/images/cm-back.gif" width="48px" height="70px" style="border: none" alt="Security Main Menu">
          </a>
      </td>	        				
			<td width="50%">&nbsp;</td>				
			</tr>
        </table>
		<input name="clientAction" type="hidden">
     </form>

 </body>
</html>
