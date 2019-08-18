package abd.com.thread;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import abd.com.dao.DataRecordDao;
import abd.com.util.PropertyUtil;

public class DealTaskManager implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		new DataRecordDao().checkTableTouchStatus();
		String time = PropertyUtil.getProperty("params.properties", "spaceTime");
		if(time==null||time.equals("")){
			time=15+"";
		}
		Timer timer = new Timer();
		timer.schedule(new DealTouch(), 1000, Integer.valueOf(time)*60*1000);//延迟1分钟启动 
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
