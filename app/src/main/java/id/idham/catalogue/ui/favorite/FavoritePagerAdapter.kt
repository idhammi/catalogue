package id.idham.catalogue.ui.favorite

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FavoritePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {

    override fun getItemCount(): Int = FavoriteFragment.TAB_TITLES.size

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FavoriteListFragment(position)
            else -> FavoriteListFragment(position)
        }

}