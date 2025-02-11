package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class SurfaceShotTest : LiveViewComposableTest() {
    @Test
    fun simplesSurfaceTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Surface {
                        Text(text = "Surface 1", modifier = Modifier.padding(16.dp))
                    }
                    Surface(color = Color.Blue) {
                        Text(text = "Surface 2", modifier = Modifier.padding(16.dp))
                    }
                    Surface(color = Color.Blue, contentColor = Color.Yellow) {
                        Text(text = "Surface 3", modifier = Modifier.padding(16.dp))
                    }
                    Surface(
                        color = Color.Blue,
                        contentColor = Color.Yellow,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Surface 4", modifier = Modifier.padding(16.dp))
                    }
                    Surface(
                        color = Color.Blue,
                        contentColor = Color.Yellow,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(2.dp, Color.Green)
                    ) {
                        Text(text = "Surface 5", modifier = Modifier.padding(16.dp))
                    }
                    Surface(
                        color = Color.Blue,
                        contentColor = Color.Yellow,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(2.dp, Color.Green),
                        tonalElevation = 4.dp
                    ) {
                        Text(text = "Surface 6", modifier = Modifier.padding(16.dp))
                    }
                    Surface(
                        color = Color.Blue,
                        contentColor = Color.Yellow,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(2.dp, Color.Green),
                        tonalElevation = 4.dp,
                        shadowElevation = 8.dp,
                    ) {
                        Text(text = "Surface 7", modifier = Modifier.padding(16.dp))
                    }
                }
            },
            template = """
                <Column>
                  <Surface>
                    <Text padding="16">Surface 1</Text>
                  </Surface>
                  <Surface color="system-blue">
                    <Text padding="16">Surface 2</Text>
                  </Surface>
                  <Surface color="system-blue" content-color="system-yellow">
                    <Text padding="16">Surface 3</Text>
                  </Surface>
                  <Surface color="system-blue" content-color="system-yellow" shape="12">
                    <Text padding="16">Surface 4</Text>
                  </Surface>    
                  <Surface color="system-blue" content-color="system-yellow" shape="12" 
                    border="{'width': '2', 'color': 'system-green'}">
                    <Text padding="16">Surface 5</Text>
                  </Surface>     
                  <Surface color="system-blue" content-color="system-yellow" shape="12" 
                    border="{'width': '2', 'color': 'system-green'}" tonal-elevation="4">
                    <Text padding="16">Surface 6</Text>
                  </Surface> 
                  <Surface color="system-blue" content-color="system-yellow" shape="12" 
                    border="{'width': '2', 'color': 'system-green'}" tonal-elevation="4"
                    shadow-elevation="8">
                    <Text padding="16">Surface 7</Text>
                  </Surface>                                                                                                
                </Column>
                """
        )
    }
}