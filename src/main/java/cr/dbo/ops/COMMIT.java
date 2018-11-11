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
	
	public COMMIT(Long id,String hash,Integer idCommitter,Integer idRepository) {
		super();
		this.id=id;
		this.hash=hash;
		this.idCommitter=idCommitter;
		this.idRepository=idRepository;
	}
	
	public COMMIT(String hash,Integer idCommitter,Integer idRepository) {
		super();
		this.hash=hash;
		this.idCommitter=idCommitter;
		this.idRepository=idRepository;
	}
	
//	public COMMIT(Object v[]) {
//		super();
//		this.id=((java.math.BigInteger)v[0]).longValue();
//		this.hash=(String)v[1];
//		this.idCommitter=(Integer)v[2];
//		this.idRepository=(Integer)v[3];
//	}
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
