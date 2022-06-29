package com.example.graphql.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private val binding by viewBinding(FragmentListBinding::bind)
    private val viewModel: ListFragmentViewModel by viewModels<ListFragmentViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MyAdapter()

        binding.list.adapter = adapter

        viewModel.getLaunchList().observe(this) {
            adapter.submitList(it as ArrayList<LaunchListQuery.Launch>)
            binding.progressBar.visibility = View.GONE
        }

        adapter.onItemClicked = { launch ->
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToDetailsFragment(
                    launch.id
                )
            )
        }
    }
}