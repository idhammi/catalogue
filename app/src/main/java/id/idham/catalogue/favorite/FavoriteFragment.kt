package id.idham.catalogue.favorite

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import id.idham.catalogue.R
import id.idham.catalogue.databinding.FragmentFavoriteBinding
import id.idham.catalogue.favorite.adapter.FavoritePagerAdapter

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(R.string.movies, R.string.tv_shows)
    }

    private val binding: FragmentFavoriteBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = FavoritePagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, pos ->
            tab.text = resources.getString(TAB_TITLES[pos])
        }.attach()
    }

}