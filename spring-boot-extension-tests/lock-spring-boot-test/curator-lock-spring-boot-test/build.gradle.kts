plugins {
    com.livk.service
}

dependencies {
    implementation(project(":spring-boot-extension-starters:lock-curator-spring-boot-starter"))
    implementation("org.springframework.boot:spring-boot-starter-web")

	testImplementation(project(":spring-extension-testcontainers"))
}
