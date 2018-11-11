package cr.dbo;

import java.lang.reflect.Field;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import cr.MsgqApplication;
import cr.interf.EncryptedMessage;

public class TableEntity {
	
	private static String encryption=MsgqApplication.class.getAnnotation(cr.annotation.QueueDefinition.class).encryption();
	
	public JSONObject toJson() {
		Field[] fields = this.getClass().getDeclaredFields();
		  JSONObject json=new JSONObject();
		  for ( Field field : fields  ) {
		    try {
		    	field.setAccessible(true);
				json.put(field.getName(),field.get(this) );
		    } catch ( IllegalAccessException ex ) {
		      System.out.println(ex);
		    }
		  }
		  return json;
	}
	@Override
	public String toString() {
		 JSONObject json=toJson() ;
		  json.put("@class", this.getClass().getName());
		  return json.toString();
	}
	public EncryptedMessage toEncryptedMessage() {
		String payload=toJson().toString();
		String payloadType=this.getClass().getName();
		return EncryptedMessage.getNewInstance(payload, payloadType, encryption);
	}
}
