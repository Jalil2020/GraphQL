package com.example.graphql.ui.screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.apollographql.apollo3.exception.ApolloException
import com.example.graphql.*
import com.example.graphql.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args: DetailsFragmentArgs by navArgs()
    private val binding by viewBinding(FragmentDetailsBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        lifecycleScope.launchWhenResumed {
            val response = try {
                apolloClient.query(LaunchDetailsQuery(args.id)).execute()

            } catch (e: ApolloException) {
                Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                return@launchWhenResumed
            }
            binding.progressBar.visibility = View.GONE
            binding.itemImage.load(response.data?.launch?.mission?.missionPatch)
            binding.itemText.text = response.data?.launch?.site
            binding.itemTitle.text = response.data?.launch?.rocket?.name

            configureButton(response.data?.launch!!.isBooked)
        }


    }

    private fun configureButton(isBooked: Boolean) {

        if (isBooked) {
            binding.bookButton.text = resources.getString(R.string.cancel)
        } else {
            binding.bookButton.text = getString(R.string.book_now)
        }

        binding.bookButton.setOnClickListener {
            if (User(requireContext()).getToken()
                    .isEmpty()
            ) {
                findNavController().navigate(
                    R.id.loginFragment
                )
                return@setOnClickListener
            } else {
                lifecycleScope.launchWhenResumed {
                    val response = try {
                        apolloClient.mutation(MutationBookMutation(listOf(args.id))).execute()
                    } catch (e: Exception) {
                        return@launchWhenResumed
                    }

                    if (!response.data?.bookTrips!!.success) Toast.makeText(
                        requireContext(),
                        "Error",
                        Toast.LENGTH_SHORT
                    ).show()
                    else Toast.makeText(
                        requireContext(),
                        "Success ${response.data!!.bookTrips.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}