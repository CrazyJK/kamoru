<%@ taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<ul>
	<li><a href="<c:url value="/home"/>">Home</a>
	<security:authorize url="/video**">
	<li><a href="<c:url value="/video"/>">Video Main</a>
		<ul>
			<li><a href="<c:url value="/video/search"/>">Video search</a>
			<li><a href="<c:url value="/video/list"/>">Video list</a>
			<li><a href="<c:url value="/video/actress"/>">Actress list</a>
			<li><a href="<c:url value="/video/studio"/>">Studio List</a>
			<li><a href="<c:url value="/video/briefing"/>">Video briefing</a>
			<li><a href="<c:url value="/video/torrent"/>">Video Torrent</a>
			<li><a href="<c:url value="/video/manager"/>">Video Manager</a>
		</ul>
	</security:authorize>
	<security:authorize url="/image**">
	<li><a href="<c:url value="/image"/>">Image view</a>
	<li><a href="<c:url value="/image/downloadGnomImage"/>">Gnom Image Download</a>
	</security:authorize>
	<li><a href="<c:url value="/image/google"/>">Google Image search</a>
	<li><a href="<c:url value="/requestMappingList"/>">request mapping list</a>
	<li><a href="<c:url value="/hitMessageCodeList"/>">hit messageCode List</a>
	<li><a href="<c:url value="/jsp/util/sessionView.jsp"/>">Web Session</a>
	<li><a href="<c:url value="/jsp/colors.jsp"/>">Standard Colors</a>
	<li><a href="<c:url value="/jsp/util/threaddump.jsp"/>">Thread dump</a>
	<li><a href="<c:url value="/jsp/pic/imgView.html"/>">Canvas Image view</a>
	<li><a href="<c:url value="/image/slides"/>">Slide Image view</a>
	<li><a href="<c:url value="/jsp/util/logView.jsp"/>">Log view</a>
	<li><a href="<c:url value="/jsp/util/portScan.jsp"/>">Port scan</a>
</ul>
