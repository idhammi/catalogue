package id.idham.catalogue.ui.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.idham.catalogue.databinding.FragmentFavoriteMovieBinding
import id.idham.catalogue.ui.detail.DetailMovieActivity
import id.idham.catalogue.utils.gone
import id.idham.catalogue.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMovieFragment : Fragment() {

    private val viewModel by viewModel<FavoriteMovieViewModel>()
    private lateinit var binding: FragmentFavoriteMovieBinding

    private val adapter = FavoriteMovieAdapter { movie -> goToDetail(movie?.id) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMovies.adapter = adapter
        observeData()
    }

    private fun observeData() {
        binding.progressBar.visible()
        viewModel.getFavoriteMovies().observe(viewLifecycleOwner, { movies ->
            binding.progressBar.gone()
            adapter.submitList(movies)
        })
    }

    private fun goToDetail(id: Int?) {
        val intent = Intent(requireContext(), DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.MOVIE_ID, id)
        intent.putExtra(DetailMovieActivity.MOVIE_TYPE, DetailMovieActivity.MovieType.MOVIE)
        requireContext().startActivity(intent)
    }

}