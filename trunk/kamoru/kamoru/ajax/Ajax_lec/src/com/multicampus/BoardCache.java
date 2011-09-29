package com.multicampus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardCache {
    static String board_key = "";
    static HashMap map = new HashMap();
    
    public static void reload() {
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)envContext.lookup("jdbc/MySQLDB");
            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select board_id from t_board order by board_id desc limit 0, 10");
            board_key  = "";
            
            map.clear();
            while (rs.next()) {
                map.put(rs.getString(1), rs.getString(1));
                board_key += rs.getString(1) + "_";
            }
            System.out.println("reload()->board_key = " + board_key);
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public static boolean update(String board_id) {
        if (true == map.containsKey(board_id)) {
            reload();
            return true;
        }
        return false;
    }
    
    public static boolean isEqualsBoardKey(String key) {
        return board_key.equals(key);
    }
    
}
