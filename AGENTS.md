# AGENTS.md

## Module Scope

- `base` is the shared foundation module used by other modules (`thermal`, `hud`, `survive`).
- Keep this module generic and reusable; do not add module-specific gameplay logic that belongs in `thermal` or
  `survive`.

## Build and Dependency Facts

- Standalone Gradle module and git submodule; also included by root multi-project build.
- Uses Java toolchain 21 and Kotlin JVM target 21.
- Uses `net.neoforged.moddev` and Kotlin for Forge.
- Resolves local jars from `../lib` via `flatDir`.

## Main Entrypoint and Core Areas

- Entrypoint: `src/main/kotlin/dev/deepslate/fallacy/base/TheMod.kt`.
- Time and tick utilities:
    - `src/main/kotlin/dev/deepslate/fallacy/base/Horology.kt`
    - `src/main/kotlin/dev/deepslate/fallacy/base/TickCollector.kt`
    - `src/main/kotlin/dev/deepslate/fallacy/utils/Worker.kt`
- Trait system:
    - `src/main/kotlin/dev/deepslate/fallacy/base/trait/Trait.kt`
    - `src/main/kotlin/dev/deepslate/fallacy/base/trait/RegisterHandler.kt`
    - `src/main/kotlin/dev/deepslate/fallacy/base/ModAttachments.kt`
- Permission integration:
    - `src/main/kotlin/dev/deepslate/fallacy/base/permission/PermissionManager.kt`
    - `src/main/kotlin/dev/deepslate/fallacy/base/permission/integration/*`

## Registration and Event Flow

- Event wiring is primarily `@EventBusSubscriber` + `@SubscribeEvent`.
- Custom trait registry is created in `Trait.REGISTRY` and registered in `trait/RegisterHandler.kt`.
- Permission provider is selected at server startup (`LuckPerms` if present, otherwise OP-based provider).
- `Worker.IO_POOL` lifecycle is tied to server start/stop events.
- `ModAttachments.kt` defines trait attachment types; when changing base attachments, ensure registration is correctly
  wired to mod bus.

## Resources, Mixin, and Datagen

- Mod metadata template: `src/main/templates/META-INF/neoforge.mods.toml`.
- Mixin config: `src/main/resources/fallacy_base.mixins.json` (currently no mixin classes listed).
- `src/generated/resources` is included in main resources, but currently mainly cache artifacts.

## Editing Guidance

- Prefer adding cross-module utilities in `src/main/kotlin/dev/deepslate/fallacy/utils/`.
- Keep APIs stable for dependents (`thermal`, `hud`, `survive`), especially command/permission/helper contracts.
- If changing trait, permission, or worker lifecycle behavior, verify dependent modules still compile from root.

## Verification

- Fast check: `./gradlew :base:compileKotlin`
- Full module build: `./gradlew :base:build`
