
plugins {
    id("java-library")
}

apply(from = "../gradle/kotlin.gradle")
apply(from = "../gradle/publish.gradle")
apply(from = "../gradle/dokka.gradle")

dependencies {
    val nettyVersion = properties["nettyVersion"]

    "api"(project(":http_client"))
    "api"(platform("io.netty:netty-bom:$nettyVersion"))
}
