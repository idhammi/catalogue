package id.idham.catalogue.ui.favorite.movie

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import id.idham.catalogue.R
import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.databinding.FragmentFavoriteMovieBinding
import id.idham.catalogue.ui.detail.DetailMovieActivity
import id.idham.catalogue.utils.SortUtils
import id.idham.catalogue.utils.gone
import id.idham.catalogue.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMovieFragment : Fragment() {

    private val viewModel by viewModel<FavoriteMovieViewModel>()
    private lateinit var binding: FragmentFavoriteMovieBinding

    private val adapter = FavoriteMovieAdapter { movie -> goToDetail(movie?.id) }
    private var selectedSort = SortUtils.TITLE
    private var checkedItem = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.registerAdapterDataObserver(adapterObserver)
        binding.rvMovies.adapter = adapter
        binding.tvSort.setOnClickListener { showOptionsDialog() }
        observeData()
    }

    private fun showOptionsDialog() {
        val sorts = arrayOf(getString(R.string.by_title), getString(R.string.by_rating))
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.sort))
            .setSingleChoiceItems(sorts, checkedItem) { dialog, which ->
                if (which == 0) selectedSort = SortUtils.TITLE
                else if (which == 1) selectedSort = SortUtils.RATING
                binding.tvSort.text = sorts[which]
                checkedItem = which
                observeData()
                dialog.dismiss()
            }.show()
    }

    private fun observeData() {
        binding.progressBar.visible()
        viewModel.getFavoriteMovies(selectedSort).observe(viewLifecycleOwner, movieObserver)
    }

    private val movieObserver = Observer<PagedList<MovieEntity>> { movies ->
        if (movies != null) {
            binding.progressBar.gone()
            adapter.submitList(movies)
        }
    }

    private val adapterObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            val count = adapter.itemCount
            if (itemCount == 0 && count == 0) {
                binding.tvSort.gone()
                binding.rvMovies.gone()
                binding.lytEmpty.root.visible()
            } else {
                binding.tvSort.visible()
                binding.rvMovies.visible()
                binding.lytEmpty.root.gone()
            }
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            val count = adapter.itemCount
            if (count == 0) {
                binding.tvSort.gone()
                binding.rvMovies.gone()
                binding.lytEmpty.root.visible()
            } else {
                binding.tvSort.visible()
                binding.rvMovies.visible()
                binding.lytEmpty.root.gone()
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