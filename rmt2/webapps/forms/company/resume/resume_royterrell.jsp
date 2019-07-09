<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
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
	<title>Roy Terrell Resume</title>
	<link href="../../../css/style.css" rel="stylesheet" type="text/css">
	<script src="../../../js/index_menu.js"></script>
	<%@include file="../../../forms/common/page_init_script.jsp"%>
</head>
<body id="1" onLoad="init()">


<font class="blocktext">
</font><font class="blocktext">
</font>
<table border="0" cellpadding="0" cellspacing="0" align="center" width="50%" style="height:100%;">
	<tr>
		<td style="height:243px; width:100%; background:url(../../../images/rez_top.jpg) top repeat-x;">
		   <%@include file="../../../forms/common/header.jsp"%>
	    </td>
   </tr>
	<auth:RolesExist roles="authadmin, conadmin, acctadmin, ProjAdm, rmt2guest">
	<tr>
		<td style=" width:100%; height:428px;  background: #353535 url(../../../images/work_bgr.jpg) top repeat-x;"><table border="0" cellpadding="0" cellspacing="0" style="width:100%;">
			<tr>
				<td style="width:50%;"><img alt=""  src="../../../images/spacer.gif" width="1" height="1"></td>
				<td style="width:766px; height:428px"><table border="0" cellpadding="0" cellspacing="0">
					<tr><td><img alt=""  src="../../../images/spacer.gif" width="1" height="7"></td></tr>
					<tr>
						<td style="height:421px; width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="height:100%; width:766px;">
							<tr>
								<!--left-->
								<td style="width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="width:766px;">
									<tr>
										<td style="height:226px; width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="margin:5px 0 0 25px; width:712px;">
											<tr><td><img alt=""  src="../../../images/title_resume.gif" style="margin-bottom:24px;"></td></tr>
											<tr>
											   <td class="ins" style="line-height:13px;">
												<font class="blocktext">
												    <font face="Verdana" size="2" color="#D4D174">
                                                      <center><b>Roy M. Terrell, Jr<br>
														2300 Dana Dr<br>
														Flower Mound, Texas 75028<br>
														Phone:  (214) 498-3935</b><br>
														<a href="mailto:rmt2bsc2@verizon.net">rmt2bsc2@verizon.net</a></center><br><br>
													</font>
<h3><strong>Objective</strong></h3>
<p>
To obtain a software developer position that allows me to learn new technologies while utilizing my experience developing web-based 
and client/server based applications as well as my knowledge of Core Java, J2EE, HTML, CSS, JavaScript, PowerBuilder, and various 
Relational Database Management Systems (RDBMS).
</p>

<h3><strong>Profile Summary</strong></h3>
<p>
Software engineer with proven experience in architecting and developing both web and 
client-server applications utilizig various technologies as core Java, J2EE, Powerbuilder, 
Oracle, SQL Server, HTML, Javascript, UML and etc.&nbsp;&nbsp;  Recognized by managers, colleagues, 
and peers as a personable, dedicated performer who demonstrates innovation, communication, 
and teamwork to ensure quality, timely project completion.
</p>
														
<h3><strong>Education</strong></h3>
<ul>
	<li>
	<div>B.S., Computer Information Systems, DeVry University (Dallas).</div>
	</li>
</ul>

<h3><strong>Technical Summary</strong></h3>
<ul>
	<li>
	   <div>Core Java, J2EE, HTML, Javascript, CSS, C++, PowerBuilder and COBOL</div>
	</li>
	<li>
	   <div>SQL, SQL Server, Sybase, Transact-SQL, Oracle, PL/SQL, MySQL, and Sybase Adaptive Server Anywhere</div>
	</li>
	<li>
	   <div>Sybase PowerDesigner and UML</div>
	</li>
	<li>
	   <div>Web Services, Messaging, Open JMS, and MQ Series. </div>
	</li>	
</ul>

<!-- Work experiences -->
<h3><strong>Professional Experience</strong></h3>
<%@include file="sabre.jsp"%>
<%@include file="ameripath.jsp"%>
<%@include file="aviall.jsp"%>
<%@include file="health_markets.jsp"%>
<%@include file="sprint.jsp"%>
<%@include file="secureinfo.jsp"%>
<%@include file="imc2.jsp"%>
<%@include file="sadbury_schweppes.jsp"%>
<%@include file="taliant.jsp"%>
<%@include file="burlington_resources.jsp"%>
<%@include file="trailblazer.jsp"%>
<%@include file="exe_tech.jsp"%>
<%@include file="archon_group.jsp"%>
<%@include file="deloitte_touche.jsp"%>
<%@include file="jayhawk.jsp"%>
<%@include file="mclane.jsp"%>
<%@include file="matcom.jsp"%>

												</font>
												<br>
												<br>
											</td></tr>
										</table></td>
									</tr>
								</table></td>
							</tr>
						</table></td>
					</tr>
				</table></td>
				<td style="width:50%;"><img alt=""  src="../../../images/spacer.gif" width="1" height="1"></td>
			</tr>
		</table></td>
	</tr>
	</auth:RolesExist>
	
	<auth:RolesNotExist roles="rmt2admin">
	  <tr><td>&nbsp;</td></tr>
	  <tr>
	     <td>
          <span style="font-weight:normal; font-family:Arial, Helvetica, sans-serif">
		          <h3>You must be logged in to access this page</h3>
	        </span>
	     </td>
	  </tr>
	  <tr><td>&nbsp;</td></tr>
	</auth:RolesNotExist>
	
	<tr>
		<td style=" width:100%; height:100%;  background: #1E1E1E url(../../../images/end_bgr.jpg) top repeat-x;">
		   <%@include file="../../../forms/common/footer.jsp"%>	
	   </td>
	</tr>
</table>
<font class="blocktext">
</font><font class="blocktext">
</font>
</body>
</html>
