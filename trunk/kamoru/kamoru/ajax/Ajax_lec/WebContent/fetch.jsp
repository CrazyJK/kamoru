<%@ page language="java" contentType="text/html; charset=EUC-KR"  pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*,java.util.*" %>

<%

try { 
  String size = (String) request.getParameter("size");
  if (size != null && size.length() != 0) {
	Class.forName("com.mysql.jdbc.Driver").newInstance(); 
	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=70sungil");
	Statement stmt = null; 
    PreparedStatement pstmt = conn.prepareStatement("insert into t_org_telephone(area1, area2, area3, telephone, puncher_id) values (?, ?, ?, ?, ?)"); 
	ResultSet rs = null; 
    int count = 0;
    int loop = Integer.parseInt(size);
    StringBuffer strSQL = new StringBuffer();
    Random random = new Random();
    
	try {
    stmt = conn.createStatement(); 
    rs = stmt.executeQuery("SELECT count(*) FROM t_telephone"); 
        if (rs.next()) {
          count = rs.getInt(1);
        }
        rs.close();

        int [] array = new int[count];
        int [] result = new int[loop];
        int [] puncher = new int[loop];
        int idx;
        int puncher_size = 50;
        
        for (int i=0;i<array.length;i++) {
          array[i] = i*2+1;
        }
        
        for (int i=0;i<loop;i++) {
          idx = Math.abs(random.nextInt() % count);
          result[i] = array[idx];
          puncher[i] = i % puncher_size;
          array[idx] = array[count-1];
          count--;
        }
        
        strSQL.append("SELECT * FROM t_telephone where id in (");
    for (int i=0;i<loop;i++) {
      if (i == 0) {
        strSQL.append(result[i]);
      } else {
            strSQL.append(",").append(result[i]);
      }
    }
    strSQL.append(")");
//    out.println("strSQL->" + strSQL.toString());
    
    rs = stmt.executeQuery(strSQL.toString());
    int i=0;
    while (rs.next()) {
      pstmt.setString(1, rs.getString(2));
      pstmt.setString(2, rs.getString(3));
      pstmt.setString(3, rs.getString(4));
      pstmt.setString(4, rs.getString(5));
      pstmt.setString(5, "chan" + puncher[i++]);
      pstmt.executeUpdate();
    }
        
        rs.close();
    } finally { 
      if (rs != null) { 
      	try {
      		rs.close(); 
      	} catch (SQLException sqlEx) {} 
      	rs = null; 
      }
      
      if (stmt != null) { 
      	try { 
      		stmt.close(); 
      	} catch (SQLException sqlEx) { } 
      	stmt = null; 
      } 
    }
  }
} catch (Exception ex) { 
  ex.printStackTrace(); 
}

%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<form action="fetch.jsp" method="post">
  <input type="text" name="size" value="" />
  <input type="submit" value="Àü¼Û">
</form>
</body>
</html>