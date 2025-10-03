package dev.deepslate.fallacy.base.client.screen.component

import com.github.wintersteve25.tau.components.base.UIComponent
import com.github.wintersteve25.tau.components.layout.Stack
import com.github.wintersteve25.tau.components.utils.Sized
import com.github.wintersteve25.tau.components.utils.Texture
import com.github.wintersteve25.tau.layout.Layout
import com.github.wintersteve25.tau.theme.Theme
import com.github.wintersteve25.tau.utils.SimpleVec2i
import com.github.wintersteve25.tau.utils.Size
import dev.deepslate.fallacy.base.TheMod
import dev.deepslate.fallacy.base.client.screen.component.primitive.ColoredTexture
import dev.deepslate.fallacy.utils.ARGB

class ProcessBar(val ratio: Int, val color: ARGB = ARGB.fromHex("0x32cd32")) : UIComponent {

    companion object {
        @JvmStatic
        val DEFAULT_TEXTURE = TheMod.withID("textures/gui/ui0.png")

        @JvmStatic
        val DEFAULT_SIZE = SimpleVec2i(102, 5)
    }

    override fun build(
        layout: Layout,
        theme: Theme
    ): UIComponent {
        val process = (102 * (ratio / 100f)).toInt()

        val backgroundBar = Texture.Builder(DEFAULT_TEXTURE).withUv(SimpleVec2i(0, 0)).withUvSize(DEFAULT_SIZE)
            .withSize(DEFAULT_SIZE)
        val frontBar =
            ColoredTexture.Builder(DEFAULT_TEXTURE).withUv(SimpleVec2i(0, 5)).withUvSize(SimpleVec2i(process, 5))
                .withSize(SimpleVec2i(process, 5)).withColor(color)

        return Sized(Size.staticSize(DEFAULT_SIZE), Stack.Builder().build(backgroundBar, frontBar))
    }
}