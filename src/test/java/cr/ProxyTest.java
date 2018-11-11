package cr;

import java.lang.reflect.Proxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cr.dbo.repository.ProjectQueries;
import cr.dbo.repository.proxy.NativeQueryInvocationHandler;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProxyTest {
	@Autowired
	private ProjectQueries queries;
	
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
	}
//	@Test
	public void test3() {}
}
