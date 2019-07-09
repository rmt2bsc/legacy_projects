<?xml version='1.0'?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="hoursDoc" select="document('EmployeeHours.xml')"/>
	<xsl:variable name="host" select="document('company.xml')"/>

    <xsl:template match="/">
		<fo:root>
            <fo:layout-master-set>
				<fo:simple-page-master master-name="main-page" page-height="11in" page-width="8.5in" margin-left="0.25in" margin-right="0.25in" margin-bottom="0.25in" margin-top="0.25in">
					<fo:region-body margin-top="1.5in" margin-bottom="0.5in"/>
					<!-- Header -->
					<fo:region-before extent="0.15in"/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="main-page">

  				<fo:static-content flow-name="xsl-region-before">
   					<fo:table-and-caption>
						<fo:table width="100%">
							<fo:table-column column-width="30mm"/>
							<fo:table-column column-width="45mm"/>
							<fo:table-column column-width="20mm"/>
							<fo:table-column column-width="5mm"/>
                             <fo:table-body>
							      <fo:table-row>
								        <fo:table-cell>
										      <fo:block text-align="left">
										      		<fo:external-graphic src="c:\temp\RMT2_logo.gif"/>
											  </fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block font-size="24pt" text-align="center">
					       							<xsl:text>Timesheet</xsl:text>
					  						</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block text-align="right">
					       							<xsl:text>Page</xsl:text>
					  						</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block text-align="left">
					       							<fo:page-number/>
					  						</fo:block>
										</fo:table-cell>
								    </fo:table-row>

									 <fo:table-row>
								        <fo:table-cell>
										      <fo:block text-align="left">
										      		<xsl:text>&#xA0;</xsl:text>
											  </fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block text-align="center">
					       							<xsl:text>Document Id: </xsl:text>
													<xsl:value-of select="$hoursDoc/dataitem/pt/display_value"/>
					  						</fo:block>
										</fo:table-cell>
										<fo:table-cell number-columns-spanned="2">
											<fo:block text-align="right">
					       							<xsl:text>&#xA0;</xsl:text>
					  						</fo:block>
										</fo:table-cell>
								    </fo:table-row>
							   </fo:table-body>
                        </fo:table>
					</fo:table-and-caption>
                </fo:static-content>

  				<fo:flow flow-name="xsl-region-body">
						<fo:table-and-caption>
							<fo:table width="100%">
									<fo:table-column column-width="45%"/>
									<fo:table-column column-width="2"/>
									<fo:table-column column-width="53%"/>
									 <fo:table-body>
									      <fo:table-row>
										       <fo:table-cell>
											         <fo:block>
													      <fo:table-and-caption>
							                     				<fo:table width="100%">
																	<fo:table-column column-width="100%"/>
		 															<fo:table-body>
																		<fo:table-row>
			       															<fo:table-cell>
													      						<fo:block text-align="left" font-size="16pt" font-weight="bold">
									                              						<xsl:text>Service Provider</xsl:text>
       								                       						</fo:block>
				   															</fo:table-cell>
			  															</fo:table-row>
											         					<xsl:for-each select="$host/dataitem/company">
																	        <fo:table-row>
											       									<fo:table-cell>
																					      <fo:block>
												                                          		<xsl:value-of select="name"/>
																						  </fo:block>
												   									</fo:table-cell>
											  								</fo:table-row>
										      								<fo:table-row>
											       									<fo:table-cell>
																					      <fo:block>
												                                          		<xsl:value-of select="address1"/>
																						  </fo:block>
												   									</fo:table-cell>
											  								</fo:table-row>
																			<fo:table-row>
										       									<fo:table-cell>
																				      <fo:block>
											                     							<xsl:value-of select="city"/>
																							<xsl:text>,&#xA0;</xsl:text>
																							<xsl:value-of select="state"/>
																							<xsl:text>&#xA0;</xsl:text>
																							<xsl:value-of select="zip"/>
																					  </fo:block>
											   									</fo:table-cell>
										  									</fo:table-row>
									      									<fo:table-row>
										       									<fo:table-cell>
																				      <fo:block>
																					        <xsl:text>Phone&#xA0;</xsl:text>
											                                          		<xsl:value-of select="phone"/>
																					  </fo:block>
											   									</fo:table-cell>
										  									</fo:table-row>
									      									<fo:table-row>
										       									<fo:table-cell>
																				      <fo:block>
																					        <xsl:text>Fax &#xA0;</xsl:text>
											                                          		<xsl:value-of select="fax"/>
																					  </fo:block>
											   									</fo:table-cell>
										  									</fo:table-row>
									      									<fo:table-row>
										       									<fo:table-cell>
																				      <fo:block>
																					        <xsl:text>Email&#xA0;</xsl:text>
											                                          		<xsl:value-of select="email"/>
																					  </fo:block>
											   									</fo:table-cell>
										  									</fo:table-row>
																			<fo:table-row>
										       									<fo:table-cell>
																				      <fo:block>
																					        <xsl:text>Website&#xA0;</xsl:text>
											                                          		<xsl:value-of select="website"/>
																					  </fo:block>
											   									</fo:table-cell>
										  									</fo:table-row>				
																		</xsl:for-each>
																	</fo:table-body>
																</fo:table>
															</fo:table-and-caption>
													 </fo:block>
											   </fo:table-cell>

												<fo:table-cell>
												     <fo:block/>
												</fo:table-cell>

										       <fo:table-cell>
											         <fo:block>
															<fo:table-and-caption>
							                     				<fo:table width="100%">
																	<fo:table-column column-width="35%"/>
																	<fo:table-column column-width="65%"/>
		 															<fo:table-body>
																		<fo:table-row>
			       															<fo:table-cell>
													      						<fo:block text-align="left" font-size="16pt" font-weight="bold">
									                              						<xsl:text>Client</xsl:text>
       								                       						</fo:block>
				   															</fo:table-cell>
			  															</fo:table-row>
											         					<xsl:for-each select="/dataitem/VW_CLIENT_EXT">
																	        <fo:table-row>
																			       <fo:table-cell>
																					      <fo:block text-align="left" font-weight="bold">
												                                          		<xsl:text>Name:</xsl:text>
																						  </fo:block>
												   									</fo:table-cell>
											       									<fo:table-cell>
																					      <fo:block>
												                                          		<xsl:value-of select="longname"/>
																						  </fo:block>
												   									</fo:table-cell>
											  								</fo:table-row>
										      								<fo:table-row>
																			         <fo:table-cell>
																					      <fo:block text-align="left" font-weight="bold">
												                                          		<xsl:text>Account No.:</xsl:text>
																						  </fo:block>
												   									</fo:table-cell>
											       									<fo:table-cell>
																					      <fo:block>
												                                          		<xsl:value-of select="account_no"/>
																						  </fo:block>
												   									</fo:table-cell>
											  								</fo:table-row>
																			<fo:table-row>
																			    <fo:table-cell>
																					      <fo:block text-align="left" font-weight="bold">
												                                          		<xsl:text>Consultant Name:</xsl:text>
																						  </fo:block>
												   									</fo:table-cell>
										       									<fo:table-cell>
																				      <fo:block>
											                     							<xsl:value-of select="$hoursDoc/dataitem/pt/shortname"/>
																					  </fo:block>
											   									</fo:table-cell>
										  									</fo:table-row>
																		</xsl:for-each>
																	</fo:table-body>
																</fo:table>
															</fo:table-and-caption>											         		

													 </fo:block>
											   </fo:table-cell>
										  </fo:table-row>
									 </fo:table-body>
							</fo:table>
						</fo:table-and-caption>

                </fo:flow>

			</fo:page-sequence>
		</fo:root>
    </xsl:template>
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="EmployeeClient.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="renderx" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="EmployeeClient.xml" srcSchemaRoot="dataitem" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->