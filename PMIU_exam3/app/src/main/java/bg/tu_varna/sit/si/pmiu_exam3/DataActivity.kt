package bg.tu_varna.sit.si.pmiu_exam3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bg.tu_varna.sit.si.pmiu_exam3.data.Datasource
import bg.tu_varna.sit.si.pmiu_exam3.model.Place
import bg.tu_varna.sit.si.pmiu_exam3.ui.theme.PMIU_exam3Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PMIU_exam3Theme {
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

    DataList(
        places = list,
        modifier = modifier
    )

    LaunchedEffect(Unit) {
        while (true) {
            createPlace()
            list.addAll(Datasource.loadPlaces())
        }
    }
}

@Composable
fun DataList(places: List<Place>, modifier: Modifier = Modifier) {
    LazyRow {
        items(places) { place ->
            PlaceCard(
                place = place,
                modifier = modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun PlaceCard(place: Place, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            Image(
                painter = painterResource(place.drawableResourceId),
                contentDescription = place.description,
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxWidth().height(195.dp)
            )
            Text(
                text = place.description,
                style = MaterialTheme.typography.headlineSmall,
                modifier = modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun PlacePreview() {
    PMIU_exam3Theme {
        PlaceCard(Place(R.drawable.image1))
    }
}


