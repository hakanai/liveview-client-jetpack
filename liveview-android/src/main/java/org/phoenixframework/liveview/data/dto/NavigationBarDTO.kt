package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design bottom navigation bar.
 * Usually is declared inside of a Scaffold using `bottomBar` template.
 * ```
 * <NavigationBar template="bottomBar">
 *   <NavigationBarItem selected={"#{@selectedTab == "0"}"} phx-click="selectTab" phx-value="0">
 *     <Icon image-vector="filled:HorizontalDistribute" template="icon"/>
 *     <Text template="label">Tab 1</Text>
 *   </NavigationBarItem>
 *   ...
 * </NavigationBar>
 * ```
 */
internal class NavigationBarDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val containerColor = builder.containerColor
    private val contentColor = builder.contentColor
    private val tonalElevation = builder.tonalElevation
    private val windowsInsets = builder.windowInsets

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val containerColor = containerColor ?: NavigationBarDefaults.containerColor
        NavigationBar(
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor
                ?: MaterialTheme.colorScheme.contentColorFor(containerColor),
            tonalElevation = tonalElevation ?: NavigationBarDefaults.Elevation,
            windowInsets = windowsInsets ?: NavigationBarDefaults.windowInsets,
            content = {
                composableNode?.children?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            }
        )
    }

    internal class Builder : ComposableBuilder() {
        var containerColor: Color? = null
            private set
        var contentColor: Color? = null
            private set
        var tonalElevation: Dp? = null
            private set
        var windowInsets: WindowInsets? = null
            private set

        /**
         * The color used for the background of this navigation bar.
         * ```
         * <NavigationBar container-color="#FFFFFF00">...</NavigationBar>
         * ```
         * @param color container color in AARRGGBB format.
         */
        fun containerColor(color: String) = apply {
            this.containerColor = color.toColor()
        }

        /**
         * The preferred color for content inside this navigation bar.
         * ```
         * <NavigationBar content-color="#FFCCCCCC">...</NavigationBar>
         * ```
         * @param color content color in AARRGGBB format.
         */
        fun contentColor(color: String) = apply {
            this.contentColor = color.toColor()
        }

        /**
         * A higher tonal elevation value will result in a darker color in light theme and lighter
         * color in dark theme.
         * ```
         * <NavigationBar tonal-elevation="24">...</NavigationBar>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        fun tonalElevation(tonalElevation: String) = apply {
            if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                this.tonalElevation = tonalElevation.toInt().dp
            }
        }

        /**
         * Window insets to be passed to the navigation bar window via PaddingValues params.
         * ```
         * <ModalBottomSheet window-insets="{'bottom': '100'}" >
         * ```
         * @param insets the space, in Dp, at the each border of the window that the inset
         * represents. The supported values are: `left`, `top`, `bottom`, and `right`.
         */
        fun windowInsets(insets: String) = apply {
            try {
                this.windowInsets = windowInsetsFromString(insets)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun build() = NavigationBarDTO(this)
    }
}

internal object NavigationBarDtoFactory :
    ComposableViewFactory<NavigationBarDTO, NavigationBarDTO.Builder>() {
    /**
     * Creates a `NavigationBarDTO` object based on the attributes of the input `Attributes` object.
     * NavigationBarDTO co-relates to the NavigationBar.
     * @param attributes the `Attributes` object to create the `NavigationBarDTO` object from
     * @return a `NavigationBarDTO` object based on the attributes of the input `Attributes` object.
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): NavigationBarDTO =
        attributes.fold(NavigationBarDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                attrContainerColor -> builder.containerColor(attribute.value)
                attrContentColor -> builder.contentColor(attribute.value)
                attrTonalElevation -> builder.tonalElevation(attribute.value)
                attrWindowInsets -> builder.windowInsets(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as NavigationBarDTO.Builder
        }.build()

    override fun subTags(): Map<String, ComposableViewFactory<*, *>> {
        return mapOf(
            tagNavigationBarItem to NavigationBarItemDtoFactory
        )
    }

    private const val tagNavigationBarItem = "NavigationBarItem"
}