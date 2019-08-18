package abd.com.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

public class Common {

	public static Map<String, IoSession> sessionMap = new HashMap<String, IoSession>();

	public static Map<Long, List<Byte>> messageMap = new HashMap<Long, List<Byte>>();

}
