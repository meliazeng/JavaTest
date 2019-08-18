package abd.com.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import abd.com.dao.DataRecordDao;
import abd.com.dao.JDBCDao;
import abd.com.pojo.TouchModel;
import abd.com.pojo.TouchStatus;
import abd.com.util.DateUtil;
import abd.com.util.PropertyUtil;

public class DealTouch extends TimerTask {

	private Logger logger = LoggerFactory.getLogger(DealTouch.class);

	@Override
	public void run() {
		try {
			logger.info("Ã¥Â¤â€žÃ§ï¿½â€ Ã¤Â¼Â Ã¦â€žÅ¸Ã¥â„¢Â¨Ã¨Â§Â¦Ã§Â¢Â°Ã¥Â¼â‚¬Ã¥Â§â€¹");
	//		Map<String, TouchStatus> map = new DataRecordDao().getTouchStatus();
	//		Map<String,Param> paramMap = new DataRecordDao().getTouchParam();
	
			Map<String, TouchStatus> recordMap = JDBCDao.getDealRecord("select deviceId, status, recordTime from Sensor.touchstatus ");
			String now = DateUtil.formatDateByFormet(new Date(), "yyyy-MM");
			List<String> tableName = null;
			String ivasSchema = PropertyUtil.getProperty("params.properties", "dbname_ivas");
			if(recordMap!=null&&recordMap.size()>0){ /*
				List<String> tableList = JDBCDao.getTable("select table_name,table_schema from information_schema.tables where table_schema = '" + ivasSchema+"' and table_name like 't"+now.replace("-", "_")+"_touch_bak_%'");
				for(String device:recordMap.keySet()){
					tableName = new ArrayList<String>();
					tableName.add("t" + recordMap.get(device).getRecordTime().substring(0, 8).replaceAll("-", "_")+"touch_bak_"+device);
					if(tableList!=null&&tableList.contains("t" + recordMap.get(device).getRecordTime().substring(0, 8).replaceAll("-", "_")+"touch_bak_"+device.toLowerCase())){
						tableList.remove("t" + recordMap.get(device).getRecordTime().substring(0, 8).replaceAll("-", "_")+"touch_bak_"+device.toLowerCase());
					}
					if(!recordMap.get(device).getRecordTime().substring(0, 7).equals(now)){
						tableName.add("t" + now.replaceAll("-", "_")+"_touch_bak_"+device);
						if(tableList!=null&&tableList.contains("t" + now.replaceAll("-", "_")+"_touch_bak_"+device.toLowerCase()) ){
							tableList.remove("t" + now.replaceAll("-", "_")+"_touch_bak_"+device.toLowerCase());
						}
					}
					List<TouchModel> bakInfos = new DataRecordDao().getBakInfos(tableName,recordMap.get(device).getRecordTime());
					if(bakInfos!=null&&bakInfos.size()>0){
						Collections.sort(bakInfos, new Comparator<TouchModel>()
						{
							@Override
							public int compare(TouchModel o1, TouchModel o2) {
								return o2.getRecordTime().compareTo(o1.getRecordTime());
							}
						});
						new DataRecordDao().saveIvas(recordMap.get(device), bakInfos);
					}
				} 
				if(tableList!=null&&tableList.size()>0){
					for(String table:tableList){
						tableName = new ArrayList<String>();
						tableName.add(table);
						List<TouchModel> bakInfos = new DataRecordDao().getBakInfos(tableName);
						if(bakInfos!=null&&bakInfos.size()>0){
							Collections.sort(bakInfos, new Comparator<TouchModel>()
							{
								@Override
								public int compare(TouchModel o1, TouchModel o2) {
									return o2.getRecordTime().compareTo(o1.getRecordTime());
								}
							});
							new DataRecordDao().saveIvas(bakInfos.get(0).getDeviceId(), bakInfos);
						}
					}
				} */
			}else{
				/*
				List<String> tableList = JDBCDao.getTable("select table_name,table_schema from information_schema.tables where table_schema = '" + ivasSchema+"' and table_name like 't________touch_bak_%'");
				if(tableList!=null&&tableList.size()>0){
					for(String table:tableList){
						tableName = new ArrayList<String>();
						tableName.add(table);
						List<TouchModel> bakInfos = new DataRecordDao().getBakInfos(tableName);
						if(bakInfos!=null&&bakInfos.size()>0){
							Collections.sort(bakInfos, new Comparator<TouchModel>()
							{
								@Override
								public int compare(TouchModel o1, TouchModel o2) {
									return o2.getRecordTime().compareTo(o1.getRecordTime());
								}
							});
							new DataRecordDao().saveIvas(bakInfos.get(0).getDeviceId(), bakInfos);
						}
					}
				}*/
			}
			logger.info("Ã¥Â¤â€žÃ§ï¿½â€ Ã¥Â®Å’Ã¦Ë†ï¿½");
			// TODO Auto-generated method stub
			/*List<TouchModel> insertList = new ArrayList<TouchModel>();
			while (!TouchQueue.isEmpty()) {
	
				try {
					List<TouchModel> list = TouchQueue.poll();
					insertList.clear();
					for (TouchModel touch : list) {
	//					Param p = paramMap.get(touch.getDeviceId());
	//					int stopSeconds = 30;
	//					if(p!=null){
	//						stopSeconds = p.getStopSeconds();
	//					}
	//					
	//					
	//					if (map.containsKey(touch.getDeviceId())) {
	//						TouchStatus status = map.get(touch.getDeviceId());
	//						if (status.getStatus() == 1) {
	//							status.setRecordTime(touch.getRecordTime());
	//							status.setStatus(2);// 2Ã¦â€¹Â¿Ã¨ÂµÂ·
	//							map.put(touch.getDeviceId(), status);
	//							touch.setStatus(2);//
	//							insertList.add(touch);
	//						} else {
	//							if (DateUtil.getdiff(status.getRecordTime(), touch.getRecordTime()) > stopSeconds) {
	//								TouchModel t = new TouchModel();
	//								t.setDeviceId(touch.getDeviceId());
	//								t.setFieldStrength(touch.getFieldStrength());
	//								t.setMac(touch.getMac());
	//								t.setMessage("");
	//								t.setRecordTime(
	//										DateUtil.getNextSeconds(status.getRecordTime(), stopSeconds));
	//								t.setStatus(1);
	//								t.setVol(touch.getVol());
	//								insertList.add(t);
	//							}
	//							touch.setStatus(2);// 1Ã¦â€�Â¾Ã¤Â¸â€¹
	//							status.setRecordTime(touch.getRecordTime());
	//							status.setStatus(2);// 2Ã¦â€¹Â¿Ã¨ÂµÂ·
	//							map.put(touch.getDeviceId(), status);
	//							insertList.add(touch);
	//						}
	//					} else {
	//						TouchStatus status = new TouchStatus();
	//						status.setDeviceId(touch.getDeviceId());
	//						status.setRecordTime(touch.getRecordTime());
	//						status.setStatus(2);// 2Ã¦â€¹Â¿Ã¨ÂµÂ·
	//						touch.setStatus(2);
	//						map.put(touch.getDeviceId(), status);
	//						insertList.add(touch);
	//					}
						if (map.containsKey(touch.getDeviceId())) {
							TouchStatus status = map.get(touch.getDeviceId());
							status.setRecordTime(touch.getRecordTime());
							map.put(touch.getDeviceId(), status);
						}else{
							TouchStatus status = new TouchStatus();
							status.setDeviceId(touch.getDeviceId());
							status.setRecordTime(touch.getRecordTime());
							status.setStatus(0);
							map.put(touch.getDeviceId(), status);
						}
						
						insertList.add(touch);
					}
					new DataRecordDao().save(map, insertList);
					logger.info("{}", insertList);
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("{}", e);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("{}", e);
				}
			}*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
