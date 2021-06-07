package id.idham.catalogue.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.idham.catalogue.data.mapper.TvShowMapper
import id.idham.catalogue.data.source.local.entity.TvShowEntity
import id.idham.catalogue.databinding.FragmentTvShowBinding
import id.idham.catalogue.utils.EspressoIdlingResource
import id.idham.catalogue.utils.enums.Status
import id.idham.catalogue.utils.gone
import id.idham.catalogue.utils.toast
import id.idham.catalogue.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {

    private val viewModel by viewModel<TvShowViewModel>()
    private lateinit var binding: FragmentTvShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        EspressoIdlingResource.increment() // for instrumentation test only
        viewModel.getTvShows()
    }

    private fun observeData() {
        viewModel.dataTvShows.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> binding.progressBar.visible()
                Status.SUCCESS -> {
                    binding.progressBar.gone()
                    it.data?.let { list ->
                        val results = ArrayList<TvShowEntity>()
                        for (data in list) {
                            results.add(TvShowMapper.mapResponseToEntity(data))
                        }
                        setData(results)
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.gone()
                    requireActivity().toast(it.message.toString())
                }
            }
            // for instrumentation test only
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        })
    }

    private fun setData(data: List<TvShowEntity>) {
        val adapter = TvShowAdapter()
        adapter.setItems(data)

        with(binding.rvTvShows) {
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

}