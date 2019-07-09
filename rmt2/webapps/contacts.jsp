<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-xmltaglib" prefix="xml" %>
<%@ page import="com.util.RMT2Utility" %>
<gen:InitAppRoot id="APP_ROOT"/>
<gen:InitSessionBean id="SESSION_BEAN"/>

<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>Contact Us / Free Quote</title>
	<link href="<%=APP_ROOT%>/css/style.css" rel="stylesheet" type="text/css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script src="<%=APP_ROOT%>/js/index_menu.js"></script>
	<%@include file="forms/common/page_init_script.jsp"%>
	<script>
	   /***********************************
	    *  Submit the form
  		***********************************/
	    function submitForm() {
			  handleAction("_top", document.QuoteForm, "submit")
	    }
	</script>
</head>

<xml:WebServiceQuery id="stateDso" 
                     serviceId="RQ_us_states_search"
                     countryId="1"
                     order=" state_name"/>

<body  id="6" onLoad="init()">
<table border="0" cellpadding="0" cellspacing="0" align="center" width="50%" style="height:100%;">
	<tr>
		<td style="height:243px; width:100%; background:url(<%=APP_ROOT%>/images/rez_top.jpg) top repeat-x;">
		   <%@include file="forms/common/header.jsp"%>
	    </td>
	</tr>
	<tr>
		<td style=" width:100%; height:428px;  background: #353535 url(<%=APP_ROOT%>/images/work_bgr.jpg) top repeat-x;"><table border="0" cellpadding="0" cellspacing="0" style="width:100%;">
			<tr>
				<td style="width:50%;"><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="1"></td>
				<td style="width:766px; height:428px"><table border="0" cellpadding="0" cellspacing="0">
					<tr><td><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="7"></td></tr>
					<tr>
						<td style="height:421px; width:766px;"><table border="0" cellpadding="0" cellspacing="0" style="height:100%; width:766px;">
							<tr>
								<td style="width:240px;"><table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td style="height:319px;"><table border="0" cellpadding="0" cellspacing="0" style="margin:10px 0 0 17px; width:193px;">
											<tr><td><img alt=""  src="<%=APP_ROOT%>/images/title_contact_info.gif" style="margin-bottom:4px;"></td></tr>
											
											<tr><td><img alt=""  src="<%=APP_ROOT%>/images/6f1.jpg" style="margin-bottom:15px;"></td></tr>
											<tr><td class="ins">
											
											<font class="blocktext"><strong>RMT2 Business Systems Corp.<br>2300 Dana Dr<br>
											Flower Mound, TX 75028.</strong></font><br><br style="line-height:15px;">
											
											<table border="0">
											  <tr>
												 <td><font class="blocktext">Telephone:</font></td>
												 <td><font class="blocktext"><strong>(214) 498-3935</strong></font></td>
											  </tr>
											  <tr>
												 <td><font class="blocktext">E-mail:</font></td>
												 <td><a href="mailto:rmt2bsc2@verizon.net"><strong>rmt2bsc2@verizon.net</strong></a></td>
											  </tr>					
											  <tr>
												 <td><font class="blocktext">Hours:</font></td>
												 <td><font class="blocktext"><strong>Monday-Friday, 9am-5pm Central Time</strong></font></td>
											  </tr>																				  
											</table>
											</font>
											</td></tr>
										</table></td>
									</tr>
									<tr>
									   <td><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="30"></td>
									</tr>
								</table></td>
								<td style="width:1px; height:100%; background:#999999"><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="1"></td>
								<td style="width:525px;"><table border="0" cellpadding="0" cellspacing="0" style="width:525px;">
									<tr>
										<td style="height:123px; width:525px;"><table border="0" cellpadding="0" cellspacing="0" style="margin:10px 0 0 25px; width:464px;">
											<tr><td><img alt=""  src="<%=APP_ROOT%>/images/title_contact_us.gif" style="margin-bottom:4px;"></td></tr>
											<tr>
											  <td><font class="blocktext">Please call us, click the Email link in the "Contact Information" section to send us an email, or use this form to submit inqueries, comments, suggestions, or request-for-proposal (RFP), and we'll contact you immediately.&nbsp;&nbsp;<strong>RMT2 respects your privacy and will not sell or share your contact information in any manner</strong>.<br></font></td>
											</tr>
										</table></td>
									</tr>
									<tr><td style="width:100%; height:1px; background: #9E9E9E"><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="1"></td></tr>
									
										<form name="QuoteForm" method="POST" action="<%=APP_ROOT%>/sessionBypassProcessor/Contact.Quote">
										<input name="clientAction" type="hidden">
                    
										<tr>
											<td style="width:525px;">
											  <table border="0" cellpadding="0" cellspacing="0" style="margin:8px 0 0 25px;">
											    <tr><td><img alt=""  src="<%=APP_ROOT%>/images/title_inquiry_form.jpg" style="margin-bottom:15px;"margin-left:15px;"></td></tr>
			                    <tr>
			                         <td>
			                            <font color="red" style="font-weight: normal">
			                               <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
			                            </font><br>
			                         </td>
			                    </tr>
  												<tr>
													<td style="width:525px; height:162px;"><table border="0" cellpadding="0" cellspacing="0" style="height:100%;">
														<tr>
															<td style="width:215px;">
															<table border="0" cellpadding="0" cellspacing="0" style="width:211px; margin:8px 0 0 0;">
																<tr>
																	<td class="form"><table border="0" cellpadding="0" cellspacing="0">
																		<tr><td>*&nbsp;Company name:</td></tr>
																		<tr>
																		  <td>
																		    <beanlib:InputControl type="text" maxLength="40" tabIndex="1" name="CompanyName" value="#quote.CompanyName" style="margin:2px 0 5px 0;"/>
																		  </td>
																		</tr>	
																		<tr><td>*&nbsp;Contact name:</td></tr>
																		<tr><td><beanlib:InputControl type="text" maxLength="50" tabIndex="2" name="ContactName" value="#quote.ContactName" style="margin:2px 0 5px 0;"/></td></tr>
																		<tr><td>Address Line 1:</td></tr>
																		<tr><td><beanlib:InputControl type="text" maxLength="25" tabIndex="3" name="Addr1" value="#quote.Addr1" style="margin:2px 0 5px 0;"/></td></tr>
																		<tr><td>Address Line 2:</td></tr>
																		<tr><td><beanlib:InputControl type="text" maxLength="25" tabIndex="4" name="Addr2" value="#quote.Addr2" style="margin:2px 0 5px 0;"/></td></tr>
																		<tr><td>City</td></tr>
																		<tr><td><beanlib:InputControl type="text" maxLength="25" tabIndex="5" name="City" value="#quote.City" style="margin:2px 0 5px 0;"/></td></tr>
																		<tr><td>*&nbsp;State:</td></tr>
																		<tr><td>
											   							<xml:InputControl dataSource="stateDso"
																											 type="select"
																											 name="State"
																											 query="//ns2:RS_us_states_search/state_info"
																											 codeProperty="state_code"
																											 displayProperty="state_name"
																											 selectedValue="#quote.State"
																											 tabIndex="6" />
																		</td></tr>
																		
																		<tr><td>*&nbsp;Zip:</td></tr>
																		<tr><td><beanlib:InputControl type="text" maxLength="5" tabIndex="7" name="Zip" value="#quote.Zip" style="margin:2px 0 5px 0;"/></td></tr>
																		<tr><td>*&nbsp;E-mail address:</td></tr>
																		<tr><td><beanlib:InputControl type="text" maxLength="40" tabIndex="8" name="Email" value="#quote.Email" style="margin:2px 0 5px 0;"/></td></tr>
																		<tr><td>Phone:</td></tr>
																		<tr><td><beanlib:InputControl type="text" maxLength="20" tabIndex="9" name="Phone" value="#quote.Phone" style="margin:2px 0 5px 0;"/></td></tr>
																	</table>
																	</td>
																</tr>
															</table></td>
															
															<td style="width:1px; height:100%; background: #2B2B2B"><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="1"></td>
															<td><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="25" height="1"></td>
															<td style="width:275px;"><table border="0" cellpadding="0" cellspacing="0" style="width:275px;">
																
																<tr>
																	<td class="form"><table border="0" cellpadding="0" cellspacing="0">
																		<tr><td>*&nbsp;Software needs:</td></tr>
																		<tr><td>&nbsp;</td></tr>																
																		<tr>
																		  <td><beanlib:InputControl type="radio" tabIndex="10" name="SolutionType" checkedValue="#quote.SolutionType" value="1" style="width:25px; background: #353535;"/>Custom software solution</td>
																		</tr>
																		<tr><td>&nbsp;</td></tr>
																		<tr>
																		   <td><beanlib:InputControl type="radio" tabIndex="11" name="SolutionType" checkedValue="#quote.SolutionType" value="2" style="width:25px; background: #353535;"/>Enhancements to existing software</td>
																		</tr>
																		<tr><td>&nbsp;</td></tr>
																		<tr>
																		   <td><beanlib:InputControl type="radio" tabIndex="12" name="SolutionType" checkedValue="#quote.SolutionType" value="3" style="width:25px; background: #353535;"/>Other</td>
																		</tr>
																		<tr><td>&nbsp;</td></tr>
																		<tr>
																		  <td>Questions, requirements, or comments:</td>
																		</tr>
																		<tr><td><beanlib:InputControl type="textarea" size="40" tabIndex="13" name="Comments" value="#quote.Comments" cols="2" rows="2" wrap="soft" style="margin-top:2px; width:270px; height:145px;" /></td></tr>
																		<tr><td>&nbsp;</td></tr>
																		<tr><td>Preferred Contact Method:</td></tr>
																		<tr>
																		   <td>
																		      <beanlib:InputControl type="radio" tabIndex="14" name="ContactPref" checkedValue="#quote.ContactPref" value="1" style="width:25px; background: #353535;"/>&nbsp;Call me&nbsp;&nbsp;
																		      <beanlib:InputControl type="radio" tabIndex="15" name="ContactPref" checkedValue="#quote.ContactPref" value="2" style="width:25px; background: #353535;"/>&nbsp;Email me
																		   </td>
																		</tr>						
																		<tr><td>&nbsp;</td></tr>
																		<tr><td>Web site URL:</td></tr>
																		<tr><td><beanlib:InputControl type="text" maxLength="100" tabIndex="16" name="Website" value="#quote.Website" style="margin:2px 0 5px 0;"/></td></tr>
																		<tr><td>&nbsp;</td></tr>
																		<tr><td>Expected time frame:</td></tr>
																		<tr><td><beanlib:InputControl type="text" maxLength="20" tabIndex="17" name="TimeFrame" value="#quote.TimeFrame" style="margin:2px 0 5px 0;"/></td></tr>
																		<tr>
																			<td>
																			   <table width="50%" align="left" border="0" cellpadding="0" cellspacing="0" style="margin:27px 0 0 0;">
																					<tr>
																						<td class="ins"><a href="javascript:submitForm()"><img src="<%=APP_ROOT%>/images/submit_button.gif" border="0"></a></td>
																					</tr>
																					<tr>
																					   <td><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="7"></td>
																					</tr>
																					<tr>
																					   <td>&nbsp;</td>
																					</tr>
																				</table>
																			</td>
																		</tr>
																	</table>
																	</td>
																</tr>
															</table></td>
														</tr>
													</table></td>
												</tr>
											</table></td>
										</tr>
									 </form>
								  
								</table></td>
							</tr>
						</table></td>
					</tr>
				</table></td>
				<td style="width:50%;"><img alt=""  src="<%=APP_ROOT%>/images/spacer.gif" width="1" height="1"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td style=" width:100%; height:100%;  background: #1E1E1E url(<%=APP_ROOT%>/images/end_bgr.jpg) top repeat-x;">
		   <%@include file="forms/common/footer.jsp"%>	
	   </td>
	</tr>
</table>
</body>
</html>
