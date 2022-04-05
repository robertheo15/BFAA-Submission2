package com.example.aplikasigithubusernavigationdanapi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubusernavigationdanapi.databinding.FragmentFollowersBinding
import com.example.aplikasigithubusernavigationdanapi.model.User
import com.example.aplikasigithubusernavigationdanapi.utils.Helper
import com.example.aplikasigithubusernavigationdanapi.view.adapter.FollowersAdapter
import com.example.aplikasigithubusernavigationdanapi.viewmodel.FollowersViewModel

class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private val followersViewModel by viewModels<FollowersViewModel>()
    private val helper = Helper()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            helper.isLoading(it, binding.progressBarFollowers)
        }
        followersViewModel.listUser.observe(viewLifecycleOwner) { followerList ->
            setRecyclerData(followerList)
        }
        followersViewModel.getFollowers(
            arguments?.getString(UserDetailFragment.EXTRA_USERNAME).toString()
        )
    }


    private fun setRecyclerData(list: List<User>) {

        with(binding) {
            val followersList = ArrayList<User>()
            for (user in list) {
                followersList.clear()
                followersList.addAll(list)
            }

            rvFollowers.layoutManager = LinearLayoutManager(context)
            val followersAdapter = FollowersAdapter(followersList)
            rvFollowers.adapter = followersAdapter
        }
    }
}