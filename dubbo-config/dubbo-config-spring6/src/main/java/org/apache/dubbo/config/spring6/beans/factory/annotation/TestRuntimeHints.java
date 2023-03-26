package org.apache.dubbo.config.spring6.beans.factory.annotation;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.http.codec.support.DefaultClientCodecConfigurer;
import org.springframework.http.codec.support.DefaultServerCodecConfigurer;

/**
 * @author: crazyhzm@apache.org
 */
public class TestRuntimeHints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.reflection().registerTypes(
            TypeReference.listOf(DefaultClientCodecConfigurer.class, DefaultServerCodecConfigurer.class),
            typeHint -> typeHint.withMembers(MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS));
    }
}
