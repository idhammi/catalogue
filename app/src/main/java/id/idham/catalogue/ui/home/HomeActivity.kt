package id.idham.catalogue.ui.home

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import id.idham.catalogue.R
import id.idham.catalogue.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(R.string.movies, R.string.tv_shows)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val screenSlidePagerAdapter = ScreenSlidePagerAdapter(this)
        binding.viewPager.adapter = screenSlidePagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, pos ->
            tab.text = resources.getString(TAB_TITLES[pos])
        }.attach()

        supportActionBar?.elevation = 0f
    }
}