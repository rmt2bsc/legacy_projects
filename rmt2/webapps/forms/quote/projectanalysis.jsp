<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>Project Analysis and Cost Determination</title>
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
											<tr><td><img alt=""  src="../../images/title_project_analysis.gif" style="margin-bottom:24px;"></td>
											</tr>
											<tr><td class="ins" style="line-height:13px;">
											   <p>The determination of the cost and task scheduling of a project goes as follows:</p>
		                                       <ul style="line-height:25px;">
											      <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
 													    <strong>Common</strong>.&nbsp;&nbsp;At the analysis stage of the project the customer requirements are gathered, reviewed, and all conditions for project execution are determined. 
                                                    </span>
												  </li>
  											      <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
 													    <strong>Project work group organization</strong>.&nbsp;&nbsp;Based on the customer requirements analysis, a group of technical specialists is organized to work on the project.&nbsp;&nbsp;The customer will receive complete data on each of the specialists assigned to the workgroup.&nbsp;&nbsp;The composition and size of the workgroup are determined based on the requirements of the project.
                                                    </span>
												  </li>
  											      <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
 													    <strong>Calculation of the hourly rate for a project</strong>.&nbsp;&nbsp;Hourly rates for project fulfillment are approved by the customer based on the analysis of the customer&acute; s project requirements.
                                                    </span>
												  </li>
												  <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
 													    <strong>Technical analysis of the project</strong>.&nbsp;&nbsp;Based on the analysis of customer&acute;s requirements (specifications), our work group will select the optimal technologies for project execution, and produce a general (aggregated) description of the project architecture for the custtomer. 
                                                    </span>
												  </li>
												  <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
 													    <strong>Cost determination for project execution</strong>.&nbsp;&nbsp;RMT2 Business Systems Corp&acute;s work group presents to the customer a detailed estimate depicting the cost of project execution.&nbsp;&nbsp;The estimate specifies how many work hours (workdays) it will take to execute any and all tasks pertaining to the project. 
                                                    </span>
												  </li>
												  <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
 													    <strong>Creation of the project schedule</strong>.&nbsp;&nbsp;When the customer approves the project execution cost, the project schedule will be determined.&nbsp;&nbsp;At this stage the following parameters are determined:
														<ol>
														   <li>The date to commence work on the project</li>
														   <li>The date to produce the final version of the project</li>
														   <li>The production dates and composition of intermediate versions, amount of interim payments (if applicable)</li>
														</ol>
                                                    </span>
												  </li>
												  <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
 													    <strong>Start of work to fulfill the order</strong>.&nbsp;&nbsp;RMT2 Business Systems Corp will begin working on all applicable tasks required to execute the project only when the customer confirms the agreement pertaining to all produced materials (project technical analysis, project execution cost, and project time schedule). 
                                                    </span>
												  </li>	
												  <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
 													    <strong>Modification of the project specifications</strong>.&nbsp;&nbsp;After work on the project has begun, modification to project specifications is prohibited, since it may result in the waste of work that has already been completed, alteration of the project cost, and disruption of the time schedule. 
                                                    </span>
												  </li>												  
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
