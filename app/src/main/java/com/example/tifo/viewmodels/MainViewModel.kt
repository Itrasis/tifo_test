package com.example.tifo.viewmodels


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tifo.data.Repository
import com.example.tifo.data.getDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.tifo.repository.RepositoryRepository


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var _repositories  : MutableStateFlow<List<Repository>> = MutableStateFlow(listOf())
    val repositories  = _repositories.asStateFlow()

    private val repositoryRepository = RepositoryRepository(getDatabase(application))


    fun executeCall(name : String) {

        viewModelScope.launch {
            Dispatchers.IO {

                try {
                  repositoryRepository.fetchRepositories(name)
                } catch (e: Exception) {
                    Log.v("CALL", "error : $e")
                }

                _repositories.value = repositoryRepository.findName(name)

            }
        }
    }


}