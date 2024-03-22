/*
 * Copyright 2021-2024 spring-boot-extension the original author or authors.
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
 */

package com.livk.autoconfigure.dynamic;

import com.livk.auto.service.annotation.SpringAutoService;
import com.livk.context.dynamic.DynamicDatasource;
import com.livk.context.dynamic.intercept.DataSourceInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * DynamicAutoConfiguration
 * </p>
 *
 * @author livk
 */
@SpringAutoService(EnableDynamicDatasource.class)
@AutoConfiguration(before = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(DynamicDatasourceProperties.class)
public class DynamicAutoConfiguration {

	/**
	 * Dynamic datasource dynamic datasource.
	 * @param datasourceProperties the datasource properties
	 * @return the dynamic datasource
	 */
	@Bean
	public DynamicDatasource dynamicDatasource(DynamicDatasourceProperties datasourceProperties) {
		Map<Object, Object> datasourceMap = datasourceProperties.getDatasource()
			.entrySet()
			.stream()
			.collect(Collectors.toMap(Map.Entry::getKey,
					entry -> entry.getValue().initializeDataSourceBuilder().build()));
		DynamicDatasource dynamicDatasource = new DynamicDatasource();
		dynamicDatasource.setTargetDataSources(datasourceMap);
		dynamicDatasource.setDefaultTargetDataSource(datasourceMap.get(datasourceProperties.getPrimary()));
		return dynamicDatasource;
	}

	/**
	 * Data source interceptor data source interceptor.
	 * @return the data source interceptor
	 */
	@Bean
	public DataSourceInterceptor dataSourceInterceptor() {
		return new DataSourceInterceptor();
	}

}
