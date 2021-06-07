package id.idham.catalogue.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import id.idham.catalogue.R
import id.idham.catalogue.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(R.string.movies, R.string.tv_shows)
    }

    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = FavoritePagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, pos ->
            tab.text = resources.getString(TAB_TITLES[pos])
        }.attach()
    }

}