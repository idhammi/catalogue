package id.idham.catalogue.tvshow

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
import id.idham.catalogue.databinding.FragmentTvShowBinding
import id.idham.catalogue.detail.DetailMovieActivity
import id.idham.catalogue.movie.adapter.MovieLoadStateAdapter
import id.idham.catalogue.tvshow.adapter.TvShowListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment(R.layout.fragment_tv_show) {

    private val viewModel by viewModel<TvShowViewModel>()
    private val binding: FragmentTvShowBinding by viewBinding()

    private var adapter: TvShowListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        adapter = TvShowListAdapter { item -> goToDetail(item?.tvShowId) }

        binding.rvTvShows.adapter = adapter?.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { adapter?.retry() },
            footer = MovieLoadStateAdapter { adapter?.retry() }
        )

        adapter?.addLoadStateListener { loadState -> renderUi(loadState) }
        binding.lytError.btnRetry.setOnClickListener { adapter?.retry() }
    }

    private fun renderUi(loadState: CombinedLoadStates) {
        val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter?.itemCount == 0

        binding.rvTvShows.isVisible = !isListEmpty
        binding.lytEmpty.root.isVisible = isListEmpty

        binding.rvTvShows.isVisible = loadState.source.refresh is LoadState.NotLoading
        binding.pbTvShow.isVisible = loadState.source.refresh is LoadState.Loading
        binding.lytError.root.isVisible = loadState.source.refresh is LoadState.Error
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tvShows.collectLatest { tvShows ->
                adapter?.submitData(tvShows)
            }
        }
    }

    private fun goToDetail(id: Int?) {
        val intent = Intent(requireContext(), DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.MOVIE_ID, id)
        intent.putExtra(DetailMovieActivity.MOVIE_TYPE, DetailMovieActivity.MovieType.TV)
        requireContext().startActivity(intent)
    }

}