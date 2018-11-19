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

import org.hibernate.annotations.ColumnDefault;

import cr.dbo.TableEntity;

@Entity
@Table(name="[COMMIT]")
public class COMMIT extends TableEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true,columnDefinition="varchar(64)")
	private String hash;
	@Column(name="id_committer")
	private Integer idCommitter;
	@Column(name="id_repository")
	private Integer idRepository;
	@Column(name="id_build")
	@ColumnDefault("(-1)")
	private Long idBuildRecord;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	public COMMIT(Long id,String hash,Integer idCommitter,Integer idRepository,Long idBuildRecord,Date date) {
		super();
		this.id=id;
		this.hash=hash;
		this.idCommitter=idCommitter;
		this.idRepository=idRepository;
		this.idBuildRecord=idBuildRecord;
		this.date=date;
	}
	
	public COMMIT(String hash,Integer idCommitter,Integer idRepository,Long idBuildRecord,Date date) {
		super();
		this.hash=hash;
		this.idCommitter=idCommitter;
		this.idRepository=idRepository;
		this.idBuildRecord=idBuildRecord;
		this.date=date;
	}
	public COMMIT(String hash,Integer idCommitter,Integer idRepository) {
		super();
		this.hash=hash;
		this.idCommitter=idCommitter;
		this.idRepository=idRepository;
		this.idBuildRecord=-1L;
		this.date=new Date();
	}

	public Long getIdBuildRecord() {
		return idBuildRecord;
	}

	public void setIdBuildRecord(Long idBuildRecord) {
		this.idBuildRecord = idBuildRecord;
	}

	public COMMIT() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Integer getIdCommitter() {
		return idCommitter;
	}

	public void setIdCommitter(Integer idCommitter) {
		this.idCommitter = idCommitter;
	}

	public Integer getIdRepository() {
		return idRepository;
	}

	public void setIdRepository(Integer idRepository) {
		this.idRepository = idRepository;
	}
}
