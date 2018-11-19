package cr.dbo;

import java.lang.reflect.Field;

import org.json.JSONObject;

import cr.DBApplication;
import cr.SharedEntity;
import cr.interf.EncryptedMessage;

public class TableEntity extends SharedEntity{
	
	private static String encryption=DBApplication.class.getAnnotation(cr.annotation.QueueDefinition.class).encryption();
	
//	public JSONObject toJson() {
//		Field[] fields = this.getClass().getDeclaredFields();
//		  JSONObject json=new JSONObject();
//		  for ( Field field : fields  ) {
//		    try {
//		    	if(!field.getName().equals("serialVersionUID")) {
//		    		field.setAccessible(true);
//					json.put(field.getName(),field.get(this) );	
//		    	}
//		    } catch ( IllegalAccessException ex ) {
//		      System.out.println(ex);
//		    }
//		  }
//		  return json;
//	}
//	@Override
//	public String toString() {
//		 JSONObject json=toJson() ;
//		  json.put("@class", this.getClass().getName());
//		  return json.toString();
//	}
//	public EncryptedMessage toEncryptedMessage() {
//		String payload=toJson().toString();
//		String payloadType=this.getClass().getName();
//		return EncryptedMessage.getNewInstance(payload, payloadType, encryption);
//	}
	public EncryptedMessage toEncryptedMessage() {
	String payload=toJson().toString();
	String payloadType=this.getClass().getName();
	return EncryptedMessage.getNewInstance(payload, payloadType, encryption);
}
}
