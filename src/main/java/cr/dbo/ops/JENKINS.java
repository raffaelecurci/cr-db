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
	private Long id;
	private String username;
	private String password;
	private String ip;
	private String job;
	
	public JENKINS(Long id, String username, String password,String ip,String job) {
		super();
		this.id=id;
		this.username=username;
		this.password=password;
		this.ip=ip;
		this.job=job;
	}
	public JENKINS(String username, String password,String ip,String job) {
		super();
		this.username=username;
		this.password=password;
		this.ip=ip;
		this.job=job;
	}
	public JENKINS() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
}
