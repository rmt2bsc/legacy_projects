<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bean.VwXactList" %>
<%@ page import="com.bean.XactType" %>
<%@ page import="com.bean.XactTypeItemActivity" %>
<%@ page import="com.xact.XactConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.action.AbstractActionHandler" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
   VwXactList xact = request.getAttribute(XactConst.CLIENT_DATA_XACT) == null ? new VwXactList() : (VwXactList) request.getAttribute(XactConst.CLIENT_DATA_XACT);
   XactType xactType = request.getAttribute(XactConst.CLIENT_DATA_XACTTYPE) == null ? new XactType() : (XactType) request.getAttribute(XactConst.CLIENT_DATA_XACTTYPE);
   String strXactTypeId = String.valueOf(xact.getXactTypeId());
   String xactTypeCriteria = "xact_catg_id = " + xactType.getXactCatgId();
   String xactTenderCriteria = "xact_code_grp_id = 2";
   String xactTypeItemCriteria = "xact_type_id = " + strXactTypeId;
   String strSelGLAcctId = "0";
   String pageTitle = "Cash Disbursement View";
   String xactTypeId;
   XactTypeItemActivity obj;
   String strSelValue = null;
   String jspOrigin = request.getParameter("jspOrigin");
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
	   <form name="DataForm"  method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/DisbursementsGeneral.View">
			<table width="80%"  border="0" cellspacing="0" cellpadding="0">
			   <caption align="left">
			      <h3><strong><%=pageTitle %>&nbsp;<beanlib:InputControl value="#xactType.Description"/></strong></h3>
			   </caption>
			  <tr>
			    <td width="20%">Transaction Id: </td>
				<td width="80%">
				   <beanlib:InputControl value="#xact.Id"/>
				   <beanlib:InputControl type="hidden" name="XactId" value="#xact.Id"/>
				</td>
			  </tr>
			  <tr>
  				<td>Transaction Date: </td>
				<td>
				   <beanlib:InputControl value="#xact.XactDate" format="MM-dd-yyyy"/> 
				   <beanlib:InputControl type="hidden" name="XactCategoryId" value="#xact.XactCatgId"/>
				</td>
			  </tr>
			  <tr>
				<td>Transaction Type: </td>
				<td>
                    <beanlib:InputControl value="#xact.XactTypeName"/>
				    <beanlib:InputControl type="hidden" name="XactTypeId" value="#xact.XactTypeId"/>			  
				</td>
			  </tr>
			  <tr>
				<td>Amount: </td>
				<td>
 					<beanlib:InputControl value="#xact.XactAmount" format="$#,##0.00;($#,##0.00)"/>
                </td>							  
			  </tr>
              <tr>			  
				<td>Tender: </td>
				<td>
				   <beanlib:InputControl value="#xact.TenderDescription"/>
				</td>
			  </tr>
			  <tr>
 		     	<td>Check Number:</td>
 		     	<td>
 		     	  <beanlib:InputControl value="#xact.NegInstrNo"/> 
 		   		</td>			  
		     </tr>
			  <tr>
  				<td>Post Date: </td>
				<td>
				   <beanlib:InputControl value="#xact.CreateDate" format="MM-dd-yyyy HH:mm:ss"/> 
				   <beanlib:InputControl type="hidden" name="PostedDate" value="#xact.PostedDate"/>
				</td>
			  </tr>		     
        <tr>
			    <td>Source/Reason:</td>
				<td>
				   <beanlib:InputControl value="#xact.Reason"/> 
				</td>
			</tr>		     
			<tr>
			  <td>Confirmation Number:</td>
				<td>
				   <beanlib:InputControl value="#xact.ConfirmNo"/>
				</td>
			</tr>
			<tr>
					<td colspan="2">
					   <gen:Evaluate expression="#xact.DocumentId">
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
			<table width="80%"  border="0" cellspacing="2" cellpadding="0">
			   <tr>
				  <td colspan="2">&nbsp;</td>
			   </tr>	   
			  <tr>
				<td valign="top">
				  <div id="Layer1" style="width:100%; height:450px; z-index:1; overflow:auto; border-style:inset; border-color:activeborder; background-color: buttonface" >
					<table width="97%" border="0" cellspacing="0" cellpadding="0">
					<caption align="left" style="background-color:#0099FF">
						<strong>Transaction Item Details</strong>
					</caption>
					   <tr>
						   <td width="35%" class="clsTableListHeader" style="text-align:left">Item</td>
						   <td width="12%" class="clsTableListHeader" style="text-align:right">Amount</td>
						   <td width="5%" class="clsTableListHeader">&nbsp;</td>
						   <td width="45%" class="clsTableListHeader" style="text-align:left">Description</td>
					   </tr>
					   
					   <beanlib:LoopRows bean="itemObj" list="xactTypeItems"> 
					     <beanlib:InputControl dataSource="itemObj" type="hidden" name="itemcount" value="rowid"/>
					     <beanlib:InputControl dataSource="itemObj" type="hidden" name="XactTypeItemActvId" value="#itemObj.Id" uniqueName="yes"/>
					     <beanlib:InputControl dataSource="itemObj" type="hidden" name="XactId" value="#itemObj.XactId" uniqueName="yes"/>	
					     <beanlib:InputControl dataSource="itemObj" type="hidden" name="Amount" value="#itemObj.ItemAmount" uniqueName="yes"/>
					     <beanlib:InputControl dataSource="itemObj" type="hidden" name="XactItemId" value="#itemObj.XactTypeItemId" uniqueName="yes"/>
					     <beanlib:InputControl dataSource="itemObj" type="hidden" name="Description" value="#itemObj.ItemName" uniqueName="yes"/>
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
				  </div>
				</td>
			  </tr>
			</table>
            
		 	<!-- Setup Command button area -->
			<table width="50%"  border="0" cellspacing="2" cellpadding="0">
		   	<tr>
		  		<td algin="left">
  				      <input name="<%=XactConst.REQ_REVERSE%>" type="button" value="Reverse" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
            	  <input type="button" name="<%=GeneralConst.REQ_ADD%>" value="New" style="width: 90" onClick="handleAction('_self', document.DataForm, this.name)">
            	  <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style="width: 90" onClick="handleAction('_self', document.DataForm, this.name)">
        	</td>	  
		   	</tr>
		 </table>
		 <input type="hidden" name="clientAction">
	   </form>
	</body>
</html>


