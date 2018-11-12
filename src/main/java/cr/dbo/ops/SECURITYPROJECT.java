package cr.dbo.ops;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cr.dbo.TableEntity;

@Entity
@Table(name="[SECURITYPROJECT]")
public class SECURITYPROJECT extends TableEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String url;
	private String language;
	boolean available;
	private String username;
	private String password;
	
	public SECURITYPROJECT(Integer id,String url,String language, Boolean available,String username,String password) {
		super();
		this.id=id;
		this.url=url;
		this.language=language;
		this.available=available;
		this.username=username;
		this.password=password;
	}
	public SECURITYPROJECT(String url,String language, Boolean available,String username,String password) {
		super();
		this.url=url;
		this.language=language;
		this.available=available;
		this.username=username;
		this.password=password;
	}
	public SECURITYPROJECT() {
		super();
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
}
