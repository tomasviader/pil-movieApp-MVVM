package com.pil.movieApp.presentation.util

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.pil.movieApp.presentation.activity.MainActivity
import com.pil.retrofit_room.databinding.FragmentDialogBinding

class ErrorDialogFragment : DialogFragment() {

    private var alertTitle: String? = null
    private var alertMessage: String? = null
    private lateinit var binding: FragmentDialogBinding

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_MESSAGE = "message"

        fun newInstance(title: String? = null, message: String? = null): ErrorDialogFragment {
            val fragment = ErrorDialogFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_MESSAGE, message)
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alertTitle = arguments?.getString(ARG_TITLE)
        alertMessage = arguments?.getString(ARG_MESSAGE)

        binding.alertTitle.text = alertTitle
        binding.alertMessage.text = alertMessage
        binding.alertButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            dismiss()
        }
    }

}