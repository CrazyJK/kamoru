package com.multicampus;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

public class ApplicationListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent e) {
        System.out.println("[hello] Before ApplicationListener.contextInitialized()...");

        ServletContext context = e.getServletContext();
        //최초에 상단 10건의 자료에 대한 id를 map을 구성한다.
        BoardCache.reload();
        
        System.out.println("[hello] After ApplicationListener.contextInitialized()...");
    }
    
    public void contextDestroyed(ServletContextEvent e) {
        System.out.println("[hello] ApplicationListener.contextDestroyed()...");
    }
}
