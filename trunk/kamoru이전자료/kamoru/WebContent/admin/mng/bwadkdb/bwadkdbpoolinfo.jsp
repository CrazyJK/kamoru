<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*, com.hs.frmwk.db.BWDBHelperFactory"%>

<%
int refreshTime = request.getParameter("refreshTime") == null ? 0 : Integer.parseInt(request.getParameter("refreshTime")) ;
%>

<%
        String info = "";

        try
        {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(os);
                BWDBHelperFactory.dumpPoolInfo(ps, true);

                info = os.toString();
        }
        catch(Exception e)
        {
        	out.println(e.getMessage());
        }
%>

<html>
<head>
<title>BWADK DB Pool information</title>
<META HTTP-EQUIV="The JavaScript Source" CONTENT = "no-cache">
<link rel="stylesheet" type="text/css" href="../mng.css">
<% if(refreshTime > 0){ %>
<meta http-equiv="refresh" content="<%=refreshTime%>;url=bwadkdbpoolinfo.jsp?refreshTime=<%=refreshTime%>">
<% } %>
</head>
<body>
<form>
<table border="0">
	<thead>
		<tr>
			<th>BWADK Pool Information.  
				refresh <input type="text" name="refreshTime" value="<%= refreshTime %>" size="2" align="bottom"/>s
			</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><%=info%></td>
		</tr>
	</tbody>
</table>
</form>
</body>
</html>
