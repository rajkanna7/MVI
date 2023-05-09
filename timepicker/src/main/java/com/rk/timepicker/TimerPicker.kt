package com.rk.timepicker

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


 fun showTimerPicker(onClick: (String) -> Unit, activity: AppCompatActivity) {
    val materialTimePicker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .setMinute(timeFormatter("mm").toInt())
        .setHour(timeFormatter("HH").toInt())
        .build()
    materialTimePicker.addOnPositiveButtonClickListener {
        onClick(
            "${getHour(materialTimePicker.hour).toString()} : ${materialTimePicker.minute.toString()} ${getAmPm(materialTimePicker.hour)}")
    }
    materialTimePicker.show(activity.supportFragmentManager, "fragment_tag")
}

private fun timeFormatter(formate: String?): String {
    val formatter = SimpleDateFormat(formate, Locale.US)
    val calendar: Calendar = Calendar.getInstance()
    return formatter.format(calendar.time)
}

private fun getAmPm(hour: Int): String {
    return if (hour < 12) "AM" else "PM"
}

private fun getHour(hour: Int): Int {
    return when {
        hour == 0 -> {
            hour + 12
        }
        hour > 12 -> {
            hour - 12
        }
        else -> {
            hour
        }
    }
}
@Composable
fun TimePicker(
    value: String,
    onValueChange: (String,Boolean) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    pattern: String = "HH:mm",
    is24HourView: Boolean = true,
) {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val time = if (value.isNotBlank()) LocalTime.parse(value, formatter) else LocalTime.now()
    val dialog = TimePickerDialog(
        LocalContext.current,
        { _, hour, minute -> onValueChange(LocalTime.of(hour, minute).toString() ,false)},
        time.hour,
        time.minute,
        is24HourView,
    )
    dialog.setOnCancelListener {
        onValueChange("",false)
    }
    dialog.show()

}

