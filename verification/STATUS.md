# Verification status

This matrix intentionally separates build checks, Gradle development-runtime checks, and the final
Prism Launcher packaged-JAR screenshot pass. A row is not visually verified until both PNG files and
the matching Prism `latest.log` exist in the linked screenshot directory.

| Minecraft | Loader | Backend | Build | Development runtime | Prism JAR + screenshots |
| --- | --- | --- | --- | --- | --- |
| 1.20.1 | Fabric | OpenGL | Pass | Pass through resource/atlas load | Pending capture |
| 1.20.1 | Forge | OpenGL | Pass | Pass through development startup | Pending capture |
| 1.21.1 | Fabric | OpenGL | Pass | Not rerun | Pending capture |
| 1.21.1 | Forge | OpenGL | Pass | Not rerun | Pending capture |
| 1.21.1 | NeoForge | OpenGL | Pass | Not rerun | Pending capture |
| 26.2 | Fabric | OpenGL | Pass | Pass through resource/atlas load | Pending capture |
| 26.2 | Fabric | Vulkan | Pass | Pass through resource/atlas load | Pending capture |
| 26.2 | NeoForge | OpenGL | Pass | Pass through resource/atlas load | Pending capture |
| 26.2 | NeoForge | Vulkan | Pass | Pass through resource/atlas load with ELS disabled | Pending capture |

## NeoForge 26.2 Vulkan

NeoForge's Early Loading Screen currently conflicts with Minecraft 26.2's Vulkan window handoff.
The NeoForge/Vulkan test instance therefore uses `earlyWindowControl = false` in
`.minecraft/config/fml.toml`; this is an instance-side loader workaround, not a change to the mod JAR.

Testing with Vulkan exposed and fixed an actual mod compatibility issue: loader-provided item vertex
pipelines did not supply the mod's former custom `gamingPosition` varying. Item and entity rainbows now
use texture-space coordinates, keeping the effect attached to the rendered object while remaining
compatible with loader-provided pipeline variants.

## Capture requirements

Each completed screenshot directory contains:

- `world.png`
- `settings.png`
- `latest.log`

The final pass must use the JARs in `release-artifacts/` through isolated Prism Launcher instances.

## Current Prism limitation

The isolated Prism 10.0.5 root recognizes all four 26.2 instances, their packaged JARs, and the test
world. In this execution environment, however, Prism attempts to refresh component metadata even with
its offline launch flag and receives `Permission denied` from the network sandbox before spawning the
Minecraft child process. No screenshot has been fabricated or marked complete as a substitute.
