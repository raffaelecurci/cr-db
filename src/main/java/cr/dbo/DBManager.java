package cr.dbo;

import org.springframework.stereotype.Component;

import cr.interf.EncryptedMessage;

@Component
public class DBManager {
	public EncryptedMessage action(EncryptedMessage message) {
		return message;
	}
}
