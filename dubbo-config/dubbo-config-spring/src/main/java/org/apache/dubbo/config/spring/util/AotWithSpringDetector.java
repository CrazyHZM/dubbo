package org.apache.dubbo.config.spring.util;

import org.apache.dubbo.common.utils.NativeDetector;
import org.springframework.core.SpringProperties;


public abstract class AotWithSpringDetector {

    /**
     * System property that indicates the application should run with AOT
     * generated artifacts. If such optimizations are not available, it is
     * recommended to throw an exception rather than fall back to the regular
     * runtime behavior.
     */
    public static final String AOT_ENABLED = "spring.aot.enabled";

    /**
     * Determine whether AOT optimizations must be considered at runtime. This
     * is mandatory in a native image but can be triggered on the JVM using
     * the {@value #AOT_ENABLED} Spring property.
     * @return whether AOT optimizations must be considered
     */
    public static boolean useGeneratedArtifacts() {
        return (NativeDetector.inNativeImage() || SpringProperties.getFlag(AOT_ENABLED));
    }

}
