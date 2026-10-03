plugins {
    id("dev.kikugie.loom-back-compat")
    id("me.modmuss50.mod-publish-plugin")
    id("maven-publish")
}

val minecraft = stonecutter.current.version
val loader = "fabric"

fun prop(name: String) = project.property(name) as String

for (overlay in listOf(minecraft, stonecutter.current.project)) {
    rootProject.file("src/main/overlays/$overlay").takeIf { it.isDirectory }?.let { sourceSets.main { resources.srcDir(it) } }
}

version = prop("mod.version")
group = prop("mod.group")
base.archivesName = "${prop("mod.id")}-$loader-$minecraft"

repositories {
    mavenCentral()
    exclusiveContent {
        forRepository { maven("https://maven.parchmentmc.org/") { name = "ParchmentMC" } }
        filter { includeGroup("org.parchmentmc.data") }
    }
    exclusiveContent {
        forRepository { maven("https://api.modrinth.com/maven") { name = "Modrinth" } }
        filter { includeGroup("maven.modrinth") }
    }
    maven("https://maven.terraformersmc.com/") { name = "TerraformersMC" }
    maven("https://maven.quiltmc.org/repository/release/") { name = "Quilt" }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-$minecraft:${prop("deps.parchment")}@zip")
    })

    modImplementation("net.fabricmc:fabric-loader:${prop("deps.fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${prop("deps.fabric_api")}")

    // EMI
    modRuntimeOnly("dev.emi:emi-fabric:${prop("deps.emi")}")

    // YACL
    modImplementation("maven.modrinth:yacl:${prop("deps.yacl")}-fabric")
    implementation("org.quiltmc.parsers:json:0.3.0")
    implementation("org.quiltmc.parsers:gson:0.3.0")
    include("org.quiltmc.parsers:json:0.3.0")
    include("org.quiltmc.parsers:gson:0.3.0")
    modImplementation("com.terraformersmc:modmenu:${prop("deps.modmenu")}")

    // Vanilla Backport
    modCompileOnly("maven.modrinth:vanillabackport:${prop("deps.vanilla_backport")}")
    modCompileOnly("maven.modrinth:platform:${prop("deps.platform")}")

    // Jade
    modImplementation("maven.modrinth:jade:${prop("deps.jade")}")
}

loom {
    runs.configureEach {
        ideConfigGenerated(true)
        runDir("runs/$name")
    }
}

val javaVer = prop("deps.java_version").toInt()
java {
    toolchain.languageVersion = JavaLanguageVersion.of(javaVer)
    withSourcesJar()
}

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release = javaVer
    }

    processResources {
        val props = mapOf(
            "version" to project.version,
            "group" to project.group,
            "minecraft_version" to prop("mod.mc_dep_fabric"),
            "fabric_version" to prop("deps.fabric_api"),
            "fabric_loader_version" to prop("deps.fabric_loader"),
            "yacl_version" to prop("deps.yacl"),
            "mod_name" to prop("mod.name"),
            "mod_author" to prop("mod.author"),
            "mod_id" to prop("mod.id"),
            "license" to prop("mod.license"),
            "description" to prop("mod.description"),
            "credits" to prop("mod.credits"),
            "java_version" to javaVer,
            "pack_format" to prop("mod.pack_format"),
        )
        inputs.properties(props)

        filesMatching(listOf("pack.mcmeta", "fabric.mod.json", "*.mixins.json")) {
            expand(props)
        }

        exclude("META-INF/mods.toml", "META-INF/neoforge.mods.toml")

        dependsOn("stonecutterGenerate")
    }

    jar {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${prop("mod.name")}" }
        }

        manifest.attributes(
            "Specification-Title" to prop("mod.name"),
            "Specification-Vendor" to prop("mod.author"),
            "Specification-Version" to archiveVersion,
            "Implementation-Title" to loader,
            "Implementation-Version" to archiveVersion,
            "Implementation-Vendor" to prop("mod.author"),
            "Built-On-Minecraft" to minecraft,
        )
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        from(loomx.modJar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.dir("libs/${project.version}"))
        dependsOn("build")
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifactId = base.archivesName.get()
            from(components["java"])
        }
    }
    repositories {
        System.getenv("local_maven_url")?.let { maven(it) }
    }
}

publishMods {
    file = loomx.modJar.flatMap { it.archiveFile }
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    type = STABLE
    version = "${project.version}-$minecraft-$loader"
    displayName = "${prop("mod.name")} Fabric $minecraft - ${project.version}"
    modLoaders.add(loader)

    curseforge {
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        projectId = prop("publish.curseforge")
        minecraftVersions.add(minecraft)
        client = true
        server = true

        requires("yacl")
    }

    modrinth {
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        projectId = prop("publish.modrinth")
        minecraftVersions.add(minecraft)

        requires("yacl")
    }
}
