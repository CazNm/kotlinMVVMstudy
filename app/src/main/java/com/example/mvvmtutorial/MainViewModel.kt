package com.example.mvvmtutorial

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MainViewModel(private val repository : MyRepository) : ViewModel() {

    private val _mainModels : MutableLiveData<List<MainModel>> = MutableLiveData()
    val mainModles : LiveData<List<MainModel>> = _mainModels
    val onItemClickEvent : MutableLiveData<MainModel> = MutableLiveData()

    fun loadMainModels() {
        repository.getModels().let {
            _mainModels.postValue(it) //Background Thread 에서 돌아간다
            //_mainModels.value = it // Main Thread 에서 돌아간다. 즉각적으로 반응
        }
    }

    fun onItemClick(position : Int)
    {
        println("click function active")
        _mainModels.value?.getOrNull(position)?.let {
            onItemClickEvent.postValue(it)
        }
    }
}

class MainViewModelFactory (private val repository: MyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel (repository) as T
        }

        throw IllegalArgumentException ("Unknown ViewModel class :: ${modelClass::class.java.simpleName}")
    }
}

interface MyRepository {
    fun getModels() : List<MainModel>
}

class TempMyRepositoryImpl : MyRepository {

    override fun getModels(): List<MainModel> = listOf(
        MainModel(title = "temp title 1"),
        MainModel(title = "temp title 2"),
        MainModel(title = "temp title 3"),
        MainModel(title = "temp title 4"),
        MainModel(title = "temp title 5"),
        MainModel(title = "temp title 6"),
        MainModel(title = "temp title 7"),
        MainModel(title = "temp title 8"),
        MainModel(title = "temp title 9"),
        MainModel(title = "temp title 10")
    )
}