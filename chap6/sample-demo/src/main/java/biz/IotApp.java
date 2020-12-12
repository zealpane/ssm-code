package biz;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
//@EnableTransactionManagement

@EnableAsync
@SpringBootApplication
public class IotApp implements ServletContextListener {

	@Value("${iot.port.wsPush}")
	private Integer port;

	private SocketIOServer server;
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(IotApp.class, args);
	}

	@Bean
	public SocketIOServer socketIOServer() {
		Configuration config = new Configuration();
		config.setOrigin(null); // 注意如果开放跨域设置，需要设置为null而不是"*"
		config.setPort(port);
		config.setSocketConfig(new SocketConfig());
		config.setWorkerThreads(100);
		config.setAuthorizationListener(handshakeData -> true);
		server = new SocketIOServer(config);
		server.start();
		return server;
	}

   	@Override
	public void contextInitialized(ServletContextEvent sce) {
	}

	@Override
	/**
	 * 在容器关闭的时候将启动的socket端口服务也关掉
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		server.stop();
	}

	@Bean
	public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketIOServer) {
		return new SpringAnnotationScanner(socketIOServer);
	}


}
