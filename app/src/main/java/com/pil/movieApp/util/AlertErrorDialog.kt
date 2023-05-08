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
            setTitle("ERROR")
            setMessage("Error in the database")
            setCancelable(false)
            setNeutralButton("OK"
            ) { _, _ ->
                val intent = Intent(activity, MainActivity::class.java)
                activity.startActivity(intent)
            }
        }
        alert.show()
    }
}