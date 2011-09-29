<?xml version="1.0" encoding="euc-kr" ?>
  
<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
    xmlns:dc="http://purl.org/dc/elements/1.1/">
	<xsl:output method = "html" indent="yes" encoding="euc-kr" />

	<xsl:template match="rss">
  		<xsl:for-each select="channel/item[position() &lt; 6]">
            <DIV>
              <H2 style="margin=0">
                <xsl:element name="a">
                  <xsl:attribute name="href"><xsl:value-of select="link"/></xsl:attribute>
                  <xsl:value-of select="title"/>
                </xsl:element>
              </H2>
            </DIV>
            <DIV>
              <SPAN><xsl:value-of select="dc:date"/></SPAN>
              <SPAN> | </SPAN>
              <SPAN><xsl:value-of select="author"/></SPAN>
            </DIV>
            <DIV><xsl:value-of select="description"/></DIV>
            <DIV style="height=20"> </DIV>
  		</xsl:for-each>
	</xsl:template>
  
</xsl:stylesheet>
