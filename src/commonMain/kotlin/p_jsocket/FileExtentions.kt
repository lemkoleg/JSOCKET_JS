/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package p_jsocket

//import kotlin.js.JsName

/**
 *
 * @author User
 */
//@JsName("PictureSet")
val PictureSet: List<String> by lazy {
    listOf(
        "bmp",
        "jpg",
        "jpeg",
        "webp",
        "png",
        "tif"
    ).sorted()
}

//@JsName("VideoSet")
val VideoSet: List<String> by lazy {
    listOf(
        "mp4",
        "mpeg",
        "mov",
        "webm",
        "3gp"
    ).sorted()
}

//@JsName("MusicSet")
val MusicSet: List<String> by lazy {
    listOf(
        "mp3"
        ).sorted()
}

//@JsName("GifSet")
val GifSet: List<String> by lazy {
    listOf(
        "gif"
    ).sorted()
}

//@JsName("VoiceSet")
val VoiceSet: List<String> by lazy {
    listOf(
        "m4a",
        "opus"
    ).sorted()
}