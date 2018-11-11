package cr.dbo.ops;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cr.dbo.TableEntity;

@Entity
@Table(name="[COMMITTER]")
public class COMMITTER extends TableEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique=true,columnDefinition="varchar(64)")
	private String email;
	@Column(columnDefinition="varchar(64)")
	private String name;
	@Column(unique=true,columnDefinition="varchar(64)")
	private String username;
	
	public COMMITTER(Integer id,String email,String name,String username) {
		super();
		this.id=id;
		this.email=email;
		this.name=name;
		this.username=username;
	}
	public COMMITTER(String email,String name,String username) {
		super();
		this.email=email;
		this.name=name;
		this.username=username;
	}
	public COMMITTER() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
