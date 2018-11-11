package cr;

import org.junit.Test;

import cr.dbo.access.USER;

public class TableTest {
	@Test
	public void test1() {
		USER u= new USER(1L,"pippo","pluto",1);
		System.out.println(u.toString());
	}
	@Test
	public void test2() {
		
	}
	
}
