<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen"%>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

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
		 RMT2TagQueryBean query = (RMT2TagQueryBean) session.getAttribute(RMT2ServletConst.QUERY_BEAN);
     String groupId = (String) query.getKeyValues("group_id");
     String groupCriteria = "id = " + groupId;
   %>												  
  <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>

  <db:datasource id="dso" 
                 classId="com.api.DataSourceApi" 
                 connection="con"
 								 query="GeneralCodesView"
 								 queryId="<%=RMT2ServletConst.QUERY_BEAN%>"
								 order="longdesc"/>

  <db:datasource id="groupDso" 
		        		 classId="com.api.DataSourceApi" connection="con"
								 query="GeneralCodesGroupView"
								 where="<%=groupCriteria%>"
								 order="description"
								 type="xml"/>
  
  <body bgcolor="#FFFFCC">
	   <font color="blue">
	     <db:LoopRows dataSource="groupDso">
          <strong>General Code Listing for: <db:ElementValue dataSource="groupDso" property="Description"/></strong>
       </db:LoopRows>    	   
     </font>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/GeneralCode.GeneralCodeList">
	     <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:90%; height:490px; overflow: auto; ">                           
		     <table  width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="white" bordercolor="#999999">
		       <tr bgcolor="#FFCC00">
		         <th width="2%" class="clsTableListHeader">&nbsp;</th>
		         <th width="12%" class="clsTableListHeader">Code Id</th>
		         <th width="12%" class="clsTableListHeader">Group Id</th>
		         <th width="12%" class="clsTableListHeaderLeft">Short Name</th>
		         <th width="38%" class="clsTableListHeaderLeft">Long Name</th>
		         <th width="12%" class="clsTableListHeader">Update Date</th>
		         <th width="12%" class="clsTableListHeader">Update User</th>
		       </tr>
		 
		       <db:LoopRows dataSource="dso">
		         <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
		           <td bgcolor="#FFCC00" align="center">
		             <db:InputControl dataSource="dso" type="checkbox" name="Id" property="Id"/>
		           </td>
		           <td class="clsTableListDataCenter">
		             <db:ElementValue dataSource="dso" property="Id"/>
		             <db:InputControl dataSource="dso" type="hidden" name="GroupId" property="GroupId"/>
		           </td>
		           <td class="clsTableListDataCenter">
		             <db:ElementValue dataSource="dso" property="GroupId"/>
		           </td>
		           <td class="clsTableListData">
		             <db:ElementValue dataSource="dso" property="Shortdesc"/>             
		           </td>
		           <td class="clsTableListData">
		             <db:ElementValue dataSource="dso" property="Longdesc"/>             
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
						 <input name="<%=GeneralConst.REQ_ADD%>" type="button" value="Add" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
	           <input name="<%=GeneralConst.REQ_EDIT%>" type="button" value="Edit" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">							
						 <input name="<%=GeneralConst.REQ_DELETE%>" type="button" value="Delete" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
					 </td>
				 </tr>
	     </table>	     
    </form>
    <db:Dispose/>
  </body>
</html>
