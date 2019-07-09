<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bean.Xact" %>
<%@ page import="com.bean.XactType" %>
<%@ page import="com.bean.XactCategory" %>
<%@ page import="com.bean.XactTypeItemActivity" %>
<%@ page import="com.action.AbstractActionHandler" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.xact.XactConst" %>
<%@ page import="com.xact.disbursements.DisbursementsConst" %>


<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<title>Cash Disbursement Details - Add</title>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	  <script type='text/javascript' language='javascript' src="<%=APP_ROOT%>/js/datetimepicker.js"></script>
	  <script Language="JavaScript">
	      function setFocus() {
		      if (DataForm.itemcount != null) {
			      var row;
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

<%
   String pageTitle = "Cash Disbursement Details - Add";
%>   

 	<body onLoad="setFocus()">
	   <form name="DataForm"  method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/DisbursementsGeneral.Edit">
			<table width="80%"  border="0" cellspacing="0" cellpadding="0">
			   <caption style="text-align:left">
			      <h3><strong><%=pageTitle%></strong></h3>
			   </caption>
			  <tr>
  			<td width="15%" class="clsTableFormHeader">Trans. Date: </td>
				<td width="18%">
				   <beanlib:InputControl id="transDate" type="text" name="XactDate" value="#xact.XactDate" format="MM-dd-yyyy"/>
				   <a href="javascript:NewCal('transDate','mmddyyyy')"><img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>
				   <beanlib:InputControl type="hidden" name="XactId" value="#xact.XactId" readOnly="yes"/>
				   <beanlib:InputControl type="hidden" name="XactCategoryId" value="#xactType.XactCatgId"/>
				   <beanlib:InputControl type="hidden" name="PostedDate" value="#xact.PostedDate" format="MM-dd-yyyy"/>
				</td>
				<td width="15%" class="clsTableFormHeader">Amount: </td>
				<td width="18%">
 					<beanlib:InputControl type="text" name="XactAmount" value="#xact.XactAmount"/>
        </td>				
				<td width="15%" class="clsTableFormHeader">Tender: </td>
				<td width="18%">
   				   <beanlib:InputControl dataSource="<%=XactConst.CLIENT_DATA_TENDERLIST %>"
																	 type="select"
																	 name="TenderId"
																	 codeProperty="XactCodeId"
																	 displayProperty="Description"
																	 selectedValue="#xact.TenderId"/>
				</td>
			  </tr>
			  <tr>
 		     	<td class="clsTableFormHeader">Tender No.:</td>
 		     	<td>
 		     	  <beanlib:InputControl type="text" name="NegInstrNo" value="#xact.NegInstrNo"/>
 		   		</td>			  
				<td class="clsTableFormHeader">Trans. Type: </td>
				<td>&nbsp;Cash Disburesments
				    <beanlib:InputControl type="hidden" name="XactTypeId" value="#xactType.XactTypeId"/>
				</td>			    
				<td colspan="2">&nbsp;</td>
		   </tr>
	   	 <tr>
			  <td class="clsTableFormHeader">Source/Reason:</td>
				<td align="left" colspan="5">
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
						     <td width="35%" class="clsTableListHeader" style="text-align:left">Expense Type</td>
						     <td width="17%" class="clsTableListHeader">Amount</td>
						     <td width="45%" class="clsTableListHeader" style="text-align:center">Item Name</td>
					   </tr>
					   
					   <beanlib:LoopRows bean="itemObj" list="<%=XactConst.CLIENT_DATA_XACTITEMS%>"> 
						   <tr bgcolor="white">
						       <td class="clsTableListHeader">
				           <beanlib:InputControl dataSource="itemObj" type="checkbox" name="selCbx" value="rowid" uniqueName="yes"/>
				           <beanlib:InputControl dataSource="itemObj" type="hidden" name="itemcount" value="rowid"/>
								   <beanlib:InputControl dataSource="itemObj" type="hidden" name="XactTypeItemActvId" value="#itemObj.XactTypeItemActvId" uniqueName="yes"/>
								   <beanlib:InputControl dataSource="itemObj" type="hidden" name="XactId" value="#itemObj.XactId" uniqueName="yes"/>	
							   <td>
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
								   <beanlib:InputControl dataSource="itemObj" type="text" name="Amount" value="#itemObj.Amount" style="text-align: right" uniqueName="yes"/>
							   </td>
							   <td align="left">
								   <beanlib:InputControl dataSource="itemObj" type="text" name="Description" value="#itemObj.Description" style="width: 100%" maxLength="50" uniqueName="yes"/>
							   </td>
						   </tr>	
					   </beanlib:LoopRows>
					</table>
				  </div>
				</td>
			  </tr>
			</table>
            
            <!--  Setup message area -->
			<table>
				<tr>
				  <td colspan="4">
				     <font color="red">
					     <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
					   </font>
				  </td>
				</tr>
				<tr>
				  <td>
				     <font color="black">Total Items Added:&nbsp;&nbsp;</font>
				  </td>
				  <td>
				     <font color="red">
					      <beanlib:InputControl value="#TotalCount" format="#,##0"/>
					   </font>
				  </td>
				  <td>
				     <font color="black">&nbsp;&nbsp;&nbsp;&nbsp;Total Amount Spent:&nbsp;&nbsp;</font>
				  </td>
				  <td>
				     <font color="red">
					      <beanlib:InputControl value="#TotalAmount" format="$#,##0.00"/>
					   </font>
				  </td>
				</tr>
		    </table>
		 	<br>
		 	
		 	<!-- Setup Command button area -->
			<table width="80%"  border="0" cellspacing="2" cellpadding="0">
		   	<tr>
		  		<td style="text-align:left">
             <input name="<%=DisbursementsConst.REQ_ADDITEM %>" type="button" value="Add Item" style="width: 90" onClick="handleAction('_self', document.DataForm, this.name)">
			 <input name="<%=GeneralConst.REQ_SAVE %>" type="button" value="Apply" style="width: 90" onClick="handleAction('_self', document.DataForm, this.name)">
             <input name="<%=GeneralConst.REQ_ADD %>" type="button" value="New" style="width: 90" onClick="handleAction('_self', document.DataForm, this.name)">
             <input name="<%=GeneralConst.REQ_BACK %>" type="button" value="Back" style="width: 90" onClick="handleAction('_self', document.DataForm, this.name)">
         	</td>	  
		   	</tr>
		 </table>
		 <input name="clientAction" type="hidden">
	   </form>
	</body>
</html>


