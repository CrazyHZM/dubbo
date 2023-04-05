package org.apache.dubbo.config.spring.util;

import org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.apache.dubbo.config.spring.context.DubboInfraBeanRegisterPostProcessor;

/**
 * @author: crazyhzm@apache.org
 */
public class Spring6Utils {

    public static Class<?> referenceAnnotationBeanPostProcessor(){
        try {
             return Class.forName("org.apache.dubbo.config.spring6.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor");
        } catch (ClassNotFoundException e) {
            return ReferenceAnnotationBeanPostProcessor.class;
        }
    }

    public static Class<?> dubboInfraBeanRegisterPostProcessor(){
        try {
            return Class.forName("org.apache.dubbo.config.spring6.context.DubboInfraBeanRegisterPostProcessor");
        } catch (ClassNotFoundException e) {
            return DubboInfraBeanRegisterPostProcessor.class;
        }
    }
}
