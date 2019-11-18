package com.joelforjava.kotlin

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.introspector.BeanAccess
import java.io.BufferedInputStream
import java.io.File
import java.io.InputStream
import java.net.URISyntaxException
import java.nio.charset.Charset
import java.nio.file.Paths

fun loadResource(fileUrl: String): File? {
    var configFile: File? = null
    try {
        val url = object {}.javaClass.classLoader.getResource(fileUrl)
        if (url != null) {
            val path = Paths.get(url.toURI())
            configFile = path.toFile()
        }
    } catch (e: URISyntaxException) {
        println("There was an error loading the file as a classpath resource. Please ensure fileUrl $fileUrl is valid.")
    }
    return configFile
}

fun loadResourceAsString(fileUrl: String): String? {
    return loadResource(fileUrl)?.readText(Charset.defaultCharset())
}

fun loadResourceAsStream(fileUrl: String): InputStream? {
    return object {}.javaClass.classLoader.getResourceAsStream(fileUrl)
}

// This will only work when the YAML has type information
fun <T> parseYaml(fileUrl: String): T? {
    var mapping: T? = null
    val inputStream = loadResourceAsStream(fileUrl) ?: return mapping
    BufferedInputStream(inputStream).use { bis ->
        val yaml = Yaml()
        mapping = yaml.load(bis) as T
    }
    return mapping
}

fun <T> parseYamlAs(fileUrl: String, clazz: Class<T>): T? {
    var mapping: T? = null
    val inputStream = loadResourceAsStream(fileUrl) ?: return mapping
    BufferedInputStream(inputStream).use { bis ->
        val yaml = Yaml()
        mapping = yaml.loadAs(bis, clazz)
    }
    return mapping
}

fun <T> parseYamlAsUsingFieldAccess(fileUrl: String, clazz: Class<T>): T? {
    var mapping: T? = null
    val inputStream = loadResourceAsStream(fileUrl) ?: return mapping
    BufferedInputStream(inputStream).use { bis ->
        val yaml = Yaml()
        yaml.setBeanAccess(BeanAccess.FIELD)
        mapping = yaml.loadAs(bis, clazz)
    }
    return mapping
}
