<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<%
   String pageTitle = "RMT2 Source Document Viewer";
   String contentId = request.getParameter("contentId");
 %>
  
<html>
  <head>
    <title><%=pageTitle%></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
		<script language=JavaScript> 
			function checkIsIE() { 
				if (navigator.appName.toUpperCase() == 'MICROSOFT INTERNET EXPLORER') { 
					return true;
				} 
				else { 
					return false; 
				} 
			} 
				

			function printDocument() { 
				if (checkIsIE() == true) { 
					document.docFrame.focus(); 
					document.docFrame.print(); 
				} 
				else { 
					window.frames['docFrame'].focus(); 
					window.frames['docFrame'].print(); 
				} 
			} 
    </script> 
  </head>
  <body>
      <a href="javascript:printDocument()">Print</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:close_without_prompt()">Close Window</a>
		  <iframe name="docFrame" src="<%=APP_ROOT%>/unsecureRequestProcessor/MimeFile.Request?clientAction=fetch&contentId=<%=contentId%>" width="100%" height="1200" scrolling="auto" frameborder="0">
		  </iframe>
  </body>
</html>
