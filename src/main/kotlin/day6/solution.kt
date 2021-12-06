package day6

import java.math.BigInteger

val input = utils.input("day6/input.txt")
    .readLines()
    .first()
    .split(",")
    .map { it.toInt() }

const val resetValue = 6 // value when up to reset
const val initValue = 8 // value when added to list

fun main() {
    //ex1()
    ex2()
}

private fun ex2() {
    val days = 1024

    var totalUnderflow = BigInteger.ZERO
    var currentValues = mapOf<Int, BigInteger?>(
        0 to input.count { it == 0 }.toBigInteger(),
        1 to input.count { it == 1 }.toBigInteger(),
        2 to input.count { it == 2 }.toBigInteger(),
        3 to input.count { it == 3 }.toBigInteger(),
        4 to input.count { it == 4 }.toBigInteger(),
        5 to input.count { it == 5 }.toBigInteger(),
        6 to input.count { it == 6 }.toBigInteger(),
        7 to input.count { it == 7 }.toBigInteger(),
        8 to input.count { it == 8 }.toBigInteger()
    )

    repeat((1..days).count()) {
        val underflow = currentValues[0]
        totalUnderflow = totalUnderflow.plus(underflow ?: BigInteger.ZERO)

        currentValues = currentValues.mapValues {
            when (it.key) {
                8 -> currentValues[0]

                6 -> {
                    val lastDay = currentValues[7] ?: BigInteger.ZERO
                    val zeroDay = currentValues[0] ?: BigInteger.ZERO

                    lastDay + zeroDay
                }

                else -> currentValues[it.key + 1]
            }
        }

        var total = BigInteger.ZERO
        currentValues.values.forEach {
            total += it ?: BigInteger.ZERO
        }

        println("underflow after $it day: $totalUnderflow")
        println("list-length after $it day: $total")
    }
}

private fun ex1() {
    val days = 256
    var list = input

    var totalUnderflows = 0

    (1..days).forEach {
        var underflow = 0
        list = list
            .map {
                it - 1
            }
            .map {
                when (it) {
                    -1 -> {
                        underflow++
                        resetValue
                    }
                    else -> it
                }
            }

        val newValues = (0 until underflow).map { initValue }

        list = list + newValues
        totalUnderflows += underflow

        println("days ready: $it")
        // println("after $it days: $list (total: ${list.sum()})")
    }

    println("total underflow: $totalUnderflows")
    println("list-length: ${list.count()}")
}