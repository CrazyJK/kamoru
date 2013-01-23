<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/kamoru/video/video.css" />
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="/kamoru/video/video.js" type="text/javascript"></script>
</head>
<body>
<div id="headerDiv">
	<div id="searchDiv" class="boxDiv">
	<html:form action="/av">
		<span class="searchGroupSpan">
			<label for="studio"> Studio	 </label><input type="text" name="studio"  id="studio"  value="<bean:write name="avForm" property="studio"/>"  class="schTxt">
			<label for="opus">   Opus  	 </label><input type="text" name="opus"    id="opus"    value="<bean:write name="avForm" property="opus"/>"    class="schTxt">
			<label for="title">  Title 	 </label><input type="text" name="title"   id="title"   value="<bean:write name="avForm" property="title"/>"   class="schTxt">
			<label for="actress">Actress </label><input type="text" name="actress" id="actress" value="<bean:write name="avForm" property="actress"/>" class="schTxt">
			<span class="checkbox" id="checkbox-addCond"        title="Add additional conditions">Add</span><input type="hidden" name="addCond"    	   id="addCond"    	   value="<bean:write name="avForm" property="addCond"/>">
			<span class="checkbox" id="checkbox-existVideo"     title="exist Video?">V</span>			    <input type="hidden" name="existVideo" 	   id="existVideo" 	   value="<bean:write name="avForm" property="existVideo"/>">
			<span class="checkbox" id="checkbox-existSubtitles" title="exist Subtitles?">S</span>			<input type="hidden" name="existSubtitles" id="existSubtitles" value="<bean:write name="avForm" property="existSubtitles"/>">
			<span class="separatorSpan">|</span>
			<span class="button" onclick="fnDetailSearch()">Search</span>
			<span class="separatorSpan">|</span>
			<span class="button" onclick="fnRandomPlay()">Random</span>
		</span>
		<span class="viewGroupSpan">
			<span class="radio" id="radio-listViewType-card"  title="show card type list">C</span> 
			<span class="radio" id="radio-listViewType-box"   title="show box type list">B</span> 
			<span class="radio" id="radio-listViewType-sbox"  title="show small box type list">SB</span>
			<span class="radio" id="radio-listViewType-table" title="show table type list">T</span>			<input type="hidden" name="listViewType" id="listViewType" value="<bean:write name="avForm" property="listViewType"/>">
			<span class="separatorSpan">|</span>
			<span class="radio" id="radio-sortMethod-S" title="sort by studio">S</span>
			<span class="radio" id="radio-sortMethod-O" title="sort by opus">O</span>
			<span class="radio" id="radio-sortMethod-T" title="sort by title">T</span>
			<span class="radio" id="radio-sortMethod-A" title="sort by actress">A</span>
			<span class="radio" id="radio-sortMethod-L" title="sort by lastmodified">M</span>				<input type="hidden" name="sortMethod"  id="sortMethod"  value="<bean:write name="avForm" property="sortMethod"/>">
			<span class="checkbox" id="checkbox-sortReverse" title="reverse sort">R</span>					<input type="hidden" name="sortReverse" id="sortReverse" value="<bean:write name="avForm" property="sortReverse"/>">
			<span class="separatorSpan">|</span>
			<span class="checkbox" id="checkbox-viewStudioDiv"  title="view Studio panel"  onclick="fnStudioDivToggle()">S</span>	<input type="hidden" name="viewStudioDiv"  id="viewStudioDiv"  value="<bean:write name="avForm" property="viewStudioDiv"/>"> 
			<span class="checkbox" id="checkbox-viewActressDiv" title="view Actress panel" onclick="fnActressDivToggle()">A</span>	<input type="hidden" name="viewActressDiv" id="viewActressDiv" value="<bean:write name="avForm" property="viewActressDiv"/>">
			<span class="separatorSpan">|</span>
			<span class="checkbox" id="checkbox-useCacheData" title="use cache data">C</span>				<input type="hidden" name="useCacheData" id="useCacheData" value="<bean:write name="avForm" property="useCacheData"/>">
		</span>
	</html:form>
	</div>
	<%-- <div id="studioDiv" class="boxDiv" style="display:<%="on".equals(viewStudioDiv) ? "" : "none" %>">
	<% for(String key : studioMap.keySet()) { %>
		<span onclick="fnStudioSearch('<%=key %>')" class="studioSpanBtn"><%=key %>(<%=studioMap.get(key) %>)</span>
	<% } %>
	</div>
	<div id="actressDiv" class="boxDiv" style="display:<%="on".equals(viewActressDiv) ? "" : "none" %>">
	<% for(String key : actressMap.keySet()) { %>
		<span onclick="fnActressSearch('<%=key %>')" class="actressSpanBtn"><%=key %>(<%=actressMap.get(key) %>)</span>
	<% } %>
	</div> --%>
</div>

<%-- <logic:iterate name="avForm" property="avlist" id="av"  type="kamoru.app.video.av.AVOpus">
	<li><bean:write name="av" property="title"/>
		<bean:write name="av" property="opus"/>
		<bean:write name="av" property="actress"/>
		<bean:write name="av" property="studio"/>
</logic:iterate> --%>

<div id="contentDiv" class="boxDiv" style="background-image:url('/kamoru/video/image.jsp?opus=<bean:write name="avForm" property="listBGImageName"/>')">
	<span id="totalCount">Total SIZE?</span><span id="debug"></span><span id="bgimg" onclick="fnImageView('<bean:write name="avForm" property="listBGImageName"/>');">BG</span>
	<logic:equal name="avForm" property="listViewType" value="card" scope="request">
	<ul>
		<logic:iterate name="avForm" property="avlist" id="av"  type="kamoru.app.video.av.AVOpus">	
		<li id="<bean:write name="av" property="opus"/>" class="boxLI">
			<div class="opusBoxDiv">
				<table>
					<tr>
						<td  colspan="2"><span class="titleSpan"><bean:write name="av" property="title"/></span></td>
					</tr>
					<tr valign="top">
						<td width="110px">
							<img src="/kamoru/video/image.jsp?opus=<bean:write name="av" property="opus"/>" height="120px" onclick="fnImageView('<bean:write name="av" property="opus"/>')"/>
						</td>
						<td>
							<dl>
								<dt><span class="studioSpan"><bean:write name="av" property="studio"/></span>&nbsp;<span class="opusSpan"><bean:write name="av" property="opus"/></span></dt>
								<dt>             
								<logic:iterate name="av" property="actressList" id="actressName" type="java.lang.String">
								<span class="actressSpan" onclick="fnActressSearch('<bean:write name="actressName"/>')"><bean:write name="actressName"/></span>
								</logic:iterate>
								</dt>
								<dd> 
									<span class="" onclick="fnPlay('<bean:write name="av" property="opus"/>')"          title="<bean:write name="av" property="videoPath"/>">Video</span>
									<span class="" onclick="fnImageView('<bean:write name="av" property="opus"/>')"     title="<bean:write name="av" property="cover"/>">Cover</span>
									<span class="" onclick="fnEditSubtitles('<bean:write name="av" property="opus"/>')" title="<bean:write name="av" property="subtitles"/>">smi</span>
									<span class="" onclick="fnEditOverview('<bean:write name="av" property="opus"/>')"  title="<bean:write name="av" property="overviewTxt"/>">Overview</span>
								</dd>
								<dd>
									<span id="DEL-<bean:write name="av" property="opus"/>" style="display:none;" class="bgSpan" onclick="fnDeleteOpus('<bean:write name="av" property="opus"/>')" title="<bean:write name="av"/>">Del</span>
								</dd>
							</dl>
						</td>
					</tr>
				</table>
			</div>
		</li>
		</logic:iterate>
	</ul>
	</logic:equal>
</div>

<form name="playFrm" target="ifrm" action="execProc.jsp" method="post">
	<input type="hidden" name="selectedOpus" id="selectedOpus">
	<input type="hidden" name="selectedMode" id="selectedMode">
</form>
<iframe name="ifrm" style="display:none; width:100%;"></iframe>


</body>
</html>
