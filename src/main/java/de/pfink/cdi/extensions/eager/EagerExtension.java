package de.pfink.cdi.extensions.eager;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

/**
 * Makes a CDI bean eager (= initialized at app startup)
 * @author Thomas Andraschko
 * @see http://ovaraksin.blogspot.nl/2013/02/eager-cdi-beans.html
 */
@Slf4j
public class EagerExtension implements Extension {
    private List<Bean<?>> eagerBeansList = new ArrayList<Bean<?>>();
 
    public <T> void collect(@Observes ProcessBean<T> event) {
        if (event.getAnnotated().isAnnotationPresent(Eager.class)
            && (event.getAnnotated().isAnnotationPresent(ApplicationScoped.class)
                || event.getAnnotated().isAnnotationPresent(Singleton.class))) {
            eagerBeansList.add(event.getBean());
        }
    }
 
    public void load(@Observes AfterDeploymentValidation event, BeanManager beanManager) {
        log.info("CDI Eager Extension: Loading eager CDI beans");
        for (Bean<?> bean : eagerBeansList) {
            // note: toString() is important to instantiate the bean
            beanManager.getReference(bean, bean.getBeanClass(), beanManager.createCreationalContext(bean)).toString();
        }
    }
}
