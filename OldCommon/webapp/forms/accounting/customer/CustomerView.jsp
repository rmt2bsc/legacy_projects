<%@ page import="com.bean.Address" %>
<%@ page import="com.bean.Business" %>
<%@ page import="com.bean.Person" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.bean.Zipcode" %>
<%@ page import="com.constants.SalesConst" %>

<%
   // Retrieve data to be maintained from the request object.
   Business bus = (Business) request.getAttribute(SalesConst.CLIENT_DATA_BUSINESS);
   String custType = (String) request.getAttribute(SalesConst.CLIENT_DATA_CUSTTYPE);
%>

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
				   
<table width="90%" border="0">
	<caption align="left"> <h3><%=pageTitle%></h3> </caption>
	<tr> 
		<td width="19%"  class="clsTableFormHeader"> 
			<div align="right"><font size="2"><b>Customer Type:</b></font></div>
		</td>	
		<td width="81%">
		   <gen:Evaluate expression="<%=custType%>">
			  <gen:When expression="1">Business</gen:When>
			  <gen:When expression="2">Personal</gen:When>
		   </gen:Evaluate>
		</td>									 
	</tr>
	<tr> 
		<td width="19%"  class="clsTableFormHeader"> 
			<div align="right">
			   <font size="2">
				 <b>Account Number:</b>
			   </font>
			</div>
		</td>	
		<td width="81%">
			<beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER%>" type="hidden" name="CustomerId" property="Id"/>
		    <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER%>" property="AccountNo"/>
		</td>									 
	</tr>
	<tr> 
		<td width="19%"  class="clsTableFormHeader"> 
			<div align="right">
			   <font size="2">
				 <b>Credit Limit:</b>
			   </font>
			</div>
		</td>	
		<td width="81%">
		  <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_CUSTOMER%>" property="CreditLimit"/>
		</td>									 
	</tr>				
	<tr> 
		<td width="19%"  class="clsTableFormHeader"> 
			<div align="right">
			   <font size="2">
				 <b>Balance:</b>
			   </font>
			</div>
		</td>	
		<td width="81%">
		   <beanlib:WrapperValue dataSource="<%=SalesConst.CLIENT_DATA_BALANCE%>"/>
		</td>									 
	</tr>			
</table>
<br>

<!-- Display either personal or business information -->			
<gen:Evaluate expression="<%=custType%>">
   <gen:When expression="2">
		<table width="90%" border="0">
		   <tr> 
				<td width="10%" class="clsTableFormHeader">First Name:</td>
				<td width="21%"> 
				   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_PERSON%>" property="Firstname"/>
				</td>
				<td width="12%" class="clsTableFormHeader">Last Name:</td>
				<td width="21%">
					<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_PERSON%>" property="Lastname"/>
				</td>
		   </tr>
		   <tr> 
				<td class="clsTableFormHeader">Middle Name:</td>
				<td> 
					<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_PERSON%>" property="Midname"/>
				</td>	
				<td class="clsTableFormHeader">Maiden Name:</td>
				<td>
					<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_PERSON%>" property="Maidenname"/>
				</td>
		   </tr>
		   <tr>
				<td class="clsTableFormHeader">SSN:</td>
				<td> 
					<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_PERSON%>" property="Ssn"/>
				</td>
				<td class="clsTableFormHeader">Birth Date:</td>
				<td> 
					<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_PERSON%>" property="BirthDate" format="MM/dd/yyyy" />
				</td>
		   </tr>
		</table>
   </gen:When>
   
   <gen:When expression="1">
		<table width="90%" border="0">
			<tr> 
				<td width="10%" class="clsTableFormHeader"> Business Name:</td>
				<td width="21%"> 
				   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_BUSINESS%>" property="Longname"/>
				</td>
				<td width="12%" class="clsTableFormHeader">Contact First Name:</td>
				<td width="24%"> 
					<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_BUSINESS%>" property="ContactFirstname"/>
				</td>
			</tr>	
			<tr> 
				<td class="clsTableFormHeader">Business Type:</td>
				<td> 
					 <db:Lookup dataSource=""
								masterCodeName=""
								masterCodeValue="<%=String.valueOf(bus.getBusType()) %>"
								type="1"
								lookupSource="busTypeDso"
								lookupCodeName="Id"
								lookupDisplayName="Longdesc"/>
				</td>
				<td class="clsTableFormHeader">Contact Last Name:</td>
				<td> 
				  <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_BUSINESS%>" property="ContactLastname"/>
				</td>
			</tr>
			<tr> 
				<td class="clsTableFormHeader">Service Type:</td>
				<td>
					 <db:Lookup dataSource=""
								masterCodeName=""
								masterCodeValue="<%=String.valueOf(bus.getServType()) %>"
								type="1"
								lookupSource="busServTypeDso"
								lookupCodeName="Id"
								lookupDisplayName="Longdesc"/>					
				</td>
				<td class="clsTableFormHeader">Contact Phone:</td>
				<td> 
					<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_BUSINESS%>" property="ContactPhone"/>
				</td>							
			</tr>
			<tr> 
				<td class="clsTableFormHeader">Tax Id:</td>
				<td> 
					<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_BUSINESS%>" property="TaxId"/>
				</td>
				<td class="clsTableFormHeader">Contact Phone Ext.:</td>
				<td> 
					<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_BUSINESS%>" property="ContactExt"/>
				</td>
			</tr>
		</table>			   
   </gen:When>
</gen:Evaluate>
<br>				

<!-- Display address information -->
<table width="90%" border="0">
	<tr> 
		<td class="clsTableFormHeader" width="10%">Address 1:</td>
		<td width="24%">
			<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_ADDRESS%>" property="Addr1"/>
		</td>
		<td class="clsTableFormHeader" width="10%">City:</td>
		<td width="24%">
		   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_ZIP%>" property="City"/>
		</td>
	</tr>
	<tr>
		<td class="clsTableFormHeader" width="10%"> Address 2:</td>
		<td width="24%">
			<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_ADDRESS%>" property="Addr2"/>
		</td>
		<td class="clsTableFormHeader" width="10%">State:</td>
		<td width="24%">
		   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_ZIP%>" property="State"/>
		</td>								
	</tr>
	<tr>
		<td class="clsTableFormHeader" width="10%">Address 3:</td>
		<td width="24%">
			<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_ADDRESS%>" property="Addr3"/>
		</td>
		<td class="clsTableFormHeader" width="10%">Main Phone:</td>
		<td width="24%">
			<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_ADDRESS%>" property="PhoneMain"/>
		</td>
	</tr>              
	<tr> 
		<td class="clsTableFormHeader" width="10%">Address 4:</td>
		<td width="24%">
			<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_ADDRESS%>" property="Addr4"/>
		</td>
		<td class="clsTableFormHeader" width="10%">Fax Phone:</td>
		<td width="24%">
			<beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_ADDRESS%>" property="PhoneFax"/>
		</td>
	</tr>
	<tr> 
		<td class="clsTableFormHeader" width="10%">Zip:</td>
		<td width="24%">
		   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_ADDRESS%>" property="Zip"/>
		   <beanlib:ElementValue dataSource="<%=SalesConst.CLIENT_DATA_ADDRESS%>" property="Zipext"/>
		</td>
		<td width="10%">&nbsp; </td>
		<td width="24%">&nbsp;</td>
	</tr>
	<tr>   
		<td colspan="4"><img src="images/clr.gif" height="20"></td>
	</tr>
	<tr>
		<td colspan="4" class="clsErrorText">
		   <gen:ShowPageMessages dataSource="ERRORS"/>  
		</td>
	</tr>				
</table>
