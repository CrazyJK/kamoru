package jk.springframework.beans.factory.config;

import java.lang.reflect.Method;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.Assert;

/**
 * jk bean post processor<br>
 * need to property 'beans' of Map type. {key = beanName, value = methodName}
 * @author kamoru
 *
 */
@Slf4j
public class JKPostProcessor implements BeanPostProcessor {

	private Map<String, String> beans;
	
	/**set bean infomation. {key = beanName, value = methodName}
	 * @param beans
	 */
	public void setBeans(Map<String, String> beans) {
		Assert.notNull(beans, "'beans' must not be null");
		this.beans = beans;
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		// Nothing to do
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		log.debug(beanName);
		for (Map.Entry<String, String> entry : beans.entrySet()) {
			if (beanName.equals(entry.getKey())) {
				log.info("attempt to {}.{}", entry.getKey(), entry.getValue());
				try {
					Method method = bean.getClass().getDeclaredMethod(entry.getValue());
					method.invoke(bean);
				} catch (Exception e) {
					log.error("Error", e);
				}
			}
		}
		return bean;
	}

}
