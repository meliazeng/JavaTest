package abd.com.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import java.sql.SQLException;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

public class ConnectionSource {
	private static BasicDataSource dataSource = null;
	private static BasicDataSource ireportSource = null;

	public ConnectionSource() {
	}

	public static void init() {

		if (dataSource != null) {
			try {
				dataSource.close();
			} catch (Exception e) {
				//
			}
			dataSource = null;
		}
/*
		String serverName = null;
        String portNumber = null;
        String databaseName = null;
        String username = null;
        String password = null;
		try {

			Properties p = new Properties();
			serverName = PropertyUtil.getProperty("params.properties", "ip_ivas");
			portNumber = PropertyUtil.getProperty("params.properties", "port_ivas");
			databaseName = PropertyUtil.getProperty("params.properties", "dbname_ivas");
			username = PropertyUtil.getProperty("params.properties", "username_ivas");
			password = PropertyUtil.getProperty("params.properties", "password_ivas");

			SQLServerDataSource ds = new SQLServerDataSource();
            ds.setServerName(serverName);
            ds.setPortNumber(Integer.parseInt(portNumber));
            ds.setDatabaseName(databaseName);
            ds.setUser(username);
            ds.setPassword(password);
		    String connectionUrl = "jdbc:sqlserver://" + PropertyUtil.getProperty("params.properties", "ip_ivas") + ":"
				 + PropertyUtil.getProperty("params.properties", "port_ivas") + ";" + "databaseName="
                 + PropertyUtil.getProperty("params.properties", "dbname_ivas") 
				 + ";username=" + PropertyUtil.getProperty("params.properties", "username_ivas")
				 + ";password=" + PropertyUtil.getProperty("params.properties", "password_ivas") + ";";

		    dataSource = (BasicDataSource) BasicDataSourceFactory.create
		    
        	// Establish the connection.
        	try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement()) {
				

            	System.out.println();
            	System.out.println("Connection established successfully.");
        	}
		} catch (Exception e) {
            e.printStackTrace();
        }
*/

		try {
			Properties p = new Properties();
//			p.setProperty("driverClassName", "");
			p.setProperty("driverClassName", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
			p.setProperty("url",
					"jdbc:sqlserver://" + PropertyUtil.getProperty("params.properties", "ip_ivas") + ":"
							+ PropertyUtil.getProperty("params.properties", "port_ivas"));
			p.setProperty("databaseName", PropertyUtil.getProperty("params.properties", "dbname_ivas"));
			p.setProperty("username",  PropertyUtil.getProperty("params.properties", "username_ivas"));
			p.setProperty("password", PropertyUtil.getProperty("params.properties", "password_ivas"));

			p.setProperty("maxActive", "100");
			p.setProperty("maxIdle", "10");
			p.setProperty("maxWait", "1000");
			p.setProperty("removeAbandoned", "false");
			p.setProperty("removeAbandonedTimeout", "120");
			p.setProperty("logAbandoned", "true");

			dataSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(p);

		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

	public static synchronized Connection getConnection() throws SQLException {
		if (dataSource == null) {
			init();
		}
		Connection conn = null;
		if (dataSource != null) {
			conn = dataSource.getConnection();
		}
		return conn;
	}

	public static synchronized Connection getIreportConnection() throws SQLException {
		if (ireportSource == null) {
			initIreport();
		}
		Connection conn = null;
		if (dataSource != null) {
			conn = ireportSource.getConnection();
		}
		return conn;
	}

	private static void initIreport() {
		// TODO Auto-generated method stub
		if (ireportSource != null) {
			try {
				ireportSource.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ireportSource = null;
		}

		try {
			Properties pp = new Properties();
			pp.setProperty("driverClassName", "com.mysql.jdbc.Driver");
			pp.setProperty("url",
					"jdbc:mysql://" + PropertyUtil.getProperty("params.properties", "ip_ireport") + ":"
							+ PropertyUtil.getProperty("params.properties", "port_ireport") + "/"
							+ PropertyUtil.getProperty("params.properties", "dbname_ireport")+"?autoReconnect=true&failOverReadOnly=false");
			pp.setProperty("username", PropertyUtil.getProperty("params.properties", "username_ireport"));
			pp.setProperty("password", PropertyUtil.getProperty("params.properties", "password_ireport"));
			pp.setProperty("maxActive", "100");
			pp.setProperty("initialSize", "100");
//			pp.setProperty("maxTotal", "100");
			pp.setProperty("maxIdle", "10");
			pp.setProperty("maxWait", "1000");
//			pp.setProperty("maxWaitMillis", "5000");
			pp.setProperty("removeAbandoned", "false");
			pp.setProperty("removeAbandonedTimeout", "120");
			pp.setProperty("testOnBorrow", "true");
			pp.setProperty("testWhileIdle", "true");
			pp.setProperty("logAbandoned", "true");

			ireportSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(pp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}