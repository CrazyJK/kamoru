<?xml version="1.0" encoding="euc-kr" ?>
  
<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method = "html" indent="yes" encoding="euc-kr" />
	<xsl:template match="books">
	<ul id="elementList">
		<xsl:for-each select="book">
           <strong><xsl:value-of select="isbn" /></strong>  
           <strong><xsl:value-of select="title" /></strong> 
    		(<i><xsl:value-of select="author" /></i>)<br/>
		</xsl:for-each>
	</ul>
	</xsl:template>
</xsl:stylesheet>
