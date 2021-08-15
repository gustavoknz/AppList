package com.kieling.applist.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kieling.applist.R
import com.kieling.applist.dagger.DaggerFragmentComponent
import com.kieling.applist.dagger.FragmentComponent
import com.kieling.applist.databinding.FragmentAppListBinding
import com.kieling.applist.extension.TAG
import com.kieling.applist.repository.ApplistRepository
import com.kieling.applist.viewmodel.AppListViewModel
import javax.inject.Inject

class AppListFragment : Fragment() {

    @Inject
    lateinit var applistRepository: ApplistRepository
    private var fragmentComponent: FragmentComponent? = null
    private var _binding: FragmentAppListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppListViewModel by lazy {
        requireNotNull(activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, AppListViewModel.Factory(applistRepository))
            .get(AppListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        getNewFragmentComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppListBinding.inflate(inflater, container, false)
        binding.listProgressBar.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")
        val adapter = AppAdapter()
        binding.listRecyclerView.adapter = adapter
        binding.listRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), resources.getInteger(R.integer.layout_rows))
        viewModel.appList.observe(viewLifecycleOwner, { apps ->
            apps.apply {
                binding.listProgressBar.visibility = View.GONE
                adapter.appList = apps
            }
        })
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            if (!dataState) {
                Toast.makeText(context, "Error loading data", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getNewFragmentComponent(): FragmentComponent {
        if (fragmentComponent == null) {
            fragmentComponent = DaggerFragmentComponent.builder()
                .appComponent((requireActivity() as MainActivity).appComponent)
                .build()
        }
        return fragmentComponent as FragmentComponent
    }
}