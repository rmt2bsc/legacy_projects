<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>
<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>Database Analysis Design Strategy</title>
	<link href="../../css/style.css" rel="stylesheet" type="text/css">
	<script src="../../js/menu.js"></script>
	<%@include file="../common/page_init_script.jsp"%>
</head>
<body  id="3" onLoad="init()">
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
                                          <tr>
                                            <td><img alt=""  src="../../images/title_strategy_db_anal_desgin.gif" style="margin-bottom:9px;"></td>
                                          </tr>
                                          <tr>
                                            <td class="ins" style="line-height:13px;">
	  <p><font class="blocktext">A structured database design might include:</font></p>
      <p><font class="blocktext"><strong>Requirements analysis:&nbsp;</strong>A document that defines the actual requirements of the software including business rules, user interfaces, navigation, 
	  reporting, and software integration definitions.</font></p>
      <p><font class="blocktext"><strong>Process flow:&nbsp;</strong>RMT2 prefers to document process flow and use cases using UML and other advanced software definition tools.&nbsp;&nbsp;The resulting design shows each software requirement and its relationship to other components of the custom software application.</font></p>
      <p><font class="blocktext"><strong>Database definition:&nbsp;</strong>Before database programming can start, it is important to have an accurate database design and a document listing actual data requirements, database table contents, index structures, and table relationships.&nbsp;&nbsp;A normalized database is essential for high reliability, high performance, database application development.&nbsp;&nbsp;The database design may also include referential integrity specifications.&nbsp;&nbsp;This document is especially important for custom database software development projects.</font></p>
      <p><font class="blocktext"><strong>User interface:&nbsp;</strong>A definition of the user interface requirements containing screen layouts, data elements, sequence of entry, and error reporting requirements.&nbsp;&nbsp;In custom business software applications the interface may also specify fonts, colors, and screen organization to create a seamless appearance when joined with the business software application.</font></p>
      <p><font class="blocktext"><strong>Reporting requirements:&nbsp;</strong>The database design should also include details of reports and queries required by the software application.&nbsp;&nbsp;In addition to actual report content, should also contain special requirements such as print preview and any specialized output such as PDF, HTML, etc.&nbsp;&nbsp; Internet software applications may also contain special requirements for document transport and internet security.</font></p>
	  <br style="line-height:3px;">	  	  	  
                                            </td>
                                          </tr>
										  <tr>
										     <td><a href="strategy_custom_database.jsp">Back to Database Design Strategy</a></td>
										  </tr>
										  <tr>
										     <td><br style="line-height:7px;">	</td>
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
