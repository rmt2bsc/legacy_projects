<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.GeneralConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
<head>
<title>General Ledger Account Category</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<script>
	function handleAction(_action) {
     DataForm.target = "EditFrame";
  	 DataForm.clientAction.value = "list";
     DataForm.submit();
	}
</script>
<body bgcolor="#FFFFCC" text="#000000">
<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/AccountsConsole.Accounts">
  <table width="80%" border="0">
    <tr bgcolor="FFCC00"> 
      <td width="7%" bgcolor="FFCC00">&nbsp; </td>
      <td width="93%"> 
        <div align="left">
            <b>
                <font size="2">GL Account Category List</font>
            </b>
        </div>
      </td>
    </tr>
    
	
	 <%
			if (request.getParameter("firsttime") != null) {
				out.println("</form>");
				out.println("<tr><td align=center colspan=2 align=center>Data Not Found</td></tr>");
				out.println("</table>");
				out.println("</body>");
				out.println("</html>");
				return;
			}
	 %>	
	
    <beanlib:LoopRows bean="beanObj" list="catgList">
		<tr> 
			<td width="7%" align="center" bgcolor="FFCC00">
			  <beanlib:InputControl type="radio" name="selCbx" value="rowid" onClick="handleAction(this)"/>
			  <beanlib:InputControl type="hidden" name="AcctCatgId" value="#beanObj.AcctCatgId" uniqueName="yes"/>					  
			  <beanlib:InputControl type="hidden" name="AcctTypeId" value="#beanObj.AcctTypeId" uniqueName="yes"/>					  
			</td>
			<td width="93%">
			   <beanlib:InputControl value="#beanObj.Description"/>					
			</td>
		</tr>
    </beanlib:LoopRows>
  </table>
  <input type="hidden" name="clientAction">
</form>
</body>
</html>
