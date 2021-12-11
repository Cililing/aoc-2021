package utils

import java.io.File

/**
 * @param path file location relative to kotlin dir
 */
fun input(path: String) = File("./src/main/kotlin/$path")
