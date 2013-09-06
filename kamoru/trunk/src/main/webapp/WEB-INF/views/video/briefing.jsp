<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s"  uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="${locale }">
<head>
<title><s:message code="text.title.briefing" text="Video Briefing"/></title>
<style type="text/css">
div#contentDiv.div-box section {
	margin: 2px;
	border-radius:5px; 
	border: 1px solid orange;
}
div#contentDiv.div-box h3 {
	margin: 2px;
	padding: 5px;
	cursor: pointer;
	text-shadow: 1px 1px 1px white;
}
div#contentDiv.div-box h3:hover {
	border-radius:5px; 
	background-color:rgba(255,165,0,.5);
}
div#contentDiv.div-box article {
	margin: 10px;
	display: none;
}
.h3-toggle-on {
	border: 1px solid blue;
	border-radius:5px; 
	background-color:rgba(255,165,0,.25);
}
</style>
<script type="text/javascript">
var bgImageCount = ${bgImageCount};
var selectedNumber = getRandomInteger(0, bgImageCount);
$(document).ready(function(){
	$(window).bind("resize", resizeDivHeight);
	
	//setBackgroundImage();
	resizeDivHeight();
	
	$("h3").bind("click", function(){
		$(this).next().slideToggle("slow", function() {
			$(this).prev().toggleClass("h3-toggle-on");
		});
	});
});

function resizeDivHeight() {
	var windowHeight = $(window).height();
	var header = $("#header_div").outerHeight();
	var calculatedDivHeight = windowHeight - header - 20 * 2; 
	$("#contentDiv").outerHeight(calculatedDivHeight);	
}

</script>
</head>
<body>

<div id="header_div" class="div-box">
	<s:message code="text.title.briefing" text="Video Briefing"/>
</div>

<div id="contentDiv" class="div-box" style="overflow:auto; text-align:left;">

<section>
	<h3><s:message code="text.video-by-folder" text="Video by folder"/></h3>
	<article>
		<table class="video-table" style="background-color:lightgray">
			<tr>
				<th style="text-align:left;"><s:message code="text.folder" text="Folder"/></th>
				<th style="text-align:right;"><s:message code="text.size"   text="size"/></th>
			</tr>
			<c:forEach items="${pathMap}" var="path" varStatus="status">
			<tr>
				<td style="text-align:left;">${path.key}</td>
				<td style="text-align:right;">${path.value}</td>
			</tr>
			</c:forEach>		
		</table>
	</article>
</section>

<section>
	<h3><s:message code="text.video-by-date" text="Video by date"/></h3>
	<article>
		<table class="video-table" style="background-color:lightgray">
			<tr>
				<th><s:message code="text.date"   text="Date"/></th>
				<th><s:message code="text.length"   text="length"/></th>
				<th><s:message code="text.video"   text="Video"/></th>
			</tr>
			<c:forEach items="${dateMap}" var="date" varStatus="status">
			<tr>
				<td class="nowrap">${date.key}</td>
				<td class="nowrap" style="text-align:right;">${fn:length(date.value)}</td>
				<td class="nowrap">
					<c:forEach items="${date.value}" var="video" varStatus="status">
					<span class="label" onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span>
					</c:forEach>
				</td>
			</tr>
			</c:forEach>		
		</table>
	</article>
</section>

<section>
	<h3><s:message code="text.video-by-rank" text="Video by rank"/></h3>
	<article>
		<table class="video-table" style="background-color:lightgray">
			<tr>
				<th><s:message code="text.rank"   text="Rank"/></th>
				<th><s:message code="text.length"   text="length"/></th>
				<th><s:message code="text.video"   text="Video"/></th>
			</tr>
			<c:forEach items="${rankMap}" var="rank" varStatus="status">
			<tr>
				<td class="nowrap">${rank.key}</td>
				<td class="nowrap" style="text-align:right;">${fn:length(rank.value)}</td>
				<td class="nowrap">
					<c:forEach items="${rank.value}" var="video" varStatus="status">
					<span class="label" onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span>
					</c:forEach>
				</td>
			</tr>
			</c:forEach>		
		</table>
	</article>
</section>

<section>
	<h3><s:message code="text.video-by-play" text="Video by play"/></h3>
	<article>
		<table class="video-table" style="background-color:lightgray">
			<tr>
				<th><s:message code="text.play"   text="Play"/></th>
				<th><s:message code="text.length"   text="length"/></th>
				<th><s:message code="text.video"   text="Video"/></th>
			</tr>
			<c:forEach items="${playMap}" var="play" varStatus="status">
			<tr>
				<td class="nowrap">${play.key}</td>
				<td class="nowrap" style="text-align:right;">${fn:length(play.value)}</td>
				<td class="nowrap">
					<c:forEach items="${play.value}" var="video" varStatus="status">
					<span class="label" onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span>
					</c:forEach>
				</td>
			</tr>
			</c:forEach>		
		</table>
	</article>
</section>

<section>
	<h3><s:message code="text.total"/> <s:message code="text.video"/> : ${fn:length(videoList)}</h3>
	<article id="videoDiv" class="div-box">
		<c:forEach items="${videoList}" var="video" varStatus="status">
		<span class="label" onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span>
		</c:forEach>
	</article>
</section>

<section>
	<h3><s:message code="text.total"/> <s:message code="text.studio"/> : ${fn:length(studioList)}</h3>
	<article id="studioDiv" class="div-box">
	<c:forEach var="studio" items="${studioList}"><c:set value="${fn:length(studio.videoList)}" var="countByStudio" />
		<span onclick="fnViewStudioDetail('${studio.name}')" class="${countByStudio > 9 ? 'item10' : countByStudio > 4 ? 'item5' : 'item1'}" 
			title="${studio.homepage} ${studio.companyName} Actress:${fn:length(studio.actressList)}">${studio.name}(${countByStudio})</span>
	</c:forEach>
	</article>
</section>

<section>
	<h3><s:message code="text.total"/> <s:message code="text.actress"/> : ${fn:length(actressList)}</h3>
	<article id="actressDiv" class="div-box">
	<c:forEach items="${actressList}" var="actress"><c:set value="${fn:length(actress.videoList)}" var="countByActress" />
		<span onclick="fnViewActressDetail('${actress.name}')" class="${countByActress > 9 ? 'item10' : countByActress > 4 ? 'item5' : 'item1'}" >${actress.name}(${countByActress})</span>
	</c:forEach>
	</article>
</section>


</div>

</body>
</html>