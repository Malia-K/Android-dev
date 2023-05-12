package com.example.assignment3.Model

import android.text.BoringLayout
import kotlinx.serialization.Serializable


@Serializable
class Book{

    var uid: String = ""
    var id : String = ""
    var title : String = ""
    var description : String = ""
    var price : String = ""
    var timestamp : Long? = null
    var isFavorite :Boolean = false
    var volumeInfo: VolumeInfo = VolumeInfo()

    constructor()


    constructor(
         uid: String ,
         id : String ,
         title : String,
         description : String,
         price : String,
         timestamp : Long,
         isFavorite :Boolean ,
         volumeInfo: VolumeInfo){
        this.uid = uid
        this.id = id
        this.title = title
        this.description = description
        this.price = price
        this.timestamp = timestamp
        this.isFavorite = isFavorite
        this.volumeInfo = volumeInfo
    }
}


@Serializable
class VolumeInfo(
    val title: String,
    val subtitle: String,
    val description: String,
    val imageLinks: ImageLinks? = null,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
) {
    constructor() : this("", "", "", null, emptyList<String>(), "", "" )
}

@Serializable
class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String,
) {
    val httpsThumbnail : String
        get() = thumbnail.replace("http", "https")
}
