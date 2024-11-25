/*
 * Copyright 2021-2024 spring-boot-extension the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *       https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.livk.autoconfigure.dynamic;

import com.livk.context.dynamic.DynamicDatasource;
import com.livk.context.dynamic.intercept.DataSourceInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>
 * DynamicAutoConfigurationTest
 * </p>
 *
 * @author livk
 */
class DynamicAutoConfigurationTest {

	final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
		.withConfiguration(AutoConfigurations.of(DynamicAutoConfiguration.class))
		.withPropertyValues("spring.dynamic.primary=mysql",
				"spring.dynamic.datasource.mysql.url=jdbc:mysql://localhost:3306/test",
				"spring.dynamic.datasource.mysql.username=root", "spring.dynamic.datasource.mysql.password=root");

	@Test
	void curatorDynamicDatasource() {
		this.contextRunner.withUserConfiguration(Config.class).run((context) -> {
			assertThat(context).hasSingleBean(DynamicDatasource.class);
		});
	}

	@Test
	void exponentialDataSourceInterceptor() {
		this.contextRunner.withUserConfiguration(Config.class).run((context) -> {
			assertThat(context).hasSingleBean(DataSourceInterceptor.class);
		});
	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(DynamicDatasourceProperties.class)
	static class Config {

	}

}
