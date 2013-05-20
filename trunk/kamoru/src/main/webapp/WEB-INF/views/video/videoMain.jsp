<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/video/video-favicon.ico" />">
<title><spring:message code="title" text="video"/></title>
<link rel="stylesheet" href="<c:url value="/resources/video/video.css" />" />
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript">
var context = '<spring:url value="/"/>';
var opusArray = ${opusArray};
var bgImageCount = ${bgImageCount};
</script>
<script src="<c:url value="/resources/video/video.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/common.js" />"></script>
<script src="<c:url value="/resources/image-popup.js" />"></script>
</head>
<body>
<div id="headerDiv">
	<div id="searchDiv" class="boxDiv">
	<form:form method="GET" commandName="videoSearch">
		<span class="searchGroupSpan">
			<form:label path="studio" >Studio </form:label><form:input path="studio"  cssClass="schTxt"/>
			<form:label path="opus"   >Opus   </form:label><form:input path="opus"    cssClass="schTxt"/>
			<form:label path="title"  >Title  </form:label><form:input path="title"   cssClass="schTxt"/>
			<form:label path="actress">Actress</form:label><form:input path="actress" cssClass="schTxt"/>
			
			<span class="checkbox" id="checkbox-addCond"        title="Add additional conditions">Add</span><form:hidden path="addCond"/>
			<span class="checkbox" id="checkbox-existVideo"     title="exist Video?">V</span>			    <form:hidden path="existVideo"/>
			<span class="checkbox" id="checkbox-existSubtitles" title="exist Subtitles?">S</span>			<form:hidden path="existSubtitles"/>
			<span class="separatorSpan">|</span>
			<span class="checkbox" id="checkbox-neverPlay" title="unseen video?">P</span>			<form:hidden path="neverPlay"/>
			<span class="separatorSpan">|</span>
			<span class="button" onclick="fnDetailSearch()"><spring:message code="btn.search" text="Search video"/></span>
			<span class="separatorSpan">|</span>
			<span class="button" onclick="fnRandomPlay()"><spring:message code="btn.random" text="Random play"/></span>
		</span>
		<span class="viewGroupSpan">
			<c:forEach items="${views}" var="view">
				<span class="radio" id="radio-listViewType-${view}"  title="show ${view.desc} type list">${view}</span> 
			</c:forEach><form:hidden path="listViewType"/>
			
			<span class="separatorSpan">|</span>
			
			<c:forEach items="${sorts}" var="sort">
			<span class="radio" id="radio-sortMethod-${sort}" title="sort by ${sort.desc}">${sort}</span>
			</c:forEach><form:hidden path="sortMethod"/>
			<span class="checkbox" id="checkbox-sortReverse" title="reverse sort">R</span><form:hidden path="sortReverse"/>
			
			<span class="separatorSpan">|</span>
			
			<span class="checkbox" id="checkbox-viewStudioDiv"  title="view Studio panel"  onclick="fnStudioDivToggle()">S</span>	<form:hidden path="viewStudioDiv"/> 
			<span class="checkbox" id="checkbox-viewActressDiv" title="view Actress panel" onclick="fnActressDivToggle()">A</span>	<form:hidden path="viewActressDiv"/>
		</span>
	</form:form>
	</div>
	<div id="studioDiv" class="boxDiv">
	<c:forEach var="studio" items="${studioList}"><c:set value="${fn:length(studio.videoList)}" var="countByStudio" />
		<span onclick="fnStudioSearch('${studio.name}')" class="studioSpanBtn ${countByStudio > 9 ? 'spanSize10' : countByStudio > 4 ? 'spanSize5' : ''}" title="${studio.homepage} ${studio.companyName} Actress:${fn:length(studio.actressList)}">${studio.name}(${countByStudio})</span>
	</c:forEach>
	</div>
	<div id="actressDiv" class="boxDiv">
	<c:forEach items="${actressList}" var="actress"><c:set value="${fn:length(actress.videoList)}" var="countByActress" />
		<span onclick="fnActressSearch('${actress.name}')" class="actressSpanBtn ${countByActress > 9 ? 'spanSize10' : countByActress > 4 ? 'spanSize5' : ''}" >${actress.name}(${countByActress})</span>
	</c:forEach>
	</div>
</div>

<div id="contentDiv" class="boxDiv" onclick="fnBGImageView();">
	<span id="totalCount">Total <c:out value="${fn:length(videoList)}"/></span><span id="debug"></span><span id="bgimg" onclick="fnBGImageView();">BG</span>
	<c:choose>
		<c:when test="${videoSearch.listViewType eq 'C' }">
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
									<dt><span class="studioSpan">${video.studio.name}</span>&nbsp;<span class="opusSpan">${video.opus}</span></dt>
									<dt>             
										<c:forEach items="${video.actressList}" var="actress">
										<span class="actressSpan" onclick="fnActressSearch('${actress.name}')">${actress.name}</span>
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
		<c:when test="${videoSearch.listViewType eq 'B'}">
		<ul>
			<c:forEach items="${videoList}" var="video" varStatus="status">
			<li id="<c:out value="${video.opus}"/>" class="boxLI">
				<div class="opusBoxDiv">     <%-- ${status.count} --%>             
					<dl style="background-image:url('<c:url value="/video/${video.existCoverFile ? video.opus : 'no'}/cover" />'); background-size:300px 200px; height:200px;">
						<dt><span class="bgSpan" id="titleSpan" onclick="fnVideoDetail('${video.opus}')"><c:out value="${video.title}"/></span></dt>
						<dd><span class="bgSpan" id="studioSpan"  onclick="fnStudioSearch('<c:out value="${video.studio.name}"/>')"><c:out value="${video.studio.name}"/></span></dd>
						<dd><span class="bgSpan" id="opusSpan"><c:out value="${video.opus}"/></span></dd>
						<dd>
							<c:forEach items="${video.actressList}" var="actress">
							<span class="bgSpan" id="actressSpan" onclick="fnActressSearch('<c:out value="${actress.name}"/>')"><c:out value="${actress.name}"/></span>
							</c:forEach>
						</dd>
						<dd><span class="bgSpan <c:out value="${video.existVideoFileList     ? 'existFile' : 'nonExistFile' }"/>" onclick="fnPlay('<c:out value="${video.opus}"/>')"          title="<c:out value="${video.videoFileListPath}" escapeXml="true"/>">Video (${video.playCount})</span></dd>
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
		<c:when test="${videoSearch.listViewType eq 'SB'}">
		<ul>
			<c:forEach items="${videoList}" var="video">
			<li id="<c:out value="${video.opus}"/>" class="sboxLI">
				<div class="opusSBoxDiv">
					<span class="bgSpan" id="titleSpan"><c:out value="${video.title}"/></span>
					<span class="bgSpan" id="studioSpan"  onclick="fnStudioSearch('<c:out value="${video.studio.name}"/>')"><c:out value="${video.studio.name}"/></span>
					<span class="bgSpan" id="opusSpan"><c:out value="${video.opus}"/></span>
					<c:forEach items="${video.actressList}" var="actress">
					<span class="bgSpan" id="actressSpan" onclick="fnActressSearch('<c:out value="${actress.name}"/>')"><c:out value="${actress.name}"/></span>
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
		<c:when test="${videoSearch.listViewType eq 'T'}">
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
				<td><span class="" id="studioSpan"  onclick="fnStudioSearch('<c:out value="${video.studio.name}"/>')"><c:out value="${video.studio.name}"/></span></td>		
				<td><span class="" id="opusSpan"><c:out value="${video.opus}"/></span></td>		
				<td><span class="" id="titleSpan"><c:out value="${video.title}"/></span></td>		
				<td><c:forEach items="${video.actressList}" var="actress">
					<span class="" id="actressSpan" onclick="fnActressSearch('<c:out value="${actress.name}"/>')"><c:out value="${actress.name}"/></span>
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
				<c:out value="${video.studio.name}"/> <c:out value="${video.opus}"/> <c:out value="${video.title}"/> 
			</c:forEach>		
		</c:otherwise>
	</c:choose>
</div>

<form name="actionFrm" target="ifrm" method="post"><input type="hidden" name="_method" id="hiddenHttpMethod"/></form>
<iframe id="actionIframe" name="ifrm" style="display:none; width:100%;"></iframe>

</body>
</html>