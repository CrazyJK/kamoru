<?xml version="1.0" encoding="euc-kr" ?>
  
<xsl:stylesheet 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:a="http://www.multicampus.or.kr/a" 
  xmlns:b="http://www.multicampus.or.kr/b"
  version="1.0">
  <xsl:output method = "html" indent="yes" encoding="euc-kr" />
  <xsl:template match="catalog">
    <h4>XSLT를 적용한 HTML 출력</h4>
    <table border="1" cellspacing="0" cellpading="0">
      <tr>
        <th>id</th>
        <th>이름</th>
        <th>가격</th>
      </tr>
      <xsl:for-each select="product">
        <tr>
          <td><xsl:value-of select="@id" />&#160;</td>
          <td><xsl:value-of select="name" />&#160;</td>
          <td><xsl:value-of select="price" />&#160;</td>
        </tr>
      </xsl:for-each>
    </table>
  </xsl:template>
</xsl:stylesheet>
