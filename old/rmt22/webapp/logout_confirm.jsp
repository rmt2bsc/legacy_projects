<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.util.RMT2Utility" %>
<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>Member Logoff Confirmation</title>
	<link href="<%=APP_ROOT%>/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" align="center" width="50%" style="height:50%;">
	<tr>
		<td style=" width:100%; height:428px;  background: #353535 url(<%=APP_ROOT%>/images/work_bgr.jpg) top repeat-x;"><table border="0" cellpadding="0" cellspacing="0" style="width:100%;">
			<tr>
				<td style="width:50%;"><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="1"></td>
				<td style="width:766px; height:428px"><table border="0" cellpadding="0" cellspacing="0">
					<tr><td><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="7"></td></tr>
					<tr>
						<td style="height:421px; width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="height:100%; width:766px;">
							<tr>
								<!--left-->
								<td style="width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="width:766px;">
									<tr>
										<td style="height:226px; width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="margin:31px 0 0 25px; width:712px;">
											<tr><td><img alt=""  src="<%=APP_ROOT%>/images/logotip.jpg" style="margin-bottom:24px;"></td></tr>
											<tr><td class="ins" style="line-height:13px;">
												<br><br><br>
												<!-- Display any messgaes -->
												<table class="table_main" width="50%">
													<tr class="table_header">
													   <td>
														  <font style="font-weight:bold">Member Logoff Confirmation</font>
													   </td>
													</tr>
													<tr>
													   <td class="blocktext">
													       <font class="strongbold">You have successfully logged off.</font>
													   </td>
													</tr>
													<tr>
													   <td class="blocktext">
													       For your security we recommend you close your browser. You may <a href="<%=APP_ROOT%>/login_form.jsp">log on</a> again.
													   </td>
													</tr>
												</table>													
												<br><br style="line-height:9px;">
											</td></tr>
											
										</table></td>
									</tr>
									
								</table></td>
							</tr>
						</table></td>
					</tr>
				</table></td>
				<td style="width:50%;"><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="1"></td>
			</tr>
		</table></td>
	</tr>
</table>
</body>
</html>
