package com.example.aplikasigithubusernavigationdanapi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.aplikasigithubusernavigationdanapi.R
import com.example.aplikasigithubusernavigationdanapi.databinding.FragmentUserDetailBinding
import com.example.aplikasigithubusernavigationdanapi.model.User
import com.example.aplikasigithubusernavigationdanapi.utils.Helper
import com.example.aplikasigithubusernavigationdanapi.view.adapter.SectionsPagerAdapter
import com.example.aplikasigithubusernavigationdanapi.viewmodel.DetailUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var username: String
    private val detailUserViewModel by viewModels<DetailUserViewModel>()
    private val helper = Helper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setUsername()
        setViewPager()
        detailUserViewModel.getDetailUser(username)
        detailUserViewModel.user.observe(viewLifecycleOwner) { user -> setUser(user) }
        detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
            helper.isLoading(
                it,
                binding.progressBarGroup
            )
        }
    }

    private fun setUsername() {
        val user = arguments?.getParcelable<User>(HomeFragment.EXTRA_USER) as User
        this.username = user.username.toString()
    }

    private fun setUser(user: User) {
        binding.userName.text = StringBuilder("@").append(user.username)
        binding.name.text = user.name
        binding.company.text = user.company ?: "-----"
        binding.location.text = user.location ?: "-----"
        binding.repository.text = StringBuilder(user.repository.toString()).append(" Repository")
        binding.followers.text = StringBuilder(user.followers.toString()).append(" Followers")
        binding.following.text = StringBuilder(user.following.toString()).append(" Following")
        Glide.with(this)
            .load(user.avatar)
            .into(binding.imageProfile)
    }

    private fun setViewPager() {

        val viewPager: ViewPager2? = view?.findViewById(R.id.view_pager)
        val tabs: TabLayout? = view?.findViewById(R.id.tabs)

        val username = Bundle()
        username.putString(EXTRA_USERNAME, this.username)

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager, lifecycle, username)

        viewPager?.adapter = sectionsPagerAdapter
        if (viewPager != null && tabs != null) {
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}
