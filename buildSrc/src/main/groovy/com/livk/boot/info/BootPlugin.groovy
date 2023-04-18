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

package com.livk.boot.info

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.bundling.Jar
import org.springframework.boot.gradle.dsl.SpringBootExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.bundling.BootJar

import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

/**
 * <p>
 * BuildInfoPlugin
 * </p>
 *
 * @author livk
 *
 */
abstract class BootPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.pluginManager.apply(JavaPlugin.class)
        project.pluginManager.apply(SpringBootPlugin.class)
        project.extensions.getByType(SpringBootExtension.class)
                .buildInfo {
                    it.properties { build ->
                        build.group.set(project.group.toString())
                        build.version.set(project.version.toString())
                        build.time.set(DateTimeFormatter.ISO_INSTANT.format(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8))))
                    }
                }
        def bootJar = project.tasks.named(SpringBootPlugin.BOOT_JAR_TASK_NAME).get() as BootJar
        bootJar.archiveBaseName.set(project.name)
        bootJar.archiveFileName.set(bootJar.archiveBaseName.get() + "." + bootJar.archiveExtension.get())
        bootJar.launchScript()
        (project.tasks.named(JavaPlugin.JAR_TASK_NAME).get() as Jar).enabled = false
    }
}
