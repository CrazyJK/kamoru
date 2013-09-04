var weekend = [0,6];
var weekendColor = "#FFFDF8";
var fontface = "Arial";
var fontsize = "2";
var gNow = new Date();
var ggWinCal;
isNav = (navigator.appName.indexOf("Netscape") != -1) ? true : false;
isIE = (navigator.appName.indexOf("Microsoft") != -1) ? true : false;
Calendar.Months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
//
//Calendar.Months = ["1월", "2월", "3월", "4월", "5월", "6월","7월", "8월", "9월", "10월", "11월", "12월"];
// Non-Leap year Month days..
Calendar.DOMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
// Leap year Month days..
Calendar.lDOMonth = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
function Calendar(p_item, p_WinCal, p_month, p_year, p_format) 
{
	if ((p_month == null) && (p_year == null))	return;
	if (p_WinCal == null)
		this.gWinCal = ggWinCal;
	else
		this.gWinCal = p_WinCal;
	if (p_month == null) {
		this.gMonthName = null;
		this.gMonth = null;
		this.gYearly = true;
	} else {
		this.gMonthName = Calendar.get_month(p_month);
		this.gMonth = new Number(p_month);
		this.gYearly = false;
	}
	this.gYear = p_year;
	this.gFormat = p_format;
	this.gBGColor = "white";
	this.gFGColor = "#352C00";
	this.gTextColor = "#352C00";
	this.gHeaderColor = "#352C00";
	this.gReturnItem = p_item;
}
Calendar.get_month = Calendar_get_month;
Calendar.get_daysofmonth = Calendar_get_daysofmonth;
Calendar.calc_month_year = Calendar_calc_month_year;
Calendar.print = Calendar_print;
function Calendar_get_month(monthNo) {
	return Calendar.Months[monthNo];
}
function Calendar_get_daysofmonth(monthNo, p_year) {
	/* 
	Check for leap year ..
	1.Years evenly divisible by four are normally leap years, except for... 
	2.Years also evenly divisible by 100 are not leap years, except for... 
	3.Years also evenly divisible by 400 are leap years. 
	*/
	if ((p_year % 4) == 0) {
		if ((p_year % 100) == 0 && (p_year % 400) != 0)
			return Calendar.DOMonth[monthNo];
		return Calendar.lDOMonth[monthNo];
	} else
		return Calendar.DOMonth[monthNo];
}
function Calendar_calc_month_year(p_Month, p_Year, incr) {
	/* 
	Will return an 1-D array with 1st element being the calculated month 
	and second being the calculated year 
	after applying the month increment/decrement as specified by 'incr' parameter.
	'incr' will normally have 1/-1 to navigate thru the months.
	*/
	var ret_arr = new Array();
	if (incr == -1) {
		// B A C K W A R D
		if (p_Month == 0) {
			ret_arr[0] = 11;
			ret_arr[1] = parseInt(p_Year) - 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) - 1;
			ret_arr[1] = parseInt(p_Year);
		}
	} else if (incr == 1) {
		// F O R W A R D
		if (p_Month == 11) {
			ret_arr[0] = 0;
			ret_arr[1] = parseInt(p_Year) + 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) + 1;
			ret_arr[1] = parseInt(p_Year);
		}
	}
	
	return ret_arr;
}
function Calendar_print() {
	ggWinCal.print();
}
function Calendar_calc_month_year(p_Month, p_Year, incr) {
	/* 
	Will return an 1-D array with 1st element being the calculated month 
	and second being the calculated year 
	after applying the month increment/decrement as specified by 'incr' parameter.
	'incr' will normally have 1/-1 to navigate thru the months.
	*/
	var ret_arr = new Array();
	
	if (incr == -1) {
		// B A C K W A R D
		if (p_Month == 0) {
			ret_arr[0] = 11;
			ret_arr[1] = parseInt(p_Year) - 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) - 1;
			ret_arr[1] = parseInt(p_Year);
		}
	} else if (incr == 1) {
		// F O R W A R D
		if (p_Month == 11) {
			ret_arr[0] = 0;
			ret_arr[1] = parseInt(p_Year) + 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) + 1;
			ret_arr[1] = parseInt(p_Year);
		}
	}
	
	return ret_arr;
}

// This is for compatibility with Navigator 3, we have to create and discard one object before the prototype object exists.
new Calendar();

Calendar.prototype.getMonthlyCalendarCode = function() {
	var vCode = "";
	var vHeader_Code = "";
	var vData_Code = "";
	
	// Begin Table Drawing code here..
	vCode = vCode + "<TABLE BORDER=1 bordercolordark='#FFFFFF' bordercolorlight='#E4D292' cellspacing='0' cellpadding='0' BGCOLOR=\"" + this.gBGColor + "\">";
	
	vHeader_Code = this.cal_header();
	vData_Code = this.cal_data();
	vCode = vCode + vHeader_Code + vData_Code;
	
	vCode = vCode + "</TABLE>";
	
	return vCode;
}

Calendar.prototype.show = function() {
	var vCode = "";
	this.gWinCal.document.open();


	// Setup the page...
	this.wwrite("<html>");
	this.wwrite("<head>");
//	this.wwrite("<script Language='JavaScript'>");
//	this.wwrite("<!--");
//	this.wwrite("document.domain = 'kita.net';");
//	this.wwrite("-->");
//	this.wwrite("</script>");
	this.wwrite("<title>Calendar</title>");
	this.wwrite("<style>");
	this.wwrite("a:link { font-size: 8pt;}");
	this.wwrite("a:active { font-size: 8pt;}");
	this.wwrite("a:visited {font-size: 8pt;}");
	this.wwrite("a:hover { font-size: 8pt;}  ");
	this.wwrite("</style>");

	this.wwrite("</head>");

	this.wwrite("<body " + 
		"link=\"" + this.gLinkColor + "\" " + 
		"vlink=\"" + this.gLinkColor + "\" " +
		"alink=\"" + this.gLinkColor + "\" " +
		"text=\"" + this.gTextColor + "\">");
	// 달력상단의 년도 월 보이기...
	//this.wwriteA("<FONT FACE='" + fontface + "' SIZE=2><B>");
	//this.wwriteA(this.gYear + " " + this.gMonthName);
	//this.wwriteA("</B><BR>");

	// Show navigation buttons
	var prevMMYYYY = Calendar.calc_month_year(this.gMonth, this.gYear, -1);
	var prevMM = prevMMYYYY[0];
	var prevYYYY = prevMMYYYY[1];

	var nextMMYYYY = Calendar.calc_month_year(this.gMonth, this.gYear, 1);
	var nextMM = nextMMYYYY[0];
	var nextYYYY = nextMMYYYY[1];
	
	this.wwrite("<TABLE WIDTH='175' BORDER=0 CELLSPACING=0 CELLPADDING=0 align='center'><TR><TD ALIGN=center><Font color='red' SIZE='2'>");
	this.wwrite("<A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', '" + this.gMonth + "', '" + (parseInt(this.gYear)-1) + "', '" + this.gFormat + "'" +
		");" +
		"\">◁◁<\/A></FONT></TD><TD ALIGN=center><Font color='#333333' SIZE='2'>");
	this.wwrite("<A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', '" + prevMM + "', '" + prevYYYY + "', '" + this.gFormat + "'" +
		");" +
		"\">◀<\/A></Font></TD>");
// 달력 프린트 하기...
//	this.wwrite("<TD ALIGN=center>[<A HREF=\"javascript:window.print();\">Print</A>]</TD>");
	this.wwriteA("<TD ALIGN=center><FONT FACE='" + fontface + "' STYLE='font-size: 10pt;color:#8B6C0E'><B>");
	this.wwriteA(this.gYear + "  " + this.gMonthName);
	this.wwriteA("<BR>");

	this.wwrite("<TD ALIGN=center><Font color='#333333' SIZE='2'><A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', '" + nextMM + "', '" + nextYYYY + "', '" + this.gFormat + "'" +
		");" +
		"\">▶<\/A></FONT></TD><TD ALIGN=center><Font color='#333333' SIZE='2'>");
	this.wwrite("<A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', '" + this.gMonth + "', '" + (parseInt(this.gYear)+1) + "', '" + this.gFormat + "'" +
		");" +
		"\">▷▷<\/A></FONT></TD></TR></TABLE><table><tr><td height='3'></td></tr></table>");

	// Get the complete calendar code for the month..
	vCode = this.getMonthlyCalendarCode();
	this.wwrite(vCode);
	this.wwrite("</font>");
	
	this.wwrite("</table>");
	this.wwrite("</body></html>");
	this.gWinCal.document.close();
}

Calendar.prototype.showY = function() {
	var vCode = "";
	var i;
	var vr, vc, vx, vy;		// Row, Column, X-coord, Y-coord
	var vxf = 285;			// X-Factor
	var vyf = 200;			// Y-Factor	
	var vxm = 10;			// X-margin
	var vym;				// Y-margin
	if (isIE)	vym = 75;	
	else if (isNav)	vym = 25;
	
	this.gWinCal.document.open();

	this.wwrite("<html>");
	this.wwrite("<head><title>Calendar</title>");
	this.wwrite("<style type='text/css'>\n<!--");
	for (i=0; i<12; i++) {
		vc = i % 3;
		if (i>=0 && i<= 2)	vr = 0;
		if (i>=3 && i<= 5)	vr = 1;
		if (i>=6 && i<= 8)	vr = 2;
		if (i>=9 && i<= 11)	vr = 3;
		
		vx = parseInt(vxf * vc) + vxm;
		vy = parseInt(vyf * vr) + vym;

		this.wwrite(".lclass" + i + " {position:absolute;top:" + vy + ";center:" + vx + ";}");
	}
	this.wwrite("-->\n</style>");
	this.wwrite("</head>");
	this.wwrite("<style>");
	this.wwrite("a:link { font-size: 8pt;}");
	this.wwrite("a:active { font-size: 8pt;}");
	this.wwrite("a:visited {font-size: 8pt;}");
	this.wwrite("a:hover { font-size: 8pt;}  ");
	this.wwrite("</style>");
	this.wwrite("<body " + 
		"link=\"" + this.gLinkColor + "\" " + 
		"vlink=\"" + this.gLinkColor + "\" " +
		"alink=\"" + this.gLinkColor + "\" " +
		"text=\"" + this.gTextColor + "\">");
	this.wwrite("<FONT FACE='" + fontface + "' SIZE=2><B>");
	this.wwrite("Year : " + this.gYear);
	this.wwrite("</B><BR>");

	// Show navigation buttons
	var prevYYYY = parseInt(this.gYear) - 1;
	var nextYYYY = parseInt(this.gYear) + 1;
	
	this.wwrite("<TABLE WIDTH='175' BORDER=1 CELLSPACING=0 CELLPADDING=0 'bordercolordark='#FFFFFF' bordercolorlight='#E4D292'><TR><TD ALIGN=center>");
	this.wwrite("[<A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', null, '" + prevYYYY + "', '" + this.gFormat + "'" +
		");" +
		"\" alt='Prev Year'><<<\/A></TD><TD ALIGN=center>");
	this.wwrite("[<A HREF=\"javascript:window.print();\">Print</A>]</TD><TD ALIGN=center>");
	this.wwrite("[<A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', null, '" + nextYYYY + "', '" + this.gFormat + "'" +
		");" +
		"\">>><\/A></TD></TR></TABLE><BR>");

	// Get the complete calendar code for each month..
	var j;
	for (i=11; i>=0; i--) {
		if (isIE)
			this.wwrite("<DIV ID=\"layer" + i + "\" CLASS=\"lclass" + i + "\">");
		else if (isNav)
			this.wwrite("<LAYER ID=\"layer" + i + "\" CLASS=\"lclass" + i + "\">");

		this.gMonth = i;
		this.gMonthName = Calendar.get_month(this.gMonth);
		vCode = this.getMonthlyCalendarCode();
		this.wwrite(this.gMonthName + "/" + this.gYear + "<BR>");
		this.wwrite(vCode);

		if (isIE)
			this.wwrite("</DIV>");
		else if (isNav)
			this.wwrite("</LAYER>");
	}

	this.wwrite("</font><BR></body></html>");
	this.gWinCal.document.close();
}

Calendar.prototype.wwrite = function(wtext) {
	this.gWinCal.document.writeln(wtext);
}

Calendar.prototype.wwriteA = function(wtext) {
	this.gWinCal.document.write(wtext);
}

Calendar.prototype.cal_header = function() {
	var vCode = "";
	
	vCode = vCode + "<TR>";
	vCode = vCode + "<TD WIDTH='25'height='24' align='center' bgcolor='#F4DD8A'><FONT STYLE='font-size:8pt;' FACE='" + fontface + "' COLOR='#F70404'" + this.gHeaderColor + "'>Sun</FONT></TD>";
	vCode = vCode + "<TD WIDTH='25' height='24' align='center' bgcolor='#F4DD8A'><FONT STYLE='font-size:8pt;' FACE='" + fontface + "' COLOR='#000000'" + this.gHeaderColor + "'>Mon</FONT></TD>";
	vCode = vCode + "<TD WIDTH='25' height='24' align='center' bgcolor='#F4DD8A'><FONT STYLE='font-size:8pt;' FACE='" + fontface + "' COLOR='#000000'" + this.gHeaderColor + "'>Tue</FONT></TD>";
	vCode = vCode + "<TD WIDTH='25' height='24' align='center' bgcolor='#F4DD8A'><FONT STYLE='font-size:8pt;' FACE='" + fontface + "' COLOR='#000000'" + this.gHeaderColor + "'>Wed</FONT></TD>";
	vCode = vCode + "<TD WIDTH='25' height='24' align='center' bgcolor='#F4DD8A'><FONT STYLE='font-size:8pt;' FACE='" + fontface + "' COLOR='#000000'" + this.gHeaderColor + "'>Thu</FONT></TD>";
	vCode = vCode + "<TD WIDTH='25' height='24' align='center' bgcolor='#F4DD8A'><FONT STYLE='font-size:8pt;' FACE='" + fontface + "' COLOR='#000000'" + this.gHeaderColor + "'>Fri</FONT></TD>";
	vCode = vCode + "<TD WIDTH='25' height='24' align='center' bgcolor='#F4DD8A'><FONT STYLE='font-size:8pt;' FACE='" + fontface + "' COLOR='#0455E9'" + this.gHeaderColor + "'>Sat</FONT></TD>";
	vCode = vCode + "</TR>";
	
	return vCode;
}

Calendar.prototype.cal_data = function() {
	var vDate = new Date();
	vDate.setDate(1);
	vDate.setMonth(this.gMonth);
	vDate.setFullYear(this.gYear);

	var vFirstDay=vDate.getDay();
	var vDay=1;
	var vLastDay=Calendar.get_daysofmonth(this.gMonth, this.gYear);
	var vOnLastDay=0;
	var vCode = "";

	/*
	Get day for the 1st of the requested month/year..
	Place as many blank cells before the 1st day of the month as necessary. 
	*/

	vCode = vCode + "<TR>";
	for (i=0; i<vFirstDay; i++) {
		vCode = vCode + "<TD WIDTH='25' height='24'" + this.write_weekend_string(i) + "><FONT SIZE='2' FACE='" + fontface + "'>&nbsp;</FONT></TD>";
	}
	var a="aaa";
	var b = "bbb";
	// Write rest of the 1st week
	for (j=vFirstDay; j<7; j++) {
		vCode = vCode + "<TD WIDTH='25' height='24' align='center'" + this.write_weekend_string(j) + "><FONT SIZE='2' FACE='" + fontface + "'>" + 
			"<A HREF='#' " + 
				"onClick=\"self.opener.document." + this.gReturnItem + ".value='" + 
				this.format_data(vDay) + 
				"';self.opener.document." + this.gReturnItem + ".focus();window.close();\">" + 
				this.format_day(vDay) + 
			"</A>" + 
			"</FONT></TD>";
		vDay=vDay + 1;
	}
	vCode = vCode + "</TR>";

	// Write the rest of the weeks
	for (k=2; k<7; k++) {
		vCode = vCode + "<TR>";

		for (j=0; j<7; j++) {
			vCode = vCode + "<TD WIDTH='25' height='24' align='center'" + this.write_weekend_string(j) + "><FONT SIZE='2' FACE='" + fontface + "'>" + 
				"<A HREF='#' " + 
					"onClick=\"self.opener.document." + this.gReturnItem + ".value='" + 
					this.format_data(vDay) + 
					"';self.opener.document." + this.gReturnItem + ".focus();window.close();\">" + 
				this.format_day(vDay) + 
				"</A>" + 
				"</FONT></TD>";
			vDay=vDay + 1;

			if (vDay > vLastDay) {
				vOnLastDay = 1;
				break;
			}
		}

		if (j == 6)
			vCode = vCode + "</TR>";
		if (vOnLastDay == 1)
			break;
	}
	
	// Fill up the rest of last week with proper blanks, so that we get proper square blocks
	for (m=1; m<(7-j); m++) {
		if (this.gYearly)
			vCode = vCode + "<TD WIDTH='25' height='24' align='center'" + this.write_weekend_string(j+m) + 
			"><FONT STYLE='font-size:8pt;' FACE='" + fontface + "' COLOR='gray'> </FONT></TD>";
		else
			vCode = vCode + "<TD WIDTH='25' height='24' align='center'" + this.write_weekend_string(j+m) + 
			"><FONT STYLE='font-size:8pt;' FACE='" + fontface + "' COLOR='gray'>" + m + "</FONT></TD>";
	}
	return vCode;
}

Calendar.prototype.format_day = function(vday) {
	
	var vNowDay   = cur_day ;//gNow.getDate();
	var vNowMonth = cur_month ;//gNow.getMonth();
	var vNowYear  = cur_year ;//gNow.getFullYear();

	if (vday == vNowDay && this.gMonth == vNowMonth && this.gYear == vNowYear)
		return ("<FONT COLOR=\"#FF4E00\" STYLE='font-size: 10pt;'><B>" + vday + "</B></FONT>");
	else
		return (vday);
}

Calendar.prototype.write_weekend_string = function(vday) {
	var i;

	// Return special formatting for the weekend day.
	for (i=0; i<weekend.length; i++) {
		if (vday == weekend[i])
			return (" BGCOLOR=\"" + weekendColor + "\"");
	}
	
	return "";
}

Calendar.prototype.format_data = function(p_day) {
	var vData;
	var vMonth = 1 + this.gMonth;
	vMonth = (vMonth.toString().length < 2) ? "0" + vMonth : vMonth;
	var vMon = Calendar.get_month(this.gMonth).substr(0,3).toUpperCase();
	var vFMon = Calendar.get_month(this.gMonth).toUpperCase();
	var vY4 = new String(this.gYear);
	var vY2 = new String(this.gYear.substr(2,2));
	var vDD = (p_day.toString().length < 2) ? "0" + p_day : p_day;

	switch (this.gFormat) {
		case "YYYY-MM-DD" :
			vData = vY4 + "-" + vMonth + "-" + vDD;
			break;						
		case "YYYY\/MM\/DD" :
			vData = vY4 + "\/" + vMonth + "\/" + vDD;
			break;						
		case "MM\/DD\/YYYY" :
			vData = vMonth + "\/" + vDD + "\/" + vY4;
			break;
		case "MM\/DD\/YY" :
			vData = vMonth + "\/" + vDD + "\/" + vY2;
			break;
		case "MM-DD-YYYY" :
			vData = vMonth + "-" + vDD + "-" + vY4;
			break;
		case "MM-DD-YY" :
			vData = vMonth + "-" + vDD + "-" + vY2;
			break;

		case "DD\/MON\/YYYY" :
			vData = vDD + "\/" + vMon + "\/" + vY4;
			break;
		case "DD\/MON\/YY" :
			vData = vDD + "\/" + vMon + "\/" + vY2;
			break;
		case "DD-MON-YYYY" :
			vData = vDD + "-" + vMon + "-" + vY4;
			break;
		case "DD-MON-YY" :
			vData = vDD + "-" + vMon + "-" + vY2;
			break;

		case "DD\/MONTH\/YYYY" :
			vData = vDD + "\/" + vFMon + "\/" + vY4;
			break;
		case "DD\/MONTH\/YY" :
			vData = vDD + "\/" + vFMon + "\/" + vY2;
			break;
		case "DD-MONTH-YYYY" :
			vData = vDD + "-" + vFMon + "-" + vY4;
			break;
		case "DD-MONTH-YY" :
			vData = vDD + "-" + vFMon + "-" + vY2;
			break;

		case "DD\/MM\/YYYY" :
			vData = vDD + "\/" + vMonth + "\/" + vY4;
			break;
		case "DD\/MM\/YY" :
			vData = vDD + "\/" + vMonth + "\/" + vY2;
			break;
		case "DD-MM-YYYY" :
			vData = vDD + "-" + vMonth + "-" + vY4;
			break;
		case "DD-MM-YY" :
			vData = vDD + "-" + vMonth + "-" + vY2;
			break;

		case "YYYYMMDD" :
			vData = vY4 + vMonth + vDD;
			break;

		default :
			vData = vY4 + vMonth + vDD;
			break;
	}

	return vData;
}

function Build(p_item, p_month, p_year, p_format) {
	var p_WinCal = ggWinCal;
	gCal = new Calendar(p_item, p_WinCal, p_month, p_year, p_format);

	// Customize your Calendar here..
	gCal.gBGColor="#FFFDF8";
	gCal.gLinkColor="#352C00";
	gCal.gTextColor="#352C00";
	gCal.gHeaderColor="#352C00";

	// Choose appropriate show function
	if (gCal.gYearly)	gCal.showY();
	else	gCal.show();
}

function show_calendar(setName) {
	/* 
		p_month : 0-11 for Jan-Dec; 12 for All Months.
		p_year	: 4-digit year
		p_format: Date format (mm/dd/yyyy, dd/mm/yy, ...)
		p_item	: Return Item.
	*/

	var obj = eval(setName)

	if ( obj.value != "" )
	{
		obj.value = "";
		return;
	}



	isNav = (navigator.appName.indexOf("Netscape") != -1) ? true : false;
	// alert(isNav);
	var ddomain = this.document.domain;
	

	//윤추가
	setDay = eval(setName).value;

	//-- 양병섭 추가
	setCurrentDate(setName) ;
	
	//alert(setDay);
	
	if(setDay == "")
	{
		if(eval(setName).pIsDate)
		{
			if(eval(setName).pIsDate != "")
			{
				setDay = eval(setName).pIsDate;
			}
		}
	}
	

	var p_month;
	var p_year;

	p_item = arguments[0];
	if (arguments[1] == null)
	{
		
		//윤추가
		if (setDay != "" && setDay != null && Number(setDay.length) == 10)
		{
			try
			{
				p_month	= Number((setDay).substring(5,7)) - 1;				
			}
			catch(exception)
			{
				p_month = new String(gNow.getMonth());
			}

		}
		else
		{
			p_month = new String(gNow.getMonth());
		}
	}
	else
	{
		p_month = arguments[1];
	}

	if (arguments[2] == "" || arguments[2] == null)
	{
		if (setDay != "" && setDay != null && Number(setDay.length) == 10)
		{
			try
			{
				p_year	= (setDay).substring(0,4);

			}
			catch(exception)
			{
				p_year = new String(gNow.getFullYear().toString());
			}
		}
		else
		{
			p_year = new String(gNow.getFullYear().toString());
		}
	}
	else
	{
		p_year = arguments[2];
	}


	if (arguments[3] == null)
		p_format = "YYYY-MM-DD";
	else
		p_format = arguments[3];


	vWinCal = window.open("", "Calendar","width=210,height=210,status=no,resizable=no,top=320,left=438");


	vWinCal.opener = self;
	ggWinCal = vWinCal;

	Build(p_item, p_month, p_year, p_format);
}
/*
Yearly Calendar Code Starts here
*/
function show_yearly_calendar(p_item, p_year, p_format) {
	// Load the defaults..
	if (p_year == null || p_year == "")
	{
		p_year = new String(gNow.getFullYear().toString());
		//p_year = cur_year; //-- 양병섭 추가
	}
	
	if (p_format == null || p_format == "")
	{
		p_format = "YYYY-MM-DD";
	}

	var vWinCal = window.open("", "Calendar", "scrollbars=no");
	vWinCal.opener = self;
	ggWinCal = vWinCal;

	Build(p_item, null, p_year, p_format);
}

var cur_year  = 0 ;
var cur_month = 0 ;
var cur_day   = 0 ;

function setCurrentDate(lsObj)
{
	var lsCurObj = eval(lsObj)
	
	//-- 해당되는 항목이 있으면 해당날짜를 가지고 오늘날짜를 세팅한다.
	if(lsCurObj.pIsDate)
	{
		var lsCurDate = lsCurObj.pIsDate ;
		if(lsCurDate !="")
		{
			//-- 2002-12-15
			//-- 1234567890
			cur_year  = eval(lsCurDate.substring(0,4));
			cur_month = eval(lsCurDate.substring(5,7))-1;
			cur_day   = eval(lsCurDate.substring(8,10)); 
		}
		//-- 항목은 있지만 값이 없으면 피씨 날짜로 세팅한다.
		else
		{
			cur_year  = gNow.getFullYear();
			cur_month = gNow.getMonth() ;
			cur_day   = gNow.getDate() ; 
		}
	}
	//-- 해당되는 항목이 없으면 PC날짜를 보여준다.
	else
	{
		cur_year  = gNow.getFullYear();
		cur_month = gNow.getMonth() ;
		cur_day   = gNow.getDate() ; 
	}
}