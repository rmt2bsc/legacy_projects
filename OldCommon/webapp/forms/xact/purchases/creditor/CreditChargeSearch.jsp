<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.criteria.CreditChargeCriteria" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.AccountingConst" %>
<%@ page import="com.constants.CreditChargesConst" %>
<%@ page import="com.constants.XactConst" %>


<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>General Credit Charge Transaction Search Criteria</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>

  <%
	  String pageTitle = "Expense Credit Purchase Console";
	  String creditorCriteria = "creditor_type_id = " + AccountingConst.CREDITOR_TYPE_CREDITOR;
  %>

  <%@include file="/includes/SessionQuerySetup.jsp"%>

  <%
	  CreditChargeCriteria query = (custObj != null && custObj instanceof CreditChargeCriteria ? (CreditChargeCriteria) baseQueryObj.getCustomObj() : CreditChargeCriteria.getInstance());
  %>

 <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
 <db:datasource id="creditorDso"
                classId="com.api.DataSourceApi"
				connection="con"
				query="VwCreditorBusinessView"
				where="<%= creditorCriteria %>"
				order="longname"
				type="xml"/>

  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

     <!--  Begin Search Criteria section -->
     <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactPurchase.CreditorSearch">
        <input type="hidden" name="XactTypeId" value="<%=XactConst.XACT_TYPE_CREDITCHARGE%>">
        <font size="4" style="color:blue">Selection Criteria</font>
        <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:90%; height:120px">
            <table width="100%" border="1" cellpadding="0" cellspacing="2">

               <!-- Begin Vendor Criteria Seciont -->
               <tr>
                  <td>
                      <table border="0" cellpadding="0" cellspacing="0">
                         <tr>
							 <td width="15%" class="clsTableFormHeader">Creditor Account Number</td>
							 <td width="35%">
								<input type="text" name="qry_AccountNo" value="<%=query.getQry_AccountNo()%>">
							 </td> 
							 <td width="15%" class="clsTableFormHeader">Creditor</td>
							 <td width="35%">
								<db:InputControl dataSource="creditorDso"
												 type="select"
												 name="qry_CreditorId"
												 codeProperty="CreditorId"
												 displayProperty="Longname"
												 selectedValue="<%=query.qetQry_CreditorId()%>"/>
							 </td>
					     </tr>
						 <tr>
							<td class="clsTableFormHeader">Tax Id</td>
							<td>
				 				<input type="text" name="qry_TaxId" size="20" value="<%=query.getQry_TaxId()%>">
							</td>
							<td colspan="2">&nbsp;</td>
						 </tr>
                      </table>
                  </td>
               </tr>

               <!-- Begin Credit Charge Criteria Seciont -->
               <tr>
                   <td>
                        <table border="0" cellpadding="0" cellspacing="0">
							 <tr>
								<td width="10%" class="clsTableFormHeader">Trans. Id.</td>
								<td width="25%">
								 	<input type="text" name="qry_XactId" size="20" value="<%=query.getQry_XactId()%>">
								</td>
								<td width="10%" class="clsTableFormHeader">Trans. Date</td>
								<td width="25%"> 
									<input type="text" name="qry_XactDate"  size="20" value="<%=query.getQry_XactDate()%>">
								</td>
								<td width="10%" class="clsTableFormHeader">Source.</td>
								<td width="20%">
									<input type="text" name="qry_Reason"  size="20" value="<%=query.getQry_Reason()%>">
								</td>
							 </tr>
							 <tr>
								<td class="clsTableFormHeader">Trans. Item</td>
								<td>
								 	<input type="text" name="qry_Item" size="20" value="<%=query.getQry_Item()%>">
								</td>
								<td class="clsTableFormHeader">Item Description</td>
								<td colspan="3">
								 	<input type="text" name="qry_ItemDescription"  size="73" value="<%=query.getQry_ItemDescription()%>">
								</td>
							 </tr>
				        </table>
			        </td>
			   </tr>
            </table>
        </div>
        <br>

        <!-- Display command buttons -->
        <table width="100%" cellpadding="0" cellspacing="0">
		   <tr>
		      <td colspan="2">
		         <input name="<%=CreditChargesConst.REQ_SEARCH%>" type="button" value="Search" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
		         <input name="<%=CreditChargesConst.REQ_ADD%>" type="button" value="Add" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
		         <input name="reset" type="reset" value="Clear" style="width:90">
		      </td>
	       </tr>
       </table>
	   <input name="clientAction" type="hidden">
     </form>
     <br>

     <!--  Begin Search Criteria section -->
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactPurchase.CreditorSearch">
        <font size="4" style="color:blue">Search Results</font>
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:90%; height:480px; overflow:auto">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					 <th width="2%" class="clsTableListHeader">&nbsp;</th>
					 <th width="10" class="clsTableListHeader" style="text-align:center">Trans. Id.</th>
					 <th width="16%" class="clsTableListHeader"  style="text-align:left">Creditor Account No.</th>
					 <th width="1%" class="clsTableListHeader">&nbsp;</th>
					 <th width="25%" class="clsTableListHeader" style="text-align:left">Creditor Name</th>
					 <th width="24%" class="clsTableListHeader" style="text-align:left">Transaction Source</th>
					 <th width="10%" class="clsTableListHeader" style="text-align:right">Trans. Total</th>
					 <th width="3%" class="clsTableListHeader">&nbsp;</th>
					 <th width="8%" class="clsTableListHeader" style="text-align:left">Trans. Date</th>
				 </tr>

				 <beanlib:LoopRows bean="xactListDso" list="<%=CreditChargesConst.CLIENT_DATA_XACTLIST %>"> 
				 <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
					<td class="clsTableListHeader">
						<beanlib:InputControl dataSource="xactListDso" type="radio" name="selCbx" value="rowid"/>
						<beanlib:InputControl dataSource="xactListDso" type="hidden" name="XactId" property="XactId" uniqueName="yes"/>
						<beanlib:InputControl dataSource="xactListDso" type="hidden" name="CreditorId" property="CreditorId" uniqueName="yes"/>
					<td>
						<font size="2">
							<beanlib:ElementValue dataSource="xactListDso" property="XactId"/>
						</font>
					</td>
					<td>
						 <font size="2">
							<beanlib:ElementValue dataSource="xactListDso" property="AccountNo"/>
						 </font>
					</td>
					<td align="right">&nbsp;</td>
					<td>
						 <font size="2">
							<beanlib:ElementValue dataSource="xactListDso" property="Longname"/>
						 </font>
					</td>
					<td align="left">
						<font size="2">
							 <beanlib:ElementValue dataSource="xactListDso" property="Reason" />
						</font>
					</td>
                    <td align="right">
						<font color="blue" size="2">
							 <beanlib:ElementValue dataSource="xactListDso" property="XactAmount" format="$#,##0.00;($#,##0.00)"/>
						</font>
					</td>					
					<td align="right">&nbsp;</td>
					<td>
						 <font size="2">
							<beanlib:ElementValue dataSource="xactListDso" property="XactDate" format="MM-dd-yyyy"/>
						 </font>
					</td>
				 </tr>
			 </beanlib:LoopRows>
			 <% if (pageContext.getAttribute("ROW") == null) {
              	   out.println("<tr><td colspan=10 align=center>Data Not Found</td></tr>");
				}
			 %>

            </table>
        </div>
        <br>

        <!-- Display command buttons -->
        <table width="100%" cellpadding="0" cellspacing="0">
		   <tr>
		      <td colspan="2">
		         <input name="<%=CreditChargesConst.REQ_EDIT%>" type="button" value="View" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
		      </td>
	       </tr>
        </table>
		<input name="clientAction" type="hidden">
     </form>
     <db:Dispose/>
 </body>
</html>
