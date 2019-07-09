<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.XactConst" %>
<%@ page import="com.bean.criteria.XactCriteria" %>
<jsp:directive.page import="com.constants.DisbursementsConst"/>
<jsp:directive.page import="com.constants.GeneralConst"/>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Disbursement Transaction Search Criteria</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	<script>
		 function validateAddSelection(_target, _form, _action) {
		   var xactTypeId = SearchForm.qry_XactTypeId.value;
		   if (xactTypeId == null || xactTypeId == "") {
		      alert("Transaction Type must be selected for this operation");
		      return;
		   }
		   handleAction(_target, _form, _action);
		 }
	</script>
  </head>
   
  <%
   	  final int CASH_PAY_XACT = 3;
   	  String pageTitle = "Cash Disbursement Transaction";
   	  String xactCatgCriteria = "xact_category_id = " + CASH_PAY_XACT;
  %>  
	
  <%@include file="/includes/SessionQuerySetup.jsp"%>  	 
  
   <%
      XactCriteria query = (custObj != null && custObj instanceof XactCriteria ? (XactCriteria) baseQueryObj.getCustomObj() : XactCriteria.getInstance());
   %>	 
    
 <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
 <db:datasource id="xactTypeDso" 
                classId="com.api.DataSourceApi" 
	   	          connection="con" 
 			          query="XactTypeView" 
			          where="<%= xactCatgCriteria %>"
			          order="description" type="xml"/>
 
  <body bgcolor="#FFFFFF" text="#000000">
       <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactDisburse.DisbursementSearch">
	     <h3><strong><%=pageTitle%></strong></h3>
		 
		 <!-- Display common selection criteria items  -->
		 <font size="3" style="color:blue">Selection Criteria</font>
		 <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:95%; height:55px">
	     <table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
			   <td width="15%" class="clsTableFormHeader">Transaction Date:</td>
			   <td width="25%" align="left" colspan="3">
			      <gen:CondOps name="qryRelOpXactDate1" selectedValue="<%=query.getQryRelOp_XactDate_1() %>" /> 
				    <input type="text" name="qry_XactDate1" value="<%=query.getQry_XactDate_1() == null ? "" :  query.getQry_XactDate_1() %>">
			   </td>
			   <td colspan="2" align="left">&nbsp;<b>and</b>&nbsp;
			      <gen:CondOps name="qryRelOpXactDate2" selectedValue="<%=query.getQryRelOp_XactDate_2() %>"/>
   				  <input type="text" name="qry_XactDate2" value="<%=query.getQry_XactDate_2() == null ? "" :  query.getQry_XactDate_2() %>">
			   </td>
			</tr>
			<tr>
			   <td class="clsTableFormHeader">Transaction Amount:</td>
			   <td align="left" colspan="3">
			      <gen:CondOps name="qryRelOpXactAmount1" selectedValue="<%=query.getQryRelOp_XactAmount_1() %>"/> 
			      <input type="text" name="qry_XactAmount1" value="<%=query.getQry_XactAmount_1() == null ? "" : query.getQry_XactAmount_1()%>">
			   </td>
			   <td colspan="2" align="left">&nbsp;<b>and</b>&nbsp;
			      <gen:CondOps name="qryRelOpXactAmount2" selectedValue="<%=query.getQryRelOp_XactAmount_2() %>" /> 
			      <input type="text" name="qry_XactAmount2" value="<%=query.getQry_XactAmount_2() == null ? "" : query.getQry_XactAmount_2() %>">
			   </td>
			</tr>	
			<tr>
			   <td class="clsTableFormHeader">Reason:</td>
			   <td colspan="5" align="left">
				   <input type="text" name="qry_Reason" size="80" maxlength="50" value="<%=query.getQry_Reason() == null ? "" : query.getQry_Reason() %>">
			   </td>
			</tr>
	  </table>
	  </div>
	  <!-- Display Search buttons -->  
	  <table>
	    <tr>
			<td colspan="2">
			  <input name="<%=GeneralConst.REQ_SEARCH%>" type="button" value="Search" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
			  <input name="reset" type="reset" value="Clear" style="width:90">
			</td>
		</tr>
	  </table>
	  <br>
	  
	  <font size="3" style="color:blue">Search Results</font>
	  <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:95%; height:350px; overflow:auto">
		 <table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
			 <tr>
				 <th width="4%" class="clsTableListHeader">&nbsp;</th>
				 <th width="10%" class="clsTableListHeader">Id</th>
				 <th width="12%" class="clsTableListHeader">Trans. Date</th>
				 <th width="12%" class="clsTableListHeader" style="text-align:right">Amount</th>
				 <th width="1%" class="clsTableListHeader">&nbsp;</th>
				 <th width="15%" class="clsTableListHeader" style="text-align:left">Date Entered</th>
				 <th width="19%" class="clsTableListHeader" style="text-align:left">Trans. Type</th>				 
				 <th width="32%" class="clsTableListHeader" style="text-align:left">Source</th>
			 </tr>

			 <beanlib:LoopRows bean="beanObj" list="<%=DisbursementsConst.CLIENT_DATA_LIST %>">
			  <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
			 	<td align="center" class="clsTableListHeader"> 
			 	   <beanlib:InputControl dataSource="beanObj" type="radio" name="selCbx" value="rowid"/>
				</td>   
				<td align="center" >
				   <font size="2">
						 <beanlib:InputControl dataSource="beanObj" type="hidden" name="XactId" property="Id" uniqueName="yes"/> 
						 <beanlib:InputControl dataSource="beanObj" type="hidden" name="XactTypeId" property="XactTypeId" uniqueName="yes"/> 
						 <beanlib:ElementValue dataSource="beanObj" property="Id"/>
				   </font>
				</td>             
				<td align="center">
				   <font size="2"> 
				      <beanlib:ElementValue dataSource="beanObj" property="XactDate" format="MM-dd-yyyy"/>
				   </font>
				</td>
				<td align="right">
					<font size="2">
					   <beanlib:ElementValue dataSource="beanObj" property="XactAmount" format="$#,##0.00;($#,##0.00)"/>
					</font>
				</td>
				<td align="right">&nbsp;</td>							 
				<td align="left" >
					<font size="2">
					   <beanlib:ElementValue dataSource="beanObj" property="CreateDate" format="MM-dd-yyyy HH:mm:ss" />
					</font>
				</td>						
				<td align="left" >
					<font size="2">
					   <beanlib:ElementValue dataSource="beanObj" property="XactTypeName"/>
					</font>
				</td>											
				<td align="left" >
					<font size="2">
					   <beanlib:ElementValue dataSource="beanObj" property="Reason"/>
					</font>
				</td>							
			 </tr>
			 </beanlib:LoopRows>

			 <% if (pageContext.getAttribute("ROW") == null) {
				   out.println("<tr><td colspan=9 align=center>Data Not Found</td></tr>");
				}
			 %>
		 </table>	  
	  </div>

	  <table>
	    <tr>
			<td colspan="2">
  			  <input name="<%=GeneralConst.REQ_ADD %>" type="button" value="Add" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
			  <input name="<%=GeneralConst.REQ_EDIT %>" type="button" value="View" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
			  <input name="<%=GeneralConst.REQ_BACK %>" type="button" value="Back" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
			</td>
		</tr>
	  </table>
	  	  
	 <input name="clientAction" type="hidden">
   </form>
   <db:Dispose/>
 </body>
</html>
