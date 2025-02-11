package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.data.constants.Attrs.attrGesturesEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrIsOpen
import org.phoenixframework.liveview.data.constants.Attrs.attrOnClose
import org.phoenixframework.liveview.data.constants.Attrs.attrOnOpen
import org.phoenixframework.liveview.data.constants.Attrs.attrScrimColor
import org.phoenixframework.liveview.data.constants.Templates
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.EVENT_TYPE_CHANGE
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design navigation drawer.
 * It can has two children: one with the template `drawerContent` will display the content inside
 * the drawer; and the second one is the screen content.
 * ```
 * <ModalNavigationDrawer
 *   is-open={"#{@drawerIsOpen}"}
 *   on-close="closeDrawer"
 *   on-open="openDrawer">
 *    <ModalDrawerSheet template="drawerContent">...</ModalDrawerSheet>
 *    <Scaffold>...</Scaffold>
 *  </ModalNavigationDrawer>
 * ```
 * Notice that it's essential to declared the `is-open`, `on-close`, and `on-open` properties in
 * order to control if the drawer is open or not.
 * You can also use a `PermanentNavigationDrawer` or a `DismissibleNavigationDrawer`.
 */
internal class NavigationDrawerDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    private val gesturesEnabled = builder.gesturesEnabled
    private val scrimColor = builder.scrimColor
    private val isOpen = builder.isOpen
    private val onClose = builder.onClose
    private val onOpen = builder.onOpen

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val drawerContent = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == Templates.templateDrawerContent }
        }
        val content = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template != Templates.templateDrawerContent }
        }
        val tag = composableNode?.node?.tag
        if (tag == ComposableTypes.permanentNavigationDrawer) {
            PermanentNavigationDrawer(
                drawerContent = {
                    drawerContent?.let {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                },
                modifier = modifier,
                content = {
                    content?.let {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }
            )
        } else {
            val drawerState =
                rememberDrawerState(
                    initialValue = if (isOpen) DrawerValue.Open else DrawerValue.Closed,
                    confirmStateChange = { drawerValue ->
                        if (drawerValue == DrawerValue.Open) {
                            pushEvent(EVENT_TYPE_CHANGE, onOpen, "", null)
                        } else {
                            pushEvent(EVENT_TYPE_CHANGE, onClose, "", null)
                        }
                        true
                    }
                )
            when (composableNode?.node?.tag) {
                ComposableTypes.modalNavigationDrawer -> {
                    ModalNavigationDrawer(
                        drawerContent = {
                            drawerContent?.let {
                                PhxLiveView(it, pushEvent, composableNode, null)
                            }
                        },
                        modifier = modifier,
                        drawerState = drawerState,
                        gesturesEnabled = gesturesEnabled,
                        scrimColor = scrimColor ?: DrawerDefaults.scrimColor,
                        content = {
                            content?.let {
                                PhxLiveView(it, pushEvent, composableNode, null)
                            }
                        }
                    )
                }

                ComposableTypes.dismissibleNavigationDrawer ->
                    DismissibleNavigationDrawer(
                        drawerContent = {
                            drawerContent?.let {
                                PhxLiveView(it, pushEvent, composableNode, null)
                            }
                        },
                        modifier = modifier,
                        drawerState = drawerState,
                        gesturesEnabled = gesturesEnabled,
                        content = {
                            content?.let {
                                PhxLiveView(it, pushEvent, composableNode, null)
                            }
                        }
                    )
            }
            LaunchedEffect(composableNode) {
                if (isOpen && drawerState.isClosed) {
                    drawerState.open()
                } else if (!isOpen && drawerState.isOpen) {
                    drawerState.close()
                }
            }
        }
    }

    internal class Builder : ComposableBuilder() {
        var gesturesEnabled: Boolean = true
            private set
        var scrimColor: Color? = null
            private set
        var isOpen: Boolean = false
            private set
        var onClose: String = ""
            private set
        var onOpen: String = ""
            private set

        /**
         * Indicates if the drawer is opened or closed. This property is only used by
         * `ModalNavigationDrawer` and `DismissibleNavigationDrawer`
         * ```
         * <ModalNavigationDrawer is-open={"#{@drawerIsOpen}"} >
         * ```
         * @param isOpen true if the drawer is opened, false if it is closed.
         */
        fun isOpen(isOpen: String) = apply {
            this.isOpen = isOpen.toBoolean()
        }

        /**
         * Whether or not the drawer can be interacted by gestures. This property is only used by
         * `ModalNavigationDrawer` and `DismissibleNavigationDrawer`.
         * ```
         * <ModalNavigationDrawer gestures-enabled="false">
         * ```
         * @param gesturesEnabled true if the user can interact with the drawer using gestures,
         * false otherwise.
         */
        fun gesturesEnabled(gesturesEnabled: String) = apply {
            this.gesturesEnabled = gesturesEnabled.toBoolean()
        }

        /**
         * Color of the scrim that obscures content when the drawer is open. This is only used by
         * the `ModalNavigationDrawer`.
         * ```
         * <ModalNavigationDrawer scrim-color="#FF000000">
         * ```
         * @scrimColor the scrim color in AARRGGBB format.
         */
        fun scrimColor(scrimColor: String) = apply {
            this.scrimColor = scrimColor.toColor()
        }

        /**
         * Function to be called on the server to be called when the drawer is closed. This property
         * is only used by `ModalNavigationDrawer` and `DismissibleNavigationDrawer`.
         * ```
         * <ModalNavigationDrawer on-close="closeDrawer" >
         * ```
         * @param event name of the function to be called on the server when the drawer is closed.
         */
        fun onClose(event: String) = apply {
            this.onClose = event
        }

        /**
         * Function to be called on the server to be called when the drawer is opened. This property
         * is only used by `ModalNavigationDrawer` and `DismissibleNavigationDrawer`.
         * ```
         * <ModalNavigationDrawer on-open="openDrawer" >
         * ```
         * @param event name of the function to be called on the server when the drawer is opened.
         */
        fun onOpen(event: String) = apply {
            this.onOpen = event
        }

        fun build() = NavigationDrawerDTO(this)
    }
}

internal object NavigationDrawerDtoFactory :
    ComposableViewFactory<NavigationDrawerDTO, NavigationDrawerDTO.Builder>() {
    /**
     * Creates a `NavigationDrawerDTO` object based on the attributes of the input `Attributes`
     * object. NavigationDrawerDTO co-relates to the ModalNavigationDrawer,
     * DismissibleNavigationDrawer, and PermanentNavigationDrawer composable.
     * @param attributes the `Attributes` object to create the `NavigationDrawerDTO` object from.
     * @return a `NavigationDrawerDTO` object based on the attributes of the input `Attributes`
     * object.
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): NavigationDrawerDTO =
        attributes.fold(NavigationDrawerDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                attrGesturesEnabled -> builder.gesturesEnabled(attribute.value)
                attrIsOpen -> builder.isOpen(attribute.value)
                attrOnClose -> builder.onClose(attribute.value)
                attrOnOpen -> builder.onOpen(attribute.value)
                attrScrimColor -> builder.scrimColor(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as NavigationDrawerDTO.Builder
        }.build()
}