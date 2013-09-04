<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="html"  uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean"  uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="avlist" value="${avForm.avlist}" scope="session"/>
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
	<html:form action="/av" method="post">
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
	<div id="studioDiv" class="boxDiv" style="display:<logic:notEqual name="avForm" property="viewStudioDiv" value="true">none</logic:notEqual>">
	<logic:iterate id="studio" name="avForm" property="studioMap">
		<span onclick="fnStudioSearch('<bean:write name="studio" property="key"/>')" class="studioSpanBtn"><bean:write name="studio" property="key"/>(<bean:write name="studio" property="value"/>)</span>
	</logic:iterate>
	</div>
	<div id="actressDiv" class="boxDiv" style="display:<logic:notEqual name="avForm" property="viewActressDiv" value="true">none</logic:notEqual>">
	<c:forEach items="${avForm.actressMap}" var="actress">
		<span onclick="fnActressSearch('<c:out value="${actress.key}"/>')" class="actressSpanBtn"><c:out value="${actress.key}"/>(<c:out value="${actress.value}"/>)</span>
	</c:forEach>
	</div>
</div>
<div id="contentDiv" class="boxDiv" style="background-image:url('/kamoru/video/image.jsp?opus=<bean:write name="avForm" property="listBGImageName"/>')">
	<span id="totalCount">Total <c:out value="${fn:length(avForm.avlist)}"/></span><span id="debug"></span><span id="bgimg" onclick="fnImageView('<bean:write name="avForm" property="listBGImageName"/>');">BG</span>
	<c:choose>
		<c:when test="${avForm.listViewType eq 'card' }">
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
		</c:when>
		<c:when test="${avForm.listViewType eq 'box'}">
		<ul>
			<c:forEach items="${avForm.avlist}" var="av">
			<li id="<c:out value="${av.opus}"/>" class="boxLI">
				<div class="opusBoxDiv">                   
					<dl style="background-image:url('<c:url value="/video/image.jsp"><c:param name="opus" value="${av.opus}"/></c:url>'); background-size:300px 200px; height:200px;">
						<dt><span class="bgSpan" id="titleSpan"><c:out value="${av.title}"/></span></dt>
						<dd><span class="bgSpan" id="studioSpan"  onclick="fnStudioSearch('<c:out value="${av.studio}"/>')"><c:out value="${av.studio}"/></span></dd>
						<dd><span class="bgSpan" id="opusSpan"><c:out value="${av.opus}"/></span></dd>
						<dd>
							<c:forEach items="${av.actressList}" var="actress">
							<span class="bgSpan" id="actressSpan" onclick="fnActressSearch('<c:out value="${actress}"/>')"><c:out value="${actress}"/></span>
							</c:forEach>
						</dd>
						<dd><span class="bgSpan <c:out value="${av.video eq null || fn:length(av.video) eq 0 ? 'nonExistFile' : 'existFile' }"/>" onclick="fnPlay('<c:out value="${av.opus}"/>')"          title="<c:out value="${av.videoPath}" escapeXml="true"/>">Video</span></dd>
						<dd><span class="bgSpan <c:out value="${av.cover eq null ? 'nonExistFile' : 'existFile' }"/>" onclick="fnImageView('<c:out value="${av.opus}"/>')"     title="<c:out value="${av.cover}" escapeXml="true"/>">Cover</span></dd>
						<dd><span class="bgSpan <c:out value="${av.subtitles eq null || fn:length(av.subtitles) eq 0 ? 'nonExistFile' : 'existFile' }"/>" onclick="fnEditSubtitles('<c:out value="${av.opus}"/>')" title="<c:out value="${av.subtitles}" escapeXml="true"/>">smi</span></dd>
						<dd><span class="bgSpan <c:out value="${av.overview eq null ? 'nonExistFile' : 'existFile' }"/>" onclick="fnEditOverview('<c:out value="${av.opus}"/>')"  title="<c:out value="${av.overviewTxt}" escapeXml="true"/>">Overview</span>
							<span id="DEL-<c:out value="${av.opus}"/>" style="display:none;" class="bgSpan" onclick="fnDeleteOpus('<c:out value="${av.opus}"/>')" title="<c:out value="${av}" escapeXml="true"/>">Del</span>
						</dd>
					</dl>
				</div>
			</li>
			</c:forEach>
		</ul>
		</c:when>
		<c:when test="${avForm.listViewType eq 'sbox'}">
		<ul>
			<c:forEach items="${avForm.avlist}" var="av">
			<li id="<c:out value="${av.opus}"/>" class="sboxLI">
				<div class="opusSBoxDiv">
					<span class="bgSpan" id="titleSpan"><c:out value="${av.title}"/></span>
					<span class="bgSpan" id="studioSpan"  onclick="fnStudioSearch('<c:out value="${av.studio}"/>')"><c:out value="${av.studio}"/></span>
					<span class="bgSpan" id="opusSpan"><c:out value="${av.opus}"/></span>
					<c:forEach items="${av.actressList}" var="actress">
					<span class="bgSpan" id="actressSpan" onclick="fnActressSearch('<c:out value="${actress}"/>')"><c:out value="${actress}"/></span>
					</c:forEach>
					<span class="bgSpan <c:out value="${av.video eq null || fn:length(av.video) eq 0 ? 'nonExistFile' : 'existFile' }"/>" onclick="fnPlay('<c:out value="${av.opus}"/>')"          title="<c:out value="${av.videoPath}" escapeXml="true"/>">V</span>
					<span class="bgSpan <c:out value="${av.cover eq null ? 'nonExistFile' : 'existFile' }"/>" onclick="fnImageView('<c:out value="${av.opus}"/>')"     title="<c:out value="${av.cover}" escapeXml="true"/>">C</span>
					<span class="bgSpan <c:out value="${av.subtitles eq null || fn:length(av.subtitles) eq 0 ? 'nonExistFile' : 'existFile' }"/>" onclick="fnEditSubtitles('<c:out value="${av.opus}"/>')" title="<c:out value="${av.subtitles}" escapeXml="true"/>">s</span>
					<span class="bgSpan <c:out value="${av.overview eq null ? 'nonExistFile' : 'existFile' }"/>" onclick="fnEditOverview('<c:out value="${av.opus}"/>')"  title="<c:out value="${av.overviewTxt}" escapeXml="true"/>">O</span>
					<span id="DEL-<c:out value="${av.opus}"/>" style="display:none;" class="bgSpan" onclick="fnDeleteOpus('<c:out value="${av.opus}"/>')" title="<c:out value="${av}" escapeXml="true"/>">Del</span>
				</div>
			</li>
			</c:forEach>
		</ul>
		</c:when>
		<c:when test="${avForm.listViewType eq 'table'}">
		<table class="listTable">
			<tr>
				<th>Studio</th>
				<th>Opus</th>
				<th>Title</th>
				<th>Actress</th>
				<th>Info</th>
			</tr>
			<c:forEach items="${avForm.avlist}" var="av">
			<tr>
				<td><span class="" id="studioSpan"  onclick="fnStudioSearch('<c:out value="${av.studio}"/>')"><c:out value="${av.studio}"/></span></td>		
				<td><span class="" id="opusSpan"><c:out value="${av.opus}"/></span></td>		
				<td><span class="" id="titleSpan"><c:out value="${av.title}"/></span></td>		
				<td><c:forEach items="${av.actressList}" var="actress">
					<span class="" id="actressSpan" onclick="fnActressSearch('<c:out value="${actress}"/>')"><c:out value="${actress}"/></span>
					</c:forEach></td>	
				<td>
					<span class="bgSpan <c:out value="${av.video eq null || fn:length(av.video) eq 0 ? 'nonExistFile' : 'existFile' }"/>" onclick="fnPlay('<c:out value="${av.opus}"/>')"          title="<c:out value="${av.videoPath}" escapeXml="true"/>">V</span>
					<span class="bgSpan <c:out value="${av.cover eq null ? 'nonExistFile' : 'existFile' }"/>" onclick="fnImageView('<c:out value="${av.opus}"/>')"     title="<c:out value="${av.cover}" escapeXml="true"/>">C</span>
					<span class="bgSpan <c:out value="${av.subtitles eq null || fn:length(av.subtitles) eq 0 ? 'nonExistFile' : 'existFile' }"/>" onclick="fnEditSubtitles('<c:out value="${av.opus}"/>')" title="<c:out value="${av.subtitles}" escapeXml="true"/>">s</span>
					<span class="bgSpan <c:out value="${av.overview eq null ? 'nonExistFile' : 'existFile' }"/>" onclick="fnEditOverview('<c:out value="${av.opus}"/>')"  title="<c:out value="${av.overviewTxt}" escapeXml="true"/>">O</span>
			</tr>
			</c:forEach>
		</table>
		</c:when>
		<c:otherwise>
			<c:forEach items="${avForm.avlist}" var="av">
				<c:out value="${av.studio}"/> <c:out value="${av.opus}"/> <c:out value="${av.title}"/> 
			</c:forEach>		
		</c:otherwise>
	</c:choose>
</div>

<form name="playFrm" target="ifrm" action="<c:url value="/video/execProc.jsp"/>" method="post">
	<input type="hidden" name="selectedOpus" id="selectedOpus">
	<input type="hidden" name="selectedMode" id="selectedMode">
</form>
<iframe name="ifrm" style="display:none; width:100%;"></iframe>


</body>
</html>
