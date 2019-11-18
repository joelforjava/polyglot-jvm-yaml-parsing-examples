package com.joelforjava.kotlin

/**
 * These data classes all contain nullable types for each field and also use vars rather than vals.
 */

data class Band(var name: String? = null, var albums: List<Album>? = null)

data class Album(var name: String? = null,
                 var releaseYear: Int? = null,
                 var label: String? = null,
                 var tracks: List<Song>? = null)

data class Song(var name: String? = null)
