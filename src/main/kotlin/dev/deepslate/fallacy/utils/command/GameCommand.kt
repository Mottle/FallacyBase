package dev.deepslate.fallacy.utils.command

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import dev.deepslate.fallacy.base.permission.PermissionManager
import net.minecraft.commands.CommandSourceStack
import net.minecraft.server.level.ServerPlayer

interface GameCommand {
    val source: String

    val suggestions: Map<String, SuggestionProvider<CommandSourceStack>>

    val permissionRequired: String?

    fun execute(context: CommandContext<CommandSourceStack>): Int

    fun checkPermission(player: ServerPlayer): Boolean =
        if (permissionRequired != null) PermissionManager.query(player, permissionRequired!!).asBoolean() else true
}