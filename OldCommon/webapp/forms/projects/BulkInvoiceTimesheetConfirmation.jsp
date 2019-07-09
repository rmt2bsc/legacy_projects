<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.ProjectConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Bulk Timesheet Invoice</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>

  <%
    String pageTitle = "Client Bulk Timesheet Confirmation";
  %>

  <body bgcolor="#FFFFFF" text="#000000">
     <h3>    
        <strong><%=pageTitle%></strong>
     </h3>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/reqeustProcessor/Project.BulkTimesheetInvoice">
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:90%; height:480px; overflow:auto">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
				   <th width="20%" class="clsTableListHeader" style="text-align:left">Client</th>
				   <th width="17%" class="clsTableListHeader" style="text-align:left">Account No.</th>
  				   <th width="18%" class="clsTableListHeader" style="text-align:left">Employee</th>
   				   <th width="8%" class="clsTableListHeader" style="text-align:left">Employee Id</th>
   				   <th width="12%" class="clsTableListHeader" style="text-align:left">Timehseet Id</th>
   				   <th width="10%" class="clsTableListHeader" style="text-align:left">Period</th>
   				   <th width="8%" class="clsTableListHeader">Hrs. Billed</th>
   				   <th width="7%" class="clsTableListHeader" style="text-align:left">Ref No.</th>
			   </tr>
			   
   			   <beanlib:LoopWrapperRows bean="item" beanClassId="com.bean.VwTimesheetList" list="<%=ProjectConst.CLIENT_DATA_TIMESHEETS%>">
			     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
			        <td>
			           <font size="2">
  			              <beanlib:ElementValue dataSource="item" property="ClientName"/>
  			           </font>
			        </td>			     
			        <td>
			           <font size="2">
  			              <beanlib:ElementValue dataSource="item" property="AccountNo"/>
  			           </font>
			        </td>			     			        
			        <td>
			            <font size="2">
			               <beanlib:ElementValue dataSource="item" property="Shortname"/>
			            </font>
			        </td>
			        <td>
			            <font size="2">
			               <beanlib:ElementValue dataSource="item" property="ProjEmployeeId"/>
			            </font>
			        </td>			   
			        <td>
	                    <font size="2">
                          <beanlib:ElementValue dataSource="item" property="DisplayValue"/>
                        </font>     
			        </td>			        
			        <td>
			           <font size="2">
			              <beanlib:ElementValue dataSource="item" property="EndPeriod" format="MM-dd-yyyy"/>
			           </font>
			        </td>
			        <td align="center">
			            <font color="blue" size="2">
			               <beanlib:ElementValue dataSource="item" property="BillHrs" format="#,##0.00;($#,##0.00)"/>
			            </font>
			        </td>			        			        
			        <td>
			           <font size="2">
			              <beanlib:ElementValue dataSource="item" property="InvoiceRefNo"/>
			           </font>
			        </td>			        
			     </tr>
			   </beanlib:LoopWrapperRows>
			   <% if (pageContext.getAttribute("ROW") == null) {
				     out.println("<tr><td colspan=7 align=center>Data Not Found</td></tr>");
				  }
			   %>			   
            </table>
        </div>
        <br>
        
        <!-- Display command buttons -->
        <table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="2">
					<input name="<%=ProjectConst.REQ_BACK%>" type="button" value="Timesheets" style="width:90" onClick="handleAction(MAIN_DETAIL_FRAME, document.DataForm, this.name)">
				</td>
			</tr>
        </table>
		<input name="clientAction" type="hidden">
     </form>
 </body>
</html>
