package utils

import java.lang.IllegalStateException

fun <T> requireNonEqual(vararg elements: T) {
    if (elements.count() != elements.toSet().count()) {
        throw IllegalStateException("all elements must be unique")
    }
}