<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
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
	  String pageTitle = "Vendor Inventory Item Edit";
	  String msg = request.getAttribute("msg") == null ? "" : (String) request.getAttribute("msg");
  %>
				
  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

     <!--  Begin Header section -->
     <form name="EditForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactPurchase.VendorItemEdit">
        <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:60%; height:150px">
           <table width="100%" cellpadding="0" cellspacing="0" border="0">
               <tr>
										<td width="25%" class="clsTableFormHeader">Vendor Number</td>
										<td width="75%">
               					  <beanlib:ElementValue dataSource="vendor" property="CreditorId"/>
               					  <beanlib:InputControl dataSource="vendor" type="hidden" name="VendorId" property="CreditorId"/>
                    </td>
               </tr>
               <tr>
										<td class="clsTableFormHeader">Vendor Name</td>
										<td>
                           <beanlib:ElementValue dataSource="vendor" property="Longname"/>
                    </td>
						   </tr>                           
							 <tr>
									<td class="clsTableFormHeader">Item Id</td>
									<td>
									    <beanlib:ElementValue dataSource="itemmaster" property="Id"/>
											<beanlib:InputControl dataSource="itemmaster" type="hidden" name="ItemMasterId" property="Id"/>
									</td>
							 </tr>
							 <tr>
									<td class="clsTableFormHeader">Item Description</td>
									<td>
									    <beanlib:ElementValue dataSource="itemmaster" property="Description"/>
									</td>
							 </tr>							 
							 
							 <tr>
									<td class="clsTableFormHeader">Vendor Item No.</td>
									<td>
										 <beanlib:InputControl dataSource="vendoritem" type="text" name="VendorItemNo" property="VendorItemNo" size="30" maxLength="25"/>
									</td>
							 </tr>                                                
							 <tr>
									<td class="clsTableFormHeader">Serial Number</td>
									<td>
										 <beanlib:InputControl dataSource="vendoritem" type="text" name="ItemSerialNo" property="ItemSerialNo" size="30" maxLength="25"/>
									</td>
							 </tr>                                                							 
							 <tr>
									<td class="clsTableFormHeader">Unit Cost</td>
									<td>
										 <beanlib:InputControl dataSource="vendoritem" type="text" name="UnitCost" property="UnitCost" size="30" maxLength="25"/>
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
		         <input name="<%=ItemConst.REQ_VEND_ITEM_SAVE %>" type="button" value="Apply" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.EditForm, this.name)">
		         <input name="<%=ItemConst.REQ_VEND_ITEM_VIEW %>" type="button" value="Back" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.EditForm, this.name)">
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
