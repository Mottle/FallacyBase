package dev.deepslate.fallacy.utils.permission

enum class PermissionValue {
    ALLOW,
    DENY,
    UNDEFINED;

    fun asBoolean() = when (this) {
        ALLOW -> true
        else -> false
    }
}