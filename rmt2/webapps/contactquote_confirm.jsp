<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>Free Quote Confirmation</title>
	<link href="<%=APP_ROOT%>/css/style.css" rel="stylesheet" type="text/css">
	<script src="js/index_menu.js"></script>
	<%@include file="forms/common/page_init_script.jsp"%>
</head>
<body  id="6" onLoad="init()">
<table border="0" cellpadding="0" cellspacing="0" align="center" width="50%" style="height:100%;">
	<tr>
		<td style="height:243px; width:100%; background:url(<%=APP_ROOT%>/images/rez_top.jpg) top repeat-x;">
		   <%@include file="forms/common/header.jsp"%>
	    </td>
	</tr>
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
                                          <tr>
                                            <td><img alt=""  src="<%=APP_ROOT%>/images/title_quoterequest_confirm.gif" style="margin-bottom:9px;"></td>
                                          </tr>
										  <tr><td><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="27"></td></tr>		
										  <tr>
										     <td>
											    <table width="53%" align="center">
												   <tr>
												      <td width="35%">
														<table class="table_main" width="100%">
															<tr class="table_header">
															   <td>
																  <font style="font-weight:bold">Submission Received</font>
															   </td>
															</tr>
															<tr>
															   <td>
															       <br>
                                                                   <span style="color:#FFFFFF;" class="strongbold"><font class="blocktext">Thank you for your interest in RMT2 Business Systems Corp.</font></span>
															   </td>
															</tr>
															<tr><td><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="27"></td></tr>		
														</table>													  
													  </td>
												   </tr>
												</table>
											 </td>
										  </tr>
										  <tr><td><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="7"></td></tr>																
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
	<tr>
		<td style=" width:100%; height:100%;  background: #1E1E1E url(<%=APP_ROOT%>/images/end_bgr.jpg) top repeat-x;">
		   <%@include file="forms/common/footer.jsp"%>	
	   </td>
	</tr>
</table>
</body>
</html>
