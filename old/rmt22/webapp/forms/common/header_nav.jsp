<table border="0" cellpadding="0" cellspacing="0" style="margin-top:11px;">
	 <tr>
		<td style="width:80px;"><a href="<%=APP_ROOT%>/index.jsp"><img alt=""  src="<%=APP_ROOT%>/images/p5.jpg" style="margin-right:10px;" border="0"><font color="#D4D174">home</font></a></td>
		<td style="width:85px;"><a href="<%=APP_ROOT%>/sitemap.jsp"><img alt=""  src="<%=APP_ROOT%>/images/p4.jpg" style="margin-right:10px;" border="0"><font color="#D4D174">sitemap</font></a></td> 
     <auth:UserLogInCheck>
			   <td style="width:85px;"><a href="<%=APP_ROOT%>/unsecureRequestProcessor/Security.Authentication?clientAction=logoff"><img alt=""  src="<%=APP_ROOT%>/images/p7.jpg" style="margin-right:10px;" border="0"><font color="#D4D174">logout</font></a></td>
		 </auth:UserLogInCheck>
     <auth:UserLogOffCheck>
	       <td style="width:85px;"><a href="<%=APP_ROOT%>/login_form.jsp"><img alt=""  src="<%=APP_ROOT%>/images/p6.jpg" style="margin-right:10px;" border="0"><font color="#D4D174">login</font></a></td>
		 </auth:UserLogOffCheck>
	 </tr>
</table>