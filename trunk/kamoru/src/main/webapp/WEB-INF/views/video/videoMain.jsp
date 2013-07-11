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
<script src="<c:url value="/resources/video/videoMain.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/video/video.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/common.js" />"></script>
<script src="<c:url value="/resources/image-popup.js" />"></script>
</head>
<body>
<div id="headerDiv">
	<div id="searchDiv" class="boxDiv roundBorderL">
	<form:form method="GET" commandName="videoSearch">
		<span class="groupSpan roundBorderM">
			<%-- <form:label path="studio" >Studio </form:label><form:input path="studio"  cssClass="schTxt"/>
			<form:label path="opus"   >Opus   </form:label><form:input path="opus"    cssClass="schTxt"/>
			<form:label path="title"  >Title  </form:label><form:input path="title"   cssClass="schTxt"/>
			<form:label path="actress">Actress</form:label><form:input path="actress" cssClass="schTxt"/> --%>
			<form:label path="searchText"><spring:message code="msg.search" text="Search"/></form:label><form:input path="searchText" cssClass="searchInput"/>
			
			<span class="checkbox" id="checkbox-addCond" title="<spring:message code="msg.title.addCondition" text="Add additional conditions"/>">
				<spring:message code="msg.addCondition" text="Add"/>
			</span><form:hidden path="addCond"/>
			<span class="checkbox" id="checkbox-existVideo" title="<spring:message code="msg.title.existVideo" text="exist Video?"/>">V</span>			    
				<form:hidden path="existVideo"/>
			<span class="checkbox" id="checkbox-existSubtitles" title="<spring:message code="msg.title.existSubtitles" text="exist Subtitles?"/>">S</span>			
				<form:hidden path="existSubtitles"/>
			<span class="separatorSpan">|</span>
			<span class="checkbox" id="checkbox-neverPlay" title="<spring:message code="msg.title.unseenVideo" text="unseen video?"/>">P</span>			
				<form:hidden path="neverPlay"/>
			<span class="separatorSpan">|</span>
			<span class="button" onclick="fnDetailSearch()"><spring:message code="btn.search" text="Search video"/></span>
		</span>
		<span class="groupSpan roundBorderM">
			<c:forEach items="${views}" var="view">
				<span class="radio" id="radio-listViewType-${view}" title="<spring:message code="msg.title.showViewTypeList" text="show view type list" arguments="${view.desc}"/>">${view}</span> 
			</c:forEach><form:hidden path="listViewType"/>
		</span>
		<span class="groupSpan roundBorderM">
			<c:forEach items="${sorts}" var="sort">
				<span class="radio" id="radio-sortMethod-${sort}" title="<spring:message code="msg.title.sortBy" text="sort by this" arguments="${sort.desc}"/>">${sort}</span>
			</c:forEach><form:hidden path="sortMethod"/>
			<span class="checkbox" id="checkbox-sortReverse" title="<spring:message code="msg.title.reverseSort" text="reverse sort"/>">R</span><form:hidden path="sortReverse"/>
		</span>
		<span class="groupSpan roundBorderM">
			<span class="checkbox" id="checkbox-viewStudioDiv"  title="<spring:message code="msg.title.viewStudioPanel"  text="view Studio panel"/>"  onclick="fnStudioDivToggle()">S</span>	
				<form:hidden path="viewStudioDiv"/> 
			<span class="checkbox" id="checkbox-viewActressDiv" title="<spring:message code="msg.title.viewActressPanel" text="view Actress panel"/>" onclick="fnActressDivToggle()">A</span>	
				<form:hidden path="viewActressDiv"/>
		</span>
		<span class="groupSpan roundBorderM">
			<span class="button" onclick="fnRandomPlay()"><spring:message code="btn.random" text="Random play"/></span>
		</span>
	</form:form>
	</div>
	<div id="studioDiv" class="boxDiv roundBorderL">
	<c:forEach var="studio" items="${studioList}"><c:set value="${fn:length(studio.videoList)}" var="countByStudio" />
		<span onclick="fnSearchText('${studio.name}')" class="${countByStudio > 9 ? 'spanSize10' : countByStudio > 4 ? 'spanSize5' : ''}" 
			title="${studio.homepage} ${studio.companyName} Actress:${fn:length(studio.actressList)}">${studio.name}(${countByStudio})</span>
	</c:forEach>
	</div>
	<div id="actressDiv" class="boxDiv roundBorderL">
	<c:forEach items="${actressList}" var="actress"><c:set value="${fn:length(actress.videoList)}" var="countByActress" />
		<span onclick="fnSearchText('${actress.name}')" class="${countByActress > 9 ? 'spanSize10' : countByActress > 4 ? 'spanSize5' : ''}" >${actress.name}(${countByActress})</span>
	</c:forEach>
	</div>
</div>

<div id="contentDiv" class="boxDiv roundBorderL" ><!-- onclick="fnBGImageView();" -->
	<span id="totalCount">Total <c:out value="${fn:length(videoList)}"/></span><span id="debug"></span><span id="bgimg" onclick="fnBGImageView();">BG</span>
	<c:choose>
		<c:when test="${videoSearch.listViewType eq 'C' }">
		<ul>
			<c:forEach items="${videoList}" var="video">
			<li id="${video.opus}" class="boxLI roundBorderM">
				<div class="cardDiv roundBorderM">
					<table>
						<tr>
							<td height="20px" colspan="2"><div style="height:20px;" class="bgSpan" onclick="fnVideoDetail('${video.opus}')">${video.title}</div></td>
						</tr>
						<tr valign="top" height="160px">
							<td width="70px" class="cardBG" style="background-image:url('<c:url value="/video/${video.opus}/cover" />')">
								&nbsp;
							</td>
							<td>
								<dl>
									<dt><c:forEach items="${video.actressList}" var="actress">
										<span class="bgSpan" onclick="fnSearchText('${actress.name}')">${actress.name}</span>
										</c:forEach></dt>
									<dd><span class="bgSpan" onclick="fnSearchText('${video.studio.name}')">${video.studio.name}</span> 
										<span class="bgSpan">${video.opus}</span></dd>
									<dd><span class="bgSpan ${video.existVideoFileList ? 'existFile' : 'nonExistFile'}" onclick="fnPlay('${video.opus}')">Video (${video.playCount})</span>
										<span class="bgSpan ${video.existCoverFile ? 'existFile' : 'nonExistFile'}" onclick="fnImageView('${video.opus}')">Cover</span></dd>
									<dd><span class="bgSpan ${video.existSubtitlesFileList ? 'existFile' : 'nonExistFile'}" onclick="fnEditSubtitles('${video.opus}')">smi</span>
										<span class="bgSpan ${video.existOverview ? 'existFile' : 'nonExistFile'}" onclick="fnEditOverview('${video.opus}')" title="${video.overviewText}">Overview</span>
										<input type="range" id="Rank-${video.opus}" name="points" min="-5" max="5" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/>
									 	<span id="DEL-${video.opus}" style="display:none;" class="bgSpan" onclick="fnDeleteOpus('${video.opus}')">Del</span></dd>
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
			<li id="${video.opus}" class="boxLI roundBorderM">
				<div class="opusBoxDiv roundBorderM">     <!-- ${status.count} -->             
					<dl class="opusBoxDL roundBorderM" style="background-image:url('<c:url value="/video/${video.opus}/cover" />');">
						<dt><span class="bgSpan" onclick="fnVideoDetail('${video.opus}')">${video.title}</span></dt>
						<dd><span class="bgSpan" onclick="fnSearchText('${video.studio.name}')">${video.studio.name}</span></dd>
						<dd><span class="bgSpan">${video.opus}</span></dd>
						<dd>
							<c:forEach items="${video.actressList}" var="actress">
							<span class="bgSpan" onclick="fnSearchText('${actress.name}')">${actress.name}</span>
							</c:forEach>
						</dd>
						<dd><span class="bgSpan ${video.existVideoFileList ? 'existFile' : 'nonExistFile'}" onclick="fnPlay('${video.opus}')">Video (${video.playCount})</span></dd>
						<dd><span class="bgSpan ${video.existCoverFile ? 'existFile' : 'nonExistFile'}" onclick="fnImageView('${video.opus}')">Cover</span></dd>
						<dd><span class="bgSpan ${video.existSubtitlesFileList ? 'existFile' : 'nonExistFile'}" onclick="fnEditSubtitles('${video.opus}')">smi</span></dd>
						<dd><span class="bgSpan ${video.existOverview ? 'existFile' : 'nonExistFile'}" onclick="fnEditOverview('${video.opus}')" title="${video.overviewText}">Overview</span>
							<input type="range" id="Rank-${video.opus}" name="points" min="-5" max="5" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/>	
							<span id="DEL-${video.opus}" style="display:none;" class="bgSpan" onclick="fnDeleteOpus('${video.opus}')">Del</span>
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
			<li id="${video.opus}" class="boxLI roundBorderM">
				<div class="opusSBoxDiv roundBorderM">
					<span class="bgSpan" onclick="fnVideoDetail('${video.opus}')">${video.title}</span>
					<span class="bgSpan" onclick="fnSearchText('${video.studio.name}')">${video.studio.name}</span>
					<span class="bgSpan">${video.opus}</span>
					<c:forEach items="${video.actressList}" var="actress">
					<span class="bgSpan" onclick="fnSearchText('${actress.name}')">${actress.name}</span>
					</c:forEach>
					<span class="bgSpan ${video.existVideoFileList ? 'existFile' : 'nonExistFile'}" onclick="fnPlay('${video.opus}')">V</span>
					<span class="bgSpan ${video.existCoverFile ? 'existFile' : 'nonExistFile'}" onclick="fnImageView('${video.opus}')">C</span>
					<span class="bgSpan ${video.existSubtitlesFileList ? 'existFile' : 'nonExistFile'}" onclick="fnEditSubtitles('${video.opus}')">s</span>
					<span class="bgSpan ${video.existOverview ? 'existFile' : 'nonExistFile'}" onclick="fnEditOverview('${video.opus}')" title="${video.overviewText}">O</span>
					<input type="range" id="Rank-${video.opus}" name="points" min="-5" max="5" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/>
					<span id="DEL-${video.opus}" style="display:none;" class="bgSpan" onclick="fnDeleteOpus('${video.opus}')">Del</span>
				</div>
			</li>
			</c:forEach>
		</ul>
		</c:when>
		<c:when test="${videoSearch.listViewType eq 'T'}">
		<table class="listTable roundBorderL">
			<tr>
				<th>Studio</th><th>Opus</th><th>Title</th><th>Actress</th><th>Info</th>
			</tr>
			<c:forEach items="${videoList}" var="video">
			<tr>
				<td><span class="bgSpan" onclick="fnSearchText('${video.studio.name}')">${video.studio.name}</span></td>		
				<td><span class="bgSpan">${video.opus}</span></td>		
				<td><span class="bgSpan" onclick="fnVideoDetail('${video.opus}')">${video.title}</span></td>		
				<td><c:forEach items="${video.actressList}" var="actress">
					<span class="bgSpan" onclick="fnSearchText('${actress.name}')">${actress.name}</span>
					</c:forEach></td>	
				<td>
					<span class="bgSpan ${video.existVideoFileList ? 'existFile' : 'nonExistFile'}" onclick="fnPlay('${video.opus}')">V</span>
					<span class="bgSpan ${video.existCoverFile ? 'existFile' : 'nonExistFile'}" onclick="fnImageView('${video.opus}')">C</span>
					<span class="bgSpan ${video.existSubtitlesFileList ? 'existFile' : 'nonExistFile'}" onclick="fnEditSubtitles('${video.opus}')">s</span>
					<span class="bgSpan ${video.existOverview ? 'existFile' : 'nonExistFile'}" onclick="fnEditOverview('${video.opus}')" title="${video.overviewText}">O</span>
					<input type="range" id="Rank-${video.opus}" name="points" min="-5" max="5" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/>
					<span id="DEL-${video.opus}" class="bgSpan" onclick="fnDeleteOpus('${video.opus}')">Del</span>
				</td>
			</tr>
			</c:forEach>
		</table>
		</c:when>
		<c:otherwise>
		<ol>
			<c:forEach items="${videoList}" var="video">
			<li>${video.studio.name} ${video.opus} ${video.title} 
			</c:forEach>
		</ol>		
		</c:otherwise>
	</c:choose>
</div>

<form name="actionFrm" target="ifrm" method="post"><input type="hidden" name="_method" id="hiddenHttpMethod"/></form>
<iframe id="actionIframe" name="ifrm" style="display:none; width:100%;"></iframe>

</body>
</html>