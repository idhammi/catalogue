package id.idham.catalogue.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.idham.catalogue.databinding.FragmentMovieBinding
import id.idham.catalogue.ui.detail.DetailMovieActivity
import id.idham.catalogue.utils.gone
import id.idham.catalogue.utils.observe
import id.idham.catalogue.utils.visible
import id.idham.catalogue.vo.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val viewModel by viewModel<MovieViewModel>()
    private lateinit var binding: FragmentMovieBinding

    private val movieAdapter = MovieAdapter { movie -> goToDetail(movie?.id) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMovies.adapter = movieAdapter
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            val movies = getMovies()
            observe(movies.pagedList) {
                movieAdapter.submitList(it)
            }
            observe(movies.networkState) {
                movieAdapter.setNetworkState(it)
                when (it.status) {
                    Status.LOADING -> if (it.message == "1") binding.progressBar.visible()
                    Status.ERROR -> {
                        binding.progressBar.gone()
                        binding.lytError.root.visible()
                    }
                    else -> binding.progressBar.gone()
                }
            }
        }
    }

    private fun goToDetail(id: Int?) {
        val intent = Intent(requireContext(), DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.MOVIE_ID, id)
        intent.putExtra(DetailMovieActivity.MOVIE_TYPE, DetailMovieActivity.MovieType.MOVIE)
        requireContext().startActivity(intent)
    }

}