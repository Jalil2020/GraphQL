package com.example.graphql.ui.screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.apollographql.apollo3.exception.ApolloException
import com.example.graphql.*
import com.example.graphql.databinding.FragmentDetailsBinding
import com.example.graphql.presenter.DetailsFragmentViewModel
import com.example.graphql.presenter.impl.DetailsFragmentViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args: DetailsFragmentArgs by navArgs()
    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val viewModel: DetailsFragmentViewModel by viewModels<DetailsFragmentViewModelImpl>()

    private var isBooking: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDetails(args.id)
        initBookButtonClick()
        initObservers()
    }

    private fun initBookButtonClick() {
        binding.bookButton.setOnClickListener {
            if (User(requireContext()).getToken().isEmpty()) {
                findNavController().navigate(R.id.loginFragment)
            } else {
                if (!isBooking) {
                    viewModel.setBookTrip(args.id)
                } else {
                    viewModel.setCancelBookTrip(args.id)
                }
                configureButton(!isBooking)
            }
        }
    }

    private fun initObservers() {
        viewModel.getDetailsFlow.onEach {
            binding.itemImage.load(it.mission?.missionPatch)
            binding.itemText.text = it.site
            binding.itemTitle.text = it.rocket?.name

            configureButton(it.isBooked)
        }.launchIn(lifecycleScope)

        viewModel.loadingFlow.onEach {
            binding.progressBar.isVisible = it
        }.launchIn(lifecycleScope)

        viewModel.setBookTripsFlow.onEach {
            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)

        viewModel.setCancelTripFLow.onEach {
            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)
    }

      private fun configureButton(isBooked: Boolean) {
        isBooking = isBooked
        if (isBooked) {
            binding.bookButton.text = resources.getString(R.string.cancel)
        } else {
            binding.bookButton.text = getString(R.string.book_now)
        }
    }

}