package id.idham.catalogue.ui.favorite.tvshow

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import id.idham.catalogue.R
import id.idham.catalogue.databinding.FragmentFavoriteTvShowBinding
import id.idham.catalogue.ui.detail.DetailMovieActivity
import id.idham.catalogue.utils.SortUtils
import id.idham.catalogue.utils.gone
import id.idham.catalogue.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTvShowFragment : Fragment() {

    private val viewModel by viewModel<FavoriteTvShowViewModel>()
    private lateinit var binding: FragmentFavoriteTvShowBinding

    private val adapter = FavoriteTvShowAdapter { tvShow -> goToDetail(tvShow?.id) }
    private var selectedSort = SortUtils.TITLE
    private var checkedItem = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteTvShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.registerAdapterDataObserver(adapterObserver)
        binding.rvTvShows.adapter = adapter
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
        viewModel.getFavoriteTvShows(selectedSort).observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private val adapterObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            val count = adapter.itemCount
            if (itemCount == 0 && count == 0) {
                binding.tvSort.gone()
                binding.rvTvShows.gone()
                binding.lytEmpty.root.visible()
            } else {
                binding.tvSort.visible()
                binding.rvTvShows.visible()
                binding.lytEmpty.root.gone()
            }
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            val count = adapter.itemCount
            if (count == 0) {
                binding.tvSort.gone()
                binding.rvTvShows.gone()
                binding.lytEmpty.root.visible()
            } else {
                binding.tvSort.visible()
                binding.rvTvShows.visible()
                binding.lytEmpty.root.gone()
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