package abd.com.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import abd.com.bytearray.ByteArrayCodecFactory;
import abd.com.dao.DataRecordDao;
import abd.com.mina.handle.MinaServerHanlder;

public class MinaServer {
	private Logger logger = LoggerFactory.getLogger(MinaServer.class);

	public MinaServer(String socketPort) {
		// 创建ServerScoket
		SocketAcceptor acceptor = new NioSocketAcceptor(
				Runtime.getRuntime().availableProcessors() * 10);
		// acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ByteArrayCodecFactory()));
		// 添加消息处理
		acceptor.setHandler(new MinaServerHanlder());
		
		// 开启服务器
		try {
			new DataRecordDao().getTable();
			acceptor.bind(new InetSocketAddress(Integer.parseInt(socketPort)));
			logger.info("{}端口开启服务", socketPort);
		} catch (IOException e) {
			logger.error("{}",e);
		} catch (SQLException e) {
			logger.error("{}",e);
		}
	}

}
