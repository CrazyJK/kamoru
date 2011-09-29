<?xml version="1.0" encoding="euc-kr" ?>
  
<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method = "html" indent="yes" encoding="euc-kr" />
	<xsl:template match="/">
	<ul>
		<xsl:for-each select="root/items">
		<li><b><xsl:value-of select="item" /></b>
		(<i><xsl:value-of select="value" /></i>)
		</li>
		</xsl:for-each>
	</ul>
	</xsl:template>
</xsl:stylesheet>
