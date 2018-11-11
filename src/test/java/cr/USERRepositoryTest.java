package cr;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cr.dbo.access.GROUP;
import cr.dbo.access.USER;
import cr.dbo.access.USERXGROUP;
import cr.dbo.ops.COMMIT;
import cr.dbo.ops.repository.COMMITRepository;
import cr.dbo.repository.ProjectQueries;
import cr.dbo.repository.access.GROUPRepository;
import cr.dbo.repository.access.USERRepository;
import cr.dbo.repository.access.USERXGROUPRepository;
import cr.interf.EncryptedMessage;
@RunWith(SpringRunner.class)
@SpringBootTest
public class USERRepositoryTest {
    @Autowired
    private USERRepository uSERRepository;
    @Autowired
    private GROUPRepository gROUPRepository;
    @Autowired
    private USERXGROUPRepository uSERXGROUPRepository;
    @Autowired
    private COMMITRepository cOMMITRepository;
//    @Autowired
//    private Queries queries;
    
    @Before
    public void setUp() throws Exception {
//    	uSERRepository.refresh();
    	System.out.println( uSERRepository.findByUsername("pippo").size());
        USER user1= new USER("admin","piedone",1);
        USER user2= new USER("pluto","cane",1);
        USER user3= new USER("pippo","cagnone",1);
        USER user4= new USER("paperino","incazzoso",1);
        USER user5= new USER("paperina","carina",1);
        GROUP group1= new GROUP("admin", "admin group");
        GROUP group2= new GROUP("users", "users group");
        
        
        //save user, verify has ID value after save
        assertNull(user1.getId());
        assertNull(user2.getId());//null before save
        this.uSERRepository.save(user1);
        this.uSERRepository.save(user2);
        this.uSERRepository.save(user3);
        this.uSERRepository.save(user4);
        this.uSERRepository.save(user5);
        
        this.gROUPRepository.save(group1);
        this.gROUPRepository.save(group2);
        
        USERXGROUP upg1=new USERXGROUP(user1.getId(), group1.getId());
        USERXGROUP upg2=new USERXGROUP(user2.getId(), group2.getId());
        USERXGROUP upg3=new USERXGROUP(user3.getId(), group2.getId());
        USERXGROUP upg4=new USERXGROUP(user4.getId(), group2.getId());
        USERXGROUP upg5=new USERXGROUP(user5.getId(), group2.getId());
        
        this.uSERXGROUPRepository.save(upg1);
        this.uSERXGROUPRepository.save(upg2);
        this.uSERXGROUPRepository.save(upg3);
        this.uSERXGROUPRepository.save(upg4);
        this.uSERXGROUPRepository.save(upg5);
//        this.userRepository.flush();
        assertNotNull(user1.getId());
        assertNotNull(user2.getId());
    }
//    @Test
//    public void toEncryptedMessage() {
//    	USER u=uSERRepository.findById(1L).get();
//    	String row="{\"enabled\":1,\"password\":\"piedone\",\"id\":1,\"username\":\"admin\"}";
//    	EncryptedMessage enc=u.toEncryptedMessage();
//    	System.out.println(enc.toString());
//    }
    @Test
    public void testFetchData(){
//    	List<Object[]> l=uSERXGROUPRepository.findByGroup("users");
//    	List<Object[]> l=queries.findByGroup("users");
////    	List<USER> l=uSERXGROUPRepository.findByGroup("users");
//    	final List<USER> list=new LinkedList<USER>();
//    	l.forEach(e->{
//    		USER u=new USER(e);
//    		u.setEnabled(0);
//    		list.add(u);
//    	});
//    	for (int i = 0; i < l.get(0).length; i++) {
//			System.out.println(l.get(0)[i].getClass().getName());
//		}
//    	
//    	list.forEach(e->{System.out.println(e);uSERRepository.save(e);});
//    	
//    	System.out.println(l.size());
    	
    	USER u=uSERRepository.findById(1L).get();
    	String row="{\"enabled\":1,\"password\":\"piedone\",\"id\":1,\"username\":\"admin\"}";
    	EncryptedMessage enc=u.toEncryptedMessage();
    	System.out.println(enc.encodeBase64());
    	
    	cOMMITRepository.save(new COMMIT("qwe", 1, 1));
        /*Test data retrieval*/
//        List<USER> userA = uSERRepository.findByUsername("pippo");
//        assertNotNull(userA.size()>0);
//        assertEquals("piedone", userA.get(0).getPassword());
//        /*Get all products, list should only have two*/
//        Iterable<USER> uSERs = uSERRepository.findAll();
//        int count = 0;
//        for(USER p : uSERs){
//            count++;
//        }
////        assertEquals(count, 2);
////        System.out.println("total:"+count+" pippo: "+uSERRepository.findByUsername("pippo").size());
    }
}