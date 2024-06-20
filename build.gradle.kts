plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	id("org.flywaydb.flyway") version "10.15.0"
	id("checkstyle")
	id("org.jetbrains.kotlinx.kover") version "0.6.1"
	kotlin("plugin.jpa") version "1.9.24"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

apply{
	plugin("checkstyle")
	apply(plugin = "org.jetbrains.kotlinx.kover")
}

group = "com.luizalabs.logistica"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Enhanced logger to print detailed DB information
	// implementation("com.github.gavlyukovskiy:datasource-proxy-spring-boot-starter:1.9.1")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.postgresql:postgresql:42.7.3")
	implementation("org.flywaydb:flyway-core:10.15.0")
	runtimeOnly("org.flywaydb:flyway-database-postgresql:10.13.0")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito:mockito-core")
	testImplementation("org.mockito:mockito-junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.jetbrains.kotlin:kotlin-test")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

checkstyle {
	toolVersion = "10.3.4"
	config = resources.text.fromFile("checkstyle.xml")
}

tasks.withType<Checkstyle> {
	reports {
		xml.required.set(false)
		html.required.set(true)
		html.outputLocation.set(layout.buildDirectory.file("reports/checkstyle/checkstyle.html"))
	}
}

kover {
	verify {
		rule {
			isEnabled = true
			name = "Coverage must be more than 60%"
			bound {
				minValue = 60
			}
		}
	}

	filters {
		classes {
			excludes += listOf("*di.*", "*Factory*")
		}
		annotations {
			excludes += listOf("*Generated", "*CustomAnnotationToExclude")
		}
	}
	htmlReport {
		onCheck.set(false)
	}

	verify {
		rule {
			isEnabled = true
			name = "Line Coverage of Tests must be more than 80%"
			bound {
				minValue = 60
			}
		}
	}
}

tasks.register<Delete>("cleanKoverHtmlReport") {
	delete(file("build/reports/kover/html"))
}

tasks.named("koverHtmlReport") {
	dependsOn("cleanKoverHtmlReport")
}