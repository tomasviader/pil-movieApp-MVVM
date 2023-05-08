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
            setTitle(alertTitle)
            setMessage(alertMessage)
            setCancelable(false)
            setNeutralButton(
                alertButtonText
            ) { _, _ ->
                val intent = Intent(activity, MainActivity::class.java)
                activity.startActivity(intent)
            }
        }
        alert.show()
    }

    private const val alertTitle = "Error in the database"
    private const val alertMessage = "Error in the database"
    private const val alertButtonText = "OK"
}