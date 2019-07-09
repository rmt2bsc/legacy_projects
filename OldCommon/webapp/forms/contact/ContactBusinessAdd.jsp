<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
		<head>
			<title>Contacts Business Maintenance</title>
			<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		</head>

		<script>												  
				function handleAction(action) {
					this.ContactBusinessForm.clientAction.value = action;
					this.ContactBusinessForm.target = "windowFrame";
					this.ContactBusinessForm.submit();
				}
    </script>

    <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
    <db:datasource id="busTypeDso" 
                          classId="com.api.DataSourceApi" 
                          connection="con"
 												  query="GeneralCodesView"
 												  where="group_id = 7"
												  order="longdesc"
												  type="xml"/>    
    <db:datasource id="busServTypeDso" 
                          classId="com.api.DataSourceApi" 
                          connection="con"
 												  query="GeneralCodesView"
 												  where="group_id = 8"
												  order="longdesc"
												  type="xml"/> 												  
 
		<body bgcolor="#FFFFFF" text="#000000">
				<form name="ContactBusinessForm" method="post" action="<%=APP_ROOT%>/contactservlet">
						<table width="90%" border="0">
							<caption align="left"> <strong>Business Contact Maintenance</strong> </caption>
							<tr> 
								<td width="10%" bgcolor="#FFCC00"> 
									<div align="right"><b><font size="2">Business Id:</font></b></div>
								</td>
								<td width="21%"> 
									<div id="bus_id" align="left"> 
											<input type="hidden" name="Id" value="0"/>
											<input type="hidden" name="BusinessId" value="0"/>
											<input type="hidden" name="PersonId" value="0"/>
											<input type="hidden" name="AddrId" value="0"/> 
									</div>
								</td>

								<td width="12%" bgcolor="#FFCC00"> 
									<div align="right"><b><font size="2">Contact First Name:</font></b></div>
								</td>
								<td width="24%"> 
									<div align="left"> 
											<input type="text" size="20" tabIndex="5" name="ContactFirstname"/>
									</div>
								</td>
							</tr>	

							<tr> 
								<td width="10%" bgcolor="#FFCC00"> 
									<div align="right"><b><font size="2">Business Name:</font></b></div>
								</td>
								<td width="21%"> 
									<div align="left"> 
											<input type="text" size="60" tabIndex= "1" name="Longname"/>
									</div>
								</td>

								<td width="12%" bgcolor="#FFCC00"> 
									<div align="right"><b><font size="2">Contact Last Name:</font></b></div>
								</td>
								<td width="21%"> 
									<div align="left"> 
											<input type="text" size="20" tabIndex= "6" name="ContactLastname"/>
									</div>
								</td>
							</tr>
							<tr> 
								<td width="12%" bgcolor="#FFCC00"> 
									<div align="right"><b><font size="2">Business Type:</font></b></div>
								</td>
								<td width="24%"> 
									<div align="left"> 
											<db:InputControl dataSource="busTypeDso" 
																							 type="select" 
																							 name="BusType" 
																							 codeProperty="Id" 
																							 displayProperty="Longdesc"
																							 tabIndex="2"/>										
									</div>
								</td>
								<td width="10%" bgcolor="#FFCC00"> 
										<div align="right"><b><font size="2">Contact Phone:</font></b></div>
								</td>
								<td width="21%"> 
										<div align="left"> 
												<input type="text" size="20" tabIndex= "7" name="ContactPhone"/>
										</div>
								</td>							
							</tr>

							<tr> 
								<td width="10%" bgcolor="#FFCC00"> 
									<div align="right"><b><font size="2">Service Type:</font></b></div>
								</td>
								<td width="30%">
											<div align="left"> 
														<db:InputControl dataSource="busServTypeDso" 
																										 type="select" 
																										 name="ServType" 
																										 codeProperty="Id" 
																										 displayProperty="Longdesc"
																										 tabIndex="3"/>	
											</div>
								</td>
								<td width="12%" bgcolor="#FFCC00"> 
									<div align="right"><b><font size="2">Contact Phone Ext.</font></b></div>
								</td>
								<td width="24%"> 
									<div align="left"> 
											<input type="text" size="20" tabIndex="8" name="ContactExt"/>
									</div>
								</td>

							</tr>
							<tr>
								<td width="12%" bgcolor="#FFCC00"> 
									<div align="right"><b><font size="2">Web Site:</font></b></div>
								</td>
								<td width="21%"> 
									<div align="left"> 
											<input type="text" size="50" tabIndex="4" name="Website"/>
									</div>
								</td>
								<td width="12%" bgcolor="#FFCC00"> 
									<div align="right"><b><font size="2">Tax Id:</font></b></div>
								</td>
								<td width="24%"> 
									<div align="left"> 
											<input type="text" size="20" tabIndex="9" name="TaxId"/>
									</div>
								</td>

							</tr>
						</table>
						<br>

						<table width="90%" border="0">
								<tr> 
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Address 1:</font></b></div>
									</td>
									<td width="24%">
											<input type="text" size="50" tabIndex="9" name="Addr1"/>
									</td>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">City:</font></b></div>
									</td>
									<td width="24%">&nbsp;</td>
								</tr>
								<tr>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Address 2:</font></b></div>
									</td>
									<td width="24%">
											<input type="text" size="50"  tabIndex="10" name="Addr2"/>
									</td>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">State:</font></b></div>
									</td>
									<td width="24%">&nbsp;</td>								
								</tr>

								<tr>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Address 3:</font></b></div>
									</td>
									<td width="24%">
											<input type="text" size="50"  tabIndex="11" name="Addr3"/>
									</td>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Main Phone:</font></b></div>
									</td>
									<td width="24%">
											<input type="text" size="20"  tabIndex="15" name="PhoneMain"/>
									</td>
								</tr>              

								<tr> 
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Address 4:</font></b></div>
									</td>
									<td width="24%">
											<input type="text" size="50"  tabIndex="12" name="Addr4" property="Addr4"/>
									</td>
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Fax Phone:</font></b></div>
									</td>
									<td width="24%">
											<input type="text" size="20"  tabIndex="16" name="PhoneFax"/>
									</td>
								</tr>

								<tr> 
									<td bgcolor="#FFCC00" width="10%"> 
										<div align="right"><b><font size="2">Zip:</font></b></div>
									</td>
									<td width="24%">
											<input type="text" size="10"  tabIndex="13" name="Zip"/>
											<input type="text" size="5"  tabIndex="14" name="Zipext"/>
									</td>
									<td width="10%">&nbsp; </td>
									<td width="24%">&nbsp;</td>
								</tr>
						</table>

					<p>&nbsp;</p>
					<input type="button" name="save_bus" value="Save" style=width:90  onClick="handleAction(this.name)">
					<input type="button" name="del_bus" value="Delete" style=width:90 onClick="handleAction(this.name)">
					<input type="button" name="back_bus" value="Back" style=width:90 onClick="handleAction(this.name)">
                    <input name="clientAction" type="hidden">
				</form>
		</body>
</html>
