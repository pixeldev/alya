# Alya ![Build Status](https://img.shields.io/github/workflow/status/pixeldev/alya/build/master)

This is a bukkit helper and storage helper to create your own plugins very fast and following SOLID, OOP, DI.

## Repositories

### Releases repository

All versions that doesn't end with "-SNAPSHOT" will be here.

#### Maven (pom.xml)

```xml

<repository>
    <id>unnamed-releases</id>
    <url>https://repo.unnamed.team/repository/unnamed-releases/</url>
</repository>
```

#### Gradle (build.gradle)

```groovy
repositories {
  maven { url 'https://repo.unnamed.team/repository/unnamed-releases/' }
}
```

### Snapshots repository

All versions ending with "-SNAPSHOT" will be here

#### Maven (pom.xml)

```xml

<repository>
    <id>unnamed-snapshots</id>
    <url>https://repo.unnamed.team/repository/unnamed-snapshots/</url>
</repository>
```

#### Gradle (build.gradle)

```groovy
repositories {
  maven { url 'https://repo.unnamed.team/repository/unnamed-snapshots/' }
}
```

### Dependency

- Latest
  snapshot: [![Latest Snapshot](https://img.shields.io/nexus/s/me.pixeldev.alya/bukkit.svg?server=https%3A%2F%2Frepo.unnamed.team)](https://repo.unnamed.team/repository/unnamed-snapshots)
- Latest
  release: [![Latest Release](https://img.shields.io/nexus/r/me.pixeldev.alya/bukkit.svg?server=https%3A%2F%2Frepo.unnamed.team)](https://repo.unnamed.team/repository/unnamed-releases)

#### Maven (pom.xml)

```xml

<dependency>
    <groupId>me.pixeldev.alya</groupId>
    <artifactId>bukkit</artifactId>
    <version>VERSION</version> <!--Check the latest version in the repositories-->
    <classifier>all</classifier>
</dependency>
```

If you're using the annotation processor to AutoListener use in your *maven-compiler-plugin*:

```xml
<configuration>
  <annotationProcessorPaths>
    <annotationProcessorPath>
      <groupId>me.pixeldev.alya</groupId>
      <artifactId>bukkit</artifactId>
      <version>VERSION</version> <!--Check the latest version in the repositories-->
      <classifier>all</classifier>
    </annotationProcessorPath>
  </annotationProcessorPaths>
</configuration>
```

#### Gradle (build.gradle)

```groovy
dependencies {
  implementation('me.pixeldev.alya:bukkit:VERSION:all') {transitive = true}
}
```

If you're using the annotation processor to AutoListener use too:

```groovy
annotationProcessor 'me.pixeldev.alya:bukkit:1.0-SNAPSHOT:all'
```

If you want to use some other module, just change the artifactId to the chosen module.

### Current modules:

- jdk
- gson