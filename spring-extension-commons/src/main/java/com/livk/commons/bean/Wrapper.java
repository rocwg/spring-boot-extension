/*
 * Copyright 2021 spring-boot-extension the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.livk.commons.bean;

/**
 * The interface Wrapper.
 */
public interface Wrapper {

    /**
     * Try unwrap t.
     *
     * @param <T>  the type parameter
     * @param obj  the obj
     * @param type the type
     * @return the t
     */
    static <T> T tryUnwrap(Object obj, Class<T> type) {
        if (obj instanceof Wrapper wrapper) {
            if (wrapper.isWrapperFor(type)) {
                return wrapper.unwrap(type);
            }
        }
        return type.cast(obj);
    }

    /**
     * Unwrap t.
     *
     * @param <T>  the type parameter
     * @param type the type
     * @return the t
     */
    <T> T unwrap(Class<T> type);

    /**
     * Is wrapper for boolean.
     *
     * @param type the type
     * @return the boolean
     */
    boolean isWrapperFor(Class<?> type);
}
