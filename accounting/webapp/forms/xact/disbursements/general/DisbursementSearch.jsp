<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.xact.XactConst" %>
<%@ page import="com.bean.criteria.XactCriteria" %>
<%@ page import="com.xact.disbursements.DisbursementsConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Cash Disbursement Search</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
		<script type='text/javascript' language='javascript' src="<%=APP_ROOT%>/js/datetimepicker.js"></script>
	<script>
		 function validateAddSelection(_target, _form, _action) {
		   var xactTypeId = SearchForm.qry_XactTypeId.value;
		   if (xactTypeId == null || xactTypeId == "") {
		      alert("Transaction Type must be selected for this operation");
		      return;
		   }
		   handleAction(_target, _form, _action);
		 }
		 
		 function initPage() {
				showItemFields();	
 		 }
 		 
 		 function showItemFields() {
 	 		 if (document.SearchForm.qry_PresentationType[0].checked) {
 	 				document.getElementById("xactTypeItemId").style.visibility = "hidden";
 	 				document.getElementById("ItemName").style.visibility = "hidden";
 	 				document.getElementById("ItemAmountOp1").style.visibility = "hidden";
 	 				document.getElementById("ItemAmount1").style.visibility = "hidden";
 	 				document.getElementById("ItemAmountOp2").style.visibility = "hidden";
 	 				document.getElementById("ItemAmount2").style.visibility = "hidden";
 	 				document.getElementById("ItemAmountAnd").style.visibility = "hidden";
 	 				document.getElementById("xactTypeItemId").disabled = true;
 	 				document.getElementById("ItemName").disabled = true;
 	 				document.getElementById("ItemAmountOp1").disabled = true;
 	 				document.getElementById("ItemAmount1").disabled = true;
 	 				document.getElementById("ItemAmountOp2").disabled = true;
 	 				document.getElementById("ItemAmount2").disabled = true;
 	 		 }
			 if (document.SearchForm.qry_PresentationType[1].checked) {
				 document.getElementById("xactTypeItemId").style.visibility = "visible";
	 			 document.getElementById("ItemName").style.visibility = "visible";
	 			 document.getElementById("ItemAmountOp1").style.visibility = "visible";
	 			 document.getElementById("ItemAmount1").style.visibility = "visible";
	 			 document.getElementById("ItemAmountOp2").style.visibility = "visible";
	 			 document.getElementById("ItemAmount2").style.visibility = "visible";
	 			 document.getElementById("ItemAmountAnd").style.visibility = "visible";
    		 document.getElementById("xactTypeItemId").disabled = false;
	 			 document.getElementById("ItemName").disabled = false;
	 			 document.getElementById("ItemAmountOp1").disabled = false;
	 			 document.getElementById("ItemAmount1").disabled = false;
	 			 document.getElementById("ItemAmountOp2").disabled = false;
	 			 document.getElementById("ItemAmount2").disabled = false;
			 }
 		 }
	</script>
  </head>
   
  <%
   	  final int CASH_PAY_XACT = 3;
   	  String pageTitle = "Cash Disbursement Search";
   	  String xactCatgCriteria = "xact_catg_id = " + CASH_PAY_XACT;
  %>  
	
  <%@include file="/includes/SessionQuerySetup.jsp"%>  	 
  
   <%
      XactCriteria query = (custObj != null && custObj instanceof XactCriteria ? (XactCriteria) baseQueryObj.getCustomObj() : XactCriteria.getInstance());
   %>	 
    
 
  <body bgcolor="#FFFFFF" text="#000000" onLoad="initPage()"> 
       <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/DisbursementsGeneral.Search">
	     <h3><strong><%=pageTitle%></strong></h3>
		 
		 <!-- Display common selection criteria items  -->
		 <font size="3" style="color:blue">Selection Criteria - Default criteria includes transactions of current date and 30 days before</font>
		 <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:75%; height:55px">
	     <table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
			   <td width="15%" class="clsTableFormHeader">Transaction Date:</td>
			   <td align="left" colspan="4">
			      <gen:CondOps name="qryRelOpXactDate1" selectedValue="#QUERY_BEAN.CustomObj.qryRelOp_XactDate_1" /> 
				    <beanlib:InputControl type="text" name="qry_XactDate1" value="#QUERY_BEAN.CustomObj.qry_XactDate_1"/>
				    <a href="javascript:NewCal('qry_XactDate1','mmddyyyy')"><img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>
				    &nbsp;<b>and</b>&nbsp;
				    <gen:CondOps name="qryRelOpXactDate2" selectedValue="#QUERY_BEAN.CustomObj.qryRelOp_XactDate_2"/>
   				  <beanlib:InputControl type="text" name="qry_XactDate2" value="#QUERY_BEAN.CustomObj.qry_XactDate_2"/>
   				  <a href="javascript:NewCal('qry_XactDate2','mmddyyyy')"><img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>
			   </td>
			   <td align="left">&nbsp;</td>
			</tr>
			<tr>
			   <td class="clsTableFormHeader">Transaction Amount:</td>
			   <td align="left" colspan="4">
			      <gen:CondOps name="qryRelOpXactAmount1" selectedValue="#QUERY_BEAN.CustomObj.qryRelOp_XactAmount_1"/> 
			      <beanlib:InputControl type="text" name="qry_XactAmount1" value="#QUERY_BEAN.CustomObj.qry_XactAmount_1"/>
			      &nbsp;<b>and</b>&nbsp;
			      <gen:CondOps name="qryRelOpXactAmount2" selectedValue="#QUERY_BEAN.CustomObj.qryRelOp_XactAmount_2" /> 
			      <beanlib:InputControl type="text" name="qry_XactAmount2" value="#QUERY_BEAN.CustomObj.qry_XactAmount_2"/>
			   </td>
			   <td align="left">&nbsp;</td>
			</tr>	
			<tr>
			   <td class="clsTableFormHeader">Source/Reason:</td>
			   <td align="left" colspan="2">
				   <beanlib:InputControl type="text" name="qry_Reason" size="75" maxLength="50" value="#QUERY_BEAN.CustomObj.qry_Reason"/>
			   </td>
			   <td align="left">Advanced Search Options:</td>
			   <td align="left">
			      <gen:SearchOptions fieldName="qry_Reason" selectedValue="#QUERY_BEAN.CustomObj.qry_Reason_ADVSRCHOPTS"/>
			   </td>
			   <td align="left">&nbsp;</td>
			</tr>
			<tr>
			   <td class="clsTableFormHeader">Confirmation Number:</td>
			   <td align="left" colspan="4">
				   <beanlib:InputControl type="text" name="qry_ConfirmNo" size="40" maxLength="20" value="#QUERY_BEAN.CustomObj.qry_ConfirmNo"/>
			   </td>
			</tr>
			<tr>
			   <td class="clsTableFormHeader">Presentation Type:</td>
			   <td align="left">
				   <beanlib:InputControl type="radio" 
				                         name="qry_PresentationType"
				                         value="1" 
				                         checkedValue="#QUERY_BEAN.CustomObj.qry_PresentationType"
				                         onClick="showItemFields()"/>&nbsp;Transactions&nbsp;&nbsp;

				   <beanlib:InputControl type="radio" 
				                         name="qry_PresentationType" 
				                         value="2" 
				                         checkedValue="#QUERY_BEAN.CustomObj.qry_PresentationType"
				                         onClick="showItemFields()"/>&nbsp;Items				                         
			   </td>
			   <td colspan="4">&nbsp;</td>
			</tr>
			<tr>
			   <td class="clsTableFormHeader">Expense Type:</td>
			   <td colspan="5" align="left">
			   <beanlib:InputControl dataSource="<%=XactConst.CLIENT_DATA_XACTITEMTYPELIST%>" 
	                             id="xactTypeItemId"
															 type="select" 
															 name="qry_XactTypeItemId" 
															 codeProperty="XactItemId" 
															 displayProperty="Name" 
															 selectedValue="#QUERY_BEAN.CustomObj.qry_XactTypeItemId"/>
			   </td>
			</tr>
			<tr>
			   <td class="clsTableFormHeader">Transaction Item:</td>
			   <td colspan="5" align="left">
				   <beanlib:InputControl type="text" id="ItemName" name="qry_ItemName"  value="#QUERY_BEAN.CustomObj.qry_ItemName"/>
			   </td>
			</tr>
			<tr>
			   <td class="clsTableFormHeader">Transaction Item Amount:</td>
			   <td align="left" colspan="4">
			      <gen:CondOps id="ItemAmountOp1" name="qryRelOpItemAmount1" selectedValue="#QUERY_BEAN.CustomObj.qryRelOp_ItemAmount_1"/> 
			      <beanlib:InputControl id="ItemAmount1" type="text" name="qry_ItemAmount1" value="#QUERY_BEAN.CustomObj.qry_ItemAmount_1"/>
			      <span id="ItemAmountAnd"><b>&nbsp;and&nbsp;</b></span>
			      <gen:CondOps id="ItemAmountOp2" name="qryRelOpItemAmount2" selectedValue="#QUERY_BEAN.CustomObj.qryRelOp_ItemAmount_2" /> 
			      <beanlib:InputControl id="ItemAmount2" type="text" name="qry_ItemAmount2" value="#QUERY_BEAN.CustomObj.qry_ItemAmount_2"/>
			   </td>
			   <td align="left">&nbsp;</td>
			</tr>
	  </table>
	  </div>
	  <!-- Display Search buttons -->  
	  <table>
	    <tr>
			<td colspan="2">
			  <input name="<%=GeneralConst.REQ_SEARCH%>" type="button" value="Search" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
			  <input name="<%=GeneralConst.REQ_RESET%>" type="reset" value="Clear" style="width:90">
			</td>
		</tr>
	  </table>
	  <br>
	  
	  <font size="3" style="color:blue">Search Results</font>
	  <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:75%; height:390px; overflow:auto">
	  <gen:Evaluate expression="#QUERY_BEAN.CustomObj.qry_PresentationType">
	  		<gen:When expression="1">
	  		   <%@include file="DisbursementByTransactionList.jsp"%>
	  		</gen:When>
	  		<gen:When expression="2">
	  		   <%@include file="DisbursementByItemsList.jsp"%>
	  		</gen:When>
	  </gen:Evaluate>
		    
	  </div>

    <!--  Display icon legend, if applicable. -->
    <gen:Evaluate expression="#QUERY_BEAN.CustomObj.qry_PresentationType">
	  		<gen:When expression="1">
	  		   <img src="<%=APP_ROOT%>/images/camera2.png" style="border: none"/><font color="black" size="1">View image of receipt</font>
	  		</gen:When>
	  		<gen:When expression="2">&nbsp;</gen:When>
	  </gen:Evaluate>
    
    	  
		<!-- Display any messgaes -->
		<table>
				<tr>
				  <td colspan="4">
				     <font color="red">
					     <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
					   </font>
				  </td>
				</tr>
				<tr>
				  <td>
				     <font color="black">Total Items Found:&nbsp;&nbsp;</font>
				  </td>
				  <td>
				     <font color="red">
					      <beanlib:InputControl value="#TotalCount" format="#,##0"/>
					   </font>
				  </td>
				  <td>
				     <font color="black">&nbsp;&nbsp;&nbsp;&nbsp;Total Amount Spent:&nbsp;&nbsp;</font>
				  </td>
				  <td>
				     <font color="red">
					      <beanlib:InputControl value="#TotalAmount" format="$#,##0.00"/>
					   </font>
				  </td>
				</tr>
		</table>
	  <table>
	    <tr>
			<td colspan="2">
  			<input name="<%=GeneralConst.REQ_ADD %>" type="button" value="Add" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
			  <input name="<%=GeneralConst.REQ_EDIT %>" type="button" value="View" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
			  <input name="<%=GeneralConst.REQ_BACK %>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
			</td>
		</tr>
	  </table>
	  	  
	 <input name="clientAction" type="hidden">
   </form>
 </body>
</html>
