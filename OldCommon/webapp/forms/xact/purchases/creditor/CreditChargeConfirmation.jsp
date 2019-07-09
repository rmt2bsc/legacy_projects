<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.bean.VwXactList" %>
<%@ page import="com.bean.XactCategory" %>
<%@ page import="com.bean.XactTypeItemActivity" %>
<%@ page import="com.constants.XactConst" %>
<%@ page import="com.constants.CreditChargesConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.action.AbstractActionHandler" %>
<%@ page import="com.bean.VwCreditorBusiness" %>

<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
   String msg = request.getAttribute(AbstractActionHandler.SINGLE_MSG) == null ? "" : (String) request.getAttribute(AbstractActionHandler.SINGLE_MSG);
   VwCreditorBusiness creditor = request.getAttribute("creditor") == null ? new VwCreditorBusiness() : (VwCreditorBusiness) request.getAttribute("creditor");
   VwXactList xact = request.getAttribute(CreditChargesConst.CLIENT_DATA_XACTEXT) == null ? new VwXactList() : (VwXactList) request.getAttribute(CreditChargesConst.CLIENT_DATA_XACTEXT);
   String pageTitle = "Expense Credit Purchase for " + creditor.getLongname() + " Confirmation";
%>   


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<title>Transaction Detail View</title>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	</head>
	
	<body>
	   <form name="DataForm"  method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactPurchase.CreditorView">
	     	<input type="hidden" name="clientAction">
				<table width="80%"  border="0" cellspacing="0" cellpadding="0">
			   <caption align="left">
			      <h3><strong><%=pageTitle%></strong></h3>
			   </caption>
						<tr>
							<td width="20%">Transaction Id: </td>
							<td width="80%">
								 <beanlib:ElementValue dataSource="<%=CreditChargesConst.CLIENT_DATA_XACTEXT %>" property="Id"/>
								 <beanlib:InputControl dataSource="creditor" type="hidden" name="CreditorId" property="CreditorId"/>
								 <beanlib:InputControl dataSource="<%=CreditChargesConst.CLIENT_DATA_XACTEXT %>" type="hidden" name="XactId" property="Id"/>
							</td>
						</tr>
						<tr>
							<td>Transaction Date: </td>
							<td>
								 <beanlib:ElementValue dataSource="<%=CreditChargesConst.CLIENT_DATA_XACTEXT %>" property="XactDate" format="MM-dd-yyyy"/> 
								 <beanlib:InputControl dataSource="<%=CreditChargesConst.CLIENT_DATA_XACTEXT %>" type="hidden" name="XactCategoryId" property="XactCategoryId"/>
							</td>
						</tr>
						<tr>
							<td>Transaction Type: </td>
							<td>
									<beanlib:ElementValue dataSource="<%=CreditChargesConst.CLIENT_DATA_XACTEXT %>" property="XactTypeName"/>
									<beanlib:InputControl dataSource="<%=CreditChargesConst.CLIENT_DATA_XACTEXT %>" type="hidden" name="XactTypeId" property="XactTypeId"/>			  
							</td>
						</tr>
						<tr>
							<td>Amount: </td>
							<td>
									<beanlib:ElementValue dataSource="<%=CreditChargesConst.CLIENT_DATA_XACTEXT %>" property="XactAmount" format="$#,##0.00;($#,##0.00)"/>
							 </td>							  
						</tr>
						<tr>			  
							<td>Tender: </td>
							<td>
								 <beanlib:ElementValue dataSource="<%=CreditChargesConst.CLIENT_DATA_XACTEXT %>" property="TenderDescription"/>
							</td>
						</tr>
						<tr>
							<td>Check Number:</td>
							<td>
								<beanlib:ElementValue dataSource="<%=CreditChargesConst.CLIENT_DATA_XACTEXT %>" property="NegInstrNo"/> 
							</td>			  
						</tr>
						<tr>
							<td>Entry Date: </td>
							<td>
								 <beanlib:ElementValue dataSource="<%=CreditChargesConst.CLIENT_DATA_XACTEXT %>" property="CreateDate" format="MM-dd-yyyy HH:mm:ss"/> 
							</td>
						</tr>		     
						<tr>
							<td>Reason:</td>
						<td>
							 <beanlib:ElementValue dataSource="<%=CreditChargesConst.CLIENT_DATA_XACTEXT %>" property="Reason"/> 
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

					 <beanlib:LoopRows bean="itemObj" list="<%=CreditChargesConst.CLIENT_DATA_XACTITEMS %>"> 
						 <tr bgcolor="white">
							 <td>
									<beanlib:ElementValue dataSource="itemObj" property="XactTypeItemName"/> 
							 </td>
							 <td align="right">
									 <beanlib:ElementValue dataSource="itemObj" property="Amount" format="$#,##0.00;($#,##0.00)"/> 
							 </td>
							 <td>&nbsp;</td>
							 <td align="left">
									 <beanlib:ElementValue dataSource="itemObj" property="Description"/> 
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
									 <font color="red"><%=msg%></font>
								</td>
						</tr>
					</table>
					<br>

					<!-- Setup Command button area -->
					<table width="50%"  border="0" cellspacing="2" cellpadding="0">
						<tr>
							<td algin="left">
							     <gen:Evaluate expression="<%=xact.getXactSubtypeId() %>">
							        <gen:When expression="999">
							           <input name="<%=GeneralConst.REQ_SEARCH%>" type="button" value="Back" style="width: 90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
							        </gen:When>
							        <gen:WhenElse>
							           <input name="<%=XactConst.REQ_REVERSE%>" type="button" value="Reverse" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
							           <input name="<%=GeneralConst.REQ_SEARCH%>" type="button" value="Back" style="width: 90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
							        </gen:WhenElse>
							     </gen:Evaluate>
							</td>	  
						</tr>
				 </table>
	   </form>
	   <db:Dispose/>
	</body>
</html>


