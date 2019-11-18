package com.joelforjava.kotlin

/**
 * These data classes all retain the nullable types for each field but vals replace the vars.
 * In order for these classes to be usable by SnakeYAML, at the very least you'll need to
 * set bean access to FIELD rather than DEFAULT, which is reflected in the parseConfig2 function.
 */

data class Band2(val name: String? = null, val albums: List<Album2>? = null)

data class Album2(val name: String? = null,
                 val releaseYear: Int? = null,
                 val label: String? = null,
                 val tracks: List<Song2>? = null)

data class Song2(val name: String? = null)
