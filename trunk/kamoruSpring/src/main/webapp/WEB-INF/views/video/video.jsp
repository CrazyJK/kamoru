<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/favicon_video.ico" />">
<title>Video collection</title>
<link rel="stylesheet" href="<c:url value="/resources/video.css" />" />
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
var context = '<spring:url value="/"/>';
var opusArray = ${opusArray};
</script>
<script src="<c:url value="/resources/video.js" />" type="text/javascript"></script>
</head>
<body>
<div id="headerDiv">
	<div id="searchDiv" class="boxDiv">
	<form>
		<span class="searchGroupSpan">
			<label for="studio"> Studio	 </label><input type="text" name="studio"  id="studio"  value="<c:out value="${params['studio']}"/>"  class="schTxt">
			<label for="opus">   Opus  	 </label><input type="text" name="opus"    id="opus"    value="<c:out value="${params['opus']}"/>"    class="schTxt">
			<label for="title">  Title 	 </label><input type="text" name="title"   id="title"   value="<c:out value="${params['title']}"/>"   class="schTxt">
			<label for="actress">Actress </label><input type="text" name="actress" id="actress" value="<c:out value="${params['actress']}"/>" class="schTxt">
			<span class="checkbox" id="checkbox-addCond"        title="Add additional conditions">Add</span><input type="hidden" name="addCond"    	   id="addCond"    	   value="<c:out value="${params['addCond']}"/>">
			<span class="checkbox" id="checkbox-existVideo"     title="exist Video?">V</span>			    <input type="hidden" name="existVideo" 	   id="existVideo" 	   value="<c:out value="${params['existVideo']}"/>">
			<span class="checkbox" id="checkbox-existSubtitles" title="exist Subtitles?">S</span>			<input type="hidden" name="existSubtitles" id="existSubtitles" value="<c:out value="${params['existSubtitles']}"/>">
			<span class="separatorSpan">|</span>
			<span class="button" onclick="fnDetailSearch()">Search</span>
			<span class="separatorSpan">|</span>
			<span class="button" onclick="fnRandomPlay()">Random</span>
		</span>
		<span class="viewGroupSpan">
			<span class="radio" id="radio-listViewType-card"  title="show card type list">C</span> 
			<span class="radio" id="radio-listViewType-box"   title="show box type list">B</span> 
			<span class="radio" id="radio-listViewType-sbox"  title="show small box type list">SB</span>
			<span class="radio" id="radio-listViewType-table" title="show table type list">T</span>			<input type="hidden" name="listViewType" id="listViewType" value="<c:out value="${params['listViewType']}"/>">
			<span class="separatorSpan">|</span>
			<span class="radio" id="radio-sortMethod-S" title="sort by studio">S</span>
			<span class="radio" id="radio-sortMethod-O" title="sort by opus">O</span>
			<span class="radio" id="radio-sortMethod-T" title="sort by title">T</span>
			<span class="radio" id="radio-sortMethod-A" title="sort by actress">A</span>
			<span class="radio" id="radio-sortMethod-L" title="sort by lastmodified">M</span>				<input type="hidden" name="sortMethod"  id="sortMethod"  value="<c:out value="${params['sortMethod']}"/>">
			<span class="checkbox" id="checkbox-sortReverse" title="reverse sort">R</span>					<input type="hidden" name="sortReverse" id="sortReverse" value="<c:out value="${params['sortReverse']}"/>">
			<span class="separatorSpan">|</span>
			<span class="checkbox" id="checkbox-viewStudioDiv"  title="view Studio panel"  onclick="fnStudioDivToggle()">S</span>	<input type="hidden" name="viewStudioDiv"  id="viewStudioDiv"  value="<c:out value="${params['viewStudioDiv']}"/>"> 
			<span class="checkbox" id="checkbox-viewActressDiv" title="view Actress panel" onclick="fnActressDivToggle()">A</span>	<input type="hidden" name="viewActressDiv" id="viewActressDiv" value="<c:out value="${params['viewActressDiv']}"/>">
			<span class="separatorSpan">|</span>
			<span class="checkbox" id="checkbox-useCacheData" title="use cache data">C</span>				<input type="hidden" name="useCacheData" id="useCacheData" value="<c:out value="${params['useCacheData']}"/>">
		</span>
	</form>
	</div>
	<div id="studioDiv" class="boxDiv">
	<c:forEach var="studio" items="${studioMap}">
		<span onclick="fnStudioSearch('<c:out value="${studio.key}"/>')" class="studioSpanBtn"><c:out value="${studio.key}"/>(<c:out value="${studio.value}"/>)</span>
	</c:forEach>
	</div>
	<div id="actressDiv" class="boxDiv">
	<c:forEach items="${actressMap}" var="actress">
		<span onclick="fnActressSearch('<c:out value="${actress.key}"/>')" class="actressSpanBtn"><c:out value="${actress.key}"/>(<c:out value="${actress.value}"/>)</span>
	</c:forEach>
	</div>
</div>

<div id="contentDiv" class="boxDiv" style="background-image:url('<c:url value="/video/bgimage" />')">
	<span id="totalCount">Total <c:out value="${fn:length(videoList)}"/></span><span id="debug"></span><span id="bgimg" onclick="fnBGImageView();">BG</span>
	<c:choose>
		<c:when test="${params['listViewType'] eq 'card' }">
		<ul>
			<c:forEach items="${videoList}" var="video">
			<li id="${video.opus}" class="boxLI">
				<div class="opusBoxDiv">
					<table>
						<tr>
							<td  colspan="2"><span class="titleSpan">${video.title}</span></td>
						</tr>
						<tr valign="top">
							<td width="110px">
								<img src="<c:url value="/video/${video.opus}/cover" />" height="120px" onclick="fnImageView('${video.opus}')"/>
							</td>
							<td>
								<dl>
									<dt><span class="studioSpan">${video.studio}</span>&nbsp;<span class="opusSpan">${video.opus}</span></dt>
									<dt>             
										<c:forEach items="${video.actressList}" var="actress">
										<span class="actressSpan" onclick="fnActressSearch('${actress}')">${actress}</span>
										</c:forEach>
									</dt>
									<dd> 
										<span class="" onclick="fnPlay('${video.opus}')"          title="${video.videoFileListPath}">Video</span>
										<span class="" onclick="fnImageView('${video.opus}')"     title="${video.coverFile}">Cover</span>
										<span class="" onclick="fnEditSubtitles('${video.opus}')" title="${video.subtitlesFileList}">smi</span>
										<span class="" onclick="fnEditOverview('${video.opus}')"  title="${video.overviewText}">Overview</span>
									</dd>
									<dd>
										<span id="DEL-${video.opus}" style="display:none;" class="bgSpan" onclick="fnDeleteOpus('${video.opus}')" title="${video}">Del</span>
									</dd>
								</dl>
							</td>
						</tr>
					</table>
				</div>
			</li>
			</c:forEach>
		</ul>
		</c:when>
		<c:when test="${params['listViewType'] eq 'box'}">
		<ul>
			<c:forEach items="${videoList}" var="video">
			<li id="<c:out value="${video.opus}"/>" class="boxLI">
				<div class="opusBoxDiv">                   
					<dl style="background-image:url('<c:url value="/video/${video.opus}/cover" />'); background-size:300px 200px; height:200px;">
						<dt><span class="bgSpan" id="titleSpan" onclick="fnVideoDetail('${video.opus}')"><c:out value="${video.title}"/></span></dt>
						<dd><span class="bgSpan" id="studioSpan"  onclick="fnStudioSearch('<c:out value="${video.studio}"/>')"><c:out value="${video.studio}"/></span></dd>
						<dd><span class="bgSpan" id="opusSpan"><c:out value="${video.opus}"/></span></dd>
						<dd>
							<c:forEach items="${video.actressList}" var="actress">
							<span class="bgSpan" id="actressSpan" onclick="fnActressSearch('<c:out value="${actress}"/>')"><c:out value="${actress}"/></span>
							</c:forEach>
						</dd>
						<dd><span class="bgSpan <c:out value="${video.existVideoFileList     ? 'existFile' : 'nonExistFile' }"/>" onclick="fnPlay('<c:out value="${video.opus}"/>')"          title="<c:out value="${video.videoFileListPath}" escapeXml="true"/>">Video</span></dd>
						<dd><span class="bgSpan <c:out value="${video.existCoverFile         ? 'existFile' : 'nonExistFile' }"/>" onclick="fnImageView('<c:out value="${video.opus}"/>')"     title="<c:out value="${video.coverFile}" escapeXml="true"/>">Cover</span></dd>
						<dd><span class="bgSpan <c:out value="${video.existSubtitlesFileList ? 'existFile' : 'nonExistFile'}"/>" onclick="fnEditSubtitles('<c:out value="${video.opus}"/>')" title="<c:out value="${video.subtitlesFileList}" escapeXml="true"/>">smi</span></dd>
						<dd><span class="bgSpan <c:out value="${video.existOverviewFile      ? 'existFile' : 'nonExistFile' }"/>" onclick="fnEditOverview('<c:out value="${video.opus}"/>')"  title="<c:out value="${video.overviewText}" escapeXml="true"/>">Overview</span>
							<span id="DEL-<c:out value="${video.opus}"/>" style="display:none;" class="bgSpan" onclick="fnDeleteOpus('<c:out value="${video.opus}"/>')" title="<c:out value="${video}" escapeXml="true"/>">Del</span>
						</dd>
					</dl>
				</div>
			</li>
			</c:forEach>
		</ul>
		</c:when>
		<c:when test="${params['listViewType'] eq 'sbox'}">
		<ul>
			<c:forEach items="${videoList}" var="video">
			<li id="<c:out value="${video.opus}"/>" class="sboxLI">
				<div class="opusSBoxDiv">
					<span class="bgSpan" id="titleSpan"><c:out value="${video.title}"/></span>
					<span class="bgSpan" id="studioSpan"  onclick="fnStudioSearch('<c:out value="${video.studio}"/>')"><c:out value="${video.studio}"/></span>
					<span class="bgSpan" id="opusSpan"><c:out value="${video.opus}"/></span>
					<c:forEach items="${video.actressList}" var="actress">
					<span class="bgSpan" id="actressSpan" onclick="fnActressSearch('<c:out value="${actress}"/>')"><c:out value="${actress}"/></span>
					</c:forEach>
					<span class="bgSpan <c:out value="${video.existVideoFileList     ? 'existFile' : 'nonExistFile' }"/>" onclick="fnPlay('<c:out value="${video.opus}"/>')"          title="<c:out value="${video.videoFileListPath}" escapeXml="true"/>">V</span>
					<span class="bgSpan <c:out value="${video.existCoverFile         ? 'existFile' : 'nonExistFile' }"/>" onclick="fnImageView('<c:out value="${video.opus}"/>')"     title="<c:out value="${video.coverFile}" escapeXml="true"/>">C</span>
					<span class="bgSpan <c:out value="${video.existSubtitlesFileList ? 'existFile' : 'nonExistFile' }"/>" onclick="fnEditSubtitles('<c:out value="${video.opus}"/>')" title="<c:out value="${video.subtitlesFileList}" escapeXml="true"/>">s</span>
					<span class="bgSpan <c:out value="${video.existOverviewFile      ? 'existFile' : 'nonExistFile' }"/>" onclick="fnEditOverview('<c:out value="${video.opus}"/>')"  title="<c:out value="${video.overviewText}" escapeXml="true"/>">O</span>
					<span id="DEL-<c:out value="${video.opus}"/>" style="display:none;" class="bgSpan" onclick="fnDeleteOpus('<c:out value="${video.opus}"/>')" title="<c:out value="${video}" escapeXml="true"/>">Del</span>
				</div>
			</li>
			</c:forEach>
		</ul>
		</c:when>
		<c:when test="${params['listViewType'] eq 'table'}">
		<table class="listTable">
			<tr>
				<th>Studio</th>
				<th>Opus</th>
				<th>Title</th>
				<th>Actress</th>
				<th>Info</th>
			</tr>
			<c:forEach items="${videoList}" var="video">
			<tr>
				<td><span class="" id="studioSpan"  onclick="fnStudioSearch('<c:out value="${video.studio}"/>')"><c:out value="${video.studio}"/></span></td>		
				<td><span class="" id="opusSpan"><c:out value="${video.opus}"/></span></td>		
				<td><span class="" id="titleSpan"><c:out value="${video.title}"/></span></td>		
				<td><c:forEach items="${video.actressList}" var="actress">
					<span class="" id="actressSpan" onclick="fnActressSearch('<c:out value="${actress}"/>')"><c:out value="${actress}"/></span>
					</c:forEach></td>	
				<td>
					<span class="bgSpan <c:out value="${video.existVideoFileList     ? 'existFile' : 'nonExistFile' }"/>" onclick="fnPlay('<c:out value="${video.opus}"/>')"          title="<c:out value="${video.videoFileListPath}" escapeXml="true"/>">V</span>
					<span class="bgSpan <c:out value="${video.existCoverFile         ? 'existFile' : 'nonExistFile' }"/>" onclick="fnImageView('<c:out value="${video.opus}"/>')"     title="<c:out value="${video.coverFile}" escapeXml="true"/>">C</span>
					<span class="bgSpan <c:out value="${video.existSubtitlesFileList ? 'existFile' : 'nonExistFile' }"/>" onclick="fnEditSubtitles('<c:out value="${video.opus}"/>')" title="<c:out value="${video.subtitlesFileList}" escapeXml="true"/>">s</span>
					<span class="bgSpan <c:out value="${video.existOverviewFile      ? 'existFile' : 'nonExistFile' }"/>" onclick="fnEditOverview('<c:out value="${video.opus}"/>')"  title="<c:out value="${video.overviewText}" escapeXml="true"/>">O</span>
			</tr>
			</c:forEach>
		</table>
		</c:when>
		<c:otherwise>
			<c:forEach items="${videoList}" var="video">
				<c:out value="${video.studio}"/> <c:out value="${video.opus}"/> <c:out value="${video.title}"/> 
			</c:forEach>		
		</c:otherwise>
	</c:choose>
</div>

<form name="actionFrm" target="ifrm" method="post"><input type="hidden" name="_method" id="hiddenHttpMethod"/></form>
<iframe id="actionIframe" name="ifrm" style="display:none; width:100%;"></iframe>

</body>
</html>