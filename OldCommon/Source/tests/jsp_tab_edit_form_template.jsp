<%@ taglib uri="/rmt2-taglib" prefix="tg"%>
<html>
<head>
<title>Funeral Service Application</title>
<link rel=STYLESHEET type="text/css" href="css/RMT2Menu.css">
<link rel=STYLESHEET type="text/css" href="css/RMT2General.css">
</head>
<Script Language="JavaScript" src="js/RMT2Menu.js">
</script>
<script language="JavaScript" src="js/RMT2Tab.js">
</script>
<script>
  createTabProfile("tab0", "fh_tab_personal");
  createTabProfile("tab1", "fh_tab_firstcall");
  createTabProfile("tab2", "fh_tab_legal");
  createTabProfile("tab3", "fh_tab_appt");
</script>
<body id="fhMainBody" class="menuBar" bgcolor="#CCCCCC" onLoad="selectTab(tabRow, 0)">
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
<div id="headerLayer" class="taskHeader" style="visibility: visible">
  <table width="100%" border="0" cellspacing="0">
  <tr valign="top">
      <td width="36%" height="20"><img src="images/fh_case_firstcall_banner.gif" width="400" height="40"></td>
      <td width="16%" height="20">&nbsp;</td>
  </tr>
</table>
</div>

<div id="menuLayer" class="taskMenu" style="visibility: visible">
  <table width="100%" border="0" cellspacing="0">
    <tr valign="top">
        <td width="16%">
          <tg:WebMenu menuName="sideMenu" filename="fh_caseTasksMenu" menuType="2"/>
        </td>
    </tr>
  </table>
</div>

<form name="form1" method="post" action="">
<div id="detailLayer" class="taskMenuDetailB">
    <div id="tabHolder" class="tabs" style="background-color: #CCCCCC; width: 830px; height: 45px">
      <table width="70%" border="0" cellspacing="0" cellpadding="0" height="51">
        <tr id="tabRow">
          <td id="col1" align="left" width="19%"
            onClick="selectTab(tabRow, 0)"
            onMouseover="this.style.cursor='hand'">
            <img id="tab0" src="images/fh_tab_personal_1.gif" width="110" height="45">
          </td>
          <td align="left" width="19%"
              onClick="selectTab(tabRow, 1)"
              onMouseover="this.style.cursor='hand'">
              <img id="tab1" src="images/fh_tab_firstcall_0.gif" width="110" height="45">
          </td>
          <td align="left" width="19%"
              onClick="selectTab(tabRow, 2)"
              onMouseover="this.style.cursor='hand'">
              <img id="tab2" src="images/fh_tab_legal_0.gif" width="110" height="45">
          </td>
          <td align="left" width="43%"
              onClick="selectTab(tabRow, 3)"
              onMouseover="this.style.cursor='hand'">
              <img id="tab3" src="images/fh_tab_appt_0.gif" width="110" height="45">
          </td>
      </tr>
    </table>
  </div>

  <div id="tabPage0" class="tabPage" style="top: 46px; width: 830px; height: 441px; visibility: visible">
  </div>

  <div id="tabPage1" class="tabPage" style="visibility: hidden">
  </div>

  <div id="tabPage2" class="tabPage" style="visibility: hidden">
  </div>

  <div id="tabPage3" class="tabPage" style="visibility: hidden">
  </div>

</div>
</form>

<div id="footerLayer" class="taskMenuFooter" style="visibility: visible">
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
