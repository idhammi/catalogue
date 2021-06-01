package id.idham.catalogue.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.idham.catalogue.ui.movie.MovieFragment
import id.idham.catalogue.ui.tvshow.TvShowFragment

class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = HomeActivity.TAB_TITLES.size

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> MovieFragment()
            else -> TvShowFragment()
        }

}