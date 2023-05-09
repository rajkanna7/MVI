package com.rk.snackbar

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

// Basic Snackbar
@Composable
fun BasicSnackBar() {
    Snackbar(modifier = Modifier.padding(4.dp)) {
        Text(text = "Basic snackbar")
    }
}


// Snackbar with action button
@Composable
fun BasicSnackBarWithActionButton(applicationContext: Context) {
    Snackbar(modifier = Modifier.padding(4.dp), action = {
        TextButton(onClick = {
            Toast.makeText(
                applicationContext,
                "This is Snackbar with action button",
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text(text = "Click Me", color = Color.White)
        }
    }) {
        Text(text = "Snackbar with action button")
    }
}

// Snackbar with action button on new line
@Composable
fun BasicSnackBarWithActionButtonNewLine(applicationContext: Context) {

    Snackbar(modifier = Modifier.padding(4.dp), actionOnNewLine = true, action = {
        TextButton(onClick = {
            Toast.makeText(
                applicationContext,
                "This is Snackbar with action button on new line",
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text(text = "ClickMe", color = Color.White)
        }
    }) {
        Text(text = "Action button on new line")
    }
}

// Snackbar with custom background color
@Composable
fun BasicSnackBarCustomBG(applicationContext: Context) {
    Snackbar(modifier = Modifier.padding(4.dp), backgroundColor = Color.White, action = {
        TextButton(onClick = {
            Toast.makeText(
                applicationContext,
                "This is Snackbar with custom color background",
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text(text = "ClickMe")
        }
    }) {
        Text(text = "Custom color background", color = Color.Black)
    }

}


// Custom snackbar with action buttons
@Composable
fun BasicSnackBarButton(applicationContext: Context) {
    Snackbar(modifier = Modifier.padding(4.dp), action = {
        TextButton(onClick = {
            Toast.makeText(
                applicationContext,
                "This is Custom Snackbar with action button",
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text(text = "ClickMe", color = Color.White)
        }
    }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                Toast.makeText(
                    applicationContext,
                    "Edit action button Clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }, modifier = Modifier.height(30.dp)) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "")
            }
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Custom Content SnackBar",
                color = Color.White,
                modifier = Modifier.wrapContentSize(Alignment.Center)
            )
        }
    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SnackbarDemo() {
    val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`
    val coroutineScope = rememberCoroutineScope()
    // using the `coroutineScope` to `launch` showing the snackbar
    // taking the `snackbarHostState` from the attached `scaffoldState`
    coroutineScope.launch {
        val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
            message = "This is your message",
            actionLabel = "Do something."
        )
        when (snackbarResult) {
            SnackbarResult.Dismissed -> Log.d("SnackbarDemo", "Dismissed")
            SnackbarResult.ActionPerformed -> Log.d("SnackbarDemo", "Snackbar's button clicked")
        }
    }



}
