package id.idham.catalogue.ui.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.idham.catalogue.BuildConfig.imageUrl
import id.idham.catalogue.data.source.local.entity.MovieEntity
import id.idham.catalogue.databinding.ItemsMovieBinding
import id.idham.catalogue.ui.detail.DetailMovieActivity

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var listCourses = ArrayList<MovieEntity>()

    fun setItems(items: List<MovieEntity>?) {
        if (items == null) return
        this.listCourses.clear()
        this.listCourses.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemsAcademyBinding =
            ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = listCourses[position]
        holder.bind(course)
    }

    override fun getItemCount(): Int = listCourses.size


    class ViewHolder(private val binding: ItemsMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {
                tvTitle.text = movie.title
                tvYear.text = movie.getYearRelease()
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.MOVIE_ID, movie.id.toString())
                    intent.putExtra(
                        DetailMovieActivity.MOVIE_TYPE,
                        DetailMovieActivity.MovieType.MOVIE
                    )
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(imageUrl + movie.imagePath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgPhoto)
            }
        }
    }
}