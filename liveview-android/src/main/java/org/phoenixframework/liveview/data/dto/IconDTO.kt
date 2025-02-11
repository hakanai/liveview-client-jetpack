package org.phoenixframework.liveview.data.dto

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrContentDescription
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrTint
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.paddingIfNotNull
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

/**
 * A Material Design icon component that draws a local imageVector using tint. Icon is an
 * opinionated component designed to be used with single-color icons so that they can be tinted
 * correctly for the component they are placed in. For generic images that should not be tinted,
 * and do not follow the recommended icon size, use the generic Image instead. For a clickable icon,
 * see IconButton.
 * ```
 * <Icon image-vector="filled:Add" />
 * ```
 */
internal class IconDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val contentDescription: String = builder.contentDescription
    private val tint: Color? = builder.tint
    private val imageVector: ImageVector? = builder.imageVector

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        imageVector?.let { imageVector ->
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = tint ?: LocalContentColor.current,
                modifier = modifier.paddingIfNotNull(paddingValues)
            )
        }
    }

    internal class Builder : ComposableBuilder() {
        var contentDescription: String = ""
            private set
        var tint: Color? = null
            private set
        var imageVector: ImageVector? = null
            private set

        /**
         * Sets the icon content description fro accessibility purpose.
         *
         * ```
         * <Icon content-description="Save Icon" />
         * ```
         * @param contentDescription string representing the icon's content description
         */
        fun contentDescription(contentDescription: String) = apply {
            this.contentDescription = contentDescription
        }

        /**
         * Tint color to be applied to icon.
         * ```
         * <Icon tint="#FFFF0000"/>
         * ```
         * @param tintColor the icon tint color in AARRGGBB format.
         */
        fun tint(tintColor: String) = apply {
            this.tint = tintColor.toColor()
        }

        /**
         * Material icon to be drawn. This icon is provided by the Material Icons library.
         * There are five distinct icon themes: Filled, Outlined, Rounded, TwoTone, and Sharp. Each
         * theme contains the same icons, but with a distinct visual style.
         * See all available icons [here](https://developer.android.com/reference/kotlin/androidx/compose/material/icons/package-summary).
         * ```
         * <Icon image-vector="filled:ChevronLeft"/>
         * ```
         * @param icon material icon following the pattern: *theme:icon* (e.g.: `"filled:Send"`).
         */
        fun imageVector(icon: String) = apply {
            imageVector = getIcon(icon)
        }

        fun build() = IconDTO(this)

        companion object {
            private val iconCache = mutableMapOf<String, ImageVector>()

            fun getIcon(icon: String): ImageVector? {
                if (!iconCache.containsKey(icon)) {
                    icon.toMaterialIcon()?.let { iconCache[icon] = it }
                }
                return iconCache[icon]
            }
        }
    }
}

internal object IconDtoFactory : ComposableViewFactory<IconDTO, IconDTO.Builder>() {
    /**
     * Creates a `IconDTO` object based on the attributes of the input `Attributes` object.
     * IconDTO co-relates to the Icon composable
     * @param attributes the `Attributes` object to create the `IconDTO` object from
     * @return a `IconDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): IconDTO = attributes.fold(
        IconDTO.Builder()
    ) { builder, attribute ->
        when (attribute.name) {
            attrImageVector -> builder.imageVector(attribute.value)
            attrTint -> builder.tint(attribute.value)
            attrContentDescription -> builder.contentDescription(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as IconDTO.Builder
    }.build()
}

private fun String.toMaterialIcon(): ImageVector? = try {
    val imageParameters = split(":")
    val themePackage = imageParameters.first()
    val action = imageParameters.last()
    val clazzName = "androidx.compose.material.icons.${themePackage.lowercase()}.${action}Kt"
    val iconClass = Class.forName(clazzName)
    val method = iconClass.declaredMethods.first()

    val theme: Any = when (themePackage) {
        "filled" -> Icons.Filled
        "rounded" -> Icons.Rounded
        "outlined" -> Icons.Outlined
        "sharp" -> Icons.Sharp
        "twoTone" -> Icons.TwoTone
        "autoMirrored.filled" -> Icons.AutoMirrored.Filled
        "autoMirrored.rounded" -> Icons.AutoMirrored.Rounded
        "autoMirrored.outlined" -> Icons.AutoMirrored.Outlined
        "autoMirrored.sharp" -> Icons.AutoMirrored.Sharp
        "autoMirrored.twoTone" -> Icons.AutoMirrored.TwoTone
        else -> Icons.Filled
    }
    method.invoke(null, theme) as ImageVector
} catch (e: Throwable) {
    Log.e("IconDTO", e.message ?: "Icon [$this] not found")
    null
}