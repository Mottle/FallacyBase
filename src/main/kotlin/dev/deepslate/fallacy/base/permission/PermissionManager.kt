package dev.deepslate.fallacy.base.permission

import dev.deepslate.fallacy.base.TheMod
import dev.deepslate.fallacy.base.permission.integration.LuckPermsProvider
import dev.deepslate.fallacy.base.permission.integration.OpPermissionProvider
import dev.deepslate.fallacy.utils.permission.PermissionProvider
import dev.deepslate.fallacy.utils.permission.PermissionValue
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.loading.FMLLoader
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent
import org.slf4j.LoggerFactory
import java.util.*

object PermissionManager : PermissionProvider {
    @Volatile
    private var provider: PermissionProvider = OpPermissionProvider()

    override fun query(uuid: UUID, key: String) = provider.query(uuid, key)

    override fun queryAsync(uuid: UUID, key: String, callback: (PermissionValue) -> Unit) =
        provider.queryAsync(uuid, key, callback)

    override val name: String
        get() = provider.name

    @EventBusSubscriber(modid = TheMod.ID)
    object Handler {
        private val logger = LoggerFactory.getLogger("fallacy-permission")

        @SubscribeEvent
        fun onServerAboutToStart(event: ServerAboutToStartEvent) {
            val luckPerms = FMLLoader.getLoadingModList().mods.any { mod -> mod.modId == "luckperms" }
            provider = if (luckPerms) LuckPermsProvider() else OpPermissionProvider()
            logger.info("Using ${provider.name} for permissions")
        }
    }
}
