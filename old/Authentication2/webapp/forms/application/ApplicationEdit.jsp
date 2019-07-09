<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.bean.Application" %>
<%@ page import="com.api.security.authentication.AuthenticationConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Application Edit</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>
  
   <%
  	  String pageTitle = "Security - Application Edit";    
	%>
	
  <body bgcolor="#FFFFFF" text="#000000">
       <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/App.Edit">
		  <h3><strong><%=pageTitle%></strong></h3>
  		<br>
	  
		<table width="50%" border="0" cellspacing="2" cellpadding="0">
				<tr> 
					 <td width="25%" class="clsTableFormHeader">
						   <font size="3"><b>Application Id</b></font>
					 </td>
					 <td align="left" width="75%" >
					     <beanlib:InputControl type="hidden" name="AppId" value="#record.AppId"/>                                  
					 	   <beanlib:InputControl value="#record.AppId"/>                                  
					 </td>
				</tr>
				<tr> 
					 <td class="clsTableFormHeader">
						<font size="3"><b>Name</b></font>
					 </td>
					 <td align="left">
						<beanlib:InputControl type="text" name="Name" value="#record.Name" size="60"/>                                  
					 </td>
				</tr>				
				<tr> 
					 <td class="clsTableFormHeader">
						<font size="3"><b>Description</b></font>
					 </td>
					 <td align="left">
						<beanlib:InputControl type="text" name="Description" value="#record.Description" size="60"/>                                  
					 </td>
				</tr>
				<tr> 
					 <td class="clsTableFormHeader">
						<font size="3"><b>Status?</b></font>
					 </td>
					 <td align="left">
					     <table width="50%" border="0">
					        <tr>
					            <th>Yes</th>
					            <td>
					                <input type="radio" name="Active" value="1"
						                <gen:Evaluate  expression="#record.Active">
														     <gen:When expression="1">
															       checked
														      </gen:When>
													   </gen:Evaluate>
												   >
					            </td>
								<th>No</th>
					            <td>
					                <input type="radio" name="Active" value="0" 
  						               <gen:Evaluate  expression="#record.Active">
														     <gen:When expression="0">
															       checked
														      </gen:When>
													   </gen:Evaluate>
												  >
					            </td>					            
					        </tr>
					     </table>
					 </td>
				</tr>
		</table>
		
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
		 
        <!-- Display command links -->
        <table width="50%" cellpadding="0" cellspacing="0">
			<tr>
				<td width="10%">
				    <a id="<%=GeneralConst.REQ_SAVE%>" href="javascript:handleAction('_self', document.DataForm, 'save')">
				       <img name="m_add" src="<%=APP_ROOT%>/images/cm-apps-save.gif"  width="48px" height="70px" style="border: none" alt="Save Application">
				    </a>
				</td>
				<td width="10%">
				    <a id="<%=GeneralConst.REQ_DELETE%>" href="javascript:handleAction('_self', document.DataForm, 'delete')">
				       <img name="m_delete" src="<%=APP_ROOT%>/images/cm-apps-delete.gif"  width="48px" height="70px" style="border: none" alt="Delete Application">
				    </a>
				</td>								
	      <td width="10%" valign="top" align="center"> 
          <a  href="javascript:handleAction('_self', document.DataForm, 'back')"> 
            <img name="m_back" src="<%=APP_ROOT%>/images/cm-back.gif" width="48px" height="70px" style="border: none" alt="Back to Application List">
          </a>
      </td>	        				
			<td width="80%">&nbsp;</td>				
			</tr>
        </table>
	    <input name="clientAction" type="hidden">
   </form>
 </body>
</html>
