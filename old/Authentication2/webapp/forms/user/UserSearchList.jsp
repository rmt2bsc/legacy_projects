<%@include file="/includes/SessionQuerySetup.jsp"%>

<%
  String criteria = baseQueryObj.getWhereClause();
%>

<db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
<db:datasource id="dso" 
               classId="com.api.DataSourceApi" 
               connection="con"
			         query="UserLoginView"
			         where="<%=criteria%>"
			         order="lastname"/>


<form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/User.Search"> 
   <font size="4" style="color:blue">Search Results</font>
   <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:90%; height:350px; overflow:auto">   
      <table  width="100%" border="0" bgcolor="white" bordercolor="#999999" cellpadding="0" cellspacing="0">
		   <tr bgcolor="#FFCC00">
		     <th align="center" width="2%">&nbsp;</th>
		     <th align="left" width="8%">Login</th>
		     <th align="left" width="20%">Last Name</th>
		     <th align="left" width="20%">First Name</th>
		     <th align="center" width="15%">Group</th>
		     <th align="left" width="10%">SSN</th>
		     <th align="left" width="10%">Start Date</th>
		     <th align="left" width="10%">Term Date</th>
		   </tr>
	
	     <db:LoopRows dataSource="dso">
		       <tr>
		         <td bgcolor="#FFCC00">
		             <db:InputControl	type="radio" name="selCbx" value="rowid"/>
		             <db:InputControl	type="hidden"	name="LoginId" value="#dso.LoginId"	uniqueName="yes"/>
		         </td>
		         <td>
		             <db:InputControl value="#dso.LoginId"/>
		             <db:InputControl	type="hidden"	name="Username" value="#dso.Username"	uniqueName="yes"/>
		         </td>		         
		         <td>
		             <db:InputControl value="#dso.Lastname"/>
		         </td>
		         <td>
		             <db:InputControl value="#dso.Firstname"/>
		         </td>
		         <td align="center">
								<db:datasource id="dsoUserGroup" 
               								 classId="com.api.DataSourceApi" 
               								 connection="con"
			         		             query="UserGroupView"
			         		             order="description"/>			         		         
							 <db:Lookup dataSource="" 
													masterCodeName=""
													masterCodeValue="#dso.GrpId"
													type="1"
													lookupSource="dsoUserGroup"
													lookupCodeName="GrpId"
													lookupDisplayName="Description"/>					 		         
		         </td>
		         <td>
		              <db:InputControl value="#dso.Ssn"/>
		         </td>
		         <td>
		             <db:InputControl value="#dso.StartDate" format="MM-dd-yyyy"/>
		         </td>
		         <td>
		             <db:InputControl  value="#dso.TerminationDate" format="MM-dd-yyyy"/>
		         </td>
		       </tr>
		     </db:LoopRows>
		
		     <% if (pageContext.getAttribute("ROW") == null) {
		          out.println("<tr><td colspan=11 align=center>Data Not Found</td></tr>");
		        }
		     %>
		     <input name="clientAction" type="hidden">
		   
		 </table>
    </div>
    <br>
    <table>
	    <tr>
	       <td>
	          <input type="button" name="<%=GeneralConst.REQ_ADD %>" value="Add" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
			      <input type="button" name="<%=GeneralConst.REQ_EDIT %>" value="Edit" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
			      <input type="button" name="<%=GeneralConst.REQ_BACK %>" value="Back" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
	       </td>
	    </tr>
	</table>    
</form>		 