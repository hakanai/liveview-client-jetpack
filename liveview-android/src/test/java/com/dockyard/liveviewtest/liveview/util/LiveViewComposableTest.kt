package com.dockyard.liveviewtest.liveview.util

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.phoenixframework.liveview.BuildConfig.IS_RECORDING_SHOT_TEST
import org.phoenixframework.liveview.domain.LiveViewCoordinator
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

// Tips: You can use Robolectric while using AndroidJUnit4
@RunWith(AndroidJUnit4::class)
// Enable Robolectric Native Graphics (RNG)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
abstract class LiveViewComposableTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()
    val isRecording = IS_RECORDING_SHOT_TEST

    fun compareNativeComposableWithTemplate(
        nativeComposable: @Composable () -> Unit,
        template: String,
        testTag: String? = null,
        delayBeforeScreenshot: Long = 0,
        coordinator: LiveViewCoordinator = LiveViewCoordinator(
            httpBaseUrl = "",
            wsBaseUrl = "",
            onNavigate = { _, _ -> }
        ),
        onBeforeScreenShot: ((ComposeContentTestRule) -> Unit)? = null
    ) {
        composeRule.setContent {
            val state by coordinator.composableTree.collectAsState()
            if (isRecording) {
                nativeComposable()
            } else {
                val json = "{\"s\": [\"${template.templateToTest()}\"]}"
                coordinator.parseTemplate(json)
                if (state.children.isNotEmpty()) {
                    PhxLiveView(
                        composableNode = state.children.first(),
                        pushEvent = coordinator::pushEvent
                    )
                }
            }
        }

        // Do some action on the UI before capture the screenshot
        onBeforeScreenShot?.invoke(composeRule)

        if (delayBeforeScreenshot > 0) {
            Thread.sleep(delayBeforeScreenshot)
        }

        // https://github.com/pedrovgs/Shot/issues/305
        if (testTag != null)
            composeRule.onNodeWithTag(testTag, useUnmergedTree = true).captureRoboImage()
        else
            composeRule.onRoot().captureRoboImage()
    }

    private fun String.templateToTest() =
        this.trimIndent().trimMargin().trimEnd().replace("\"", "\\\"").lines().joinToString("")

    protected fun String.toJsonForTemplate() =
        this.trimIndent().trim().replace("\n", "")

}

