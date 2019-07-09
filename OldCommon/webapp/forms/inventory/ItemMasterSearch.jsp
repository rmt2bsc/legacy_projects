<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.ItemMasterQuery" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.AccountingConst" %>
<%@ page import="com.constants.ItemConst" %>

<%@ page import="com.util.RMT2Utility" %>

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
	  String pageTitle = "Item Master Maintenance Console";
	  String jspOrigin = request.getParameter("jspOrigin");
	  String vendorCriteria = "creditor_type_id = " + AccountingConst.CREDITOR_TYPE_VENDOR;
	  String itemCriteria = null;
	  String overrideInd = null;
  %>
  
  <%@include file="/includes/SessionQuerySetup.jsp"%>  	  	 
  
  <%	
	  ItemMasterQuery query = (custObj != null && custObj instanceof ItemMasterQuery ? (ItemMasterQuery)baseQueryObj.getCustomObj() : ItemMasterQuery.getInstance());
	  itemCriteria = baseQueryObj.getWhereClause();
	  query.setJspOrigin(jspOrigin);
  %>
    
 <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
 <db:datasource id="itemTypeDso" 
                classId="com.api.DataSourceApi" 
			    connection="con" 
			    query="ItemMasterTypeView" 
			    order="description" 
			    type="xml"/>
					   
 <db:datasource id="vendorDso" 
                classId="com.api.DataSourceApi" 
				connection="con" 
				query="VwCreditorBusinessView" 
				where="<%= vendorCriteria %>"
				order="longname" 
				type="xml"/>					   
 
  <db:datasource id="itemDso" 
                classId="com.api.DataSourceApi" 
				connection="con" 
				query="VwItemMasterView" 
				where="<%= itemCriteria %>"
				order="Description" 
				type="xml"/>					   
				
  <db:datasource id="itemSatusDso" 
                classId="com.api.DataSourceApi" 
				connection="con" 
				query="ItemMasterStatusView" 
				order="id" 
				type="xml"/>					   				
				
  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

     <!--  Begin Search Criteria section -->
     <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/ItemMaster.Search">
        <font size="4" style="color:blue">Selection Criteria</font>
        <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:90%; height:120px">
            <table width="100%" cellpadding="0" cellspacing="2">
               <tr>
                  <td width="15%" class="clsTableFormHeader">Item No.</td>
                  <td width="35%">
                     <input type="text" name="qry_Id" value="<%=query.getQry_Id()%>">
                  </td>
                  <td width="15%" class="clsTableFormHeader">Item Name</td>
                  <td width="35%">
					 <input type="text" name="qry_Description" size="55" value="<%=query.getQry_Description()%>">
                  </td>                  
               </tr>                           
               <tr>
                  <td class="clsTableFormHeader">Vendor</td>
                  <td>
					 <db:InputControl dataSource="vendorDso"
									  type="select"
									  name="qry_VendorId"
									  codeProperty="CreditorId"
									  displayProperty="Longname"
									  selectedValue="<%=query.getQry_VendorId()%>"/>
                  </td>
                  <td class="clsTableFormHeader">Item Type</td>
                  <td>
					 <db:InputControl dataSource="itemTypeDso"
									  type="select"
									  name="qry_ItemTypeId"
									  codeProperty="Id"
									  displayProperty="Description"
									  selectedValue="<%=query.getQry_ItemTypeId()%>"/>
                  </td>                  
               </tr>
               
               <tr>
                  <td class="clsTableFormHeader">Vendor Item No.</td>
                  <td>
                     <input type="text" name="qry_VendorItemNo" size="40" value="<%=query.getQry_VendorItemNo()%>">
                  </td>
                  <td class="clsTableFormHeader">Serial No.</td>
                  <td>
					 <input type="text" name="qry_ItemSerialNo"  size="40" value="<%=query.getQry_ItemSerialNo()%>">
                  </td>                  
               </tr>               
               
               <tr>
                  <td class="clsTableFormHeader">Unit Cost</td>
                  <td>
                     <gen:CondOps name="qryRelOp_UnitCost" selectedValue="<%=query.getQryRelOp_UnitCost()%>"/>
                     <input type="text" name="qry_UnitCost" value="<%=query.getQry_UnitCost()%>" size="15" style="text-align: right">
                  </td>
                  <td class="clsTableFormHeader">Qty On Hand</td>
                  <td>
					 <input type="text" name="qry_QtyOnHand" value="<%=query.getQry_QtyOnHand()%>" size="10" style="text-align: right">
                  </td>                  
               </tr>                              
               
               <tr>
                  <td class="clsTableFormHeader">Item Status</td>
                  <td>
					 <db:InputControl dataSource="itemSatusDso"
									  type="select"
									  name="qry_ItemStatusId"
									  codeProperty="Id"
									  displayProperty="Description"
									  selectedValue="<%=query.getQry_ItemStatusId()%>"/>
                  </td>
                  <td>&nbsp;</td>
                  <td>
                  Active<input type="checkbox" name="qry_Active" value="1" <%=query.getQry_Active().equals("1") ? "checked" : ""%>>
                  Inactive<input type="checkbox" name="qry_Inactive" value="0" <%=query.getQry_Inactive().equals("0") ? "checked" : ""%>>
                  </td>                  
               </tr>            
            </table>
        </div>
        <br>
        
        <!-- Display command buttons -->  
        <table width="100%" cellpadding="0" cellspacing="0">
		   <tr>
		      <td colspan="2">
		         <input name="search" type="button" value="Search" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.SearchForm, this.name)">
		         <input name="reset" type="reset" value="Clear" style="width:90">
		      </td>
	       </tr>		
       </table>            
	   <input name="clientAction" type="hidden">        
     </form>
     <br>
     
     <!--  Begin Search Criteria section -->
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/ItemMaster.Search">
        <font size="4" style="color:blue">Search Results</font>
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:90%; height:480px; overflow:auto">
            <table width="100%" cellpadding="0" cellspacing="0">
				<tr>
				   <th width="3%" class="clsTableListHeader">&nbsp;</th>
				   <th width="6%" class="clsTableListHeader" style="text-align:left">Item No.</th>
				   <th width="24%" class="clsTableListHeader"  style="text-align:left">Item Name</th>
				   <th width="2%" class="clsTableListHeader">&nbsp;</th>
				   <th width="8%" class="clsTableListHeader" style="text-align:left">Item Type</th>
				   <th width="8%" class="clsTableListHeader" style="text-align:right">Unit Cost</th>
				   <th width="10%" class="clsTableListHeader" style="text-align:right">Qty On Hand</th>
				   <th width="8%" class="clsTableListHeader" style="text-align:center">Override</th>
				   <th width="2%" class="clsTableListHeader">&nbsp;</th>
				   <th width="8%" class="clsTableListHeader" style="text-align:left">Status</th>
				   <th width="21%" class="clsTableListHeader" style="text-align:left">Vendor</th>
			   </tr>
               
   			   <db:LoopRows dataSource="itemDso"> 
			     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
			        <td class="clsTableListHeader">
			            <db:InputControl dataSource="itemDso" type="radio" name="selCbx" value="rowid"/>
					    <db:InputControl dataSource="itemDso" type="hidden" name="ItemId" property="Id" uniqueName="yes"/>                                  
			        <td>
			            <db:ElementValue dataSource="itemDso" property="Id"/> 
			        </td>
			        <td>
			            <db:ElementValue dataSource="itemDso" property="Description"/> 
			        </td>
			        <td align="right">&nbsp;</td>
			        <td>
			            <db:ElementValue dataSource="itemDso" property="ItemTypeDescription"/> 
			        </td>
			        <td align="right">
			            <font color="blue">
			               <db:ElementValue dataSource="itemDso" property="UnitCost" format="$#,##0.00;($#,##0.00)"/> 
			            </font>
			        </td>
			        <td align="right">
			            <font color="blue">
			               <db:ElementValue dataSource="itemDso" property="QtyOnHand" format="#,##0;(#,##0)"/> 
			            </font>
			        </td>
							<td align="center">
							    <%
							        overrideInd = itemDso.getColumnValue("OverrideRetail");
							        if (overrideInd.equals("1")) {
							           overrideInd = "Yes";
							        }
							        else {
							            overrideInd = "No";
							        }
							    %>
							    <%=overrideInd%>
			        </td>			        
			        <td align="right">&nbsp;</td>			        
			        <td>
			            <%
			               String itemStatusColor = "black";
			               int itemStatusId = Integer.parseInt(itemDso.getColumnValue("ItemStatusId"));
			               if (itemStatusId == ItemConst.ITEM_STATUS_OUTSTOCK) {
			                  itemStatusColor = "red";
			               }
			               if (itemStatusId == ItemConst.ITEM_STATUS_OUTSRVC) {
			                  itemStatusColor = "gray";
			               }
			            %>
			            <font color="<%=itemStatusColor%>">
			               <db:ElementValue dataSource="itemDso" property="ItemStatus"/> 
			            </font>
			        </td>			        			        			        			        			        
			        <td>
			            <db:ElementValue dataSource="itemDso" property="VendorName"/> 
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
		         <input name="edit" type="button" value="Edit" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
		         <input name="add" type="button" value="Add" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
		      </td>
	       </tr>		
        </table>            
		<input name="clientAction" type="hidden">
     </form>
     <db:Dispose/>
 </body>
</html>
