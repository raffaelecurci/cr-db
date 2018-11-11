package cr.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cr.dbo.DBManager;
import cr.generated.interf.Listener;
import cr.generated.ops.MessageListener;
import cr.interf.EncryptedMessage;

@Configuration
public class ApplicationListener {
	@Autowired
	private DBManager dbManager;
	@Bean
	public Listener listener() {
		return new Listener() {
			@Override
			public void processDb(EncryptedMessage message) {
				// TODO Auto-generated method stub
				dbManager.action(message);
			}
		};
	}
	@Bean
	public MessageListener messageListener() {
		return new MessageListener();
	}
}

