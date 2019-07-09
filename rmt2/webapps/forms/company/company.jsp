<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>Our Company</title>
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
								<td style="width:515px;"><table border="0" cellpadding="0" cellspacing="0" style="width:515px;">
									<tr>
										<td style="height:210px; width:525px;"><table border="0" cellpadding="0" cellspacing="0" style="margin:11px 0 0 25px; width:464px;">
											<tr><td><img alt=""  src="../../images/title_ourworkteam.gif" style="margin-bottom:7px;"></td></tr>
											<tr><td><img alt=""  src="../../images/2f1.jpg" style="margin-right:17px; float:left;">
											<br style="line-height:5px;">
											<font class="blocktext">At RMT2 Business Systems Corp we are proud of our high quality standards. These standards allow us to provide our customers with <strong>reliable</strong> and error-free software applications, regardless of complexity. Our top-notch developers use the latest software methodologies and technologies. This means that they can concentrate on our clients' <strong>business goals</strong> and keep them involved in every stage through the entire project. Our meticulous approach has helped us build our <strong>excellent track record</strong> with no failed or aborted projects.</font> 
                                            <br clear="all"></td>
											</tr>
											<tr><td><img alt=""  src="../../images/spacer.gif" width="1" height="10"></td></tr>
										</table></td>
									</tr>
									<tr><td style="width:100%; height:1px; background: #9E9E9E"><img alt=""  src="../../images/spacer.gif" width="1" height="1"></td></tr>
									<tr>
										<td style="width:525px;"><table border="0" cellpadding="0" cellspacing="0" style="margin:28px 0 0 25px;">
											<tr>
												<td style="width:525px; height:100px;"><table border="0" cellpadding="0" cellspacing="0" style="height:100%;">
													<tr><td colspan="10"><img alt=""  src="../../images/title_our_mission.gif" style="margin-bottom:17px;"><img alt="" src="../../images/spacer.gif" width="107" height="1"><img alt=""  src="../../images/title_our_goals.gif" style="margin-bottom:17px;"></td></tr>
													<tr>
														<td style="width:233px;"><table border="0" cellpadding="0" cellspacing="0" style="width:213px;">
															<tr><td><img alt=""  src="../../images/2f2.jpg" style="margin-right:14px; float:left;">
																	<font class="blocktext">We are committed to personal and professional integrity, honesty, and open communications with our clients.  We seek long-term relationships with other companies that have similar professional goals and expectations...</font><br><br style="line-height:5px;">
																	<strong style="margin-left:95px;"><a href="companymission.jsp">[READ MORE]</a></strong><br clear="all"></td>
																</tr>
														</table></td>
														<td style="width:1px; height:100%; background:url(../../images/b_bgr.jpg) top repeat-y"><img alt=""  src="../../images/spacer.gif" width="1" height="1"></td>
														<td><img alt=""  src="../../images/spacer.gif" width="25" height="1"></td>
														<td style="width:213px;"><table border="0" cellpadding="0" cellspacing="0" style="width:213px;">
															<tr><td><img alt=""  src="../../images/2f3.jpg" style="margin-right:14px; float:left;">
																	<font class="blocktext">Provide expertly-designed custom enterprise solutions that have real value for our clients and provide the best possible client service and support...</font><br><br><br style="line-height:5px;">
																	<strong style="margin-left:100px;"><a href="companygoals.jsp">[READ MORE]</a></strong><br clear="all"></td>
																</tr>
														</table></td>
													</tr>
												</table></td>
											</tr>
										</table></td>
									</tr>
								</table></td>
								<td style="width:1px; height:100%; background:#999999"><img alt=""  src="../../images/spacer.gif" width="1" height="1"></td>
								<!--right-->
								<td style="width:250px;"><table border="0" cellpadding="0" cellspacing="0" style="width:250px;">
									<tr>
										<td style="height:319px;"><table border="0" cellpadding="0" cellspacing="0" style="margin:11px 5px 0 27px; width:195px;">
											<tr><td><img alt=""  src="../../images/title_aboutus.gif" style="margin-bottom:14px;"></td></tr>
											<tr><td><font class="blocktext">RMT2 Business Systems Corp. is a privately held software development company established in August 1997.  Our head office is in Flower Mound, Texas.  Our competence and</font></td></tr>
											<tr><td><img alt=""  src="../../images/2f4.jpg" style="margin:15px 0 15px 0;"></td></tr>
											<tr>
											  <td><font class="blocktext">experience ensure that we deliver <strong>excellent services</strong> and products to our customers.  At every stage of the system development life cycle process, the <strong>highest quality standards</strong> are maintained.</font><br></td>
											</tr>
											<tr><td><img alt=""  src="../../images/spacer.gif" width="1" height="5"></td></tr>
											<tr><td><strong style="margin-left:77px;">&nbsp;</a></strong></td></tr>
											<tr>
											  <td><font class="blocktext">We provide custom software services including specialized application and database development, data-driven web sites, web site design, and graphics design.  We deliver economical, leading-edge solutions.</font><br><br></td>
											</tr>
											<tr>
											  <td><font class="blocktext">Click <a href="../quote/rate_overview.jsp">here</a> to view our pricing facts.</font><br><br></td>
											</tr>
											<tr>
											  <td><font class="blocktext">View our industry <a href="../company/companyexperience.jsp">experience</a>.</font><br><br></td>
											</tr>																						
											<tr><td><img alt=""  src="../../images/spacer.gif" width="1" height="5"></td></tr>
											<tr><td><strong style="margin-left:77px;">&nbsp;</a></strong></td></tr>
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
		<td style=" width:100%; height:100%;  background: #1E1E1E url(images/end_bgr.jpg) top repeat-x;">
		   <%@include file="../common/footer.jsp"%>	
	   </td>
	</tr>
</table>
</body>
</html>
