<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>

<%@ page import="com.util.RMT2Utility" %>

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
      function init() {
        var jspOrigin;
        jspOrigin = parent.SearchFrame.document.SearchForm.jspOrigin.value;
        this.document.DataForm.jspOrigin.value = jspOrigin;
      }
      function executeList(_target, _form, _action) {
		var obj = DataForm.selCbx;  
		var row = null;
		var msg = "A row must be selected for this operation";
		
		if (obj == null) {
		   alert(msg);
		   return;
		}
		for (ndx = 0;ndx < obj.length; ndx++) {
		   if (obj[ndx].checked) {
		      row = obj[ndx].value;
		      break;
		   }
		} 
		if (row == null || row == "undefined") {
		   alert(msg);
		   return;
		}
		handleAction(_target, _form, _action);
	  }
    </script>   
  </head>

  <body onload="init();">
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/xactservlet">
             <input name="jspOrigin" type="hidden">
			 <input name="clientAction" type="hidden">
			 <table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
			     <caption align="left" style="color:blue">Cash Flow Transaction Search Results</caption>
				 <tr>
					 <th width="5%" class="clsTableListHeader">&nbsp;</th>
					 <th width="5%" class="clsTableListHeader">Id</th>
					 <th width="10%" class="clsTableListHeader">Transaction Date</th>
					 <th width="10%" class="clsTableListHeader">Date Entered</th>
					 <th width="8%" class="clsTableListHeader" style="text-align:right">Amount</th>
					 <th width="5%" class="clsTableListHeader">&nbsp;</th>
					 <th width="18%" class="clsTableListHeader" style="text-align:left">Transaction Type</th>
					 <th width="39%" class="clsTableListHeader" style="text-align:left">Reason</th>
				 </tr>
   
				 <%
						if (request.getAttribute("data") == null) {
							out.println("</form>");
							out.println("<tr><td colspan=9 align=\"center\">Data Not Found</td></tr>");
							out.println("</table>");
							out.println("</body>");
							out.println("</html>");
							return;
						}
				 %>
					 <beanlib:LoopRows bean="beanObj" list="data">
					      <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>

							 <td align="center" class="clsTableListHeader">
								 <beanlib:InputControl dataSource="beanObj" type="radio" name="selCbx" value="rowid"/>
								 <beanlib:InputControl dataSource="beanObj" type="hidden" name="Id" property="Id" uniqueName="yes"/>                                  
								 <beanlib:InputControl dataSource="beanObj" type="hidden" name="XactTypeId" property="XactTypeId" uniqueName="yes"/>                            
							 </td>   
							 <td align="center" >
							    <font size="2">
							      <beanlib:ElementValue dataSource="beanObj" property="Id"/>
								</font>
							 </td>             
 							 <td align="center">
							    <font size="2"> 
   							      <beanlib:ElementValue dataSource="beanObj" property="XactDate" format="MM-dd-yyyy"/>
								</font>
							 </td>
 							 <td align="center">
							    <font size="2"> 
   							      <beanlib:ElementValue dataSource="beanObj" property="CreateDate" format="MM-dd-yyyy HH:mm:ss"/>
								</font>
							 </td>							 
							 <td align="right">
							    <font size="2">
							       <beanlib:ElementValue dataSource="beanObj" property="XactAmount" format="$#,##0.00;($#,##0.00)"/>
								</font>
							 </td>
							 <td align="right">&nbsp;</td>							 
							 <td align="left" >
								<font size="2">
								   <beanlib:ElementValue dataSource="beanObj" property="XactTypeName"/>
								</font>
  					        </td>
							 <td align="left" >
								<font size="2">
								   <beanlib:ElementValue dataSource="beanObj" property="Reason"/>
								</font>
  					        </td>							
						 </tr>
					 </beanlib:LoopRows>

				 <% if (pageContext.getAttribute("ROW") == null) {
							out.println("<tr><td colspan=9 align=center>Data Not Found</td></tr>");
						}
				 %>
			 </table>
			 
     </form>
  </body>
</html>
