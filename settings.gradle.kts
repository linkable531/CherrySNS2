pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        jcenter() //추가
        maven(url = "https://jitpack.io") //추가

        google()
        mavenCentral()
    }
}

rootProject.name = "Cherry"
include(":app")
