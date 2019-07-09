<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>Payment Terms</title>
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
	
	<auth:RolesExist roles="rmt2admin">
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
											<tr><td><img alt=""  src="../../images/title_payterms.gif" style="margin-bottom:24px;"></td></tr>
											<tr><td class="ins" style="line-height:13px;">
		                                       <ul style="line-height:25px;">
											      <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
 													    Consultation is always free.
                                                    </span>
												  </li>
												  <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
													   Our terms of payment goes as follows:
													   <ol>
													      <li> Projects <strong>less than $1,500.00</strong>&nbsp;requires
														     <ul>
															    <li>100% advance payment.</li>
															 </ul>
														  </li>
														  <li> Projects <strong>between $1,500.00 and $3,500.00</strong>&nbsp;requires
														     <ul>
															    <li>50% advance retainer payment.</li>
																<li>50% after delivery.</li>
															 </ul>
														  </li>
														  <li> Projects <strong>exceeding $3,500.00</strong>&nbsp;requires
														     <ul>
															    <li>40% advance retainer payment</li>
																<li>40% after beta version delivery</li>
																<li>20% after final version delivery</li>
															 </ul>
														  </li>
													   </ol>
													   *** Full payment is due immediately at the commencement of each project phase.
													 </span>
													 <br>
												  </li>
												  <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
													    If we are working for you by the hour, we will send you an invoice on the 1st and 16th of each month in most cases. All source code will remain in "escrow" until payment is made in full.
													 </span>
												  </li>
												  <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
													    All work performed is billable and will be considered as part of the finished product whether content is used in the final design or not. 
												     </span>
												  </li>
												  <li class="strongbold">
												     <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
													    It is the customer&acute;s responsibility to review design milestones for accuracy and suggest alterations at the appropriate phase. Any changes made after design agreements are considered billable and will be considered as part of the finished product whether content is used in the final design or not.
													  </span>
												  </li>
												  <li class="strongbold">
												      <span style="color:#FFFFFF; font-weight:normal; font-family:Arial, Helvetica, sans-serif">
													    Graphics and logo designs that are known to infringe on copyrights or trademarks of others will be avoided, though it is the customer's responsibility to secure IP rights for any copyrightable and/or trademarked elements used.
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
		<td style=" width:100%; height:100%;  background: #1E1E1E url(../../images/end_bgr.jpg) top repeat-x;">
		   <%@include file="../common/footer.jsp"%>	
	   </td>
	</tr>
</table>

</body>
</html>
