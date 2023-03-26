package org.apache.dubbo.springboot.demo.provider;

import org.springframework.boot.SpringApplicationAotProcessor;

/**
 * @author: crazyhzm@apache.org
 */
public class TTTT {

    public static void main(String[] args) throws Exception {
        String str = "org.apache.dubbo.springboot.demo.provider.ProviderApplication, /Users/huazhongming/IDEAWorkSpace/dubbo/dubbo-demo/dubbo-demo-spring-boot/dubbo-demo-spring-boot-provider/target/spring-aot/main/sources, /Users/huazhongming/IDEAWorkSpace/dubbo/dubbo-demo/dubbo-demo-spring-boot/dubbo-demo-spring-boot-provider/target/spring-aot/main/resources, /Users/huazhongming/IDEAWorkSpace/dubbo/dubbo-demo/dubbo-demo-spring-boot/dubbo-demo-spring-boot-provider/target/spring-aot/main/classes, org.apache.dubbo, dubbo-demo-spring-boot-provider";
        String[] strs = str.split(",");
        SpringApplicationAotProcessor.main(strs);


    }

}
