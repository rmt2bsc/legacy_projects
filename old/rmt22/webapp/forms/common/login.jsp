	<script>
	   /************************************************************
	    *  Submit the form.  Only submit form if the formSubmitted 
	    *  global variable is set to false.
		  ************************************************************/
	   function submitForm() {
		    if (!formSubmitted) {
		    	handleAction("_top", document.LoginForm, "login")
		    	formSubmitted = true;
		    }
		    
	   }
</script>

<form name="LoginForm" action="<%=APP_ROOT%>/unsecureRequestProcessor/Security.Authentication" method="post">
	<table width="200px" border="0" cellpadding="0" cellspacing="0">
	  <tr>
		<td style="width:60px; vertical-align:bottom">User Id</td>
		<td style="width:140px">  
		   <%
		      String uid = (String) request.getAttribute(AuthenticationConst.AUTH_PROP_USERID);
		      uid = uid == null ? "" : uid;
		   %>
		   <input name="<%=AuthenticationConst.AUTH_PROP_USERID %>" type="text" value="<%=uid %>">
		</td>
	  </tr>
	  <tr>
		<td style="width:60px; vertical-align:bottom">Password</td>
		<td>
		   <input name="<%=AuthenticationConst.AUTH_PROP_PASSWORD %>" type="password">
		</td>
	  </tr>
	  <tr>
		<td>&nbsp;</td>
		<td style="text-align:right"><a href="javascript:submitForm()"><img alt=""  src="<%=APP_ROOT%>/images/login.jpg" border="0" style="margin-top:3px;"></a></td>
	  </tr>		  
      <tr>
         <td colspan="2">  
						<font color="red" style="font-weight: normal">
						   <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
						</font>
						<!-- 
			      <input type="submit" style="height:0px; width:0px; border:0px;">
			       -->
		  </td>
      </tr>
	</table>
	<input name="clientAction" type="hidden">
	<!-- Added invisible submit button to prevent "ding" sound when the form is submitted 
	     and when some other input control has focus 
	-->
	
</form>