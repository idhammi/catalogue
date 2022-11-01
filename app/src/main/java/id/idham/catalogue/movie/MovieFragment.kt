package id.idham.catalogue.movie

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import id.idham.catalogue.R
import id.idham.catalogue.databinding.FragmentMovieBinding
import id.idham.catalogue.detail.DetailMovieActivity
import id.idham.catalogue.movie.adapter.MovieListAdapter
import id.idham.catalogue.movie.adapter.MovieLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment(R.layout.fragment_movie) {

    private val viewModel by viewModel<MovieViewModel>()
    private val binding: FragmentMovieBinding by viewBinding()

    private var adapter: MovieListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        adapter = MovieListAdapter { item -> goToDetail(item?.movieId) }

        binding.rvMovies.adapter = adapter?.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { adapter?.retry() },
            footer = MovieLoadStateAdapter { adapter?.retry() }
        )

        adapter?.addLoadStateListener { loadState -> renderUi(loadState) }
        binding.lytMovieError.btnErrorRetry.setOnClickListener { adapter?.retry() }
    }

    private fun renderUi(loadState: CombinedLoadStates) {
        val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter?.itemCount == 0
        with(binding) {
            rvMovies.isVisible = !isListEmpty
            lytMovieEmpty.root.isVisible = isListEmpty

            rvMovies.isVisible = loadState.source.refresh is LoadState.NotLoading
            pbMovie.isVisible = loadState.source.refresh is LoadState.Loading
            lytMovieError.root.isVisible = loadState.source.refresh is LoadState.Error
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collectLatest { movies ->
                adapter?.submitData(movies)
            }
        }
    }

    private fun goToDetail(id: Int?) {
        val intent = Intent(requireContext(), DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.MOVIE_ID, id)
        intent.putExtra(DetailMovieActivity.MOVIE_TYPE, DetailMovieActivity.MovieType.MOVIE)
        requireContext().startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

}