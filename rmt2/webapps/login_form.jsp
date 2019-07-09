<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>Login Form</title>
	<link href="<%=APP_ROOT%>/css/style.css" rel="stylesheet" type="text/css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>  
	<script src="<%=APP_ROOT%>/js/index_menu.js"></script>
	<%@include file="forms/common/page_init_script.jsp"%>
</head>
<body id="1" onLoad="init()">
<table border="0" cellpadding="0" cellspacing="0" align="center" width="50%" style="height:100%;">
	<tr>
		<td style="height:243px; width:100%; background:url(images/rez_top.jpg) top repeat-x;">
		   <%@include file="forms/common/header.jsp"%>
	    </td>
	</tr>
	<tr>
		<td style=" width:100%; height:428px;  background: #353535 url(images/work_bgr.jpg) top repeat-x;"><table border="0" cellpadding="0" cellspacing="0" style="width:100%;">
			<tr>
				<td style="width:50%;"><img alt=""  src="images/spacer.gif" width="1" height="1"></td>
				<td style="width:766px; height:428px">
				  <table border="0" cellpadding="0" cellspacing="0">
					<tr>
					   <td><img alt=""  src="images/spacer.gif" width="1" height="7"></td>
					</tr>
					<tr>  
						<td style="height:421px; width:766px;">
						  <table border="0" cellpadding="0" cellspacing="0" style="height:100%; width:766px;">
							<tr>
								<td style="width:766px;">
								   <table border="0" cellpadding="0" cellspacing="0" style="width:766px;">
									<tr>
										<td style="height:226px; width:766px;">
										   <table border="0" cellpadding="0" cellspacing="0" style="margin:5px 0 0 25px; width:712px;">
											  <tr>
											     <td><img alt=""  src="images/title_login.gif" style="margin-bottom:24px;"></td>
											  </tr>
											  <tr>
											    <td >
												  <table border="0" cellpadding="0" cellspacing="0" style="width:240px; margin:10px 0 0 0;">
													 <tr>
														<td style="background:url(images/bgr_1.jpg) top no-repeat; width:240px; height:102px;" class="form1">				
															<br style="line-height:20px">
															<div align="center"><%@include file="forms/common/login.jsp"%></div>
														</td>
													 </tr>
												  </table>
											   </td>
											</tr>
										</table>
									   </td>
									</tr>
								</table></td>
							</tr>
						</table></td>
					</tr>
				</table></td>
				<td style="width:50%;"><img alt=""  src="images/spacer.gif" width="1" height="1"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td style=" width:100%; height:100%;  background: #1E1E1E url(images/end_bgr.jpg) top repeat-x;">
		   <%@include file="forms/common/footer.jsp"%>	
	   </td>
	</tr>
</table>
</body>
</html>
