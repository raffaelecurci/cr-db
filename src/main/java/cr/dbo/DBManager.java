package cr.dbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cr.DBApplication;
import cr.annotation.QueueDefinition;
import cr.dbo.ops.COMMIT;
import cr.dbo.ops.COMMITTER;
import cr.dbo.ops.PROJECT;
import cr.dbo.ops.repository.COMMITRepository;
import cr.dbo.ops.repository.COMMITTERRepository;
import cr.dbo.ops.repository.PROJECTRepository;
import cr.dbo.repository.ProjectQueries;
import cr.interf.EncryptedMessage;
import cr.shared.Commit;
import cr.shared.Reply;

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
	
	private static String encryption=DBApplication.class.getAnnotation(QueueDefinition.class).encryption();
	public EncryptedMessage action(EncryptedMessage message) {
		if(message.getPayloadType().equals("cr.shared.Commit"))
			processCommit(message);
		return new Reply().setReply("NOP").toEncryptedMessage(encryption).encodeBase64();
	}

	private EncryptedMessage processCommit(EncryptedMessage message) {
		// TODO Auto-generated method stub
		Commit commit=message.decodeBase64ToObject();
		System.out.println("processCommit: "+commit);
		
//		System.out.println(pROJECTRepository.count());
//		System.out.println("\n\n");
		ProjectQueries queries=cr.BeanUtil.getBean(ProjectQueries.class); 
		PROJECT p=queries.findProject(commit.getProjectname(), commit.getUrl());
//		System.out.println("\n\n");
		if(p==null)
			p=pROJECTRepository.save(new PROJECT("", null, commit.getProjectname(), commit.getUrl()));
		COMMITTER c=queries.findCommitter(commit.getEmail());
		if(c==null)
			c=cOMMITTERRepository.save(new COMMITTER(commit.getEmail(), commit.getCommittername(), commit.getUsername()));
		COMMIT cmt=cOMMITRepository.save(new COMMIT(commit.getHash(),c.getId(),p.getId()));
		
		return cmt.toEncryptedMessage().encodeBase64();
//		return message;
	}
}
