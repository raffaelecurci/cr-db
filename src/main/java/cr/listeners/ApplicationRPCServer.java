package cr.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cr.dbo.DBManager;
import cr.generated.interf.ProcessRPCResponse;
import cr.interf.EncryptedMessage;

@Configuration
public class ApplicationRPCServer {
	@Autowired
	private DBManager dbManager;
	@Bean
	public ProcessRPCResponse ProcessRPCResponse() {
		return new ProcessRPCResponse() {
			@Override
			public EncryptedMessage processDbResponseRPC(EncryptedMessage message) {
				// TODO Auto-generated method stub
				return dbManager.action(message);
			}
				
		};
	}
}
