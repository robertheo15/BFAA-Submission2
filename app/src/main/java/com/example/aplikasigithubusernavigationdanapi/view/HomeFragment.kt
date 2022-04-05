package com.example.aplikasigithubusernavigationdanapi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubusernavigationdanapi.R
import com.example.aplikasigithubusernavigationdanapi.databinding.FragmentHomeBinding
import com.example.aplikasigithubusernavigationdanapi.model.User
import com.example.aplikasigithubusernavigationdanapi.utils.Helper
import com.example.aplikasigithubusernavigationdanapi.view.adapter.ListUserAdapter
import com.example.aplikasigithubusernavigationdanapi.viewmodel.HomeViewModel
import java.util.ArrayList
import kotlin.system.exitProcess

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()
    private val helper = Helper()
    private var listUser = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        context?.let { searchUsername() }
        homeViewModel.listUser.observe(viewLifecycleOwner) { listUser -> setUser(listUser) }
        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            helper.isLoading(
                it,
                binding.progressBarHome
            )
        }

        val binding = binding.recyclerViewHome
        binding.layoutManager = LinearLayoutManager(activity)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                true
            }
            R.id.exit -> {
                activity?.finish()
                exitProcess(0)
            }
            else -> true
        }
    }

    private fun searchUsername() {
        val searchView = binding.searchViewHome
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                homeViewModel.findUser(query)
                query.let {
                    binding.recyclerViewHome.visibility = View.VISIBLE
                    homeViewModel.findUser(it)
                    setUser(listUser)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun setUser(users: List<User>) {
        val listUser = ArrayList<User>()

        for (user in users) {
            listUser.clear()
            listUser.addAll(users)
        }

        val adapter = ListUserAdapter(listUser)
        binding.recyclerViewHome.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showUser(data)
            }
        })
    }

    private fun showUser(data: User) {
        val moveDataUser = Bundle()
        moveDataUser.putParcelable(EXTRA_USER, data)
        NavHostFragment
            .findNavController(this)
            .navigate(R.id.action_homeFragment_to_userDetailFragment, moveDataUser)
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}


