package abd.com.mina.handle;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import abd.com.common.Common;
import abd.com.dao.DataRecordDao;
import abd.com.pojo.TouchModel;
import abd.com.util.ConvertUtil;
import abd.com.util.DataTabelUtil;
import abd.com.util.DateUtil;

public class MinaServerHanlder extends IoHandlerAdapter {
	private final static Logger logger = LoggerFactory.getLogger(MinaServerHanlder.class);

	public void sessionCreated(IoSession session) {
	}

	public void sessionOpened(IoSession session) throws Exception {
	}

	// å½“æ”¶åˆ°äº†å®¢æˆ·ç«¯å�‘é€�çš„æ¶ˆæ�¯å�Žä¼šå›žè°ƒè¿™ä¸ªå‡½æ•°
	public void messageReceived(IoSession session, Object message) throws Exception {
		byte[] bytes = (byte[]) message;
		if (bytes != null) {
			logger.info("{},æ”¶åˆ°:{}", session.getId(), ConvertUtil.toHexString(bytes));
			String mac = analysis(bytes);
			dealSession(session, mac);
		}

	}

	private void dealSession(IoSession session, String mac) {
		// TODO Auto-generated method stub
		if (Common.sessionMap.containsKey(mac)) {
			IoSession s = Common.sessionMap.get(mac);
			if (s.getId() != session.getId()) {
				s.close(true);
				Common.sessionMap.put(mac, session);
			}
		} else {
			Common.sessionMap.put(mac, session);
		}
	}

	private String analysis(byte[] bytes) {
		// TODO Auto-generated method stub
		String mac = "";
		if (bytes.length == 26 && ((bytes[0] & 0xFF) == 0x55) && ((bytes[1] & 0xFF) == 0x55)
				&& ((bytes[24] & 0xFF) == 0xAA) && ((bytes[25] & 0xFF) == 0xAA)) {
			byte b = ConvertUtil.getCheckSum(bytes, 2, 21);
			if (bytes[23] == b) {
				TouchModel touch = new TouchModel();

				touch.setDeviceId(ConvertUtil.toHexString(bytes, 2, 4).toUpperCase());
				touch.setFieldStrength(bytes[6]);
				touch.setVol(ConvertUtil.getFloat(bytes, 7));
				touch.setRecordTime(ConvertUtil.getTime(bytes, 11));
				mac = ConvertUtil.toMacString(bytes, 17, 6);
				touch.setMac(mac);
				touch.setMessage(ConvertUtil.toHexString(bytes));
				logger.info("{}", touch);
				String tableName = "Sensor.TouchLog";
				//String tableName = "t" + DateUtil.getDateStrByFormet(touch.getRecordTime(), "yyyy_MM") + "_touch_"
				//+ touch.getDeviceId();
//				String tableNameBak = "t" + DateUtil.getDateStrByFormet(touch.getRecordTime(), "yyyy_MM") + "_touch_bak_"
//						+ touch.getDeviceId();
				//TouchQueue.offer(touch);
//				DataTabelUtil.check(tableName);
//				DataTabelUtil.check(tableNameBak);
//				new DataRecordDao().insertPassenger(touch);
				new DataRecordDao().insertRecord(touch);
			} else {
				logger.info("æ ¡éªŒå’Œä¸�æ­£ç¡®");
			}
		} else {
			logger.info("é•¿åº¦ä¸�æ­£ç¡®æˆ–é¦–æœ«ä½�ä¸�æ­£ç¡®");
		}
		return mac;
	}

	// å�‘é€�æˆ�åŠŸå�Žä¼šå›žè°ƒçš„æ–¹æ³•
	public void messageSent(IoSession session, Object message) {
		// session.close(true);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		super.exceptionCaught(session, cause);
		logger.error("error", cause);
		session.close(true);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
	}
}