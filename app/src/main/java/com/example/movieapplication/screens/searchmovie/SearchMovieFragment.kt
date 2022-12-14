package com.example.movieapplication.screens.searchmovie

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapplication.R
import com.example.movieapplication.database.movies.MovieDatabase
import com.example.movieapplication.databinding.FragmentSearchmovieBinding
import com.example.movieapplication.utils.Utils
import timber.log.Timber

class SearchMovieFragment : Fragment() {
    private lateinit var binding: FragmentSearchmovieBinding
    private lateinit var viewModel: SearchMovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_searchmovie, container, false)
        viewModel = createViewModel()

        /* SETUP FRAGMENT */
        setOnClickListeners()
        setObservers()
        buildRecyclerView()

        binding.lifecycleOwner = this

        return binding.root
    }

    /**
     * UI Method for handling the search movie button click
     */
    private fun handleSearchMovieClick() {
        hideKeyboard()

        // Calls the fetchMovieData function in the SearchMovieViewModel
        viewModel.fetchMovieData(binding.searchMovieTextInput.text.toString())

        clearSearchTermInput()
    }

    /**
     * UI Method for clearing the searchTermTextInput with the sought for search term
     */
    private fun clearSearchTermInput() {
        // Clears the input field
        binding.searchMovieTextInput.text.clear()
    }

    /**
     * UI Method for hiding the keyboard after the button is hidden
     */
    private fun hideKeyboard() {
        // Hides the keyboard
        Utils.hideSoftKeyboard(this@SearchMovieFragment.requireContext(), binding.searchMovieTextInput)
    }

    /**
     * Helper method for creating a new ViewModel
     */
    private fun createViewModel() : SearchMovieViewModel {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = SearchMovieViewModel.Factory(application)

        return ViewModelProvider(this, viewModelFactory)[SearchMovieViewModel::class.java]
    }

    /**
     * Helper method for setting the click listeners
     */
    private fun setOnClickListeners() {
        binding.searchMovieButton.setOnClickListener {
            handleSearchMovieClick()
        }
    }

    /**
     * Helper method for setting observers
     */
    private fun setObservers() {
        viewModel.searchTerm.observe(viewLifecycleOwner, Observer { newSearchTerm ->
            if (newSearchTerm == "") {
                binding.searchTermTextView.text = newSearchTerm
            } else {
                binding.searchTermTextView.text = "Results for $newSearchTerm"
            }
        })

        viewModel.searchResults.observe(viewLifecycleOwner, Observer {
            buildRecyclerView()
        })
    }

    /**
     * Helper method for setting up the recycler view
     */
    private fun buildRecyclerView() {
        binding.searchResultsRecyclerView!!.layoutManager = LinearLayoutManager(context)
        binding.searchResultsRecyclerView!!.adapter = SearchMovieAdapter(viewModel.searchResults)
    }
}