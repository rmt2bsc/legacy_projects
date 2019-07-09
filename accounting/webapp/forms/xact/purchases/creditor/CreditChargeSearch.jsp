<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ page import="com.bean.criteria.CreditChargeCriteria" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.xact.XactConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.xact.purchases.creditor.CreditorPurchasesConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
  String pageTitle = "Creditor Purchases Console";
%>

<html>
  <head>
    <title><%=pageTitle%></title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
		<script type='text/javascript' language='javascript' src="<%=APP_ROOT%>/js/datetimepicker.js"></script>
  </head>

  

  <%@include file="/includes/SessionQuerySetup.jsp"%>

  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

     <!--  Begin Search Criteria section -->
     <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/PurchasesCreditor.Search">
        <input type="hidden" name="XactTypeId" value="<%=XactConst.XACT_TYPE_CREDITCHARGE%>">
        <font size="4" style="color:blue">Selection Criteria</font>
        <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:55%; height:120px">
            <table width="100%" border="1" cellpadding="0" cellspacing="2">

               <!-- Begin Vendor Criteria Seciont -->
               <tr>
                  <td>
                      <table border="0" cellpadding="0" cellspacing="0">
                         <tr>
							 <td width="15%" class="clsTableFormHeader">Account Number</td>
							 <td width="35%">
								<beanlib:InputControl type="text" name="qry_AccountNo" value="#QUERY_BEAN.CustomObj.qry_AccountNo"/>
							 </td> 
							 <td width="15%" class="clsTableFormHeader">Creditor</td>
							 <td width="35%">
							 <xml:InputControl dataSource="<%=CreditorPurchasesConst.CLIENT_DATA_CREDIOTRLIST %>"
																 type="select"
																 name="qry_CreditorId"
																 query="//Creditors/CreditorExt"
																 codeProperty="creditorId"
																 displayProperty="name"
																 selectedValue="#QUERY_BEAN.CustomObj.qry_CreditorId"/>	
							 </td>
					     </tr>
						 <tr>
							<td class="clsTableFormHeader">Tax Id</td>
							<td>
				 				<beanlib:InputControl type="text" name="qry_TaxId" size="20" value="#QUERY_BEAN.CustomObj.qry_TaxId"/>
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
								 	<beanlib:InputControl type="text" name="qry_XactId" size="20" value="#QUERY_BEAN.CustomObj.qry_XactId"/>
								</td>
								<td width="10%" class="clsTableFormHeader">Trans. Date</td>
								<td width="25%"> 
									<beanlib:InputControl type="text" name="qry_XactDate"  size="20" value="#QUERY_BEAN.CustomObj.qry_XactDate"/>
									 <a href="javascript:NewCal('qry_XactDate','mmddyyyy')"><img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>
								</td>
								<td width="10%" class="clsTableFormHeader">Source/Reason</td>
								<td width="20%">
									<beanlib:InputControl type="text" name="qry_Reason"  size="20" value="#QUERY_BEAN.CustomObj.qry_Reason"/>
								</td>
							 </tr>
							 <tr>
								<td class="clsTableFormHeader">Trans. Item</td>
								<td>
								 	<beanlib:InputControl type="text" name="qry_Item" size="20" value="#QUERY_BEAN.CustomObj.qry_Item"/>
								</td>
								<td class="clsTableFormHeader">Item Description</td>
								<td colspan="3">
								 	<beanlib:InputControl type="text" name="qry_ItemDescription"  size="73" value="#QUERY_BEAN.CustomObj.qry_ItemDescription"/>
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
		         <input name="<%=GeneralConst.REQ_SEARCH%>" type="button" value="Search" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
		         <input name="<%=GeneralConst.REQ_ADD%>" type="button" value="Add" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
		         <input name="<%=GeneralConst.REQ_RESET%>" type="reset" value="Clear" style="width:90">
		      </td>
	       </tr>
       </table>
	   <input name="clientAction" type="hidden">
     </form>
     <br>

     <!--  Begin Search Results section -->
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/PurchasesCreditor.Search">
        <font size="4" style="color:blue">Search Results</font>
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:70%; height:440px; overflow:auto">
		        <table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									 <th width="2%" class="clsTableListHeader">&nbsp;</th>
									 <th width="7%" class="clsTableListHeader" style="text-align:center">Trans. Id.</th>
									 <th width="10%" class="clsTableListHeader"  style="text-align:left">Account No.</th>
									 <th width="5%" class="clsTableListHeader">&nbsp;</th>
									 <th width="8%" class="clsTableListHeader" style="text-align:left">Trans. Date</th>
									 
									 <th width="10%" class="clsTableListHeader" style="text-align:left">Confirm No.</th>
									 
									 <th width="20%" class="clsTableListHeader" style="text-align:left">Creditor Name</th>
									 <th width="10%" class="clsTableListHeader" style="text-align:right">Trans. Total</th>
									 <th width="3%" class="clsTableListHeader">&nbsp;</th>
									 <th width="20%" class="clsTableListHeader" style="text-align:left">Transaction Source</th>
									 <th width="5%" class="clsTableListHeader">Status</th>
								 </tr>
				
								 <beanlib:LoopRows bean="item" list="<%=XactConst.CLIENT_DATA_XACTLIST %>"> 
								 <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
									<td class="clsTableListHeader">
										<beanlib:InputControl type="radio" name="selCbx" value="rowid"/>
										<beanlib:InputControl type="hidden" name="XactId" value="#item.XactId" uniqueName="yes"/>
										<beanlib:InputControl type="hidden" name="CreditorId" value="#item.CreditorId" uniqueName="yes"/>
									</td>
									<td align="left">
										<font size="2">
											<beanlib:InputControl value="#item.XactId"/>
										</font>
									</td>
									<td>
										 <font size="2">
											<beanlib:InputControl value="#item.AccountNo"/>
										 </font>
									</td>
									<td align="center">
		    							<beanlib:InputControl type="hidden" name="DocumentId" value="#item.DocumentId" uniqueName="yes"/>
											<gen:Evaluate expression="#item.DocumentId">
				   								<gen:When expression="0">&nbsp;</gen:When>
				   								<gen:WhenElse>
				     									<a href="<%=APP_ROOT%>/DocumentViewer.jsp?contentId=<beanlib:InputControl value='#item.DocumentId'/>" target="_blank"><img src="<%=APP_ROOT%>/images/camera2.png" style="border: none" alt="View Cash Disbursement Reciept"/></a> 
				   								</gen:WhenElse>
		    							</gen:Evaluate>
									</td>     
									
									<td>
										 <font size="2">
											<beanlib:InputControl value="#item.XactDate" format="MM-dd-yyyy"/>
										 </font>
									</td>
									<td align="left" >
										<font size="2">
			   							<beanlib:InputControl value="#item.ConfirmNo"/>
										</font>
									</td>					
									<td>
										 <font size="2">
											<beanlib:InputControl value="#item.Name"/>
										 </font>
									</td>
									<td align="right">
										<font color="blue" size="2">
											 <beanlib:InputControl value="#item.XactAmount" format="$#,##0.00;($#,##0.00)"/>
										</font>
									</td>					
									<td align="right">&nbsp;</td>
									
									<td align="left">
										<font size="2">
											 <beanlib:InputControl value="#item.Reason" />
										</font>
									</td>
									<td align="center">
										<font size="2">
										   <gen:Evaluate expression="#item.XactSubtypeId">
										     <gen:When expression="999">Finalized</gen:When>
										     <gen:WhenElse>Open</gen:WhenElse>
										   </gen:Evaluate>
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
        
        <!--  Display icon legend, if applicable. -->
 		    <img src="<%=APP_ROOT%>/images/camera2.png" style="border: none"/><font color="black" size="1">View image of receipt</font>
        <br><br>

        <!-- Display command buttons -->
        <table width="100%" cellpadding="0" cellspacing="0">
		   <tr>
		      <td colspan="2">
		         <input name="<%=GeneralConst.REQ_VIEW%>" type="button" value="View" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
		         <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
		      </td>
	       </tr>
        </table>
		<input name="clientAction" type="hidden">
     </form>
 </body>
</html>
