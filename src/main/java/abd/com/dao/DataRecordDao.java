package abd.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import abd.com.pojo.Param;
import abd.com.pojo.TouchModel;
import abd.com.pojo.TouchStatus;
import abd.com.util.ConnectionSource;
import abd.com.util.DBUtil;
import abd.com.util.DataTabelUtil;
import abd.com.util.DateUtil;

public class DataRecordDao {
	private Logger logger = LoggerFactory.getLogger(DataRecordDao.class);

	public void getTable() throws SQLException {
		new JDBCDao().getTable();
	}

	public void insertRecord(TouchModel touch) {
		// TODO Auto-generated method stub
		String ym = DateUtil.getDateStrByFormet(touch.getRecordTime(), "yyyy_MM");
		String tableName = "Sensor.TouchLog";
		//String tableName = "t" + ym + "_touch_" + touch.getDeviceId();
		DataTabelUtil.check(tableName);

		String insertSql = "";

		List<String> sqlList = new ArrayList<String>();
		insertSql = "insert into " + tableName + "(DeviceSerialNumber, fieldStrength,recordDate,vol,macAddress,message) values("
				+ "'" + touch.getDeviceId() + "',"
				+ touch.getFieldStrength() + ",'" + touch.getRecordTime() + "'," + touch.getVol() + ",'"
				+ touch.getMac() + "','"+touch.getMessage()+"')";
		sqlList.add(insertSql);
		System.out.println(insertSql);

		try {
			new JDBCDao().executeBatch(sqlList);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("{}", e);
		}
	}

	public Map<String, TouchStatus> getTouchStatus() {
		// TODO Auto-generated method stub
		Map<String, TouchStatus> map = new HashMap<String, TouchStatus>();

		try {
			List<TouchStatus> list = new JDBCDao().getTouchStatus();
			if (list.size() > 0) {
				for (TouchStatus s : list) {
					map.put(s.getDeviceId(), s);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

	public void save(Map<String, TouchStatus> map, List<TouchModel> insertList) {
		// TODO Auto-generated method stub
		List<String> statusList = new ArrayList<String>();
		statusList.add("delete from Sensor.touchstatus");
		for (String str : map.keySet()) {
			TouchStatus s = map.get(str);
			StringBuffer b = new StringBuffer();
			b.append("insert into Sensor.touchstatus(deviceId,status,recordTime) values('");
			b.append(s.getDeviceId());
			b.append("',");
			b.append(s.getStatus());
			b.append(",'");
			b.append(s.getRecordTime());
			b.append("')");
			logger.info("{}", b.toString());
			statusList.add(b.toString());
		}

		for (TouchModel s : insertList) {
			StringBuffer b = new StringBuffer();
			b.append("insert into ");
			String tableName = "Sensor.TouchLog";
			//String tableName = "t" + DateUtil.getDateStrByFormet(s.getRecordTime(), "yyyy_MM") + "_touch_"
			//		+ s.getDeviceId();
			DataTabelUtil.check(tableName);
			b.append(tableName);
			b.append("(DeviceSerialNumber,status,recordDate,fieldStrength,macAddress,vol,message) values('");
			b.append(s.getDeviceId());
			b.append("',");
			b.append(s.getStatus());
			b.append(",'");
			b.append(s.getRecordTime());
			b.append("',");
			b.append(s.getFieldStrength());
			b.append(",'");
			b.append(s.getMac());
			b.append("',");
			b.append(s.getVol());
			b.append(",'");
			b.append(s.getMessage());
			b.append("')");
			logger.info("{}", b.toString());
			statusList.add(b.toString());
		}

		try {
			new JDBCDao().executeBatch(statusList);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("{}", e.getMessage());
		}
	}

	public void checkTableTouchStatus() {
		// TODO Auto-generated method stub
		try {
			new JDBCDao().checkTableTouchStatus();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("{}", e);
		}
	}

	public Map<String, Param> getTouchParam() {
		Map<String, Param> map = new HashMap<String, Param>();

		try {
			List<Param> list = new JDBCDao().getTouchParam();
			if (list.size() > 0) {
				for (Param s : list) {
					map.put(s.getDeviceId(), s);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}
	
	public void insertPassenger(TouchModel s){
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			StringBuffer b = new StringBuffer();
			b.append("insert into ");
			String tableName = "t" + DateUtil.getDateStrByFormet(s.getRecordTime(), "yyyy_MM") + "_touch_bak_"
					+ s.getDeviceId();
			DataTabelUtil.check(tableName);
			b.append(tableName);
			b.append("(deviceId,status,record_time,fieldStrength,mac,vol,message) values('");
			b.append(s.getDeviceId());
			b.append("',");
			b.append(s.getStatus());
			b.append(",'");
			b.append(s.getRecordTime());
			b.append("',");
			b.append(s.getFieldStrength());
			b.append(",'");
			b.append(s.getMac());
			b.append("',");
			b.append(s.getVol());
			b.append(",'");
			b.append(s.getMessage());
			b.append("')");
			logger.info("{}", b.toString());
			conn = ConnectionSource.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(b.toString());
			ps.executeUpdate();
			conn.commit();
		}catch(Exception e ) {
			e.printStackTrace();
		}finally {
			DBUtil.close(ps);
			DBUtil.close(conn);
		}
	}
	
	public static void createTouchStatusTable() {
/*		String sql = "CREATE TABLE if not exists touchstatus "
				+ "( deviceId varchar(8) NOT NULL, "
				+ " status int(2) DEFAULT NULL, "
				+ " recordTime varchar(20) DEFAULT NULL,  "
				+ " PRIMARY KEY (`deviceId`) )"
				+ " ENGINE=InnoDB DEFAULT CHARSET=utf8";
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = ConnectionSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null)
				DBUtil.close(ps);
			if (conn != null)
				DBUtil.close(conn);
		} */
	}
	
	public List<TouchModel> getBakInfos(List<String> tableName, String record) {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		List<TouchModel> list = new ArrayList<TouchModel>();
		try {
			String searchTableSql = "";
			for(String table:tableName){
				if(searchTableSql.equals("")){
					searchTableSql = " select DeviceSerialNumber,fieldStrength,recordDate,macAddress,message,status,vol from " + table + " where recordDate >= '"+ record +"'";
				}else{
					searchTableSql = searchTableSql + " union all select DeviceSerialNumber,fieldStrength,recordDate,macAddress,message,status,vol from " + table + " where recordDate >= '"+ record +"'";
				}
			}
			conn = ConnectionSource.getConnection();
			ps = conn.prepareStatement(searchTableSql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				TouchModel s = new TouchModel();
				s.setDeviceId(rs.getString("DeviceSerialNumber"));
				s.setFieldStrength(rs.getInt("fieldStrength"));
				s.setRecordTime(rs.getString("recordDate"));
				s.setMac(rs.getString("macAddress"));
				s.setMessage(rs.getString("message"));
				s.setStatus(rs.getInt("status"));
				s.setVol(rs.getFloat("vol"));
				list.add(s);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null)
				DBUtil.close(rs);
			if (ps != null)
				DBUtil.close(ps);
			if (conn != null)
				DBUtil.close(conn);
			return list;
		}
	}
	
	public List<TouchModel> getBakInfos(List<String> tableName) throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		String searchTableSql = "";
		List<TouchModel> list = new ArrayList<TouchModel>();
		try {
			for(String table:tableName){
				if(searchTableSql.equals("")){
					searchTableSql = " select DeviceSerialNumber,fieldStrength,recordDate,macAddress,message,status,vol from " + table ;
				}else{
					searchTableSql = searchTableSql+" union all select DeviceSerialNumber,fieldStrength,recordDate,macAddress,message,status,vol from " + table ;
				}
			}
			conn = ConnectionSource.getConnection();
			ps = conn.prepareStatement(searchTableSql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				TouchModel s = new TouchModel();
				s.setDeviceId(rs.getString("DeviceSerialNumber"));
				s.setFieldStrength(rs.getInt("fieldStrength"));
				s.setRecordTime(rs.getString("recordDate"));
				s.setMac(rs.getString("macAddress"));
				s.setMessage(rs.getString("message"));
				s.setStatus(rs.getInt("status"));
				s.setVol(rs.getFloat("vol"));
				list.add(s);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null)
				DBUtil.close(rs);
			if (ps != null)
				DBUtil.close(ps);
			if (conn != null)
				DBUtil.close(conn);
			return list;
		}
	}
	
	public void saveIvas(TouchStatus touch, List<TouchModel> insertList) {
		// TODO Auto-generated method stub
		List<String> statusList = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		sb.append("update Sensor.touchstatus set recordtime = '"+insertList.get(0).getRecordTime()+"' where deviceId = '"+touch.getDeviceId()+"'");
		logger.info("{}", sb.toString());
		statusList.add(sb.toString());
		
		for (TouchModel s : insertList) {
			if(s.getRecordTime().equals(insertList.get(0).getRecordTime())){
				continue;
			}
			StringBuffer b = new StringBuffer();
			b.append("insert into ");
			String tableName = "Sensor.TouchLog";
			//String tableName = "t" + DateUtil.getDateStrByFormet(s.getRecordTime(), "yyyy_MM") + "_touch_"
			//		+ s.getDeviceId();
			b.append(tableName);
			b.append("(DeviceSerialNumber,status,recordDate,fieldStrength,macAddress,vol,message) values('");
			b.append(s.getDeviceId());
			b.append("',");
			b.append(s.getStatus());
			b.append(",'");
			b.append(s.getRecordTime());
			b.append("',");
			b.append(s.getFieldStrength());
			b.append(",'");
			b.append(s.getMac());
			b.append("',");
			b.append(s.getVol());
			b.append(",'");
			b.append(s.getMessage());
			b.append("')");
			logger.info("{}", b.toString());
			statusList.add(b.toString());
		}

		try {
			new JDBCDao().executeBatch(statusList);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("{}", e);
		}
	}
	
	public void saveIvas(String deviceId, List<TouchModel> insertList) {
		// TODO Auto-generated method stub
		List<String> statusList = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		sb.append("insert into Sensor.touchstatus(deviceId,recordTime) values('");
		sb.append(deviceId);
		sb.append("','");
		sb.append(insertList.get(0).getRecordTime());
		sb.append("')");
		logger.info("{}", sb.toString());
		statusList.add(sb.toString());
		
		for (TouchModel s : insertList) {
			if(s.getRecordTime().equals(insertList.get(0).getRecordTime())){
				continue;
			}
			StringBuffer b = new StringBuffer();
			b.append("insert into ");
			String tableName = "Sensor.TouchLog";
			//String tableName = "t" + DateUtil.getDateStrByFormet(s.getRecordTime(), "yyyy_MM") + "_touch_"
			//		+ s.getDeviceId();
			b.append(tableName);
			b.append("(DeviceSerialNumber,status,recordDate,fieldStrength,macAddress,vol,message) values('");
			b.append(s.getDeviceId());
			b.append("',");
			b.append(s.getStatus());
			b.append(",'");
			b.append(s.getRecordTime());
			b.append("',");
			b.append(s.getFieldStrength());
			b.append(",'");
			b.append(s.getMac());
			b.append("',");
			b.append(s.getVol());
			b.append(",'");
			b.append(s.getMessage());
			b.append("')");
			logger.info("{}", b.toString());
			statusList.add(b.toString());
		}

		try {
			new JDBCDao().executeBatch(statusList);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("{}", e);
		}
	}
}
