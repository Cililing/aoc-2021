package utils

/**
 * returns sum of series 1+2+3+4+5+...+n
 */
fun eps(n: Int): Int {
    return (n * (n + 1)) / 2
}

fun Boolean.toInt() = if (this) 1 else 0

fun Boolean.toLong() = if (this) 1L else 0L
