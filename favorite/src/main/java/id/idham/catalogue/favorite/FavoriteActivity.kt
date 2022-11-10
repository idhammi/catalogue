package id.idham.catalogue.favorite

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.preference.PreferenceManager
import com.google.android.material.tabs.TabLayoutMediator
import id.idham.catalogue.favorite.adapter.FavoritePagerAdapter
import id.idham.catalogue.favorite.databinding.ActivityFavoriteBinding
import id.idham.catalogue.favorite.di.favoriteModule
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setPreferences()

        loadKoinModules(favoriteModule)

        binding.viewPager.adapter = FavoritePagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, pos ->
            tab.text = resources.getString(TAB_TITLES[pos])
        }.attach()
    }

    private fun setPreferences() {
        val sh = PreferenceManager.getDefaultSharedPreferences(this)
        val isDarkMode = sh.getBoolean(getString(id.idham.catalogue.R.string.key_dark_mode), false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        }
    }

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(
            id.idham.catalogue.R.string.movies, id.idham.catalogue.R.string.tv_shows
        )
    }
}