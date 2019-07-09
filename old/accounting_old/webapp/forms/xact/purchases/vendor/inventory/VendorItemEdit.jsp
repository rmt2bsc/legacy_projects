<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.inventory.ItemConst" %>
<%@ page import="com.xact.purchases.vendor.inventory.VendorItemConst" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>
<%
  String pageTitle = "Vendor Inventory Item Edit";
  String msg = request.getAttribute("msg") == null ? "" : (String) request.getAttribute("msg");
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
       
  
				
  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

     <!--  Begin Header section -->
     <form name="EditForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/PurchasesVendor.ItemEdit">
        <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:60%; height:150px">
           <table width="100%" cellpadding="0" cellspacing="0" border="0">
               <tr>
										<td width="25%" class="clsTableFormHeader">Vendor Number</td>
										<td width="75%">
               					  <beanlib:InputControl value="#vendor.CreditorId"/>
               					  <beanlib:InputControl type="hidden" name="CreditorId" value="#vendor.CreditorId"/>
                    </td>
               </tr>
               <tr>
										<td class="clsTableFormHeader">Vendor Name</td>
										<td>
                           <beanlib:InputControl value="#vendor.Name"/>
                    </td>
						   </tr>                           
							 <tr>
									<td class="clsTableFormHeader">Item Id</td>
									<td>
									    <beanlib:InputControl value="#itemmaster.ItemId"/>
											<beanlib:InputControl type="hidden" name="ItemId" value="#itemmaster.ItemId"/>
									</td>
							 </tr>
							 <tr>
									<td class="clsTableFormHeader">Item Description</td>
									<td>
									    <beanlib:InputControl value="#itemmaster.Description"/>
									</td>
							 </tr>							 
							 
							 <tr>
									<td class="clsTableFormHeader">Vendor Item No.</td>
									<td>
										 <beanlib:InputControl type="text" name="VendorItemNo" value="#vendoritem.VendorItemNo" size="30" maxLength="25"/>
									</td>
							 </tr>                                                
							 <tr>
									<td class="clsTableFormHeader">Serial Number</td>
									<td>
										 <beanlib:InputControl type="text" name="ItemSerialNo" value="#vendoritem.ItemSerialNo" size="30" maxLength="25"/>
									</td>
							 </tr>                                                							 
							 <tr>
									<td class="clsTableFormHeader">Unit Cost</td>
									<td>
										 <beanlib:InputControl type="text" name="UnitCost" value="#vendoritem.UnitCost" size="30" maxLength="25"/>
									</td>
							 </tr>                                                							 							 
							 
							 
               <tr>
                  <td colspan="2">
	                  <font color="red">
	                     <%=msg%>
	                  </font>
                  </td>
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
		         <input name="<%=VendorItemConst.REQ_VEND_ITEM_SAVE %>" type="button" value="Apply" style="width:90" onClick="handleAction('_self', document.EditForm, this.name)">
		         <input name="<%=VendorItemConst.REQ_VEND_ITEM_VIEW %>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.EditForm, this.name)">
		      </td>
	       </tr>		
           <tr>
              <td>&nbsp;</td>
           </tr>               	        		       
        </table>            
		<input name="clientAction" type="hidden">	    
     </form>
     <br>
     
 </body>
</html>
