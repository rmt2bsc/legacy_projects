<%@ taglib uri="/rmt2-taglib" prefix="tg" %>
<jsp:useBean id="QUERY_BEAN" scope="session" class="com.bean.RMT2TagQueryBean"/>
<jsp:useBean id="errorBean" scope="request" class="com.bean.RMT2ExceptionBean"/>
 

<html>
<head>
  <title>CMS Service Application</title>
  <meta http-equiv="Pragma" content="no-cache">
  <meta http-equiv="Expires" content="-1">
</head>
<link rel=STYLESHEET type="text/css" href="css/CMSMenu.css">
<link rel=STYLESHEET type="text/css" href="css/RMT2General.css">
<Script Language="JavaScript" src="js/CMSMenu.js">
</script>
<script Language="JavaScript" src="js/RMT2General.js">
</script>
<body id="MainBody" bgcolor="#FFFFFF">
<font color="#FFFFFF"></font>
<table width="80%" border="0" height="167" align="center">
  <tr>
    <td width="25%">&nbsp;</td>
    <td width="50%">
      <div align="center"><img src="images/main_logo.gif" width="400" height="200"></div>
    </td>
    <td width="25%">&nbsp;</td>
  </tr>
</table>
<table width="80%" border="1" align="center">
  <tr>
    <td width="16%" bgcolor="#FFFFFF"><img src="images/BOBO.GIF" width="100" height="100"></td>
    <td width="84%">
      <table width="100%" border="0">
        <caption align="left">
         <h2>
            <i>
              <font face="Verdana, Arial, Helvetica, sans-serif" color="#FF0000"><jsp:getProperty name="errorBean" property="className"/>&nbsp; Error!</font>
           </i>
         </h2>
        </caption>
        <tr>
          <td><font color="#000000" size="5">
            <i>Do not Panic...An error has occurred</i>
          </font>
        </td>
        </tr>
        <tr>
          <td>
            <i>
              <b>Error Code: </b>
                <jsp:getProperty name="errorBean" property="errorCode"/>
            </i>
        </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<p>&nbsp;</p><table width="50%" border="1" align="center" cellspacing="0">
  <tr>
    <td bgcolor="#CC99FF">
      <b>
        <font face="Verdana, Arial, Helvetica, sans-serif" color="#333333" size="2">What Happened?</font>
      </b>
    </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">
      <font color="#FF0000">
        <i>
           <jsp:getProperty name="errorBean" property="errorMsg"/>
        </i>
      </font>
    </td>
  </tr>
  <tr>
    <td bgcolor="#CC99FF">
      <font face="Verdana, Arial, Helvetica, sans-serif" color="#333333" size="2">
        <b>What Shall I do?</b>
      </font>
    </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">
      <font color="#FF0000">
        <i>
           <jsp:getProperty name="errorBean" property="resolution"/>
        </i>
      </font>
    </td>
  </tr>
</table>
<p>&nbsp;</p>
<form name="detailForm" action="file:///C|/Projects/webapp/coleman/codemaint" method="POST">
  <input type="button" name="Button" value="Back" style="width:90" onClick="prevPage()">
</form>
</body>
</html>
