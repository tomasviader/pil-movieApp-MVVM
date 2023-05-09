package com.pil.movieApp.util

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.pil.movieApp.activity.MainActivity
import com.pil.retrofit_room.R


object AlertErrorDialog {

    fun showDialogError(activity : AppCompatActivity){
        val alert = AlertDialog.Builder(activity)
        alert.apply {
            setIcon(R.drawable.baseline_error)
            setTitle(R.string.alert_title)
            setMessage(R.string.alert_message)
            setCancelable(false)
            setNeutralButton(
                R.string.alert_button_text
            ) { _, _ ->
                val intent = Intent(activity, MainActivity::class.java)
                activity.startActivity(intent)
            }
        }
        alert.show()
    }

}