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

package com.livk.rsocket.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * <p>
 * Message
 * </p>
 *
 * @author livk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String from;

    private String to;

    private long index;

    private long created = Instant.now().getEpochSecond();

    public Message(String from, String to) {
        this(from, to, 0);
    }

    public Message(String from, String to, long index) {
        this.from = from;
        this.to = to;
        this.index = index;
    }

}
