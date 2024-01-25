package com.example.myapplicationhp

import DetailViewModel
import MainViewModel
import androidx.compose.ui.unit.sp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplicationhp.ui.theme.MyApplicationhpTheme


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import coil.compose.AsyncImage
import com.example.myapplicationhp.repository.Character


class DetailsActivity : ComponentActivity() {
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getStringExtra("id") ?: throw Error("Brakuje id")

        viewModel.getData(id)
        setContent {
            MyApplicationhpTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DetailView(viewModel =viewModel)
                }
            }
        }
    }
}



@Composable//renderowanie jednego wierszu
fun Detail(character: Character) {
    Column(
        modifier = Modifier
            .padding(0.dp)
            .padding(start = 20.dp)
    ) {




        AsyncImage(
            model = character.image,
            contentDescription = "portret",
            placeholder = painterResource(
                id = R.drawable.harrypotter
            ),
        )
        Column {
            Row {
                Text(text = "name: ", color = Color.Gray, fontSize = 25.sp)
                Text(text = character.name, color = Color.Black, fontSize = 25.sp)
            }
            Row {
                Text(text = "house: ", color = Color.Gray, fontSize = 25.sp)
                Text(text = character.house, color = Color.Black, fontSize = 25.sp)
            }
            Row {
                Text(text = "actor: ", color = Color.Gray, fontSize = 25.sp)
                Text(text = character.actor, color = Color.Black, fontSize = 25.sp)
            }
            Row {
                Text(text = "Date of Birth: ", color = Color.Gray, fontSize = 25.sp)
                Text(text = character.dateOfBirth, color = Color.Black, fontSize = 25.sp)
            }
            Row {
                Text(text = "Patronus: ", color = Color.Gray, fontSize = 25.sp)
                Text(text = character.patronus, color = Color.Black, fontSize = 25.sp)
            }
            Row {
                Text(text = "Eye colour: ", color = Color.Gray, fontSize = 25.sp)
                Text(text = character.eyeColour, color = Color.Black, fontSize = 25.sp)
            }



        }
    }
}



@Composable
fun DetailView(viewModel: DetailViewModel, modifier: Modifier = Modifier) {
    val uiState by viewModel.immutableCharacterData.observeAsState(UIState())

    when {
        uiState.isLoading -> {
            LoadingView()
        }
        uiState.error != null -> {
            ErrorView()
        }
        uiState.data != null -> {
            uiState.data
                ?.let { TileDetailView(characters = it) }
        }
    }

}
@Composable
fun TileDetailView(characters: List<Character>){
    if (characters.isNotEmpty()){
        LazyColumn {
            items(characters) { character ->
                Detail(character)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

}
