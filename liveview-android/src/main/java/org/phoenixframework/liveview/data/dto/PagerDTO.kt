package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import org.phoenixframework.liveview.data.constants.Attrs.attrBeyondBoundsPageCount
import org.phoenixframework.liveview.data.constants.Attrs.attrContentPadding
import org.phoenixframework.liveview.data.constants.Attrs.attrCurrentPage
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialPageOffsetFraction
import org.phoenixframework.liveview.data.constants.Attrs.attrPageCount
import org.phoenixframework.liveview.data.constants.Attrs.attrPageSize
import org.phoenixframework.liveview.data.constants.Attrs.attrPageSpacing
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.data.constants.Attrs.attrReverseLayout
import org.phoenixframework.liveview.data.constants.Attrs.attrUserScrollEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrVerticalAlignment
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.EVENT_TYPE_CHANGE
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Pager that scrolls horizontally or vertically. Pages are lazily placed in accordance to the
 * available viewport size. By definition, pages in a Pager have the same size, defined by pageSize.
 * You can use beyondBoundsPageCount to place more pages before and after the visible pages.
 * ```
 * <Column size="fill">
 *   <TabRow selected-tab-index={"#{@selectedTab}"}>
 *     <Tab selected={"#{@selectedTab == "0"}"} phx-click="selectTab" phx-value="0">
 *       <Text template="text">Tab 0</Text>
 *     </Tab>
 *     <Tab selected={"#{@selectedTab == "1"}"} phx-click="selectTab" phx-value="1">
 *       <Text template="text">Tab 1</Text>
 *     </Tab>
 *     <Tab selected={"#{@selectedTab == "2"}"} phx-click="selectTab" phx-value="2">
 *       <Text template="text">Tab 2</Text>
 *     </Tab>
 *   </TabRow>
 *   <HorizontalPager current-page={"#{@selectedTab}"} page-count="3" phx-change="selectTab">
 *     <Box content-alignment="center" background="system-red" size="fill">
 *       <Text font-size="24">Red</Text>
 *     </Box>
 *     <Box content-alignment="center" background="system-green" size="fill">
 *       <Text font-size="24">Green</Text>
 *     </Box>
 *     <Box content-alignment="center" background="system-blue" size="fill">
 *       <Text font-size="24">Blue</Text>
 *     </Box>
 *   </HorizontalPager>
 * </Column>
 * ```
 */
@OptIn(ExperimentalFoundationApi::class, FlowPreview::class)
internal class PagerDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val currentPage = builder.currentPage
    private val initialPageOffsetFraction = builder.initialPageOffsetFraction
    private val pageCount = builder.pageCount
    private val contentPadding = builder.contentPadding
    private val onChanged = builder.onChanged
    private val pageSize = builder.pageSize
    private val beyondBoundsPageCount = builder.beyondBoundsPageCount
    private val pageSpacing = builder.pageSpacing
    private val verticalAlignment = builder.verticalAlignment
    private val horizontalAlignment = builder.horizontalAlignment
    private val userScrollEnabled = builder.userScrollEnabled
    private val reverseLayout = builder.reverseLayout

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val childrenNodes = composableNode?.children ?: emptyArray()
        val state = rememberPagerState(
            initialPage = currentPage,
            initialPageOffsetFraction = initialPageOffsetFraction ?: 0f,
            pageCount = { pageCount },
        )
        when (composableNode?.node?.tag) {
            ComposableTypes.horizontalPager -> {
                HorizontalPager(
                    state = state,
                    modifier = modifier,
                    contentPadding = contentPadding ?: PaddingValues(0.dp),
                    pageSize = pageSize ?: PageSize.Fill,
                    beyondBoundsPageCount = beyondBoundsPageCount
                        ?: PagerDefaults.BeyondBoundsPageCount,
                    pageSpacing = pageSpacing,
                    verticalAlignment = verticalAlignment ?: Alignment.CenterVertically,
                    // TODO flingBehavior: SnapFlingBehavior,
                    userScrollEnabled = userScrollEnabled,
                    reverseLayout = reverseLayout,
                    key = { index ->
                        childrenNodes[index].id
                    },
                    // TODO pageNestedScrollConnection: NestedScrollConnection,
                    pageContent = { index ->
                        PhxLiveView(childrenNodes[index], pushEvent, composableNode, null, this)
                    }
                )
            }

            ComposableTypes.verticalPager -> {
                VerticalPager(
                    state = state,
                    modifier = modifier,
                    contentPadding = contentPadding ?: PaddingValues(0.dp),
                    pageSize = pageSize ?: PageSize.Fill,
                    beyondBoundsPageCount = beyondBoundsPageCount
                        ?: PagerDefaults.BeyondBoundsPageCount,
                    pageSpacing = pageSpacing,
                    horizontalAlignment = horizontalAlignment ?: Alignment.CenterHorizontally,
                    // TODO flingBehavior: SnapFlingBehavior,
                    userScrollEnabled = userScrollEnabled,
                    reverseLayout = reverseLayout,
                    key = { index ->
                        childrenNodes[index].id
                    },
                    // TODO pageNestedScrollConnection: NestedScrollConnection,
                    pageContent = { index ->
                        PhxLiveView(childrenNodes[index], pushEvent, composableNode, null, this)
                    }
                )
            }
        }
        LaunchedEffect(state) {
            snapshotFlow {
                state.currentPage
            }
                .distinctUntilChanged()
                // debouncing to avoid send intermediate values between the scrolling animation
                .debounce(300)
                .collect { page ->
                    onChanged?.let { event ->
                        pushEvent.invoke(EVENT_TYPE_CHANGE, event, page, null)
                    }
                }
        }
        LaunchedEffect(currentPage) {
            if (currentPage != state.currentPage) {
                state.animateScrollToPage(currentPage)
            }
        }
    }

    internal class Builder : ComposableBuilder() {
        var beyondBoundsPageCount: Int? = null
            private set
        var contentPadding: PaddingValues? = null
            private set
        var currentPage: Int = 0
            private set
        var horizontalAlignment: Alignment.Horizontal? = null
            private set
        var initialPageOffsetFraction: Float? = null
            private set
        var onChanged: String? = null
            private set
        var pageCount: Int = 0
            private set
        var pageSize: PageSize? = null
            private set
        var pageSpacing: Dp = 0.dp
            private set
        var reverseLayout: Boolean = false
            private set
        var userScrollEnabled: Boolean = true
            private set
        var verticalAlignment: Alignment.Vertical? = null
            private set

        /**
         * Pages to compose and layout before and after the list of visible pages.
         * Note: Be aware that using a large value for beyondBoundsPageCount will cause a lot of
         * pages to be composed, measured and placed which will defeat the purpose of using lazy
         * loading. This should be used as an optimization to pre-load a couple of pages before and
         * after the visible ones. This does not include the pages automatically composed and laid
         * out by the pre-fetcher in the direction of the scroll during scroll events.
         * ```
         * <HorizontalPager beyond-bounds-page-count="2" >...</HorizontalPager>
         * ```
         * @param count int value representing the number of pages to compose and layout before and
         * after the list of visible pages.
         */
        fun beyondBoundsPageCount(count: String) = apply {
            this.beyondBoundsPageCount = count.toIntOrNull()
        }

        /**
         * A padding around the whole content. This will add padding for the content after it has
         * been clipped, which is not possible via modifier param. You can use it to add a padding
         * before the first page or after the last one. Use pageSpacing to add spacing between the
         * pages.
         * ```
         * <HorizontalPager content-padding="8" >...</HorizontalPager>
         * ```
         * @param padding int value representing a padding around the whole content.
         */
        fun contentPadding(padding: String) = apply {
            padding.toIntOrNull()?.let {
                this.contentPadding = PaddingValues(it.dp)
            }
        }

        /**
         * The current selected page index. Notice this value must be between 0 and pageCount
         * (exclusive). This value is also used as initial selected page.
         * ```
         * <HorizontalPager current-page="0" >...</HorizontalPager>
         * ```
         * @param currentPage int value representing the current selected page index.
         */
        fun currentPage(currentPage: String) = apply {
            this.currentPage = currentPage.toIntOrNull() ?: 0
        }

        /**
         * How pages are aligned horizontally in this in a VerticalPager.
         * ```
         * <VerticalPager horizontal-alignment="center" >...</VerticalPager>
         * ```
         * @param alignment supported values are 'start', 'center', and 'end'.
         */
        fun horizontalAlignment(alignment: String) = apply {
            this.horizontalAlignment = horizontalAlignmentFromString(alignment)
        }

        /**
         * The offset of the initial page as a fraction of the page size. This should vary between
         * -0.5 and 0.5 and indicates how to offset the initial page from the snapped position.
         * @param offset float value indicating the offset of the initial page as a fraction of the
         * page size.
         */
        fun initialPageOffsetFraction(offset: String) = apply {
            this.initialPageOffsetFraction = offset.toFloatOrNull()
        }

        /**
         * Event called in the server when the current page changes. This event receives the page
         * index as parameter.
         * ```
         * <HorizontalPager phx-change="selectTab" >...</HorizontalPager>
         * ```
         * @param onChanged event to be triggered on the server when the selected tab changes.
         */
        fun onChanged(onChanged: String) = apply {
            this.onChanged = onChanged
        }

        /**
         * The amount of pages this Pager will have.
         * ```
         * <HorizontalPager page-count="3" >...</HorizontalPager>
         * ```
         * @param count int value representing the amount of pages this Pager will have.
         */
        fun pageCount(count: String) = apply {
            this.pageCount = count.toIntOrNull() ?: 0
        }

        /**
         * Use this to change how the pages will look like inside this pager.
         * ```
         * <HorizontalPager page-size="fill" >...</HorizontalPager>
         * ```
         * @param size the supported values are 'fill' (default) or an int value to set the page
         * size.
         */
        fun pageSize(size: String) = apply {
            this.pageSize = when (size) {
                "fill" -> PageSize.Fill
                else -> size.toIntOrNull()?.let { PageSize.Fixed(it.dp) }
            }
        }

        /**
         * The amount of space to be used to separate the pages in this Pager.
         * ```
         * <HorizontalPager page-spacing="16" >...</HorizontalPager>
         * ```
         * @param spacing int value representing the space to separate the pages
         */
        fun pageSpacing(spacing: String) = apply {
            spacing.toIntOrNull()?.let { this.pageSpacing = it.dp }
        }

        /**
         * Reverse the direction of scrolling and layout.
         * ```
         * <HorizontalPager reverse-layout="true" >...</HorizontalPager>
         * ```
         * @param reverse true if the scrolling direction and layout must be reversed, false
         * otherwise
         */
        fun reverseLayout(reverse: String) = apply {
            this.reverseLayout = reverse.toBoolean()
        }

        /**
         * Whether the scrolling via the user gestures or accessibility actions is allowed.
         * ```
         * <HorizontalPager user-scroll-enabled="false" >...</HorizontalPager>
         * ```
         * @param enabled true if the user gestures are enabled, false otherwise.
         */
        fun userScrollEnabled(enabled: String) = apply {
            this.userScrollEnabled = enabled.toBoolean()
        }

        /**
         * How pages are aligned vertically in this in a HorizontalPager.
         * ```
         * <HorizontalPager vertical-alignment="center" >...</HorizontalPager>
         * ```
         * @param alignment supported values are 'start', 'center', and 'end'.
         */
        fun verticalAlignment(alignment: String) = apply {
            this.verticalAlignment = verticalAlignmentFromString(alignment)
        }

        fun build() = PagerDTO(this)
    }
}

internal object PagerDtoFactory : ComposableViewFactory<PagerDTO, PagerDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): PagerDTO = PagerDTO.Builder().also {
        attributes.fold(
            it
        ) { builder, attribute ->
            when (attribute.name) {
                attrBeyondBoundsPageCount -> builder.beyondBoundsPageCount(attribute.value)
                attrContentPadding -> builder.contentPadding(attribute.value)
                attrCurrentPage -> builder.currentPage(attribute.value)
                attrHorizontalAlignment -> builder.horizontalAlignment(attribute.value)
                attrInitialPageOffsetFraction -> builder.initialPageOffsetFraction(attribute.value)
                attrPageCount -> builder.pageCount(attribute.value)
                attrPageSize -> builder.pageSize(attribute.value)
                attrPageSpacing -> builder.pageSpacing(attribute.value)
                attrPhxChange -> builder.onChanged(attribute.value)
                attrReverseLayout -> builder.reverseLayout(attribute.value)
                attrUserScrollEnabled -> builder.userScrollEnabled(attribute.value)
                attrVerticalAlignment -> builder.verticalAlignment(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as PagerDTO.Builder
        }
    }.build()
}