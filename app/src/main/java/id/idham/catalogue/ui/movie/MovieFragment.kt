package id.idham.catalogue.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import id.idham.catalogue.databinding.FragmentMovieBinding
import id.idham.catalogue.ui.detail.DetailMovieActivity
import id.idham.catalogue.ui.movie.adapter.MovieListAdapter
import id.idham.catalogue.ui.movie.adapter.MovieLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val viewModel by viewModel<MovieViewModel>()
    private lateinit var binding: FragmentMovieBinding

    private var adapter: MovieListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        adapter = MovieListAdapter { item -> goToDetail(item?.id) }

        binding.rvMovies.adapter = adapter?.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { adapter?.retry() },
            footer = MovieLoadStateAdapter { adapter?.retry() }
        )

        adapter?.addLoadStateListener { loadState -> renderUi(loadState) }
        binding.lytError.btnRetry.setOnClickListener { adapter?.retry() }
    }

    private fun renderUi(loadState: CombinedLoadStates) {
        val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter?.itemCount == 0

        binding.rvMovies.isVisible = !isListEmpty
        binding.lytEmpty.root.isVisible = isListEmpty

        binding.rvMovies.isVisible = loadState.source.refresh is LoadState.NotLoading
        binding.pbMovie.isVisible = loadState.source.refresh is LoadState.Loading
        binding.lytError.root.isVisible = loadState.source.refresh is LoadState.Error
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMovies().collectLatest { movies ->
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