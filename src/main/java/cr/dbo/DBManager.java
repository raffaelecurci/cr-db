package cr.dbo;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import cr.DBApplication;
import cr.annotation.QueueDefinition;
import cr.dbo.ops.BUILD;
import cr.dbo.ops.COMMIT;
import cr.dbo.ops.COMMITTER;
import cr.dbo.ops.FLAW;
import cr.dbo.ops.JENKINS;
import cr.dbo.ops.PROJECT;
import cr.dbo.ops.SECURITYPROJECT;
import cr.dbo.ops.repository.BUILDRepository;
import cr.dbo.ops.repository.COMMITRepository;
import cr.dbo.ops.repository.COMMITTERRepository;
import cr.dbo.ops.repository.FLAWRepository;
import cr.dbo.ops.repository.PROJECTRepository;
import cr.dbo.ops.repository.SECURITYPROJECTRepository;
import cr.dbo.repository.ProjectQueries;
import cr.generated.config.ApplicationConfigReader;
import cr.generated.ops.MessageSender;
import cr.interf.EncryptedMessage;
import cr.shared.BuildRecord;
import cr.shared.BuildRecordList;
import cr.shared.Commit;
import cr.shared.FlawList;
import cr.shared.JenkinsBuildInfo;
import cr.shared.JenkinsJob;
import cr.shared.Operation;
import cr.shared.Project;
//import cr.shared.ProjectFinder;
import cr.shared.ProjectList;
import cr.shared.ProjectScan;
import cr.shared.SecurityProject;
import cr.shared.SecurityProjectList;

@Component
public class DBManager {
	@Autowired
	private ProjectQueries queries;
	@Autowired
	private PROJECTRepository pROJECTRepository;
	@Autowired
	private COMMITTERRepository cOMMITTERRepository;
	@Autowired
	private COMMITRepository cOMMITRepository;
	@Autowired
	private BUILDRepository bUILDRepository;
	@Autowired
	private SECURITYPROJECTRepository sECURITYPROJECTRepository;
	@Autowired
	private FLAWRepository fLAWRepository;
	@Autowired
	private MessageSender messageSender;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private ApplicationConfigReader applicationConfigReader;

	private static String encryption = DBApplication.class.getAnnotation(QueueDefinition.class).encryption();
	private static final Logger log = LoggerFactory.getLogger(DBManager.class);

	public EncryptedMessage action(EncryptedMessage message) {
		// if (message.getPayloadType().equals("cr.shared.ProjectFinder"))
		// return findProjectByNameAndUrl(message);
		if (message.getPayloadType().equals("cr.shared.Commit"))
			processCommit(message);
		if (message.getPayloadType().equals("cr.shared.Project"))
			return updateProject(message);
		if (message.getPayloadType().equals("cr.shared.ProjectScan"))
			return processProjectScan(message);
		if (message.getPayloadType().equals("cr.shared.BuildRecord"))
			return addUpdateDeleteBuildRecord(message);
		if (message.getPayloadType().equals("cr.shared.SecurityProject"))
			return listSecurityProject(message);
		if (message.getPayloadType().equals("cr.shared.JenkinsBuildInfo"))
			return updateBuildRecordFromJenkins(message);
		if (message.getPayloadType().equals("cr.shared.JenkinsJob"))
			return manageJenkins(message);
		if (message.getPayloadType().equals("cr.shared.FlawList"))
			manageFlawList(message);
		return new Operation("").setMessage("NOP Cannot Find Action").toEncryptedMessage(encryption).encodeBase64();
	}

	private EncryptedMessage updateProject(EncryptedMessage message) {
		Project p=message.decodeBase64ToObject();
		log.info(p.toString());
		Optional<PROJECT> op = pROJECTRepository.findById(p.getId());
		if(op.isPresent()) {
			PROJECT ptosave=pROJECTRepository.findById(p.getId()).get();
			if(ptosave.getLanguage()==null) {
				ptosave.setLanguage(p.getLanguage());
				ptosave=pROJECTRepository.save(ptosave);
				p.setId(ptosave.getId());
				p.setLanguage(ptosave.getLanguage());
				p.setLastscan(ptosave.getLastscan());
				p.setName(ptosave.getName());
				p.setUrl(ptosave.getUrl());
				log.info("Returning "+p.toString());
				return p.toEncryptedMessage(encryption).encodeBase64();
			}else if(ptosave.getLanguage()!=null){
				p.setId(ptosave.getId());
				p.setLanguage(ptosave.getLanguage());
				p.setLastscan(ptosave.getLastscan());
				p.setName(ptosave.getName());
				p.setUrl(ptosave.getUrl());
				log.info("Returning "+p.toString());
				return p.toEncryptedMessage(encryption).encodeBase64();
			}	
			
		}else
			log.info("No projects to Update");
		return new Operation("").setMessage("NOP for updateProject( "+p.toString()+" ) Optional is present:"+op.isPresent()).toEncryptedMessage(encryption).encodeBase64();
	}

	// private EncryptedMessage findProjectByNameAndUrl(EncryptedMessage message) {
	// ProjectFinder pf=message.decodeBase64ToObject();
	// return queries.findProject(pf.getProjectName(),
	// pf.getProjectUrl()).toEncryptedMessage().encodeBase64();
	// }

	private EncryptedMessage manageFlawList(EncryptedMessage message) {
		FlawList fl = message.decodeBase64ToObject();
		if (fl.getFlaws().size() > 0) {
			COMMIT c = queries.findCommitByHash(fl.getFlaws().get(0).getCommitHash());
			fl.getFlaws().forEach(f -> {
				FLAW fd = new FLAW(f);
				fd.setId_commit(c.getId());
//				try {
					fLAWRepository.save(fd);
//				}catch (Exception e) {
//					log.info(fd.toJson().toString());
//				}				
			});
		}

		return new Operation("").setMessage("NOP").toEncryptedMessage(encryption).encodeBase64();
	}

	private EncryptedMessage manageJenkins(EncryptedMessage message) {
		JenkinsJob jj = message.decodeBase64ToObject();
		if (jj.getId() == null) {
			JENKINS jenkins = queries.findJenkinsJobBySecurityId(jj.getIdSecurityRepository());
			jj.setId(jenkins.getId());
			jj.setJob(jenkins.getJob());
			jj.setUser(jenkins.getUsername());
			jj.setToken(jenkins.getPassword());
			jj.setUrl(jenkins.getUrl());
			return jj.toEncryptedMessage(encryption).encodeBase64();
		}
		return new Operation("").setMessage("NOP for method manageJenkins()").toEncryptedMessage(encryption).encodeBase64();
	}

	private EncryptedMessage updateBuildRecordFromJenkins(EncryptedMessage message) {
		JenkinsBuildInfo jpb = message.decodeBase64ToObject();
		log.info(jpb.toString());
		BUILD b = queries.findBuildByCommitHash(jpb.getCommit());
		b.setIdJenkinsBuild(jpb.getBuildId());
		b.setStatus(jpb.getStatus());
		if (jpb.getVeracodeScan() != null) {
			b.setVeracodePackageName(jpb.getVeracodeScan());
//			SECURITYPROJECT sp = sECURITYPROJECTRepository.findById(b.getidSecurityProject()).get();
//			sp.setAvailable(true);
//			sECURITYPROJECTRepository.save(sp);
			PROJECT p = pROJECTRepository.findById(b.getIdRepository()).get();
			p.setLastscan(new Date());
			p=pROJECTRepository.save(p);
			log.info(p.toString());
		}
		b=bUILDRepository.save(b);
		BuildRecord build=new BuildRecord();
		build.setId(b.getId());
		build.setIdCommit(b.getIdCommit());
		build.setDate(b.getDate());
		build.setIdRepository(b.getIdRepository());
		build.setStatus(b.getStatus());
		build.setStoragefolder(b.getStoragefolder());
		build.setIdJenkinsBuild(b.getIdJenkinsBuild());
		build.setIdSecurityProject(b.getidSecurityProject());
		build.setStoragefolder(b.getStoragefolder());
		build.setStatus(b.getStatus());
		return build.toEncryptedMessage(encryption).encodeBase64();
	}

	private EncryptedMessage listSecurityProject(EncryptedMessage message) {
		ObjectMapper mapper = new ObjectMapper();
		SecurityProject sp = message.decodeBase64ToObject();
		List<SECURITYPROJECT> l = queries.findSecurityProjectByLanguage(sp.getLanguage());
		if(sp.getId()==null) {
			List<SecurityProject> splist = new LinkedList<SecurityProject>();
			l.forEach(e -> {
				try {
					SecurityProject p = mapper.readValue(e.toJson().toString(), SecurityProject.class);
					splist.add(p);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			return new SecurityProjectList(splist).toEncryptedMessage(encryption).encodeBase64();
		}else {
			SECURITYPROJECT s=sECURITYPROJECTRepository.findById(sp.getId()).get();
			s.setAvailable(sp.isAvailable());
			s=sECURITYPROJECTRepository.save(s);
			sp=new SecurityProject();
			sp.setId(s.getId());
			sp.setLanguage(s.getLanguage());
			sp.setAvailable(s.isAvailable());
			sp.setUrl(s.getUrl());
			sp.setIdJenkins(s.getIdJenkins());
			return sp.toEncryptedMessage(encryption).encodeBase64();
		}
		
	}

	private EncryptedMessage addUpdateDeleteBuildRecord(EncryptedMessage message) {
		BuildRecord br = message.decodeBase64ToObject();
		log.info("\n\n" + br.getStatus() + " " + br.getId());
		COMMIT commit = queries.findLastCommitForProject(br.getIdRepository());
		if (br.getId() == null&& commit!=null && !br.getStatus().equals("FIND_BACKLOG")) {
			BUILD b = new BUILD();			
			System.out.println("\t" + commit);
			b.setIdCommit(commit.getId());
			b.setStatus(br.getStatus());
			b.setDate(br.getDate());
			b.setIdRepository(br.getIdRepository());
			b.setidSecurityProject(br.getIdSecurityProject());
			b.setStoragefolder(br.getStoragefolder());
			b = bUILDRepository.save(b);// -Dmaven.test.skip=true
			if(br.getIdSecurityProject()!=null) {
//				SECURITYPROJECT spr = sECURITYPROJECTRepository.findById(br.getIdSecurityProject()).get();
//				PROJECT pr = pROJECTRepository.findById(br.getIdRepository()).get();
//				pr.setLanguage(spr.getLanguage());
//				pROJECTRepository.save(pr);
				commit.setIdBuildRecord(b.getId());
				cOMMITRepository.save(commit);
			}
			
			br.setId(b.getId());
			return br.toEncryptedMessage(encryption).encodeBase64();
		} else if (br.getStatus().equals("DELETE")) {
			commit = queries.findLastCommitForProject(br.getIdRepository());
			BUILD b = queries.findBuildByCommitHash(commit.getHash());
			Long deteletdBuild=b.getId();
			bUILDRepository.delete(b);
			return new Operation("").setMessage("Deleted build "+deteletdBuild).toEncryptedMessage(encryption).encodeBase64();
		} else if (br.getStatus().equals("BUILDSTARTING")&& commit!=null) {	
			SECURITYPROJECT sp = sECURITYPROJECTRepository.findById(br.getIdSecurityProject()).get();
//			br.getIdSecurityProject();
			queries.markSecurityProjectAsUnavailable(br.getIdSecurityProject());
			System.out.println("Updating security Project " + sp.getUrl() + " as unavailable");
			BUILD b = queries.findBuildByCommitHash(commit.getHash());
			if(b==null)
				b=bUILDRepository.save(new BUILD(commit.getId(), br.getIdRepository(), br.getStoragefolder(), br.getIdSecurityProject(), null, null));
			b.setidSecurityProject(br.getIdSecurityProject());
			b.setStatus(br.getStatus());
			b=bUILDRepository.save(b);
			br.setId(b.getId());
			br.setIdCommit(b.getIdCommit());
			return br.toEncryptedMessage(encryption).encodeBase64();
//			return new Operation("").setMessage("Build Started").toEncryptedMessage(encryption).encodeBase64();
		}else if (br.getStatus().equals("FIND_BACKLOG")) {
			List<BUILD>BUILDbacklog=queries.findBuildRecordsBackLog();
			List<BuildRecord> l=new LinkedList<BuildRecord>();
			BUILDbacklog.forEach(b->{
				BuildRecord build=new BuildRecord();
				build.setId(b.getId());
				build.setIdCommit(b.getIdCommit());
				build.setDate(b.getDate());
				build.setIdRepository(b.getIdRepository());
				build.setStatus(b.getStatus());
				build.setStoragefolder(b.getStoragefolder());
				l.add(build);
			});
			return new BuildRecordList().setBuilds(l).toEncryptedMessage(encryption).encodeBase64();
		} else
			return new Operation("").setMessage("NOP").toEncryptedMessage(encryption).encodeBase64();

	}

	private EncryptedMessage processProjectScan(EncryptedMessage message) {
		ObjectMapper mapper = new ObjectMapper();
		ProjectScan prjscan = message.decodeBase64ToObject();
		List<PROJECT> listp = queries.findProjectForScan(prjscan.getDate());
		List<Project> rplist = new LinkedList<Project>();
		listp.forEach(e -> {
			try {
				Project p = mapper.readValue(e.toJson().toString(), Project.class);
				rplist.add(p);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		return new ProjectList(rplist).toEncryptedMessage(encryption).encodeBase64();
	}

	private synchronized EncryptedMessage processCommit(EncryptedMessage message) {
		Commit commit = message.decodeBase64ToObject();
		System.out.println("processCommit: " + commit);
		// queries = cr.BeanUtil.getBean(ProjectQueries.class);
		PROJECT p = queries.findProject(commit.getProjectname(), commit.getUrl());
		if (p == null)
			p = pROJECTRepository.save(new PROJECT(null, null, commit.getProjectname(), commit.getUrl()));
		COMMITTER c = queries.findCommitter(commit.getEmail());
		if (c == null)
			c = cOMMITTERRepository
					.save(new COMMITTER(commit.getEmail(), commit.getCommittername(), commit.getUsername()));
		COMMIT cmt = cOMMITRepository.save(new COMMIT(commit.getHash(), c.getId(), p.getId()));
		Project pr = new Project(p.getId(), p.getLanguage(), p.getLastscan(), p.getName(), p.getUrl());
		messageSender.sendMessage(rabbitTemplate, applicationConfigReader.getAnaExchange(),applicationConfigReader.getAnaRoutingKey(), pr.toEncryptedMessage(encryption).encodeBase64());
		return cmt.toEncryptedMessage().encodeBase64();
	}
}