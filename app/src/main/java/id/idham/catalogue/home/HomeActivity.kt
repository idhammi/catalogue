package id.idham.catalogue.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import id.idham.catalogue.R
import id.idham.catalogue.core.utils.ext.gone
import id.idham.catalogue.core.utils.ext.visible
import id.idham.catalogue.databinding.ActivityHomeBinding
import id.idham.catalogue.favorite.FavoriteFragment
import id.idham.catalogue.movie.MovieFragment
import id.idham.catalogue.tvshow.TvShowFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.content.bnMain.setOnItemSelectedListener { item ->
            var fragment: Fragment? = null
            val currentFragment = supportFragmentManager.findFragmentById(R.id.frameLayout)
            when (item.itemId) {
                R.id.menu_movie -> {
                    binding.toolbar.visible()
                    if (currentFragment !is MovieFragment) fragment = MovieFragment()
                }
                R.id.menu_tv_show -> {
                    binding.toolbar.visible()
                    if (currentFragment !is TvShowFragment) fragment = TvShowFragment()
                }
                R.id.menu_favorite -> {
                    binding.toolbar.gone()
                    if (currentFragment !is FavoriteFragment) fragment = FavoriteFragment()
                }
            }
            return@setOnItemSelectedListener loadFragment(fragment)
        }
        loadFragment(MovieFragment())
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
            return true
        }
        return false
    }
}