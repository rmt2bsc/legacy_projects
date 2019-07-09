<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.CustomerWithName" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.XactConst" %>
<%@ page import="com.constants.SalesConst" %>
<%@ page import="com.constants.GeneralConst" %>

<gen:InitAppRoot id="APP_ROOT"/>


<html>
  <title>Transaction Search Results</title>
  <head>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	  <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">   
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>   
    <script Language="JavaScript">
		function processAction(_srcObj, _actionName) {
			 // Change the controller that is to handle this request.
			this.document.DataForm.action = "<%=APP_ROOT%>" + _actionName;
			this.document.DataForm.clientAction.value = _srcObj;
			// Submit page
			handleAction(MAIN_DETAIL_FRAME, this.document.DataForm, _srcObj);
		}				
    </script>	
  </head>

  
  <%
	 String pageTitle = "Customer Transaction History";
  %>
  
  <body>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/XactSales.CustomerSalesXactList">
		  <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" type="hidden" name="CustomerId" property="Id"/>
  	  <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" type="hidden" name="BusinessId" property="BusinessId"/>
  	  <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER_EXT%>" type="hidden" name="PersonId" property="PersonId"/>
		  <table width="60%" border="0">
			<caption align="left"><h3><%=pageTitle%></h3> </caption>
			<tr>
				<td>
					<%@include file="/forms/xact/sales/CustomerXactHdr.jsp"%>
				</td>
		    </tr>
		  </table>
		   <br>			  
		  <table width="85%" border="3">
			  <tr>
				   <td>
					  <div id="Layer1" style="width:100%; height:550px; z-index:1; overflow:auto; ; background-color: buttonface">
						 <table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
							 <tr>
								 <th width="5%" class="clsTableListHeader">&nbsp;</th>
								 <th width="10%" class="clsTableListHeader" style="text-align:left">Transaction Id</th>
								 <th width="15%" class="clsTableListHeader" style="text-align:left">Transaction Date</th>
								 <th width="15%" class="clsTableListHeader" style="text-align:right">Transaction Amount</th>
								 <th width="2%" class="clsTableListHeader" style="text-align:center">&nbsp;</th>
								 <th width="20%" class="clsTableListHeader" style="text-align:left">Transaction Type</th>
 								 <th width="2%" class="clsTableListHeader" style="text-align:center">&nbsp;</th>
								 <th width="31%" class="clsTableListHeader" style="text-align:left">Reason</th>
							 </tr>

							 <beanlib:LoopRows bean="item" list="<%=XactConst.CLIENT_DATA_SALESPAYMENT_HIST%>">
							     <tr>
  								     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
									 <td align="center" class="clsTableListHeader">
										 <beanlib:InputControl dataSource="item" type="radio" name="selCbx" value="rowid"/>
										 <beanlib:InputControl dataSource="item" type="hidden" name="CustomerActivityId" property="CustomerActivityId" uniqueName="yes"/>
										 <beanlib:InputControl dataSource="item" type="hidden" name="XactId" property="XactId" uniqueName="yes"/>
									 </td>   
									 <td align="left">
										<font size="2"> 
										  <beanlib:ElementValue dataSource="item" property="XactId"/>
										</font>
									 </td>
									 <td align="left">
										<font size="2"> 
										  <beanlib:ElementValue dataSource="item" property="XactDate" format="MM-dd-yyyy H:m:s"/>
										</font>
									 </td>
									 <td align="right">
										<font size="2">
										   <beanlib:ElementValue dataSource="item" property="CustomerActivityAmount" format="$#,##0.00;($#,##0.00)"/>
										</font>		
									 </td>
									 <td>&nbsp;</td>
									 <td align="left" >
										<font size="2">
										   <beanlib:ElementValue dataSource="item" property="XactTypeName"/>
										</font>
									</td>
   								    <td>&nbsp;</td>
									 <td align="left" >
										<font size="2">
										   <beanlib:ElementValue dataSource="item" property="Reason"/>
										</font>
									</td>
								 </tr>
							 </beanlib:LoopRows>
			
							 <% if (pageContext.getAttribute("ROW") == null) {
										out.println("<tr><td colspan=5 align=center>Data Not Found</td></tr>");
									}
							 %>
						 </table>
					  </div>	
				   
				   </td>
			  </tr>
		   </table>
		  <br>
		  <div id="Layer2" style="width:50%; height:80px; z-index:1">
			  <input type="button" name="<%=SalesConst.REQ_REVERSEPAYMENT%>" value="Reverse" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
			  <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">	
			  <input name="clientAction" type="hidden">		  
		  </div>		 
     </form>
     <db:Dispose/>
  </body>
</html>
