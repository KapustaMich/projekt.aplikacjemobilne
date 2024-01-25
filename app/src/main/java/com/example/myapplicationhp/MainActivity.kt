package com.example.myapplicationhp

import MainViewModel
import android.content.Intent
import androidx.compose.ui.unit.sp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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


class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getData()
        setContent {
            MyApplicationhpTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView(viewModel =viewModel, onClick = {id -> navigateToDetails(id)})
                }
            }
        }


    }
    fun navigateToDetails(id: String) {
        val detailsIntent = Intent(this, DetailsActivity::class.java)
        detailsIntent.putExtra("id",id)
        startActivity(detailsIntent)
    }
}//glowny widok render

//Griffindor
val gFirst = Color(0xFFB40000)
val gSecond = Color(0xFFE09C0A)
//Slitherin
val sFirst = Color(0xFF2E761C)
val sSecond = Color(0xFFCCCCCC)
//Ravenclav
val rFirst = Color(0xFF285581)
val rSecond = Color(0xFFB86623)
//Hufflepuff
val hFirst = Color(0xFFE58E0B)
val hSecond = Color(0xFF1F1E19)

enum class House {
    Gryffindor, Slytherin, Ravenclaw, Hufflepuff
}

fun getHouseColors(house: House): Pair<Color, Color> {
    return if(house == House.Gryffindor)
        Pair(gFirst, gSecond)
    else if(house == House.Slytherin)
        Pair(sFirst, sSecond)
    else if(house == House.Ravenclaw)
        Pair(rFirst, rSecond)
    else
        Pair(hFirst, hSecond)
}

fun houseFromString(house: String): House {
    return if(house == "Gryffindor")
        House.Gryffindor
    else if(house == "Slytherin")
        House.Slytherin
    else if(house == "Ravenclaw")
        House.Ravenclaw
    else
        House.Hufflepuff
}

@Composable//renderowanie jednego wierszu
fun Tile(id: String, name: String, house: House, image:String, onClick: (String) -> Unit) {
    val (houseMainColor, houseSecondColor) = getHouseColors(house)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(houseMainColor, shape = RoundedCornerShape(0.dp))
            .padding(0.dp)
            .padding(start = 20.dp)
            .width(400.dp)
            .height(100.dp)
            .clickable { onClick.invoke(id) }
    ) {




        AsyncImage(
            model = image,
            contentDescription = "portret",
            placeholder = painterResource(
                id = R.drawable.harrypotter
            ),
        )
        Spacer(modifier = Modifier.width(80.dp))
        Column {
            Text(text = name, color = Color.White, fontSize = 25.sp)
            Text(text = house.name, color = Color.White, fontSize = 25.sp)


        }
    }
}
@Composable
fun LoadingView() {
    Column {
        Text(
            text = "Ładowanie. Prosze czekac",
            color = Color.Black
        )
    }
}

@Composable
fun ErrorView() {
    Column {
        Text(
            text = "Coś nie pykło",
            color = Color.Red
        )
    }
}

@Composable
fun MainView(viewModel: MainViewModel, onClick: (String) -> Unit) {
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
                ?.let { TileView(characters = it, onClick={id -> onClick.invoke(id)}) }
        }
    }

}
@Composable
fun TileView(characters: List<Character>, onClick: (String) -> Unit){
    if (characters.isNotEmpty()){
        LazyColumn {
            items(characters) { character ->
                Tile(character.id, name = character.name, houseFromString(character.house) , image=character.image, onClick={id -> onClick.invoke(id)})
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationhpTheme {
    }
}