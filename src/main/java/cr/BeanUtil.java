package cr;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class BeanUtil implements ApplicationContextAware {
	private static ApplicationContext ctx;
	
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		BeanUtil.ctx=ctx;
	}
	public static <T> T getBean(Class<T> beanClass) {
		return ctx.getBean(beanClass);
	}
	public static ApplicationContext getApplicationContext() {
		return ctx;
	}
}
