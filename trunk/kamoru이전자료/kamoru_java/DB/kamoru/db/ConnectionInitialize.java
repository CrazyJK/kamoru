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

			// jdbc driver �� ��Ÿ ���Ǹ� �����մϴ�.
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
			// jdbc class�� �ε��մϴ�.
			Class.forName(driverClassName);
		} catch (ClassNotFoundException classnotfoundexception) {
			System.out.println(driverClassName + " is not found");
			classnotfoundexception.printStackTrace();
			throw classnotfoundexception;
		}

		// Ŀ�ؼ� Ǯ�� ����� commons-collections�� genericOjbectPool�� �����մϴ�.
		GenericObjectPool connectionPool = new GenericObjectPool(null);
		connectionPool.setMaxActive(maxActive);
		connectionPool.setMaxIdle(maxIdle);
		connectionPool.setMaxWait(maxWait);

		// Ǯ�� Ŀ�ؼ��� �����ϴµ� ����ϴ� DriverManagerConnectionFactory�� �����մϴ�.
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
				url, username, password);

		// ConnectionFactory�� ���� Ŭ������ PoolableConnectionFactory�� �����Ѵ�
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
				connectionFactory, connectionPool, null, null, defaultReadOnly,
				defaultAutoCommit);

		// ���������� PoolingDriver �ڽ��� �ε��Ѵ�
		Class.forName("org.apache.commons.dbcp.PoolingDriver");

		PoolingDriver driver = (PoolingDriver) DriverManager
				.getDriver("jdbc:apache:commons:dbcp:");

		// �׸��� Ǯ�� ����Ѵ�. Ǯ�̸��� "unicorn"�̶�� �����Ͽ���
		driver.registerPool("unicorn", connectionPool);
	}
}
