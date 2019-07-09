 <table border="0" cellpadding="0" cellspacing="0" style="margin:31px 0 0 25px; width:712px;">
	<tr>
		<td class="ins" style="line-height:13px;">
			<br>
			<font color="black" size="4" face="Verdana, Arial, Helvetica, sans-serif" style="font-style:italic">
			   <strong>Sorry for the inconvenience, but an error occurred.</strong>
			</font>
			<br><br>
			
			<!-- Display any messgaes -->
			<table class="msgBox" width="100%">
				<tr>
				  <td  valign="top" width="4%">
					<img src="<%=APP_ROOT%>/images/error.gif">
				  </td>
				  <td colspan="3">
 					  <font color="red" size="3">
						 <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
					  </font>
				  </td>
				</tr>
			</table>
			<br>
			<br style="line-height:9px;">
		</td>
	</tr>
</table>
