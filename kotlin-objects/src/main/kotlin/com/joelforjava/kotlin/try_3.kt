package com.joelforjava.kotlin

/**
 * These data classes build on the 'try_2' classes and start to replace the nullable types for a few of the fields.
 * Unfortunately, due to SnakeYAML requiring an empty constructor, we still have to give default values for everything.
 */

data class Band3(val name: String = "", val albums: List<Album3> = emptyList())

data class Album3(val name: String = "",
                  val releaseYear: Int = 0,
                  val label: String = "",
                  val tracks: List<Song3> = emptyList())

data class Song3(val name: String = "")
