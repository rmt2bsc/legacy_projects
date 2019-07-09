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

<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
   Xact xact = request.getAttribute("xact") == null ? new Xact() : (Xact) request.getAttribute("xact");
   XactType xactType = request.getAttribute("xactType") == null ? new XactType() : (XactType) request.getAttribute("xactType");
   XactCategory xactCatg = request.getAttribute("xactCatg") == null ? new XactCategory() : (XactCategory) request.getAttribute("xactCatg");
   String msg = request.getAttribute(AbstractActionHandler.SINGLE_MSG) == null ? "" : (String) request.getAttribute(AbstractActionHandler.SINGLE_MSG);
   String strXactTypeId = String.valueOf(xactType.getId());
   String strXactCatgId = String.valueOf(xactType.getXactCategoryId());
   String xactTypeCriteria = "xact_category_id = " + strXactCatgId;
   String xactTenderCriteria = "group_id = 2";
   String xactTypeItemCriteria = "xact_type_id = " + strXactTypeId;
   String strSelGLAcctId = "0";
   String pageTitle = xactCatg.getDescription() + " - New";
   String xactTypeId;
   XactTypeItemActivity obj;
   String strSelValue = null;
   String jspOrigin = request.getParameter("jspOrigin");
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

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<title>Transaction Details - Add</title>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	</head>
	
	<body>
	   <form name="DataForm"  method="POST" action="<%=APP_ROOT%>/xactservlet">
	     <input type="hidden" name="clientAction">
			<table width="80%"  border="0" cellspacing="0" cellpadding="0">
			   <caption align="left">
			      <h3><strong><%=pageTitle%></strong></h3>
			   </caption>
			  <tr>
  				<td width="15%" class="clsTableFormHeader">Transaction Date: </td>
				<td width="18%">
				   <beanlib:InputControl dataSource="xact" type="text" name="XactDate" property="XactDate" format="MM-dd-yyyy"/>
				   <beanlib:InputControl dataSource="xact" type="hidden" name="Id" property="Id" readOnly="yes"/>
				   <beanlib:InputControl dataSource="xactType" type="hidden" name="XactCategoryId" property="XactCategoryId"/>
				   <beanlib:InputControl dataSource="xact" type="hidden" name="PostedDate" property="PostedDate" format="MM-dd-yyyy"/>
				</td>
				<td width="15%" class="clsTableFormHeader">Amount: </td>
				<td width="18%">
 					<beanlib:InputControl dataSource="xact" type="text" name="XactAmount" property="XactAmount"/>
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
 		     	<td class="clsTableFormHeader">Tender Number:</td>
 		     	<td>
 		     	  <beanlib:InputControl dataSource="xact" type="text" name="NegInstrNo" property="NegInstrNo"/>
 		   		</td>			  
				<td class="clsTableFormHeader">Transaction Type: </td>
				<td>
                     <db:Lookup dataSource="" 
							 	masterCodeName=""
							 	masterCodeValue="<%=strXactTypeId%>"
							 	type="1"
							 	lookupSource="xactTypeDso"
							 	lookupCodeName="Id"
							 	lookupDisplayName="Description"/>	
							 								
				    <beanlib:InputControl dataSource="xactType" type="hidden" name="XactTypeId" property="Id"/>
				</td>			    
				<td colspan="2">&nbsp;</td>
		     </tr>
			</table>
			
			<br>
			<table width="40%"  border="0" cellspacing="0" cellpadding="0">
				<tr>
				    <td class="clsTableFormHeader" width="27%" valign="top">Reason:</td>
					<td width="73%">
					   <beanlib:InputControl dataSource="xact" type="multiline" name="Reason" property="Reason" rows="2" cols="50" style="border:none"/>
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
					   
					   <beanlib:LoopRows bean="itemObj" list="xactTypeItems"> 
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
				                   <beanlib:InputControl dataSource="xactTypeItemList" 
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
								   <beanlib:InputControl dataSource="itemObj" type="text" name="Description" property="Description" style="width: 100%" uniqueName="yes"/>
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
                  	<input type="button" name="add" value="New" style="width: 90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
                  	<input type="button" name="addXactItem" value="Add Item" style="width: 90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
                  	<input type="button" name="addSave" value="Apply" style="width: 90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
                  	<input type="button" name="newsearch" value="Search" style="width: 90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
              	</td>	  
		   	</tr>
		 </table>
		 <input name="jspOrigin" type="hidden" value="<%=jspOrigin%>">
	   </form>
	</body>
</html>


