package cr.dbo.ops;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cr.dbo.TableEntity;

@Entity
@Table(name="[PROJECT]")
public class PROJECT extends TableEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(columnDefinition="varchar(64)")
	private String language;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastscan;
	@Column(unique=true,columnDefinition="varchar(64)")
	private String name;
	@Column(unique=true,columnDefinition="varchar(128)")
	private String url;
	
	public PROJECT(Integer id,String language,Date lastscan,String name,String url) {
		super();
		this.id=id;
		this.language=language;
		this.lastscan=lastscan;
		this.name=name;
		this.url=url;
	}
	public PROJECT(String language,Date lastscan,String name,String url) {
		super();
		this.language=language;
		this.lastscan=lastscan;
		this.name=name;
		this.url=url;
	}
	public PROJECT() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Date getLastscan() {
		return lastscan;
	}
	public void setLastscan(Date lastscan) {
		this.lastscan = lastscan;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
