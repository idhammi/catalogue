package id.idham.catalogue.ui.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.idham.catalogue.BuildConfig.imageUrl
import id.idham.catalogue.data.source.local.entity.TvShowEntity
import id.idham.catalogue.databinding.ItemsTvShowBinding
import id.idham.catalogue.ui.detail.DetailMovieActivity

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.ViewHolder>() {
    private val listCourses = ArrayList<TvShowEntity>()

    fun setItems(items: List<TvShowEntity>?) {
        if (items == null) return
        this.listCourses.clear()
        this.listCourses.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemsBookmarkBinding =
            ItemsTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemsBookmarkBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = listCourses[position]
        holder.bind(course)
    }

    override fun getItemCount(): Int = listCourses.size

    inner class ViewHolder(private val binding: ItemsTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity) {
            with(binding) {
                tvTitle.text = tvShow.name
                tvYear.text = tvShow.getYearRelease()
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.MOVIE_ID, tvShow.id.toString())
                    intent.putExtra(
                        DetailMovieActivity.MOVIE_TYPE,
                        DetailMovieActivity.MovieType.TV
                    )
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(imageUrl + tvShow.imagePath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgPhoto)
            }
        }
    }
}