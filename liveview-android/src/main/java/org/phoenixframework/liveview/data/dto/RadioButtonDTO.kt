package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxValue
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledSelectedColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledUnselectedColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnselectedColor
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.ThemeHolder.disabledContentAlpha
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

/**
 * Material Design radio button.
 * ```
 * <Row verticalAlignment="center">
 *   <RadioButton phx-value="A" phx-change="setRadioOption" selected={"#{@radioOption == "A"}"} />
 *   <Text>A</Text>
 *   <RadioButton phx-value="B" phx-change="setRadioOption" selected={"#{@radioOption == "B"}"} />
 *   <Text>B</Text>
 *   <RadioButton phx-value="C" phx-change="setRadioOption" selected={"#{@radioOption == "C"}"} />
 *   <Text>C</Text>
 * </Row>
 * ```
 */
internal class RadioButtonDTO private constructor(builder: Builder) :
    ChangeableDTO<String>(builder) {

    private val selected = builder.selected
    private val colors = builder.colors?.toImmutableMap()

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        RadioButton(
            modifier = modifier,
            selected = selected,
            onClick = {
                if (!selected) {
                    changeValueEventName?.let {
                        pushOnChangeEvent(pushEvent, it, value)
                    }
                }
            },
            enabled = enabled,
            colors = getRadioButtonColors(colors),
        )
    }

    @Composable
    private fun getRadioButtonColors(colors: ImmutableMap<String, String>?): RadioButtonColors {
        val defaultValue = RadioButtonDefaults.colors()
        return if (colors == null) {
            defaultValue
        } else {
            RadioButtonDefaults.colors(
                selectedColor = colors[colorAttrSelectedColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                unselectedColor = colors[colorAttrUnselectedColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSelectedColor = colors[colorAttrDisabledSelectedColor]?.toColor()
                    ?: colors[colorAttrSelectedColor]?.toColor()?.copy(alpha = disabledContentAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                disabledUnselectedColor = colors[colorAttrDisabledUnselectedColor]?.toColor()
                    ?: colors[colorAttrUnselectedColor]?.toColor()
                        ?.copy(alpha = disabledContentAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
            )
        }
    }

    internal class Builder : ChangeableDTOBuilder<String>("") {
        var colors: Map<String, String>? = null
            private set

        var selected: Boolean = false
            private set

        /**
         * Whether this radio button is selected or not.
         *
         * ```
         * <RadioButton selected="true" />
         * ```
         * @param selected true if the RadioButton must be selected, false otherwise.
         */
        fun selected(selected: String) = apply {
            this.selected = selected.toBoolean()
        }

        /**
         * Set RadioButton colors.
         * ```
         * <RadioButton
         *   colors="{'selectedColor': '#FFFF0000', 'unselectedColor': '#FF00FF00'}"/>
         * ```
         * @param colors an JSON formatted string, containing the checkbox colors. The color keys
         * supported are: `selectedColor`, `unselectedColor`, `disabledSelectedColor, and
         * `disabledUnselectedColor`.
         */
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)
            }
        }

        fun build() = RadioButtonDTO(this)
    }
}

internal object RadioButtonDtoFactory :
    ComposableViewFactory<RadioButtonDTO, RadioButtonDTO.Builder>() {
    /**
     * Creates a `RadioButtonDTO` object based on the attributes of the input `Attributes` object.
     * RadioButtonDTO co-relates to the RadioButton composable
     * @param attributes the `Attributes` object to create the `RadioButtonDTO` object from
     * @return a `RadioButtonDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): RadioButtonDTO = RadioButtonDTO.Builder().also {
        attributes.fold(
            it
        ) { builder, attribute ->
            if (builder.handleChangeableAttribute(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    attrColors -> builder.colors(attribute.value)
                    attrPhxValue -> builder.value(attribute.value)
                    attrSelected -> builder.selected(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as RadioButtonDTO.Builder
            }
        }
    }.build()
}