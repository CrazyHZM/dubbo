/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.common;

import org.apache.dubbo.common.constants.DeprecatedMethodInvocationCounterConstants;
import org.apache.dubbo.common.logger.ErrorTypeAwareLogger;
import org.apache.dubbo.common.logger.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * Deprecated method invocation counter, which is used by annotation processor.
 * <p>
 * If an IDE says it is unused, just ignore it.
 */
public final class DeprecatedMethodInvocationCounter {
    private DeprecatedMethodInvocationCounter() {
        throw new UnsupportedOperationException("No instance of DeprecatedMethodInvocationCounter for you! ");
    }

    private static final ConcurrentHashMap<String, LongAdder> COUNTERS = new ConcurrentHashMap<>();

    private static final ErrorTypeAwareLogger LOGGER =
            LoggerFactory.getErrorTypeAwareLogger(DeprecatedMethodInvocationCounter.class);

    /**
     * Invoked by (modified) deprecated method.
     *
     * @param methodDefinition filled by annotation processor. (like 'org.apache.dubbo.common.URL.getServiceName()')
     */
    public static void onDeprecatedMethodCalled(String methodDefinition) {
        if (!hasThisMethodInvoked(methodDefinition)) {
            LOGGER.warn(
                    DeprecatedMethodInvocationCounterConstants.ERROR_CODE,
                    DeprecatedMethodInvocationCounterConstants.POSSIBLE_CAUSE,
                    DeprecatedMethodInvocationCounterConstants.EXTENDED_MESSAGE,
                    DeprecatedMethodInvocationCounterConstants.LOGGER_MESSAGE_PREFIX + methodDefinition);
        }

        increaseInvocationCount(methodDefinition);
    }

    private static void increaseInvocationCount(String methodDefinition) {
        COUNTERS.computeIfAbsent(methodDefinition, k -> new LongAdder());
        LongAdder adder = COUNTERS.get(methodDefinition);

        adder.increment();
    }

    public static boolean hasThisMethodInvoked(String methodDefinition) {
        return COUNTERS.containsKey(methodDefinition);
    }

    public static Map<String, Integer> getInvocationRecord() {
        // Perform a deep-copy to avoid concurrent issues.
        HashMap<String, Integer> copyOfCounters = new HashMap<>();

        for (Map.Entry<String, LongAdder> entry : COUNTERS.entrySet()) {
            copyOfCounters.put(entry.getKey(), entry.getValue().intValue());
        }

        return Collections.unmodifiableMap(copyOfCounters);
    }
}
