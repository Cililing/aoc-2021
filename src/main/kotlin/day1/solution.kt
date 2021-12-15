package day1

import utils.input

val input = input("day1/input.txt")
    .readLines()
    .map { it.toInt() }

fun main() {
    println(ex1())
    println(ex2())
}

fun ex2(): Int {
    return input.asSequence() // for performance
        .zipWithNext()
        .zipWithNext()
        // res := ((n, n+1), (n+1, n+2)), but we want get the sum of n, n+1, n+2
        .map { it.first.first + it.first.second + it.second.second }
        .zipWithNext()
        .count { it.second > it.first }
}

fun ex1(): Int {
    return input.zipWithNext().count { it.second > it.first }
}
