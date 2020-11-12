package com.example.giflightsensor.api.data

data class GiphyGif(val data : Data)

data class Data(val images : Images)

data class Images(val original : OriginalQualityGif)

data class OriginalQualityGif(val url : String)