<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<script>
  function submitForm() {
    var src;
    var action;
    
    src  = event.srcElement;
    parent.ListFrame.handleAction(src.name);
  }
 </script>

<body bgcolor="#FFFFCC" text="#000000" onLoad="setButtonText()">
<form name="SubmitSection" method="post" action="">
  <input type="button" name="add" value="Add" style=width:90 onClick="submitForm()">
  <input type="button" name="back" value="Back" style=width:90 onClick="submitForm()">
</form>
</body>
</html>
