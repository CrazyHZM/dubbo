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
package org.apache.dubbo.common.utils;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import static org.apache.dubbo.common.utils.StringConstantFieldValuePredicate.of;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link StringConstantFieldValuePredicate} Test
 *
 * @since 2.7.8
 */
class StringConstantFieldValuePredicateTest {

    public static final String S1 = "1";

    public static final Object O1 = "2";

    public static final Object O2 = 3;

    @Test
    void test() {
        Predicate<String> predicate = of(getClass());
        assertTrue(predicate.test("1"));
        assertTrue(predicate.test("2"));
        assertFalse(predicate.test("3"));
    }
}
