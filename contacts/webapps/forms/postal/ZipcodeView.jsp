<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page import="com.api.ContactsConst" %>
<%@ page import="com.constants.GeneralConst" %>



<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<%
  String pageTitle = "Postal Zip Code View";
%>   

<html>
	<head>
			<title><%=pageTitle%></title>
			<meta http-equiv="Pragma" content="no-cache">
    	<meta http-equiv="Expires" content="-1">
	  	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
			<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
			<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
			<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	</head>
    
	<body bgcolor="#FFFFFF" text="#000000">
	    <h3><strong><%=pageTitle%></strong></h3>
		<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/Zipcode.View">
					<table width="90%" border="0">
						<caption align="left"><font color="blue"><strong>General Data</strong></font></caption>
						<tr> 
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>Zip Code:</b></font></div>
							</td>
							<td width="19%">
								<beanlib:InputControl value="#record.Zip"/> 
							</td>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>State:</b></font></div>
							</td>
							<td width="19%">
							    <beanlib:InputControl value="#record.State"/>								 
							</td>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b><font size="2">County:</font></b></font></div>
							</td>
							<td width="24%">
								<beanlib:InputControl value="#record.CountyName"/> 
							</td>										
						</tr>
						<tr> 
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>City</b></font></div>
							</td>
							<td width="24%">
								<beanlib:InputControl value="#record.City"/>
							</td>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><b><font size="2">Area Code:</font></b></div>
							</td>
							<td width="21%">
							    <beanlib:InputControl value="#record.AreaCode"/>
							</td>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><b><font size="2">Time Zone:</font></b></div>
							</td>
							<td width="21%">
								<beanlib:InputControl value="#record.TimezoneDescr"/>
							</td>										
						</tr>
						<tr> 
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>City Type:</b></font></div>
							</td>
							<td width="19%">
								<beanlib:InputControl value="#record.CitytypeDescr"/>
							</td>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>City Alias:</b></font></div>
							</td>
							<td width="24%">
								<beanlib:InputControl value="#record.CityAliasName"/>
							</td>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>Day Light Savings:</b></font></div>
							</td>
							<td width="24%">
								<beanlib:InputControl value="#record.DayLightSaving"/>
							</td>
	                    </tr>
	                    <tr>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>Latitude:</b></font></div>
							</td>
							<td width="19%">
								<beanlib:InputControl value="#record.Latitude"/>
							</td>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>Longitude:</b></font></div>
							</td>
							<td width="24%">
								<beanlib:InputControl value="#record.Longitude"/>
							</td>
							<td bgcolor="#FFCC00" width="12%"> 
								<div align="right"><font size="2"><b>Elevation:</b></font></div>
							</td>
							<td width="24%">
								<beanlib:InputControl value="#record.Elevation"/>
							</td>
						</tr>
					</table>
					<br>
					<table width="50%" border="0">
						<caption align="left"><font color="blue"><strong>Metropolitan Statistical Area Data</strong></font></caption>
						<tr>	
							<td bgcolor="#FFCC00" width="25%"> 
								<div align="right"><b><font size="2">MSA:</font></b></div>
							</td>
							<td width="25%">
							    <beanlib:InputControl value="#record.Msa"/>
							</td>
							<td bgcolor="#FFCC00" width="25%"> 
								<div align="right"><font size="2"><b>PMSA:</b></font></div>
							</td>
							<td width="25%">
							    <beanlib:InputControl value="#record.Pmsa"/>
							</td>							
						</tr>
					</table>
					<br>
					<table width="50%" border="0">
						<caption align="left"><font color="blue"><strong>Core Based Statistical Area Data</strong></font></caption>					
							<td bgcolor="#FFCC00" width="25%"> 
								<div align="right"><font size="2"><b>CBSA:</b></font></div>
							</td>
							<td width="25%">
								<beanlib:InputControl value="#record.Cbsa"/>
							</td>
							<td bgcolor="#FFCC00" width="25%"> 
								<div align="right"><font size="2"><b>CBSA Div:</b></font></div>
							</td>
							<td width="25%">
								<beanlib:InputControl value="#record.CbsaDiv"/>
							</td>							
						</tr>
					</table>
					<br>
					<table width="90%" border="0">
						<caption align="left"><font color="blue"><strong>Population Related Area</strong></font></caption>					
	                    <tr> 
							<td bgcolor="#FFCC00" width="15%"> 
								<div align="right"><font size="2"><b>Persons/Household:</b></font></div>
							</td>
							<td width="18%"> 
								<div align="left">
								    <beanlib:InputControl value="#record.PersonsPerHousehold"/>
								</div>
							</td>
							<td bgcolor="#FFCC00" width="15%"> 
								<div align="right"><font size="2"><b>Households/Zipcode:</b></font></div>
							</td>
							<td width="18%> 
								<div align="left">
								    <beanlib:InputControl value="#record.HouseholdsPerZipcode"/>
								</div>
							</td>
							<td bgcolor="#FFCC00" width="15%"> 
								<div align="right"><font size="2"><b>Zipcode Population:</b></font></div>
							</td>
							<td width="18%> 
								<div align="left">
								    <beanlib:InputControl value="#record.ZipcodePopulation"/>
								</div>
							</td>							
						</tr>
	                    <tr> 
							<td bgcolor="#FFCC00"> 
								<div align="right"><font size="2"><b>White Population:</b></font></div>
							</td>
							<td> 
								<div align="left">
								    <beanlib:InputControl value="#record.WhitePopulation"/>
								</div>
							</td>
							<td bgcolor="#FFCC00"> 
								<div align="right"><font size="2"><b>Black Population:</b></font></div>
							</td>
							<td> 
								<div align="left">
								    <beanlib:InputControl value="#record.BlackPopulation"/>
								</div>
							</td>
							<td bgcolor="#FFCC00"> 
								<div align="right"><font size="2"><b>Hispanic Population:</b></font></div>
							</td>
							<td> 
								<div align="left">
								    <beanlib:InputControl value="#record.HispanicPopulation"/>
								</div>
							</td>							
						</tr>						
	                    <tr> 
							<td bgcolor="#FFCC00"> 
								<div align="right"><font size="2"><b>Income/Household:</b></font></div>
							</td>
							<td> 
								<div align="left">
								    <beanlib:InputControl value="#record.IncomePerHousehold"/>
								</div>
							</td>
							<td bgcolor="#FFCC00"> 
								<div align="right"><font size="2"><b>Average Household Value:</b></font></div>
							</td>
							<td> 
								<div align="left">
								    <beanlib:InputControl value="#record.AverageHouseValue"/>
								</div>
							</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>												
					</table>
					<br>

				<p>&nbsp;</p>
				<p>&nbsp;</p>

				<input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style=width:90 onClick="handleAction('_self', document.DataForm, this.name)">
        <input name="clientAction" type="hidden">
			</form>
		</body>
</html>
