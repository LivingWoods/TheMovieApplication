package com.example.movieapplication.screens.title

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.movieapplication.R
import com.example.movieapplication.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater, R.layout.fragment_title, container, false)

        binding.toSearchMovieButton.setOnClickListener (
            Navigation.createNavigateOnClickListener(
                R.id.action_titleFragment_to_searchMovieFragment
            )
        )

        return binding.root
    }
}