<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.CustomerCombine" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<jsp:useBean id="QUERY_BEAN" scope="session" class="com.bean.RMT2TagQueryBean"/>

<html>
  <head>
    <title>General Ledger Customer Account Maintenance Listing</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
    <script>
      function handleAction2(_action) {
        this.DataForm.clientAction.value = _action;
        this.DataForm.target = "EditFrame";
        this.DataForm.submit();
      }
    
      function executeList(_target, _form, _action) {
	 	var obj = DataForm.selCbx;  
		var row = null;
		var msg = "A row must be selected for this operation";
		
		if (obj == null) {
		   alert(msg);
		   return;
		}
		for (ndx = 0;ndx < obj.length; ndx++) {
		   if (obj[ndx].checked) {
		      row = obj[ndx].value;
		      break;
		   }
		} 
		if (row == null || row == "undefined") {
		   alert(msg);
		   return;
		}
		handleAction(_target, _form, _action);
	  }	  
  </script>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	
  </head>

     <%
		 CustomerCombine cc = (CustomerCombine) QUERY_BEAN.getCustomObj();
		 String criteria =  QUERY_BEAN.getWhereClause();
		 String queryView = QUERY_BEAN.getQuerySource();
         String customerType = cc.getCustomerType() == null ?  "" :  cc.getCustomerType();		
		 String requestType =  request.getParameter("requestType") ;
    %>

  <db:connection id="con" classId="com.bean.RMT2DBConnectionBean"/>
  <db:datasource id="dso" 
				classId="com.api.DataSourceApi" 
				connection="con"
				query="<%=queryView%>"
				where="<%=criteria%>"
				order="name"
				type="xml"/>    
  
  <body>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/glcustomerservlet">
			 <table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
			     <caption align="left" style="color:blue">Customer List</caption>
				 <tr>
					 <th class="clsTableListHeader">&nbsp;</th>
					 <th class="clsTableListHeader"><div align="center">Account Number</div></th>
					 <th class="clsTableListHeader"><div align="left">Name </div></th>
					 <th class="clsTableListHeader"><div align="left">Credit Limit</div></th>
					 <th class="clsTableListHeader">Create Date</th>
					 <th class="clsTableListHeader">Update Date</th>
					 <th class="clsTableListHeader">Update User</th>
				 </tr>
   
				 <db:LoopRows dataSource="dso">
					  <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>

					 <td width="1%" align="center" class="clsTableListHeader">
						 <db:InputControl dataSource="dso" type="radio" name="selCbx" value="rowid"/>
						 <db:InputControl dataSource="dso" type="hidden" name="CustomerId" property="CustomerId" uniqueName="yes"/>         
						 <% 
							if (customerType.equals("1")) {
						  %>                         
							 <db:InputControl dataSource="dso" type="hidden" name="BusinessId" property="BusinessId" uniqueName="yes"/>                            
						  <%
							 }
						  %>
						 <% 
							if (customerType.equals("2")) {
						  %>                         
							 <db:InputControl dataSource="dso" type="hidden" name="PersonId" property="PersonId" uniqueName="yes"/>
						  <%
							 }
						  %>								  

						 <db:InputControl dataSource="dso" type="hidden" name="GlAccountId" property="GlAccountId" uniqueName="yes"/>                                  
					 </td>   
					 <td width="10%" align="center" >
						<font size="2">
						  <db:ElementValue dataSource="dso" property="AccountNo"/>
						</font>
					 </td>             
					 <td width="22%">
						<font size="2"> 
						  <db:ElementValue dataSource="dso" property="Name"/>
						  </font>
					 </td>
					 <td width="10%">
						 <font size="2">
						<db:ElementValue dataSource="dso" property="CreditLimit" format="$#,##0.00;($#,##0.00)"/>
						</font>
					 </td>
					 <td width="9%" align="center">
						 <font size="2">
						<db:ElementValue dataSource="dso" property="DateCreated" format="MM-dd-yyyy"/>
						</font>
					 </td>
					 <td width="9%" align="center" >
					 <font size="2">
						<db:ElementValue dataSource="dso" property="DateUpdated" format="MM-dd-yyyy"/>
						</font>
					 </td>
					<td width="7%" align="center" >
					<font size="2">
					  <db:ElementValue dataSource="dso" property="UserId" />
					  </font>
				   </td>
				 </tr>
		   </db:LoopRows>
		   <input name="requestType" type="hidden" value=<%=requestType%>>
  		   <input name="clientAction" type="hidden">

		 <% if (pageContext.getAttribute("ROW") == null) {
					out.println("<tr><td colspan=7 align=center>Data Not Found</td></tr>");
				}
		 %>
	 </table>
	 <table>
		<tr>
			<td><img src="images/clr.gif" height="10"></td>
			<td><img src="images/clr.gif" height="10"></td>
			<td><img src="images/clr.gif" height="10"></td>
		</tr>
<!--				
			    <tr>
				  <td><input type="button" name="edit" value="Edit" class="clsButton" onClick="handleAction(MAIN_DETAIL_FRAME, DataForm, this.name)"></td>
				  <td><input type="button" name="add" value="Add" class="clsButton" onClick="handleAction(MAIN_DETAIL_FRAME, DataForm, this.name)"></td>
				  <td><input type="button" name="delete" value="Delete" class="clsButton" onClick="handleAction(MAIN_DETAIL_FRAME, DataForm, this.name)"></td>
				</tr>
-->				
			 </table>
			 
     </form>
  </body>
</html>
