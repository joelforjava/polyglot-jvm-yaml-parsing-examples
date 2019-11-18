package com.joelforjava.kotlin

import org.yaml.snakeyaml.constructor.ConstructorException
import org.yaml.snakeyaml.error.YAMLException
import kotlin.test.*

class SnakeYamlParsingTest {

    /**
     * Try 1 tests
     */

    @Test
    fun `original parseYamlAs function can load YAML into initial Band classes`() {
        val fileUrl = "band_1.yaml"
        val band = parseYamlAs(fileUrl, Band::class.java)
        assertNotNull(band)
        assertEquals(band.name, "Fleetwood Mac")
        assertEquals(band.albums!!.size, 2)
        assertEquals(band.albums?.get(0)!!.tracks!!.size, 11)
        assertEquals(band.albums?.get(0)!!.tracks?.get(0)!!.name, "Second Hand News")
    }

    /**
     * Try 2 tests
     */

    @Test
    fun `original parseYamlAs function cannot load YAML into Band classes that use vals and null defaults`() {
        val fileUrl = "band_1.yaml"
        assertFailsWith<YAMLException> { parseYamlAs(fileUrl, Band2::class.java) }
    }

    @Test
    fun `parseYamlAsUsingFieldAccess function can load YAML into Band classes that use vals`() {
        val fileUrl = "band_2.yaml"
        val band = parseYamlAsUsingFieldAccess(fileUrl, Band2::class.java)
        assertNotNull(band)
        assertEquals(band.name, "Blur")
        assertEquals(band.albums!!.size, 1)
        assertEquals(band.albums?.get(0)!!.tracks!!.size, 14)
        assertEquals(band.albums?.get(0)!!.tracks?.get(1)!!.name, "Song 2")
    }

    /**
     * Try 3 tests
     */

    @Test
    fun `original parseYamlAs function cannot load YAML into Band classes that use vals and non-null defaults`() {
        val fileUrl = "band_1.yaml"
        assertFailsWith<YAMLException> { parseYamlAs(fileUrl, Band3::class.java) }
    }

    @Test
    fun `parseYamlAsUsingFieldAccess function can load YAML into Band classes that use vals and non-null defaults`() {
        val fileUrl = "band_1.yaml"
        val band = parseYamlAsUsingFieldAccess(fileUrl, Band3::class.java)
        assertNotNull(band)
        assertEquals(band.name, "Fleetwood Mac")
        assertEquals(band.albums.size, 2)
        assertEquals(band.albums[0].tracks.size, 11)
        assertEquals(band.albums[0].tracks[0].name, "Second Hand News")
    }

    @Test
    fun `loaded objects have expected defaults for empty data when non-null defaults present when Band classes have non-null defaults`() {
        val fileUrl = "band_3.yaml"
        val band = parseYamlAsUsingFieldAccess(fileUrl, Band3::class.java)
        assertNotNull(band)
        assertEquals(band.name, "The Who")
        assertEquals(band.albums.size, 4)
        assertNotNull(band.albums[0].tracks)
        assertEquals(band.albums[0].tracks.size, 0)
    }

    /**
     * Try 4 (Immutable) tests
     */

    @Test
    fun `original parseYamlAs function cannot load YAML into Band classes that use vals with no optional types and no defaults`() {
        val fileUrl = "band_1.yaml"
        assertFailsWith<YAMLException> { parseYamlAs(fileUrl, Band4::class.java) }
    }

    @Test
    fun `parseYamlAsUsingFieldAccess function cannot load YAML with root class type info into Band classes that use vals with no optional types and no defaults`() {
        val fileUrl = "band_4.yaml"
        assertFailsWith<YAMLException> { parseYamlAsUsingFieldAccess(fileUrl, Band4::class.java) }
    }

    @Test
    fun `updated parseYaml cannot load YAML with root class type info into Band classes that use vals with no optional types and no defaults`() {
        val fileUrl = "band_4.yaml"
        assertFailsWith<ConstructorException> { parseYaml<Band4>(fileUrl) }
    }

    /**
     * Try 4 Song tests
     */

    @Test
    fun `parseYaml can load song into immutable class when class type info is in YAML file and no field names are present`() {
        val fileUrl = "song_4_no_field_names.yaml"
        val song: Song4? = parseYaml<Song4>(fileUrl)
        assertNotNull(song)
        assertEquals(song.name, "Stairway to Heaven")
    }

    @Test
    fun `parseYaml cannot load song into immutable class when class type info is in YAML file and field names are present`() {
        val fileUrl = "song_4_with_field_names.yaml"
        assertFailsWith<YAMLException> { parseYaml<Song4>(fileUrl) }
    }

    @Test
    fun `parseYaml can load song list into immutable class when class type info is in YAML file and no field names are present`() {
        val fileUrl = "song_4_list_no_field_names.yaml"
        val songs: List<Song4>? = parseYaml<List<Song4>>(fileUrl)
        assertNotNull(songs)
        assertEquals(songs.size, 2)
        assertEquals(songs[0].name, "Stairway to Heaven")
        assertEquals(songs[1].name, "Black Dog")
    }

    /**
     * Try 4 Album tests
     */

    @Test
    fun `parseYaml cannot load album into immutable class when class type info is in YAML and field names are present`() {
        val fileUrl = "album_with_field_names.yaml"
        assertFailsWith<YAMLException> { parseYaml<Album4>(fileUrl) }
    }

    @Test
    fun `parseYaml can load album into immutable class when class type info is in YAML and no field names are present`() {
        val fileUrl = "album_with_no_field_names.yaml"
        val album: Album4? = parseYaml<Album4>(fileUrl)
        assertNotNull(album)
        assertEquals(album.name, "Houses of the Holy")
        assertEquals(album.tracks.size, 2)
    }

    @Test
    fun `parseYaml cannot load album into immutable class when class type info is in YAML and field names are present and no songs are present`() {
        val fileUrl = "no_field_names/album_4_no_songs.yaml"
        assertFailsWith<YAMLException> { parseYaml<Album4>(fileUrl) }
    }

    @Test
    fun `parseYaml cannot load album into immutable class when class type info is in YAML and field names are present and with an empty song list`() {
        val fileUrl = "no_field_names/album_4_with_empty_songs.yaml"
        val album: Album4? = parseYaml<Album4>(fileUrl)
        assertNotNull(album)
        assertEquals(album.name, "Presence")
        assertEquals(album.releaseYear, 1976)
        assertEquals(album.tracks.size, 0)
    }

    @Test
    fun `parseYaml cannot load album into immutable class when the YAML is an associative array`() {
        val fileUrl = "album_4_with_assoc_array.yaml"
        assertFailsWith<YAMLException> { parseYaml<Album4>(fileUrl) }
    }

    @Test
    fun `parseYaml cannot load album into immutable class when all custom class type info is in YAML and field names are present`() {
        val fileUrl = "album_4_with_field_names_and_track_list.yaml"
        assertFailsWith<YAMLException> { parseYaml<Album4>(fileUrl) }
    }

    @Test
    fun `parseYaml cannot load album into immutable class when fields are out of order`() {
        val fileUrl = "no_field_names/album_4_props_ooo.yaml"
        assertFailsWith<YAMLException> { parseYaml<Album4>(fileUrl) }
    }

    /**
     * Try 4 Band tests
     */

    @Test
    fun `parseYaml can load band into immutable class when class type info is in YAML and no field names are present and an empty list for songs`() {
        val fileUrl = "band_4_no_field_names_empty_songs.yaml"
        val band: Band4? = parseYaml<Band4>(fileUrl)
        assertNotNull(band)
        assertEquals(band.name, "Led Zeppelin")
        assertEquals(band.albums[0].tracks.size, 0)
    }

    @Test
    fun `parseYaml cannot load band into immutable classes when class type info is in YAML and no field names are present and no songs are present`() {
        val fileUrl = "band_4_no_field_names_no_songs.yaml"
        assertFailsWith<YAMLException> { parseYaml<Band4>(fileUrl) }
    }

    @Test
    fun `parseYaml can load band into immutable classes when class type info is in YAML and no field names are present and songs exist with type info`() {
        val fileUrl = "band_4_no_field_names_with_songs.yaml"
        val band: Band4? = parseYaml<Band4>(fileUrl)
        assertNotNull(band)
        assertEquals(band.name, "Led Zeppelin")
        assertEquals(band.albums[0].tracks.size, 2)
    }

    @Test
    fun `parseYaml can incorrectly load band into immutable classes when class type info is in YAML and no field names are prsent and songs exist with type info and properties are out of order`() {
        val fileUrl = "band_4_no_field_names_with_songs_props_ooo.yaml"
        val band: Band4? = parseYaml<Band4>(fileUrl)
        assertNotNull(band)
        assertEquals(band.name, "Led Zeppelin")
        assertEquals(band.albums[0].name, "Atlantic")
        assertEquals(band.albums[1].name, "Atlantic")
    }

    @Test
    fun `original parseYamlAs function can load YAML into immutable classes when class type info is in YAML and no field names are present and songs exist with type info`() {
        val fileUrl = "band_4_no_field_names_with_songs.yaml"
        val band: Band4? = parseYamlAs(fileUrl, Band4::class.java)
        assertNotNull(band)
        assertEquals(band.name, "Led Zeppelin")
        assertEquals(band.albums[0].tracks.size, 2)
    }

}
