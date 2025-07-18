package dev.deepslate.fallacy.base.permission.integration

import dev.deepslate.fallacy.utils.permission.PermissionProvider
import dev.deepslate.fallacy.utils.permission.PermissionValue
import net.neoforged.neoforge.server.ServerLifecycleHooks
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

class OpPermissionProvider: PermissionProvider {
    override fun query(uuid: UUID, key: String): PermissionValue {
        val server = ServerLifecycleHooks.getCurrentServer() ?: return PermissionValue.UNDEFINED
        val profiler = server.profileCache?.get(uuid)?.getOrNull() ?: return PermissionValue.DENY
        val permissionLevel = server.getProfilePermissions(profiler)

        return if (permissionLevel == 4) PermissionValue.ALLOW else PermissionValue.DENY
    }

    override fun queryAsync(uuid: UUID, key: String, callback: (PermissionValue) -> Unit) {
        TODO("Not yet implemented")
    }

    override val name: String
        get() = "ServerOp"
}