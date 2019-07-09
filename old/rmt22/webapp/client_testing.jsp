<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>RMT2 Client Test Area</title>
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<script src="js/index_menu.js"></script>
	<%@include file="forms/common/page_init_script.jsp"%>
</head>
<body id="1" onLoad="init()">

<table border="0" cellpadding="0" cellspacing="0" align="center" width="50%" style="height:100%;">
	<tr>
		<td style="height:243px; width:100%; background:url(images/rez_top.jpg) top repeat-x;">
		   <%@include file="forms/common/header.jsp"%>
	    </td>
	</tr>
	<auth:RolesExist roles="rmt2admin, rmt2client">
	<tr>
		<td style=" width:100%; height:428px;  background: #353535 url(images/work_bgr.jpg) top repeat-x;"><table border="0" cellpadding="0" cellspacing="0" style="width:100%;">
			<tr>
				<td style="width:50%;"><img alt=""  src="images/spacer.gif" width="1" height="1"></td>
				<td style="width:766px; height:428px"><table border="0" cellpadding="0" cellspacing="0">
					<tr><td><img alt=""  src="images/spacer.gif" width="1" height="7"></td></tr>
					<tr>
						<td style="height:421px; width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="height:100%; width:766px;">
							<tr>
								<!--left-->
								<td style="width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="width:766px;">
									<tr>
										<td style="height:226px; width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="margin:5px 0 0 25px; width:712px;">
											<tr><td><img alt=""  src="images/title_client_testing.gif" style="margin-bottom:24px;"></td></tr>
											<au
											<tr><td class="ins" style="line-height:13px;">
												<font class="blocktext">
                           <a href="/speedykwik" target="_blank"><strong>Speedy Kwik Courier Services</strong></a>
												</font>
												<br>
												<br>
											</td></tr>
											
											<tr><td class="ins" style="line-height:13px;">
												<font class="blocktext">
                           <a href="/romemadison" target="_blank"><strong>Rome Madison</strong></a>
												</font>
												<br>
												<br>
											</td></tr>
											
											<tr><td class="ins" style="line-height:13px;">
												<font class="blocktext">
                           <a href="/sportbossierblack" target="_blank"><strong>Shreveport Bossier Black</strong></a>
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
				<td style="width:50%;"><img alt=""  src="images/spacer.gif" width="1" height="1"></td>
			</tr>
		</table></td>
	</tr>
    </auth:RolesExist>
	
	<auth:RolesNotExist roles="rmt2admin, rmt2client">
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
		<td style=" width:100%; height:100%;  background: #1E1E1E url(images/end_bgr.jpg) top repeat-x;">
		   <%@include file="forms/common/footer.jsp"%>	
	   </td>
	</tr>
</table>
</body>
</html>
