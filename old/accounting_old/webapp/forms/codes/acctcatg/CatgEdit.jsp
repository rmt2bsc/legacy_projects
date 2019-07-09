<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>General Ledger Account Category Maintenance</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	  <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
  </head>
  
  <body bgcolor="#FFFFCC">
     <h1>General Ledger Account Category Maintenance</h1>
     <form name="MainForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/AcctCategory.Edit">
        <beanlib:InputControl type="hidden" name="AcctCatgId" value="#record.Acctcatid"/>
        <beanlib:InputControl type="hidden" name="AcctTypeId" value="#record.Accttypeid"/>
			 <table  width="50%" border="0"> 
				 <tr>
					 <th width="40%" class="clsTableFormHeader"><div align="right">Id:</div></th>
					 <td>
					    <beanlib:InputControl value="#record.Acctcatid"/>
					 </td>
		       </tr>
				 <tr>
					 <th class="clsTableFormHeader"><div align="right">Account Type:</div></th>
					 <td>
					     <beanlib:InputControl value="#record.Accttypedescr"/>
					 </td>
		       </tr>				  
				  <tr>
					 <th class="clsTableFormHeader"><div align="right">Category Description:</div></th>
					 <td>
					     <beanlib:InputControl type="text" name="Description" value="#record.Acctcatgdescr" size="48%" maxLength="40"/>
					 </td>
				  </tr>
					 <input name="clientAction" type="hidden">
			 </table>
			 <br>
       <table  width="20%" border="0"> 
		      <tr>
			       <td colspan="2">
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
               <input type="button" name="<%=GeneralConst.REQ_SAVE%>" value="Save" class="clsGeneralButton" onClick="handleAction('EditFrame', document.MainForm, this.name)">
               <input type="button" name="<%=GeneralConst.REQ_DELETE%>" value="Delete" class="clsGeneralButton" onClick="handleAction('EditFrame', document.MainForm, this.name)">
               <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" class="clsGeneralButton"  onClick="handleAction('EditFrame', document.MainForm, this.name)">
            </td>
				</tr>
			 </table>
     </form>
  </body>
</html>
