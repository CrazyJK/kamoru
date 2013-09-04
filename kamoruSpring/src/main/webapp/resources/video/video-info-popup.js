function fnViewActressDetail(name) {
	popup(context + "video/actress/" + name, "actressDetail-" + name, 800, 600);
}

function fnViewStudioDetail(name) {
	popup(context + "video/studio/" + name, "studioDetail-" + name, 800, 600);
}

function fnViewVideoDetail(opus) {
	popup(context + "video/" + opus, "videoDetail-" + opus, 800, 600);
}