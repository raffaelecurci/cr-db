package cr.dbo.repository.proxy;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import cr.dbo.repository.ProjectQueries;
import cr.dbo.repository.annotation.NativeQuery;

@Configuration
public class NativeQueryInvocationHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(method);
		if (args != null) {
			// Arrays.stream(args).forEach(c->c.toString());
		}

		return null;
	}
	
	
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.INTERFACES)
	public ProjectQueries getProxyedInstance() {
		NativeQueryInvocationHandler handler=new NativeQueryInvocationHandler();
		ProjectQueries p = (ProjectQueries) Proxy.newProxyInstance(this.getClass().getClassLoader(),
				getNativeQueryInterfaces(), handler);
		// NativeQueryInvocationHandler handler = this;
		// ProjectQueries p = (ProjectQueries) Proxy.newProxyInstance(
		// NativeQueryInvocationHandler.class.getClassLoader(),
		// new Class[]{ProjectQueries.class}, this);
		// System.out.println("\n\n proxyed:"+p+"\n\n");
		// if(p==null)
		// System.exit(0);
		return p;
	}

	private Class<?>[] getNativeQueryInterfaces() {
		String root = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		List<Class<?>> classList = new LinkedList<Class<?>>();
		listAnnotationCandidate(root, classList, root, FileSystems.getDefault().getSeparator());
		Class<?> v[] = new Class<?>[classList.size() + 1];
		classList.toArray(v);
		// cr.dbo.repository.proxy
		try {
			v[classList.size()] = Class.forName("cr.dbo.repository.proxy.Queries");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\t\t\t{START}");
		System.out.println();
		System.out.println(Arrays.toString(v));
		System.out.println();
		System.out.println("\t\t\t{END}");
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