<table border="0" cellpadding="0" cellspacing="0" style="margin-top:31px;">
	 <tr style="margin: 10px 13px 0 0;">
 	  	 <td colspan=2>
		   <auth:UserLogInCheck>
		      Welcome back:&nbsp;
		      <%
		      RMT2SessionBean ses = (RMT2SessionBean) session.getAttribute("SESSION_BEAN");
		      %>
		   		<bean:InputControl value="<%= ses.getFirstName()%>"/>
				  &nbsp;
			    <bean:InputControl value="<%= ses.getLastName()%>"/>
		   </auth:UserLogInCheck>
		   &nbsp;
		 </td>
	 </tr>								
</table>