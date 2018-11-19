package cr;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RefreshScope
@Configuration
public class DBConf {
	@PersistenceUnit(unitName = "default")
	private EntityManagerFactory entityManagerFactory;

	@Value("${path}")
	private String filePath;

	@Bean
	public String init() {
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction tx=null;
		try {
			InputStream is = new FileInputStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			tx = em.getTransaction();
			tx.begin();
			br.lines().forEach(l -> {
				System.out.println(l);
				em.createNativeQuery(l).executeUpdate();
			});
			tx.commit();
		} catch (IOException e) {
			tx.rollback();
			e.printStackTrace();
		}
		return "DB-Ready";
	}

}
