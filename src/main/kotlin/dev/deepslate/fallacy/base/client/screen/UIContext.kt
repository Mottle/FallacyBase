package dev.deepslate.fallacy.base.client.screen

import com.github.wintersteve25.tau.components.base.UIComponent
import dev.deepslate.fallacy.base.client.screen.component.ExtendedUIRender
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen

data class UIContext(val visited: List<VisitedUI> = emptyList()) {
    sealed class VisitedUI {

        abstract fun display()

        data class Screen(val screen: net.minecraft.client.gui.screens.Screen) : VisitedUI() {
            override fun display() {
                Minecraft.getInstance().setScreen(screen)
            }
        }

        data class UIComponent(val uiComponent: com.github.wintersteve25.tau.components.base.UIComponent) :
            VisitedUI() {
            override fun display() {
                Minecraft.getInstance().setScreen(ExtendedUIRender(uiComponent))
            }
        }
    }

    fun previousUI() = visited.first()

    fun displayPreviousUI() {
        previousUI().display()
    }

    fun generateNext(uiComponent: UIComponent) = UIContext(visited + VisitedUI.UIComponent(uiComponent))

    fun generateNext(screen: Screen) = UIContext(visited + VisitedUI.Screen(screen))
}