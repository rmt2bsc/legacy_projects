<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen"%>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<html>
  <title>General Codes Group Maintenance </title>
  <head>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
  	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
  	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>      
  </head>

   <% 
     String pageTitle = "General Code Listing";
   %>												  

  <body bgcolor="#FFFFCC">
     <h3><%=pageTitle%></h3>
	   <font color="blue">
          <strong>Code Listing for group: <beanlib:InputControl value="#data.Description"/></strong>
     </font>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/GeneralCode.GeneralCodeList">
       <beanlib:InputControl name="CodeGrpId" type="hidden" value="#data.CodeGrpId"/>
	     <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:50%; height:690px; overflow: auto; ">                           
		     <table  width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="white" bordercolor="#999999">
		       <tr bgcolor="#FFCC00">
		         <th width="2%" class="clsTableListHeader">&nbsp;</th>
		         <th width="8%" class="clsTableListHeader">Code Id</th>
		         <th width="10%" class="clsTableListHeader">Group Id</th>
		         <th width="18%" class="clsTableListHeaderLeft">Short Name</th>
		         <th width="38%" class="clsTableListHeaderLeft">Long Name</th>
		         <th width="12%" class="clsTableListHeader">Update Date</th>
		         <th width="12%" class="clsTableListHeader">Update User</th>
		       </tr>
		 
		       <beanlib:LoopRows bean="dso" list="<%=GeneralConst.CLIENT_DATA_LIST %>">
		         <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
		           <td bgcolor="#FFCC00" align="center">
		             <beanlib:InputControl type="radio" name="CodeId" value="#dso.CodeId"/>
		           </td>
		           <td class="clsTableListDataCenter">
		             <beanlib:InputControl value="#dso.CodeId"/>
		           </td>
		           <td class="clsTableListDataCenter">
		             <beanlib:InputControl value="#dso.CodeGrpId"/>
		           </td>
		           <td class="clsTableListData">
		             <beanlib:InputControl value="#dso.Shortdesc"/>             
		           </td>
		           <td class="clsTableListData">
		             <beanlib:InputControl value="#dso.Longdesc"/>             
		           </td>
		           <td class="clsTableListDataCenter">
		             <beanlib:InputControl value="#dso.DateUpdated" format="MM-dd-yyyy"/>
		           </td>
		           <td class="clsTableListDataCenter">
		             <beanlib:InputControl value="#dso.UserId"/>
		           </td>
		         </tr>
		       </beanlib:LoopRows>
		       <input name="clientAction" type="hidden">
		       
		       <% if (pageContext.getAttribute("ROW") == null) {
		            out.println("<tr><td colspan=7 align=center>Data Not Found</td></tr>");
		          }
		       %>
		     </table>
	     </div>
	     <br>
	     <!-- Display command buttons -->
	     <table width="100%" cellpadding="0" cellspacing="0">
		 	 <tr>
				 <td colspan="2">
					 <input name="<%=GeneralConst.REQ_ADD%>" type="button" value="Add" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
		       <input name="<%=GeneralConst.REQ_EDIT%>" type="button" value="Edit" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">							
					 <input name="<%=GeneralConst.REQ_BACK%>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
				 </td>
			 </tr>
	     </table>	     
    </form>
    <db:Dispose/>
  </body>
</html>
