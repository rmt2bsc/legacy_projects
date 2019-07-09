<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bean.VwXactList" %>
<%@ page import="com.bean.XactType" %>
<%@ page import="com.bean.XactTypeItemActivity" %>
<%@ page import="com.constants.XactConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.action.AbstractActionHandler" %>

<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
   VwXactList xact = request.getAttribute(XactConst.CLIENT_DATA_XACT) == null ? new VwXactList() : (VwXactList) request.getAttribute(XactConst.CLIENT_DATA_XACT);
   XactType xactType = request.getAttribute(XactConst.CLIENT_DATA_XACTTYPE) == null ? new XactType() : (XactType) request.getAttribute(XactConst.CLIENT_DATA_XACTTYPE);
   String strXactTypeId = String.valueOf(xact.getXactTypeId());
   String xactTypeCriteria = "xact_category_id = " + xactType.getXactCategoryId();
   String xactTenderCriteria = "group_id = 2";
   String xactTypeItemCriteria = "xact_type_id = " + strXactTypeId;
   String strSelGLAcctId = "0";
   String pageTitle = xactType.getDescription() + " View";
   String xactTypeId;
   XactTypeItemActivity obj;
   String strSelValue = null;
   String jspOrigin = request.getParameter("jspOrigin");
%>   

 <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<title>Transaction Detail View</title>
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	</head>
	
	<body>
	   <form name="DataForm"  method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactDisburse.DisbursementView">
			<table width="80%"  border="0" cellspacing="0" cellpadding="0">
			   <caption align="left">
			      <h3><strong><%=pageTitle%></strong></h3>
			   </caption>
			  <tr>
			    <td width="20%">Transaction Id: </td>
				<td width="80%">
				   <beanlib:ElementValue dataSource="xact" property="Id"/>
				   <beanlib:InputControl dataSource="xact" type="hidden" name="XactId" property="Id"/>
				</td>
			  </tr>
			  <tr>
  				<td>Transaction Date: </td>
				<td>
				   <beanlib:ElementValue dataSource="xact" property="XactDate" format="MM-dd-yyyy"/> 
				   <beanlib:InputControl dataSource="xact" type="hidden" name="XactCategoryId" property="XactCategoryId"/>
				</td>
			  </tr>
			  <tr>
				<td>Transaction Type: </td>
				<td>
                    <beanlib:ElementValue dataSource="xact" property="XactTypeName"/>
				    <beanlib:InputControl dataSource="xact" type="hidden" name="XactTypeId" property="XactTypeId"/>			  
				</td>
			  </tr>
			  <tr>
				<td>Amount: </td>
				<td>
 					<beanlib:ElementValue dataSource="xact" property="XactAmount" format="$#,##0.00;($#,##0.00)"/>
                </td>							  
			  </tr>
              <tr>			  
				<td>Tender: </td>
				<td>
				   <beanlib:ElementValue dataSource="xact" property="TenderDescription"/>
				</td>
			  </tr>
			  <tr>
 		     	<td>Check Number:</td>
 		     	<td>
 		     	  <beanlib:ElementValue dataSource="xact" property="NegInstrNo"/> 
 		   		</td>			  
		     </tr>
			  <tr>
  				<td>Post Date: </td>
				<td>
				   <beanlib:ElementValue dataSource="xact" property="CreateDate" format="MM-dd-yyyy HH:mm:ss"/> 
				   <beanlib:InputControl dataSource="xact" type="hidden" name="PostedDate" property="PostedDate"/>
				</td>
			  </tr>		     
             <tr>
			    <td>Reason:</td>
				<td>
				   <beanlib:ElementValue dataSource="xact" property="Reason"/> 
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
				  </div>
				</td>
			  </tr>
			</table>
            
		 	<!-- Setup Command button area -->
			<table width="50%"  border="0" cellspacing="2" cellpadding="0">
		   	<tr>
		  		<td algin="left">
  					  <input name="<%=XactConst.REQ_REVERSE %>" type="button" value="Reverse" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
            	<input type="button" name="<%=GeneralConst.REQ_ADD%>" value="New" style="width: 90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
            	<input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style="width: 90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
        	</td>	  
		   	</tr>
		 </table>
		 <input type="hidden" name="clientAction">
	   </form>
	   <db:Dispose/>
	</body>
</html>


