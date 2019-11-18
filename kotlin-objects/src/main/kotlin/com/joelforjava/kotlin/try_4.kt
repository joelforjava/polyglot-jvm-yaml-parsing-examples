package com.joelforjava.kotlin

/**
 * These data classes build on the 'try_3' classes by removing all default values.
 *
 */

data class Band4(val name: String, val albums: List<Album4>)

data class Album4(val name: String,
                  val releaseYear: Int,
                  val label: String,
                  val tracks: List<Song4>)

data class Song4(val name: String)

