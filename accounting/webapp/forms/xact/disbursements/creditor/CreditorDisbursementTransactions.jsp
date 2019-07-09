<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-taglib" prefix="db" %>
<%@ page import="com.bean.RMT2TagQueryBean" %>
<%@ page import="com.constants.GeneralConst" %>
<%@ page import="com.xact.XactConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

  <%
   	  String pageTitle = "Creditor/Vendor Cash Disbursement Transactions";
  %>  
  
<html>
  <head>
    <title><%=pageTitle%></title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
  	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>
   

	
  <%@include file="/includes/SessionQuerySetup.jsp"%>  	 
    
 <db:connection id="con" classId="com.bean.db.DatabaseConnectionBean"/>
 <db:datasource id="xactTypeDso" 
                classId="com.api.DataSourceApi" 
	   	          connection="con" 
	              query="XactTypeView" 
                where="xact_catg_id = 3"
                order="description" type="xml"/>
 
  <body bgcolor="#FFFFFF" text="#000000">
    <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/DisbursementsCreditor.XactList">
      <h2><strong><%=pageTitle%></strong></h2>
		  <font size="3" style="color:blue">
		    List results for: 
		    <strong>
		        <font style="color:black">
		          <beanlib:InputControl value="#creditorext.Name"/>
		        </font>
		    </strong>
		  </font>
		  <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:95%; height:700px; overflow:auto">
			 <table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
				 <tr>
					 <th width="4%" class="clsTableListHeader">&nbsp;</th>
					 <th width="10%" class="clsTableListHeader">Id</th>
					 <th width="12%" class="clsTableListHeader">Trans. Date</th>
					 <th width="12%" class="clsTableListHeader" style="text-align:right">Amount</th>
					 <th width="1%" class="clsTableListHeader">&nbsp;</th>
					 <th width="15%" class="clsTableListHeader" style="text-align:left">Date Entered</th>
					 <th width="19%" class="clsTableListHeader" style="text-align:left">Trans. Type</th>				 
					 <th width="32%" class="clsTableListHeader" style="text-align:left">Source</th>
				 </tr>

				 <beanlib:InputControl type="hidden" name="CreditorId" value="#creditor.CreditorId"/>	
				 <beanlib:LoopRows bean="beanObj" list="<%=GeneralConst.CLIENT_DATA_LIST %>">
				  <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
				 	<td align="center" class="clsTableListHeader" style="vertical-align:middle"> 
				 	   <beanlib:InputControl type="radio" name="selCbx" value="rowid"/>
					</td>   
					<td align="center" >
					   <font size="2">
							 <beanlib:InputControl type="hidden" name="XactId" value="#beanObj.XactId" uniqueName="yes"/> 
							 <beanlib:InputControl type="hidden" name="XactTypeId" value="#beanObj.XactTypeId" uniqueName="yes"/> 
							 <beanlib:InputControl value="#beanObj.XactId"/>
					   </font>
					</td>             
					<td align="center">
					   <font size="2"> 
					      <beanlib:InputControl value="#beanObj.XactDate" format="MM-dd-yyyy"/>
					   </font>
					</td>
					<td align="right">
						<font size="2">
						   <beanlib:InputControl value="#beanObj.XactAmount" format="$#,##0.00;($#,##0.00)"/>
						</font>
					</td>
					<td align="right">&nbsp;</td>							 
					<td align="left" >
						<font size="2">
						   <beanlib:InputControl value="#beanObj.DateCreated" format="MM-dd-yyyy HH:mm:ss" />
						</font>
					</td>						
					<td align="left" >
						<font size="2">
						   <beanlib:InputControl value="#beanObj.XactTypeName"/>
						</font>
					</td>											
					<td align="left" >
						<font size="2">
						   <beanlib:InputControl value="#beanObj.Reason"/>
						</font>
					</td>							
				 </tr>
				 </beanlib:LoopRows>
	
				 <% if (pageContext.getAttribute("ROW") == null) {
					   out.println("<tr><td colspan=9 align=center>Data Not Found</td></tr>");
					}
				 %>
			 </table>	  
		  </div>
	
	    <br>
		  <table>
		    <tr>
				<td colspan="2">
				  <input name="<%=XactConst.REQ_VIEW%>" type="button" value="View" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
				  <input name="<%=GeneralConst.REQ_BACK %>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
				</td>
			</tr>
		  </table>
		  	  
		 <input name="clientAction" type="hidden">
   </form>
   <db:Dispose/>
 </body>
</html>
