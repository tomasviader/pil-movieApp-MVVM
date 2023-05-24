package com.pil.movieApp.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pil.movieApp.presentation.util.ErrorDialogFragment
import com.pil.movieApp.presentation.mvvm.viewmodel.MainViewModel
import com.pil.retrofit_room.R
import com.pil.retrofit_room.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMovies.setOnClickListener { viewModel.onMoviesButtonPressed() }
        binding.errorDialog.setOnClickListener { viewModel.onShowErrorButtonPressed() }
        viewModel.getValue().observe(this) { updateUI(it) }
    }

    private fun updateUI(data: MainViewModel.MainData) {
        when (data.status) {
            MainViewModel.MainStatus.SHOW_MOVIES -> {
                startActivity(Intent(this, MovieActivity::class.java))
                finish()
            }

            MainViewModel.MainStatus.SHOW_ERROR_DIALOG -> {
                ErrorDialogFragment.newInstance(
                    getString(R.string.alert_title),
                    getString(R.string.alert_message)
                ).show(supportFragmentManager, getString(R.string.fragment_tag))
            }
        }
    }
}
