/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package p_jsocket

import kotlin.js.JsName

/**
 *
 * @author User
 */
@JsName("PictureSet")
val PictureSet: List<String> by lazy {
    listOf(
        //"asf",
        "bmp",
        //"cdw",
        //"cr2",
        //"cs",
        //"cur",
        //"dm",
        //"drv",
        "jpg",
        "jpeg",
        //"gif",
        //"icns",
        //"ico",
        //"max",
        //"mds",
        //"mng",
        "msv",
        //"odt",
        //"ogg",
        //"pct",
        //"pict",
        "png"
        //"pps",
        //"prf",
        //"tex",
        //"tif",
        //"ttf",
        //"xps"
    ).sorted()
}

@JsName("VideoSet")
val VideoSet: List<String> by lazy {
    listOf(
        "3g2",
        "3gp",
        "3gp2",
        "3gpp",
        "asx",
        "avi",
        "dat",
        "f4v",
        "flv",
        "gtp",
        "h264",
        "m4v",
        "mkv",
        "mod",
        "moov",
        "mov",
        "mp4",
        "mpeg",
        "mpg",
        "rm",
        "rmvb",
        "spl",
        "stl",
        "swf",
        "ts",
        "vcd",
        "vid",
        "vob",
        "webm",
        "wm",
        "wmv",
        "yuv"
    ).sorted()
}

@JsName("MusicSet")
val MusicSet: List<String> by lazy {
    listOf(
        "aac",
        "ac3",
        "aif",
        "aiff",
        "amr",
        "aob",
        "ape",
        "awb",
        "bwg",
        "cdr",
        "flac",
        "gpx",
        "ics",
        "iff",
        "m3u",
        "m3u8",
        "m4a",
        "m4b",
        "m4p",
        "m4r",
        "mid",
        "midi",
        "mp3",
        "mpa",
        "mpp",
        "msc",
        "mts",
        "nkc",
        "ps",
        "ra",
        "ram",
        "sdf",
        "sib",
        "sln",
        "srt",
        "temp",
        "vb",
        "wav",
        "wave",
        "wma",
        "wpd",
        "xsb",
        "xwb"
        ).sorted()
}