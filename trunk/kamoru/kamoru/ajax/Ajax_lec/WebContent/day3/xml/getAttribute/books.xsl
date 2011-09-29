<?xml version="1.0" encoding="euc-kr" ?>
  
<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method = "html" indent="yes" encoding="euc-kr" />
	<xsl:template match="books">
	<table border="1" cellspacing="0" cellpading="0">
      <tr>
        <th>isbn</th>
        <th>제목</th>
        <th>저자</th>
        <th>가격</th>
      </tr>
	  <xsl:for-each select="book">
      <tr>
           <td><xsl:value-of select="isbn" /></td>
           <td><xsl:value-of select="title" /></td>
    	   <td><i><xsl:value-of select="author" /></i></td>
           <td><xsl:value-of select="@price" /></td>
      </tr>
	  </xsl:for-each>
	</table>
	</xsl:template>
</xsl:stylesheet>
