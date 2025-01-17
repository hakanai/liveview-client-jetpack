package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material.icons.sharp.PersonAdd
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

@OptIn(ExperimentalLayoutApi::class)
class ChipShotTest : LiveViewComposableTest() {

    @Test
    fun assistChipTest() {
        val colorsForTemplate = """
            {
                'containerColor': 'system-blue',
                'labelColor': 'system-yellow',
                'leadingIconContentColor': 'system-white',
                'trailingIconContentColor': 'system-cyan',
                'disabledContainerColor': 'system-light-gray',
                'disabledLabelColor': 'system-gray',
                'disabledLeadingIconContentColor': 'system-dark-gray',
                'disabledTrailingIconContentColor': 'system-black'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color.Blue,
                    labelColor = Color.Yellow,
                    leadingIconContentColor = Color.White,
                    trailingIconContentColor = Color.Cyan,
                    disabledContainerColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledLeadingIconContentColor = Color.DarkGray,
                    disabledTrailingIconContentColor = Color.Black,
                )
                FlowRow {
                    AssistChip(onClick = {}, label = { Text("Chip 1") })
                    AssistChip(onClick = {}, label = { Text("Chip 2") }, enabled = false)
                    AssistChip(
                        onClick = {}, label = { Text("Chip 3") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    AssistChip(
                        onClick = {}, label = { Text("Chip 4") }, enabled = false,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    AssistChip(
                        onClick = {}, label = { Text("Chip 5") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    AssistChip(
                        onClick = {}, label = { Text("Chip 6") }, enabled = false,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    AssistChip(
                        onClick = {}, label = { Text("Chip 7") }, shape = RectangleShape,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    AssistChip(
                        onClick = {},
                        label = { Text("Chip 8") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    AssistChip(
                        onClick = {},
                        label = { Text("Chip 9") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    AssistChip(
                        onClick = {},
                        label = { Text("Chip 10") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        enabled = false,
                        colors = colors
                    )
                }
            },
            template = """
                <FlowRow>
                  <AssistChip phx-click="">
                    <Text template="label">Chip 1</Text>
                  </AssistChip>     
                  <AssistChip phx-click="" enabled="false">
                    <Text template="label">Chip 2</Text>
                  </AssistChip>      
                  <AssistChip phx-click="">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 3</Text>
                  </AssistChip>    
                  <AssistChip phx-click="" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 4</Text>
                  </AssistChip>                                                         
                  <AssistChip phx-click="">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 5</Text>
                  </AssistChip>
                  <AssistChip phx-click="" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 6</Text>
                  </AssistChip>   
                  <AssistChip phx-click="" shape="rect">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 7</Text>
                  </AssistChip>    
                  <AssistChip phx-click="" border="{'width': '2', 'color': 'system-red'}">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 8</Text>
                  </AssistChip> 
                  <AssistChip phx-click="" colors="$colorsForTemplate">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 9</Text>
                  </AssistChip>   
                  <AssistChip phx-click="" colors="$colorsForTemplate" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 10</Text>
                  </AssistChip>                                                                                
                </FlowRow>            
                """
        )
    }

    @Test
    fun elevatedAssistChipTest() {
        val colorsForTemplate = """
            {
                'containerColor': 'system-blue',
                'labelColor': 'system-yellow',
                'leadingIconContentColor': 'system-white',
                'trailingIconContentColor': 'system-cyan',
                'disabledContainerColor': 'system-light-gray',
                'disabledLabelColor': 'system-gray',
                'disabledLeadingIconContentColor': 'system-dark-gray',
                'disabledTrailingIconContentColor': 'system-black'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color.Blue,
                    labelColor = Color.Yellow,
                    leadingIconContentColor = Color.White,
                    trailingIconContentColor = Color.Cyan,
                    disabledContainerColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledLeadingIconContentColor = Color.DarkGray,
                    disabledTrailingIconContentColor = Color.Black,
                )
                FlowRow {
                    ElevatedAssistChip(onClick = {}, label = { Text("Chip 1") })
                    ElevatedAssistChip(onClick = {}, label = { Text("Chip 2") }, enabled = false)
                    ElevatedAssistChip(
                        onClick = {}, label = { Text("Chip 3") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedAssistChip(
                        onClick = {}, label = { Text("Chip 4") }, enabled = false,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedAssistChip(
                        onClick = {}, label = { Text("Chip 5") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedAssistChip(
                        onClick = {}, label = { Text("Chip 6") }, enabled = false,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedAssistChip(
                        onClick = {}, label = { Text("Chip 7") }, shape = RectangleShape,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedAssistChip(
                        onClick = {},
                        label = { Text("Chip 8") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedAssistChip(
                        onClick = {},
                        label = { Text("Chip 9") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    ElevatedAssistChip(
                        onClick = {},
                        label = { Text("Chip 10") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        enabled = false,
                        colors = colors
                    )
                }
            },
            template = """
                <FlowRow>
                  <ElevatedAssistChip phx-click="">
                    <Text template="label">Chip 1</Text>
                  </ElevatedAssistChip>     
                  <ElevatedAssistChip phx-click="" enabled="false">
                    <Text template="label">Chip 2</Text>
                  </ElevatedAssistChip>      
                  <ElevatedAssistChip phx-click="">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 3</Text>
                  </ElevatedAssistChip>    
                  <ElevatedAssistChip phx-click="" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 4</Text>
                  </ElevatedAssistChip>                                                         
                  <ElevatedAssistChip phx-click="">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 5</Text>
                  </ElevatedAssistChip>
                  <ElevatedAssistChip phx-click="" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 6</Text>
                  </ElevatedAssistChip>   
                  <ElevatedAssistChip phx-click="" shape="rect">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 7</Text>
                  </ElevatedAssistChip>    
                  <ElevatedAssistChip phx-click="" border="{'width': '2', 'color': 'system-red'}">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 8</Text>
                  </ElevatedAssistChip> 
                  <ElevatedAssistChip phx-click="" colors="$colorsForTemplate">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 9</Text>
                  </ElevatedAssistChip>   
                  <ElevatedAssistChip phx-click="" colors="$colorsForTemplate" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 10</Text>
                  </ElevatedAssistChip>                                                                                
                </FlowRow>            
                """
        )
    }

    @Test
    fun suggestionChipTest() {
        val colorsForTemplate = """
            {
                'containerColor': 'system-blue',
                'labelColor': 'system-yellow',
                'iconContentColor': 'system-white',
                'disabledContainerColor': 'system-light-gray',
                'disabledLabelColor': 'system-gray',
                'disabledIconContentColor': 'system-dark-gray'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = Color.Blue,
                    labelColor = Color.Yellow,
                    iconContentColor = Color.White,
                    disabledContainerColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledIconContentColor = Color.DarkGray,
                )
                FlowRow {
                    SuggestionChip(onClick = {}, label = { Text("Chip 1") })
                    SuggestionChip(onClick = {}, label = { Text("Chip 2") }, enabled = false)
                    SuggestionChip(
                        onClick = {}, label = { Text("Chip 3") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    SuggestionChip(
                        onClick = {}, label = { Text("Chip 4") }, enabled = false,
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    SuggestionChip(
                        onClick = {}, label = { Text("Chip 5") }, shape = RectangleShape,
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    SuggestionChip(
                        onClick = {},
                        label = { Text("Chip 6") },
                        border = BorderStroke(2.dp, Color.Red),
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    SuggestionChip(
                        onClick = {},
                        label = { Text("Chip 7") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        colors = colors
                    )
                    SuggestionChip(
                        onClick = {},
                        label = { Text("Chip 8") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        enabled = false,
                        colors = colors
                    )
                }
            },
            template = """
                <FlowRow>
                  <SuggestionChip phx-click="">
                    <Text template="label">Chip 1</Text>
                  </SuggestionChip>     
                  <SuggestionChip phx-click="" enabled="false">
                    <Text template="label">Chip 2</Text>
                  </SuggestionChip>      
                  <SuggestionChip phx-click="">
                    <Icon image-vector="filled:Check" template="icon"/>
                    <Text template="label">Chip 3</Text>
                  </SuggestionChip>    
                  <SuggestionChip phx-click="" enabled="false">
                    <Icon image-vector="filled:Check" template="icon"/>
                    <Text template="label">Chip 4</Text>
                  </SuggestionChip>                                                          
                  <SuggestionChip phx-click="" shape="rect">
                    <Icon image-vector="filled:Check" template="icon"/>
                    <Text template="label">Chip 5</Text>
                  </SuggestionChip>    
                  <SuggestionChip phx-click="" border="{'width': '2', 'color': 'system-red'}">
                    <Icon image-vector="filled:Check" template="icon"/>
                    <Text template="label">Chip 6</Text>
                  </SuggestionChip> 
                  <SuggestionChip phx-click="" colors="$colorsForTemplate">
                    <Icon image-vector="filled:Check" template="icon"/>
                    <Text template="label">Chip 7</Text>
                  </SuggestionChip>   
                  <SuggestionChip phx-click="" colors="$colorsForTemplate" enabled="false">
                    <Icon image-vector="filled:Check" template="icon"/>
                    <Text template="label">Chip 8</Text>
                  </SuggestionChip>                                                                                
                </FlowRow>            
                """
        )
    }

    @Test
    fun elevatedSuggestionChipTest() {
        val colorsForTemplate = """
            {
                'containerColor': 'system-blue',
                'labelColor': 'system-yellow',
                'iconContentColor': 'system-white',
                'disabledContainerColor': 'system-light-gray',
                'disabledLabelColor': 'system-gray',
                'disabledIconContentColor': 'system-dark-gray'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = SuggestionChipDefaults.elevatedSuggestionChipColors(
                    containerColor = Color.Blue,
                    labelColor = Color.Yellow,
                    iconContentColor = Color.White,
                    disabledContainerColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledIconContentColor = Color.DarkGray,
                )
                FlowRow {
                    ElevatedSuggestionChip(onClick = {}, label = { Text("Chip 1") })
                    ElevatedSuggestionChip(
                        onClick = {},
                        label = { Text("Chip 2") },
                        enabled = false
                    )
                    ElevatedSuggestionChip(
                        onClick = {}, label = { Text("Chip 3") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedSuggestionChip(
                        onClick = {}, label = { Text("Chip 4") }, enabled = false,
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedSuggestionChip(
                        onClick = {}, label = { Text("Chip 5") }, shape = RectangleShape,
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedSuggestionChip(
                        onClick = {},
                        label = { Text("Chip 6") },
                        border = BorderStroke(2.dp, Color.Red),
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedSuggestionChip(
                        onClick = {},
                        label = { Text("Chip 7") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        colors = colors
                    )
                    ElevatedSuggestionChip(
                        onClick = {},
                        label = { Text("Chip 8") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        enabled = false,
                        colors = colors
                    )
                }
            },
            template = """
                <FlowRow>
                  <ElevatedSuggestionChip phx-click="">
                    <Text template="label">Chip 1</Text>
                  </ElevatedSuggestionChip>     
                  <ElevatedSuggestionChip phx-click="" enabled="false">
                    <Text template="label">Chip 2</Text>
                  </ElevatedSuggestionChip>      
                  <ElevatedSuggestionChip phx-click="">
                    <Icon image-vector="filled:Check" template="icon"/>
                    <Text template="label">Chip 3</Text>
                  </ElevatedSuggestionChip>    
                  <ElevatedSuggestionChip phx-click="" enabled="false">
                    <Icon image-vector="filled:Check" template="icon"/>
                    <Text template="label">Chip 4</Text>
                  </ElevatedSuggestionChip>                                                          
                  <ElevatedSuggestionChip phx-click="" shape="rect">
                    <Icon image-vector="filled:Check" template="icon"/>
                    <Text template="label">Chip 5</Text>
                  </ElevatedSuggestionChip>    
                  <ElevatedSuggestionChip phx-click="" border="{'width': '2', 'color': 'system-red'}">
                    <Icon image-vector="filled:Check" template="icon"/>
                    <Text template="label">Chip 6</Text>
                  </ElevatedSuggestionChip> 
                  <ElevatedSuggestionChip phx-click="" colors="$colorsForTemplate">
                    <Icon image-vector="filled:Check" template="icon"/>
                    <Text template="label">Chip 7</Text>
                  </ElevatedSuggestionChip>   
                  <ElevatedSuggestionChip phx-click="" colors="$colorsForTemplate" enabled="false">
                    <Icon image-vector="filled:Check" template="icon"/>
                    <Text template="label">Chip 8</Text>
                  </ElevatedSuggestionChip>                                                                                
                </FlowRow>            
                """
        )
    }

    @Test
    fun filterChipTest() {
        val colorsForTemplate = """
            {                
            'containerColor': 'system-blue',
            'labelColor': 'system-yellow',
            'iconColor': 'system-white',
            'disabledContainerColor': 'system-light-gray',
            'disabledLabelColor': 'system-gray',
            'disabledLeadingIconColor': 'system-dark-gray',
            'disabledTrailingIconColor': 'system-black',
            'selectedContainerColor': 'system-red',
            'disabledSelectedContainerColor': 'system-magenta',
            'selectedLabelColor': 'system-green',
            'selectedLeadingIconColor': 'system-yellow',
            'selectedTrailingIconColor': 'system-white'                
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color.Blue,
                    labelColor = Color.Yellow,
                    iconColor = Color.White,
                    disabledContainerColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledLeadingIconColor = Color.DarkGray,
                    disabledTrailingIconColor = Color.Black,
                    selectedContainerColor = Color.Red,
                    disabledSelectedContainerColor = Color.Magenta,
                    selectedLabelColor = Color.Green,
                    selectedLeadingIconColor = Color.Yellow,
                    selectedTrailingIconColor = Color.White
                )

                FlowRow {
                    FilterChip(onClick = {}, selected = false, label = { Text("Chip 1") })
                    FilterChip(onClick = {}, selected = true, label = { Text("Chip 2") })
                    FilterChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 3") },
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 4") },
                    )
                    FilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 5") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 6") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    FilterChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 7") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 8") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    FilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 9") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 10") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    FilterChip(
                        onClick = {},
                        selected = false,
                        shape = RectangleShape,
                        label = { Text("Chip 11") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        shape = RectangleShape,
                        label = { Text("Chip 12") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    FilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 13") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 14") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    FilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 15") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    FilterChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 16") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 17") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 18") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                }
            },
            template = """
                <FlowRow>
                  <FilterChip phx-click="" selected="false">
                    <Text template="label">Chip 1</Text>
                  </FilterChip>   
                  <FilterChip phx-click="" selected="true">
                    <Text template="label">Chip 2</Text>
                  </FilterChip>                    
                  <FilterChip phx-click="" selected="false" enabled="false">
                    <Text template="label">Chip 3</Text>
                  </FilterChip>     
                  <FilterChip phx-click="" selected="true" enabled="false">
                    <Text template="label">Chip 4</Text>
                  </FilterChip>                     
                  <FilterChip phx-click="" selected="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 5</Text>
                  </FilterChip> 
                  <FilterChip phx-click="" selected="true">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 6</Text>
                  </FilterChip>                         
                  <FilterChip phx-click="" selected="false" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 7</Text>
                  </FilterChip>    
                  <FilterChip phx-click="" selected="true" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 8</Text>
                  </FilterChip>                                                                        
                  <FilterChip phx-click="" selected="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 9</Text>
                  </FilterChip>
                  <FilterChip phx-click="" selected="true">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 10</Text>
                  </FilterChip>   
                  <FilterChip phx-click="" shape="rect" selected="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 11</Text>
                  </FilterChip>  
                  <FilterChip phx-click="" shape="rect" selected="true">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 12</Text>
                  </FilterChip>                     
                  <FilterChip phx-click="" border="{'width': '2', 'color': 'system-red'}" selected="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 13</Text>
                  </FilterChip> 
                  <FilterChip phx-click="" border="{'width': '2', 'color': 'system-red'}" selected="true">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 14</Text>
                  </FilterChip>   
                  <FilterChip phx-click="" colors="$colorsForTemplate" selected="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 15</Text>
                  </FilterChip>                                   
                  <FilterChip phx-click="" colors="$colorsForTemplate" selected="false" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 16</Text>
                  </FilterChip>   
                  <FilterChip phx-click="" colors="$colorsForTemplate" selected="true">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 17</Text>
                  </FilterChip>    
                  <FilterChip phx-click="" colors="$colorsForTemplate" selected="true" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 18</Text>
                  </FilterChip>                                                                                                
                </FlowRow>            
                """
        )
    }

    @Test
    fun elevatedFilterChipTest() {
        val colorsForTemplate = """
            {                
            'containerColor': 'system-blue',
            'labelColor': 'system-yellow',
            'iconColor': 'system-white',
            'disabledContainerColor': 'system-light-gray',
            'disabledLabelColor': 'system-gray',
            'disabledLeadingIconColor': 'system-dark-gray',
            'disabledTrailingIconColor': 'system-black',
            'selectedContainerColor': 'system-red',
            'disabledSelectedContainerColor': 'system-magenta',
            'selectedLabelColor': 'system-green',
            'selectedLeadingIconColor': 'system-yellow',
            'selectedTrailingIconColor': 'system-white'                
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = FilterChipDefaults.elevatedFilterChipColors(
                    containerColor = Color.Blue,
                    labelColor = Color.Yellow,
                    iconColor = Color.White,
                    disabledContainerColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledLeadingIconColor = Color.DarkGray,
                    disabledTrailingIconColor = Color.Black,
                    selectedContainerColor = Color.Red,
                    disabledSelectedContainerColor = Color.Magenta,
                    selectedLabelColor = Color.Green,
                    selectedLeadingIconColor = Color.Yellow,
                    selectedTrailingIconColor = Color.White
                )

                FlowRow {
                    ElevatedFilterChip(onClick = {}, selected = false, label = { Text("Chip 1") })
                    ElevatedFilterChip(onClick = {}, selected = true, label = { Text("Chip 2") })
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 3") },
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 4") },
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 5") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 6") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 7") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 8") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 9") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 10") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        shape = RectangleShape,
                        label = { Text("Chip 11") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        shape = RectangleShape,
                        label = { Text("Chip 12") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 13") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 14") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 15") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 16") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 17") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 18") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                }
            },
            template = """
                <FlowRow>
                  <ElevatedFilterChip phx-click="" selected="false">
                    <Text template="label">Chip 1</Text>
                  </ElevatedFilterChip>   
                  <ElevatedFilterChip phx-click="" selected="true">
                    <Text template="label">Chip 2</Text>
                  </ElevatedFilterChip>                    
                  <ElevatedFilterChip phx-click="" selected="false" enabled="false">
                    <Text template="label">Chip 3</Text>
                  </ElevatedFilterChip>     
                  <ElevatedFilterChip phx-click="" selected="true" enabled="false">
                    <Text template="label">Chip 4</Text>
                  </ElevatedFilterChip>                     
                  <ElevatedFilterChip phx-click="" selected="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 5</Text>
                  </ElevatedFilterChip> 
                  <ElevatedFilterChip phx-click="" selected="true">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 6</Text>
                  </ElevatedFilterChip>                         
                  <ElevatedFilterChip phx-click="" selected="false" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 7</Text>
                  </ElevatedFilterChip>    
                  <ElevatedFilterChip phx-click="" selected="true" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 8</Text>
                  </ElevatedFilterChip>                                                                        
                  <ElevatedFilterChip phx-click="" selected="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 9</Text>
                  </ElevatedFilterChip>
                  <ElevatedFilterChip phx-click="" selected="true">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 10</Text>
                  </ElevatedFilterChip>   
                  <ElevatedFilterChip phx-click="" shape="rect" selected="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 11</Text>
                  </ElevatedFilterChip>  
                  <ElevatedFilterChip phx-click="" shape="rect" selected="true">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 12</Text>
                  </ElevatedFilterChip>                     
                  <ElevatedFilterChip phx-click="" border="{'width': '2', 'color': 'system-red'}" selected="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 13</Text>
                  </ElevatedFilterChip> 
                  <ElevatedFilterChip phx-click="" border="{'width': '2', 'color': 'system-red'}" selected="true">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 14</Text>
                  </ElevatedFilterChip>   
                  <ElevatedFilterChip phx-click="" colors="$colorsForTemplate" selected="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 15</Text>
                  </ElevatedFilterChip>                                   
                  <ElevatedFilterChip phx-click="" colors="$colorsForTemplate" selected="false" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 16</Text>
                  </ElevatedFilterChip>   
                  <ElevatedFilterChip phx-click="" colors="$colorsForTemplate" selected="true">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 17</Text>
                  </ElevatedFilterChip>    
                  <ElevatedFilterChip phx-click="" colors="$colorsForTemplate" selected="true" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 18</Text>
                  </ElevatedFilterChip>                                                                                                
                </FlowRow>            
                """
        )
    }


    @Test
    fun inputChipTest() {
        val colorsForTemplate = """
            {                
            'containerColor': 'system-blue',
            'labelColor': 'system-yellow',
            'leadingIconColor': 'system-white',
            'trailingIconColor': 'system-red',
            'disabledContainerColor': 'system-light-gray',
            'disabledLabelColor': 'system-gray',
            'disabledLeadingIconColor': 'system-dark-gray',
            'disabledTrailingIconColor': 'system-black',
            'selectedContainerColor': 'system-red',
            'disabledSelectedContainerColor': 'system-magenta',
            'selectedLabelColor': 'system-green',
            'selectedLeadingIconColor': 'system-yellow',
            'selectedTrailingIconColor': 'system-white'                
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = InputChipDefaults.inputChipColors(
                    containerColor = Color.Blue,
                    labelColor = Color.Yellow,
                    leadingIconColor = Color.White,
                    trailingIconColor = Color.Red,
                    disabledContainerColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledLeadingIconColor = Color.DarkGray,
                    disabledTrailingIconColor = Color.Black,
                    selectedContainerColor = Color.Red,
                    disabledSelectedContainerColor = Color.Magenta,
                    selectedLabelColor = Color.Green,
                    selectedLeadingIconColor = Color.Yellow,
                    selectedTrailingIconColor = Color.White
                )

                FlowRow {
                    InputChip(onClick = {}, selected = false, label = { Text("Chip 1") })
                    InputChip(onClick = {}, selected = true, label = { Text("Chip 2") })
                    InputChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 3") },
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 4") },
                    )
                    InputChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 5") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 6") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    InputChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 7") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 8") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    InputChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 9") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 10") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    InputChip(
                        onClick = {},
                        selected = false,
                        shape = RectangleShape,
                        label = { Text("Chip 11") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        shape = RectangleShape,
                        label = { Text("Chip 12") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    InputChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 13") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 14") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    InputChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 15") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    InputChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 16") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 17") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 18") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    InputChip(
                        selected = true,
                        onClick = { },
                        label = { Text("Chip 19") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.Person,
                                contentDescription = ""
                            )
                        },
                    )
                    InputChip(
                        selected = false,
                        onClick = { },
                        label = { Text("Chip 20") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.PersonAdd,
                                contentDescription = ""
                            )
                        },
                    )
                    InputChip(
                        selected = true,
                        enabled = false,
                        onClick = { },
                        label = { Text("Chip 21") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.Person,
                                contentDescription = ""
                            )
                        },
                    )
                    InputChip(
                        selected = false,
                        enabled = false,
                        onClick = { },
                        label = { Text("Chip 22") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.PersonAdd,
                                contentDescription = ""
                            )
                        },
                    )
                    InputChip(
                        selected = true,
                        onClick = { },
                        label = { Text("Chip 23") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.Person,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                    InputChip(
                        selected = false,
                        onClick = { },
                        label = { Text("Chip 24") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.PersonAdd,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                    InputChip(
                        selected = true,
                        enabled = false,
                        onClick = { },
                        label = { Text("Chip 25") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.Person,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                    InputChip(
                        selected = false,
                        enabled = false,
                        onClick = { },
                        label = { Text("Chip 26") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.PersonAdd,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                }
            },
            template = """
                <FlowRow>
                  <InputChip phx-click="" selected="false">
                    <Text template="label">Chip 1</Text>
                  </InputChip>   
                  <InputChip phx-click="" selected="true">
                    <Text template="label">Chip 2</Text>
                  </InputChip>                    
                  <InputChip phx-click="" selected="false" enabled="false">
                    <Text template="label">Chip 3</Text>
                  </InputChip>     
                  <InputChip phx-click="" selected="true" enabled="false">
                    <Text template="label">Chip 4</Text>
                  </InputChip>                     
                  <InputChip phx-click="" selected="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 5</Text>
                  </InputChip> 
                  <InputChip phx-click="" selected="true">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 6</Text>
                  </InputChip>                         
                  <InputChip phx-click="" selected="false" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 7</Text>
                  </InputChip>    
                  <InputChip phx-click="" selected="true" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 8</Text>
                  </InputChip>                                                                        
                  <InputChip phx-click="" selected="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 9</Text>
                  </InputChip>
                  <InputChip phx-click="" selected="true">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 10</Text>
                  </InputChip>   
                  <InputChip phx-click="" shape="rect" selected="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 11</Text>
                  </InputChip>  
                  <InputChip phx-click="" shape="rect" selected="true">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 12</Text>
                  </InputChip>                     
                  <InputChip phx-click="" border="{'width': '2', 'color': 'system-red'}" selected="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 13</Text>
                  </InputChip> 
                  <InputChip phx-click="" border="{'width': '2', 'color': 'system-red'}" selected="true">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 14</Text>
                  </InputChip>   
                  <InputChip phx-click="" colors="$colorsForTemplate" selected="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 15</Text>
                  </InputChip>                                   
                  <InputChip phx-click="" colors="$colorsForTemplate" selected="false" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 16</Text>
                  </InputChip>   
                  <InputChip phx-click="" colors="$colorsForTemplate" selected="true">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 17</Text>
                  </InputChip>    
                  <InputChip phx-click="" colors="$colorsForTemplate" selected="true" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 18</Text>
                  </InputChip>     
                  <InputChip phx-click="" selected="true">
                    <Icon image-vector="sharp:Person" template="avatar"/>
                    <Text template="label">Chip 19</Text>
                  </InputChip>    
                  <InputChip phx-click="" selected="false">
                    <Icon image-vector="sharp:PersonAdd" template="avatar"/>
                    <Text template="label">Chip 20</Text>
                  </InputChip>    
                  <InputChip phx-click="" selected="true" enabled="false">
                    <Icon image-vector="sharp:Person" template="avatar"/>
                    <Text template="label">Chip 21</Text>
                  </InputChip>    
                  <InputChip phx-click="" selected="false" enabled="false">
                    <Icon image-vector="sharp:PersonAdd" template="avatar"/>
                    <Text template="label">Chip 22</Text>
                  </InputChip>    
                  <InputChip phx-click="" colors="$colorsForTemplate" selected="true" >
                    <Icon image-vector="sharp:Person" template="avatar"/>
                    <Text template="label">Chip 23</Text>
                  </InputChip>    
                  <InputChip phx-click="" colors="$colorsForTemplate" selected="false">
                    <Icon image-vector="sharp:PersonAdd" template="avatar"/>
                    <Text template="label">Chip 24</Text>
                  </InputChip>    
                  <InputChip phx-click="" colors="$colorsForTemplate" selected="true" enabled="false">
                    <Icon image-vector="sharp:Person" template="avatar"/>
                    <Text template="label">Chip 25</Text>
                  </InputChip>    
                  <InputChip phx-click="" colors="$colorsForTemplate" selected="false" enabled="false">
                    <Icon image-vector="sharp:PersonAdd" template="avatar"/>
                    <Text template="label">Chip 26</Text>
                  </InputChip>                                                                                                                                                             
                </FlowRow>            
                """
        )
    }
}