package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Templates.templateConfirmButton
import org.phoenixframework.liveview.data.constants.Templates.templateDismissButton
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design date picker dialog.
 * You can specify three children:
 *   - confirm button (required) using the `confirm` template;
 *   - dismiss button (optional) using the `dismiss` template;
 *   - and the content of the dialog (required, i.e. `DatePicker`) require no template.
 * An `DatePickerDialog` usually is wrapped by a condition in order to show it or not.
 * And a dismiss event should be used to hide it:
 * ```
 * def handle_event("showDialog", _params, socket) do
 *   {:noreply, assign(socket, :showDialog, true)}
 * end
 *
 * def handle_event("hideDialog", _params, socket) do
 *     {:noreply, assign(socket, :showDialog, false)}
 * end
 *
 * // render function...
 * <%= if @showDialog do %>
 *   <DatePickerDialog phx-click="hideDialog">...</DatePickerDialog>
 * <% end %>
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class DatePickerDialogDTO private constructor(builder: Builder) : DialogDTO(builder) {
    private val colors = builder.colors?.toImmutableMap()

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val dismissButton = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateDismissButton }
        }
        val confirmButton = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateConfirmButton }
        }
        val content = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.template == null }
        }
        DatePickerDialog(
            onDismissRequest = dismissEvent?.let {
                onClickFromString(pushEvent, it, value?.toString() ?: "")
            } ?: {
                // Do nothing
            },
            confirmButton = {
                confirmButton?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            modifier = modifier,
            dismissButton = dismissButton?.let {
                {
                    PhxLiveView(dismissButton, pushEvent, composableNode, null)
                }
            },
            shape = shape ?: DatePickerDefaults.shape,
            tonalElevation = tonalElevation ?: DatePickerDefaults.TonalElevation,
            colors = getDatePickerColors(colors),
            properties = dialogProperties,
            content = {
                content?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            }
        )
    }

    internal class Builder : DialogDTO.Builder() {
        init {
            // DatePickerDialog does not use the platform default width
            usePlatformDefaultWidth("false")
        }

        var colors: Map<String, String>? = null
            private set

        /**
         * Set DatePicker colors.
         * ```
         * <DatePicker
         *   colors="{'containerColor': '#FFFF0000', 'titleContentColor': '#FF00FF00'}"/>
         * ```
         * @param colors an JSON formatted string, containing the checkbox colors. The color keys
         * supported are: `containerColor`, `titleContentColor`, `headlineContentColor`,
         * `weekdayContentColor`, `subheadContentColor`, `yearContentColor`,
         * `currentYearContentColor`, `selectedYearContentColor`, `selectedYearContainerColor`,
         * `dayContentColor`, `disabledDayContentColor`, `selectedDayContentColor`,
         * `disabledSelectedDayContentColor`, `selectedDayContainerColor`,
         * `disabledSelectedDayContainerColor`, `todayContentColor`, `todayDateBorderColor`,
         * `dayInSelectionRangeContentColor`, and `dayInSelectionRangeContainerColor`.
         */
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)
            }
        }

        fun build(): DatePickerDialogDTO {
            buildDialogProperties()
            return DatePickerDialogDTO(this)
        }
    }
}

internal object DatePickerDialogDtoFactory :
    ComposableViewFactory<DatePickerDialogDTO, DatePickerDialogDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): DatePickerDialogDTO =
        attributes.fold(DatePickerDialogDTO.Builder()) { builder, attribute ->
            if (builder.handleDialogAttributes(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    attrColors -> builder.colors(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as DatePickerDialogDTO.Builder
            }
        }.build()
}