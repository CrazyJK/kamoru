<?xml version="1.0" encoding="euc-kr" ?>

<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method = "html" indent="yes" encoding="euc-kr" />

	<xsl:template match="rss">
  		<xsl:for-each select="channel/item">
            <DIV id="title">
              <xsl:element name="a">
                <xsl:attribute name="href"><xsl:value-of select="link"/></xsl:attribute>
                <xsl:value-of select="title" disable-output-escaping="yes"/>
              </xsl:element>
            </DIV>
            <DIV id="thumbnail">
              <xsl:element name="a">
                <xsl:attribute name="href"><xsl:value-of select="link"/></xsl:attribute>
		              <xsl:element name="img">
                    <xsl:attribute name="src"><xsl:value-of select="thumbnail"/></xsl:attribute>
                    <xsl:attribute name="border">0</xsl:attribute>
		              </xsl:element>
              </xsl:element>
            </DIV>
            <DIV id="content"><xsl:value-of select="description" disable-output-escaping="yes"/></DIV>
            <DIV style="height=20"> </DIV>
  		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
