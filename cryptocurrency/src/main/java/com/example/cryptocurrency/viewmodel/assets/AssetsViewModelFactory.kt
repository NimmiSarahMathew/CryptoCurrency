package com.example.cryptocurrency.viewmodel.assets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cryptocurrency.data.repository.AssetsRepository
import javax.inject.Inject

class AssetsViewModelFactory @Inject constructor(private val repo: AssetsRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AssetsViewModel(repo) as T
    }
}