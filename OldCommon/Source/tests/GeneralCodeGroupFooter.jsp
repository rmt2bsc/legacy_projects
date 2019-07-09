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
      var footerForm;
      
      if (parent.ListFrame.document.forms[0].name == "GeneralGroupForm") {
         parent.ListFrame.document.forms[0].entity.value = "group";
      }
      if (parent.ListFrame.document.forms[0].name == "GeneralCodesForm") {
         parent.ListFrame.document.forms[0].entity.value = "code";
      }      
//      alert("Entity: " + parent.ListFrame.document.forms[0].entity.value);
   }   
 </script>

<body bgcolor="#FFFFCC" text="#000000" onLoad="setButtonText()">
<form name="SubmitSection" method="post" action="">
  <input type="button" name="add" value="Add" style=width:90 onClick="submitForm()">
  <input type="button" name="delete" value="Delete" style=width:90 onClick="submitForm()">
  <input type="button" name="save" value="Save" style=width:90 onClick="submitForm()">
  <input type="button" name="details" value="Details" style=width:90 onClick="submitForm()">
</form>
</body>
</html>
