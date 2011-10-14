/**
 * 
 */
var path = "";
var prevPath = "";
var files = new Array();
var currSection = "";

$(document).ready(function(){
	$("#loadingbar").hide();
	$("#localpath").bind("keyup", function(event){
		if (event.keyCode == '13') {
			getFileList();
		}
	});
});

function getFileList()
{
	path = jQuery.trim($("#localpath").val());
	var filter = jQuery.trim($("#filter").val());
	if(path != "")
	{
		$.ajax({
			url: "filelistAction.jsp?p=" + path + "&f=" + filter,
			beforeSend: function() {
	            $('#loadingbar').show().fadeIn('fast'); 
	        },
			success: function(data){
				viewData(jQuery.trim(data));
			},
			error: function(xhr, status, error){ 
				alert(error); 
			},
			complete: function() { 
				$("#loadingbar").fadeOut();
		    }
		});
	}
}
function viewData(data)
{
	$("#debugDiv").val(data).hide();
	try{
		files = eval(data);
		prevPath = path;
	}catch(e){
		alert(data)
		$("#localpath").val(prevPath);		
		return;
	}
	var totalFileLength = 0;
	$("#filelist").empty();
	$("#complist").empty();
	for(var i=0; i<files.length; i++)
	{
		totalFileLength += parseInt(files[i].SIZE);
		$("#filelist").append("<li id='file" + i + "'><a href='javascript:void()' onclick='fileAction("+i+", \"PLAY\")'>" 
				+ "<span class='filesize'>" + convertSize(files[i].SIZE) + "</span>"
				+ "<span class='filename'>" + files[i].NAME + "</span>"
				+ "<span class='filepath'>" + files[i].PATH.replace(path, "") + "</span>" 
				+ "</a>"
				+ "<a href='javascript:void()' onclick='fileAction("+i+", \"DEL\")'><span class='filedel'>DEL</span></a>"
				+ "</li>");		
	}
	$("#fileCnt").html("Total " + files.length + "; " + convertSize(totalFileLength));
	if(currSection == "sameSizeFileSec")
	{
		sameSizeFile();
	}
	else
	{
		allFile();
	}
}

function sameSizeFile()
{
	$("#allFileSec").hide();
	$("#complist").empty();
	var compArr = new Array();
	var idx = 0;
	for(var i=0; i<files.length-1; i++)
	{
		var except = false;
		for(var k=0; k<compArr.length; k++)
		{
			if(i == compArr[k])
			{
				except = true;
				break;
			}
		}
		
		var first = true;
		if(!except)
		{
			for(var j=i+1; j<files.length; j++)
			{
				if(files[i].SIZE == files[j].SIZE)
				{
					// 번호 기억
					//alert(i + ", " + j);
					if(first)
					{
						$("#complist").append("<li><a href='javascript:void()' onclick='fileAction("+i+", \"PLAY\")'>" + (i + 1) + ". "
								+ "<span class='filesize'>" + files[i].SIZE + "</span>"
								+ "<span class='filename'>" + files[i].NAME + "</span>"
								+ "<span class='filepath'>" + files[i].PATH.replace(path, "") + "</span>" 
								+ "</a>"
								+ "<a href='javascript:void()' onclick='fileAction("+i+", \"DEL\")'><span class='filedel'>DEL</span></a>"
								+ "</li>");		
						first = false;
					}
					
					
					compArr[idx++] = j;
					
					$("#complist").append("<li><a href='javascript:void()' onclick='fileAction("+j+", \"PLAY\")'>" + (j + 1) + ". "
							+ "<span class='filesize'>" + files[j].SIZE + "</span>"
							+ "<span class='filename'>" + files[j].NAME + "</span>"
							+ "<span class='filepath'>" + files[j].PATH.replace(path, "") + "</span>" 
							+ "</a>"
							+ "<a href='javascript:void()' onclick='fileAction("+i+", \"DEL\")'><span class='filedel'>DEL</span></a>"
							+ "</li>");		
				}
			}
			if(!first)
			{
				$("#complist").append("<br/>");
			}
		}
	}
	$("#sameSizeFileSec").show();
	currSection = "sameSizeFileSec";
}
function allFile()
{
	$("#allFileSec").show();
	$("#sameSizeFileSec").hide();
	currSection = "allFileSec";
}
function convertSize(s)
{
	var i = parseInt(s);
	if(i < 1000){
		return i + "B";
	}else if(i < 1000000){
		return Math.round(i/1000) + "KB";
	}else if(i < 1000000000){
		return Math.round(i/1000000) + "MB";
	}else if(i < 1000000000000){
		return roundXL(i/1000000000,2) + "GB";
	}else if(i < 1000000000000000){
		return roundXL(i/1000000000000, 2) + "TB";
	}else{
		return s;
	}
}

//엑셀 스타일의 반올림 함수 정의
function roundXL(n, digits) {
  if (digits >= 0) return parseFloat(n.toFixed(digits)); // 소수부 반올림

  digits = Math.pow(10, digits); // 정수부 반올림
  var t = Math.round(n * digits) / digits;

  return parseFloat(t.toFixed(0));
}

function fileAction(idx, mode)
{
	var uri = files[idx].URI;
	var player = $("#playeruri").val();
//	alert(uri);
	$.ajax({
		url: "fileAction.jsp",
		type: "POST",
		data: "uri=" + uri + "&mode=" + mode + "&player=" + player, 
		beforeSend: function() {
            $('#loadingbar').show().fadeIn('fast'); 
            $("#filelist > li").removeClass("selectfile");
            $("#file"+idx).addClass("selectfile");
        },
		success: function(data){
			if(mode == "DEL")
			{
				getFileList();	
			}
		},
		error: function(xhr, status, error){ 
			alert(error); 
		},
		complete: function() { 
			$("#loadingbar").fadeOut();
	    }
	});
}
function randomPlay()
{
	var filesLength = files.length;
	var randomNo = Math.floor(Math.random() * filesLength) + 1;
    location.href = "#file" + randomNo;
	fileAction(randomNo, "PLAY");
}