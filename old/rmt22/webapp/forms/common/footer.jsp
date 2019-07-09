<%
  String userId = null;
	String parms = null; 
  if (SESSION_BEAN != null) {
      userId = SESSION_BEAN.getLoginId();
  }
  if (userId != null) {
      parms = "?UID=" + userId;
  }
  else {
      parms = "";
  }
%>
<table border="0" cellpadding="0" cellspacing="0" style="width:100%;">
	<tr>
		<td style="width:766px; height:89px;">
		   <table border="0" cellpadding="0" cellspacing="0" style="width:766px; margin-top:23px;">
			 <tr>
				<td width="35%">
				   <table border="0" cellpadding="0" cellspacing="0">
					  <tr>
						  <td class="end">RMT2 Business Systems Corp. &copy; 2008</a></td>
					  </tr>
					  <tr>
						  <td class="end"><a href="<%=APP_ROOT%>/privacy.jsp">Privacy Policy</a> | <a href="<%=APP_ROOT%>/terms.jsp">Terms of Usage</a></td>
					  </tr>
				   </table>
				</td>
				<auth:RolesExist roles="authadmin, conadmin, acctadmin, ProjAdm, rmt2guest, rmt2client">
				<td width="65%">
				   <table border="0" align="right" cellpadding="0" cellspacing="0">
					  <tr>
					   <td><strong>Applications</strong>&nbsp;&nbsp;</td>
						 <td class="end1">
						     <auth:RolesExist roles="authadmin">
						        <a href="/authentication<%=parms%>" target="_blank">Security</a> 
						     </auth:RolesExist>
								 <auth:RolesExist roles="conadmin">
								    | <a href="/contacts<%=parms%>" target="_blank">Contacts</a>
								 </auth:RolesExist>
								 <auth:RolesExist roles="acctadmin">
								    | <a href="/accounting<%=parms%>" target="_blank">Accounting</a>  
								 </auth:RolesExist>
								 <auth:RolesExist roles="ProjAdm">
								    | <a href="/projecttracker<%=parms%>" target="_blank">Project Tracker</a>
								 </auth:RolesExist>
								 <auth:RolesExist roles="rmt2admin, rmt2guest">
								    | <a href="<%=APP_ROOT%>/forms/company/resume/resume_royterrell.jsp">My Resume</a>
								 </auth:RolesExist>
   								 <auth:RolesExist roles="rmt2admin, rmt2client">
								    | <a href="<%=APP_ROOT%>/client_testing.jsp">Client Testing Area</a>
								 </auth:RolesExist>
						 </td>
					  </tr>
				   </table>
				</td>
				</auth:RolesExist>
			 </tr>
		   </table>
		</td>
		<td style="width:50%;"><img alt=""  src="/images/spacer.gif" width="1" height="1"></td>
	</tr>
</table>