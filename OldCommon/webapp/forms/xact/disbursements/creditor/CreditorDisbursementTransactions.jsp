<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.bean.Business" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.XactConst" %>
<%@ page import="com.constants.AccountingConst" %>

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
  </head>
   
  <%
   	  final int CASH_PAY_XACT = 3;
   	  Business bus = request.getAttribute(GeneralConst.CLIENT_DATA_BUSINESS) == null ? new Business() : (Business) request.getAttribute(GeneralConst.CLIENT_DATA_BUSINESS);
   	  String pageTitle = "Creditor/Vendor Cash Disbursement Transactions";
   	  String listTitle = "List results for: <strong>" + bus.getLongname() + "</string>";
   	  String xactCatgCriteria = "xact_category_id = " + CASH_PAY_XACT;
  %>  
	
  <%@include file="/includes/SessionQuerySetup.jsp"%>  	 
    
 <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
 <db:datasource id="xactTypeDso" 
                classId="com.api.DataSourceApi" 
	   	          connection="con" 
	              query="XactTypeView" 
                where="<%= xactCatgCriteria %>"
                order="description" type="xml"/>
 
  <body bgcolor="#FFFFFF" text="#000000">
    <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactDisburse.DisbursementCreditorXactList">
      <beanlib:InputControl dataSource="<%=AccountingConst.CLIENT_DATA_CREDITOR%>" type="hidden" name="CreditorId" property="Id"/>
      <h3><strong><%=pageTitle%></strong></h3>
		  <font size="3" style="color:blue"><%=listTitle%></font>
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
	
				 <beanlib:LoopRows bean="beanObj" list="<%=GeneralConst.CLIENT_DATA_LIST %>">
				  <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
				 	<td align="center" class="clsTableListHeader" style="vertical-align:middle"> 
				 	   <beanlib:InputControl dataSource="beanObj" type="radio" name="selCbx" value="rowid"/>
					</td>   
					<td align="center" >
					   <font size="2">
							 <beanlib:InputControl dataSource="beanObj" type="hidden" name="XactId" property="XactId" uniqueName="yes"/> 
							 <beanlib:InputControl dataSource="beanObj" type="hidden" name="XactTypeId" property="XactTypeId" uniqueName="yes"/> 
							 <beanlib:ElementValue dataSource="beanObj" property="XactId"/>
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
						   <beanlib:ElementValue dataSource="beanObj" property="DateCreated" format="MM-dd-yyyy HH:mm:ss" />
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
				  <input name="<%=XactConst.REQ_VIEW %>" type="button" value="View" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
				  <input name="<%=GeneralConst.REQ_BACK %>" type="button" value="Back" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
				</td>
			</tr>
		  </table>
		  	  
		 <input name="clientAction" type="hidden">
   </form>
   <db:Dispose/>
 </body>
</html>
