package cr.dbo.access;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cr.dbo.TableEntity;

@Entity
@Table(name = "[USER]")
public class USER extends TableEntity{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true,columnDefinition="varchar(64)")
	private String username;
	@Column(columnDefinition="varchar(128)")
	private String password;
	@Column(columnDefinition="int")
	private Integer enabled;
	
	public USER(Long id, String username, String password,Integer enabled) {
		super();
		this.id=id;
		this.username=username;
		this.password=password;
		this.enabled=enabled;
	}
	public USER(Object[] v) {
		super();
		this.id=((java.math.BigInteger)v[0]).longValue();
		this.username=(String)v[1];
		this.password=(String)v[2];
		this.enabled=(Integer)v[3];
	}
	public USER(String username, String password,Integer enabled) {
		super();
		this.username=username;
		this.password=password;
		this.enabled=enabled;
	}	
	public USER() {
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
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
}
