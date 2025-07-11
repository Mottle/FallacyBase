package dev.deepslate.fallacy.utils.command

import com.mojang.brigadier.CommandDispatcher
import dev.deepslate.fallacy.base.permission.PermissionManager
import net.minecraft.commands.CommandSourceStack

fun registerAll(dispatcher: CommandDispatcher<CommandSourceStack>, commands: Iterable<GameCommand>) {
    val converter = CommandConverter()

    for (gcm in commands) {
        val raw = converter.convert(gcm).requires { ctx ->
            if (!ctx.isPlayer) return@requires true
            if (gcm.permissionRequired == null) return@requires true
            return@requires PermissionManager.query(ctx.player!!, gcm.permissionRequired!!).asBoolean()
        }
        dispatcher.register(raw)
    }
}