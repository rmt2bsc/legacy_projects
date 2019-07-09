<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bean.Xact" %>
<%@ page import="com.bean.XactType" %>
<%@ page import="com.bean.XactCategory" %>
<%@ page import="com.action.AbstractActionHandler" %>
<%@ page import="com.xact.XactConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.xact.purchases.creditor.CreditorPurchasesConst" %>

<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
   Xact xact = request.getAttribute("xact") == null ? new Xact() : (Xact) request.getAttribute("xact");
   XactType xactType = request.getAttribute("xactType") == null ? new XactType() : (XactType) request.getAttribute("xactType");
   String msg = request.getAttribute(AbstractActionHandler.SINGLE_MSG) == null ? "" : (String) request.getAttribute(AbstractActionHandler.SINGLE_MSG);
   String strXactTypeId = String.valueOf(xactType.getXactTypeId());
   String strXactCatgId = String.valueOf(xactType.getXactCatgId());
   String xactTypeCriteria = "xact_catg_id = " + strXactCatgId;
   String xactTypeItemCriteria = "xact_id = " + xact.getXactId();
   String pageTitle = "Creditor Purchases Transaction Edit";
   String xactTypeId;
   String strSelValue = null;
%>   

 <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
 <db:datasource id="xactTypeDso" 
				classId="com.api.DataSourceApi" 
				connection="con" 
				query="XactTypeView" 
				where="<%= xactTypeCriteria %>"
				order="description" 
				type="xml"/>
				
 <db:datasource id="xactTenderDso" 
				classId="com.api.DataSourceApi" 
				connection="con" 
				query="XactCodesView" 
				where="xact_code_grp_id = 2"
				order="description" 
				type="xml"/>					   
				   				

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<title><%=pageTitle%></title>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
    <script type='text/javascript' language='javascript' src="<%=APP_ROOT%>/js/datetimepicker.js"></script>
    <script Language="JavaScript">
	      function setFocus() {
		      if (DataForm.itemcount != null) {
			      var row = -1;
			      if (DataForm.elements['itemcount'].length == null) {
		    	  		row = 0;
			      }
			      if (DataForm.elements['itemcount'].length > 1) {
			    	    row = DataForm.elements['itemcount'].length - 1;
			      }
		    	  document.getElementById("item" + row).focus();
			      return;
			      			      
		      }
	    	  document.getElementById("transDate").focus();
	      }
	  </script>
	</head>
	
	<body onLoad="setFocus()">
	   <form name="DataForm"  method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/PurchasesCreditor.Edit">
			<table width="80%"  border="0" cellspacing="0" cellpadding="0">
				<caption align="left">
				    <h3><strong><%=pageTitle%></strong></h3>
				</caption>
				<tr>
					<td width="15%" class="clsTableFormHeader">Transaction Date: </td>
					<td width="18%">
						 <beanlib:InputControl id="transDate" type="text" name="XactDate" value="#xact.XactDate" format="MM-dd-yyyy"/>
						 <a href="javascript:NewCal('transDate','mmddyyyy')"><img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>
						 <beanlib:InputControl type="hidden" name="XactId" value="#xact.XactId" readOnly="yes"/>
						 <beanlib:InputControl type="hidden" name="XactCategoryId" value="#xactCatg.XactCatgId"/>
						 <beanlib:InputControl type="hidden" name="XactTypeId" value="#xactType.XactTypeId"/>
					</td>
					<td width="15%" class="clsTableFormHeader">Amount: </td>
					<td width="18%">
						<beanlib:InputControl type="text" name="XactAmount" value="#xact.XactAmount"/>
					</td>				
					<td width="15%" class="clsTableFormHeader">Tender: </td>
					<td width="18%">
							 <db:InputControl dataSource="xactTenderDso"
																type="select"
																name="TenderId"
																codeProperty="XactCodeId"
																displayProperty="Description"
																selectedValue="#xact.TenderId"/>
					</td>
				</tr>
		
			</table>
			
			<br>
			<table width="40%"  border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td width="27%" class="clsTableFormHeader">Creditor: </td>		
					<td width="73%">
							 <xml:InputControl dataSource="<%=CreditorPurchasesConst.CLIENT_DATA_CREDIOTRLIST %>"
																 type="select"
																 name="CreditorId"
																 query="//Creditors/CreditorExt"
																 codeProperty="creditorId"
																 displayProperty="name"
																 selectedValue="#creditor.CreditorId"/>	
					</td>  
			  </tr>
				<tr>
					<td class="clsTableFormHeader"  valign="top">Source/Reason:</td>
					<td>
						 <beanlib:InputControl type="text" name="Reason" value="#xact.Reason" size="50" maxLength="100"/>
					</td>
				</tr>
				<tr>
			  	<td class="clsTableFormHeader">Confirmation Number:</td>
					<td align="left" colspan="5">
				  	 <beanlib:InputControl type="text" name="ConfirmNo" value="#xact.ConfirmNo" size="40" maxLength="100"/>
					</td>
				</tr>
			</table>
			<br>

			<!--  Build transaction item list -->
			<table width="80%"  border="0" cellspacing="2" cellpadding="0">
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>	   
				<tr>
					<td valign="top">
						<div id="Layer1" style="width:100%; height:450px; z-index:1; overflow:auto; border-style:inset; border-color:activeborder; background-color: buttonface" >
							<table width="97%" border="1" cellspacing="0" cellpadding="0">
								<caption align="left" style="background-color:#0099FF">
									<strong>Transaction Item Details</strong>
								</caption>
								<tr>
									 <td width="3%" class="clsTableListHeader">&nbsp;</td>
									 <td width="35%" class="clsTableListHeader" style="text-align:left">Item</td>
									 <td width="17%" class="clsTableListHeader">Amount</td>
									 <td width="45%" class="clsTableListHeader" style="text-align:left>Description">Description</td>
							  </tr>

							  <beanlib:LoopRows bean="itemObj" list="<%=XactConst.CLIENT_DATA_XACTITEMS %>"> 
							    <tr bgcolor="white">
								    <td class="clsTableListHeader">
										 <beanlib:InputControl type="checkbox" name="selCbx" value="rowid" uniqueName="yes"/>
										 <beanlib:InputControl type="hidden" name="itemcount" value="rowid"/>
										 <beanlib:InputControl type="hidden" name="XactTypeItemActvId" value="#itemObj.XactTypeItemActvId" uniqueName="yes"/>
										 <beanlib:InputControl type="hidden" name="XactId" value="#itemObj.XactId" uniqueName="yes"/>	
                    </td>										 
							      <td>
							           <%
								         //obj = (XactTypeItemActivity) pageContext.getAttribute("itemObj");
								         //strSelValue = String.valueOf(obj.getXactTypeItemId()); 
							           %>
							 		   <beanlib:InputControl dataSource="<%=XactConst.CLIENT_DATA_XACTITEMTYPELIST%>" 
							 		                         id="item"
															    				 type="select" 
																			     name="XactItemId" 
																			     codeProperty="XactItemId" 
																			     displayProperty="Name" 
																			     uniqueName="yes"
																			     style="width: 100%"
																			     selectedValue="#itemObj.XactItemId"/>																 
								   </td>
								   <td align="left">
									   <beanlib:InputControl type="text" name="Amount" value="#itemObj.Amount" style="text-align: right" uniqueName="yes"/>
								   </td>
								   <td align="left">
									   <beanlib:InputControl type="text" name="Description" value="#itemObj.Description" style="width: 100%" maxLength="50" uniqueName="yes"/>
								   </td>
						 	  </tr>	
							  </beanlib:LoopRows>
							</table>
						</div>
					</td>
				</tr>
			</table>

			<!--  Setup message area -->
			<table width="80%"  border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="4">
						<font color="red">
					     <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
	   			 </font>
					</td>
				</tr>
			</table>
			<table width="32%"  border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td width="25%">
				     <font color="black">Total Items Added:&nbsp;&nbsp;</font>
				  </td>
				  <td align="left">
				     <font color="red">
					      <beanlib:InputControl value="#TotalCount" format="#,##0"/>
					   </font>
				  </td>
				  <td width="32%">
				     <font color="black">&nbsp;&nbsp;&nbsp;&nbsp;Total Amount Spent:&nbsp;&nbsp;</font>
				  </td>
				  <td align="left">
				     <font color="red">
					      <beanlib:InputControl value="#TotalAmount" format="$#,##0.00"/>
					   </font>
				  </td>
				</tr>
			</table>
			<br>

			<!-- Setup Command button area -->
			<table width="50%"  border="0" cellspacing="2" cellpadding="0">
				<tr>
					<td algin="left">   
					  <input type="button" name="<%=CreditorPurchasesConst.REQ_ADDITEM%>" value="Add Item" style="width: 90" onClick="handleAction('_self', document.DataForm, this.name)">
					  <input type="button" name="<%=GeneralConst.REQ_SAVE %>" value="Save" style="width: 90" onClick="handleAction('_self', document.DataForm, this.name)">
					  <input name="<%=GeneralConst.REQ_ADD %>" type="button" value="New" style="width: 90" onClick="handleAction('_self', document.DataForm, this.name)">
						<input type="button" name="<%=GeneralConst.REQ_SEARCH%>" value="Back" style="width: 90" onClick="handleAction('_self', document.DataForm, this.name)">
					</td>	  
				</tr> 
			</table> 
		 <input type="hidden" name="clientAction">
	   </form>
	   <db:Dispose/>
	</body>
</html>


