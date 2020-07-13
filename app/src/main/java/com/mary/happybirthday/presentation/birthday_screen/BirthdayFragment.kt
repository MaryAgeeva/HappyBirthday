package com.mary.happybirthday.presentation.birthday_screen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mary.happybirthday.R

class BirthdayFragment : Fragment() {

    companion object {
        fun newInstance() = BirthdayFragment()
    }

    private lateinit var viewModel: BirthdayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.birthday_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BirthdayViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
