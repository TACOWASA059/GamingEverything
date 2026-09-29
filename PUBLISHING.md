# Publishing checklist

Publishing is intentionally a manual, owner-approved final step.

1. Review the summaries and descriptions in `MOD_LISTING.md`.
2. Use `branding/gamingeverything-icon.png` as the icon and choose gallery images from `branding/showcase/`.
3. Create the `gaming-everything` project on CurseForge and Modrinth.
4. Set `CURSEFORGE_TOKEN` as an environment variable or repository secret. Never put the token in `gradle.properties`.
5. Build and test every loader on its matching release branch.
6. Run `gradlew.bat prepareRelease` and check the jars and SHA-256 files under `release-artifacts`.
7. Review `CHANGELOG.md`, screenshots, icon, license, supported versions, and client-only status.
8. Run `gradlew.bat publishCurseForgeInfo` to inspect every upload without changing CurseForge.
9. After explicit owner approval, run `gradlew.bat publishCurseForge` to build and upload every loader jar for the checked-out release branch.

CurseForge project ID `1717890` is configured in `gradle.properties`. Each Fabric upload declares Fabric API as a required dependency. All files are tagged `Client`, their Minecraft version, and their loader.

The current GitHub Actions workflow builds and stores release artifacts. It does not publish automatically; publication is an explicit Gradle task.

Never commit access tokens or project credentials.
