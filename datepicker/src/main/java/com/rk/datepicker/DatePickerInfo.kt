package com.rk.datepicker

import android.app.DatePickerDialog
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun MyContent(onValueChange: (String) -> Unit = {}, onClick: (String, Boolean) -> Unit) {

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            onValueChange(LocalDate.of(mYear, mMonth + 1, mDayOfMonth).toString())
            onClick(LocalDate.of(mYear, mMonth + 1, mDayOfMonth).toString(), false)
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )
    mDatePickerDialog.setOnCancelListener {
        onClick("", false)
    }
    mDatePickerDialog.show()

}


@Composable
fun DatePicker( onClick: (String, Boolean) -> Unit) {
    val selDate = remember { mutableStateOf(LocalDate.now()) }

    //todo - add strings to resource after POC
    Dialog(onDismissRequest = { onClick("",false) }, properties = DialogProperties()) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(size = 16.dp)
                )
        ) {
            Column(
                Modifier
                    .defaultMinSize(minHeight = 72.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "Select date".toUpperCase(Locale.ENGLISH),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onPrimary
                )

                Spacer(modifier = Modifier.size(24.dp))

                Text(
                    text = selDate.value.format(DateTimeFormatter.ofPattern("MMM d, YYYY")),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onPrimary
                )

                Spacer(modifier = Modifier.size(16.dp))
            }

            CustomCalendarView(
                onDateSelected = {
                selDate.value = it
            })

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                TextButton(
                    onClick = {onClick("",false)}
                ) {
                    //TODO - hardcode string
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.button,
                        color = Color.Black
                    )
                }

                TextButton(
                    onClick = {
                        onClick(selDate.value.format(DateTimeFormatter.ofPattern("MMM d, YYYY")),false)
                    }
                ) {
                    //TODO - hardcode string
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.button,
                        color = Color.Black
                    )
                }

            }
        }
    }
}

private fun dateFormatter(milliseconds: Long?): String? {
    milliseconds?.let {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = it
        return formatter.format(calendar.time)
    }
    return ""
}

fun showDatePicker(
    activity: AppCompatActivity,
    updatedDate: (String) -> Unit
) {
    val picker = MaterialDatePicker.Builder.datePicker().build()
    picker.show(activity.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener {
        dateFormatter(it)?.let { it1 -> updatedDate(it1) }
    }

}

@Composable
fun CustomCalendarView(onDateSelected: (LocalDate) -> Unit) {
    // Adds view to Compose
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            CalendarView(ContextThemeWrapper(context, R.style.CalenderViewCustom))
        },
        update = { view ->
//            view.minDate = // contraints
//                view.maxDate = // contraints

            view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                onDateSelected(
                    LocalDate
                        .now()
                        .withMonth(month + 1)
                        .withYear(year)
                        .withDayOfMonth(dayOfMonth)
                )
            }
        }
    )
}


