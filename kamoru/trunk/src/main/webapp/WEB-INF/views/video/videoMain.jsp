<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s"      uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<title><s:message code="title" text="video"/></title>
<script type="text/javascript">
var opusArray = ${opusArray};
var bgImageCount = ${bgImageCount};
var totalVideoSize = parseInt('${fn:length(videoList)}');
var currentVideoIndex = getRandomInteger(1, totalVideoSize);
var listViewType = '${videoSearch.listViewType}';
</script>
<script src="<c:url value="/resources/video/videoMain.js" />" type="text/javascript"></script>
<script src="http://slidesjs.com/examples/standard/js/jquery.slides.min.js"></script>
<script src="http://vjs.zencdn.net/c/video.js"></script>
<link  href="http://vjs.zencdn.net/c/video-js.css" rel="stylesheet" />
</head>
<body>
<div id="headerDiv">
	<div id="searchDiv" class="div-box">
	<form:form method="GET" commandName="videoSearch">
		<span class="group">
			<%-- <form:label path="studio" >Studio </form:label><form:input path="studio"  cssClass="schTxt"/>
			<form:label path="opus"   >Opus   </form:label><form:input path="opus"    cssClass="schTxt"/>
			<form:label path="title"  >Title  </form:label><form:input path="title"   cssClass="schTxt"/>
			<form:label path="actress">Actress</form:label><form:input path="actress" cssClass="schTxt"/> --%>
			<form:label path="searchText"><s:message code="msg.search" text="Search"/></form:label>
			<form:input path="searchText" cssClass="searchInput" placeHolder="Search"/>
			
			<span class="checkbox" id="checkbox-addCond" title="<s:message code="msg.title.addCondition" text="Add additional conditions"/>">
				<s:message code="msg.addCondition" text="Add"/>
			</span><form:hidden path="addCond"/>
			<span class="checkbox" id="checkbox-existVideo" title="<s:message code="msg.title.existVideo" text="exist Video?"/>">V</span>			    
				<form:hidden path="existVideo"/>
			<span class="checkbox" id="checkbox-existSubtitles" title="<s:message code="msg.title.existSubtitles" text="exist Subtitles?"/>">S</span>			
				<form:hidden path="existSubtitles"/>
			<span class="separator">|</span>
			<span class="checkbox" id="checkbox-neverPlay" title="<s:message code="msg.title.unseenVideo" text="unseen video?"/>">P</span>			
				<form:hidden path="neverPlay"/>
			<span class="checkbox" id="checkbox-zeroRank" title="<s:message code="msg.title.zeroRankVideo" text="0 Rank video?"/>">R</span>			
				<form:hidden path="zeroRank"/>
			<span class="checkbox" id="checkbox-oldVideo" title="<s:message code="msg.title.oldVideo" text="old video?"/>">O</span>
				<form:hidden path="oldVideo"/>
			<span class="separator">|</span>
			<span class="button" onclick="fnSearch()"><s:message code="btn.search" text="Search video"/>
				(<c:out value="${fn:length(videoList)}"/>)
			</span>
		</span>
		<span class="group">
			<c:forEach items="${views}" var="view">
				<span class="radio" id="radio-listViewType-${view}" title="<s:message code="msg.title.showViewTypeList" text="show view type list" arguments="${view.desc}"/>">${view}</span> 
			</c:forEach><form:hidden path="listViewType"/>
		</span>
		<span class="group">
			<c:forEach items="${sorts}" var="sort">
				<span class="radio" id="radio-sortMethod-${sort}" title="<s:message code="msg.title.sortBy" text="sort by this" arguments="${sort.desc}"/>">${sort}</span>
			</c:forEach><form:hidden path="sortMethod"/>
			<span class="separator">|</span>
			<span class="checkbox" id="checkbox-sortReverse" title="<s:message code="msg.title.reverseSort" text="reverse sort"/>">R</span><form:hidden path="sortReverse"/>
		</span>
		<span class="group">
			<span class="checkbox" id="checkbox-viewStudioDiv"  title="<s:message code="msg.title.viewStudioPanel"  text="view Studio panel"/>"  onclick="fnStudioDivToggle()">S</span>	
				<form:hidden path="viewStudioDiv"/> 
			<span class="checkbox" id="checkbox-viewActressDiv" title="<s:message code="msg.title.viewActressPanel" text="view Actress panel"/>" onclick="fnActressDivToggle()">A</span>	
				<form:hidden path="viewActressDiv"/>
		</span>
		<span class="group">
			<span class="button" onclick="fnRandomPlay()"><s:message code="btn.random" text="Random play"/></span>
		</span>
		<span class="group">
			<span class="button" onclick="fnBGImageView();"><s:message code="btn.bgimage" text="Show bg-image"/></span>
		</span>
		<span id="debug" style="display:none"></span>
	</form:form>
	</div>
	<div id="studioDiv" class="div-box">[${fn:length(studioList)}]
	<c:forEach var="studio" items="${studioList}"><c:set value="${fn:length(studio.videoList)}" var="countByStudio" />
		<span onclick="fnSearch('${studio.name}')" class="${countByStudio > 9 ? 'item10' : countByStudio > 4 ? 'item5' : 'item1'}" 
			title="${studio.homepage} ${studio.companyName} Actress:${fn:length(studio.actressList)}">${studio.name}(${countByStudio})</span>
	</c:forEach>
	</div>
	<div id="actressDiv" class="div-box">[${fn:length(actressList)}]
	<c:forEach items="${actressList}" var="actress"><c:set value="${fn:length(actress.videoList)}" var="countByActress" />
		<span onclick="fnSearch('${actress.name}')" class="${countByActress > 9 ? 'item10' : countByActress > 4 ? 'item5' : 'item1'}" >${actress.name}(${countByActress})</span>
	</c:forEach>
	</div>
</div>

<div id="contentDiv" class="div-box" ><!-- onclick="fnBGImageView();" -->
<c:choose>
	<c:when test="${videoSearch.listViewType eq 'C' }">
	<ul>
		<c:forEach items="${videoList}" var="video">
		<li id="opus-${video.opus}" class="li-box">
			<div class="video-card">
				<table>
					<tr>
						<td colspan="2"><span style="height:16px" class="label" onclick="fnVideoDetail('${video.opus}')" title="${video.title}">${video.title}</span></td>
					</tr>
					<tr valign="top" height="120px">
						<td width="70px" class="video-card-bg" style="background-image:url('<c:url value="/video/${video.opus}/cover" />')">
							&nbsp;
						</td>
						<td>
							<dl>
								<dd><c:forEach items="${video.actressList}" var="actress" varStatus="status">
									<span class="label" onclick="fnSearch('${actress.name}')">${actress.name}</span>
									<img src="<c:url value="/resources/magnify${status.count%2}.png"/>" onclick="fnViewActressDetail('${actress.name}')" width="12px">
									</c:forEach></dd>
								<dd><span class="label" onclick="fnSearch('${video.studio.name}')">${video.studio.name}</span>
									<img src="<c:url value="/resources/link.png"/>" onclick="fnViewStudioDetail('${video.studio.name}')"></dd> 
								<dd><span class="label">${video.opus}</span></dd>
								<dd><span class="label ${video.existVideoFileList ? 'exist' : 'nonExist'}" onclick="fnPlay('${video.opus}')" title="play ${video.playCount}">V</span>
									<span class="label ${video.existCoverFile ? 'exist' : 'nonExist'}" onclick="fnImageView('${video.opus}')">C</span>
									<span class="label ${video.existSubtitlesFileList ? 'exist' : 'nonExist'}" onclick="fnEditSubtitles('${video.opus}')">s</span>
									<span class="label ${video.existOverview ? 'exist' : 'nonExist'}" onclick="fnEditOverview('${video.opus}')" title="${video.overviewText}">O</span>
									<span id="DEL-opus-${video.opus}" style="display:none;" class="label" onclick="fnDeleteOpus('${video.opus}')">D</span></dd>
								<dd><input type="range" id="Rank-${video.opus}" name="points" min="<s:eval expression="@videoProp['minRank']"/>" max="<s:eval expression="@videoProp['maxRank']"/>" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/></dd>
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
		<li id="opus-${video.opus}" class="li-box">
			<div class="video-box">     <!-- ${status.count} -->             
				<dl class="video-box-bg" style="background-image:url('<c:url value="/video/${video.opus}/cover" />');">
					<dt><span class="label" onclick="fnVideoDetail('${video.opus}')">${video.title}</span></dt>
					<dd><span class="label" onclick="fnSearch('${video.studio.name}')">${video.studio.name}</span>
						<img src="<c:url value="/resources/link.png"/>" onclick="fnViewStudioDetail('${video.studio.name}')"></dd>
					<dd><span class="label">${video.opus}</span></dd>
					<dd>
						<c:forEach items="${video.actressList}" var="actress" varStatus="status">
						<span class="label" onclick="fnSearch('${actress.name}')">${actress.name}</span>
						<img src="<c:url value="/resources/magnify${status.count%2}.png"/>" onclick="fnViewActressDetail('${actress.name}')" width="12px">
						</c:forEach>
					</dd>
					<dd><span class="label ${video.existVideoFileList ? 'exist' : 'nonExist'}" onclick="fnPlay('${video.opus}')">Video (${video.playCount})</span></dd>
					<dd><span class="label ${video.existCoverFile ? 'exist' : 'nonExist'}" onclick="fnImageView('${video.opus}')">Cover</span></dd>
					<dd><span class="label ${video.existSubtitlesFileList ? 'exist' : 'nonExist'}" onclick="fnEditSubtitles('${video.opus}')">smi</span></dd>
					<dd><span class="label ${video.existOverview ? 'exist' : 'nonExist'}" onclick="fnEditOverview('${video.opus}')" title="${video.overviewText}">Overview</span>
						<input type="range" id="Rank-${video.opus}" name="points" min="<s:eval expression="@videoProp['minRank']"/>" max="<s:eval expression="@videoProp['maxRank']"/>" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/></dd>	
					<dd id="DEL-opus-${video.opus}" style="display:none;"><span class="label" onclick="fnDeleteOpus('${video.opus}')">Del</span></dd>
				</dl>
			</div>
		</li>
		</c:forEach>
	</ul>
	</c:when>
	<c:when test="${videoSearch.listViewType eq 'SB'}">
	<ul>
		<c:forEach items="${videoList}" var="video" varStatus="status">
		<li id="opus-${video.opus}" class="li-box">
			<div class="video-sbox">
				<span class="label" onclick="fnVideoDetail('${video.opus}')">${video.title}</span>
				<span class="label" onclick="fnSearch('${video.studio.name}')">${video.studio.name}</span>
				<img src="<c:url value="/resources/link.png"/>" onclick="fnViewStudioDetail('${video.studio.name}')">
				<span class="label">${video.opus}</span>
				<c:forEach items="${video.actressList}" var="actress">
				<span class="label" onclick="fnSearch('${actress.name}')">${actress.name}</span>
				<img src="<c:url value="/resources/magnify${status.count%2}.png"/>" onclick="fnViewActressDetail('${actress.name}')" width="12px">
				</c:forEach>
				<span class="label ${video.existVideoFileList ? 'exist' : 'nonExist'}" onclick="fnPlay('${video.opus}')">V</span>
				<span class="label ${video.existCoverFile ? 'exist' : 'nonExist'}" onclick="fnImageView('${video.opus}')">C</span>
				<span class="label ${video.existSubtitlesFileList ? 'exist' : 'nonExist'}" onclick="fnEditSubtitles('${video.opus}')">s</span>
				<span class="label ${video.existOverview ? 'exist' : 'nonExist'}" onclick="fnEditOverview('${video.opus}')" title="${video.overviewText}">O</span>
				<span id="DEL-opus-${video.opus}" style="display:none;" class="label" onclick="fnDeleteOpus('${video.opus}')">D</span>
				<input type="range" id="Rank-${video.opus}" name="points" min="<s:eval expression="@videoProp['minRank']"/>" max="<s:eval expression="@videoProp['maxRank']"/>" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/>
			</div>
		</li>
		</c:forEach>
	</ul>
	</c:when>
	<c:when test="${videoSearch.listViewType eq 'T'}">
	<table class="video-table">
		<c:forEach items="${videoList}" var="video" varStatus="status">
		<tr id="opus-${video.opus}">
			<td><span class="label" onclick="fnSearch('${video.studio.name}')">${video.studio.name}</span>
				<img src="<c:url value="/resources/link.png"/>" onclick="fnViewStudioDetail('${video.studio.name}')"></td>		
			<td><span class="label">${video.opus}</span></td>		
			<td><span class="label" onclick="fnVideoDetail('${video.opus}')">${video.title}</span></td>		
			<td><c:forEach items="${video.actressList}" var="actress">
				<span class="label" onclick="fnSearch('${actress.name}')">${actress.name}</span>
				<img src="<c:url value="/resources/magnify${status.count%2}.png"/>" onclick="fnViewActressDetail('${actress.name}')" width="12px">
				</c:forEach></td>	
			<td>
				<span class="label ${video.existVideoFileList ? 'exist' : 'nonExist'}" onclick="fnPlay('${video.opus}')">V</span>
				<span class="label ${video.existCoverFile ? 'exist' : 'nonExist'}" onclick="fnImageView('${video.opus}')">C</span>
				<span class="label ${video.existSubtitlesFileList ? 'exist' : 'nonExist'}" onclick="fnEditSubtitles('${video.opus}')">s</span>
				<span class="label ${video.existOverview ? 'exist' : 'nonExist'}" onclick="fnEditOverview('${video.opus}')" title="${video.overviewText}">O</span>
				<span id="DEL-opus-${video.opus}" class="label" onclick="fnDeleteOpus('${video.opus}')">D</span></td>
			<td><input type="range" id="Rank-${video.opus}" name="points" min="<s:eval expression="@videoProp['minRank']"/>" max="<s:eval expression="@videoProp['maxRank']"/>" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/></td>
		</tr>
		</c:forEach>
	</table>
	</c:when>
	<c:when test="${videoSearch.listViewType eq 'S'}">
	<div id="video-slide-wrapper">
		<div id="slides" class="slides">
		<c:forEach items="${videoList}" var="video" varStatus="status">
			<div id="opus-${video.opus}" tabindex="${status.count}" class="video-slide" style="display:none;">             
				<dl class="video-slide-bg" style="background-image:url('<c:url value="/video/${video.opus}/cover" />');">
					<dt><span class="label-large" onclick="fnVideoDetail('${video.opus}')">${video.title}</span></dt>
					<dd><span class="label-large" onclick="fnSearch('${video.studio.name}')">${video.studio.name}</span>
						<img src="<c:url value="/resources/link.png"/>" onclick="fnViewStudioDetail('${video.studio.name}')"></dd>
					<dd><span class="label-large">${video.opus}</span></dd>
					<dd>
						<c:forEach items="${video.actressList}" var="actress" varStatus="status">
						<span class="label-large" onclick="fnSearch('${actress.name}')">${actress.name}</span>
						<img src="<c:url value="/resources/magnify${status.count%2}.png"/>" onclick="fnViewActressDetail('${actress.name}')" width="12px">
						</c:forEach>
					</dd>
					<dd><span class="label-large ${video.existVideoFileList ? 'exist' : 'nonExist'}" onclick="fnPlay('${video.opus}')">Video (${video.playCount})</span></dd>
					<dd><span class="label-large ${video.existCoverFile ? 'exist' : 'nonExist'}" onclick="fnImageView('${video.opus}')">Cover</span></dd>
					<dd><span class="label-large ${video.existSubtitlesFileList ? 'exist' : 'nonExist'}" onclick="fnEditSubtitles('${video.opus}')">smi</span></dd>
					<dd><span class="label-large ${video.existOverview ? 'exist' : 'nonExist'}" onclick="fnEditOverview('${video.opus}')" title="${video.overviewText}">Overview</span>
						<input type="range" id="Rank-${video.opus}" name="points" min="<s:eval expression="@videoProp['minRank']"/>" max="<s:eval expression="@videoProp['maxRank']"/>" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/>	
					</dd>
					<dd><span class="label-large">Download : ${video.videoDate}</span></dd>
					<dd><span class="label-large">Release : ${video.releaseDate}</span></dd>
				</dl>
			</div>
		</c:forEach>
		</div>
		<!-- <div><span id="slideNumber" class="label-large"></span></div> -->
		<!-- <div id="video_slide_bar" style=""></div> -->
	</div>
	</c:when>
	<c:when test="${videoSearch.listViewType eq 'L'}">
	<div id="video-slide-wrapper">
		<div id="slides" class="slides">
		<c:forEach items="${videoList}" var="video" varStatus="status">
			<div id="opus-${video.opus}" tabindex="${status.count}" class="video-slide" style="display:none;">             
				<dl class="video-slide-bg" style="background-image:url('<c:url value="/video/${video.opus}/cover" />');">
					<dt><span class="label-large" onclick="fnVideoDetail('${video.opus}')">${video.title}</span></dt>
					<dd><span class="label-large" onclick="fnSearch('${video.studio.name}')">${video.studio.name}</span>
						<img src="<c:url value="/resources/link.png"/>" onclick="fnViewStudioDetail('${video.studio.name}')"></dd>
					<dd><span class="label-large">${video.opus}</span></dd>
					<dd>
						<c:forEach items="${video.actressList}" var="actress" varStatus="status">
						<span class="label-large" onclick="fnSearch('${actress.name}')">${actress.name}</span>
						<img src="<c:url value="/resources/magnify${status.count%2}.png"/>" onclick="fnViewActressDetail('${actress.name}')" width="12px">
						</c:forEach>
					</dd>
					<dd><span class="label-large ${video.existVideoFileList ? 'exist' : 'nonExist'}" onclick="fnPlay('${video.opus}')">Video (${video.playCount})</span></dd>
					<dd><span class="label-large ${video.existCoverFile ? 'exist' : 'nonExist'}" onclick="fnImageView('${video.opus}')">Cover</span></dd>
					<dd><span class="label-large ${video.existSubtitlesFileList ? 'exist' : 'nonExist'}" onclick="fnEditSubtitles('${video.opus}')">smi</span></dd>
					<dd><span class="label-large ${video.existOverview ? 'exist' : 'nonExist'}" onclick="fnEditOverview('${video.opus}')" title="${video.overviewText}">Overview</span>
						<input type="range" id="Rank-${video.opus}" name="points" min="<s:eval expression="@videoProp['minRank']"/>" max="<s:eval expression="@videoProp['maxRank']"/>" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/>	
					</dd>
				</dl>
			</div>
		</c:forEach>
		</div>
		<div><span id="slideNumber" class="label-large"></span></div>
		<div id="video_slide_bar" style=""></div>
	</div>
	</c:when>
	<c:when test="${videoSearch.listViewType eq 'V'}">
	<div id="video-slide-wrapper">
		<div id="slides" class="slides">
		<c:forEach items="${videoList}" var="video" varStatus="status">
			<div id="opus-${video.opus}" tabindex="${status.count}" class="video-slide" style="display:none;">    
				<dl class="video-slide-bg" ><%-- style="background-image:url('<c:url value="/video/${video.opus}/cover" />');" --%>
					<dt>
						<span class="label-large" onclick="fnSearch('${video.studio.name}')">${video.studio.name}</span>
						<img src="<c:url value="/resources/link.png"/>" onclick="fnViewStudioDetail('${video.studio.name}')">
						<span class="label-large">${video.opus}</span>
						<span class="label-large" onclick="fnVideoDetail('${video.opus}')">${video.title}</span>
						<c:forEach items="${video.actressList}" var="actress" varStatus="status">
						<span class="label-large" onclick="fnSearch('${actress.name}')">${actress.name}</span>
						<img src="<c:url value="/resources/magnify${status.count%2}.png"/>" onclick="fnViewActressDetail('${actress.name}')" width="12px">
						</c:forEach>
						<br/>
						<span class="label ${video.existVideoFileList ? 'exist' : 'nonExist'}" onclick="fnPlay('${video.opus}')">Video (${video.playCount})</span>
						<span class="label ${video.existCoverFile ? 'exist' : 'nonExist'}" onclick="fnImageView('${video.opus}')">Cover</span>
						<span class="label ${video.existSubtitlesFileList ? 'exist' : 'nonExist'}" onclick="fnEditSubtitles('${video.opus}')">smi</span>
						<span class="label ${video.existOverview ? 'exist' : 'nonExist'}" onclick="fnEditOverview('${video.opus}')" title="${video.overviewText}">Overview</span>
						<input type="range" id="Rank-${video.opus}" name="points" min="<s:eval expression="@videoProp['minRank']"/>" max="<s:eval expression="@videoProp['maxRank']"/>" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/>
					</dt>
					<dd>
						<video poster="<c:url value="/video/${video.opus}/cover" />" 
							preload="none" width="800" height="480"
							controls="controls" class="video-js vjs-default-skin" data-setup="{}"
							src="<c:url value="${video.videoURL}" />"
							><%-- <source src="<c:url value="${video.videoURL}" />" type="video/mp4" ></source> --%></video>
					</dd>
					<dd><a href="<c:url value="${video.videoURL}" />"><span class="label">${video.videoURL}</span></a></dd>
				</dl>				         
			</div>
		</c:forEach>
		</div>
	</div>
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

</body>
</html>