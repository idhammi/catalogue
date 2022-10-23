package id.idham.catalogue.favorite

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.idham.catalogue.BuildConfig
import id.idham.catalogue.R
import id.idham.catalogue.core.domain.model.Movie
import id.idham.catalogue.core.domain.model.TvShow
import id.idham.catalogue.core.ui.BaseListAdapter
import id.idham.catalogue.core.ui.Equatable
import id.idham.catalogue.core.utils.SortUtils
import id.idham.catalogue.core.utils.ext.gone
import id.idham.catalogue.core.utils.ext.visible
import id.idham.catalogue.databinding.FragmentFavoriteListBinding
import id.idham.catalogue.detail.DetailMovieActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteListFragment(private val index: Int) : Fragment(R.layout.fragment_favorite_list) {

    private val viewModel by viewModel<FavoriteViewModel>()
    private val binding: FragmentFavoriteListBinding by viewBinding()

    private var adapter: BaseListAdapter? = null
    private var selectedSort = SortUtils.TITLE
    private var checkedItem = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        adapter = object : BaseListAdapter(
            layoutId = R.layout.item_favorite,
            bind = { item, view -> bindItem(item, view) }
        ) {}
        adapter?.registerAdapterDataObserver(adapterObserver)
        binding.rvFavorites.adapter = adapter
        binding.tvSort.setOnClickListener { showOptionsDialog() }
    }

    private fun bindItem(item: Equatable, view: View) {
        view.findViewById<TextView>(R.id.tv_title).text = when (item) {
            is Movie -> item.title
            is TvShow -> item.name
            else -> ""
        }
        view.findViewById<TextView>(R.id.tv_desc).text = when (item) {
            is Movie -> item.overview
            is TvShow -> item.overview
            else -> ""
        }
        val imgPath = when (item) {
            is Movie -> item.posterPath
            is TvShow -> item.posterPath
            else -> ""
        }
        view.setOnClickListener {
            when (item) {
                is Movie -> goToDetail(item.movieId, DetailMovieActivity.MovieType.MOVIE)
                is TvShow -> goToDetail(item.tvShowId, DetailMovieActivity.MovieType.TV)
            }
        }
        Glide.with(view.context)
            .load(BuildConfig.imageUrl + imgPath)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view.findViewById(R.id.img_photo))
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
        when (index) {
            0 -> viewModel.getFavoriteMovies(selectedSort)
                .observe(viewLifecycleOwner) { adapter?.submitList(it) }
            1 -> viewModel.getFavoriteTvShows(selectedSort)
                .observe(viewLifecycleOwner) { adapter?.submitList(it) }
        }

    }

    private val adapterObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            val count = adapter?.itemCount
            if (itemCount == 0 && count == 0) onItemEmpty()
            else onItemNotEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            val count = adapter?.itemCount
            if (count == 0) onItemEmpty()
            else onItemNotEmpty()
        }
    }

    private fun onItemNotEmpty() {
        binding.tvSort.visible()
        binding.rvFavorites.visible()
        binding.lytEmpty.root.gone()
    }

    private fun onItemEmpty() {
        binding.tvSort.gone()
        binding.rvFavorites.gone()
        binding.lytEmpty.root.visible()
    }

    private fun goToDetail(id: Int?, type: DetailMovieActivity.MovieType) {
        val intent = Intent(requireContext(), DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.MOVIE_ID, id)
        intent.putExtra(DetailMovieActivity.MOVIE_TYPE, type)
        context?.startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

}