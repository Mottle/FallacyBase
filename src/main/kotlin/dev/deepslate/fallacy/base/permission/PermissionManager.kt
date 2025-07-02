package dev.deepslate.fallacy.base.permission

import dev.deepslate.fallacy.base.TheMod
import dev.deepslate.fallacy.base.permission.integration.LuckPermsProvider
import dev.deepslate.fallacy.base.permission.integration.SimplePermissionProvider
import dev.deepslate.fallacy.utils.permission.PermissionProvider
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.loading.FMLLoader
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent
import org.slf4j.LoggerFactory

private var instance: PermissionProvider = SimplePermissionProvider()

object PermissionManager : PermissionProvider by instance {
    @EventBusSubscriber(modid = TheMod.ID)
    object Handler {
        private val logger = LoggerFactory.getLogger("fallacy-permission")

        @SubscribeEvent
        fun onServerAboutToStart(event: ServerAboutToStartEvent) {
            val luckPerms = FMLLoader.getLoadingModList().mods.any { mod -> mod.modId == "luckperms" }

            if (luckPerms) {
                instance = LuckPermsProvider()
            }

            logger.info("Using ${instance.name} for permissions")
        }
    }
}