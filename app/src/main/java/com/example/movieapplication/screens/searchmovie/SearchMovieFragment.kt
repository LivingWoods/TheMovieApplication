package com.example.movieapplication.screens.searchmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
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
        setActionBarTitle("Search a movie")
        setOnClickListeners()
        setObservers()
        setupRecyclerView()

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
        val dataSource = MovieDatabase.getInstance(application).movieDatabaseDao
        val viewModelFactory = SearchMovieViewModelFactory(dataSource, application)

        return ViewModelProvider(this, viewModelFactory)[SearchMovieViewModel::class.java]
    }

    /**
     * Helper method for setting the title of the fragment in the action bar
     */
    // TODO: Create extension function
    private fun setActionBarTitle(title: String) {
        requireActivity().title = title
    }

    /**
     * Helper method for setting the title of the fragment in the action bar
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
    }

    /**
     * Helper method for setting up the recycler view
     */
    private fun setupRecyclerView() {
        binding.searchResultsRecyclerView!!.layoutManager = LinearLayoutManager(context)
        binding.searchResultsRecyclerView!!.adapter = SearchMovieAdapter(viewModel.searchResults)
    }
}