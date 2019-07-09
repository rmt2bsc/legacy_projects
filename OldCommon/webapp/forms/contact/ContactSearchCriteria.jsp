<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<jsp:useBean id="contactArgs" class="com.bean.ContactCombine" scope="session"/>

<html>
		<head>
			<title>Contacts Search</title>
			<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		</head>

    <gen:InitAppRoot id="APP_ROOT"/>
	
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
												  
    <script type="text/javascript" src="/js/RMT2Contact.js"></script>        												  					  
	<script>												  
		function handleAction(action) {
			this.ContactSearchForm.clientAction.value = action;
			this.ContactSearchForm.target = "windowFrame";
			this.ContactSearchForm.submit();
		}
    </script>
    
	<body bgcolor="#FFFFFF" text="#000000">     
			<form name="ContactSearchForm" method="post" action="<%=APP_ROOT%>/contactservlet">
				<table width="30%" border="0">
					<tr> 
						<td width="33%" bgcolor="#FFCC00"> 
							<div align="right"><font size="2"><b>Contact Type:</b></font></div>
						</td>
						<td width="67%">
							<select name="ContactType" onChange="enableCombinedContactControls(ContactSearchForm, ContactSearchForm.ContactType.value)">
								<option value="1" <%= contactArgs.getContactType().equals("1") ? " selected" : "" %>>Business</option>              
								<option value="2" <%= contactArgs.getContactType().equals("2") ? " selected" : "" %>>Personal</option>
							</select>
						</td>
					</tr>    
				</table>
				<br>
				
				<table width="90%" border="0">
					<caption align="left" style="color:blue">Business Contact Criteria</caption>
					<tr> 
						<td width="10%" bgcolor="#FFCC00"> 
							<div align="right"><b><font size="2">Business Id:</font></b></div>
						</td>
						<td width="21%"> 
							<div id="bus_id" align="left"> 
								<input type="text" name="BusinessId" value="<jsp:getProperty name='contactArgs' property='businessId'/>" size="10">
							</div>
						</td>
						<td width="12%" bgcolor="#FFCC00"> 
							<div align="right"><b><font size="2">Tax Id:</font></b></div>
						</td>
						<td width="24%"> 
							<div align="left"> 
								<input type="text" name="BusTaxId" value="<jsp:getProperty name='contactArgs' property='busTaxId'/>" size="10">
							</div>
						</td>
						<td width="12%" bgcolor="#FFCC00"> 
							<div align="right"><b><font size="2">Web Site:</font></b></div>
						</td>
						<td width="21%"> 
							<div align="left"> 
								<input type="text" name="BusWebsite" value="<jsp:getProperty name='contactArgs' property='busWebsite'/>" size="20">
							</div>
						</td>
					</tr>
					<tr> 
						<td width="10%" bgcolor="#FFCC00"> 
							<div align="right"><b><font size="2">Name:</font></b></div>
						</td>
						<td width="21%"> 
							<div align="left"> 
								<input type="text" name="BusLongname" value="<jsp:getProperty name="contactArgs" property="busLongname"/>" size="20">
							</div>
						</td>
						<td width="12%" bgcolor="#FFCC00"> 
							<div align="right"><b><font size="2">Contact First Name:</font></b></div>
						</td>
						<td width="24%"> 
							<div align="left"> 
								<input type="text" name="BusContactFirstname" value="<jsp:getProperty name='contactArgs' property='busContactFirstname'/>" size="20">
							</div>
						</td>
						<td width="12%" bgcolor="#FFCC00"> 
							<div align="right"><b><font size="2">Contact Last Name:</font></b></div>
						</td>
						<td width="21%"> 
							<div align="left"> 
								<input type="text" name="BusContactLastname" value="<jsp:getProperty name='contactArgs' property='busContactLastname'/>" size="20">
							</div>
						</td>
					</tr>
					<tr> 
						<td width="10%" bgcolor="#FFCC00"> 
							<div align="right"><b><font size="2">Contact Phone:</font></b></div>  
						</td>
						<td width="21%"> 
							<div align="left"> 
								<input type="text" name="BusContactPhone" value="<jsp:getProperty name='contactArgs' property='busContactPhone'/>" size="20">
							</div>
						</td>
						<td width="12%" bgcolor="#FFCC00"> 
							<div align="right"><b><font size="2">Business Type:</font></b></div>
						</td>
						<td width="24%"> 
							<div id="bus_bus_type" align="left"> 
								<db:InputControl dataSource="busTypeDso"
														 type="select"
														 name="BusType"
														 codeProperty="Id"
														 displayProperty="Longdesc"
														 selectedValue="<jsp:getProperty name='contactArgs' property='busType'/>"/>	
							</div>
						</td>
						<td width="12%"> 
							<div align="right"><b></b></div>
						</td>
						<td width="21%"> 
							</div>
						</td>
					</tr>
					<tr> 
						<td width="10%" bgcolor="#FFCC00"> 
							<div align="right"><b><font size="2">Service Type:</font></b></div>
						</td>
						<td colspan="2">
									<div id="bus_serv_type" align="left"> 
										<db:InputControl dataSource="busServTypeDso"
																 type="select"
																 name="BusServType"
																 codeProperty="Id"
																 displayProperty="Longdesc"
																 selectedValue="<jsp:getProperty name='contactArgs' property='busServType'/>"/>	

					</td>
						<td width="24%">&nbsp;</td>
						<td width="12%">&nbsp;</td>      
						<td width="21%">&nbsp;</td>      
					</tr>
				</table>
				<br>

				<table width="90%" border="0">
					<caption align="left" style="color:blue">Personal Contact Criteria</caption>
					<tr> 
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><font size="2"><b>Person Id:</b></font></div>
						</td>
						<td width="19%">
								<input type="text" name="PersonId" value="<jsp:getProperty name='contactArgs' property='personId'/>" size="10">								
						</td>
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><font size="2"><b>SSN:</b></font></div>
						</td>
						<td width="24%">
								<input type="text" name="PerSsn" value="<jsp:getProperty name='contactArgs' property='perSsn'/>" size="10">								
						</td>
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><b><font size="2">Birth Date:</font></b></div>
						</td>
						<td width="21%">
								<input type="text" name="PerBirthDate" value="<jsp:getProperty name='contactArgs' property='perBirthDate'/>" size="10">								
						</td>
					</tr>
					<tr> 
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><font size="2"><b>First Name:</b></font></div>
						</td>
						<td width="19%">
								<input type="text" name="PerFirstname" value="<jsp:getProperty name='contactArgs' property='perFirstname'/>" size="10">								
						</td>
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><font size="2"><b>Middle Name:</b></font></div>
						</td>
						<td width="24%">
								<input type="text" name="PerMidname" value="<jsp:getProperty name='contactArgs' property='perMidname'/>" size="10">								
						</td>
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><b><font size="2">Last Name:</font></b></div>
						</td>
						<td width="21%">
								<input type="text" name="PerLastname" value="<jsp:getProperty name='contactArgs' property='perLastname'/>" size="10">								
						</td>
					</tr>
					<tr> 
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><font size="2"><b>Maiden Name:</b></font></div>
						</td>
						<td width="19%">
								<input type="text" name="PerMaidenname" value="<jsp:getProperty name='contactArgs' property='perMaidenname'/>" size="10">								
						</td>
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><font size="2"><b>Title</b></font></div>
						</td>
						<td width="24%">
									<db:InputControl dataSource="perTitleDso"
															 type="select"
															 name="PerTitle"
															 codeProperty="Id"
															 displayProperty="Longdesc"
															 selectedValue="<jsp:getProperty name='contactArgs' property='perTitle'/>"/>								
						</td>
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><b><font size="2">Generation:</font></b></div>
						</td>
						<td width="21%">
								<input type="text" name="PerGeneration" value="<jsp:getProperty name='contactArgs' property='perGeneration'/>" size="5">								
						</td>
					</tr>
					<tr> 
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><font size="2"><b>Gender:</b></font></div>
						</td>
						<td width="19%">
									<db:InputControl dataSource="perGenderDso"
															 type="select"
															 name="PerGenderId"
															 codeProperty="Id"
															 displayProperty="Longdesc"
															 selectedValue="<jsp:getProperty name='contactArgs' property='perGenderId'/>"/>								
						</td>
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><font size="2"><b><font size="2">Marital Status:</font></b></font></div>
						</td>
						<td width="24%">
									<db:InputControl dataSource="perMaritalStatDso"
															 type="select"
															 name="PerMaritalStatus"
															 codeProperty="Id"
															 displayProperty="Longdesc"
															 selectedValue="<jsp:getProperty name='contactArgs' property='perMaritalStatus'/>"/>								
						</td>
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><b><font size="2">Race:</font></b></div>
						</td>
						<td width="21%">
									<db:InputControl dataSource="perRaceDso"
															 type="select"
															 name="PerRaceId"
															 codeProperty="Id"
															 displayProperty="Longdesc"
															 selectedValue="<jsp:getProperty name='contactArgs' property='perRaceId'/>"/>								
						</td>
					</tr>
					<tr> 
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><font size="2"><b>Email:</b></font></div>
						</td>
						<td colspan="3"> 
							<div align="left">
								<input type="text" name="PerEmail" value="<jsp:getProperty name='contactArgs' property='perEmail'/>" size="45">								
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
					<caption align="left" style="color:blue">Address Criteria</caption>
					<tr> 
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><b><font size="2">Address 1:</font></b></div>
						</td>
						<td width="18%">
								<input type="text" name="Addr1" value="<jsp:getProperty name='contactArgs' property='addr1'/>" size="10">								
						</td>
						<td bgcolor="#FFCC00" width="13%"> 
							<div align="right"><b><font size="2">Address 2:</font></b></div>
						</td>
						<td width="23%">
								<input type="text" name="Addr2" value="<jsp:getProperty name='contactArgs' property='addr2'/>" size="10">									
						</td>
						<td bgcolor="#FFCC00" width="13%"> 
							<div align="right"><b><font size="2">Address 3:</font></b></div>
						</td>
						<td width="21%">
								<input type="text" name="Addr3" value="<jsp:getProperty name='contactArgs' property='addr3'/>" size="10">									
						</td>
					</tr>
					<tr> 
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><b><font size="2">Address 4:</font></b></div>
						</td>
						<td width="18%">
								<input type="text" name="Addr4" value="<jsp:getProperty name='contactArgs' property='addr4'/>" size="10">									
						</td>
						<td bgcolor="#FFCC00" width="13%"> 
							<div align="right"><b><font size="2">City:</font></b></div>
						</td>
						<td width="23%">
								<input type="text" name="AddrCity" value="<jsp:getProperty name='contactArgs' property='addrCity'/>" size="10">									
						</td>
						<td bgcolor="#FFCC00" width="13%"> 
							<div align="right"><b><font size="2">State:</font></b></div>
						</td>
						<td width="21%">
								<input type="text" name="AddrState" value="<jsp:getProperty name='contactArgs' property='addrState'/>" size="10">								
						</td>
					</tr>
					<tr> 
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><b><font size="2">Zip:</font></b></div>
						</td>
						<td width="18%">
								<input type="text" name="AddrZip" 	value="<jsp:getProperty name='contactArgs' property='addrZip'/>" 	size="10">									
						</td>
						<td bgcolor="#FFCC00" width="13%"> 
							<div align="right"><b><font size="2">Home Phone:</font></b></div>
						</td>
						<td width="23%">
								<input type="text"  name="AddrPhoneHome"  value="<jsp:getProperty name='contactArgs' property='addrPhoneHome'/>" size="10">								
						</td>
						<td bgcolor="#FFCC00" width="13%"> 
							<div align="right"><b><font size="2">Work Phone:</font></b></div>
						</td>
						<td width="21%">
								<input type="text"  name="AddrPhoneWork"  value="<jsp:getProperty name='contactArgs' property='addrPhoneWork'/>"  size="10">
						</td>
					</tr>
					<tr> 
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><b><font size="2">Work Extension:</font></b></div>
						</td>
						<td width="18%">
								<input type="text"  name="AddrPhoneExt" 	value="<jsp:getProperty name='contactArgs' property='addrPhoneExt'/>" size="10">								
						</td>
						<td bgcolor="#FFCC00" width="13%"> 
							<div align="right"><b><font size="2">Mobile Phone:</font></b></div>
						</td>
						<td width="23%">
								<input type="text" name="AddrPhoneCell" value="<jsp:getProperty name='contactArgs' property='addrPhoneCell'/>" size="10">								
						</td>
						<td bgcolor="#FFCC00" width="13%"> 
							<div align="right"><b><font size="2">Fax:</font></b></div>
						</td>
						<td width="21%">
								<input type="text" name="AddrPhoneFax" value="<jsp:getProperty name='contactArgs' property='addrPhoneFax'/>" size="10">								
						</td>
					</tr>
					<tr> 
						<td bgcolor="#FFCC00" width="12%"> 
							<div align="right"><b><font size="2">Pager:</font></b></div>
						</td>
						<td width="18%">
								<input type="text" name="AddrPhonePager" value="<jsp:getProperty name='contactArgs' property='addrPhonePager'/>" size="10">								
						</td>
						<td width="13%"> 
							<div align="right"><b><font size="2"></font></b></div>
						</td>
						<td width="23%">&nbsp;</td>
						<td width="13%"> 
							<div align="right"><b><font size="2"></font></b></div>
						</td>
						<td width="21%">&nbsp;</td>
					</tr>
				</table>
				<p>&nbsp;</p>
				<p>&nbsp;</p>

				<input type="button" name="search" value="Search" style=width:90  onClick="handleAction(this.name)">
				<input type="button" name="reset" value="Reset" style=width:90 onClick="handleAction(this.name)">
				<input type="button" name="back_code" value="Back" style=width:90 onClick="handleAction(this.name)">
        	    <input name="clientAction" type="hidden">
			</form>
	</body>
</html>
