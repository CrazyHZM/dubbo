package org.apache.dubbo.config.spring.context;

import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.config.spring.context.annotation.DubboConfigConfigurationRegistrar;
import org.apache.dubbo.config.spring.extension.SpringExtensionInjector;
import org.apache.dubbo.config.spring.util.DubboBeanUtils;
import org.apache.dubbo.config.spring.util.EnvironmentUtils;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.ModuleModel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.SortedMap;

/**
 * @author: crazyhzm@apache.org
 */
public class DubboContextPostProcessor implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    /**
     * The bean name of {@link DubboConfigConfigurationRegistrar}
     */
    public final static String BEAN_NAME = "dubboContextPostProcessor";

    private BeanDefinitionRegistry registry;

    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ApplicationModel applicationModel = DubboBeanUtils.getApplicationModel(beanFactory);
        ModuleModel moduleModel = DubboBeanUtils.getModuleModel(beanFactory);

        // Initialize SpringExtensionInjector
        SpringExtensionInjector.get(applicationModel).init(applicationContext);
        SpringExtensionInjector.get(moduleModel).init(applicationContext);
        DubboBeanUtils.getInitializationContext(beanFactory).setApplicationContext(applicationContext);

        // Initialize dubbo Environment before ConfigManager
        // Extract dubbo props from Spring env and put them to app config
        ConfigurableEnvironment environment = (ConfigurableEnvironment) applicationContext.getEnvironment();
        SortedMap<String, String> dubboProperties = EnvironmentUtils.filterDubboProperties(environment);
        applicationModel.getModelEnvironment().setAppConfigMap(dubboProperties);

        // register ConfigManager singleton
        beanFactory.registerSingleton(ConfigManager.BEAN_NAME, applicationModel.getApplicationConfigManager());

    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        DubboSpringInitializer.initialize(beanDefinitionRegistry);
        this.registry = beanDefinitionRegistry;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
