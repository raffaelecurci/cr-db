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
@Table(name="[BUILD]")
public class BUILD extends TableEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="id_commit")
	private Long idCommit;
	@Column(name="id_repository")
	private Integer idRepository;
	private String storagefolder;
	@Column(name="id_securityproject")
	private Integer idSecurityProject;
	@Column(name="id_jenkinsjob")
	private Integer idJenkinsJob;
	@Column(name="id_jenkinsbuild")
	private Long idJenkinsBuild;
	private String status;
	@Column(name="veracodepackage")
	private String veracodePackageName;
	@Column(name="veracodeapplicationname")
	private String veracodeApplicationName;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	
	public BUILD(Long id,Long idCommit,Integer idRepository,String storagefolder,Integer idSecurityProject,Integer idJenkinsJob,Long idJenkinsBuild, String veracodePackageName,String veracodeApplicationName,Date date,String status) {
		super();
		this.id=id;
		this.idCommit=idCommit;
		this.idRepository=idRepository;
		this.storagefolder=storagefolder;
		this.idSecurityProject=idSecurityProject;
		this.idJenkinsJob=idJenkinsJob;
		this.idJenkinsBuild=idJenkinsBuild;
		this.veracodePackageName=veracodePackageName;
		this.veracodeApplicationName=veracodeApplicationName;
		this.date=date;
		this.status=status;
	}
	public BUILD(Long idCommit,Integer idRepository,String storagefolder,Integer idSecurityProject,Integer idJenkinsJob, String veracodePackageName,String veracodeApplicationName) {
		super();
		this.idCommit=idCommit;
		this.idRepository=idRepository;
		this.storagefolder=storagefolder;
		this.idSecurityProject=idSecurityProject;
		this.idJenkinsJob=idJenkinsJob;
		this.idJenkinsBuild=-1L;
		this.veracodePackageName=veracodePackageName;
		this.veracodeApplicationName=veracodeApplicationName;
		this.date=new Date();
		this.status="TOBUILD";
	}
	
	public BUILD() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdCommit() {
		return idCommit;
	}
	public void setIdCommit(Long idCommit) {
		this.idCommit = idCommit;
	}
	public Integer getIdRepository() {
		return idRepository;
	}
	public void setIdRepository(Integer idRepository) {
		this.idRepository = idRepository;
	}
	public String getStoragefolder() {
		return storagefolder;
	}
	public void setStoragefolder(String storagefolder) {
		this.storagefolder = storagefolder;
	}
	public Integer getidSecurityProject() {
		return idSecurityProject;
	}
	public void setidSecurityProject(Integer idSecurityProject) {
		this.idSecurityProject = idSecurityProject;
	}
	public Integer getIdJenkinsJob() {
		return idJenkinsJob;
	}
	public void setIdJenkinsJob(Integer idJenkinsJob) {
		this.idJenkinsJob = idJenkinsJob;
	}
	public Long getIdJenkinsBuild() {
		return idJenkinsBuild;
	}
	public void setIdJenkinsBuild(Long idJenkinsBuild) {
		this.idJenkinsBuild = idJenkinsBuild;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVeracodePackageName() {
		return veracodePackageName;
	}
	public void setVeracodePackageName(String veracodePackageName) {
		this.veracodePackageName = veracodePackageName;
	}
	public String getVeracodeApplicationName() {
		return veracodeApplicationName;
	}
	public void setVeracodeApplicationName(String veracodeApplicationName) {
		this.veracodeApplicationName = veracodeApplicationName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
