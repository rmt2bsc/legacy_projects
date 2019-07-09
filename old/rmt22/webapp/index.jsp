<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.api.security.authentication.RMT2SessionBean" %>

<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<%
  // Prevent JSP from caching.
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta name="description" content="RMT2 Business Systems Corp provides custom software development, web application development, and software engineering services."/>
	<meta name="keywords" content="software development, software developers, custom software development, software designers, website design, web developers, software development services, systems integration, database services, database developement, software test systems."/>
	<title>RMT2 Business Systems Corp. The Software Development Specialist</title>
	<link href="<%=APP_ROOT%>/css/style.css" rel="stylesheet" type="text/css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>  
	<script src="<%=APP_ROOT%>/js/index_menu.js"></script>
	<%@include file="forms/common/page_init_script.jsp"%>
</head>
<body  id="1" onLoad="init()">
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
								<td style="width:240px;">
								    <table border="0" cellpadding="0" cellspacing="0">
									  <auth:UserLogOffCheck>
                                        <tr>
                                            <td style="background:url(<%=APP_ROOT%>/images/bgr_1.jpg) top no-repeat; width:240px; height:102px;" class="form1">
                                                  <table border="0" cellpadding="0" cellspacing="0" style="width:210px; margin:10px 0 0 26px;">
                                                    <tr>
                                                       <td colspan="2"><img alt="" src="<%=APP_ROOT%>/images/login_title.jpg"></td>
                                                    </tr>
                                                    <tr>
                                                        <td >				
                                                            <%@include file="forms/common/login.jsp"%>
                                                        </td>
                                                    </tr>
                                                  </table>
                                            </td>
                                        </tr>
									  </auth:UserLogOffCheck>								
									<tr>
										<td style="height:319px;"><table border="0" cellpadding="0" cellspacing="0" style="margin:5px 0 0 27px; width:193px;">
											<tr><td><img alt=""  src="<%=APP_ROOT%>/images/title_other_feat.gif" style="margin-bottom:5px;"></td></tr>
											<tr>
											  <td>
												    <img alt=""  src="<%=APP_ROOT%>/images/ch_go.gif" style="margin-right:8px;"><strong><a href="forms/company/company.jsp">About Us</a></strong><span class="smalltxt"> - <font class="blocktextsmall">RMT2 Business Systems Corp. is a privately held software development company established in August 1997...</font></span>
											</td>
											</tr>
											<tr><td><img alt=""  src="<%=APP_ROOT%>/images/dot1.jpg" style="margin:10px 0 10px 0;"></td></tr>
											<tr><td>
												    <img alt="" src="<%=APP_ROOT%>/images/ch_go.gif" style="margin-right:8px;"><strong><a href="forms/quote/rate_overview.jsp">Project Pricing Facts</a></strong><span class="smalltxt"> - <font class="blocktextsmall">We offer competitive & reasonable rates, but we know they aren't for everyone because some insist on getting less by paying less...</font></span>
											</td></tr>
											<tr><td><img alt=""  src="<%=APP_ROOT%>/images/dot1.jpg" style="margin:10px 0 10px 0;"></td></tr>
											<tr><td>
												    <img alt=""  src="<%=APP_ROOT%>/images/ch_go.gif" style="margin-right:8px;"><strong><a href="forms/company/companyexperience.jsp">Industry Experience</a></strong><span class="smalltxt"> - <font class="blocktextsmall">RMT2 Business Systems Corp has experience in the development and maintenance of software in all project stages - from creation of large distributed aggregates to simple web site design and development.</font></span>
											</td></tr>											
										</table></td>
									</tr>
								</table></td>
								<td style="width:1px; height:100%; background:#999999"><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="1"></td>
								<td style="width:525px;"><table border="0" cellpadding="0" cellspacing="0" style="width:525px;">
									<tr>
										<td style="height:210px; width:525px;"><table border="0" cellpadding="0" cellspacing="0" style="margin:31px 0 0 25px; width:484px;">
											<tr><td><img alt=""  src="<%=APP_ROOT%>/images/title_welcome.gif" style="margin-bottom:24px;"></td></tr>
											<tr><td><img alt=""  src="<%=APP_ROOT%>/images/1f1.jpg" style="margin-right:17px; float:left;">
											<br style="line-height:5px;">
											<font class="blocktext">Welcome to RMT2 Business Systems Corp., your complete resource for custom software solutions.  Our competence and experience ensure that we deliver <strong>excellent services</strong> and products to our customers. At every stage of the development process, from conceptual design to product release, the <strong>highest quality standards</strong> are maintained.</font> <br><br style="line-height:5px;">
											<strong style="margin-left:325px;"><a href="forms/company/company.jsp>[READ MORE]</a></strong><br clear="all"></td>
											</tr>
											<tr><td><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="10"></td></tr>
										</table></td>
									</tr>
									<tr><td style="width:100%; height:1px; background: #9E9E9E"><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="1"></td></tr>
									<tr>
										<td style="width:525px;"><table border="0" cellpadding="0" cellspacing="0" style="margin:28px 0 0 25px;">
											<tr>
												<td style="width:525px; height:162px;"><table border="0" cellpadding="0" cellspacing="0" style="height:100%;">
													<tr>
														<td style="width:237px;"><table border="0" cellpadding="0" cellspacing="0" style="width:237px;">
															<tr><td><img alt=""  src="<%=APP_ROOT%>/images/title_spec_serv.gif" style="margin-bottom:20px;"></td></tr>
															<tr><td class="r1">
																<ul>
																	<li><a href="forms/services/avail_serv_software_dev.jsp">Software Design and Development</a></li>
																	<li><a href="forms/services/avail_serv_software_test.jsp">Software Testing</a></li>
																	<li><a href="forms/services/avail_serv_software_impl.jsp">Implementation and Integration</a></li>
																	<li><a href="forms/services/avail_serv_software_install.jsp">Installation and Training</a></li>
																	<li><a href="forms/services/avail_serv_software_support.jsp">Technical Support</a></li>
																</ul>
															</td></tr>
														</table></td>
														<td style="width:1px; height:100%; background: #2B2B2B"><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="1"></td>
														<td><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="25" height="1"></td>
														<td style="width:215px;"><table border="0" cellpadding="0" cellspacing="0" style="width:211px;">
															<tr><td><img alt=""  src="<%=APP_ROOT%>/images/title_corp_solution.gif" style="margin-bottom:20px;"></td></tr>
															<tr><td><img alt=""  src="<%=APP_ROOT%>/images/1f3.jpg" style="margin-right:14px; float:left;">
																<a href="forms/solutions/solutions_why_customize.jsp">Why Customize?</a><br><br style="line-height:5px;"><font class="blocktext">We specialize in custom desktop and web-based business software applications...</font><br clear="all"></td>
															</tr>
															<tr><td><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="21"></td></tr>
															<tr><td><img alt=""  src="<%=APP_ROOT%>/images/1f2.jpg" style="margin-right:14px; float:left;">
																<a href="forms/solutions/solutions_technical.jsp">Software Solutions</a><br>
																<br style="line-height:5px;"><font class="blocktext">We have extensive experience in system analysis, custom software design and development, training and support on a variety of platforms and languages...</font><br clear="all"></td> 
															</tr>
															<tr><td><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="10"></td></tr>
														</table></td>  
													</tr>
												</table></td>
											</tr>
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
