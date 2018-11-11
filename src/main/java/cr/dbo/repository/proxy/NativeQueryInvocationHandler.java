package cr.dbo.repository.proxy;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import cr.dbo.repository.ProjectQueries;
import cr.dbo.repository.annotation.NativeQuery;

@RefreshScope
@Configuration
public class NativeQueryInvocationHandler implements InvocationHandler {
	@Autowired
	private EntityManager em;
	
	@Value("${spring.jpa.show-sql}")
	boolean showSQL;
	
	private static final Logger log=LoggerFactory.getLogger(NativeQueryInvocationHandler.class);
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("Hibernate: NativeQuery from method --->>> "+method);
		NativeQuery annotation = method.getAnnotation(NativeQuery.class);
		final Query q=em.createNativeQuery(annotation.query(), annotation.resultClass());
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				q.setParameter(i+1, args[i]);
			}
			if(showSQL)
				System.out.print("Hibernate: Query Parameters in order of assignment --->>> ");
				System.out.println(Arrays.toString(args));
		}
		
		List<?> result=q.getResultList();
		log.info("Retrieved records: "+result.size());
		if(Collection.class.isAssignableFrom(method.getReturnType()))
			return result;
		else if(result.size()==1)
			return result.get(0);
		else if(result.size()==0)
			return null;
		else
			throw new Exception("Expected a Single result but retrieved more than one record");
	}
	
	
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.INTERFACES)
	public ProjectQueries getProxyedInstance() {
//		NativeQueryInvocationHandler handler=new NativeQueryInvocationHandler();
		ProjectQueries p = (ProjectQueries) Proxy.newProxyInstance(this.getClass().getClassLoader(),
				getNativeQueryInterfaces(), this);
		return p;
	}

	private Class<?>[] getNativeQueryInterfaces() {
		String root = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		List<Class<?>> classList = new LinkedList<Class<?>>();
		listAnnotationCandidate(root, classList, root, FileSystems.getDefault().getSeparator());
		Class<?> v[] = new Class<?>[classList.size()];
		classList.toArray(v);
		return v;
	}

	private void listAnnotationCandidate(final String root, List<Class<?>> files, String strip, String separator) {
		for (final File fileEntry : new File(root).listFiles()) {
			if (fileEntry.isDirectory()) {
				listAnnotationCandidate(fileEntry.getPath(), files, strip, separator);
			} else {
				if (fileEntry.getPath().endsWith(".class") && !fileEntry.getPath().contains("$")) {
					// System.out.println(fileEntry.getPath().replace(strip, ""));
					try {
						Class<?> cl = Class.forName(
								fileEntry.getPath().replace(strip, "").replace(".class", "").replace(separator, "."));
						boolean valid = false;
						Method mth[] = cl.getDeclaredMethods();
						for (int i = 0; i < mth.length; i++) {
							if (mth[i].isAnnotationPresent(NativeQuery.class)) {
								valid = true;
								break;
							}
						}
						if (valid) {// .isAnnotationPresent(NativeQuery.class)
							files.add(cl);
							// System.out.println(fileEntry.getPath().replace(strip, ""));
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}