package com.livk.redisson.lock;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author livk
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class ShopControllerTest {
    @Autowired
    MockMvc mockMvc;


    @Order(1)
    @Test
    void testBuyLocal() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(500);
        CountDownLatch countDownLatch = new CountDownLatch(500);
        for (int i = 0; i < 500; i++) {
            service.submit(() -> {
                try {
                    mockMvc.perform(post("/shop/buy/distributed"))
                            .andExpect(status().isOk());
                    countDownLatch.countDown();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        countDownLatch.await();
        service.shutdown();
    }

    @Order(2)
    @Test
    void testResult() throws Exception {
        mockMvc.perform(get("/shop/result"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("redisson.buyCount", 500).exists())
                .andExpect(jsonPath("redisson.buySucCount", 250).exists())
                .andExpect(jsonPath("redisson.num", 0).exists());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme