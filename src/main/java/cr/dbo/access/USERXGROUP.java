package cr.dbo.access;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cr.dbo.TableEntity;

@Entity
@Table(name = "[USERXGROUP]")
public class USERXGROUP extends TableEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	protected Long id_user;
	protected Long id_group;

	public USERXGROUP(Long id, Long id_user, Long id_group) {
		super();
		this.id = id;
		this.id_user = id_user;
		this.id_group = id_group;
	}

	public USERXGROUP(Long id_user, Long id_group) {
		super();
		this.id_user = id_user;
		this.id_group = id_group;
	}

	public USERXGROUP() {
		super();
	}

	public Long getId_user() {
		return id_user;
	}

	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}

	public Long getId_group() {
		return id_group;
	}

	public void setId_group(Long id_group) {
		this.id_group = id_group;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
