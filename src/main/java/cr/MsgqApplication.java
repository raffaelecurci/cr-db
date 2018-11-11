package cr;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;

import cr.annotation.QueueDefinition;

@QueueDefinition(queues = { "db"}, rpcServer= {"db"})
@RefreshScope
@SpringBootApplication
@EnableRabbit
@EnableDiscoveryClient
public class MsgqApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MsgqApplication.class, args);
	}
	
	
}

