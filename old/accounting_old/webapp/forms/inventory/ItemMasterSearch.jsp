<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.criteria.ItemMasterCriteria" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.gl.AccountingConst" %>
<%@ page import="com.inventory.ItemConst" %>

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
  
  <%@include file="/includes/SessionQuerySetup.jsp"%>  	  	 
    
  <%
	  String pageTitle = "Item Master Maintenance Console";
	  String jspOrigin = request.getParameter("jspOrigin");
	  String itemCriteria = null;
	  String overrideInd = null;
 	  ItemMasterCriteria query = (custObj != null && custObj instanceof ItemMasterCriteria ? (ItemMasterCriteria)baseQueryObj.getCustomObj() : ItemMasterCriteria.getInstance());
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
								order="item_status_id" 
								type="xml"/>					   				
				
  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

     <!--  Begin Search Criteria section -->
     <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/Inventory.Search">
        <font size="4" style="color:blue">Selection Criteria</font>
        <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:90%; height:120px">
            <table width="100%" cellpadding="0" cellspacing="2">
               <tr>
                  <td width="15%" class="clsTableFormHeader">Item No.</td>
                  <td width="35%">
                     <beanlib:InputControl type="text" name="qry_Id" value="#QUERY_BEAN.CustomObj.qry_Id"/>
                  </td>
                  <td width="15%" class="clsTableFormHeader">Item Name</td>
                  <td width="35%">
                     <beanlib:InputControl type="text" name="qry_Description" size="55" value="#QUERY_BEAN.CustomObj.qry_Description"/>
                  </td>                  
               </tr>                           
               <tr>
                  <td class="clsTableFormHeader">Vendor</td>
                  <td>
									  <xml:InputControl dataSource="<%=ItemConst.CLIENT_DATA_VENDORLIST %>"
																 type="select"
																 name="qry_VendorId"
																 query="//Creditors/CreditorExt"
																 codeProperty="creditorId"
																 displayProperty="name"
																 selectedValue="#QUERY_BEAN.CustomObj.qry_VendorId"/>	
                  </td>
                  <td class="clsTableFormHeader">Item Type</td>
                  <td>
					 <db:InputControl dataSource="itemTypeDso"
									  type="select"
									  name="qry_ItemTypeId"
									  codeProperty="ItemTypeId"
									  displayProperty="Description"
									  selectedValue="#QUERY_BEAN.CustomObj.qry_ItemTypeId"/>
                  </td>                  
               </tr>
               
               <tr>
                  <td class="clsTableFormHeader">Vendor Item No.</td>
                  <td>
                     <beanlib:InputControl type="text" name="qry_VendorItemNo" size="40" value="#QUERY_BEAN.CustomObj.qry_VendorItemNo"/>
                  </td>
                  <td class="clsTableFormHeader">Serial No.</td>
                  <td>
                      <beanlib:InputControl type="text" name="qry_ItemSerialNo" size="40" value="#QUERY_BEAN.CustomObj.qry_ItemSerialNo"/>
                  </td>                  
               </tr>               
               
               <tr>
                  <td class="clsTableFormHeader">Unit Cost</td>
                  <td>
                     <gen:CondOps name="qryRelOp_UnitCost" selectedValue="<%=query.getQryRelOp_UnitCost()%>"/>
                     <beanlib:InputControl type="text" name="qry_UnitCost" size="15" value="#QUERY_BEAN.CustomObj.qry_UnitCost"/>
                  </td>
                  <td class="clsTableFormHeader">Qty On Hand</td>
                  <td>
                     <beanlib:InputControl type="text" name="qry_QtyOnHand" size="10" value="#QUERY_BEAN.CustomObj.qry_QtyOnHand"/>
                  </td>                  
               </tr>                              
               
               <tr>
                  <td class="clsTableFormHeader">Item Status</td>
                  <td>
					 <db:InputControl dataSource="itemSatusDso"
													  type="select"
													  name="qry_ItemStatusId"
													  codeProperty="ItemStatusId"
													  displayProperty="Description"
													  selectedValue="#QUERY_BEAN.CustomObj.qry_ItemStatusId"/>
                  </td>
                  <td>&nbsp;</td>
                  <td>
                    <beanlib:InputControl type="checkbox" 
                                          name="qry_Active" 
                                          size="10" 
                                          value="1" 
                                          checkedValue="#QUERY_BEAN.CustomObj.qry_Active" 
                                          displayProperty="Active" 
                                          displayPropertyPos="left"/>&nbsp;&nbsp;
                                          
                    <beanlib:InputControl type="checkbox" 
                                          name="qry_Inactive" 
                                          size="10" 
                                          value="0" 
                                          checkedValue="#QUERY_BEAN.CustomObj.qry_Inactive" 
                                          displayProperty="Inactive" 
                                          displayPropertyPos="left"/>
                  </td>                  
               </tr>            
            </table>
        </div>
        <br>
        
        <!-- Display command buttons -->  
        <table width="100%" cellpadding="0" cellspacing="0">
		   <tr>
		      <td colspan="2">
		         <input name="search" type="button" value="Search" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
		         <input name="reset" type="reset" value="Clear" style="width:90">
		      </td>
	       </tr>		
       </table>            
	   <input name="clientAction" type="hidden">        
     </form>
     <br>
     
     <!--  Begin Search Criteria section -->
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/Inventory.Search">
        <font size="4" style="color:blue">Search Results</font>
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:90%; height:480px; overflow:auto">
            <table width="100%" cellpadding="0" cellspacing="0">
				<tr>
				   <th width="3%" class="clsTableListHeader">&nbsp;</th>
				   <th width="6%" class="clsTableListHeader" style="text-align:left">Item No.</th>
				   <th width="39%" class="clsTableListHeader"  style="text-align:left">Item Name</th>
				   <th width="2%" class="clsTableListHeader">&nbsp;</th>
				   <th width="8%" class="clsTableListHeader" style="text-align:left">Item Type</th>
				   <th width="8%" class="clsTableListHeader" style="text-align:right">Unit Cost</th>
				   <th width="10%" class="clsTableListHeader" style="text-align:right">Qty On Hand</th>
				   <th width="8%" class="clsTableListHeader" style="text-align:center">Override</th>
				   <th width="2%" class="clsTableListHeader">&nbsp;</th>
				   <th width="8%" class="clsTableListHeader" style="text-align:left">Status</th>
				   <th width="6%" class="clsTableListHeader" style="text-align:left">Vendor</th>
			   </tr>
               
   			   <db:LoopRows dataSource="itemDso"> 
			     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
			        <td class="clsTableListHeader">
			            <db:InputControl  type="radio" name="selCbx" value="rowid"/>
					    <db:InputControl type="hidden" name="ItemId" value="#itemDso.Id" uniqueName="yes"/>
			        <td>
			            <db:InputControl  value="#itemDso.Id"/> 
			        </td>
			        <td>
			            <db:InputControl  value="#itemDso.Description"/> 
			        </td>
			        <td align="right">&nbsp;</td>
			        <td>
			            <db:InputControl  value="#itemDso.ItemTypeDescription"/> 
			        </td>
			        <td align="right">
			            <font color="blue">
			               <db:InputControl  value="#itemDso.UnitCost" format="$#,##0.00;($#,##0.00)"/> 
			            </font>
			        </td>
			        <td align="right">
			            <font color="blue">
			               <db:InputControl  value="#itemDso.QtyOnHand" format="#,##0;(#,##0)"/> 
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
			               <db:InputControl  value="#itemDso.ItemStatus"/> 
			            </font>
			        </td>			        			        			        			        			        
			        <td>
			            <db:InputControl  value="#itemDso.VendorId"/> 
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
		         <input name="edit" type="button" value="Edit" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
		         <input name="add" type="button" value="Add" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
		         <input name="back" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
		      </td>
	       </tr>		
        </table>            
		<input name="clientAction" type="hidden">
     </form>
     <db:Dispose/>
 </body>
</html>
