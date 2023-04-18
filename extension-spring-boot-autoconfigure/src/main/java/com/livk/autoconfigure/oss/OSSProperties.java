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

package com.livk.autoconfigure.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.util.Assert;

/**
 * The type Oss properties.
 *
 * @author livk
 */
@Data
@ConfigurationProperties(OSSProperties.PREFIX)
public class OSSProperties {
    /**
     * The constant PREFIX.
     */
    public static final String PREFIX = "spring.oss";

    private String accessKey;

    private String secretKey;

    private String prefix;

    private String endpoint;

    /**
     * Instantiates a new Oss properties.
     *
     * @param url       the url
     * @param accessKey the access key
     * @param secretKey the secret key
     */
    public OSSProperties(@Name("url") String url,
                         @Name("accessKey") String accessKey,
                         @Name("secretKey") String secretKey) {
        Assert.hasText(url, "url not be blank");
        Assert.hasText(accessKey, "accessKey not be blank");
        Assert.hasText(secretKey, "secretKey not be blank");
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.prefix = prefix(url);
        this.endpoint = endpoint(url, prefix);
    }

    /**
     * Endpoint string.
     *
     * @return the string
     */
    private String endpoint(String url, String prefix) {
        return url.replaceFirst(prefix + ":", "");
    }

    /**
     * Gets prefix.
     *
     * @return the prefix
     */
    private String prefix(String url) {
        int index = url.indexOf(":");
        if (index != -1) {
            return url.substring(0, index);
        }
        throw new RuntimeException("url缺少前缀!");
    }
}
