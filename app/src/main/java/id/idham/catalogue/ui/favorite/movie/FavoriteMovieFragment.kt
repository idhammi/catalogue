package id.idham.catalogue.ui.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.idham.catalogue.databinding.FragmentFavoriteMovieBinding

class FavoriteMovieFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}