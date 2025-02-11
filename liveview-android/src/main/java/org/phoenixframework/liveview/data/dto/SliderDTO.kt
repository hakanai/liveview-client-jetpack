package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.compositeOver
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrMaxValue
import org.phoenixframework.liveview.data.constants.Attrs.attrMinValue
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxValue
import org.phoenixframework.liveview.data.constants.Attrs.attrSteps
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrActiveTickColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrActiveTrackColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledActiveTickColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledActiveTrackColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledInactiveTickColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledInactiveTrackColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledThumbColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrInactiveTickColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrInactiveTrackColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrThumbColor
import org.phoenixframework.liveview.data.constants.Templates.templateEndThumb
import org.phoenixframework.liveview.data.constants.Templates.templateStartThumb
import org.phoenixframework.liveview.data.constants.Templates.templateThumb
import org.phoenixframework.liveview.data.constants.Templates.templateTrack
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.ThemeHolder.disabledContainerAlpha
import org.phoenixframework.liveview.domain.ThemeHolder.disabledContentAlpha
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design slider.
 * ```
 * // sliderValue is a float
 * <Slider
 *   phx-value={@sliderValue}
 *   phx-change="setSliderValue"
 *   min-value="0"
 *   max-value="100" />
 * // sliderRange is an array of two positions
 * <RangeSlider
 *   phx-value={Enum.join(@sliderRange, ",")}
 *   phx-change="setSliderRange"
 *   min-value="0"
 *   max-value="100" />
 * ```
 * It's also possible to customize the track of both types of slider using a child with the
 * template set as "track". The thumb of the `Slider` component can be customized using the "thumb"
 * template.
 * ```
 * <Slider ...>
 *   <Box size="40" clip="4" background="#FFFF00FF" template="thumb"/>
 *   <Box width="fill" height="10" background="#FF0000FF" template="track"/>
 * </Slider>
 * ```
 * For the `RangeSlider` component, you can customize both start and end thumbs using "start-thumb"
 * and "end-thumb" templates respectively.
 * ```
 * <RangeSlider ...>
 *   <Box size="40" clip="4" background="#FFFF00FF" template="start-thumb"/>
 *   <Box size="40" clip="circle" background="#FF0000FF" template="end-thumb"/>
 * </RangeSlider>
 * ```
 */
internal class SliderDTO private constructor(builder: Builder) : ChangeableDTO<Float>(builder) {
    private val minValue = builder.minValue
    private val maxValue = builder.maxValue
    private val steps = builder.steps
    private val colors = builder.colors?.toImmutableMap()
    private val range = builder.range

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val colors = getSliderColors(colors)
        val track = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTrack }
        }
        when (composableNode?.node?.tag) {
            ComposableTypes.slider -> {
                val sliderState = remember {
                    SliderState(
                        value = value,
                        valueRange = minValue..maxValue,
                        steps = steps,
                    )
                }
                val thumb = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == templateThumb }
                }

                Slider(
                    state = sliderState,
                    modifier = modifier,
                    enabled = enabled,
                    colors = colors,
                    thumb = {
                        thumb?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        } ?: SliderDefaults.Thumb(
                            interactionSource = interactionSource,
                            colors = colors,
                            enabled = enabled
                        )
                    },
                    track = { positions ->
                        track?.let {
                            //TODO how to pass the positions to the child?
                            PhxLiveView(it, pushEvent, composableNode, null)
                        } ?: SliderDefaults.Track(
                            colors = colors,
                            enabled = enabled,
                            sliderState = sliderState,
                        )
                    }
                )

                LaunchedEffect(composableNode) {
                    changeValueEventName?.let { event ->
                        snapshotFlow { sliderState.value }
                            .onChangeable()
                            .collect { value ->
                                pushOnChangeEvent(pushEvent, event, value)
                            }
                    }
                }
            }

            ComposableTypes.rangeSlider -> {
                val endInteractionSource = remember { MutableInteractionSource() }
                val rangeSliderState = remember {
                    RangeSliderState(
                        activeRangeStart = range.start,
                        activeRangeEnd = range.endInclusive,
                        steps = steps,
                        valueRange = minValue..maxValue,
                    )
                }
                val startThumb = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == templateStartThumb }
                }
                val endThumb = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == templateEndThumb }
                }
                RangeSlider(
                    state = rangeSliderState,
                    modifier = modifier,
                    enabled = enabled,
                    colors = colors,
                    track = { positions ->
                        track?.let {
                            //TODO how to pass the positions to the child?
                            PhxLiveView(it, pushEvent, composableNode, null)
                        } ?: SliderDefaults.Track(
                            colors = colors,
                            enabled = enabled,
                            rangeSliderState = rangeSliderState
                        )
                    },
                    startThumb = {
                        startThumb?.let {
                            //TODO how to pass the positions to the child?
                            PhxLiveView(it, pushEvent, composableNode, null)
                        } ?: SliderDefaults.Thumb(
                            interactionSource = interactionSource,
                            colors = colors,
                            enabled = enabled
                        )
                    },
                    endThumb = {
                        endThumb?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        } ?: SliderDefaults.Thumb(
                            interactionSource = endInteractionSource,
                            colors = colors,
                            enabled = enabled
                        )
                    },
                )

                LaunchedEffect(composableNode) {
                    changeValueEventName?.let { event ->
                        snapshotFlow {
                            rangeSliderState.activeRangeStart..rangeSliderState.activeRangeEnd
                        }
                            .onTypedChangeable()
                            .collect { value ->
                                pushEvent(
                                    ComposableBuilder.EVENT_TYPE_CHANGE,
                                    event,
                                    arrayOf(value.start, value.endInclusive),
                                    null
                                )
                            }
                    }
                }
            }
        }
    }

    @Composable
    private fun getSliderColors(sliderColors: ImmutableMap<String, String>?): SliderColors {
        val defaultValue = SliderDefaults.colors()
        return if (sliderColors == null) {
            defaultValue
        } else {
            SliderDefaults.colors(
                thumbColor = sliderColors[colorAttrThumbColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                activeTrackColor = sliderColors[colorAttrActiveTrackColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                activeTickColor = sliderColors[colorAttrActiveTickColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f),
                inactiveTrackColor = sliderColors[colorAttrInactiveTrackColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surfaceVariant,
                inactiveTickColor = sliderColors[colorAttrInactiveTickColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
                disabledThumbColor = sliderColors[colorAttrDisabledThumbColor]?.toColor()
                    ?: sliderColors[colorAttrThumbColor]?.toColor()
                        ?.copy(alpha = disabledContentAlpha)
                        ?.compositeOver(MaterialTheme.colorScheme.surface)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                disabledActiveTrackColor = sliderColors[colorAttrDisabledActiveTrackColor]?.toColor()
                    ?: sliderColors[colorAttrActiveTrackColor]?.toColor()
                        ?.copy(alpha = disabledContentAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                disabledActiveTickColor = sliderColors[colorAttrDisabledActiveTickColor]?.toColor()
                    ?: sliderColors[colorAttrActiveTickColor]?.toColor()
                        ?.copy(alpha = disabledContentAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                disabledInactiveTrackColor = sliderColors[colorAttrDisabledInactiveTrackColor]?.toColor()
                    ?: sliderColors[colorAttrInactiveTrackColor]?.toColor()
                        ?.copy(alpha = disabledContainerAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContainerAlpha),
                disabledInactiveTickColor = sliderColors[colorAttrDisabledInactiveTickColor]?.toColor()
                    ?: sliderColors[colorAttrInactiveTickColor]?.toColor()
                        ?.copy(alpha = disabledContentAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
            )
        }
    }

    internal class Builder : ChangeableDTOBuilder<Float>(0f) {
        var minValue = 0f
            private set
        var maxValue = 1f
            private set
        var steps = 0
            private set
        var colors: Map<String, String>? = null
            private set
        var range: ClosedFloatingPointRange<Float> = minValue.rangeTo(maxValue)
            private set

        /**
         * The min value for the range of values that this slider can take. The passed value will
         * be coerced to this range.
         * ```
         * <Slider min-value="0" />
         * ```
         * @param value a float value to set the min value accepted by the slider.
         */
        fun minValue(value: String) = apply {
            this.minValue = value.toFloatOrNull() ?: 0f
        }

        /**
         * The max value for the range of values that this slider can take. The passed value will
         * be coerced to this range.
         * ```
         * <Slider max-value="100" />
         * ```
         * @param value a float value to set the max value accepted by the slider.
         */
        fun maxValue(value: String) = apply {
            this.maxValue = value.toFloatOrNull() ?: 1f
        }

        /**
         * If greater than 0, specifies the amount of discrete allowable values, evenly distributed
         * across the whole value range. If 0, the slider will behave continuously and allow any
         * value from the range specified. Must not be negative.
         * ```
         * <Slider steps="5" />
         * ```
         * @param value an int value to define the number of steps the slider has.
         */
        fun steps(value: String) = apply {
            this.steps = value.toIntOrNull() ?: 0
        }

        /**
         * Set Slider colors.
         * ```
         * <Slider
         *   colors="{'thumbColor': '#FFFF0000', 'activeTrackColor': '#FF00FF00'}" />
         * ```
         * @param colors an JSON formatted string, containing the slider colors. The color keys
         * supported are: `thumbColor`, `activeTrackColor`, `activeTickColor, `inactiveTrackColor`,
         * `inactiveTickColor`, `disabledThumbColor`, `disabledActiveTrackColor`,
         * `disabledActiveTickColor`, `disabledInactiveTrackColor`, and `disabledInactiveTickColor`.
         */
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)
            }
        }

        fun handleValue(stringValue: String) = apply {
            if (stringValue.contains(',')) {
                this.range = try {
                    stringValue
                        .split(',')
                        .map { it.toFloat() }
                        .let { list ->
                            list[0]..list[1]
                        }
                } catch (e: Exception) {
                    minValue..maxValue
                }
            } else {
                this.value(stringValue.toFloat())
            }
        }

        fun build(): SliderDTO = SliderDTO(this)
    }
}

internal object SliderDtoFactory : ComposableViewFactory<SliderDTO, SliderDTO.Builder>() {

    /**
     * Creates a `SliderDTO` object based on the attributes of the input `Attributes` object.
     * SliderDTO co-relates to the Slider composable
     * @param attributes the `Attributes` object to create the `SliderDTO` object from
     * @return a `SliderDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): SliderDTO = SliderDTO.Builder().also {
        attributes.fold(
            it
        ) { builder, attribute ->
            if (builder.handleChangeableAttribute(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    attrColors -> builder.colors(attribute.value)
                    attrMaxValue -> builder.maxValue(attribute.value)
                    attrMinValue -> builder.minValue(attribute.value)
                    attrPhxValue -> builder.handleValue(attribute.value)
                    attrSteps -> builder.steps(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as SliderDTO.Builder
            }
        }
    }.build()
}