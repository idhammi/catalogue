package id.idham.catalogue.ui.home

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.idham.catalogue.R
import id.idham.catalogue.databinding.ActivityHomeBinding
import id.idham.catalogue.ui.favorite.FavoriteFragment
import id.idham.catalogue.ui.movie.MovieFragment
import id.idham.catalogue.ui.tvshow.TvShowFragment

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.elevation = 0f

        binding.bnMain.setOnNavigationItemSelectedListener(this)
        loadFragment(MovieFragment())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frameLayout)
        when (item.itemId) {
            R.id.menu_movie -> if (currentFragment !is MovieFragment) fragment = MovieFragment()
            R.id.menu_tv_show -> if (currentFragment !is TvShowFragment) fragment = TvShowFragment()
            R.id.menu_favorite -> if (currentFragment !is FavoriteFragment) fragment =
                FavoriteFragment()
        }
        return loadFragment(fragment)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
            return true
        }
        return false
    }
}