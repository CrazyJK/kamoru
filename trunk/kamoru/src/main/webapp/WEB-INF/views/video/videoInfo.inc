<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>

<li id="opus-${video.opus}" class="li-box">
	<div class="video-card">
		<table>
			<tr>
				<td colspan="2"><div style="height:17px;" class="label" onclick="fnVideoDetail('${video.opus}')" title="${video.title}">${video.title}</div></td>
			</tr>
			<tr valign="top" height="120px">
				<td width="70px" class="video-card-bg" style="background-image:url('<c:url value="/video/${video.opus}/cover" />')">
					&nbsp;
				</td>
				<td>
					<dl>
						<dd><c:forEach items="${video.actressList}" var="actress">
							<span class="label" onclick="fnViewActressDetail('${actress.name}')">${actress.name}</span>
							</c:forEach></dd>
						<dd><span class="label" onclick="fnViewStudioDetail('${video.studio.name}')">${video.studio.name}</span></dd> 
						<dd><span class="label">${video.opus}</span></dd>
						<dd><span class="label ${video.existVideoFileList ? 'exist' : 'nonExist'}" onclick="fnPlay('${video.opus}')">V</span>
							<span class="label ${video.existCoverFile ? 'exist' : 'nonExist'}" onclick="fnImageView('${video.opus}')">C</span>
							<span class="label ${video.existSubtitlesFileList ? 'exist' : 'nonExist'}" onclick="fnEditSubtitles('${video.opus}')">s</span>
							<span class="label ${video.existOverview ? 'exist' : 'nonExist'}" onclick="fnEditOverview('${video.opus}')" title="${video.overviewText}">O</span>
						</dd>
						<dd><input type="range" id="Rank-${video.opus}" name="points" min="-5" max="5" value="${video.rank}" onmouseup="fnRank('${video.opus}')"/></dd>
					</dl>
				</td>
			</tr>
		</table>
	</div>
</li>
