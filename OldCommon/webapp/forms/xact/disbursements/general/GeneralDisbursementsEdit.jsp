<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bean.Xact" %>
<%@ page import="com.bean.XactType" %>
<%@ page import="com.bean.XactCategory" %>
<%@ page import="com.bean.XactTypeItemActivity" %>
<%@ page import="com.action.AbstractActionHandler" %>
<%@ page import="com.constants.XactConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.DisbursementsConst" %>
<%@ page import="com.constants.RMT2ServletConst" %>


<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<title>Transaction Details - Add</title>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	</head>

<%
   Xact xact = request.getAttribute(XactConst.CLIENT_DATA_XACT) == null ? new Xact() : (Xact) request.getAttribute(XactConst.CLIENT_DATA_XACT);
   XactType xactType = request.getAttribute(XactConst.CLIENT_DATA_XACTTYPE) == null ? new XactType() : (XactType) request.getAttribute(XactConst.CLIENT_DATA_XACTTYPE);
   String strXactTypeId = String.valueOf(xactType.getId());
   String strXactCatgId = String.valueOf(xactType.getXactCategoryId());
   String xactTypeCriteria = "xact_category_id = " + strXactCatgId;
   String xactTenderCriteria = "group_id = 2";
   String xactTypeItemCriteria = "xact_type_id = " + strXactTypeId;
   String strSelGLAcctId = "0";
   String pageTitle = xactType.getDescription() + " - New";
   String xactTypeId;
   XactTypeItemActivity obj;
   String strSelValue = null;
%>   

 <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
 <db:datasource id="xactTypeDso" 
				classId="com.api.DataSourceApi" 
				connection="con" 
				query="XactTypeView" 
				where="<%= xactTypeCriteria %>"
				order="description" type="xml"/>
				
 <db:datasource id="xactTenderDso" 
				classId="com.api.DataSourceApi" 
				connection="con" 
				query="XactCodesView" 
				where="<%= xactTenderCriteria %>"
				order="description" 
				type="xml"/>					   


	
	<body>
	   <form name="DataForm"  method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactDisburse.DisbursementEdit">
			<table width="80%"  border="0" cellspacing="0" cellpadding="0">
			   <caption style="text-align:left">
			      <h3><strong><%=pageTitle%></strong></h3>
			   </caption>
			  <tr>
  				<td width="15%" class="clsTableFormHeader">Trans. Date: </td>
				<td width="18%">
				   <beanlib:InputControl dataSource="<%=XactConst.CLIENT_DATA_XACT%>" type="text" name="XactDate" property="XactDate" format="MM-dd-yyyy"/>
				   <beanlib:InputControl dataSource="<%=XactConst.CLIENT_DATA_XACT %>" type="hidden" name="XactId" property="Id" readOnly="yes"/>
				   <beanlib:InputControl dataSource="<%=XactConst.CLIENT_DATA_XACTTYPE %>" type="hidden" name="XactCategoryId" property="XactCategoryId"/>
				   <beanlib:InputControl dataSource="<%=XactConst.CLIENT_DATA_XACT %>" type="hidden" name="PostedDate" property="PostedDate" format="MM-dd-yyyy"/>
				</td>
				<td width="15%" class="clsTableFormHeader">Amount: </td>
				<td width="18%">
 					<beanlib:InputControl dataSource="<%=XactConst.CLIENT_DATA_XACT %>" type="text" name="XactAmount" property="XactAmount"/>
        </td>				
				<td width="15%" class="clsTableFormHeader">Tender: </td>
				<td width="18%">
   				   <db:InputControl dataSource="xactTenderDso"
									 type="select"
									 name="TenderId"
									 codeProperty="Id"
									 displayProperty="Description"
									 selectedValue="<%=String.valueOf(xact.getTenderId()) %>"/>
				</td>
			  </tr>
			  <tr>
 		     	<td class="clsTableFormHeader">Tender No.:</td>
 		     	<td>
 		     	  <beanlib:InputControl dataSource="<%=XactConst.CLIENT_DATA_XACT %>" type="text" name="NegInstrNo" property="NegInstrNo"/>
 		   		</td>			  
				<td class="clsTableFormHeader">Trans. Type: </td>
				<td>
            <db:Lookup dataSource="" 
							 	masterCodeName=""
							 	masterCodeValue="<%=strXactTypeId%>"
							 	type="1"
							 	lookupSource="xactTypeDso"
							 	lookupCodeName="Id"
							 	lookupDisplayName="Description"/>	
							 								
				    <beanlib:InputControl dataSource="<%=XactConst.CLIENT_DATA_XACTTYPE %>" type="hidden" name="XactTypeId" property="Id"/>
				</td>			    
				<td colspan="2">&nbsp;</td>
		   </tr>
	   	 <tr>
			  <td class="clsTableFormHeader">Source/Reason:</td>
				<td align="left" colspan="5">
				   <beanlib:InputControl dataSource="<%=XactConst.CLIENT_DATA_XACT %>" type="text" name="Reason" property="Reason" size="50" maxLength="100"/>
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
					   
					   <beanlib:LoopRows bean="itemObj" list="<%=XactConst.CLIENT_DATA_XACTITEMS %>"> 
						   <tr bgcolor="white">
						       <td class="clsTableListHeader">
 						           <beanlib:InputControl dataSource="itemObj" type="checkbox" name="selCbx" value="rowid" uniqueName="yes"/>
 						           <beanlib:InputControl dataSource="itemObj" type="hidden" name="itemcount" value="rowid"/>
								       <beanlib:InputControl dataSource="itemObj" type="hidden" name="Id" property="Id" uniqueName="yes"/>
								       <beanlib:InputControl dataSource="itemObj" type="hidden" name="XactId" property="XactId" uniqueName="yes"/>	
							   <td>
								   <% 
									  obj = (XactTypeItemActivity) pageContext.getAttribute("itemObj");
									  strSelValue = String.valueOf(obj.getXactTypeItemId()); 
								   %>
				                   <beanlib:InputControl dataSource="<%=XactConst.CLIENT_DATA_XACTITEMTYPELIST%>" 
													 type="select" 
													 name="XactTypeItemId" 
													 codeProperty="Id" 
													 displayProperty="Name" 
													 uniqueName="yes"
													 style="width: 100%"
													 selectedValue="<%=strSelValue%>"/>																 
							   </td>
							   <td align="left">
								   <beanlib:InputControl dataSource="itemObj" type="text" name="Amount" property="Amount" style="text-align: right" uniqueName="yes"/>
							   </td>
							   <td align="left">
								   <beanlib:InputControl dataSource="itemObj" type="text" name="Description" property="Description" style="width: 100%" maxLength="50" uniqueName="yes"/>
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
				  <td colspan="3">
				     <font color="red">
					     <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
					   </font>
				  </td>
				</tr>
		    </table>
		 	<br>
		 	
		 	<!-- Setup Command button area -->
			<table width="80%"  border="0" cellspacing="2" cellpadding="0">
		   	<tr>
		  		<td style="text-align:left">
             <input name="<%=DisbursementsConst.REQ_ADDITEM %>" type="button" value="Add Item" style="width: 90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
						 <input name="<%=GeneralConst.REQ_SAVE %>" type="button" value="Apply" style="width: 90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
             <input name="<%=GeneralConst.REQ_ADD %>" type="button" value="New" style="width: 90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
             <input name="<%=GeneralConst.REQ_BACK %>" type="button" value="Back" style="width: 90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
         	</td>	  
		   	</tr>
		 </table>
		 <input name="clientAction" type="hidden">
	   </form>
	   <db:Dispose/>
	</body>
</html>


