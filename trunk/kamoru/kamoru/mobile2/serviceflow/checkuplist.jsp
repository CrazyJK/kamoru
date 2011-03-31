<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hs.frmwk.db.*, java.util.*" %>
<%@ include file="../common.jsp" %>
<%
//variable
String currYear  = new java.text.SimpleDateFormat("yyyy").format(new java.util.Date());
String currMonth = new java.text.SimpleDateFormat("MM").format(new java.util.Date());
String currDay   = new java.text.SimpleDateFormat("dd").format(new java.util.Date());

ArrayList list = new ArrayList();
try
{
	ArrayList param = new ArrayList();
	param.add(currYear + currMonth);
	param.add("%," + Integer.parseInt(currMonth) + ",%");
	param.add(currYear + "-" + currMonth + "-" + currDay);
	param.add(currYear + "-" + currMonth + "-" + currDay);
	list = executeQuery(3201, param, requestURI);
}
catch(Exception e)
{
	e.printStackTrace();
}

// data processing
HashMap personMap 		= new HashMap();
ArrayList listByPerson 	= new ArrayList();
String prevPerson 		= "";
for(int i=0; i<list.size(); i++)
{
	HashMap data 	  = (HashMap)list.get(i);
	String currPerson = (String)data.get("LOGIN_ID");
	if(!prevPerson.equals(currPerson))
	{
		personMap.put(prevPerson, listByPerson);
		listByPerson = new ArrayList();
	}
	prevPerson   = currPerson;
	listByPerson.add(data);
}
%>
<div id="checkuplist">
	<div class="toolbar">
		<h1>정기 방문 현황</h1>
		<a class="back" href="#">이전</a>
	</div>
<%
	if(personMap.size() > 0)
	{
		Object[] keyArr = personMap.keySet().toArray();
		Arrays.sort(keyArr);
		for(int k=0; k<keyArr.length; k++)
		{
%>
	<h2 style="color:white;"><%=keyArr[k] %></h2>
	<ul class="rounded">
<%
			listByPerson = (ArrayList)personMap.get(keyArr[k]);
			for(int i=0; i<listByPerson.size(); i++)
			{
				HashMap data 	 = (HashMap)listByPerson.get(i);
				String cmpnyName = (String)data.get("CMPNY_NAME");
				String product   = (String)data.get("PRODUCT_NAME");
				String cnt		 = (String)data.get("CNT");
%>
		<li><font color="<%=cnt.equals("1") ? "white" : "gray" %>"><%=cmpnyName + " - " + product %></font></li>
<%
			}
%>
	</ul>
<%
		}
	}
%>	
</div>