package id.idham.catalogue.favorite.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.idham.catalogue.favorite.FavoriteActivity.Companion.TAB_TITLES
import id.idham.catalogue.favorite.FavoriteListFragment

class FavoritePagerAdapter(f: FragmentActivity) : FragmentStateAdapter(f) {

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FavoriteListFragment(position)
            else -> FavoriteListFragment(position)
        }

}