# Loader compatibility (Minecraft 1.21.1)

Build versions and required runtime versions are independent. Updating a build
dependency must not automatically raise the minimum accepted by the mod metadata.

| Loader | Minimum | Reason |
| --- | --- | --- |
| NeoForge | 21.1.80 | Bundled upstream Triggers 1.0.1 requires this version. Questlog compiles against it. |
| Fabric Loader | 0.15.11 | Fabric API 0.116.6+1.21.1 requires this version; Triggers and Cloth Config require only 0.14. |

The loader-specific code uses existing event, configuration and networking APIs. Compilation checks API availability;
it does not replace startup, client/server networking or in-game tests.

Use `neoforge_min_version` and `fabric_loader_min_version` for metadata. Use the
corresponding `*_version` properties for compilation. Fabric API is separate from
Fabric Loader and remains a required external mod. Other installed mods can
impose stricter requirements.

Official versions checked on 2026-09-26:

- NeoForge 21.1.252: https://maven.neoforged.net/releases/net/neoforged/neoforge/maven-metadata.xml
- Fabric Loader 0.19.5: https://meta.fabricmc.net/v2/versions/loader

Compilation and resource expansion passed with NeoForge 21.1.80 / Fabric Loader
0.15.11 and NeoForge 21.1.252 / Fabric Loader 0.19.5. Generated metadata still
requires NeoForge 21.1.80 and Fabric Loader 0.15.11. The default build targets these minimum versions to prevent accidental
use of newer loader APIs; recent
versions can be checked with `-Pneoforge_version=21.1.252 -Pfabric_loader_version=0.19.5`.
