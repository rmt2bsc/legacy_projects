<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.criteria.PurchaseOrderCriteria" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.xact.purchases.vendor.VendorPurchasesConst" %>


<gen:InitAppRoot id="APP_ROOT"/>
<%@include file="/includes/SessionQuerySetup.jsp"%>
<%
  String pageTitle = "Vendor Purchase Order Console";
  String jspOrigin = request.getParameter("jspOrigin");

%>
<html>
  <head>
	<title><%=pageTitle %></title>
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Expires" content="-1">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>

 <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>

 <db:datasource id="poStatusDso"
                classId="com.api.DataSourceApi"
								connection="con"
								query="PurchaseOrderStatusView"
								order="description"
								type="xml"/>


  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>
     <!--  Begin Search Criteria section -->
     <form name="SearchForm"  method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/PurchasesVendor.Search">
        <font size="4" style="color:blue">Selection Criteria</font>
        <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:60%; height:120px">
            <table width="100%" border="1" cellpadding="0" cellspacing="2">
               <!-- Begin Vendor Criteria Section -->
               <tr>
                  <td>
                      <table border="0" cellpadding="0" cellspacing="0">
                         <caption align="left">P.O. Criteria</caption>
                         <tr>
												   <td width="13%" class="clsTableFormHeader">Vendor</td>
												   <td width="20%">
														  <beanlib:InputControl dataSource="<%=VendorPurchasesConst.CLIENT_DATA_VENODRLIST %>" 
																									   type="select"
																									   name="qry_CreditorId"
																									   codeProperty="CreditorId"
																									   displayProperty="Name"
																									   selectedValue="#QUERY_BEAN.CustomObj.Qry_CreditorId"/>
												   </td>
												   <td width="13%" class="clsTableFormHeader">P.O. No.</td>
												   <td width="20%">
														  <beanlib:InputControl type="text" name="qry_Id" size="20" value="#QUERY_BEAN.CustomObj.Qry_Id"/>
												   </td>
                        </tr>
												<tr>
													 <td class="clsTableFormHeader">PO Status.</td>
												   <td>
													  <db:InputControl dataSource="poStatusDso"
																						  type="select"
																						  name="qry_StatusId"
																						  codeProperty="PoStatusId"
																						  displayProperty="Description"
												                      selectedValue="#QUERY_BEAN.CustomObj.Qry_StatusId"/>
												   </td>
												   <td class="clsTableFormHeader">Ref No.</td>
												   <td>
													  	<beanlib:InputControl type="text" name="qry_RefNo"  size="20" value="#QUERY_BEAN.CustomObj.Qry_RefNo"/>
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
		         <input type="button" name="vendoritemview" value="Inventory Items" style="width:120"  onClick="handleAction('_self', document.SearchForm, this.name)">
		         <input name="reset" type="reset" value="Clear" style="width:90">
		      </td>
	      </tr>
       </table>
	     <input name="clientAction" type="hidden">
     </form>
     <br>

     <!--  Begin Result Set section -->
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/PurchasesVendor.Search">
        <font size="4" style="color:blue">Search Results</font>
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:60%; height:450px; overflow:auto">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
				   <th width="2%" class="clsTableListHeader">&nbsp;</th>
				   <th width="6%" class="clsTableListHeader" style="text-align:center">PO No.</th>
				   <th width="16%" class="clsTableListHeader"  style="text-align:left">Account No.</th>
				   <th width="1%" class="clsTableListHeader">&nbsp;</th>
				   <th width="24%" class="clsTableListHeader" style="text-align:left">Name</th>
				   <th width="10%" class="clsTableListHeader" style="text-align:right">Total</th>
				   <th width="10%" class="clsTableListHeader" style="text-align:center">Status</th>
				   <th width="1%" class="clsTableListHeader">&nbsp;</th>
				   <th width="14%" class="clsTableListHeader" style="text-align:left">Effective Date</th>
				   <th width="14%" class="clsTableListHeader" style="text-align:left">End Date</th>
			   </tr>

   			   <beanlib:LoopRows list="<%=VendorPurchasesConst.CLIENT_DATA_POLIST %>" bean="item">
			     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
			        <td class="clsTableListHeader">
			            <beanlib:InputControl type="radio" name="selCbx" value="rowid"/>
			            <beanlib:InputControl type="hidden" name="PurchaseOrderId" value="#item.Id" uniqueName="yes"/>
			            <beanlib:InputControl type="hidden" name="VendorId" value="#item.VendorId" uniqueName="yes"/>
			        <td align="center">
			            <font size="2">
			               <beanlib:InputControl value="#item.Id"/>
			            </font>
			        </td>
			        <td>
			           <font size="2">
			              <beanlib:InputControl value="#item.AccountNumber"/>
			           </font>
			        </td>
			        <td align="right">&nbsp;</td>
			        <td>
			           <font size="2">
  			              <beanlib:InputControl value="#item.Name"/>
  			           </font>
			        </td>
			        <td align="right">
			            <font color="blue" size="2">
			               <beanlib:InputControl value="#item.Total" format="$#,##0.00;($#,##0.00)"/>
			            </font>
			        </td>
			        <td align="center">
			            <font color="blue" size="2">
			               <beanlib:InputControl value="#item.StatusDescription" />
			            </font>
			        </td>
			        <td align="right">&nbsp;</td>
			        <td>
			           <font size="2">
		                  <beanlib:InputControl value="#item.EffectiveDate" format="MM-dd-yyyy HH:mm:ss"/>
		               </font>
			        </td>
			        <td>
			            <font size="2">
			               <beanlib:InputControl value="#item.EndDate" format="MM-dd-yyyy"/>
			            </font>
			        </td>
			     </tr>
			   </beanlib:LoopRows>
			   <%
   	   		  if (pageContext.getAttribute("ROW") == null) {
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
		         <input name="<%=GeneralConst.REQ_EDIT%>" type="button" value="Edit" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
		         <input name="<%=GeneralConst.REQ_DELETE%>" type="button" value="Delete" disabled style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
		         <input name="<%=GeneralConst.REQ_BACK %>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
		      </td>
	       </tr>
        </table>
				<input name="clientAction" type="hidden">
     </form>
     <db:Dispose/>
 </body>
</html>
