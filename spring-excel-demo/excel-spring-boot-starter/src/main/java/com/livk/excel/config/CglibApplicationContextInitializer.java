package com.livk.excel.config;

import org.burningwave.core.assembler.StaticComponentContainer;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

/**
 * <p>
 * CglibApplicationContextInitializer
 * </p>
 *
 * @author livk
 * @date 2022/1/18
 */
@Configuration
public class CglibApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(@Nullable ConfigurableApplicationContext applicationContext) {
        StaticComponentContainer.Modules.exportAllToAll();
    }
}
