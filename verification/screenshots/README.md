# Prism Launcher verification screenshots

Screenshots captured from the release JARs are grouped by Minecraft version and loader.

| Minecraft | Loader | Directory |
| --- | --- | --- |
| 1.20.1 | Fabric | `1.20.1/fabric/` |
| 1.20.1 | Forge | `1.20.1/forge/` |
| 1.21.1 | Fabric | `1.21.1/fabric/` |
| 1.21.1 | Forge | `1.21.1/forge/` |
| 1.21.1 | NeoForge | `1.21.1/neoforge/` |
| 26.2 | Fabric / OpenGL | `26.2/fabric/opengl/` |
| 26.2 | Fabric / Vulkan | `26.2/fabric/vulkan/` |
| 26.2 | NeoForge / OpenGL | `26.2/neoforge/opengl/` |
| 26.2 | NeoForge / Vulkan | `26.2/neoforge/vulkan/` |

Each completed run should contain:

- `world.png`: world, blocks, sky, entities, and first-person hand visible.
- `settings.png`: Gaming Everything settings GUI visible.
- `latest.log`: the matching Prism Launcher game log.

Captures must come from packaged release JARs launched through Prism Launcher, not a Gradle development run.
For Minecraft 26.2, the same checks are repeated with both rendering backends selected in Video Settings.

NeoForge 26.2 currently has an upstream Early Loading Screen conflict with Vulkan. In the dedicated
NeoForge/Vulkan Prism instance, set `earlyWindowControl = false` in `.minecraft/config/fml.toml`
before launching. Keep the OpenGL instance at its default value so both configurations are tested
independently.

See `../STATUS.md` for the distinction between development-runtime checks and completed Prism
packaged-JAR captures.
