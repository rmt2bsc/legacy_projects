<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.XactQuery" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.XactConst" %>

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
	<script Language="JavaScript">
	     var xactCatgImgs = new Array();
		 
	     function initPage() {
		    loadImages();
		 }
		 
		 function loadImages() {
	       xactCatgImgs[0] = document.img_cf_0;
		   xactCatgImgs[1] = document.img_cf_2;
		   xactCatgImgs[2] = document.img_cf_3;
		   xactCatgImgs[3] = document.img_cf_4;
		   xactCatgImgs[4] = document.img_cf_8;
		   focusImage(0);
		 }
		 
		 function focusImage(_img) {
		     for(ndx=0; ndx < xactCatgImgs.length; ndx++) {
			    xactCatgImgs[ndx].style.borderStyle = "none";
		     }
			 //  Get current transaction category id that is
			 curCatg = document.SearchForm.XactCatgId.value;
			 obj = eval("document.img_cf_" + curCatg);
			 obj.style.borderStyle = "solid"
		 }
		 
		 function refreshCategory(_action, _catgCode) {
		    this.SearchForm.target = "SearchFrame";
			this.SearchForm.clientAction.value = _action;
			if (_catgCode != null  && _catgCode != "") {
			  this.SearchForm.XactCatgId.value = _catgCode;
			}
			this.SearchForm.submit();
		 }
		 
		 function executeSearchCriteria(_target, _form, _action) {
		   var xactTypeId = SearchForm.xactTypeId.value;
		   if (xactTypeId == null || xactTypeId == "") {
		      alert("Transaction Type must be selected for this operation");
		      return;
		   }
		   handleAction(_target, _form, _action);
		 }
    </script>
  </head>
  
  
  
  <%
      String strSelectedXactType;
	  String pageTitle = null;
	  String xactCatgCriteria = "";
	  String xactCatgSelectionVisible = "visibility:visible";
  	  String xactCatgSelectionHidden = "visibility:hidden";
	  String xactCatgBoxStyle = null;
	  String jspOrigin = request.getParameter("jspOrigin");
	  System.out.println("JSP origin from Search Transaction Page: " + jspOrigin);
	  
	  int xactCatgId = 0;
	  final int CRED_SALES_XACT = 4;
	  final int CASH_RECEIPTS_XACT = 2;
	  final int CASH_PAY_XACT = 3;
	  final int PURCHASE_XACT = 8;
  %>  
	
  <%@include file="/includes/SessionQuerySetup.jsp"%>  	  	 
  
  <%
  	  	       	  	     	  XactQuery query = (custObj != null && custObj instanceof XactQuery ? (XactQuery)baseQueryObj.getCustomObj() : XactQuery.getInstance());
  	  	       	  	     	  query.setJspOrigin(jspOrigin);
  	  	       	  	     	  
  	  	       	  	     	  if (query.getXactTypeId() == 0) {
  	  	       	  	     	    strSelectedXactType = "";
  	  	       	  	     	  }
  	  	       	  	     	  else {
  	  	       	  	     	     strSelectedXactType = String.valueOf(query.getXactTypeId());
  	  	       	  	     	   }
  	  	       	  	     	   // Build the list of transaction types we are limited by based on the transactio category id.
  	  	       	  	     	   if (query.getJspOrigin().equalsIgnoreCase(XactConst.XACT_JSP_ORIGIN_GENERAL)) {
  	  	       	  	     	      xactCatgBoxStyle =  xactCatgSelectionVisible;
  	  	       	  	       		  pageTitle = "General Transaction Management Console";	     
  	  	       	  	     		  xactCatgCriteria = query.getXactCatgId() == 0 ? "xact_category_id in (1,2,3,4,6,7,8,9,10,11,12,13,14,15,16)" : "xact_category_id = " +  query.getXactCatgId();
  	  	       	  	     	   }
  	  	       	  	     	   else {
  	  	       	  	     	      xactCatgBoxStyle =  xactCatgSelectionHidden;
  	  	       	  	     		  if (query.getJspOrigin().equalsIgnoreCase(XactConst.XACT_JSP_ORIGIN_DISBURSEMENT)) {
  	  	       	  	     		     pageTitle = "Cash Disbursement Transaction Management Console";	     
  	  	       	  	     	 xactCatgCriteria = "xact_category_id in (3)" ;
  	  	       	  	     	 query.setXactCatgId(3);
  	  	       	  	     		  }  
  	  	       	  	     	   }

  	  	       	  	     	   // Determine what transaction category we are targeting
  	  	       	  	     	   xactCatgId = query.getXactCatgId();
  	  	       	  	     	   	   
  	  	       	  	            //xactCatgCriteria = query.getXactCatgId() == 0 ? "xact_category_id in (1,2,3,4,6,7,8,9,10,11,12,13,14,15,16)" : "xact_category_id = " +  query.getXactCatgId() ;
  	  	       	  	     //  	   xactCatgCriteria = query.getXactCatgId() == 0 ? "xact_category_id in (3)" : "xact_category_id = " +  query.getXactCatgId() ;
  	  	       	  	     //	 xactCatgCriteria += " and id not in (20)";
  	  	     %>
    
 <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
 <db:datasource id="xactTypeDso" 
                       classId="com.api.DataSourceApi" 
					   connection="con" 
					   query="XactTypeView" 
					   where="<%= xactCatgCriteria %>"
					   order="description" type="xml"/>
 
  <body bgcolor="#FFFFFF" text="#000000" onLoad="initPage()">
       <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/xactservlet">
	     <input type="hidden" name="XactCatgId" value="<%= xactCatgId%>">
		 
	     <h3><strong><%=pageTitle%></strong></h3>
		 
         <div style="border-style:solid;border-color:#999999;width:30%" style="<%=xactCatgBoxStyle%>">
		   <table width="100%" bgcolor="#CCCCCC" cellpadding="1" cellspacing="1" border= "0">
	  		 <caption align="left" style="color:#0000FF ">
  	  		     <strong>Select cash flow transaction categories</strong>
	  		 </caption>	 
			 <tr>
				 <td valign="top">
					 <a href="javascript:refreshCategory('cfRefresh', '')"> 
						<img name="img_cf_0" src="<%=APP_ROOT%>/images/menuitem_all_cashflow.gif" style="border:none" alt="Search All Transaction Types">
					 </a>       
				 </td>
				 <td valign="top">
					 <a href="javascript:refreshCategory('cfRefresh', 'CREDSALES')"> 
						<img name="img_cf_4" src="<%=APP_ROOT%>/images/menuitem_sales.gif" style="border:none" alt="Search Sales Transaction Type">
					 </a>       
				 </td>							
				 <td valign="top">
					 <a href="javascript:refreshCategory('cfRefresh', 'CASHRECT')">  
						<img name="img_cf_2" src="<%=APP_ROOT%>/images/menuitem_cashin.gif" style="border:none" alt="Search Cash Receipts Transaction Type">
					 </a>       
				 </td>														
				 <td valign="top">
					 <a href="javascript:refreshCategory('cfRefresh', 'CASHPAY')">  
						<img name="img_cf_3" src="<%=APP_ROOT%>/images/menuitem_cashout.gif" style="border:none" alt="Search Cash Disbursements Transaction Type">
					 </a>       
				 </td>																					 
				 <td valign="top">
					 <a href="javascript:refreshCategory('cfRefresh', 'PURCH')">  
						<img name="img_cf_8" src="<%=APP_ROOT%>/images/menuitem_purchases.gif" style="border:none" alt="Search Purchases Transaction Type">
					 </a>       
				 </td>																					 				   
			 </tr>
		 </table>
	   </div>
	   
	   <table>
		  <tr>
			 <td>&nbsp;</td>
		 </tr>
	  </table>
	  
	  <table width="100%" cellpadding="0" cellspacing="0">
		 <caption align="left" style="color:blue">Selection Criteria</caption>
         <tr>
		     <!-- Display common selection criteria items  -->
		    <td width="66%">
				 <table  width="100%" border="0" cellpadding="0" cellspacing="0"> 
					<tr>
					   <td width="20%" class="clsTableFormHeader">Transaction Type:</td>
					   <td colspan="5" align="left">
						 <db:InputControl dataSource="xactTypeDso"
												 type="select"
												 name="xactTypeId"
												 codeProperty="Id"
												 displayProperty="Description"
												 selectedValue="<%=strSelectedXactType %>"/>
					   </td>
					</tr>						 
					<tr>
					   <td width="20%" class="clsTableFormHeader">Transaction Date:</td>
					   <td width="10%" align="left"><gen:CondOps name="XactDate1Op" /> </td>
					   <td width="15%" align="left">
							<input type="text" name="XactDate1" value="<%=query.getXactDate1() == null ? "" :  query.getXactDate1() %>">
					   </td>
					   <td align="center">&nbsp;<b>and<b></b>&nbsp;</td>
					   <td width="10%" align="center"><gen:CondOps name="XactDate2Op" /> </td>
					   <td>
						  <input type="text" name="XactDate2" value="<%=query.getXactDate2() == null ? "" :  query.getXactDate2() %>">
					   </td>
					</tr>
					<tr>
					   <td width="20%" class="clsTableFormHeader">Transaction Amount:</td>
					   <td width="10%" align="left"><gen:CondOps name="XactAmount1Op" /> </td>
					   <td width="15%" align="left">
							<input type="text" name="XactAmount1" value="<%=query.getXactAmount1() %>">
					   </td>
					   <td align="center">&nbsp;<b>and<b></b>&nbsp;</td>
					   <td width="10%" align="center"><gen:CondOps name="XactAmount2Op" /> </td>
					   <td>
						  <input type="text" name="XactAmount2" value="<%=query.getXactAmount2() %>">
					   </td>
					</tr>	
					<tr>
					   <td width="20%" class="clsTableFormHeader">Source of Transaction:</td>
					   <td colspan="5" align="left">
						   <input type="text" name="XactReason" size="50" maxlength="50" value="<%=query.getXactReason() == null ? "" : query.getXactReason() %>">
					   </td>
					</tr>
					<tr>
					  <td colspan="6">&nbsp;</td>
					</tr>					
				 </table>
			</td>

              <!-- Determine if Customer/Creditor selection criteria items are displayed -->
		     <td width="35%" valign="top">       
			       <% if (query.getXactCatgId() == CRED_SALES_XACT || query.getXactCatgId() == CASH_RECEIPTS_XACT)  { %>
  			              <%@include file="XactCustomerSearchCritera.jsp"%>   
				   <% } %>
				   
			       <% if (query.getXactCatgId() == CASH_PAY_XACT || query.getXactCatgId() == PURCHASE_XACT) { %>
     			      <%@include file="XactCreditorSearchCritera.jsp"%>    
			       <% } %>
			 </td>     
		  </tr>    

          <!-- Display command buttons -->
		  <tr>
			<td colspan="2">
			  <input name="search" type="button" value="Search" style="width:90" onClick="handleAction('ListFrame', document.SearchForm, this.name)">
			  <input name="reset" type="reset" value="Clear" style="width:90">
			</td>
		 </tr>		
	  </table>
	  
     <input type="hidden" name="UserLocationId" value="0">
	 <input name="clientAction" type="hidden">
	 <input name="jspOrigin" type="hidden" value="<%=jspOrigin%>">
   </form>
 </body>
</html>
