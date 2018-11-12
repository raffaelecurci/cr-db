package cr;

import java.lang.reflect.Proxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cr.dbo.DBManager;
import cr.dbo.repository.ProjectQueries;
import cr.dbo.repository.proxy.NativeQueryInvocationHandler;
import cr.encryption.PlainText;
import cr.interf.EncryptedMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProxyTest {
	@Autowired
	private ProjectQueries queries;
	@Autowired
	private DBManager dbManager;
	
//	@Autowired
//    private USERRepository uSERRepository;
	
//	@Test
	public void test1() {
		NativeQueryInvocationHandler handler = new NativeQueryInvocationHandler();
        ProjectQueries o = (ProjectQueries) Proxy.newProxyInstance(
        		NativeQueryInvocationHandler.class.getClassLoader(),
                            new Class[]{ProjectQueries.class}, handler);
//        o.doSomething();
	}
	
	@Test
	public void test2() {
//		System.out.println(queries.doSomething());
//		System.out.println(uSERRepository);
		System.out.println(queries.findCommitter("pippo@pluto.it"));;
		EncryptedMessage enc=new PlainText("{\"type\":\"PlainText\",\"payload\":\"fSJwZW5hYyI6ImVtYW5yZXN1IiwiZXdxLmV3cS8vOnB0dGgiOiJscnUiLCJ0aS5vdHVscEBvcHBpcCI6ImxpYW1lIiwiMWV3cSI6ImhzYWgiLCIiOiJlZ2F1Z25hbCIsInRpbW1vQy5kZXJhaHMucmMiOiJzc2FsY0AiLCJhdm9ycCI6ImVtYW50Y2Vqb3JwIiwib3BwaXAiOiJlbWFucmV0dGltbW9jIns=\"}", null);
		dbManager.action(enc);
	}
//	@Test
	public void test3() {}
}
