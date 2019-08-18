package abd.com.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * property 文件信息读取类
 * @author Administrator
 *
 */
public class PropertyUtil {
	
	private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
	
	private static PropertyUtil instance = new PropertyUtil();
	public static PropertyUtil getInstance() {
		return instance;
	}
	
	/**
	 * 读取property文件中的信息
	 * @param fileName property文件名称，必须是完成名称，包含后缀名".properties"
	 * @param propertyName 属性名称
	 * @return
	 */
	public static String getProperty(String fileName,String propertyName){
		String rtnValue=null;
		InputStream inputStream = null;
		try {
			Properties properties = new Properties();
			inputStream = new BufferedInputStream(Thread
					.currentThread().getContextClassLoader()
					.getResourceAsStream(fileName));
			properties.load(inputStream); //加载config.properties配置文件
			rtnValue = properties.getProperty(propertyName);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}finally{
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			finally{
				return rtnValue;
			}
		}
	}
}
