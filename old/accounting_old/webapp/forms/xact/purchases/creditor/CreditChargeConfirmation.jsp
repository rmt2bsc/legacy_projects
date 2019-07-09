<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.xact.XactConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
   String pageTitle = "Creditor Purchases Confirmation";
%>   


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<title><%=pageTitle %></title>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	</head>
	
	<body>
	   <form name="DataForm"  method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/PurchasesCreditor.View">
	     	<input type="hidden" name="clientAction">
				<table width="80%"  border="0" cellspacing="0" cellpadding="0">
			   <caption align="left">
			      <h3><strong><%=pageTitle%></strong></h3>
			   </caption>
						<tr>
							<td width="20%">Transaction Id: </td>
							<td width="80%">
								 <beanlib:InputControl value="#xactext.Id"/>
								 <beanlib:InputControl type="hidden" name="CreditorId" value="#creditor.CreditorId"/>
								 <beanlib:InputControl type="hidden" name="XactId" value="#xactext.Id"/>
							</td>
						</tr>
						<tr>
							<td>Creditor Name: </td>
							<td>
								 <strong><beanlib:InputControl value="#creditor.Name"/></strong>
							</td>
						</tr>
						<tr>
							<td>Transaction Date: </td>
							<td>
								 <beanlib:InputControl value="#xactext.XactDate" format="MM-dd-yyyy"/> 
								 <beanlib:InputControl type="hidden" name="XactCategoryId" value="#xactext.XactCatgId"/>
							</td>
						</tr>
						<tr>
							<td>Transaction Type: </td>
							<td>
									<beanlib:InputControl value="#xactext.XactTypeName"/>
									<beanlib:InputControl type="hidden" name="XactTypeId" value="#xactext.XactTypeId"/>			  
							</td>
						</tr>
						<tr>
							<td>Amount: </td>
							<td>
									<beanlib:InputControl value="#xactext.XactAmount" format="$#,##0.00;($#,##0.00)"/>
							 </td>							  
						</tr>
						<tr>			  
							<td>Tender: </td>
							<td>
								 <beanlib:InputControl value="#xactext.TenderDescription"/>
							</td>
						</tr>
						<tr>
							<td>Check Number:</td>
							<td>
								<beanlib:InputControl value="#xactext.NegInstrNo"/> 
							</td>			  
						</tr>
						<tr>
							<td>Entry Date: </td>
							<td>
								 <beanlib:InputControl value="#xactext.CreateDate" format="MM-dd-yyyy HH:mm:ss"/> 
							</td>
						</tr>		     
						<tr>
							<td>Source/Reason:</td>
						<td>
							 <beanlib:InputControl value="#xactext.Reason"/> 
						</td>
					</tr>		    
					<tr>
			  		<td>Confirmation Number:</td>
						<td>
				   		<beanlib:InputControl value="#xactext.ConfirmNo"/>
						</td>
					</tr> 
					<tr>
						<td colspan="2">
						   <gen:Evaluate expression="#xactext.DocumentId">
						   		<gen:When expression="0">
						   		   <font color="red">Receipt is not available for view</font>
						   		</gen:When>
					   			<gen:WhenElse>
						   			 <a href="<%=APP_ROOT%>/DocumentViewer.jsp?contentId=<beanlib:InputControl value='#xact.DocumentId'/>" target="_blank">View Receipt</a>
						 			</gen:WhenElse>
					 		 </gen:Evaluate>
						</td>
					</tr>
				</table>
				<br>

						<!--  Build transaction item list -->
				<table width="50%"  border="0" cellspacing="0" cellpadding="0">
					 <tr>
						 <td width="35%" class="clsTableListHeader" style="text-align:left">Item</td>
						 <td width="12%" class="clsTableListHeader" style="text-align:right">Amount</td>
						 <td width="5%" class="clsTableListHeader">&nbsp;</td>
						 <td width="45%" class="clsTableListHeader" style="text-align:left">Description</td>
					 </tr>

					 <beanlib:LoopRows bean="itemObj" list="<%=XactConst.CLIENT_DATA_XACTITEMS %>"> 
						 <tr bgcolor="white">
							 <td>
									<beanlib:InputControl value="#itemObj.XactTypeItemName"/> 
							 </td>
							 <td align="right">
									 <beanlib:InputControl value="#itemObj.ItemAmount" format="$#,##0.00;($#,##0.00)"/> 
							 </td>
							 <td>&nbsp;</td>
							 <td align="left">
									 <beanlib:InputControl value="#itemObj.ItemName"/> 
							 </td>
						 </tr>	
						 </beanlib:LoopRows>
					</table>

					<!--  Setup message area -->
					<table width="80%"  border="0" cellspacing="0" cellpadding="0">
						<tr>
								<td>&nbsp;</td>
						</tr>			
						<tr>
							<td>
								 <font color="red">
								     <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
				   			 </font>
							</td>
						</tr>
					</table>
					<br>

					<!-- Setup Command button area -->
					<table width="50%"  border="0" cellspacing="2" cellpadding="0">
						<tr>
							<td algin="left">
							     <gen:Evaluate expression="#xactext.XactSubtypeId">
							        <gen:When expression="999">
							           <input name="<%=GeneralConst.REQ_SEARCH%>" type="button" value="Back" style="width: 90" onClick="handleAction('_self', document.DataForm, this.name)">
							        </gen:When>
							        <gen:WhenElse>
							           <input name="<%=XactConst.REQ_REVERSE%>" type="button" value="Reverse" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
							           <input name="<%=GeneralConst.REQ_ADD %>" type="button" value="New" style="width: 90" onClick="handleAction('_self', document.DataForm, this.name)">
							           <input name="<%=GeneralConst.REQ_SEARCH%>" type="button" value="Back" style="width: 90" onClick="handleAction('_self', document.DataForm, this.name)">
							        </gen:WhenElse>
							     </gen:Evaluate>
							</td>	  
						</tr>
				 </table>
	   </form>
	</body>
</html>


