package com.altaie.triviagame.data.response.category

import com.google.gson.annotations.SerializedName

data class NationalCategoryResponse(
    @SerializedName("trivia_categories") val categories: List<Category>
)
