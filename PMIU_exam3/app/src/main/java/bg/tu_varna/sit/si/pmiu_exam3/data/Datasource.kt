package bg.tu_varna.sit.si.pmiu_exam3.data

import bg.tu_varna.sit.si.pmiu_exam3.R
import bg.tu_varna.sit.si.pmiu_exam3.model.Place

object Datasource {
    private val places: MutableList<Place> = mutableListOf()

    fun add(place: Place) {
        places.add(place)
    }

    fun loadPlaces(): List<Place> {
        return places
    }

    private fun getDrawableResource() = listOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            R.drawable.image9,
            R.drawable.image10,
        ).random()

    fun generatePlace(): Place {
        return Place(getDrawableResource(), "Palce ${(1..10).random()}")
    }

}