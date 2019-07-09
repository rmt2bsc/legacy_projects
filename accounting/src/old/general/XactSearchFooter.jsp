<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
<head>
<title>Transaction Search Footer</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	<script>
	  function submitForm() {
	    var src;
	    var action;
	    
	    src  = event.srcElement;
		switch (src.name) {
		  case "add":
		     // Submit Add request to the server.
			 parent.SearchFrame.executeSearchCriteria(MAIN_DETAIL_FRAME, parent.SearchFrame.document.SearchForm, src.name);
//			 parent.SearchFrame.handleAction(MAIN_DETAIL_FRAME, parent.SearchFrame.document.SearchForm, src.name);
		     break;
	      case "reverse":
		     // Submit Reverse request for selected transaction to the server
		     parent.ListFrame.executeList(MAIN_DETAIL_FRAME, parent.ListFrame.document.DataForm, src.name);
//	         parent.ListFrame.handleAction(MAIN_DETAIL_FRAME, parent.ListFrame.document.DataForm, src.name);
		     break;
		  case "view":
		     // Submit View request for selected transaction to the server
		     parent.ListFrame.executeList(MAIN_DETAIL_FRAME, parent.ListFrame.document.DataForm, src.name);
//		     parent.ListFrame.handleAction(MAIN_DETAIL_FRAME, parent.ListFrame.document.DataForm, src.name);
		}
	
	  }
	</script>
</head>


<body bgcolor="#FFFFCC" text="#000000">
<form name="SubmitSection" method="post" action="">
  <input type="button" name="view" value="Details" style="width:90" onClick="submitForm()">
  <input type="button" name="add" value="Add" style="width:90" onClick="submitForm()">
  <input type="button" name="reverse" value="Reverse" style="width:90" onClick="submitForm()">
</form>
</body>
</html>
