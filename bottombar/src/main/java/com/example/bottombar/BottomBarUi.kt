package com.example.bottombar

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.debugInspectorInfo

val arrayList = getItemItemIfo()

fun getItemItemIfo(): ArrayList<ItemInfo> {
    val arrayList = arrayListOf<ItemInfo>()
    arrayList.add(ItemInfo("Games", Icons.Filled.Favorite))
    arrayList.add(ItemInfo("Apps", Icons.Filled.Apps))
    arrayList.add(ItemInfo("Movies", Icons.Filled.Movie))
    arrayList.add(ItemInfo("Books", Icons.Filled.Book))
    return arrayList
}

// We represent a Composable function by annotating it with the @Composable annotation. Composable
// functions can only be called from within the scope of other composable functions. We should
// think of composable functions to be similar to lego blocks - each composable function is in turn
// built up of smaller composable functions.



@Composable
fun BottomNavigationAlwaysShowLabelComponent(itemClick: List<Pair<String, ImageVector>>, function: (String) -> Unit) {
    // Reacting to state changes is the core behavior of Compose. You will notice a couple new
    // keywords that are compose related - remember & mutableStateOf.remember{} is a helper
    // composable that calculates the value passed to it only during the first composition. It then
    // returns the same value for every subsequent composition. Next, you can think of
    // mutableStateOf as an observable value where updates to this variable will redraw all
    // the composable functions that access it. We don't need to explicitly subscribe at all. Any
    // composable that reads its value will be recomposed any time the value
    // changes. This ensures that only the composables that depend on this will be redraw while the
    // rest remain unchanged. This ensures efficiency and is a performance optimization. It
    // is inspired from existing frameworks like React.
    var selectedIndex by remember { mutableStateOf(0) }
    // BottomNavigation is a component placed at the bottom of the screen that represents primary
    // destinations in your application.
    BottomNavigation(modifier = Modifier.padding(0.dp)) {
        itemClick.forEachIndexed { index, label ->
            // A composable typically used as part of BottomNavigation. Since BottomNavigation
            // is usually used to represent primary destinations in your application,
            // BottomNavigationItem represents a singular primary destination in your application.
            BottomNavigationItem(
                icon = {
                    // Simple composable that allows you to draw an icon on the screen. It
                    // accepts a vector asset as the icon.
                    Icon(imageVector = label.second, contentDescription = "Favorite")
                },
                label = {
                    // Text is a predefined composable that does exactly what you'd expect it to -
                    // display text on the screen. It allows you to customize its appearance using the
                    // style property.
                    Text(text = label.first)
                },
                // Update the selected index when the BottomNavigationItem is clicked
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    function(label.first)
                }
            )
        }
    }
}

@Composable
fun BottomNavigationOnlySelectedLabelComponent() {
    // Reacting to state changes is the core behavior of Compose. You will notice a couple new
    // keywords that are compose related - remember & mutableStateOf.remember{} is a helper
    // composable that calculates the value passed to it only during the first composition. It then
    // returns the same value for every subsequent composition. Next, you can think of
    // mutableStateOf as an observable value where updates to this variable will redraw all
    // the composable functions that access it. We don't need to explicitly subscribe at all. Any
    // composable that reads its value will be recomposed any time the value
    // changes. This ensures that only the composables that depend on this will be redraw while the
    // rest remain unchanged. This ensures efficiency and is a performance optimization. It
    // is inspired from existing frameworks like React.
    var selectedIndex by remember { mutableStateOf(0) }
    // BottomNavigation is a component placed at the bottom of the screen that represents primary
    // destinations in your application.
    BottomNavigation(modifier = Modifier.padding(16.dp)) {
        arrayList.forEachIndexed { index, label ->
            // A composable typically used as part of BottomNavigation. Since BottomNavigation
            // is usually used to represent primary destinations in your application,
            // BottomNavigationItem represents a singular primary destination in your application.
            BottomNavigationItem(
                icon = {
                    // Simple composable that allows you to draw an icon on the screen. It
                    // accepts a vector asset as the icon.
                    Icon(imageVector = label.imageVector, contentDescription = "Favorite")
                },
                label = {
                    // Text is a predefined composable that does exactly what you'd expect it to -
                    // display text on the screen. It allows you to customize its appearance using the
                    // style property.
                    Text(text = label.name)
                },
                selected = selectedIndex == index,
                // Update the selected index when the BottomNavigationItem is clicked
                onClick = { selectedIndex = index },
                // Setting this to false causes the label to be show only for the navigation item
                // that is currently selected, like in the BottomNavigationAlwaysShowLabelComponent
                // component.
                alwaysShowLabel = false
            )
        }
    }
}

// Android Studio lets you preview your composable functions within the IDE itself, instead of
// needing to download the app to an Android device or emulator. This is a fantastic feature as you
// can preview all your custom components(read composable functions) from the comforts of the IDE.
// The main restriction is, the composable function must not take any parameters. If your composable
// function requires a parameter, you can simply wrap your component inside another composable
// function that doesn't take any parameters and call your composable function with the appropriate
// params. Also, don't forget to annotate it with @Preview & @Composable annotations.
@Preview
@Composable
fun BottomNavigationAlwaysShowLabelComponentPreview() {
    BottomNavigationAlwaysShowLabelComponent(listOf()) {

    }
}

@Preview
@Composable
fun BottomNavigationOnlySelectedLabelComponentPreview() {
    BottomNavigationOnlySelectedLabelComponent()
}

@Composable
fun ScrollableTabBarWithTextIconIndicationDivider(
    tabData: List<Pair<String, ImageVector>>,
    onclick: (String) -> Unit
) {
    Row {
        var tabIndex by remember { mutableStateOf(0) }

        ScrollableTabRow(selectedTabIndex = tabIndex,
            edgePadding = 5.dp,
            divider = {
                TabRowDefaults.Divider(
                    thickness = 0.dp,
                    color = MaterialTheme.colors.onBackground
                )
            }, indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.indicatorSetUp(tabPositions[tabIndex]),
                    height = 0.dp,
                    color = Color.Green
                )
            }) {
            tabData.forEachIndexed { index, pair ->
                Tab(selected = tabIndex == index, onClick = {
                    onclick(pair.first)
                    tabIndex = index
                }, text = {
                    Text(text = pair.first)
                }, icon = {
                    Icon(imageVector = pair.second, contentDescription = null)
                })
            }
        }
    }
}

private fun Modifier.indicatorSetUp(currentTabPosition: TabPosition): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicator"
        value = currentTabPosition
    }
) {
    val indicatorWidth = 32.dp
    val currentTabWidth = currentTabPosition.width
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left + currentTabWidth / 2 - indicatorWidth / 2,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(indicatorWidth)
}
