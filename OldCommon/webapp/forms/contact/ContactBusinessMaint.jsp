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
    <%
         // Ensure that request parameters business id and address id have values.
         // Otherwise, force view to return an empty result set.
         String criteria;
         String business_id = request.getParameter("business_id");
         String address_id  = request.getParameter("address_id");
         if (business_id == null || address_id == null) {
           criteria = "business.id = 0 and address.id = 0" ;
         }
         else {
           criteria = "business.id = " + business_id + " and address.id = " + address_id ;
         }
     %>
     
    <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
    <db:datasource id="dso" 
                          classId="com.api.DataSourceApi" 
                          connection="con"
 												  query="BusinessAddressView"
 												  where="<%=criteria%>"
												  type="xml"/>    
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
					<db:LoopRows dataSource="dso">
							<table width="90%" border="0">
								<caption align="left"> <strong>Business Contact Maintenance</strong> </caption>
								<tr> 
									<td width="10%" bgcolor="#FFCC00"> 
										<div align="right"><b><font size="2">Business Id:</font></b></div>
									</td>
									<td width="21%"> 
										<div id="bus_id" align="left"> 
												<db:InputControl dataSource="dso" type="hidden" name="Id" property="BusinessId"/>
												<db:InputControl dataSource="dso" type="hidden" name="BusinessId" property="AddrBusinessId"/>
												<db:InputControl dataSource="dso" type="hidden" name="PersonId" property="AddrPersonId"/>
												<db:InputControl dataSource="dso" type="hidden"  name="AddrId" property="AddrId"/>
												<db:ElementValue dataSource="dso" property="BusinessId"/> 
										</div>
									</td>

									<td width="12%" bgcolor="#FFCC00"> 
										<div align="right"><b><font size="2">Contact First Name:</font></b></div>
									</td>
									<td width="24%"> 
										<div align="left"> 
												<db:InputControl dataSource="dso" type="text" size="20" tabIndex="5" name="ContactFirstname" property="BusContactFirstname"/>
										</div>
									</td>
								</tr>	

								<tr> 
									<td width="10%" bgcolor="#FFCC00"> 
										<div align="right"><b><font size="2">Business Name:</font></b></div>
									</td>
									<td width="21%"> 
										<div align="left"> 
												<db:InputControl dataSource="dso" type="text" size="60" tabIndex= "1" name="Longname" property="BusLongname"/>
										</div>
									</td>

									<td width="12%" bgcolor="#FFCC00"> 
										<div align="right"><b><font size="2">Contact Last Name:</font></b></div>
									</td>
									<td width="21%"> 
										<div align="left"> 
												<db:InputControl dataSource="dso" type="text" size="20" tabIndex= "6" name="ContactLastname" property="BusContactLastname"/>
										</div>
									</td>
								</tr>
								<tr> 
									<td width="12%" bgcolor="#FFCC00"> 
										<div align="right"><b><font size="2">Business Type:</font></b></div>
									</td>
									<td width="24%"> 
										<div align="left"> 
												 <db:Lookup dataSource="dso"
															 masterCodeName="BusType"
															 name="BusType"
															 type="3"
															 lookupSource="busTypeDso"
															 lookupCodeName="Id"
															 lookupDisplayName="Longdesc"
															 tabIndex="2"/>								
										</div>
									</td>
									<td width="10%" bgcolor="#FFCC00"> 
											<div align="right"><b><font size="2">Contact Phone:</font></b></div>
									</td>
									<td width="21%"> 
											<div align="left"> 
													<db:InputControl dataSource="dso" type="text" size="20" tabIndex= "7" name="ContactPhone" property="BusContactPhone"/>
											</div>
									</td>							
								</tr>

								<tr> 
									<td width="10%" bgcolor="#FFCC00"> 
										<div align="right"><b><font size="2">Service Type:</font></b></div>
									</td>
									<td width="30%">
												<div align="left"> 
														 <db:Lookup dataSource="dso"
																	 masterCodeName="BusServType"
																	 name="ServType"
																	 type="3"
																	 lookupSource="busServTypeDso"
																	 lookupCodeName="Id"
																	 lookupDisplayName="Longdesc"
																	 tabIndex= "3"/>
												</div>
									</td>
									<td width="12%" bgcolor="#FFCC00"> 
										<div align="right"><b><font size="2">Contact Phone Ext.</font></b></div>
									</td>
									<td width="24%"> 
										<div align="left"> 
												<db:InputControl dataSource="dso" type="text" size="20" tabIndex="8" name="ContactExt" property="BusContactExt"/>
										</div>
									</td>
									
								</tr>
								<tr>
									<td width="12%" bgcolor="#FFCC00"> 
										<div align="right"><b><font size="2">Web Site:</font></b></div>
									</td>
									<td width="21%"> 
										<div align="left"> 
												<db:InputControl dataSource="dso" type="text" size="50" tabIndex="4" name="Website" property="BusWebsite"/>
										</div>
									</td>
									<td width="12%" bgcolor="#FFCC00"> 
										<div align="right"><b><font size="2">Tax Id:</font></b></div>
									</td>
									<td width="24%"> 
										<div align="left"> 
												<db:InputControl dataSource="dso" type="text" size="20" tabIndex="9" name="TaxId" property="BusTaxId"/>
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
												<db:InputControl dataSource="dso" type="text" size="50" tabIndex="9" name="Addr1" property="Addr1"/>
										</td>
										<td bgcolor="#FFCC00" width="10%"> 
											<div align="right"><b><font size="2">City:</font></b></div>
										</td>
										<td width="24%">
												<db:ElementValue dataSource="dso" property="ZipCity"/> 
										</td>
									</tr>
									<tr>
										<td bgcolor="#FFCC00" width="10%"> 
											<div align="right"><b><font size="2">Address 2:</font></b></div>
										</td>
										<td width="24%">
												<db:InputControl dataSource="dso" type="text" size="50"  tabIndex="10" name="Addr2" property="Addr2"/>
										</td>
										<td bgcolor="#FFCC00" width="10%"> 
											<div align="right"><b><font size="2">State:</font></b></div>
										</td>
										<td width="24%">
												<db:ElementValue dataSource="dso" property="ZipState"/> 
										</td>								
									</tr>

									<tr>
										<td bgcolor="#FFCC00" width="10%"> 
											<div align="right"><b><font size="2">Address 3:</font></b></div>
										</td>
										<td width="24%">
												<db:InputControl dataSource="dso" type="text" size="50"  tabIndex="11" name="Addr3" property="Addr3"/>
										</td>
										<td bgcolor="#FFCC00" width="10%"> 
											<div align="right"><b><font size="2">Main Phone:</font></b></div>
										</td>
										<td width="24%">
												<db:InputControl dataSource="dso" type="text" size="20"  tabIndex="15" name="PhoneMain" property="AddrPhoneMain"/>
										</td>
									</tr>              

									<tr> 
										<td bgcolor="#FFCC00" width="10%"> 
											<div align="right"><b><font size="2">Address 4:</font></b></div>
										</td>
										<td width="24%">
												<db:InputControl dataSource="dso" type="text" size="50"  tabIndex="12" name="Addr4" property="Addr4"/>
										</td>
										<td bgcolor="#FFCC00" width="10%"> 
											<div align="right"><b><font size="2">Fax Phone:</font></b></div>
										</td>
										<td width="24%">
												<db:InputControl dataSource="dso" type="text" size="20"  tabIndex="16" name="PhoneFax" property="AddrPhoneFax"/>
										</td>
									</tr>

									<tr> 
										<td bgcolor="#FFCC00" width="10%"> 
											<div align="right"><b><font size="2">Zip:</font></b></div>
										</td>
										<td width="24%">
												<db:InputControl dataSource="dso" type="text" size="10"  tabIndex="13" name="Zip" property="AddrZip"/>
												<db:InputControl dataSource="dso" type="text" size="5"  tabIndex="14" name="Zipext" property="AddrZipext"/>
										</td>
										<td width="10%">&nbsp; </td>
										<td width="24%">&nbsp;</td>
									</tr>
							</table>
					</db:LoopRows>
					<p>&nbsp;</p>
					<input type="button" name="save_bus" value="Save" style=width:90  onClick="handleAction(this.name)">
					<input type="button" name="del_bus" value="Delete" style=width:90 onClick="handleAction(this.name)">
					<input type="button" name="back_bus" value="Back" style=width:90 onClick="handleAction(this.name)">
                    <input name="clientAction" type="hidden">
				</form>
		</body>
</html>
