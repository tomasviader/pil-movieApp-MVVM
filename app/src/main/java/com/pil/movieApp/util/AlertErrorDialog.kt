package com.pil.movieApp.util

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
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
                Toast.makeText(activity,"To home page",Toast.LENGTH_LONG).show()
            }
        }
        alert.show()
    }
}