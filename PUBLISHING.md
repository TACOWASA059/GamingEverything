# Publishing checklist

Publishing is intentionally a manual, owner-approved final step.

1. Create the `gaming-everything` project on CurseForge and Modrinth.
2. Set `CURSEFORGE_TOKEN`, `MODRINTH_TOKEN`, `CURSEFORGE_PROJECT_ID`, and `MODRINTH_PROJECT_ID` as repository secrets.
3. Build and test every loader on its matching release branch.
4. Check the generated jars and SHA-256 files under `release-artifacts`.
5. Review `CHANGELOG.md`, screenshots, icon, license, supported game versions, and client-only status.
6. Run the publishing workflow manually and approve its protected `publish` environment.

Never commit access tokens or project credentials.

