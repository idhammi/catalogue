package id.idham.catalogue.ui.favorite.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.idham.catalogue.databinding.FragmentFavoriteTvShowBinding
import id.idham.catalogue.ui.detail.DetailMovieActivity
import id.idham.catalogue.utils.gone
import id.idham.catalogue.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTvShowFragment : Fragment() {

    private val viewModel by viewModel<FavoriteTvShowViewModel>()
    private lateinit var binding: FragmentFavoriteTvShowBinding

    private val adapter = FavoriteTvShowAdapter { tvShow -> goToDetail(tvShow?.id) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteTvShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTvShows.adapter = adapter
        observeData()
    }

    private fun observeData() {
        binding.progressBar.visible()
        viewModel.getFavoriteTvShows().observe(viewLifecycleOwner, { tvShows ->
            binding.progressBar.gone()
            adapter.submitList(tvShows)
        })
    }

    private fun goToDetail(id: Int?) {
        val intent = Intent(requireContext(), DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.MOVIE_ID, id)
        intent.putExtra(DetailMovieActivity.MOVIE_TYPE, DetailMovieActivity.MovieType.TV)
        requireContext().startActivity(intent)
    }

}