package com.example.mvvmtutorial

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel> { MainViewModelFactory(TempMyRepositoryImpl()) }
//    private val viewModel by viewModels<MainViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Text("this is test")
//            val clickedModel = viewModel.onItemClickEvent.observeAsState().value

            MainModelList(models = viewModel.mainModels.observeAsState().value) {
                println(it)
            }

//            if (clickedModel != null) {
//                ComposableToast(clickedModel.title)
//            }
        }

       viewModel.getMainModels()
    }
}

@Composable
fun MainModelList(models: List<MainModel>?, onItemClick : (Int) -> Unit = {} ){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

       if(models != null)
       {
           itemsIndexed(models) {
                   index, item -> MainModelListItem(model = item) { onItemClick(index) }
           }
       }
    }
}

@Composable
fun MainModelListItem(model : MainModel , onClick : () -> Unit){
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        backgroundColor = Color.White,
        contentColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        Text(text = model.title, style = MaterialTheme.typography.h3)
    }
}

@Composable
fun ComposableToast(message: String) {
    Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
}