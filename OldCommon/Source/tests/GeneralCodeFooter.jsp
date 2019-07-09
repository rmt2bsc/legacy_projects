<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<script>
  function submitForm() {
    var src;
    var action;
    
    this.setEntity();
    src  = event.srcElement;
    parent.ListFrame.handleAction2(src.name);
  }
  
  function setEntity() {
     parent.ListFrame.document.forms[0].entity.value = "code";
   }   
 </script>

<body bgcolor="#FFFFCC" text="#000000" onLoad="setButtonText()">
<form name="SubmitSection" method="post" action="">
  <input type="button" name="add_code" value="Add" style=width:90 onClick="submitForm()">
  <input type="button" name="delete_code" value="Delete" style=width:90 onClick="submitForm()">
  <input type="button" name="save_code" value="Save" style=width:90 onClick="submitForm()">
  <input type="button" name="back_parent" value="Back" style=width:90 onClick="submitForm()">
</form>
</body>
</html>
