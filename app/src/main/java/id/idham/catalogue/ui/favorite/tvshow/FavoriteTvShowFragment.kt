package id.idham.catalogue.ui.favorite.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.idham.catalogue.databinding.FragmentFavoriteTvShowBinding

class FavoriteTvShowFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteTvShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteTvShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}