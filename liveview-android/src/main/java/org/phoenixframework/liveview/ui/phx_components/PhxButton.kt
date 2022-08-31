package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element

@Composable fun PhxButton(
    element: Element,
    modifier: Modifier,
    phxActionListener: (PhxAction) -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = {
            phxActionListener.invoke(
                PhxAction.GenericAction(
                    element = element
                )
            )
        }
    ) {
        walkChildrenAndBuildComposables(
            children = element.children(),
            phxActionListener = phxActionListener
        )
    }
}