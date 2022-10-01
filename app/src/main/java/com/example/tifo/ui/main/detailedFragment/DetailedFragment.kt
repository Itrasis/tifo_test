package com.example.tifo.ui.main.detailedFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tifo.R
import com.example.tifo.data.DataModel
import com.example.tifo.data.Repository
import com.example.tifo.databinding.FragmentDetailedBinding
import com.example.tifo.ui.main.RepositoriesAdapter
import kotlinx.coroutines.launch

class DetailedFragment : Fragment() {

    private lateinit var binding: FragmentDetailedBinding
    val args : DetailedFragmentArgs  by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailedBinding.inflate(inflater, container, false)

        val view = binding.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = args.repository

        val recyclerview = binding.branchesView

        try {
            val joinedList = ArrayList<DataModel>()

            joinedList.add(DataModel.Header("Branches"))
            joinedList.addAll(repository.branches!!.map { DataModel.Branch(it) })
            joinedList.add(DataModel.Header("Contributors"))
            joinedList.addAll(repository.contributors!!)


            val adapter = context?.let { DataAdapter(joinedList, it) }

            recyclerview.adapter = adapter
        } catch (e : Exception) {
            Log.v("Error", "error $e")
        }

        println(repository)
    }
}