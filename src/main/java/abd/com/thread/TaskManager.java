package abd.com.thread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import abd.com.dao.DataRecordDao;
import abd.com.mina.MinaServer;
import abd.com.util.PropertyUtil;

public class TaskManager implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		String socketPort = PropertyUtil.getProperty("params.properties", "socketPort");
		if (socketPort == null || socketPort.trim().equals(""))
			return;
		String[] socketPorts = socketPort.split(",");
		if (socketPorts == null || socketPorts.length == 0)
			return;
		DataRecordDao.createTouchStatusTable();
		for (String port : socketPorts) {
			if (port == null || port.trim().equals(""))
				continue;
			new MinaServer(port);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
