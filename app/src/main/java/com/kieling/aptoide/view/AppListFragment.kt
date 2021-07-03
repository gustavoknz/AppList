package com.kieling.aptoide.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kieling.aptoide.MainActivity
import com.kieling.aptoide.dagger.DaggerFragmentComponent
import com.kieling.aptoide.dagger.FragmentComponent
import com.kieling.aptoide.databinding.FragmentAppListBinding
import com.kieling.aptoide.repository.AptoideRepository
import com.kieling.aptoide.viewmodel.AppListViewModel
import javax.inject.Inject

class AppListFragment : Fragment() {

    @Inject
    lateinit var aptoideRepository: AptoideRepository
    private var fragmentComponent: FragmentComponent? = null
    private var _binding: FragmentAppListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppListViewModel by lazy {
        requireNotNull(activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, AppListViewModel.Factory(aptoideRepository))
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
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            if (!dataState) {
                AlertDialog.Builder(requireActivity())
                    .setTitle("Error")
                    .setMessage("Error loading data")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
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