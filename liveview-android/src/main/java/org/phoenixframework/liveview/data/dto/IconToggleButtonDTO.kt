package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrBorder
import org.phoenixframework.liveview.data.constants.Attrs.attrChecked
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckedContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledContentColor
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design icon toggle buttons.
 * ```
 * <IconToggleButton checked={"#{@isChecked}"} phx-change="toggleCheck">
 *   <Icon image-vector="filled:Check" />
 * </IconToggleButton>
 * <FilledIconToggleButton checked={"#{@isChecked}"} phx-change="toggleCheck">
 *   <Icon image-vector="filled:Check" />
 * </FilledIconToggleButton>
 * <FilledTonalIconToggleButton checked={"#{@isChecked}"} phx-change="toggleCheck">
 *   <Icon image-vector="filled:Check" />
 * </FilledTonalIconToggleButton>
 * <OutlinedIconToggleButton checked={"#{@isChecked}"} phx-change="toggleCheck">
 *   <Icon image-vector="filled:Check" />
 * </FilledTonalIconToggleButton>
 * ```
 */
internal class IconToggleButtonDTO private constructor(builder: Builder) :
    ChangeableDTO<Boolean>(builder) {
    private val border: BorderStroke? = builder.border
    private val colors = builder.colors?.toImmutableMap()
    private val shape: Shape? = builder.shape

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        var stateValue by remember(composableNode) {
            mutableStateOf(value)
        }
        when (composableNode?.node?.tag) {
            ComposableTypes.iconToggleButton -> {
                IconToggleButton(
                    checked = stateValue,
                    onCheckedChange = {
                        stateValue = it
                    },
                    modifier = modifier,
                    enabled = enabled,
                    colors = getIconToggleButtonColors(colors),
                    // TODO interactionSource: MutableInteractionSource,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }
            }

            ComposableTypes.filledIconToggleButton -> {
                FilledIconToggleButton(
                    checked = stateValue,
                    onCheckedChange = {
                        stateValue = it
                    },
                    modifier = modifier,
                    enabled = enabled,
                    shape = shape ?: IconButtonDefaults.filledShape,
                    colors = getFilledIconToggleButtonColors(colors),
                    // TODO interactionSource: MutableInteractionSource,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }
            }

            ComposableTypes.filledTonalIconToggleButton -> {
                FilledTonalIconToggleButton(
                    checked = stateValue,
                    onCheckedChange = {
                        stateValue = it
                    },
                    modifier = modifier,
                    enabled = enabled,
                    shape = shape ?: IconButtonDefaults.filledShape,
                    colors = getFilledTonalIconToggleButtonColors(colors),
                    // TODO interactionSource: MutableInteractionSource,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }
            }

            ComposableTypes.outlinedIconToggleButton -> {
                OutlinedIconToggleButton(
                    checked = stateValue,
                    onCheckedChange = {
                        stateValue = it
                    },
                    modifier = modifier,
                    enabled = enabled,
                    shape = shape ?: IconButtonDefaults.filledShape,
                    colors = getOutlinedIconToggleButtonColors(colors),
                    border = border ?: IconButtonDefaults.outlinedIconToggleButtonBorder(
                        enabled,
                        stateValue
                    ),
                    // TODO interactionSource: MutableInteractionSource,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }
            }
        }

        LaunchedEffect(composableNode) {
            changeValueEventName?.let { event ->
                snapshotFlow { stateValue }
                    .onChangeable()
                    .collect { value ->
                        pushOnChangeEvent(pushEvent, event, value)
                    }
            }
        }
    }

    @Composable
    private fun getIconToggleButtonColors(colors: ImmutableMap<String, String>?): IconToggleButtonColors {
        val defaultValue = IconButtonDefaults.iconToggleButtonColors()
        return if (colors == null) {
            defaultValue
        } else {
            val contentColor = colors[colorAttrContentColor]?.toColor()
                ?: LocalContentColor.current
            IconButtonDefaults.iconToggleButtonColors(
                containerColor = colors[colorAttrContainerColor]?.toColor()
                    ?: Color.Transparent,
                contentColor = contentColor,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: Color.Transparent,
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: contentColor.copy(alpha = 0.38f),
                checkedContainerColor = colors[colorAttrCheckedContainerColor]?.toColor()
                    ?: Color.Transparent,
                checkedContentColor = colors[colorAttrCheckedContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
            )
        }
    }

    @Composable
    private fun getFilledIconToggleButtonColors(colors: ImmutableMap<String, String>?): IconToggleButtonColors {
        val defaultValue = IconButtonDefaults.filledIconToggleButtonColors()
        return if (colors == null) {
            defaultValue
        } else {
            val checkedContainerColor = colors[colorAttrCheckedContainerColor]?.toColor()
                ?: MaterialTheme.colorScheme.primary
            IconButtonDefaults.filledIconToggleButtonColors(
                containerColor = colors[colorAttrContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surfaceVariant,
                contentColor = colors[colorAttrContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                checkedContainerColor = checkedContainerColor,
                checkedContentColor = colors[colorAttrCheckedContentColor]?.toColor()
                    ?: contentColorFor(checkedContainerColor),
            )
        }
    }

    @Composable
    private fun getFilledTonalIconToggleButtonColors(colors: ImmutableMap<String, String>?): IconToggleButtonColors {
        val defaultValue = IconButtonDefaults.filledTonalIconToggleButtonColors()
        return if (colors == null) {
            defaultValue
        } else {
            val containerColor = colors[colorAttrContainerColor]?.toColor()
                ?: MaterialTheme.colorScheme.surfaceVariant
            IconButtonDefaults.filledTonalIconToggleButtonColors(
                containerColor = containerColor,
                contentColor = colors[colorAttrContentColor]?.toColor()
                    ?: contentColorFor(containerColor),
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                checkedContainerColor = colors[colorAttrCheckedContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.secondaryContainer,
                checkedContentColor = colors[colorAttrCheckedContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }

    @Composable
    private fun getOutlinedIconToggleButtonColors(colors: ImmutableMap<String, String>?): IconToggleButtonColors {
        val defaultValue = IconButtonDefaults.outlinedIconToggleButtonColors()
        return if (colors == null) {
            defaultValue
        } else {
            val contentColor = colors[colorAttrContentColor]?.toColor()
                ?: LocalContentColor.current
            val checkedContainerColor = colors[colorAttrCheckedContainerColor]?.toColor()
                ?: MaterialTheme.colorScheme.inverseSurface
            IconButtonDefaults.outlinedIconToggleButtonColors(
                containerColor = colors[colorAttrContainerColor]?.toColor()
                    ?: Color.Transparent,
                contentColor = contentColor,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: Color.Transparent,
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: contentColor.copy(alpha = 0.38f),
                checkedContainerColor = colors[colorAttrCheckedContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.inverseSurface,
                checkedContentColor = colors[colorAttrCheckedContentColor]?.toColor()
                    ?: contentColorFor(checkedContainerColor)
            )
        }
    }

    internal class Builder : ChangeableDTOBuilder<Boolean>(false) {
        var border: BorderStroke? = null
            private set
        var colors: Map<String, String>? = null
            private set
        var shape: Shape? = null
            private set

        /**
         * The border to draw around the container of this button. This property is used just for
         * `OutlineIconToggleButton`.
         * ```
         * <OutlineIconToggleButton border="{'width': 2, 'color': '#FF0000FF'}">...</OutlineIconToggleButton>
         * ```
         * @param border a JSON representing the border object. The `width` key is an int value
         * representing button border's width content. The `color` key must be specified as a string
         * in the AARRGGBB format
         */
        fun border(border: String) = apply {
            this.border = borderFromString(border)
        }

        /**
         * Set IconToggleButton colors.
         * ```
         * <IconToggleButton
         *   colors="{'containerColor': '#FFFF0000', 'contentColor': '#FF00FF00'}"/>
         * ```
         * @param colors an JSON formatted string, containing the icon toggle button colors. The
         * color keys supported are: `containerColor`, `contentColor`, `disabledContainerColor,
         * `disabledContentColor`, `checkedContainerColor`, and `checkedContentColor`.
         */
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)
            }
        }

        /**
         * Defines the shape of the button's container, border, and shadow (when using elevation).
         * This attribute is only used by `FilledIconToggleButton`, `FilledTonalIconToggleButton`,
         * and `OutlinedIconButton`.
         * ```
         * <FilledIconToggleButton shape="rectangle" >...</FilledIconToggleButton>
         * ```
         * @param shape button's shape. Supported values are: `circle`,
         * `rectangle`, or an integer representing the curve size applied to all four corners.
         */
        fun shape(shape: String) = apply {
            this.shape = shapeFromString(shape)
        }

        fun build() = IconToggleButtonDTO(this)
    }
}

internal object IconToggleButtonDtoFactory :
    ComposableViewFactory<IconToggleButtonDTO, IconToggleButtonDTO.Builder>() {
    /**
     * Creates a `IconToggleButtonDTO` object based on the attributes of the input `Attributes`
     * object. IconToggleButtonDTO co-relates to the IconToggleButton composables.
     * @param attributes the `Attributes` object to create the `IconToggleButtonDTO` object from
     * @return a `IconToggleButtonDTO` object based on the attributes of the input `Attributes`
     * object.
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): IconToggleButtonDTO = IconToggleButtonDTO.Builder().also {
        attributes.fold(
            it
        ) { builder, attribute ->
            if (builder.handleChangeableAttribute(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    attrBorder -> builder.border(attribute.value)
                    attrChecked -> builder.value(attribute.value.toBoolean())
                    attrColors -> builder.colors(attribute.value)
                    attrShape -> builder.shape(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as IconToggleButtonDTO.Builder
            }
        }
    }.build()
}