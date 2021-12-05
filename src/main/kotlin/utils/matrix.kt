package utils

typealias Row<T> = List<T>
typealias MutableRow<T> = MutableList<T>

typealias Matrix<T> = List<Row<T>>
typealias MutableMatrix<T> = MutableList<MutableList<T>>

fun <T> matrixOf(rows: List<T>): Matrix<T> = listOf(rows)

fun <T> mutableMatrixOf(size: Int, initialValue: T): MutableMatrix<T> {
    return MutableList(size) {
        MutableList(size) { initialValue }
    }
}

/**
 * @param a number of items in a single row (applicable to all rows)
 */
fun <T> matrixOf(a: Int, vararg elements: T): Matrix<T> = elements.toList().chunked(a)

/**
 * Transposes matrix
 */
fun <T> List<List<T>>.trans(): List<List<T>> {
    return (this[0].indices).map { i ->
        this.map { it[i] }
    }
}

/**
 * Applies XOR operator on each element on the list
 */
fun List<Boolean>.revBitMask() = this.map { !it }

