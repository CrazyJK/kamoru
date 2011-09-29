<?xml version="1.0" encoding="euc-kr" ?>
  
<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method = "html" indent="yes" encoding="euc-kr" />
	<xsl:template match="books">
	<ul>
		<xsl:for-each select="book">
    		<li>
              <strong><xsl:value-of select="isbn" /></strong>
              <strong><xsl:value-of select="title" /></strong>
    		(<i>
               <xsl:call-template name="lf2br">
                 <xsl:with-param name="str" select="author" /> 
               </xsl:call-template>
            </i>)
		</li>
		</xsl:for-each>
	</ul>
	</xsl:template>
  
    <xsl:template name="lf2br">
      <!--  ���� str�� �����Ѵ�. --> 
      <xsl:param name="str" /> 
      <xsl:choose>
        <!--  ���ڿ����� \n�� �����ϴ°� �� Ȯ���Ѵ�--> 
        <xsl:when test="contains($str,'\n')">
          <!--  ���ڿ� ������ \n�� �ִ� �������� ���ڿ� ����ϴ�.--> 
          <xsl:value-of select="substring-before($str,'\n')" /> 
          <!-- <br/> ���ڿ��� ����Ѵ�. -->
          <br /> 
          <!--  \n ������ ���ڿ��� \n �� ������ <br\> �� �����ؼ� ����� �� �ְ� lf2br �ۺ���  ȣ���Ѵ�.--> 
          <xsl:call-template name="lf2br">
            <xsl:with-param name="str">
              <xsl:value-of select="substring-after($str,'\n')" /> 
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <!--  ���ڿ��� \n�� �������� ������ �ش� ���ڿ��� ����Ѵ�.--> 
        <xsl:otherwise>
          <xsl:value-of select="$str" /> 
        </xsl:otherwise>
      </xsl:choose>
    </xsl:template>
  
</xsl:stylesheet>
