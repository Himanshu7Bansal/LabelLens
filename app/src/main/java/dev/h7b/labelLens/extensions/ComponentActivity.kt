package dev.h7b.labelLens.extensions

import android.widget.Toast
import androidx.activity.ComponentActivity

fun ComponentActivity.showToast(text: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, duration).show()
}