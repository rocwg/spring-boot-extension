package com.livk.webflux.controller;

import com.livk.crypto.annotation.CryptoDecrypt;
import com.livk.webflux.entity.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author livk
 */
@Slf4j
@RestController
@RequestMapping("info")
public class InfoController {

    @PostMapping("{id}")
    public Map<String, Info> info(@PathVariable("id") @CryptoDecrypt Long variableId,
                                  @RequestParam("id") @CryptoDecrypt Long paramId,
                                  @RequestBody Info info) {
        log.info("PathVariable:{}", variableId);
        log.info("RequestParam:{}", paramId);
        log.info("RequestBody:{}", info);
        return Map.of("id", new Info(variableId, paramId), "body", info);
    }
}