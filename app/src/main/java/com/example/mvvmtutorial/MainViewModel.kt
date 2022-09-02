package com.example.mvvmtutorial

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(private val repository : MyRepository) : ViewModel() {

    private var _mainModels = MutableLiveData<List<MainModel>>()
    var mainModels : LiveData<List<MainModel>> = _mainModels

    val onItemClickEvent : MutableLiveData<MainModel> = MutableLiveData()

   private fun loadMainModels() : Flow<List<MainModel>> = flow {
        emit(repository.getModels())
    }

    fun getMainModels() {
        viewModelScope.launch {
            loadMainModels().collect {
                _mainModels.value = it
            }
        }
    }

    fun onItemClick(position : Int)
    {
        println("click function active")
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