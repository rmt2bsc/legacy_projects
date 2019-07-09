<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.bean.VwAccount" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>General Ledger Account Maintenance</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
		<script>
    		function handleAction2(action) {
		      this.DataForm.clientAction.value = action;
    		  this.DataForm.target = "EditFrame";
		      this.DataForm.submit();
    		}
    </script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
  </head>
 
 <% 
   VwAccount acct = (VwAccount) request.getAttribute(GeneralConst.CLIENT_DATA_RECORD);
 %>
  <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
  <db:datasource id="balTypeDso" 
                 classId="com.api.DataSourceApi" 
                 connection="con"
 							   query="GlBalanceTypeView"
								 order="long_desc"
								 type="xml"/>												    
  
  <body bgcolor="#FFFFCC">
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/Account.Edit">
		     <beanlib:InputControl type="hidden" name="AcctId" value="#record.Id"/>
		     <beanlib:InputControl type="hidden" name="AcctTypeId" value="#record.AcctTypeId"/>
		     <beanlib:InputControl type="hidden" name="AcctCatgId" value="#record.AcctCatId"/>
				 
				 <h1>General Ledger Account Maintenance</h1>
				 <table  width="100%" border="0"> 
					 <tr>
						 <td width="13%" class="clsTableFormHeader">Id:</td>
						 <td width="87%">
						     <beanlib:InputControl value="#record.Id"/>
						 </td>
					 </tr>  
					 <tr>
						 <td class="clsTableFormHeader">Account Type:</td>
						 <td>
						    <beanlib:InputControl value="#record.Accttypedescr"/>
						 </td>
					 </tr>
					 <tr>
						 <td class="clsTableFormHeader">Account Category:</td>
						 <td>
						    <beanlib:InputControl value="#record.Acctcatgdescr"/>
						 </td>
					 </tr>
					 <tr>
						 <td class="clsTableFormHeader">Account Number:</td>
						 <td>
						    <beanlib:InputControl value="#record.AcctNo"/>
						 </td>
					 </tr>			 			 			 
					 <tr>
						 <td class="clsTableFormHeader">Account Name:</td>
						 <td>
						     <beanlib:InputControl type="text" name="Name" value="#record.Name" size="85" maxLength="100"/>
						  </td>
					 </tr>
					 <tr>
						 <td class="clsTableFormHeader">Account Code:</td>
						 <td>
						     <beanlib:InputControl type="text" name="Code" value="#record.Code" size="20" maxLength="15"/>
						  </td>
					 </tr>					 
					 <tr>
						 <td class="clsTableFormHeader">Account Description:</td>
						 <td>
						     <beanlib:InputControl type="text" name="Description" value="#record.Description" size="125" maxLength="255"/>
						  </td>
					 </tr>			 
					 <tr>
						 <td class="clsTableFormHeader">Balance Type:</td>
						 <td>
						     <db:InputControl dataSource="balTypeDso"
															   type="select"
															   name="AcctBaltypeId"
															   codeProperty="AcctBaltypeId"
															   displayProperty="LongDesc"
															   selectedValue="#record.BalanceTypeId"/>	
						  </td>
					 </tr>
					 <tr>
						  <td colspan=2">
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
						 </td>
					 </tr>
					 <tr>
						<td colspan="2">
						   <input type="button" name="<%=GeneralConst.REQ_SAVE%>" value="Save" class="clsGeneralButton" onClick="handleAction('EditFrame', document.DataForm, this.name)">
						   <input type="button" name="<%=GeneralConst.REQ_DELETE%>" value="Delete" style=width:90 onClick="handleAction2(this.name)">
		           <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" class="clsGeneralButton"  onClick="handleAction('EditFrame', document.DataForm, this.name)">						   
						</td>
					 </tr>   
					 </table>
					 <input type="hidden" name="clientAction">
     </form>
     <db:Dispose/>
  </body>
</html>
