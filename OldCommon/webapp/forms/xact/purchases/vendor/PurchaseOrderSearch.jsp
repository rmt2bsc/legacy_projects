<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.criteria.PurchaseOrderCriteria" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.AccountingConst" %>
<%@ page import="com.constants.PurchasesConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Transaction Search Criteria</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	  <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>

  <%
	  String pageTitle = "Vendor Purchase Order Console";
	  String jspOrigin = request.getParameter("jspOrigin");
	  String vendorCriteria = "creditor_type_id = " + AccountingConst.CREDITOR_TYPE_VENDOR;
	  String mainCriteria = null;
  %>
   
  <%@include file="/includes/SessionQuerySetup.jsp"%>

  <%
	  PurchaseOrderCriteria query = (custObj != null && custObj instanceof PurchaseOrderCriteria ? (PurchaseOrderCriteria) baseQueryObj.getCustomObj() : PurchaseOrderCriteria.getInstance());
	  mainCriteria = baseQueryObj.getWhereClause();
  %>

 <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
 <db:datasource id="vendorDso"
                classId="com.api.DataSourceApi"
								connection="con"
								query="VwCreditorBusinessView"
								where="<%= vendorCriteria %>"
								order="longname"
								type="xml"/>

 <db:datasource id="poStatusDso"
                classId="com.api.DataSourceApi"
								connection="con"
								query="PurchaseOrderStatusView"
								order="description"
								type="xml"/>

  <db:datasource id="poListDso"
                classId="com.api.DataSourceApi"
								connection="con"
								query="VwPurchaseOrderListView"
								where="<%= mainCriteria %>"
								order="id"
								type="xml"/>

  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>
     <!--  Begin Search Criteria section -->
     <form name="SearchForm"  method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactPurchase.VendorSearch">
        <font size="4" style="color:blue">Selection Criteria</font>
        <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:90%; height:120px">
            <table width="100%" border="1" cellpadding="0" cellspacing="2">
               <!-- Begin Vendor Criteria Section -->
               <tr>
                  <td>
                      <table border="0" cellpadding="0" cellspacing="0">
                         <tr>
												   <td width="15%" class="clsTableFormHeader">Vendor Account Number</td>
												   <td width="35%">
													  	<input type="text" name="qry_AccountNumber" value="<%=query.getQry_AccountNumber()%>">
												   </td>
												   <td width="15%" class="clsTableFormHeader">Vendor</td>
												   <td width="35%">
														  <db:InputControl dataSource="vendorDso"
																						   type="select"
																						   name="qry_VendorId"
																						   codeProperty="CreditorId"
																						   displayProperty="Longname"
																						   selectedValue="<%=query.getQry_VendorId()%>"/>
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

               <!-- Begin Purchase Order Criteria Section -->
               <tr>
                  <td>
                    <table border="0" cellpadding="0" cellspacing="0">
										   <tr>
												  <td width="10%" class="clsTableFormHeader">P.O. No.</td>
												  <td width="25%">
														<input type="text" name="qry_Id" size="20" value="<%=query.getQry_Id()%>">
												  </td>
												  <td width="10%" class="clsTableFormHeader">Ref No.</td>
												  <td width="25%">
													 	<input type="text" name="qry_RefNo"  size="20" value="<%=query.getQry_RefNo()%>">
												  </td>
												  <td width="10%" class="clsTableFormHeader">PO Status.</td>
												  <td width="20%">
													  <db:InputControl dataSource="poStatusDso"
																						  type="select"
																						  name="qry_StatusId"
																						  codeProperty="Id"
																						  displayProperty="Description"
  					                                  selectedValue="<%=query.getQry_StatusId()%>"/>
												  </td>
										   </tr>
										   <tr>
												  <td class="clsTableFormHeader">Item Number</td>
												  <td>
														 <input type="text" name="qry_ItemNumber" size="20" value="<%=query.getQry_ItemNumber()%>">
												  </td>
												  <td class="clsTableFormHeader">Item Serial No.</td>
												  <td>
				    								 <input type="text" name="qry_SerialNo"  size="20" value="<%=query.getQry_SerialNo()%>">
												  </td>
												  <td class="clsTableFormHeader">Vendor Item No.</td>
												  <td>
													  <input type="text" name="qry_VendorItemNumber"  size="20" value="<%=query.getQry_VendorItemNumber()%>">
											  	</td>
										   </tr>
										   <tr>
											  	<td class="clsTableFormHeader">Item Description</td>
											  	<td colspan="4">
												 		<input type="text" name="qry_ItemDescription"  size="73" value="<%=query.getQry_ItemDescription()%>">
											  	</td>
											  	<td colspan="4">&nbsp;</td>
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
		         <input name="<%=PurchasesConst.REQ_SEARCH%>" type="button" value="Search" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
		         <input name="<%=PurchasesConst.REQ_ADD%>" type="button" value="Add" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
		         <input type="button" name="vendoritemview" value="Inventory Items" style="width:120"  onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
		         <input name="reset" type="reset" value="Clear" style="width:90">
		      </td>
	      </tr>
       </table>
	     <input name="clientAction" type="hidden">
     </form>
     <br>

     <!--  Begin Result Set section -->
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactPurchase.VendorSearch">
        <font size="4" style="color:blue">Search Results</font>
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:90%; height:480px; overflow:auto">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
				   <th width="2%" class="clsTableListHeader">&nbsp;</th>
				   <th width="6%" class="clsTableListHeader" style="text-align:left">PO No.</th>
				   <th width="16%" class="clsTableListHeader"  style="text-align:left">Vendor Account No.</th>
				   <th width="1%" class="clsTableListHeader">&nbsp;</th>
				   <th width="24%" class="clsTableListHeader" style="text-align:left">Vendor Name</th>
				   <th width="10%" class="clsTableListHeader" style="text-align:right">Total</th>
				   <th width="10%" class="clsTableListHeader" style="text-align:center">Status</th>
				   <th width="1%" class="clsTableListHeader">&nbsp;</th>
				   <th width="14%" class="clsTableListHeader" style="text-align:left">Effective Date</th>
				   <th width="14%" class="clsTableListHeader" style="text-align:left">End Date</th>
			   </tr>

   			   <db:LoopRows dataSource="poListDso">
			     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
			        <td class="clsTableListHeader">
			            <db:InputControl dataSource="poListDso" type="radio" name="selCbx" value="rowid"/>
			            <db:InputControl dataSource="poListDso" type="hidden" name="PurchaseOrderId" property="Id" uniqueName="yes"/>
			            <db:InputControl dataSource="poListDso" type="hidden" name="VendorId" property="VendorId" uniqueName="yes"/>
			        <td>
			            <font size="2">
			               <db:ElementValue dataSource="poListDso" property="Id"/>
			            </font>
			        </td>
			        <td>
			           <font size="2">
			              <db:ElementValue dataSource="poListDso" property="AccountNumber"/>
			           </font>
			        </td>
			        <td align="right">&nbsp;</td>
			        <td>
			           <font size="2">
  			              <db:ElementValue dataSource="poListDso" property="Longname"/>
  			           </font>
			        </td>
			        <td align="right">
			            <font color="blue" size="2">
			               <db:ElementValue dataSource="poListDso" property="Total" format="$#,##0.00;($#,##0.00)"/>
			            </font>
			        </td>
			        <td align="center">
			            <font color="blue" size="2">
			               <db:ElementValue dataSource="poListDso" property="StatusDescription" />
			            </font>
			        </td>
			        <td align="right">&nbsp;</td>
			        <td>
			           <font size="2">
		                  <db:ElementValue dataSource="poListDso" property="EffectiveDate" format="MM-dd-yyyy HH:mm:ss"/>
		               </font>
			        </td>
			        <td>
			            <font size="2">
			               <db:ElementValue dataSource="poListDso" property="EndDate" format="MM-dd-yyyy"/>
			            </font>
			        </td>
			     </tr>
			   </db:LoopRows>
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
		         <input name="<%=PurchasesConst.REQ_EDIT%>" type="button" value="Edit" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
		         <input name="<%=PurchasesConst.REQ_DELETE%>" type="button" value="Delete" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
		      </td>
	       </tr>
        </table>
				<input name="clientAction" type="hidden">
     </form>
     <db:Dispose/>
 </body>
</html>
