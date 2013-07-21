<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="title" text="video"/></title>
<script type="text/javascript">
var opusArray = ${opusArray};
var bgImageCount = ${bgImageCount};
</script>
<script src="<c:url value="/resources/video/videoMain.js" />" type="text/javascript"></script>
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
			<form:label path="searchText"><spring:message code="msg.search" text="Search"/></form:label>
			<form:input path="searchText" cssClass="searchInput"/>
			
			<span class="checkbox" id="checkbox-addCond" title="<spring:message code="msg.title.addCondition" text="Add additional conditions"/>">
				<spring:message code="msg.addCondition" text="Add"/>
			</span><form:hidden path="addCond"/>
			<span class="checkbox" id="checkbox-existVideo" title="<spring:message code="msg.title.existVideo" text="exist Video?"/>">V</span>			    
				<form:hidden path="existVideo"/>
			<span class="checkbox" id="checkbox-existSubtitles" title="<spring:message code="msg.title.existSubtitles" text="exist Subtitles?"/>">S</span>			
				<form:hidden path="existSubtitles"/>
			<span class="separator">|</span>
			<span class="checkbox" id="checkbox-neverPlay" title="<spring:message code="msg.title.unseenVideo" text="unseen video?"/>">P</span>			
				<form:hidden path="neverPlay"/>
			<span class="checkbox" id="checkbox-zeroRank" title="<spring:message code="msg.title.zeroRankVideo" text="0 Rank video?"/>">R</span>			
				<form:hidden path="zeroRank"/>
			<span class="separator">|</span>
			<span class="button" onclick="fnSearch()"><spring:message code="btn.search" text="Search video"/>
				(<c:out value="${fn:length(videoList)}"/>)
			</span>
		</span>
		<span class="group">
			<c:forEach items="${views}" var="view">
				<span class="radio" id="radio-listViewType-${view}" title="<spring:message code="msg.title.showViewTypeList" text="show view type list" arguments="${view.desc}"/>">${view}</span> 
			</c:forEach><form:hidden path="listViewType"/>
		</span>
		<span class="group">
			<c:forEach items="${sorts}" var="sort">
				<span class="radio" id="radio-sortMethod-${sort}" title="<spring:message code="msg.title.sortBy" text="sort by this" arguments="${sort.desc}"/>">${sort}</span>
			</c:forEach><form:hidden path="sortMethod"/>
			<span class="checkbox" id="checkbox-sortReverse" title="<spring:message code="msg.title.reverseSort" text="reverse sort"/>">R</span><form:hidden path="sortReverse"/>
		</span>
		<span class="group">
			<span class="checkbox" id="checkbox-viewStudioDiv"  title="<spring:message code="msg.title.viewStudioPanel"  text="view Studio panel"/>"  onclick="fnStudioDivToggle()">S</span>	
				<form:hidden path="viewStudioDiv"/> 
			<span class="checkbox" id="checkbox-viewActressDiv" title="<spring:message code="msg.title.viewActressPanel" text="view Actress panel"/>" onclick="fnActressDivToggle()">A</span>	
				<form:hidden path="viewActressDiv"/>
		</span>
		<span class="group">
			<span class="button" onclick="fnRandomPlay()"><spring:message code="btn.random" text="Random play"/></span>
		</span>
		<span class="group">
			<span class="button" onclick="fnBGImageView();"><spring:message code="btn.bgimage" text="Show bg-image"/></span>
		</span>
		<span id="debug" style="display:none"></span>
	</form:form>
	</div>
	<div id="studioDiv" class="div-box">
	<c:forEach var="studio" items="${studioList}"><c:set value="${fn:length(studio.videoList)}" var="countByStudio" />
		<span onclick="fnSearch('${studio.name}')" class="${countByStudio > 9 ? 'item10' : countByStudio > 4 ? 'item5' : 'item1'}" 
			title="${studio.homepage} ${studio.companyName} Actress:${fn:length(studio.actressList)}">${studio.name}(${countByStudio})</span>
	</c:forEach>
	</div>
	<div id="actressDiv" class="div-box">
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
										<span id="DEL-${video.opus}" style="display:none;" class="label" onclick="fnDeleteOpus('${video.opus}')">D</span></dd>
									<dd><input type="range" id="Rank-${video.opus}" name="points" min="-5" max="5" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/></dd>
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
							<input type="range" id="Rank-${video.opus}" name="points" min="-5" max="5" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/>	
							<span id="DEL-${video.opus}" style="display:none;" class="label" onclick="fnDeleteOpus('${video.opus}')">Del</span>
						</dd>
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
					<span id="DEL-${video.opus}" style="display:none;" class="label" onclick="fnDeleteOpus('${video.opus}')">D</span>
					<input type="range" id="Rank-${video.opus}" name="points" min="-5" max="5" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/>
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
					<span id="DEL-${video.opus}" class="label" onclick="fnDeleteOpus('${video.opus}')">D</span></td>
				<td><input type="range" id="Rank-${video.opus}" name="points" min="-5" max="5" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/></td>
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

</body>
</html>