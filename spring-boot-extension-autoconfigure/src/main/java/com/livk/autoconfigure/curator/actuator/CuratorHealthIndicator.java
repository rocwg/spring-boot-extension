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

package com.livk.autoconfigure.curator.actuator;

import lombok.RequiredArgsConstructor;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

/**
 * @author livk
 */
@RequiredArgsConstructor
public class CuratorHealthIndicator extends AbstractHealthIndicator {

	private final CuratorFramework curatorFramework;

	@Override
	protected void doHealthCheck(Health.Builder builder) {
		try {
			CuratorFrameworkState state = curatorFramework.getState();
			if (state != CuratorFrameworkState.STARTED) {
				builder.down().withDetail("error", "Client not started");
			} else if (curatorFramework.checkExists().forPath("/") == null) {
				builder.down().withDetail("error", "Root for namespace does not exist");
			} else {
				builder.up();
			}
			builder.withDetail("connectionString", curatorFramework.getZookeeperClient().getCurrentConnectionString())
				.withDetail("state", state);
		} catch (Exception e) {
			builder.down(e);
		}
	}
}
