package id.idham.catalogue.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import id.idham.catalogue.BuildConfig.imageUrl
import id.idham.catalogue.R
import id.idham.catalogue.data.mapper.MovieMapper
import id.idham.catalogue.data.mapper.TvShowMapper
import id.idham.catalogue.data.source.local.entity.MovieEntity
import id.idham.catalogue.data.source.local.entity.TvShowEntity
import id.idham.catalogue.databinding.ActivityDetailMovieBinding
import id.idham.catalogue.databinding.ContentDetailMovieBinding
import id.idham.catalogue.utils.EspressoIdlingResource
import id.idham.catalogue.utils.enums.Status
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

    private lateinit var binding: ContentDetailMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailCourseBinding = ActivityDetailMovieBinding.inflate(layoutInflater)
        binding = activityDetailCourseBinding.detailContent

        setContentView(activityDetailCourseBinding.root)

        setSupportActionBar(activityDetailCourseBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        onObserveData()

        val extras = intent.extras
        if (extras != null) {
            val id = extras.getString(MOVIE_ID)
            val type = extras.getSerializable(MOVIE_TYPE) as MovieType

            if (id != null) {
                EspressoIdlingResource.increment() // for instrumentation test only
                viewModel.setSelectedId(id)
                if (type is MovieType.MOVIE) viewModel.getMovie()
                else if (type is MovieType.TV) viewModel.getTvShow()
            }
        }
    }

    private fun onObserveData() {
        viewModel.dataMovie.observe(this, {
            when (it.status) {
                Status.LOADING -> binding.progressBar.visible()
                Status.SUCCESS -> {
                    binding.progressBar.gone()
                    it.data?.let { data -> populateMovie(MovieMapper.mapResponseToEntity(data)) }
                }
                Status.ERROR -> {
                    binding.progressBar.gone()
                    toast(it.message.toString())
                }
            }
            // for instrumentation test only
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        })

        viewModel.dataTvShow.observe(this, {
            when (it.status) {
                Status.LOADING -> binding.progressBar.visible()
                Status.SUCCESS -> {
                    binding.progressBar.gone()
                    it.data?.let { data -> populateTvShow(TvShowMapper.mapResponseToEntity(data)) }
                }
                Status.ERROR -> {
                    binding.progressBar.gone()
                    toast(it.message.toString())
                }
            }
            // for instrumentation test only
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        })
    }

    private fun populateMovie(movieEntity: MovieEntity) {
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
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.imgPhoto)
    }

    private fun populateTvShow(tvShowEntity: TvShowEntity) {
        binding.txtName.text = tvShowEntity.name
        binding.txtYear.text = tvShowEntity.getYearRelease()
        binding.txtRating.text = tvShowEntity.rating.toString()
        binding.txtLanguage.text = getLanguageName(tvShowEntity.lang.toString())
        binding.txtDetailDescription.text = tvShowEntity.overview
        binding.txtReleaseDateTitle.text = resources.getText(R.string.first_air_date)
        binding.txtReleaseDate.text = tvShowEntity.getFormattedDate()

        Glide.with(this)
            .load(imageUrl + tvShowEntity.imagePath)
            .transform(RoundedCorners(20))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.imgPhoto)
    }

    private fun getLanguageName(lang: String): String {
        val loc = Locale(lang)
        return loc.getDisplayLanguage(loc)
    }
}