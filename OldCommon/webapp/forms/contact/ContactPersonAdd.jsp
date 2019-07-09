<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
		<head>
			<title>Contacts Search</title>
			<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		</head>
    <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
    <db:datasource id="perTitleDso" 
                          classId="com.api.DataSourceApi" 
                          connection="con"
						  query="GeneralCodesView"
						  where="group_id = 26"
						  order="longdesc"
						  type="xml"/>												  
    <db:datasource id="perMaritalStatDso" 
                          classId="com.api.DataSourceApi" 
                          connection="con"
						  query="GeneralCodesView"
						  where="group_id = 5"
						  order="longdesc"
						  type="xml"/>												  
    <db:datasource id="perGenderDso" 
                          classId="com.api.DataSourceApi" 
                          connection="con"
						  query="GeneralCodesView"
						  where="group_id = 6"
						  order="longdesc"
						  type="xml"/>
    <db:datasource id="perRaceDso" 
                          classId="com.api.DataSourceApi" 
                          connection="con"
						  query="GeneralCodesView"
						  where="group_id = 15"
						  order="longdesc"
						  type="xml"/>												  
		<script>												  
				function handleAction(action) {
					this.ContactPersonForm.clientAction.value = action;
					this.ContactPersonForm.target = "windowFrame";
					this.ContactPersonForm.submit();
				}
    </script>
		<body bgcolor="#FFFFFF" text="#000000">
				<form name="ContactPersonForm" method="post" action="<%=APP_ROOT%>/contactservlet">
						<table width="90%" border="0">
								<caption align="left">
										<strong>Personal Contact Add</strong> 
								</caption>
								<tr> 
									<td bgcolor="#FFCC00" width="12%"> 
										<div align="right"><font size="2"><b>Person Id:</b></font></div>
									</td>
									<td width="19%">
											<input type="hidden" name="Id" value="0"/>
											<input type="hidden" name="BusinessId" value="0"/>
											<input type="hidden" name="PersonId" value="0"/>												
											<input type="hidden"  name="AddrId" value="0"/>
									</td>
									<td bgcolor="#FFCC00" width="12%"> 
										<div align="right"><font size="2"><b>Maiden Name:</b></font></div>
									</td>
									<td width="19%">
											<input type="text" size="20" tabIndex="6" name="Maidenname" property="PerMaidenname"/>								 
									</td>
									<td bgcolor="#FFCC00" width="12%"> 
										<div align="right"><font size="2"><b><font size="2">Marital Status:</font></b></font></div>
									</td>
									<td width="24%">
											<db:InputControl dataSource="perMaritalStatDso" 
																							 type="select" 
																							 name="MaritalStatus" 
																							 codeProperty="Id" 
																							 displayProperty="Longdesc"
																							 tabIndex="11"/>	
									</td>										
								</tr>

								<tr> 
									<td bgcolor="#FFCC00" width="12%"> 
										<div align="right"><font size="2"><b>Title</b></font></div>
									</td>
									<td width="24%">
											<db:InputControl dataSource="perTitleDso" 
																							 type="select" 
																							 name="Title" 
																							 codeProperty="Id" 
																							 displayProperty="Longdesc"
																							 tabIndex="1"/>										
									</td>
									<td bgcolor="#FFCC00" width="12%"> 
										<div align="right"><b><font size="2">Generation:</font></b></div>
									</td>
									<td width="21%">
											<input type="text" size="5" tabIndex="7" name="Generation" property="PerGeneration"/>								 
									</td>
									<td bgcolor="#FFCC00" width="12%"> 
										<div align="right"><b><font size="2">Race:</font></b></div>
									</td>
									<td width="21%">
											<db:InputControl dataSource="perRaceDso" 
																							 type="select" 
																							 name="RaceId" 
																							 codeProperty="Id" 
																							 displayProperty="Longdesc"
																							 tabIndex="12"/>									
									</td>										
								</tr>
								<tr> 
									<td bgcolor="#FFCC00" width="12%"> 
										<div align="right"><font size="2"><b>First Name:</b></font></div>
									</td>
									<td width="19%">
											<input type="text" size="20" tabIndex="2" name="Firstname" property="PerFirstname"/>								 
									</td>
									<td bgcolor="#FFCC00" width="12%"> 
										<div align="right"><font size="2"><b>SSN:</b></font></div>
									</td>
									<td width="24%">
											<input type="text" size="10" tabIndex="8" name="Ssn" property="PerSsn"/>
									</td>
									<td width="12%">&nbsp;</td>
								</tr>
								<tr>
									<td bgcolor="#FFCC00" width="12%"> 
										<div align="right"><font size="2"><b>Middle Name:</b></font></div>
									</td>
									<td width="24%">
											<input type="text" size="20" tabIndex="3" name="Midname" property="PerMidname"/>								 
									</td>
									<td bgcolor="#FFCC00" width="12%"> 
										<div align="right"><b><font size="2">Birth Date:</font></b></div>
									</td>
									<td width="21%">
											<input type="text" size="20" tabIndex="9" name="BirthDate" property="PerBirthDate"/>								
									</td>										
									<td width="12%">&nbsp;</td>
								</tr>

								<tr>	
									<td bgcolor="#FFCC00" width="12%"> 
										<div align="right"><b><font size="2">Last Name:</font></b></div>
									</td>
									<td width="21%">
											<input type="text" size="20" tabIndex="4" name="Lastname" property="PerLastname"/>								 
									</td>
									<td bgcolor="#FFCC00" width="12%"> 
										<div align="right"><font size="2"><b>Gender:</b></font></div>
									</td>
									<td width="19%">
											<db:InputControl dataSource="perGenderDso" 
																							 type="select" 
																							 name="GenderId" 
																							 codeProperty="Id" 
																							 displayProperty="Longdesc"
																							 tabIndex="10"/>										
									</td>
									<td width="12%">&nbsp;</td>
								</tr>
								<tr> 
									<td bgcolor="#FFCC00" width="12%"> 
										<div align="right"><font size="2"><b>Email:</b></font></div>
									</td>
									<td colspan="3"> 
										<div align="left">
												<input type="text" size="45" tabIndex="5" name="Email" property="PerEmail"/>								 
										</div>
									</td>
									<td width="12%"> 
										<div align="right"><b><font size="2"></font></b></div>
									</td>
									<td width="21%">&nbsp;</td>
								</tr>
						</table>
						<br>

						<table width="90%" border="0">
								<tr> 
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Address 1:</font></b></div>
									</td>
									<td width="24%">
											<input type="text" size="30" tabIndex="13" name="Addr1" property="Addr1"/>
									</td>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">City:</font></b></div>
									</td>
									<td width="24%">&nbsp;</td>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Main Phone:</font></b></div>
									</td>
									<td width="10%">
											<input type="text" size="20"  tabIndex="22" name="PhoneMain" property="AddrPhoneMain"/>
									</td>										
								</tr>

								<tr>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Address 2:</font></b></div>
									</td>
									<td width="24%">
											<input type="text" size="30"  tabIndex="14" name="Addr2" property="Addr2"/>
									</td>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">State:</font></b></div>
									</td>
									<td width="24%">&nbsp;</td>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Mobile Phone:</font></b></div>
									</td>
									<td width="10%">
											<input type="text" size="20"  tabIndex="23" name="PhoneCell" property="AddrPhoneCell"/>
									</td>										
								</tr>

								<tr>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Address 3:</font></b></div>
									</td>
									<td width="24%">
											<input type="text" size="30"  tabIndex="15" name="Addr3" property="Addr3"/>
									</td>
									<td bgcolor="#FFCC00" width="13%"> 
										<div align="right"><b><font size="2">Home Phone:</font></b></div>
									</td>
									<td width="23%">
											<input type="text" size="20"  tabIndex="19" name="PhoneHome" property="AddrPhoneHome"/>
									</td>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Pager Phone:</font></b></div>
									</td>
									<td width="10%">
											<input type="text" size="20"  tabIndex="24" name="PhonePager" property="AddrPhonePager"/>
									</td>										
								</tr>              

								<tr> 
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Address 4:</font></b></div>
									</td>
									<td width="24%">
											<input type="text" size="30"  tabIndex="16" name="Addr4" property="Addr4"/>
									</td>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Work Phone:</font></b></div>
									</td>
									<td width="24%">
											<input type="text" size="20"  tabIndex="20" name="PhoneWork" property="AddrPhoneWork"/>
									</td>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Fax:</font></b></div>
									</td>
									<td width="24%">
											<input type="text" size="20"  tabIndex="25" name="PhoneFax" property="AddrPhoneFax"/>
									</td>										
								</tr>

								<tr> 
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Zip:</font></b></div>
									</td>
									<td width="24%">
											<input type="text" size="10"  tabIndex="17" name="Zip" property="AddrZip"/>
											<input type="text" size="5"  tabIndex="18" name="Zipext" property="AddrZipext"/>
									</td>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Work Extension:</font></b></div>
									</td>
									<td width="24%">
											<input type="text" size="20"  tabIndex="21" name="PhoneExt" property="AddrPhoneExt"/>
									</td>
									<td width="24%">&nbsp;</td>
								</tr>
						</table>
					<p>&nbsp;</p>
					<p>&nbsp;</p>

					<input type="button" name="save_per" value="Save" style=width:90  onClick="handleAction(this.name)">
					<input type="button" name="del_per" value="Delete" style=width:90 onClick="handleAction(this.name)">
					<input type="button" name="back_per" value="Back" style=width:90 onClick="handleAction(this.name)">
                    <input name="clientAction" type="hidden">
				</form>
		</body>
</html>
