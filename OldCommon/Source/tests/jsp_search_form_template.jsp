<%@ taglib uri="/rmt2-taglib" prefix="tg"%>
<html>
<head>
<title>Funeral Service Application</title>
</head>
<link rel=STYLESHEET type="text/css" href="css/RMT2Menu.css">
<Script Language="JavaScript" src="js/RMT2Menu.js">
</script>
<body id="fhMainBody" class="menuBar" bgcolor="#FFFFFF">
<table width="992px" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <tr>
        <td valign="top" align="left" bgcolor="green" >
          <table border="0" cellspacing="0" cellpadding="0">
            <tr align="center" valign="middle">
              <td bgcolor="green" nowrap><a id="start" class="menuBarItems" onMouseOver="startIt('caseMenu',this,0)"><b><font color="#FFFFFF">&nbsp;&nbsp;&nbsp;Case Management</font></b><img src="images/yellow_arrow_down2.gif" width="20" height="11" border=0></a></td>
              <td bgcolor="green" nowrap width="30">&nbsp;</td>
              <td bgcolor="green" nowrap><a id="start4" class="menuBarItems" onMouseOver="startIt('dataMaintMenu',this,0)"><b><font color="#FFFFFF">Data Codes</font></b><img src="images/yellow_arrow_down2.gif" width="20" height="11" border=0></a></td>
              <td bgcolor="green" nowrap width="30">&nbsp;</td>
              <td bgcolor="green" nowrap><a id="start5" class="menuBarItems" onMouseOver="startIt('pricingMenu',this,0)"><b><font color="#FFFFFF">GPL Maintenance</font></b><img src="images/yellow_arrow_down2.gif" width="20" height="11" border=0></a></td>
              <td bgcolor="green" nowrap width="30">&nbsp;</td>
              <td bgcolor="green" nowrap><a id="start6" class="menuBarItems" onMouseOver="startIt('accountingMenu',this,0)"><b><font color="#FFFFFF">General Ledger</font></b><img src="images/yellow_arrow_down2.gif" width="20" height="11" border=0></a></td>
              <td bgcolor="green" nowrap width="30">&nbsp;</td>
              <td bgcolor="green" nowrap><a id="start7" class="menuBarItems" onMouseOver="startIt('reportsMenu',this,0)"><b><font color="#FFFFFF">Reports</font></b><img src="images/yellow_arrow_down2.gif" width="20" height="11" border=0></a></td>
              <td bgcolor="green" nowrap width="20">&nbsp;</td>
              <td bgcolor="green" nowrap><a id="start8" class="menuBarMainMenuItem" href="main.htm"><u>Main Menu</u></a></td>
            </tr>
          </table>
        </td>
      </tr>
     </td>
  </tr>
</table>
<div id="headerLayer" class="taskHeader">
  <table width="100%" border="0" cellspacing="0">
  <tr valign="top">
      <td width="36%" height="20"><img src="images/fh_im_search_banner.gif" width="400" height="40"></td>
      <td width="16%" height="20">&nbsp;</td>
  </tr>
</table>
</div>

<div id="criteriaLayer" class="searchCriteriaFull">
  <form name="criteria" action="" method="post">
  </form>
</div>

<div id="detailLayer" class="searchResultsFull">
  <form name="details" action="" method="post">
  </form>
</div>

<div id="footerLayer" class="fullFooter">
  <form name="footer">
    <table width="55%" border="0" cellspacing="0" cellpadding="0" height="30">
      <tr valign="bottom" align="right">
        <td width="9%">
          <input type="button" name="Submit" value="Submit" style=width:90>
        </td>
        <td width="9%">
          <input type="button" name="Submit2" value="Submit" style=width:90>
        </td>
        <td width="9%">
          <input type="button" name="Submit3" value="Submit" style=width:90>
        </td>
        <td width="9%">
          <input type="button" name="Submit4" value="Submit" style=width:90>
        </td>
      </tr>
    </table>
  </form>
</div>



<tg:WebMenu menuName="caseMenu" filename="caseMenu"/>
<tg:WebMenu menuName="scheduleMenu" filename="scheduleMenu"/>
<tg:WebMenu menuName="trackingMenu" filename="trackingMenu"/>
<tg:WebMenu menuName="dataMaintMenu" filename="dataMaintMenu"/>
<tg:WebMenu menuName="pricingMenu" filename="pricingMenu"/>
<tg:WebMenu menuName="accountingMenu" filename="accountingMenu"/>
<tg:WebMenu menuName="reportsMenu" filename="reportsMenu"/>

</body>
</html>
