<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.constants.RMT2ServletConst" %>
<%@ page import="com.util.RMT2Utility" %>
<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>Error Page</title>
	<link href="<%=APP_ROOT%>/css/style.css" rel="stylesheet" type="text/css">
    <script language="javascript">
	     function goBackOnePage() {
			 window.history.go(-1);
		 }
	</script>
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" align="center" width="50%" style="height:50%;">    
	<tr>
		<td style=" width:100%; height:428px;"><table border="0" cellpadding="0" cellspacing="0" style="width:100%;">
			<tr>
				<td style="width:50%;"><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="1"></td>
				<td style="width:766px; height:428px"><table border="0" cellpadding="0" cellspacing="0">
					<tr><td><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="7"></td></tr>
					<tr>
						<td style="height:421px; width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="height:100%; width:766px;">
							<tr>
								<!--left-->
								<td style="width:766px;">
								   <table border="0" cellpadding="0" cellspacing="0" style="width:766px;">
									  <tr>
										<td style="height:226px; width:766px;">
										   <table border="0" cellpadding="0" cellspacing="0" style="margin:31px 0 0 25px; width:712px;">
											   <tr>
												  <td>
													 <img alt="Correct image logo or include text as company name"  src="<%=APP_ROOT%>/images/RMT2_logo.gif" style="margin-bottom:24px;">
												  </td>
											    </tr>
												<tr>
												   <td class="ins" style="line-height:13px;">
												       <%@include file="includes/error_message.jsp"%>
													   <br>
													   <br style="line-height:9px;">
													</td>
												</tr>
										   </table>
										</td>
									</tr>
								  </table>
								</td>
							</tr>
                            <tr>
                       <td></td>
                    </tr>
                    <tr>
                       <td align="center"><input type="button" value="Go Back" style="width:120" onClick="goBackOnePage()"></td>
                    </tr>
						</table>
					  </td>
					</tr>
				</table></td>
				<td style="width:50%;"><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="1"></td>
			</tr>
		</table></td>
	</tr>
</table>
</body>
</html>
