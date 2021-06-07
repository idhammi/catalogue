package id.idham.catalogue.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.idham.catalogue.data.mapper.MovieMapper
import id.idham.catalogue.data.source.local.entity.MovieEntity
import id.idham.catalogue.databinding.FragmentMovieBinding
import id.idham.catalogue.utils.EspressoIdlingResource
import id.idham.catalogue.utils.enums.Status
import id.idham.catalogue.utils.gone
import id.idham.catalogue.utils.toast
import id.idham.catalogue.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val viewModel by viewModel<MovieViewModel>()
    private lateinit var binding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        EspressoIdlingResource.increment() // for instrumentation test only
        viewModel.getMovies()
    }

    private fun observeData() {
        viewModel.dataMovies.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> binding.progressBar.visible()
                Status.SUCCESS -> {
                    binding.progressBar.gone()
                    it.data?.let { list ->
                        val results = ArrayList<MovieEntity>()
                        for (data in list) {
                            results.add(MovieMapper.mapResponseToEntity(data))
                        }
                        setData(results)
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.gone()
                    requireActivity().toast(it.message.toString())
                }
            }
            // for instrumentation test only
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        })
    }

    private fun setData(data: List<MovieEntity>) {
        val movieAdapter = MovieAdapter()
        movieAdapter.setItems(data)
        with(binding.rvMovies) {
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

}