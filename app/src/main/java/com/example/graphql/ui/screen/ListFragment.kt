package com.example.graphql.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.graphql.*
import com.example.graphql.databinding.FragmentListBinding
import com.example.graphql.presenter.ListFragmentViewModel
import com.example.graphql.presenter.impl.ListFragmentViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private val binding by viewBinding(FragmentListBinding::bind)
    private val viewModel: ListFragmentViewModel by viewModels<ListFragmentViewModelImpl>()
    private val adapter = MyAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        viewModel.getLaunchList()
        initObserver()
    }

    private fun initRecyclerView() {
        binding.list.adapter = adapter
        adapter.onItemClicked = { launch ->
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToDetailsFragment(
                    launch.id
                )
            )
        }
    }

    private fun initObserver() {
        viewModel.launchListFlow.onEach {
            adapter.submitList(it as ArrayList<LaunchListQuery.Launch>)
            binding.progressBar.visibility = View.GONE
        }.launchIn(lifecycleScope)

        viewModel.loadingListFlow.onEach {
            binding.progressBar.isVisible = it
        }.launchIn(lifecycleScope)
    }
}