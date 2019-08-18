
package abd.com.util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import abd.com.dao.JDBCDao;

public class DataTabelUtil {

	private static Map<String, String> tabelMap = new HashMap<String, String>();
	private static Object object = new Object();

	public static void put(String tableName) {
		synchronized (object) {
			tabelMap.put(tableName, tableName);
		}
	}

	/**
	 * @param tableName
	 */
	public static void check(String tableName) {
		synchronized (object) {
			if (tabelMap.get(tableName) == null) {
				try {
					new JDBCDao().createTable(tableName);
//					new JDBCDao().createTable2(tableName);
					tabelMap.put(tableName, tableName);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
}
