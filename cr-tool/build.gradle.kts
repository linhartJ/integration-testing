description = ""

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}
val jUnitVersion = "5.7.1"
val kotlinVersion = "1.4.31"
val mockitoVersion = "3.9.0"
val mockitoKotlinVersion = "1.6.0"
val kotlinTestVersion = "1.5.0-RC"
val springVersion = "5.3.6"
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.springframework:spring-core:$springVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    testImplementation("org.springframework:spring-test:$springVersion")
    testImplementation("com.nhaarman:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$jUnitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
}

configurations {
    val testArtifacts by creating {
        extendsFrom(getByName("testRuntime"))
    }
}

val testJar by tasks.registering(Jar::class) {
    from(sourceSets.test.map { it.output })
    archiveClassifier.set("test")
}

artifacts {
    add("testArtifacts", testJar)
}


