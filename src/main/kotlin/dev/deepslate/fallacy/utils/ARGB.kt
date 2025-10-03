package dev.deepslate.fallacy.utils

import com.mojang.blaze3d.systems.RenderSystem
import kotlin.math.floor

data class ARGB(val red: Int, val green: Int, val blue: Int, val alpha: Float = 1f) {
    companion object {
        fun from(red: Int, green: Int, blue: Int): ARGB = ARGB(red, green, blue)

        val BLACK = ARGB(0, 0, 0)
        val RED = ARGB(0xff, 0, 0)
        val YELLOW = ARGB(0xff, 0xff, 0)

        fun reset() {
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        }

        fun fromHex(hex: String): ARGB {
            val value = Integer.decode(hex)
            return from(value)
        }

        fun from(value: Int): ARGB {
            if (value > 0xffffff) {
                val a = (value shr 24) and 0xff
                val r = (value shr 16) and 0xff
                val g = (value shr 8) and 0xff
                val b = value and 0xff
                return ARGB(r, g, b, a / 255f)
            }

            val r = (value shr 16) and 0xff
            val g = (value shr 8) and 0xff
            val b = value and 0xff
            return ARGB(r, g, b)
        }

//        val CODEC = RecordCodecBuilder.create { instance ->
//            instance.group(
//                Codec.INT.fieldOf("r").forGetter(RGB::),
//                Codec.INT.fieldOf("g").forGetter { it.green },
//                Codec.INT.fieldOf("b").forGetter { it.blue },
//                Codec.FLOAT.optionalFieldOf("alpha", 1f).forGetter { it.alpha }
//            ).apply(instance) { red, green, blue, alpha -> RGB(red, green, blue, alpha) }
//        }
    }

    fun blend(other: ARGB, rate: Float): ARGB {
        val r = floor(this.red * (1 - rate) + other.red * rate).toInt()
        val g = floor(this.green * (1 - rate) + other.green * rate).toInt()
        val b = floor(this.blue * (1 - rate) + other.blue * rate).toInt()
        return from(r, g, b)
    }

    val value: Int
        get() = (red shl 16) + (green shl 8) + blue

    private fun pushGLWithAlpha(alpha: Float) {
        val r = red / 255.0f
        val g = green / 255.0f
        val b = blue / 255.0f
        RenderSystem.setShaderColor(r, g, b, alpha)
    }

    fun pushGL() = pushGLWithAlpha(alpha)
}