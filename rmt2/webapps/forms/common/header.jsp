<%@ taglib uri="/rmt2-securitytaglib" prefix="auth" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="bean" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-xmltaglib" prefix="xdb" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.constants.RMT2ServletConst"%>
<%@ page import="com.api.security.authentication.AuthenticationConst" %>
<%@ page import="com.api.security.authentication.RMT2SessionBean" %>

<%
//Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control","no-cache"); 
//Directs caches not to store the page under any circumstance
response.setHeader("Cache-Control","no-store"); 
//Causes the proxy cache to see the page as "stale"
response.setDateHeader("Expires", 0); 
//HTTP 1.0 backward compatibility
response.setHeader("Pragma","no-cache"); 
%>

<input type="hidden" id="urlRoot" value="<%=APP_ROOT%>">
<table border="0" cellpadding="0" cellspacing="0" style="width:100%; height:100%">
	<tr>
		<td style="width:50%">
		   <table border="0" cellpadding="0" cellspacing="0" style="width:100%; height:100%;">
			  <tr>
				 <td><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="104"></td>
			  </tr>
			  <tr>
				 <td style="background-image:url(<%=APP_ROOT%>/images/rez_top1.jpg); background-repeat:repeat-x; width:50%; height:139px; background-position:right top;"><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="1"></td>
			  </tr>
		   </table>
		 </td>
		 <td style="width:766px; height:243px;">
			<table border="0" cellpadding="0" cellspacing="0" style="width:766px;">
			   <tr>
				   <td colspan="10" style="height:104px">
					   <table border="0" cellpadding="0" cellspacing="0">
						   <tr>
							   <td style="width:503px;"><a href="<%=APP_ROOT%>/index.jsp"><img alt=""  src="<%=APP_ROOT%>/images/logotip.jpg" width="480" height="41" border="0" style="margin:39px 0 0 33px;"></a></td>
							   <td style="width:263px;" class="top">
								  <%@include file="user_identification.jsp"%>
								  <%@include file="header_nav.jsp"%>
						     </td>  
						   </tr>
						</table>
				   </td>
			   </tr>
			   <tr>
				   <td><a href="<%=APP_ROOT%>/index.jsp"><img name="m1" alt=""  src="<%=APP_ROOT%>/images/m1.jpg" border="0" onMouseOver="enableMenuItem(this)" onMouseOut="disableMenuItem(this)"></a></td>
				   <td><a href="<%=APP_ROOT%>/forms/company/company.jsp"><img name="m2" alt=""  src="<%=APP_ROOT%>/images/m2.jpg" border="0" onMouseOver="enableMenuItem(this)" onMouseOut="disableMenuItem(this)"></a></td>
				   <td><a href="<%=APP_ROOT%>/forms/services/services.jsp"><img name="m3" alt=""  src="<%=APP_ROOT%>/images/m3.jpg" border="0" onMouseOver="enableMenuItem(this)" onMouseOut="disableMenuItem(this)"></a></td>
				   <td><a href="<%=APP_ROOT%>/forms/solutions/solutions.jsp"><img name="m4" alt=""  src="<%=APP_ROOT%>/images/m4.jpg" border="0" onMouseOver="enableMenuItem(this)" onMouseOut="disableMenuItem(this)"></a></td>
				   <td><a href="<%=APP_ROOT%>/forms/process/our_process.jsp"><img name="m5" alt=""  src="<%=APP_ROOT%>/images/m5.jpg" border="0" onMouseOver="enableMenuItem(this)" onMouseOut="disableMenuItem(this)"></a></td>
				   <td><a href="<%=APP_ROOT%>/sessionBypassProcessor/Contact.Quote?clientAction=RQ_us_states_search"><img name="m6" alt=""  src="<%=APP_ROOT%>/images/m6.jpg" border="0" onMouseOver="enableMenuItem(this)" onMouseOut="disableMenuItem(this)"></a></td>
			   </tr>
			</table>
		</td>
		<td style="width:50%">
		   <table border="0" cellpadding="0" cellspacing="0" style="width:100%; height:100%;">
			  <tr>
				 <td><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="104"></td>
			  </tr>
			  <tr>
				 <td style="background-image:url(<%=APP_ROOT%>/images/rez_top2.jpg); background-repeat:repeat-x; width:50%; height:139px; background-position:top;"><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="1"></td>
			  </tr>
		   </table>
		</td>
	</tr>
</table>