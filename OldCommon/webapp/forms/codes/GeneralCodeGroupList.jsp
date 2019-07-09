<%@ taglib uri="/rmt2-taglib" prefix="db"%>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen"%>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

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
 	  String pageTitle = "General Code Group List";
	%>  
	
  <body bgcolor="#FFFFCC">
     <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
     <db:datasource id="dso" 
                    classId="com.api.DataSourceApi" 
                    connection="con"
                    query="GeneralCodesGroupView"
                    order="description"/>
     <font color="blue">
        <strong><%=pageTitle%></strong>
     </font>
     <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:70%; height:390px; overflow: auto; ">                           
	     <table  width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="white" bordercolor="#999999">
	       <tr bgcolor="#FFCC00">
	         <th width="5%" class="clsTableListHeader">&nbsp;</th>
	         <th width="10%" class="clsTableListHeader">Id</th>
	         <th width="45%" class="clsTableListHeaderLeft">Group Description</th>
	         <th width="20%" class="clsTableListHeader">Update Date</th>
	         <th width="20%" class="clsTableListHeader">Update User</th>
	       </tr>
	       <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/GeneralCodeGroup.GeneralCodeGroupList">
	         <db:LoopRows dataSource="dso">
				     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
	             <td align="center" bgcolor="#FFCC00">
	                 <db:InputControl dataSource="dso" type="checkbox" name="Id" property="Id"/>
	             </td>
	             <td class="clsTableListDataCenter">
	                 <db:ElementValue dataSource="dso" property="Id"/>
	             </td>
	             <td class="clsTableListData">
	                 <db:ElementValue dataSource="dso" property="Description"/>           
	             </td>
	             <td class="clsTableListDataCenter">
	                <db:ElementValue dataSource="dso" property="DateUpdated" format="MM-dd-yyyy"/>
	             </td>
	             <td class="clsTableListDataCenter">
	                <db:ElementValue dataSource="dso" property="UserId"/>
	             </td>
	           </tr>
	         </db:LoopRows>
	         <input name="clientAction" type="hidden">
	       </form>
	     </table>
     </div>
     
     <br>
     <!-- Display command buttons -->
     <table width="100%" cellpadding="0" cellspacing="0">
		 	 <tr>
				 <td colspan="2">
					 <input name="<%=GeneralConst.REQ_ADD%>" type="button" value="Add" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
           <input name="<%=GeneralConst.REQ_EDIT%>" type="button" value="Edit" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">							
					 <input name="<%=GeneralConst.REQ_DELETE%>" type="button" value="Delete" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
					 <input name="<%=GeneralConst.REQ_DETAILS%>" type="button" value="Codes" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
				 </td>
			 </tr>
     </table>
     <db:Dispose/>
  </body>
</html>
