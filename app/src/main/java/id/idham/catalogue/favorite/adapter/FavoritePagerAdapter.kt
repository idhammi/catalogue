package id.idham.catalogue.favorite.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.idham.catalogue.favorite.FavoriteFragment
import id.idham.catalogue.favorite.FavoriteListFragment

class FavoritePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {

    override fun getItemCount(): Int = FavoriteFragment.TAB_TITLES.size

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FavoriteListFragment(position)
            else -> FavoriteListFragment(position)
        }

}