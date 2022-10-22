package id.idham.catalogue.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import id.idham.catalogue.BuildConfig.imageUrl
import id.idham.catalogue.R
import id.idham.catalogue.data.Resource
import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.data.local.entity.TvShowEntity
import id.idham.catalogue.databinding.ActivityDetailMovieBinding
import id.idham.catalogue.utils.gone
import id.idham.catalogue.utils.toast
import id.idham.catalogue.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable
import java.util.*

class DetailMovieActivity : AppCompatActivity() {

    private val viewModel by viewModel<DetailMovieViewModel>()

    companion object {
        const val MOVIE_ID = "movie_id"
        const val MOVIE_TYPE = "movie_type"
    }

    sealed class MovieType : Serializable {
        object MOVIE : MovieType()
        object TV : MovieType()
    }

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var movie: MovieEntity
    private lateinit var tvShow: TvShowEntity

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private val detailType: MovieType
        get() = intent.getSerializableExtra(MOVIE_TYPE) as MovieType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if (extras != null) {
            viewModel.setSelectedId(extras.getInt(MOVIE_ID))
            onObserveData(detailType)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.action_favorite -> {
                updateFavorite()
                isFavorite = !isFavorite
                setFavorite()
            }
            R.id.action_share -> onShareClick()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        } else
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
    }

    private fun onShareClick() {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
            .from(this)
            .setType(mimeType)
            .setChooserTitle(getString(R.string.share_title))
            .setText(resources.getString(R.string.share_text, binding.txtName.text))
            .startChooser()
    }

    private fun onObserveData(type: MovieType) {
        if (type is MovieType.MOVIE) {
            viewModel.getMovie().observe(this) {
                when (it) {
                    is Resource.Loading -> {
                        binding.groupTitle.gone()
                        binding.progressBar.visible()
                    }
                    is Resource.Success -> {
                        binding.groupTitle.visible()
                        binding.progressBar.gone()
                        it.data?.let { data -> populateMovie(data) }
                    }
                    is Resource.Error -> {
                        binding.progressBar.gone()
                        toast(it.message.toString())
                    }
                }
            }
        } else if (type is MovieType.TV) {
            viewModel.getTvShow().observe(this) {
                when (it) {
                    is Resource.Loading -> {
                        binding.groupTitle.gone()
                        binding.progressBar.visible()
                    }
                    is Resource.Success -> {
                        binding.groupTitle.visible()
                        binding.progressBar.gone()
                        it.data?.let { data -> populateTvShow(data) }
                    }
                    is Resource.Error -> {
                        binding.progressBar.gone()
                        toast(it.message.toString())
                    }
                }
            }
        }
    }

    private fun populateMovie(movieEntity: MovieEntity) {
        movie = movieEntity
        isFavorite = movieEntity.favorite
        binding.txtName.text = movieEntity.title
        binding.txtYear.text = movieEntity.getYearRelease()
        binding.txtRating.text = movieEntity.rating.toString()
        binding.txtLanguage.text = getLanguageName(movieEntity.lang.toString())
        binding.txtDetailDescription.text = movieEntity.overview
        binding.txtReleaseDateTitle.text = resources.getText(R.string.release_date)
        binding.txtReleaseDate.text = movieEntity.getFormattedDate()

        Glide.with(this)
            .load(imageUrl + movieEntity.imagePath)
            .transform(RoundedCorners(20))
            .into(binding.imgPhoto)
        setFavorite()
    }

    private fun populateTvShow(tvShowEntity: TvShowEntity) {
        tvShow = tvShowEntity
        isFavorite = tvShowEntity.favorite
        binding.txtName.text = tvShowEntity.title
        binding.txtYear.text = tvShowEntity.getYearRelease()
        binding.txtRating.text = tvShowEntity.rating.toString()
        binding.txtLanguage.text = getLanguageName(tvShowEntity.lang.toString())
        binding.txtDetailDescription.text = tvShowEntity.overview
        binding.txtReleaseDateTitle.text = resources.getText(R.string.first_air_date)
        binding.txtReleaseDate.text = tvShowEntity.getFormattedDate()

        Glide.with(this)
            .load(imageUrl + tvShowEntity.imagePath)
            .transform(RoundedCorners(20))
            .into(binding.imgPhoto)
        setFavorite()
    }

    private fun getLanguageName(lang: String): String {
        val loc = Locale(lang)
        return loc.getDisplayLanguage(loc)
    }

    private fun updateFavorite() {
        if (detailType is MovieType.MOVIE) {
            viewModel.setFavoriteMovie(movie)
        } else viewModel.setFavoriteTvShow(tvShow)
    }

}