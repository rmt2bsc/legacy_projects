<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>
<%@ page errorPage="../../jsperror.jsp"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>Software Development Rate</title>
	<link href="../../css/style.css" rel="stylesheet" type="text/css">
	<script src="../../js/menu.js"></script>
	<%@include file="../common/page_init_script.jsp"%>
</head>
<body  id="2" onLoad="init()">

<table border="0" cellpadding="0" cellspacing="0" align="center" width="50%" style="height:100%;">
	<tr>
		<td style="height:243px; width:100%; background:url(../../images/rez_top.jpg) top repeat-x;">
		   <%@include file="../common/header.jsp"%>
	    </td>
	</tr>
<auth:RolesNotExist roles="rmt2admin">
	<gen:PageException msg="User must be logged on and granted the permission to access this page: Software Rates"/>
</auth:RolesNotExist>	
	<tr>
		<td style=" width:100%; height:428px;  background: #353535 url(../../images/work_bgr.jpg) top repeat-x;"><table border="0" cellpadding="0" cellspacing="0" style="width:100%;">
			<tr>
				<td style="width:50%;"><img alt=""  src="../../images/spacer.gif" width="1" height="1"></td>
				<td style="width:766px; height:428px"><table border="0" cellpadding="0" cellspacing="0">
					<tr><td><img alt=""  src="../../images/spacer.gif" width="1" height="7"></td></tr>
					<tr>
						<td style="height:421px; width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="height:100%; width:766px;">
							<tr>
								<!--left-->
								<td style="width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="width:766px;">
									<tr>
										<td style="height:226px; width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="margin:31px 0 0 25px; width:712px;">
											<tr><td><img alt=""  src="../../images/title_rates_software.gif" style="margin-bottom:24px;"></td></tr>
											<tr><td class="ins" style="line-height:13px;">
												<table class="table_main" width="50%" cellpadding="3" cellspacing="0" border="1" bordercolor="#FFFFFF">
													<tr class="table_header">
													   <td width="60%"><font style="font-weight:bold">Service</font></td>
													   <td width="40%"><font style="font-weight:bold">Rate/Cost</font></td>
													</tr>
													<tr>
													   <td><strong>Consultation</strong></td>
													   <td><strong>$0</strong></td>
												    </tr>
												    <tr>
													   <td><strong>Custom desktop applicaltions</strong></td>
													   <td><strong>negotiable</strong></td>
												    </tr>
													<tr>
													   <td><strong>Database Design</strong></td>
													   <td><strong>$70/hour*</strong></td>
												    </tr>
													<tr>
													   <td><strong>Database Deveolpment</strong></td>
													   <td><strong>negotiable</strong></td>
												    </tr>
													<tr>
													   <td><strong>System Analysis and Design</strong></td>
													   <td><strong>$50/hour*</strong></td>
												    </tr>
													<tr>
													   <td><strong>Software Rewrites</strong></td>
													   <td><strong>negotiable</strong></td>
												    </tr>
													<tr>
													   <td><strong>Software Support</strong></td>
													   <td><strong>$50/hour*</strong></td>
												    </tr>
													<tr>
													   <td><strong>Technical Support</strong></td>
													   <td><strong>negotiable</strong></td>
												    </tr>
													<tr>
													   <td><strong>Consulting</strong></td>
													   <td><strong>negotiable</strong></td>
												    </tr>
													<tr>
													   <td><strong>Testing and installation of software</strong></td>
													   <td><strong>Included with project cost</strong></td>
												    </tr>													
													<tr>
													   <td colspan="2"><span style="color:#FFFFFF;"><font class="blocktext">* Minimum One Hour</font></span></td>
												    </tr>													
												</table>													
											</td></tr>
											
										</table></td>
									</tr>
									
								</table></td>
							</tr>
						</table></td>
					</tr>
				</table></td>
				<td style="width:50%;"><img alt=""  src="../../images/spacer.gif" width="1" height="1"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td style=" width:100%; height:100%;  background: #1E1E1E url(../../images/end_bgr.jpg) top repeat-x;">
		   <%@include file="../common/footer.jsp"%>	
	   </td>
	</tr>
</table>
</body>
</html>
