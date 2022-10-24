package id.idham.catalogue.favorite

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.google.android.material.tabs.TabLayoutMediator
import id.idham.catalogue.favorite.adapter.FavoritePagerAdapter
import id.idham.catalogue.favorite.databinding.ActivityFavoriteBinding
import id.idham.catalogue.favorite.di.favoriteModule
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

        binding.viewPager.adapter = FavoritePagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, pos ->
            tab.text = resources.getString(TAB_TITLES[pos])
        }.attach()
    }

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(R.string.movies, R.string.tv_shows)
    }
}