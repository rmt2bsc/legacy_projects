 <jsp:useBean id="QUERY_BEAN" scope="session" class="com.bean.RMT2TagQueryBean"/>
 <%@ page import="com.bean.criteria.UserCriteria" %>

   <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/User.Search">
     <font size="4" style="color:blue">Selection Criteria</font>  
     <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:60%; height:55px">
		     <br>
		     <input type="hidden" name="UserLocationId" value="0">
				 <table  width="100%" border="0">
						 <tr>
								 <th align="right"><strong>Login ID:</strong></th>
								 <td width="10%">
								   <beanlib:InputControl type="text" name="qry_Username" value="#QUERY_BEAN.CustomObj.qry_Username"/>
								 </td>
								 <th align="right" width="10%"><strong>Start Date:</strong></th>
								 <td width="8%">
								    <beanlib:InputControl type="text" name="qry_StartDate" value="#QUERY_BEAN.CustomObj.qry_StartDate"/>
								 </td>
						 </tr>
						 <tr>
								 <th width="10%" align="right"><strong>First Name:</strong></th>
								 <td width="15%">
								    <beanlib:InputControl type="text" name="qry_Firstname" value="#QUERY_BEAN.CustomObj.qry_Firstname"/>
								 </td>
								 <th width="10%" align="right"><strong>Last Name:</strong></th>
								 <td width="20%">
								   <beanlib:InputControl type="text" name="qry_Lastname" value="#QUERY_BEAN.CustomObj.qry_Lastname"/>
								 </td>
						 </tr>
						 <tr>
								 <th width="10%" align="right"><strong>SSN:</strong></th>
								 <td width="20%">
								   <beanlib:InputControl type="text" name="qry_Ssn" value="#QUERY_BEAN.CustomObj.qry_Ssn"/>
								 </td>							 
						 </tr>
		     </table>
     </div>       
     <br>

			<!-- Display command buttons -->         
			<table width="100%" cellpadding="0" cellspacing="0">
			  <tr>
					<td colspan="2">
					  <input name="<%=GeneralConst.REQ_SEARCH%>" type="button" value="Search" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
						  <input type="reset" name="clear" value="Clear" style="width:90">
			 		</td>
				  </tr>		
		  </table>        
			<input name="clientAction" type="hidden">
   </form>
 