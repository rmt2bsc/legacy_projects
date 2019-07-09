<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>Project Pricing Facts</title>
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
								<td style="width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="width:766px;">
									<tr>
										<td style="height:226px; width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="margin:31px 0 0 25px; width:712px;">
											<tr><td><img alt=""  src="../../images/title_rates.gif" style="margin-bottom:24px;"></td></tr>
											<tr><td class="ins" style="line-height:13px;">
		                                       <ul style="line-height:25px;">
											      <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
 													    <strong>Determination of project cost</strong>.&nbsp;&nbsp;The cost of project fulfillment is determined before the start of work (as the estimated number of work hours multiplied by the hourly rate). See in more detail about <a href="projectanalysis.jsp"><strong>Project Analysis and Cost Determination</strong></a>.
                                                    </span>
												  </li>
												  <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
													   <strong>Software development services</strong>.&nbsp;&nbsp;The hourly rate for a software development project depends on many factors such as project complexity, technologies that are necessary for project fulfillment, and project duration.&nbsp;&nbsp;For each project, the hourly rate is determined individually during preliminary negotiations with a Customer.&nbsp;&nbsp;
													   <auth:RolesExist roles="rmt2admin">
													   		See <a href="rates_software.jsp"><strong>Software Development Price List</strong></a>   
													   </auth:RolesExist>
													 </span>
												  </li>
												  <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
													    <strong>Web design services</strong>.&nbsp;&nbsp;The hourly rate for a web site project depends on many factors such as the complexity of the web site requirements, the number pages comprising the site, original artwork design vs. using artwork provided by the client, the involvement of a shopping cart, database design and development, and etc.&nbsp;&nbsp;For each project the hourly rate is determined individually during preliminary negotiations with a Customer.&nbsp;&nbsp;
														<auth:RolesExist roles="rmt2admin">
															See <a href="rates_website.jsp"><strong>Web Design Price List</strong></a>    
														</auth:RolesExist>
													 </span>
												  </li>
												  <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
													    <strong>Testing and deployment</strong>.&nbsp;&nbsp;All project hourly rates include thorough testing of a product by our testing specialists.  Likewise, the deployment of the product is is included in the project price.
												     </span>
												  </li>
												  <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
													    <strong>Detailed Information</strong>.&nbsp;&nbsp;We are ready to supply you more detailed information on our prices, but in order to do it we need to learn about your specific project requirements. If you are interested and wish to receive more detailed information, please fill out our <a href="<%=APP_ROOT%>/sessionBypassProcessor/Contact.Quote?clientAction=new"><strong>Free Quote Form</strong></a>.
													  </span>
												  </li>
												  <auth:RolesExist roles="rmt2admin">
														  <li class="strongbold">
														      <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
															     To learn about our pricing terms and conditions, please click <a href="payterms.jsp"><strong>here</strong></a>.
															  </span>
														  </li>
												  </auth:RolesExist>
											   </ul>
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
