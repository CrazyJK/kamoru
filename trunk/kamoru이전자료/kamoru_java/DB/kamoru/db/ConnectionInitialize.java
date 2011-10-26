package kamoru.db;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;

public class ConnectionInitialize {

	public void init() {

		String driverClassName = null;
		String url = null;
		String username = null;
		String password = null;
		boolean defaultAutoCommit = true;
		boolean defaultReadOnly = false;
		int maxActive = 0;
		int maxIdle = 0;
		long maxWait = 0;


		try {

			// jdbc driver 및 기타 정의를 설정합니다.
			setupDriver(driverClassName,
					url,
					username,
					password,
					defaultAutoCommit,
					defaultReadOnly,
					maxActive,
					maxIdle,
					maxWait);
			System.out.println("Connection initialize success");
		} catch (Exception exception) {
			System.out.println("Connection initialize fail!");
			exception.printStackTrace();
		}
	}

	public void setupDriver(String driverClassName, String url,
			String username, String password, boolean defaultAutoCommit,
			boolean defaultReadOnly, int maxActive, int maxIdle, long maxWait)
			throws Exception {

		try {
			// jdbc class를 로딩합니다.
			Class.forName(driverClassName);
		} catch (ClassNotFoundException classnotfoundexception) {
			System.out.println(driverClassName + " is not found");
			classnotfoundexception.printStackTrace();
			throw classnotfoundexception;
		}

		// 커넥션 풀로 사용할 commons-collections의 genericOjbectPool을 생성합니다.
		GenericObjectPool connectionPool = new GenericObjectPool(null);
		connectionPool.setMaxActive(maxActive);
		connectionPool.setMaxIdle(maxIdle);
		connectionPool.setMaxWait(maxWait);

		// 풀이 커넥션을 생성하는데 사용하는 DriverManagerConnectionFactory를 생성합니다.
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
				url, username, password);

		// ConnectionFactory의 래퍼 클래스인 PoolableConnectionFactory를 생성한다
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
				connectionFactory, connectionPool, null, null, defaultReadOnly,
				defaultAutoCommit);

		// 마지막으로 PoolingDriver 자신을 로딩한다
		Class.forName("org.apache.commons.dbcp.PoolingDriver");

		PoolingDriver driver = (PoolingDriver) DriverManager
				.getDriver("jdbc:apache:commons:dbcp:");

		// 그리고 풀에 등록한다. 풀이름을 "unicorn"이라고 지정하였다
		driver.registerPool("unicorn", connectionPool);
	}
}
