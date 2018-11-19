package cr;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Proxy;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cr.dbo.DBManager;
import cr.dbo.ops.COMMIT;
import cr.dbo.ops.FLAW;
import cr.dbo.ops.SECURITYPROJECT;
import cr.dbo.ops.repository.FLAWRepository;
import cr.dbo.repository.ProjectQueries;
import cr.dbo.repository.proxy.NativeQueryInvocationHandler;
import cr.encryption.PlainText;
import cr.interf.EncryptedMessage;
import cr.shared.Flaw;
import cr.shared.ProjectList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProxyTest {
	@Autowired
	private ProjectQueries queries;
	@Autowired
	private DBManager dbManager;
	
	@Autowired
    private FLAWRepository fLAWRepository;
	
//	@Test
	public void test1() {
		NativeQueryInvocationHandler handler = new NativeQueryInvocationHandler();
        ProjectQueries o = (ProjectQueries) Proxy.newProxyInstance(
        		NativeQueryInvocationHandler.class.getClassLoader(),
                            new Class[]{ProjectQueries.class}, handler);
//        o.doSomething();
	}
	
//	@Test
	public void test2() {
//		System.out.println(queries.doSomething());
//		System.out.println(uSERRepository);
		System.out.println(queries.findCommitter("pippo@pluto.it"));;
		EncryptedMessage enc=new PlainText("fSJwZW5hYyI6ImVtYW5yZXN1IiwiZXdxLmV3cS8vOnB0dGgiOiJscnUiLCJ0aS5vdHVscEBvcHBpcCI6ImxpYW1lIiwiMWV3cSI6ImhzYWgiLCIiOiJlZ2F1Z25hbCIsInRpbW1vQy5kZXJhaHMucmMiOiJzc2FsY0AiLCJhdm9ycCI6ImVtYW50Y2Vqb3JwIiwib3BwaXAiOiJlbWFucmV0dGltbW9jIns=", null);
		dbManager.action(enc);
		enc=new PlainText("fSJwZW5hYyI6ImVtYW5yZXN1IiwiMWV3cS5ld3EvLzpwdHRoIjoibHJ1IiwidGkub3R1bHBAb3BwaXAiOiJsaWFtZSIsImV3cSI6ImhzYWgiLCIiOiJlZ2F1Z25hbCIsInRpbW1vQy5kZXJhaHMucmMiOiJzc2FsY0AiLCIxYXZvcnAiOiJlbWFudGNlam9ycCIsIm9wcGlwIjoiZW1hbnJldHRpbW1vYyJ7", null);
		dbManager.action(enc);
		enc=new PlainText("fSJwZW5hYyI6ImVtYW5yZXN1IiwiZXdxLmV3cS8vOnB0dGgiOiJscnUiLCJ0aS5vdHVscEBvcHBpcCI6ImxpYW1lIiwiNWV3cSI6ImhzYWgiLCIiOiJlZ2F1Z25hbCIsInRpbW1vQy5kZXJhaHMucmMiOiJzc2FsY0AiLCJhdm9ycCI6ImVtYW50Y2Vqb3JwIiwib3BwaXAiOiJlbWFucmV0dGltbW9jIns=", null);
		dbManager.action(enc);
		enc=new PlainText("fSJuYWNTdGNlam9yUC5kZXJhaHMucmMiOiJzc2FsY0AiLCIzMS0xMS04MTAyIjoiZXRhZCJ7", null);
		enc=dbManager.action(enc);
		System.out.println(((ProjectList)enc.decodeBase64ToObject()).toJson());
//		"{\"type\":\"PlainText\",\"payload\":\"fSJuYWNTdGNlam9yUC5kZXJhaHMucmMiOiJzc2FsY0AiLCIzMS0xMS04MTAyIjoiZXRhZCJ7\"}";
	}
//	@Test
	public void test3() {
		System.out.println("\n\n test 3 \n");
		EncryptedMessage enc=new PlainText("fSJwZW5hYyI6ImVtYW5yZXN1IiwiZXdxLmV3cS8vOnB0dGgiOiJscnUiLCJ0aS5vdHVscEBvcHBpcCI6ImxpYW1lIiwiMWV3cSI6ImhzYWgiLCIiOiJlZ2F1Z25hbCIsInRpbW1vQy5kZXJhaHMucmMiOiJzc2FsY0AiLCJhdm9ycCI6ImVtYW50Y2Vqb3JwIiwib3BwaXAiOiJlbWFucmV0dGltbW9jIns=", null);
		dbManager.action(enc);
		enc=new PlainText("fSJwZW5hYyI6ImVtYW5yZXN1IiwiMWV3cS5ld3EvLzpwdHRoIjoibHJ1IiwidGkub3R1bHBAb3BwaXAiOiJsaWFtZSIsImV3cSI6ImhzYWgiLCIiOiJlZ2F1Z25hbCIsInRpbW1vQy5kZXJhaHMucmMiOiJzc2FsY0AiLCIxYXZvcnAiOiJlbWFudGNlam9ycCIsIm9wcGlwIjoiZW1hbnJldHRpbW1vYyJ7", null);
		dbManager.action(enc);
		enc=new PlainText("fSJwZW5hYyI6ImVtYW5yZXN1IiwiZXdxLmV3cS8vOnB0dGgiOiJscnUiLCJ0aS5vdHVscEBvcHBpcCI6ImxpYW1lIiwiNWV3cSI6ImhzYWgiLCIiOiJlZ2F1Z25hbCIsInRpbW1vQy5kZXJhaHMucmMiOiJzc2FsY0AiLCJhdm9ycCI6ImVtYW50Y2Vqb3JwIiwib3BwaXAiOiJlbWFucmV0dGltbW9jIns=", null);
		dbManager.action(enc);
		System.out.println("\n\n\n");
		COMMIT commit=queries.findLastCommitForProject(1);
		System.out.println(commit);
	}
//	@Test
	public void test4(){
		try {
			InputStream is = new FileInputStream("/home/fefe/Desktop/config/dbinit.sql");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			br.lines().forEach(l -> {
				System.out.println(l);
//				em.createNativeQuery(l).executeUpdate();
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	@Test
	public void test5() {
		boolean available=true;
		String language="Java";
		List<SECURITYPROJECT>l=queries.findSecurityProjectByLanguage(language);
		l.forEach(System.out::println);
	}
	@Test
	public void test6() {
		EncryptedMessage enc=new PlainText("fSJwZW5hYyI6ImVtYW5yZXN1IiwidGlnLmlwYS13dGUvc3BvZy9tY3MvMzQ0ODowOS45MS4xMS4wMS8vOnNwdHRoIjoibHJ1IiwidGkub3R1bHBAb3BwaXAiOiJsaWFtZSIsImIyZThiZGMxZmMxODE2NDZkMWIxYmNhN2Q4M2Y5YmM4NjY0Y2FmYTQiOiJoc2FoIiwiIjoiZWdhdWduYWwiLCJ0aW1tb0MuZGVyYWhzLnJjIjoic3NhbGNAIiwiaXBhLXd0ZSI6ImVtYW50Y2Vqb3JwIiwib3BwaXAiOiJlbWFucmV0dGltbW9jIns=",null);
		dbManager.action(enc);
		InputStream is=this.getClass().getClassLoader().getResourceAsStream("flawList");
		StringBuilder sb=new StringBuilder();
//		new BufferedReader(new InputStreamReader(is)).lines().forEach(System.out::println);
		new BufferedReader(new InputStreamReader(is)).lines().forEach(l->sb.append(l));
		enc=new PlainText(sb.toString(),"cr.shared.FlawList").encodeBase64();
		dbManager.action(enc);
	}
//	@Test
	public void test7() throws JsonParseException, JsonMappingException, IOException {
		String flaw="{\n" + 
				"         \"loc\":5654,\n" + 
				"         \"issueid\":54,\n" + 
				"         \"line\":59,\n" + 
				"         \"rating\":\"C\",\n" + 
				"         \"jenkinsBuild\":31,\n" + 
				"         \"type\":\"org.slf4j.Logger.debug\",\n" + 
				"         \"commitHash\":\"4afac4668cb9f38d7acb1b1d646181cf1cdb8e2b\",\n" + 
				"         \"category_description\":\"The acronym CRLF stands for \\\"Carriage Return, Line Feed\\\" and refers to the sequence of characters used to denote the end of a line of text.  CRLF injection vulnerabilities occur when data enters an application from an untrusted source and is not properly validated before being used.  For example, if an attacker is able to inject a CRLF into a log file, he could append falsified log entries, thereby misleading administrators or cover traces of the attack.  If an attacker is able to inject CRLFs into an HTTP response header, he can use this ability to carry out other attacks such as cache poisoning.  CRLF vulnerabilities primarily affect data integrity.  \",\n" + 
				"         \"score\":\"82\",\n" + 
				"         \"cia_impact\":\"npp\",\n" + 
				"         \"exploitLevel\":1,\n" + 
				"         \"functionrelativelocation\":8,\n" + 
				"         \"scope\":\"com.ryanair.etwapi.auth.controller.AuthenticationController\",\n" + 
				"         \"id\":null,\n" + 
				"         \"categoryid\":21,\n" + 
				"         \"sourcefile\":\"AuthenticationController.java\",\n" + 
				"         \"severity\":3,\n" + 
				"         \"cwename\":\"Improper Output Neutralization for Logs\",\n" + 
				"         \"functionprototype\":\"model.AuthResponse createAuthenticationToken(model.AuthRequest, javax.servlet.http.HttpServletResponse)\",\n" + 
				"         \"analysis_size_bytes\":163403,\n" + 
				"         \"affects_policy_compliance\":false,\n" + 
				"         \"cweid\":117,\n" + 
				"         \"pcirelated\":false,\n" + 
				"         \"cwe_description\":\"A function call could result in a log forging attack.  Writing untrusted data into a log file allows an attacker to forge log entries or inject malicious content into log files.  Corrupted log files can be used to cover an attacker's tracks or as a delivery mechanism for an attack on a log viewing or processing utility.  For example, if a web administrator uses a browser-based utility to review logs, a cross-site scripting attack might be possible.\",\n" + 
				"         \"count\":1,\n" + 
				"         \"sourcefilepath\":\"com/ryanair/etwapi/auth/controller/\",\n" + 
				"         \"cwepcirelated\":false,\n" + 
				"         \"version\":\"jenkinsJava-#31-4afac4668cb9f38d7acb1b1d646181cf1cdb8e2b\",\n" + 
				"         \"recommendation_description\":\"Apply robust input filtering for all user-supplied data, using centralized data validation routines when possible.  Use output filters to sanitize all output derived from user-supplied input, replacing non-alphanumeric characters with their HTML entity equivalents.\",\n" + 
				"         \"id_commit\":null,\n" + 
				"         \"categoryname\":\"CRLF Injection\",\n" + 
				"         \"flaw_description\":\"This call to org.slf4j.Logger.debug() could result in a log forging attack. Writing untrusted data into a log file allows an attacker to forge log entries or inject malicious content into log files. Corrupted log files can be used to cover an attacker's tracks or as a delivery mechanism for an attack on a log viewing or processing utility. For example, if a web administrator uses a browser-based utility to review logs, a cross-site scripting attack might be possible. The second argument to debug() contains tainted data from the variable crewCode. The tainted data originated from an earlier call to annotationvirtualcontroller.vc_annotation_entry.\\r\\n\\r\\nAvoid directly embedding user input in log files when possible. Sanitize untrusted data used to construct log entries by using a safe logging mechanism such as the OWASP ESAPI Logger, which will automatically remove unexpected carriage returns and line feeds and can be configured to use HTML entity encoding for non-alphanumeric data. Only write custom blacklisting code when absolutely necessary. Always validate untrusted input to ensure that it conforms to the expected format, using centralized data validation routines when possible.\\r\\n\\r\\nReferences: \\r\\nCWE (http://cwe.mitre.org/data/definitions/117.html) \\r\\nOWASP (http://www.owasp.org/index.php/Log_injection) \\r\\nWASC (http://webappsec.pbworks.com/Improper-Output-Handling)\\r\\n\\r\\n\"\n" + 
				"      }";
		ObjectMapper om=new ObjectMapper();
		Flaw f=om.readValue(flaw, Flaw.class);
		System.out.println(f);
		FLAW fd=new FLAW(f);
//		fd.setInstance(f);
		fd=fLAWRepository.save(fd);
		System.out.println(fd);
//		fd.setId(1L);
//		System.out.println(fd.toJson());
//		PlainText p=new PlainText(flaw, "cr.shared.Flaw");
		
	}
}
