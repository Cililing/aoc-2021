package day7

import utils.eps
import kotlin.math.abs

val input = utils.input("day7/input.txt")
    .readLines()
    .first()
    .split(",")
    .map { it.toInt() }

fun main() {
    println(ex1())
    println(ex2())
}

private fun ex2(): Int? {
    val (min, max) = input.minOrNull()!! to input.maxOrNull()!!

    return (min..max).minOfOrNull {
        input.sumOf { x ->
            val steps = abs(it - x)
            eps(steps)
        }
    }
}

private fun ex1(): Int? {
    val (min, max) = input.minOrNull()!! to input.maxOrNull()!!

    return (min..max).minOfOrNull {
        input.sumOf { x -> abs(it - x) }
    }
}
