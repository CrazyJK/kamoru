<%@ page language="java" import="java.util.*" %>

<%
Enumeration applicationAttrNames = application.getAttributeNames();
Enumeration sessionAttrNames = session.getAttributeNames();
Cookie [] cookies = request.getCookies();

Enumeration sessionIDEnum = session.getSessionContext().getIds();
Vector sessionIDs = new Vector();
StringBuffer sessionIDsBuffer = new StringBuffer();

while (sessionIDEnum.hasMoreElements()) 
{
    String sessionID = (String) sessionIDEnum.nextElement();
    sessionIDs.addElement(sessionID);
    sessionIDsBuffer.append(sessionID);
    sessionIDsBuffer.append(';');
}
%>

<html>
<head>
<title>Web Application Varibales</title>
<style>
<!--
th
{
    background-color: Lavender;
}
td
{
    background-color: CornSilk;
}
-->
</style>
</head>
<body>

<a name="client"></a>
<h1>Current Client</h1>

<table border="1">
    <tr>
        <th>RemoteAddr</th>
        <td><%=request.getRemoteAddr()%></td>
    </tr>
    <tr>
        <th>RemoteHost</th>
        <td><%=request.getRemoteHost()%></td>
    </tr>
    <tr>
        <th>RemoteUser</th>
        <td><%=request.getRemoteUser()%></td>
    </tr>
	<tr>
		<th>RequestedSessionId</th>
		<td><%= request.getRequestedSessionId() %></td>
	</tr>
	<tr>
		<th>RequestURI</th>
		<td><%= request.getRequestURI() %></td>
	</tr>
	<tr>
		<th>RequestURL</th>
		<td><%= request.getRequestURL().toString() %></td>
	</tr>
</table>

<a name="session"></a>
<h1>Current Sessions</h1>

This information cannot be exact
because the APIs related to this information are deprecated. 
As of Java(tm) Servlet API 2.1 for security reasons, with no replacement. 

<table border="1">
    <tr>
        <th>Current Sessions Count: </th>
        <td><%=sessionIDs.size()%></td>
    </tr>
    <tr>
        <th>Session IDs:</th>
        <td>
            <%=sessionIDsBuffer.toString()%>
        </td>
    </tr>
</table>

<h1>Session Variables</h1>

<table border="1">
    <tr>
        <th>Created At:</th>
        <td><%=(new Date(session.getCreationTime())).toString()%></td>
    </tr>
    <tr>
        <th>ID:</th>
        <td><%=session.getId()%></td>
    </tr>
    <tr>
        <th>Last Accessed At:</th>
        <td><%=(new Date(session.getLastAccessedTime())).toString()%></td>
    </tr>
    <tr>
        <th>Max Interactive Interval:</th>
        <td><%=session.getMaxInactiveInterval()%> (seconds)</td>
    </tr>
</table>

<br>

<table border="1">
    
    <tr>
        <th>Name</th>
        <th>Type</th>
        <th>Value</th>
    </tr>

    <%
    while (sessionAttrNames.hasMoreElements())
    {
        String name = (String) sessionAttrNames.nextElement();
        Object value = session.getAttribute(name);
        Class type = value.getClass();
    %>
        <tr>
            <td><%=name%></td>
            <td><%=type.getName()%></td>
            <td><%=getEscapedStr(value.toString())%></td>
        </tr>
    <% 
    } 
    %>
</table>

<h1>Cookie Variables</h1>

<table border="1">
    <tr>
        <th>Name</th>
        <th>Value</th>
    </tr>
    <% for (int i = 0; i != cookies.length; ++i) { %>
        <tr>
            <td><%=cookies[i].getName()%></td>
            <td><%=cookies[i].getValue()%></td>
        </tr>
    <% } %>
</table>

<a name="application"></a>
<h1>Application Variables</h1>

<table border="1">
    
    <tr>
        <th>Name</th>
        <th>Type</th>
        <th>Value</th>
    </tr>

    <%
    while (applicationAttrNames.hasMoreElements())
    {
        String name = (String) applicationAttrNames.nextElement();
        Object value = application.getAttribute(name);
        Class type = value.getClass();
    %>
        <tr>
            <td><%=name%></td>
            <td><%=type.getName()%></td>
            <td><%=value.toString()%></td>
        </tr>
    <% 
    } 
    %>
</table>

</body>
</html>
<%!
    public String getEscapedStr(String value)
    {
        char keys[] = {
            '&', '"', '<', '>'
        };
        String reps[] = {
            "&amp;", "&quot;", "&lt;", "&gt;"
        };
        return replaceCharToStr(keys, reps, value);
    }

    public String replaceCharToStr(char keys[], String reps[], String strData)
    {
        if(strData == null)
            return null;
        int keylen = keys.length;
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for(int iend = strData.length(); i < iend; i++)
        {
            char ch = strData.charAt(i);
            boolean found = false;
            int j = 0;
            do
            {
                if(j >= keylen)
                    break;
                if(ch == keys[j])
                {
                    sb.append(reps[j]);
                    found = true;
                    break;
                }
                j++;
            } while(true);
            if(!found)
                sb.append(ch);
        }

        return sb.toString();
    }

%>