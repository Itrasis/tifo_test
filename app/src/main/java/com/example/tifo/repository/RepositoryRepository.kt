package com.example.tifo.repository

import com.example.tifo.api.GitHubApi
import com.example.tifo.api.ResponseRepositoryClass
import com.example.tifo.data.AppDatabase
import com.example.tifo.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class RepositoryRepository(private val database : AppDatabase) {

    fun findName(name : String) : List<Repository> {
        return database.repositoryDao().findByName(name)
    }

    private suspend fun fetchBranchesAndContributors(
        repositoriesFromApi: List<ResponseRepositoryClass>,
        coroutineScope: CoroutineScope
    )  {

        val repositories : List<Repository> = repositoriesFromApi.map {

            coroutineScope.async {
                println(it)

                val branches = GitHubApi.apiService.getBranches(it.full_name)
                val contributors = GitHubApi.apiService.getContributors(it.full_name)

                if (branches.isSuccessful && contributors.isSuccessful &&
                    branches.body() != null && contributors.body() != null
                ) {

                    val contentBranches = branches.body()
                    val contentContributors = contributors.body()

                    Repository(
                        id = it.id,
                        full_name = it.full_name,
                        description = it.description,
                        language = it.language,
                        stargazers_count = it.stargazers_count,
                        contributors = contentContributors,
                        branches = contentBranches!!.map{it.name}
                    )
                } else {
                    Repository(
                        id = it.id,
                        full_name = it.full_name,
                        description = it.description,
                        language = it.language,
                        stargazers_count = it.stargazers_count,
                        contributors = listOf(),
                        branches = listOf()
                    )
                }
            }
        }.map { it.await() }

        database.repositoryDao().insertAll(repositories)

    }

    suspend fun fetchRepositories(name : String) {
        withContext(Dispatchers.IO) {
            val response = GitHubApi.apiService.listRepos(name, 3)

            if (response.isSuccessful && response.body() != null) {
                val content = response.body()

                if (content != null) fetchBranchesAndContributors(content.items, this)
            }
        }
    }
}