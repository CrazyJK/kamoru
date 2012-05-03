<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*,java.util.*,kamoru.bg.net.server.biz.*"%>
<%
	String strId = request.getParameter("id");

	BloodGlucoseBiz biz = new BloodGlucoseBiz();
	List idList = biz.getBloodSugarIdList();
	List dataList = new ArrayList();
	if (strId != null && strId.trim().length() > 0) {
		dataList = biz.getBloodSugarList(strId);
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>BloodSugar List</title>
</head>
<body>
	<form>
		<table>
			<tr>
				<td>Select ID : 
					<select name="id">
						<option value=''>== 선택 ==</option>
						<% for(int i=0; i<idList.size(); i++) {
								Map idMap = (HashMap)idList.get(i); 
								String currId = (String)idMap.get("ID");%>
						<option value='<%=currId %>' <%=currId.equals(strId) ? "SELECTED" : "" %>><%=currId %></option>
						<%	} %>
					</select>
					<input type="submit" value="View">
				</td>
			</tr>
			<tr>
				<td>
					<table border=1 cellspacing=0>
						<tr>
							<td width='200' align="center">측정일자</td>
							<td width='100' align="center">혈당값</td>
						</tr>
						<% for(int i=0; i<dataList.size(); i++) { 
							Map data = (HashMap)dataList.get(i);
						%>
						<tr>
							<td align="center">
								20<%=data.get("YEAR") %>-<%=data.get("MONTH") %>-<%=data.get("DAY") %>
								<%=data.get("TIME") %>:<%=data.get("MINUTE") %>:<%=data.get("SECOND") %>
							</td>
							<td align="center"><%=data.get("BLOODSUGARVALUE") %></td>
						</tr>
						<% } %>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>