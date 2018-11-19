package cr.dbo.ops;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cr.dbo.TableEntity;

@Entity
@Table(name="[JENKINS]")
public class JENKINS extends TableEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String password;
	private String url;
	private String job;
	
	public JENKINS(Integer id, String username, String password,String url,String job) {
		super();
		this.id=id;
		this.username=username;
		this.password=password;
		this.url=url;
		this.job=job;
	}
	public JENKINS(String username, String password,String url,String job) {
		super();
		this.username=username;
		this.password=password;
		this.url=url;
		this.job=job;
	}
	public JENKINS() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
}
