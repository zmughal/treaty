<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/verificationresults">
		<html>
		<head>
		<link href="verificationresults.css" type="text/css" rel="stylesheet" />
		</head>
			<body>
				<h1>Contract Verification Results</h1>
				<xsl:for-each select="contract">
					<xsl:apply-templates select="."/>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="contract">
		<hr/>
		<table class="contract">
				<tr><td class="label">plugin defining extension point:</td><td class="value"><xsl:value-of select="@plugin"/></td></tr>
				<tr><td class="label">plugin defining extension:</td><td class="value"><xsl:value-of select="@pluginOfExtension"/></td></tr>
				<tr><td class="label">extension point:</td><td class="value"><xsl:value-of select="@extensionPoint"/></td></tr>
				<tr><td class="label">contract location:</td><td class="value"><xsl:value-of select="@url"/></td></tr>
				<tr><td class="label">verification result:</td><td class="value"><xsl:value-of select="verificationresult/@result"/></td></tr>
		</table>
		<h3 align="center">verification details:</h3>
		<xsl:for-each select="group|condition">
				<xsl:apply-templates select="."/>
		</xsl:for-each>
				
	</xsl:template>
	
	<xsl:template match="group">
		<xsl:if test="verificationresult/@result='success'"><table class="success">
				<tr><td class="connective"><xsl:value-of select="@connective"/></td><td class="conditionpart">
							<xsl:for-each select="group|condition"><xsl:apply-templates select="."/></xsl:for-each>
				</td></tr>
			</table>
		</xsl:if>
		<xsl:if test="verificationresult/@result='failure'"><table class="failure">
				<tr><td><xsl:value-of select="@connective"/></td><td class="conditionpart">
							<xsl:for-each select="group|condition"><xsl:apply-templates select="."/></xsl:for-each>
				</td></tr>
			</table>
		</xsl:if>
	
	</xsl:template>
	
	<xsl:template match="condition">
			<xsl:if test="verificationresult/@result='success'"><table class="success">
				<tr><td class="label">constraint:</td><td class="value"><xsl:value-of select="@relationship"/></td></tr>
				<tr><td class="label">resource1:</td><td class="value"><xsl:apply-templates select="resource[1]"/></td></tr>
				<tr><td class="label">resource2:</td><td class="value"><xsl:apply-templates select="resource[2]"/></td></tr>
				<xsl:apply-templates select="verificationresult"/>
			</table>
		</xsl:if>
		<xsl:if test="verificationresult/@result='failure'"><table class="failure">
				<tr><td class="label">constraint:</td><td class="value"><xsl:value-of select="@relationship"/></td></tr>
				<tr><td class="label">resource1:</td><td class="value"><xsl:apply-templates select="resource[1]"/></td></tr>
				<tr><td class="label">resource2:</td><td class="value"><xsl:apply-templates select="resource[2]"/></td></tr>
				<xsl:apply-templates select="verificationresult"/>
			</table>
		</xsl:if>			
	</xsl:template>
	
	<xsl:template match="verificationresult">
		<tr><td class="label">result:</td><td class="value"><xsl:value-of select="@result"/></td></tr>
		<tr><td class="label">remarks:</td><td class="value"><xsl:value-of select="@remarks"/></td></tr>
	</xsl:template>
	
	<xsl:template match="resource">
			<table class="resource">
			    <xsl:if test="@value"> 
				    <tr><td class="label">value:</td><td class="value"><xsl:value-of select="@value"/></td></tr>
				</xsl:if>
				<xsl:if test="@ref"> 
					<tr><td class="label">reference:</td><td class="value"><xsl:value-of select="@ref"/></td></tr>
				</xsl:if>
				<tr><td class="label">type:</td><td class="value"><xsl:value-of select="@type"/></td></tr>
				<tr><td class="label">owner:</td><td class="value"><xsl:value-of select="@owner"/></td></tr>
			</table>
	</xsl:template>
	
</xsl:stylesheet>
