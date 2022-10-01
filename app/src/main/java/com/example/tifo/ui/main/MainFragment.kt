package com.example.tifo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.tifo.data.AppDatabase
import com.example.tifo.databinding.FragmentMainBinding
import com.example.tifo.viewmodels.MainViewModel
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding


    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        val view = binding.root
        val recyclerview = binding.repositoriesRecyclerView


        viewModel.viewModelScope.launch {
            viewModel.repositories.collect { it ->
                val adapter = RepositoriesAdapter(it)

                recyclerview.adapter = adapter
                
                adapter.onItemCLick = {


                    val action = MainFragmentDirections.actionMainFragmentToDetailedFragment(it)
                    findNavController().navigate(action)

                }
            }
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    viewModel.executeCall(query)
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })


        return view
    }
}