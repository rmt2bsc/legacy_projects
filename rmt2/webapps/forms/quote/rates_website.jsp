<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<%@ page errorPage="../../jsperror.jsp"%>
<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>Website Design Rates</title>
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
		<gen:PageException msg="User must be logged on and granted the permission to access this page: Webs Design Rates"/>
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
											<tr><td><img alt=""  src="../../images/title_rates_web.gif" style="margin-bottom:24px;"></td></tr>
											<tr>
											  <td class="ins" width="50%" style="line-height:13px;">
												<table class="table_main" width="95%" cellpadding="3" cellspacing="0" border="1" bordercolor="#FFFFFF">
												    <caption><font color="#FFFFFF">Typical Hourly Rates</font></caption>
													<tr class="table_header">
													   <td width="60%"><font style="font-weight:bold">Service</font></td>
													   <td width="40%"><font style="font-weight:bold">Rate</font></td>
													</tr>
													<tr>
													   <td><strong>Consultation</strong></td>
													   <td><strong>$0</strong></td>
												    </tr>
												    <tr>
													   <td><strong>Static Web Site</strong></td>
													   <td><strong>$65/hour*</strong></td>
												    </tr>
													<tr>
													   <td><strong>Dynamic Web Site</strong></td>
													   <td><strong>$65/hour*</strong></td>
												    </tr>
													<tr>
													   <td><strong>Custom logo design</strong></td>
													   <td><strong>$80/hour*</strong></td>
												    </tr>
													<tr>
													   <td><strong>Image editing</strong></td>
													   <td><strong>$65/hour*</strong></td>
												    </tr>
													<tr>
													   <td><strong>Custom art work, drawing, design, animations</strong></td>
													   <td><strong>$80/hour</strong></td>
												    </tr>
													<tr>
													   <td><strong>Text updates</strong></td>
													   <td><strong>$30/hour*</strong></td>
												    </tr>
													<tr>
													   <td><strong>Custom programming, scripting, database interface and design</strong></td>
													   <td><strong>negotiable</strong></td>
												    </tr>
													<tr>
													   <td colspan="2"><span style="color:#FFFFFF;"><font class="blocktext">* Minimum One Hour</font></span></td>
												    </tr>													
												</table>													
											</td>
											
											<td class="ins" width="50%" style="line-height:13px;">
												<table class="table_main" width="95%" cellpadding="3" cellspacing="0" border="1" bordercolor="#FFFFFF">
												    <caption><font color="#FFFFFF">Typical Project Costs</font></caption>
													<tr class="table_header">
													   <td width="60%"><font style="font-weight:bold">Service</font></td>
													   <td width="40%">Cost</td>
													</tr>
													<tr>
													   <td><strong>Touch-up existing Web Site</strong></td>
													   <td><strong>$50 ~ $250</strong></td>
												    </tr>
												    <tr>
													   <td><strong>Web site with 4 pages, logo/colors provided</strong></td>
													   <td><strong>$650 ~ $950</strong></td>
												    </tr>
													<tr>
													   <td><strong>Web site with 4 pages, logo/colors created</strong></td>
													   <td><strong>$750 ~ $1,550</strong></td>
												    </tr>
													<tr>
													   <td><strong>Web site with 10 pages and original artwork</strong></td>
													   <td><strong>$1350 ~ $2000</strong></td>
												    </tr>
													<tr>
													   <td><strong>Medium-sized Web site with database</strong></td>
													   <td><strong>$1,700 ~ $3,000</strong></td>
												    </tr>
													<tr>
													   <td><strong>Web site with shopping cart</strong></td>
													   <td><strong>$3,000 ~ $5,000</strong></td>
												    </tr>
													<tr>
													   <td><strong>Web site with shopping cart and product image catalog</strong></td>
													   <td><strong>$4,000 ~ $10,000</strong></td>
												    </tr>
													<tr>
													   <td colspan="2">Dollar amounts listed are not official offerings for similary described projects</td>
												    </tr>													
												</table>													
											</td>											
										  </tr>
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
