package com.rk.sample.counter


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.dp
import com.example.bottombar.BottomNavigationAlwaysShowLabelComponent
import com.example.bottombar.ScrollableTabBarWithTextIconIndicationDivider
import com.rk.clickeffect.bounceClick
import com.rk.searchbar.ExpandableSearchView
import com.rk.searchbar.TopBar
import com.rk.snackbar.*
import com.rk.timepicker.showTimerPicker
import kotlin.reflect.KFunction1


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun MainScreen(
    state: MainContract.State,
    dispatch: KFunction1<MainContract.Intent, Unit>,
    activity: AppCompatActivity,
) = Scaffold(
    topBar = {
//        ExpandableSearchView(
//            searchDisplay = "",
//            onSearchDisplayChanged = {
//                Toast.makeText(activity,it,Toast.LENGTH_SHORT).show()
//            },
//            expandedInitially = false,
//            onSearchDisplayClosed = {}
//        )
        TopBar()
    },
    content = {
        ListOfButtons(
            state = state,
            activity = activity,
            onClickReactions = {
            },
            onDialog = {
                dispatch(
                    MainContract.Intent.ShowDialogIntent(it)
                )
            },
            onDatePicker = {
                dispatch(
                    MainContract.Intent.ShowDatePickerIntent(it)
                )
            },
            onTimePicker = {
                dispatch(
                    MainContract.Intent.ShowTimePickerIntent(it)
                )
            },
            onBottomSheet = {
                dispatch(
                    MainContract.Intent.ShowBottomSheet(it)
                )
            },
            onClickMessage = {
                dispatch(
                    MainContract.Intent.ShowReactionsIntent(
                        x = it.x.toInt(),
                        y = it.y.toInt(),
                    )
                )
            },
        )
    },
    bottomBar = {
        val tabData = listOf(
            "HOME" to Icons.Filled.Home,
            "FAVORITE" to Icons.Filled.Favorite,
            "DOWNLOAD" to Icons.Filled.GetApp,
            "PICTURES" to Icons.Filled.AddAPhoto,
            "MUSIC" to Icons.Filled.Headset,
            "USER" to Icons.Filled.Person,
            "SETTING" to Icons.Filled.Settings,
        )

        val bottomData = listOf(
            "HOME" to Icons.Filled.Home,
            "FAVORITE" to Icons.Filled.Favorite,
            "DOWNLOAD" to Icons.Filled.GetApp,
            "PICTURES" to Icons.Filled.AddAPhoto,
            "MUSIC" to Icons.Filled.Headset,
        )

        ScrollableTabBarWithTextIconIndicationDivider(tabData) {
            Toast.makeText(activity,it,Toast.LENGTH_SHORT).show()
        }
//        BottomNavigationAlwaysShowLabelComponent (bottomData) {
//            Toast.makeText(activity,it,Toast.LENGTH_SHORT).show()
//        }
    }
)




@SuppressLint("UnrememberedMutableState")
@Composable
private fun ListOfButtons(
    onClickReactions: () -> Unit,
    onClickMessage: (Offset) -> Unit,
    state: MainContract.State,
    onDialog: (Boolean) -> Unit,
    onDatePicker: (Boolean) -> Unit,
    onTimePicker: (Boolean) -> Unit,
    activity: AppCompatActivity,
    onBottomSheet: (Boolean) -> Unit,
) {
    var positionItem: Offset by remember { mutableStateOf(Offset.Zero) }
    var message: InfoBarMessage? by remember { mutableStateOf(null) }
    Box(
        Modifier.fillMaxSize()
    ) {
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .wrapContentHeight()
                .align(Alignment.TopCenter)
                .verticalScroll(scrollState)
                .padding(all = 10.dp)

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f, fill = true)
                        .onGloballyPositioned { coordinates ->
                            positionItem = coordinates.positionInRoot()
                        },
                    onClick = { onClickMessage(positionItem) },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        backgroundColor = Color.Blue
                    )
                ) {
                    Text(
                        text = "Emoji",
                        color = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Button(
                    modifier = Modifier
                        .bounceClick()
                        .weight(1f, fill = true)
                        .onGloballyPositioned { coordinates ->
                            positionItem = coordinates.positionInRoot()
                        },
                    onClick = {},
                    shape = RoundedCornerShape(12.dp), contentPadding = PaddingValues(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.Red
                    )
                ) {
                    Text(
                        text = "Click Effect",
                        color = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f, fill = true)
                        .onGloballyPositioned { coordinates ->
                            positionItem = coordinates.positionInRoot()
                        },
                    onClick = {
                        message = InfoBarMessage(
                            text = "Example message",
                            action = "Click",
                            onAction = {
                                message = null
                            },
                        )
                    },
                    colors = ButtonDefaults.buttonColors(contentColor = Color.Red)
                ) {
                    Text(
                        text = "Info Bar",
                        color = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f, fill = true),
                    onClick = { onDialog(true) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.Blue,
                    )
                ) {
                    Text(
                        text = "Dialog",
                        color = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f, fill = true),
                    onClick = {

//                        showDatePicker(updatedDate = {
//                            Toast.makeText(activity, it.toString(), Toast.LENGTH_LONG).show()
//
//                        }, activity = activity)
                        onDatePicker(true)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.Blue,
                    )
                ) {
                    Text(
                        text = "DatePicket",
                        color = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f, fill = true),
                    onClick = {

                        showTimerPicker(onClick = {
                            Toast.makeText(activity, it.toString(), Toast.LENGTH_LONG).show()

                        }, activity = activity)
//                        onTimePicker(true)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.Blue,
                    )
                ) {
                    Text(
                        text = "TimePicker",
                        color = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f, fill = true),
                    onClick = {
                        onTimePicker(true)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.Blue,
                    )
                ) {
                    Text(
                        text = "Image Picker",
                        color = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f, fill = true),
                    onClick = {
                        onBottomSheet(true)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.Blue,
                    )
                ) {
                    Text(
                        text = "BottomSheet",
                        color = Color.White,
                    )
                }
            }

        }

        InfoBar(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            offeredMessage = message,
            slideEffect = InfoBarSlideEffect.FROM_BOTTOM
        ) {}

    }
}




