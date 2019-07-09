<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.ItemMasterQuery" %>
<%@ page import="com.bean.VwItemMaster" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.AccountingConst" %>
<%@ page import="com.constants.ItemConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

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
  	  VwItemMaster item = (VwItemMaster) request.getAttribute("item");
	  String pageTitle = "Inventory Item Master - " + (item.getId() > 0 ? "Edit" : "Add");
	  String vendorCriteria = "creditor_type_id = " + AccountingConst.CREDITOR_TYPE_VENDOR;
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
 
  <db:datasource id="itemSatusDso" 
                classId="com.api.DataSourceApi" 
				connection="con" 
				query="ItemMasterStatusView" 
				order="id" 
				type="xml"/>					   				
				
  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

     <!--  Begin Search Criteria section -->
     <form name="EditForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/ItemMaster.Edit">
        <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:60%; height:200px">
           <table width="100%" cellpadding="0" cellspacing="2">
               <tr>
                  <td>&nbsp;</td>
               </tr>            
               <tr>
                  <td width="95%">
                     <font size="4" style="color:blue">Item Data</font>
                     <div id="ItemLayer" style="border-style:double;border-color:#999999; width:95%; height:120px">
                        <table width="100%" cellpadding="0" cellspacing="2">
                           <tr>
                              <td width="25%" class="clsTableFormHeader">Item Number</td>
                              <td width="25%">
               					  <beanlib:ElementValue dataSource="item" property="Id"/>
               					  <beanlib:InputControl dataSource="item" type="hidden" name="ItemId" property="Id"/>
                              </td>
                              <td width="25%" align="right" class="clsTableFormHeader">Item Status</td>
                              <td width="25%">
                                  <font color="red">
                                     <beanlib:ElementValue dataSource="item" property="ItemStatus"/>
                                  </font>
                              </td>
                           </tr>                           
                           <tr>
                              <td class="clsTableFormHeader">Item Name</td>
                              <td colspan="3">
                                  <beanlib:InputControl dataSource="item" type="text" name="Description" property="Description" size="65%" maxLength="80"/>
                              </td>
                           </tr>
                           <tr>
                              <td class="clsTableFormHeader">Item Type</td>
                              <td>
               					  <db:InputControl dataSource="itemTypeDso"
									               type="select"
									               name="ItemTypeId"
									               codeProperty="Id"
									               displayProperty="Description"
									               selectedValue="<%=String.valueOf(item.getItemTypeId())%>"/>
                              </td>
                              <td align="right" class="clsTableFormHeader">Active</td>
                              <td>
                                 <input type="checkbox" name="Active" value="1" <%=item.getActive() == 1 ? "checked" : ""%>>
                              </td>
                           </tr>                           
                           <tr>
                              <td class="clsTableFormHeader">Unit Cost</td>
                              <td>
               					  <beanlib:InputControl dataSource="item" type="text" name="UnitCost" property="UnitCost" style="text-align:right"/>
                              </td>
                              <td align="right" class="clsTableFormHeader">Qty On Hand</td>
                              <td>
                                  <beanlib:InputControl dataSource="item" type="text" name="QtyOnHand" property="QtyOnHand" style="text-align:right"/>
                              </td>
                           </tr>                           
                           <tr>
                              <td class="clsTableFormHeader">Retail Price</td>
                              <td>
               					  <beanlib:InputControl dataSource="item" type="text" name="RetailPrice" property="RetailPrice" style="text-align:right"/>
                              </td>
                              <td align="right" class="clsTableFormHeader">Mark Up</td>
                              <td>
                                  <beanlib:InputControl dataSource="item" type="text" name="Markup" property="Markup" style="text-align:right"/>
                              </td>
                           </tr>                                                      
                           <tr>
                              <td class="clsTableFormHeader">Override Retail</td>
                              <td colspan="3" align="left">
                                  <input type="checkbox" name="OverrideRetail" value="1" <%=item.getOverrideRetail() == 1 ? "checked" : ""%>>
                              </td>
                           </tr>                                                                                 
                        </table>
                     </div>
                  </td>
               </tr>
               <tr>
                  <td>&nbsp;</td>
               </tr>
               <tr>
                  <td>
                     <font size="4" style="color:blue">Vendor Data</font>
                     <div id="VendorLayer" align="center" style="border-style:double;border-color:#999999; width:50%; height:80px">
                        <table width="100%" cellpadding="0" cellspacing="2">
                           <tr>
                              <td width="30%" class="clsTableFormHeader">Vendor Name</td>
                              <td>
               					  <db:InputControl dataSource="vendorDso"
									               type="select"
									               name="VendorId"
									               codeProperty="CreditorId"
									               displayProperty="Longname"
									               selectedValue="<%=String.valueOf(item.getVendorId())%>"/>
                              </td>
                           </tr>
                           <tr>
                              <td class="clsTableFormHeader">Vendor Item No.</td>
                              <td>
                                 <beanlib:InputControl dataSource="item" type="text" name="VendorItemNo" property="VendorItemNo" size="30" maxLength="25"/>
                              </td>
                           </tr>                                                
                           <tr>
                              <td class="clsTableFormHeader">Serial Number</td>
                              <td>
                                 <beanlib:InputControl dataSource="item" type="text" name="ItemSerialNo" property="ItemSerialNo" size="30" maxLength="25"/>
                              </td>
                           </tr>                                                
                        </table>
                     </div>                  
                  </td>
               </tr>
               <tr>
                  <td>&nbsp;</td>
               </tr>               
               <tr>
                  <td>
                     <div id="NotesLayer" align="center" style="border-style:double;border-color:#999999; width:50%; height:80px">
                        <table width="100%" cellpadding="0" cellspacing="2">
                           <tr>
                              <td width="30%" valign="top" class="clsTableFormHeader">Reason for Change</td>
                              <td width="70%">
                                  <TEXTAREA name="Reason" rows="4" cols="25">&nbsp;</TEXTAREA>
                              </td>
                           </tr>
                        </table>
                     </div>                  
                  </td>
               </tr>               
               <tr>
           		  <!-- Display any messgaes -->
				  <table>
					<tr>
					  <td colspan="3">
					     <font color="red">
						     <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
						   </font>
					  </td>
					</tr>
			      </table>
               </tr>               
            </table>
	    </div>  			
	    
        <!-- Display command buttons -->  
        <table width="100%" cellpadding="0" cellspacing="0">
           <tr>
              <td>&nbsp;</td>
           </tr>               	        
		   <tr>
		      <td colspan="2">
		         <input name="save" type="button" value="Save" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.EditForm, this.name)">
		         <input name="add" type="button" value="New" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.EditForm, this.name)">
		         <input name="delete" type="button" value="Delete" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.EditForm, this.name)">
		         <input name="back" type="button" value="Back" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.EditForm, this.name)">
		      </td>
	       </tr>		
           <tr>
              <td>&nbsp;</td>
           </tr>               	        		       
        </table>            
		<input name="clientAction" type="hidden">	    
     </form>
     <br>
     <db:Dispose/>
 </body>
</html>
