<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.constants.RMT2ServletConst" %>

<jsp:useBean id="QUERY_BEAN" scope="session" class="com.bean.RMT2TagQueryBean"/>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel=STYLESHEET type="text/css" href="/css/RMT2General.css">
</head>

<gen:InitAppRoot id="APP_ROOT"/>

<script src="/js/RMT2General.js"></script>
<script>												  
		function handleAction(action) {
		  if (action == "add") {
		    action = "add_bus";
		  }
			this.ContactForm.clientAction.value = action;
			this.ContactForm.target = "windowFrame";
			this.ContactForm.submit();
		}
</script>
    
    
<body bgcolor="#FFFFFF" text="#000000">
	<div id="resultsPage" class="resultsPage">
		<form name="ContactForm" method="post" action="<%=APP_ROOT%>/contactservlet">
		  <table width="90%" border="0" cellspacing="0">
			<caption> 
			<h2>Business Contact List</h2>
			</caption>
			<tr bgcolor="#FFCC00"> 
			  <td width="8%"> 
				<div align="center"> <font color="#000000" size="2"><b>Id</b></font> </div>
			  </td>
			  <td width="19%" bgcolor="#FFCC00"> 
				<div align="left"> <font color="#000000" size="2"><b>Business Name</b></font> 
				</div>
			  </td>
			  <td width="24%"> 
				<div align="left"> <font color="#000000" size="2"><b>Business Address</b></font> 
				</div>
			  </td>
			  <td width="15%"> 
				<div align="left"> <font color="#000000" size="2"><b>Contact Name</b></font></div>
			  </td>
			  <td width="11%"> 
				<div align="left"><b><font size="2">Contact Phone</font></b></div>
			  </td>
			  <td width="21%"> 
				<div align="left"><b><font size="2">Business Phone Numbers</font></b></div>
			  </td>
			</tr>
			<db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/> 
			<db:datasource id="address"
								  classId="com.api.DataSourceApi" 
								  connection="con"
								  query="BusinessAddressView"
								  queryId="<%=RMT2ServletConst.QUERY_BEAN%>"/>
						   
			<db:LoopRows dataSource="address"> 
			<gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>

			  <td width="8%" valign="top">
				<%
				  String urlTarget = APP_ROOT + "/forms/contact/ContactBusinessMaint.jsp?business_id="  + address.getColumnValue("BusinessId") + "&address_id=" + address.getColumnValue("AddrId");
  			      out.print("<a href=" + urlTarget + " target=windowFrame>");
				%>
				<db:ElementValue dataSource="address" property="BusinessId"/> 
				<%out.print("</a>");%>
			  </td>
			  <td width="19%"  valign="top"> 
				<db:ElementValue dataSource="address" property="BusLongname"/>
			  </td>
			  <td width="24%"> 
				<%! String addr = null;%>
				<%! String addr2 = null;%>
				<%! String temp = null;%>
				<%! String phoneNums = ""; %>
				<%! String brk = ""; %>
				<% temp = address.getColumnValue("Addr1");
					 if (temp != null && !temp.equals("")) {
					   addr = temp;
					 }
					 temp = address.getColumnValue("Addr2");
					 if (temp != null && !temp.equals("")) {
					   addr += "<br>" + temp;
					 }
					 temp = address.getColumnValue("Addr3");
					 if (temp != null && !temp.equals("")) {
					   addr += "<br>" + temp;
					 }
					 temp = address.getColumnValue("Addr4");
					 if (temp != null && !temp.equals("")) {
					   addr += "<br>" + temp;
					 }
					 temp = address.getColumnValue("ZipCity");
					 if (temp != null && !temp.equals("")) {
					   addr2 =  temp;
					 }
					 temp = address.getColumnValue("ZipState");
					 if (temp != null && !temp.equals("")) {
					   addr2 += ", " + temp;
					 }
					 temp = address.getColumnValue("AddrZip");
					 if (temp != null && !temp.equals("")) {
					   addr2 += " " + temp;
					 }
					 temp = address.getColumnValue("AddrZipext");
					 if (temp != null && !temp.equals("")) {
					   addr2 +=  "-" + temp;
					 }
		
					 if (addr == null && addr2 == null) {
					   addr = "Not Available";
					 }
					 else if (addr == null && addr2 != null) {
					   addr = addr2;
					 }
					 else if (addr != null && !addr.equals("") && addr2 != null) {
					   addr += "<br>" + addr2;
					 }
				  %>
				<div align="left"> 
				  <%out.print(addr);%>
				  <% temp = null;
					   addr = null;
					   addr2 = null;
					%>
				</div>
			  </td>
			  <td width="15%" valign="top"> 
				<db:ElementValue dataSource="address" property="BusContactFirstname"/> <db:ElementValue dataSource="address" property="BusContactLastname"/>
			  </td>
				
			  <td width="11%" valign="top"><db:ElementValue dataSource="address" property="BusContactPhone"/></td>
			  <td width="21%" valign="top">
				
				<%  
					  temp = address.getColumnValue("AddrPhoneMain");
					  if (temp != null && !temp.equals("")) {
						brk = (phoneNums.length() > 0 ? "<br>" : "");
						phoneNums += brk + "Main - " + temp;
					  }
					  temp = address.getColumnValue("AddrPhoneFax");
					  if (temp != null && !temp.equals("")) {
						brk = (phoneNums.length() > 0 ? "<br>" : "");
						phoneNums += brk + "Fax. - " + temp;
					  }              
					  if (phoneNums == null || phoneNums.equals("")) {
						out.println("Not Available");
					  }
					  else {
						out.println(phoneNums);
					  }
					  phoneNums = "";
				   %>
			  </td>
			</tr>
			</db:LoopRows> 
		  </table>
		  <input name="clientAction" type="hidden">
		</form>
	</div>
</body>
</html>
