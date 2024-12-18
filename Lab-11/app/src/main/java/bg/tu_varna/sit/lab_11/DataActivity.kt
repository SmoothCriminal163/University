package bg.tu_varna.sit.lab_11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bg.tu_varna.sit.lab_11.data.Datasource
import bg.tu_varna.sit.lab_11.model.Place
import bg.tu_varna.sit.lab_11.ui.theme.Lab11Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class DataActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    Datasource.add(Place(R.drawable.image1, "Place 1"))
    setContent {
      Lab11Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          DataApp(
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

suspend fun createPlace() {
  delay(2000)
  Datasource.add(Datasource.generatePlace())
}

@Composable
fun DataApp(modifier: Modifier = Modifier) {

  val list = remember { mutableStateListOf<Place>() }
  val scope = rememberCoroutineScope()
  var newDescription by remember { mutableStateOf("") }

  val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Cyan, Color.Magenta, Color.Yellow)
  var cardBackgroundColor by remember { mutableStateOf(Color.White) }

  LaunchedEffect(Unit) {
    while (true) {
      delay(2000)
      cardBackgroundColor = colors.random()
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .then(modifier),
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    DataList(
      places = list,
      modifier = modifier,
      backgroundColor = cardBackgroundColor
    )

    TextField(
      value = newDescription,
      onValueChange = { newDescription = it },
      label = { Text("New Description") },
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    )

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      Button(
        onClick = {
          scope.launch {
            delay(2000)
            val newPlace = Datasource.generatePlace()
            list.add(newPlace)
          }
        },
        modifier = Modifier.weight(1f).padding(end = 8.dp)
      ) {
        Text("Add New Place")
      }

      Button(
        onClick = {
          scope.launch {
            delay(2000)
            if (list.isNotEmpty()) {
              list.removeLast()
            }
          }
        },
        modifier = Modifier.weight(1f).padding(start = 8.dp),
        enabled = list.isNotEmpty()
      ) {
        Text("Remove Last Place")
      }
      Button(
        onClick = {
          scope.launch {
            delay(2000)
            if (list.isNotEmpty() && newDescription.isNotEmpty()) {
              val randomIndex = Random.nextInt(list.size)
              list[randomIndex] = list[randomIndex].copy(description = newDescription)
            }
          }
        },
        modifier = Modifier.weight(1f).padding(start = 8.dp),
        enabled = list.isNotEmpty() && newDescription.isNotEmpty()
      ) {
        Text("Update Random Place Description")
      }
    }
  }
}

@Composable
fun DataList(places: List<Place>, modifier: Modifier, backgroundColor: Color) {
  LazyRow {
    items(places) { place ->
      PlaceCard(
        place = place,
        modifier = modifier.padding(8.dp),
        backgroundColor = backgroundColor
      )
    }
  }
}

@Composable
fun PlaceCard(place: Place, modifier: Modifier, backgroundColor: Color) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(8.dp),
    colors = CardDefaults.cardColors(containerColor = backgroundColor)
  ) {
    Column {
      Image(
        painter = painterResource(place.drawableResourceId),
        contentDescription = place.description,
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .fillMaxWidth()
          .height(195.dp)
      )
      Text(
        text = place.description,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(16.dp)
      )
    }
  }
}

@Preview
@Composable
fun PlacePreview() {
  Lab11Theme {
    PlaceCard(Place(R.drawable.image1), modifier = Modifier, Color.Red)
  }
}
