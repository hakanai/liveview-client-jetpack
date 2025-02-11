package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalArrangement
import org.phoenixframework.liveview.data.constants.Attrs.attrScroll
import org.phoenixframework.liveview.data.constants.Attrs.attrVerticalAlignment
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.optional
import org.phoenixframework.liveview.domain.extensions.paddingIfNotNull
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * A layout composable that places its children in a horizontal sequence.
 * ```
 * <Row width="fill" height="wrap" background="#FFCCCCCC">
 *   // Children
 * </Row>
 * ```
 */
internal class RowDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val horizontalArrangement: Arrangement.Horizontal = builder.horizontalArrangement
    private val verticalAlignment: Alignment.Vertical = builder.verticalAlignment
    private val hasVerticalScroll = builder.hasVerticalScrolling
    private val hasHorizontalScroll = builder.hasHorizontalScrolling

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        Row(
            modifier = modifier
                .paddingIfNotNull(paddingValues)
                .optional(
                    hasVerticalScroll, Modifier.verticalScroll(rememberScrollState())
                )
                .optional(
                    hasHorizontalScroll, Modifier.horizontalScroll(rememberScrollState())
                ),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment
        ) {
            composableNode?.children?.forEach {
                PhxLiveView(it, pushEvent, composableNode, null, this)
            }
        }
    }

    internal class Builder : ComposableBuilder() {
        var horizontalArrangement: Arrangement.Horizontal = Arrangement.Start
            private set
        var verticalAlignment: Alignment.Vertical = Alignment.Top
            private set

        /**
         * The horizontal arrangement of the Row's children
         *
         * ```
         * <Row horizontal-arrangement="spaceAround" >...</Row>
         * ```
         * @param horizontalArrangement the horizontal arrangement of the row's children. The
         * supported values are: `start`, `spacedEvenly`, `spaceAround`, `spaceBetween`, `end`,
         * and `center`. An int value is also supported, which will be used to determine the space.
         */
        fun horizontalArrangement(horizontalArrangement: String) = apply {
            if (horizontalArrangement.isNotEmpty()) {
                this.horizontalArrangement = horizontalArrangementFromString(horizontalArrangement)
            }
        }

        /**
         * The vertical alignment of the Row's children
         *
         * ```
         * <Row vertical-alignment="center" >...</Row>
         * ```
         * @param verticalAlignment the vertical alignment of the row's children. The
         * supported values are: `top`, `center`, and `bottom`.
         */
        fun verticalAlignment(verticalAlignment: String) = apply {
            if (verticalAlignment.isNotEmpty()) {
                this.verticalAlignment = verticalAlignmentFromString(verticalAlignment)
            }
        }

        fun build(): RowDTO = RowDTO(this)
    }
}

internal object RowDtoFactory : ComposableViewFactory<RowDTO, RowDTO.Builder>() {
    /**
     * Creates a `RowDTO` object based on the attributes of the input `Attributes` object.
     * RowDTO co-relates to the Row composable
     * @param attributes the `Attributes` object to create the `RowDTO` object from
     * @return a `RowDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): RowDTO =
        attributes.fold(RowDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                attrHorizontalArrangement -> builder.horizontalArrangement(attribute.value)
                attrScroll -> builder.scrolling(attribute.value)
                attrVerticalAlignment -> builder.verticalAlignment(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as RowDTO.Builder
        }.build()
}