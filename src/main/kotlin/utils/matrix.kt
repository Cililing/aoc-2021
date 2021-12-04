package utils

fun <T> List<List<T>>.trans(): List<List<T>> {
    return (this[0].indices).map { i ->
        this.map { it[i] }
    }
}