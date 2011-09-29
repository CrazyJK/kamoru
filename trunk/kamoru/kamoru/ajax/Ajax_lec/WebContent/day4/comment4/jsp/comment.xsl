<?xml version="1.0" encoding="euc-kr" ?>

<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method = "html" indent="yes" encoding="euc-kr" />

	<xsl:template match="comments">
	 <xsl:apply-templates select="comment"/>   
	</xsl:template>

    <xsl:template match="comment">
      <ul>
    	 <li>
         <span align="left">
           <strong ><xsl:value-of select="name" /></strong>
         </span>
         <span align="right">
           <xsl:value-of select="make_date" />
         </span>
       </li>
       <li>
        <div>
          <xsl:value-of select="content" />
        </div>
       </li>
       <li>
        <xsl:element name="input">
          <xsl:attribute name="type" >button</xsl:attribute>
          <xsl:attribute name="value">����</xsl:attribute>
          <xsl:attribute name="onclick">parent.commentUpdateForm('<xsl:value-of select="@id" />', this)</xsl:attribute>
        </xsl:element> 
        <xsl:element name="input">
          <xsl:attribute name="type" >button</xsl:attribute>
          <xsl:attribute name="value">����</xsl:attribute>
          <xsl:attribute name="onclick">parent.commentDelete('<xsl:value-of select="@id" />', this)</xsl:attribute>
        </xsl:element> 
    	 </li>
      </ul>
    </xsl:template>
</xsl:stylesheet>
