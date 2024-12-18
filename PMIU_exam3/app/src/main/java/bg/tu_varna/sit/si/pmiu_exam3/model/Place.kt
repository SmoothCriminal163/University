package bg.tu_varna.sit.si.pmiu_exam3.model

import androidx.annotation.DrawableRes

data class Place(
    @DrawableRes val drawableResourceId: Int,
    var description: String = "new place"
)