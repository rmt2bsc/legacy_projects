<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
<head>
<title>General Ledger Account Types</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<script>
	function handleAction(_action) {
     GLAcctTypeListForm.target = "EditFrame";
  	 GLAcctTypeListForm.clientAction.value = "getAcctCatg";
	 GLAcctTypeListForm.submit();
	}
</script>

<db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
<db:datasource id="dso" classId="com.api.DataSourceApi"  connection="con" query="GlAccountTypesView" order="id"/>
											
<body bgcolor="#FFFFCC" text="#000000">
<form name="GLAcctTypeListForm" method="post" action="<%=APP_ROOT%>/glacctcatgmaintservlet">
  <table width="20%" border="1">
	  <caption>
			 <strong>GL Account Type List</strong>
	  </caption>  
    <tr bgcolor="FFCC00"> 
      <td width="14%" bgcolor="FFCC00">&nbsp; </td>
      <td width="86%"> 
        <div align="center">
            <b>
                <font size="2">Description</font>
            </b>
        </div>
      </td>
    </tr>
    
    <db:LoopRows dataSource="dso">
		<tr> 
			<td width="14%" bgcolor="FFCC00">
				<db:InputControl dataSource="dso"	type="radio" name="selCbx" value="rowid" onClick="handleAction(this)"/>
				<db:InputControl dataSource="dso" type="hidden" name="Id" property="Id" uniqueName="yes"/>					  
			</td>
			<td width="86%">
				<db:ElementValue dataSource="dso" property="Description"/>					
			</td>
		</tr>
  </db:LoopRows>
  </table>
  <input type="hidden" name="clientAction">
</form>
</body>
</html>
