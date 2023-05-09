package com.rk.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun DialogInfo(onClick: (Boolean) -> Unit, isDialog: Boolean) {
    val openDialog = remember { mutableStateOf(isDialog) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
                onClick (openDialog.value)
            },
            title = {
                Text(text = "Title")
            },
            text = {
                Text(text = "Turned on by default")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onClick (openDialog.value)
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onClick (openDialog.value)
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}
@Composable
fun InfoDialog(
    title: String?="Message",
    desc: String?="Your Message",
    onClick: (Boolean) -> Unit, isDialog: Boolean
) {
    Dialog(
        onDismissRequest = {onClick(false)}
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .padding(all = 20.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(all = 20.dp)
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(25.dp, 10.dp, 25.dp, 10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = title!!,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.subtitle1,
                            color = MaterialTheme.colors.onPrimary,
                        )
                        Spacer(modifier = Modifier.height(8.dp))


                        Text(
                            text = desc!!,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onPrimary,
                        )
                        Spacer(modifier = Modifier.height(24.dp))


                        Button(
                            onClick = {onClick(false)},
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                        ) {
                            Text(
                                text = "Later",
                                color = Color.White
                            )
                        }
                        Button(
                            onClick = {onClick(false)},
                            colors= ButtonDefaults.buttonColors( contentColor = MaterialTheme.colors.primary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                        ) {
                            Text(
                                text = "Enable Location",
                                color = Color.White
                            )
                        }


                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
            HeaderImage(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.TopCenter)
                /*.border(
                    border = BorderStroke(width = 5.dp, color = Color.White),
                    shape = CircleShape
                )*/
            )
        }
    }
}
@Composable
fun HeaderImage(modifier: Modifier) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.location))
    val progress by animateLottieCompositionAsState(composition = composition)

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomInfoDialog(
    title: String?="Message",
    desc: String?="Your Message",
    onClick: (Boolean) -> Unit, isDialog: Boolean
) {
    Dialog(
        onDismissRequest = { onClick(false) },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color.Transparent,
                )
        ) {


            Box(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp)
                    )
                    .align(Alignment.BottomCenter),
            ) {

                Image(
                    painter = painterResource(id = R.drawable.bolt_uix_no_internet),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth(),

                    )
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Text: title
                    Text(
                        text = title!!,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 130.dp)
                            .fillMaxWidth(),
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //.........................Text : description
                    Text(
                        text = desc!!,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        color = Color.Black,
                    )
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Button : OK button
                    Button(
                        onClick = { onClick(false) },
                        //shape = Shapes.small,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors= ButtonDefaults.buttonColors(contentColor = MaterialTheme.colors.primary),
                        //.clip(RoundedCornerShape(25.dp))
                    ) {
                        Text(
                            text = "OK",
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.onPrimary,
                        )
                    }



                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                }


            }

        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
 fun NoInternetScreen(onClick: (Boolean) -> Unit, isDialog: Boolean) {
    val openFullDialogCustom = remember { mutableStateOf(isDialog) }
    if (openFullDialogCustom.value) {

        Dialog(
            onDismissRequest = {
                openFullDialogCustom.value = false
                onClick( openFullDialogCustom.value)
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false // experimental
            )
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {



                    Image(
                        painter = painterResource(id = R.drawable.no_intrenet),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),

                        )

                    Spacer(modifier = Modifier.height(20.dp))
                    //.........................Text: title
                    Text(
                        text = "Whoops!!",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.Bold,
                        color =Color.Black,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //.........................Text : description
                    Text(
                        text = "No Internet connection was found. Check your connection or try again.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        letterSpacing = 1.sp,
                        color =Color.Black,
                    )
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    val cornerRadius = 16.dp
                    val gradientColor = listOf(Color(0xFFff669f), Color(0xFFff8961))
                    GradientButton(
                        gradientColors = gradientColor,
                        cornerRadius = cornerRadius,
                        nameButton = "Try again",
                        roundedCornerShape = RoundedCornerShape(topStart = 30.dp,bottomEnd = 30.dp)
                    )

                }

            }
        }

    }
}




//...........................................................................
@Composable
fun GradientButton(
    gradientColors: List<Color>,
    cornerRadius: Dp,
    nameButton: String,
    roundedCornerShape: RoundedCornerShape
) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        onClick = {
            //your code
        },

        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Transparent
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                    shape = roundedCornerShape
                )
                .clip(roundedCornerShape)
                /*.background(
                    brush = Brush.linearGradient(colors = gradientColors),
                    shape = RoundedCornerShape(cornerRadius)
                )*/
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nameButton,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}
