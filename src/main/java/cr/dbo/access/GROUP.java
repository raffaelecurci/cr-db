package cr.dbo.access;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cr.dbo.TableEntity;

@Entity
@Table(name = "[GROUP]")
public class GROUP extends TableEntity{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true,columnDefinition="varchar(64)")
	private String name;
	@Column(unique=true,columnDefinition="varchar(128)")
	private String description;
	
	public GROUP(Long id, String name, String description) {
		super();
		this.id=id;
		this.name=name;
		this.description=description;
	}
	public GROUP(String name, String description) {
		super();
		this.name=name;
		this.description=description;
	}	
	public GROUP() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
		
}
