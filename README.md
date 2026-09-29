# Gaming Everything

Gaming Everything is a client-side Minecraft mod that gives blocks, the sky, entities, items, first-person hands, particles, the HUD, and menus a bright animated RGB rainbow look.

No server installation is required. Press **G** in game, or run **`/gaming`**, to open the settings screen.

## Supported versions

| Minecraft | Fabric | Forge | NeoForge |
| --- | --- | --- | --- |
| 1.20.1 | Yes | Yes | No |
| 1.21.1 | Yes | Yes | Yes |
| 26.2 | Yes | No | Yes |

Use a jar whose Minecraft version and mod loader exactly match your instance.

## Features

- Animated world-space rainbow coloring for terrain and blocks
- Rainbow sky, living entities, players, items, and first-person hands
- RGB particles, HUD frame, crosshair, menus, and text
- Independent animation speed and wavelength controls for each major visual category
- Color-intensity control and individual effect toggles
- In-game configuration through the **G** key or a client command
- Settings are saved in `config/gamingeverything.json`

## Commands

These are client-side commands and do not require operator permission.

| Command | Effect |
| --- | --- |
| `/gaming` | Open the settings screen |
| `/gaming on` | Enable the whole mod |
| `/gaming off` | Disable the whole mod |
| `/gaming toggle` | Toggle the whole mod |
| `/gaming <category>` | Toggle one category |
| `/gaming <category> on` | Enable one category |
| `/gaming <category> off` | Disable one category |
| `/gaming <category> toggle` | Toggle one category |

Available categories: `master`, `blocks`, `sky`, `entities`, `hands`, `items`, `gui`, `particles`, `hud`, and `special`.

Examples:

```text
/gaming blocks off
/gaming sky toggle
/gaming gui on
```

## Installation

1. Install the supported Fabric, Forge, or NeoForge loader for your Minecraft version.
2. Install any dependencies shown by the download page or launcher.
3. Put the matching Gaming Everything jar in the instance's `mods` folder.
4. Start Minecraft and press **G** to configure the effects.

## Building

Run `gradlew.bat prepareRelease` on Windows or `./gradlew prepareRelease` on Linux/macOS. Release jars and SHA-256 files are collected in `release-artifacts`.

Japanese documentation: [README_ja.md](README_ja.md)
