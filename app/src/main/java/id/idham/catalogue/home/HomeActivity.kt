package id.idham.catalogue.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import id.idham.catalogue.R
import id.idham.catalogue.core.utils.ext.visible
import id.idham.catalogue.databinding.ActivityHomeBinding
import id.idham.catalogue.movie.MovieFragment
import id.idham.catalogue.setting.SettingsActivity
import id.idham.catalogue.tvshow.TvShowFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setPreferences()

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
                    val uri = Uri.parse("catalogue://favorite")
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                }
            }
            return@setOnItemSelectedListener loadFragment(fragment)
        }

        if (savedInstanceState == null) {
            loadFragment(MovieFragment())
        }
    }

    private fun setPreferences() {
        val sh = PreferenceManager.getDefaultSharedPreferences(this)
        val isDarkMode = sh.getBoolean(getString(R.string.key_dark_mode), false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
            return true
        }
        return false
    }
}