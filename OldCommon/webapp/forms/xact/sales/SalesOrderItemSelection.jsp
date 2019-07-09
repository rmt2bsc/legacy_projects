<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.constants.SalesConst" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Sales Order Item Selection</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>

   <%
      String pageTitle = "Sales On Account - Item Selection";
 	 %>
	
  <body bgcolor="#FFFFFF" text="#000000">
       <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactSales.CustomerNewSalesItemSelect">
          <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" type="hidden" name="CustomerId" property="Id"/>
          <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_SALESORDER%>" type="hidden" name="OrderId" property="Id"/>
          <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" type="hidden" name="BusinessId" property="BusinessId"/>
          <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" type="hidden" name="PersonId" property="PersonId"/>
 	      <h3><strong><%=pageTitle%></strong></h3>
	  
		<table width="60%" border="0">
			<tr> 
				<td align="right" width="30%" bgcolor="#FFCC00"> 
				    <font size="2">
					   <b>Account Number</b>
					</font>
				</td>
				<td width="70%">
		   		   	<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" property="AccountNo"/>
				</td>
			</tr>    
			<tr> 
				<td align="right" width="30%" bgcolor="#FFCC00"> 
					    <font size="2">
						   <b>Account Name</b>
						</font>
				</td>
				<td width="70%">
				   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" property="Name"/>
				</td>
			</tr>    
			<tr> 
				<td align="right" width="30%" bgcolor="#FFCC00"> 
					    <font size="2">
						   <b>Sales Order</b>
						</font>
				</td>
				<td width="70%">
				   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_SALESORDER%>" property="Id"/>
				</td>
			</tr>    
		</table>
		<br>
	  
		<table width="80%" border="0">
			<tr> 
				<td align="left" width="50%" >
				     <font size="2">
						<b>Services</b>
					</font>
				 </td>
				<td align="left" width="50%">
			        <font size="2">
						<b>Merchandise</b>
					</font>
				 </td>
			</tr>
			<tr> 
				<td width="50%" > 
					 <beanlib:InputControl dataSource="services"
													 type="select"
													 name="ServiceItems"
													 codeProperty="Id"
													 displayProperty="Description"
													 multiSelect="Yes"
													 size="15"
													 style="width:400"/>
				</td>
				<td width="50%"> 
					 <beanlib:InputControl dataSource="merchandise"
													 type="select"
													 name="MerchItems"
													 codeProperty="Id"
													 displayProperty="Description"
													 multiSelect="Yes"
													 size="15"
													 style="width:400"/>				
				</td>
			</tr>			
		</table>
		<br>
	  
	   <!-- Display command buttons -->
	  <table width="100%" cellpadding="0" cellspacing="0">
		  <tr>
			<td colspan="2">
			    <input name="<%=GeneralConst.REQ_BACK%>" type="button" value="Back" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
  			  <input name="<%=GeneralConst.REQ_NEXT%>" type="button" value="Next" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
			</td>
		 </tr>		
	  </table>
	 <input name="clientAction" type="hidden">
   </form>
 </body>
</html>
