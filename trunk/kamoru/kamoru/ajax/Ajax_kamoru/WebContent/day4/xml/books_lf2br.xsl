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
      <!--  인자 str을 선언한다. --> 
      <xsl:param name="str" /> 
      <xsl:choose>
        <!--  문자열내에 \n을 존재하는가 를 확인한다--> 
        <xsl:when test="contains($str,'\n')">
          <!--  문자열 내에서 \n이 있는 곳까지의 문자열 출력하다.--> 
          <xsl:value-of select="substring-before($str,'\n')" /> 
          <!-- <br/> 문자열을 출력한다. -->
          <br /> 
          <!--  \n 이후의 문자열에 \n 이 있으면 <br\> 로 변경해서 출력할 수 있게 lf2br 템블릿을  호출한다.--> 
          <xsl:call-template name="lf2br">
            <xsl:with-param name="str">
              <xsl:value-of select="substring-after($str,'\n')" /> 
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <!--  문자열에 \n이 존재하지 않으면 해당 문자열을 출력한다.--> 
        <xsl:otherwise>
          <xsl:value-of select="$str" /> 
        </xsl:otherwise>
      </xsl:choose>
    </xsl:template>
  
</xsl:stylesheet>
