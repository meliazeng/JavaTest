package abd.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abd.com.pojo.Param;
import abd.com.pojo.TouchStatus;
import abd.com.util.ConnectionSource;
import abd.com.util.DBUtil;
import abd.com.util.DataTabelUtil;

public class JDBCDao {
	/**
	 * æ‰¹é‡�æ‰§è¡Œ
	 * 
	 * @param sqlList
	 * @return
	 * @throws SQLException
	 */
	public void executeBatch(List<String> sqlList) throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		if (sqlList != null && sqlList.size() > 0) {

			conn = ConnectionSource.getConnection();
			conn.setAutoCommit(false);
			for (String sql : sqlList) {
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
			}
			conn.commit();
			if (ps != null)
				ps.close();
		}
		if (conn != null) {
			DBUtil.close(conn);
		}

	}

	public void getTable() throws SQLException {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		String searchTableSql = "SELECT table_name  from information_schema.tables";
		//String searchTableSql = "SELECT table_name  from information_schema.tables  where table_schema in( select DATABASE()) ";
		conn = ConnectionSource.getConnection();
		ps = conn.prepareStatement(searchTableSql);
		rs = ps.executeQuery();
		while (rs.next()) {
			DataTabelUtil.put(rs.getString("table_name"));
		}

		if (rs != null)
			DBUtil.close(rs);
		if (ps != null)
			DBUtil.close(ps);
		if (conn != null)
			DBUtil.close(conn);

	}

	public void createTable(String tableName) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection conn = null;

		conn = ConnectionSource.getConnection();
		String str = getCreateTableSql_ivas(tableName);
		ps = conn.prepareStatement(str);
		ps.execute();

		if (ps != null)
			DBUtil.close(ps);
		if (conn != null)
			DBUtil.close(conn);
	}
	// the script won't work in sql.
	private String getCreateTableSql_ivas(String tableName) {
/*		String str = "create table if not exists " + tableName + "( id int(11) NOT NULL AUTO_INCREMENT,"
				+ "  deviceId varchar(8) DEFAULT NULL," + "  fieldStrength int(4) DEFAULT NULL,"
				+ "  record_time varchar(20) DEFAULT NULL," + "  vol float DEFAULT NULL,"
				+ " mac varchar(25) DEFAULT NULL," + "  status int(2) DEFAULT NULL,"
				+ " message varchar(100) DEFAULT NULL," + "  PRIMARY KEY (id))";
*/
		String str="";
		return str;
	}

	public void checkTableTouchStatus() throws SQLException {
/*
		// TODO Auto-generated method stub
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		String searchTableSql = "SELECT table_name  from information_schema.tables  where table_schema = 'Sensor') ";
		conn = ConnectionSource.getConnection();
		ps = conn.prepareStatement(searchTableSql);
		rs = ps.executeQuery();
		boolean isExist = false;
		while (rs.next()) {
			if (rs.getString("table_name").equals("touchstatus")) {
				isExist = true;
			}
		}

		if (!isExist) {
			createTouchStatus();
		}

		if (rs != null)
			DBUtil.close(rs);
		if (ps != null)
			DBUtil.close(ps);
		if (conn != null)
			DBUtil.close(conn);
*/	}

	private void createTouchStatus() throws SQLException {
		// TODO Auto-generated method stub
/*
		PreparedStatement ps = null;
		Connection conn = null;

		conn = ConnectionSource.getConnection();
		String str = "create table if not exists touchstatus( deviceId varchar(8) NOT NULL,"
				+ "  status int(2) DEFAULT NULL," + "  recordTime varchar(20) DEFAULT NULL,"
				+ "  PRIMARY KEY (deviceId))";
		ps = conn.prepareStatement(str);
		ps.execute();

		if (ps != null)
			DBUtil.close(ps);
		if (conn != null)
			DBUtil.close(conn);
			*/
	}

	public List<TouchStatus> getTouchStatus() throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		String searchTableSql = "select deviceId,status,recordTime from Sensor.touchstatus";
		conn = ConnectionSource.getConnection();
		ps = conn.prepareStatement(searchTableSql);
		rs = ps.executeQuery();
		List<TouchStatus> list = new ArrayList<TouchStatus>();
		while (rs.next()) {
			TouchStatus s = new TouchStatus();
			s.setDeviceId(rs.getString("deviceId"));
			s.setRecordTime(rs.getString("recordTime"));
			s.setStatus(rs.getInt("status"));
			list.add(s);
		}

		if (rs != null)
			DBUtil.close(rs);
		if (ps != null)
			DBUtil.close(ps);
		if (conn != null)
			DBUtil.close(conn);
		return list;
	}

	public List<Param> getTouchParam() throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		String searchTableSql = "select deviceId,stopSeconds,filterSeconds from busi_touchparam";
		conn = ConnectionSource.getIreportConnection();
		ps = conn.prepareStatement(searchTableSql);
		rs = ps.executeQuery();
		List<Param> list = new ArrayList<Param>();
		while (rs.next()) {
			Param p = new Param();
			p.setDeviceId(rs.getString("deviceId"));
			p.setStopSeconds(rs.getInt("stopSeconds"));
			p.setFilterSeconds(rs.getInt("filterSeconds"));
			list.add(p);
		}

		if (rs != null)
			DBUtil.close(rs);
		if (ps != null)
			DBUtil.close(ps);
		if (conn != null)
			DBUtil.close(conn);
		return list;
	}

//	public void createTable2(String tableName) throws SQLException {
//		// TODO Auto-generated method stub
//		// TODO Auto-generated method stub
//		PreparedStatement ps = null;
//		Connection conn = null;
//		tableName = tableName + "_status";
//		conn = ConnectionSource.getConnection();
//		String str = "create table if not exists " + tableName + "( id int(11) NOT NULL AUTO_INCREMENT,"
//				+ "  deviceId varchar(8) DEFAULT NULL," + "  record_time varchar(20) DEFAULT NULL,"
//				+ "  status int(2) DEFAULT NULL," + " fieldStrength int DEFAULT NULL," + "  PRIMARY KEY (id))";
//		ps = conn.prepareStatement(str);
//		ps.execute();
//
//		if (ps != null)
//			DBUtil.close(ps);
//		if (conn != null)
//			DBUtil.close(conn);
//	}
	
	public static Map<String,TouchStatus> getDealRecord(String sql){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Map<String,TouchStatus> map = new HashMap<String,TouchStatus>();
		TouchStatus touch = null;
		try {
			conn = ConnectionSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				touch = new TouchStatus();
				touch.setDeviceId(rs.getString("deviceId"));
				touch.setStatus(rs.getInt("status"));
				touch.setRecordTime(rs.getString("recordTime"));
				map.put(touch.getDeviceId(), touch);
			}
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			return map;
		}finally{
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(conn);
		}
	}
	
	public static List<String> getTable(String sql) {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		List<String> list = new ArrayList<String>();
		try{
			conn = ConnectionSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("table_name"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (rs != null)
				DBUtil.close(rs);
			if (ps != null)
				DBUtil.close(ps);
			if (conn != null)
				DBUtil.close(conn);
			return list;
		}
	}
}
