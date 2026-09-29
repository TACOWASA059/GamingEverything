# Publishing checklist

Publishing is intentionally a manual, owner-approved final step.

1. Review the summaries and descriptions in `MOD_LISTING.md`.
2. Use `branding/gamingeverything-icon.png` as the icon and choose gallery images from `branding/showcase/`.
3. Create the `gaming-everything` project on CurseForge and Modrinth.
4. Set `CURSEFORGE_TOKEN`, `MODRINTH_TOKEN`, `CURSEFORGE_PROJECT_ID`, and `MODRINTH_PROJECT_ID` as repository secrets when upload automation is added.
5. Build and test every loader on its matching release branch.
6. Run `gradlew.bat prepareRelease` and check the jars and SHA-256 files under `release-artifacts`.
7. Review `CHANGELOG.md`, screenshots, icon, license, supported versions, and client-only status.
8. Upload only after the project owner gives explicit approval.

The current GitHub Actions workflow builds and stores release artifacts, but does not publish them to CurseForge or Modrinth yet.

Never commit access tokens or project credentials.
