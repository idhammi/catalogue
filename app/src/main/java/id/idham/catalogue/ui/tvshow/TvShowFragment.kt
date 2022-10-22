package id.idham.catalogue.ui.tvshow

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
import id.idham.catalogue.databinding.FragmentTvShowBinding
import id.idham.catalogue.ui.detail.DetailMovieActivity
import id.idham.catalogue.ui.movie.adapter.MovieLoadStateAdapter
import id.idham.catalogue.ui.tvshow.adapter.TvShowListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {

    private val viewModel by viewModel<TvShowViewModel>()
    private lateinit var binding: FragmentTvShowBinding

    private var adapter: TvShowListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        adapter = TvShowListAdapter { item -> goToDetail(item?.id) }

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
            viewModel.getTvShows().collectLatest { tvShows ->
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