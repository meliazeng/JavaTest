package abd.com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DBUtil {

	private static Connection conn1 = null;

	private static Connection conn2 = null;

	private static Object object = new Object();

	public static Connection getConn() {
		System.out.println("********新建一个长连接************");
		synchronized (object) {
			Connection conn = null;
			PropertyUtil prop = PropertyUtil.getInstance();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String url = "jdbc:mysql://" + PropertyUtil.getProperty("params.properties", "ip_ivas") + ":"
						+ PropertyUtil.getProperty("params.properties", "port_ivas") + "/"
						+ PropertyUtil.getProperty("params.properties", "dbname_ivas");
				String username = PropertyUtil.getProperty("params.properties", "username_ivas");
				String password = PropertyUtil.getProperty("params.properties", "password_ivas");
				conn = DriverManager.getConnection(url, username, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return conn;
		}
	}

	public static void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(PreparedStatement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void setConn2(Connection conn2) {
		DBUtil.conn2 = conn2;
	}

	public static void rollback(Connection conn) {
		try {
			if (conn != null)
				conn.rollback();
		} catch (Exception e1) {
		}
	}

}
