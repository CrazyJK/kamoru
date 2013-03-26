<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./common.jsp" %>
<!doctype html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>CSD Mobile</title>
        <style type="text/css" media="screen">@import "./jqtouch/jqtouch.min.css";</style>
        <style type="text/css" media="screen">@import "./themes/jqt/theme.min.css";</style>
        <script src="./jqtouch/jquery.1.3.2.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="./jqtouch/jqtouch.min.js" type="application/x-javascript" charset="utf-8"></script>
        <script src="./login/loginCheck.js" type="application/x-javascript" charset="utf-8"></script>
        <script type="text/javascript" charset="utf-8">
            var jQT = new $.jQTouch({
                icon: 'csd.png',
                addGlossToIcon: false,
                startupScreen: 'csd_startup.png',
                statusBar: 'black',
                preloadImages: [
                    './themes/jqt/img/back_button.png',
                    './themes/jqt/img/back_button_clicked.png',
                    './themes/jqt/img/button_clicked.png',
                    './themes/jqt/img/grayButton.png',
                    './themes/jqt/img/whiteButton.png',
                    './themes/jqt/img/loading.gif'
                    ]
            });
            // Some sample Javascript functions:
            $(function(){
                // Show a swipe event on swipe test
                $('#swipeme').swipe(function(evt, data) {                
                    $(this).html('You swiped <strong>' + data.direction + '</strong>!');
                });
                $('a[target="_blank"]').click(function() {
                    if (confirm('This link opens in a new window.')) {
                        return true;
                    } else {
                        $(this).removeClass('active');
                        return false;
                    }
                });
                // Page animation callback events
                $('#pageevents').
                    bind('pageAnimationStart', function(e, info){ 
                        $(this).find('.info').append('Started animating ' + info.direction + '&hellip; ');
                    }).
                    bind('pageAnimationEnd', function(e, info){
                        $(this).find('.info').append(' finished animating ' + info.direction + '.<br /><br />');
                    });
                // Page animations end with AJAX callback event, example 1 (load remote HTML only first time)
                $('#callback').bind('pageAnimationEnd', function(e, info){
                    if (!$(this).data('loaded')) {                      // Make sure the data hasn't already been loaded (we'll set 'loaded' to true a couple lines further down)
                        $(this).append($('<div>Loading</div>').         // Append a placeholder in case the remote HTML takes its sweet time making it back
                            load('ajax.html .info', function() {        // Overwrite the "Loading" placeholder text with the remote HTML
                                $(this).parent().data('loaded', true);  // Set the 'loaded' var to true so we know not to re-load the HTML next time the #callback div animation ends
                            }));
                    }
                });
                // Orientation callback event
                $('body').bind('turn', function(e, data){
                    $('#orient').html('Orientation: ' + data.orientation);
                });
            });
            
			$.ajax({
				url: "./count.jsp",
				data: "id=" + userid,
				success: function(data){
					data = jQuery.trim(data);
					var cnt = eval(data);
					$("#todocnt").html(cnt[0].TODOCNT);
					$("#runcnt").html(cnt[0].RUNCNT);
				}
			});
			function logout(){
				sessionStorage.removeItem("CSD_LOGIN_STATE");
				localStorage.removeItem("CSD_AUTOLOGIN");
				localStorage.removeItem("CSD_PWD");
				location.href = "./index.jsp";
			}
        </script>
        <style type="text/css" media="screen">
            body.fullscreen #home .info {
                display: none;
            }
            #about {
                padding: 100px 10px 40px;
                text-shadow: rgba(255, 255, 255, 0.3) 0px -1px 0;
                font-size: 13px;
                text-align: center;
                background: #161618;
            }
            #about p {
                margin-bottom: 8px;
            }
            #about a {
                color: #fff;
                font-weight: bold;
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <div id="home" class="current">
            <div class="toolbar">
                <h1>CSD</h1>
                <a class="button" id="logoutButton" onclick="logout();">Logout</a>
            </div>
            <ul class="rounded">
                <li class="arrow"><a href="./information/ic.jsp">Informaion Center</a> </li>
                <li class="arrow"><a href="#service">Service Flow</a> </li>
                <li class="arrow"><a href="./faq/faq.jsp">FAQ</a></li>
                <li class="arrow"><a href="./serviceflow/checkuplist.jsp">정기방문현황</a></li>
            </ul>
            <h2>External Links</h2>
            <ul class="rounded">
                <li class="forward"><a href="http://www.handysoft.co.kr" target="_blank">HANDYSOFT Homepage</a></li>
                <li class="forward"><a href="http://gw.handysoft.co.kr" target="_blank">Groupware</a></li>
            </ul>
        </div>
        <div id="about" class="selectable">
                <p><img src="csd.png" /></p>
                <p><strong>CSD Mobile</strong><br />Version 1.0 beta<br /></p>
                <p><em>Consolidated Service Desk<br /> for Customer Satisfaction</em></p>
                <p>
                    <a href="http://www.handysoft.co.kr" target="_blank">HANDYSOFT</a>
                </p>
                <p><br /><br /><a href="#" class="grayButton goback">Close</a></p>
        </div>
        <div id="ic_2">
            <div class="toolbar">
                <h1>Information</h1>
                <a class="back" href="#">Home</a>
            </div>
            <ul class="rounded">
                <li class="arrow"><a href="./information/cmpny.jsp">업체정보</a></li>
                <li class="arrow"><a href="./information/person.jsp">인물정보</a></li>
                <li class="arrow"><a href="./information/install.jsp">설치정보</a></li>
            </ul>
        </div>
        <div id="service">
            <div class="toolbar">
                <h1>Service Flow</h1>
                <a class="back" href="#">Home</a>
            </div>
            <ul class="rounded">
                <li class="arrow"><a href="./serviceflow/rqsttodolist.jsp">문의 대기</a><small class="counter" id="todocnt">0</small></li>
                <li class="arrow"><a href="./serviceflow/rqstrunlist.jsp">문의 진행</a><small class="counter" id="runcnt">0</small></li>
                <li class="arrow"><a href="./serviceflow/rqstdonelist.jsp">문의 완료</a></li>
                <!--<li class="arrow"><a href="./serviceflow/checkuplist.jsp">정기방문 현황</a></li>-->
            </ul>
        </div>
        <div id="faq">
            <div class="toolbar">
                <h1>FAQ</h1>
                <a class="back" href="#home">Home</a>
            </div>
            <ul class="rounded">
                <li class="arrow"><a href="#ajax_post">기술공지</a></li>
                <li class="arrow"><a href="ajax.html">Groupware</a></li>
                <li class="arrow"><a href="#callback">Messenger</a></li>
                <li class="arrow"><a href="#callback">자료관</a></li>
                <li class="arrow"><a href="#callback">BPM Suite</a></li>
            </ul>
        </div>
        <div id="education">
            <div class="toolbar">
                <h1>Education</h1>
                <a class="back" href="#home">Home</a>
            </div>
            <ul class="rounded">
                <li class="arrow"><a href="#ajax_post">Groupware</a></li>
                <li class="arrow"><a href="ajax.html">BPM</a></li>
                <li class="arrow"><a href="#callback">k*cube</a></li>
                <li class="arrow"><a href="#callback">기타</a></li>
            </ul>
        </div>

        <div id="company">
            <div class="toolbar">
                <h1>업체정보</h1>
                <a class="back" href="#ic">이전</a>
            </div>
        </div>

    </body>
</html>