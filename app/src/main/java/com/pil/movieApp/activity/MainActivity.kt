package com.pil.movieApp.activity


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pil.movieApp.util.AlertErrorDialog
import com.pil.movieApp.util.ErrorDialogFragment
import com.pil.retrofit_room.R
import com.pil.retrofit_room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentMovieActivity = Intent(this, MovieActivity::class.java)
        binding.btnBack.visibility = View.INVISIBLE

        binding.btnMovies.setOnClickListener {
            startActivity(intentMovieActivity)
            finish()
        }

            //AlertErrorDialog.showDialogError(this)
        binding.errorDialog.setOnClickListener{
            ErrorDialogFragment.newInstance(getString(R.string.alert_title),
                getString(R.string.alert_message)).show(supportFragmentManager,"ERROR")

        }


    }

}
