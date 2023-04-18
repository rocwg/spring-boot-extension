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

package com.livk.redis.controller;

import com.livk.autoconfigure.redis.supprot.UniversalReactiveRedisTemplate;
import com.livk.common.redis.domain.LivkMessage;
import com.livk.redis.entity.Person;
import com.livk.redis.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * <p>
 * MessageController
 * </p>
 *
 * @author livk
 */
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final UniversalReactiveRedisTemplate universalReactiveRedisTemplate;

    private final PersonRepository personRepository;

    @PostMapping("/redis/{id}")
    public Mono<Void> send(@PathVariable("id") Long id, @RequestParam("msg") String msg,
                           @RequestBody Map<String, Object> data) {
        return universalReactiveRedisTemplate
                .convertAndSend(LivkMessage.CHANNEL, LivkMessage.of().setId(id).setMsg(msg).setData(data))
                .flatMap(n -> Mono.empty());
    }

    @PostMapping("/redis/stream")
    public Mono<Void> stream() {
        return universalReactiveRedisTemplate.opsForStream()
                .add(StreamRecords.newRecord()
                        .ofObject("livk-object")
                        .withStreamKey("livk-streamKey"))
                .flatMap(n -> Mono.empty());
    }

    @PostMapping("/redis/hyper-log-log")
    public Mono<Void> add(@RequestParam Object data) {
        return universalReactiveRedisTemplate.opsForHyperLogLog()
                .add("log", data)
                .flatMap(n -> Mono.empty());
    }

    @GetMapping("/redis/hyper-log-log")
    public Mono<Long> get() {
        return universalReactiveRedisTemplate.opsForHyperLogLog()
                .size("log");
    }

    @PostMapping("person")
    public Mono<Void> add(@RequestBody Mono<Person> personMono) {
        return personMono.doOnNext(personRepository::save).then();
    }
}
