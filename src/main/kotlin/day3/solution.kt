package day3

import utils.trans
import java.io.File
import java.lang.IllegalArgumentException

val input = File("./src/main/kotlin/day3/input.txt")
    .readLines()
    .map { line ->
        line.map {
            if (it == '1') {
                1
            } else {
                0
            }
        }
    }

fun main() {
    val result = mutableListOf<Int>()
    input.trans().forEach { v ->
        val zeros = v.count { it == 0 }
        val ones = v.count { it == 1 }
        when {
            zeros == ones -> {
                throw IllegalArgumentException("cannot be equal")
            }
            zeros > ones -> {
                result.add(0)
            }
            else -> {
                result.add(1)
            }
        }
    }

    val binaryToDec = { i: List<Int> ->
        i.joinToString(separator = "") {
            it.toString()
        }.also { println(it) }
            .toInt(2)
            .also { println(it) }
    }

    println(
        "product: ${
        binaryToDec.invoke(result) * binaryToDec.invoke(result.revBitMask())
        }"
    )

    // part 2
    val ox = input.reduceByBit { zeros, ones ->
        when {
            ones > zeros -> 1
            zeros > ones -> 0
            else -> 1
        }
    }
    val co2 = input.reduceByBit { zeros, ones ->
        when {
            ones < zeros -> 1
            zeros < ones -> 0
            else -> 0
        }
    }

    println(
        "product of multiplying oxygen and co2 is ${
        binaryToDec.invoke(ox) * binaryToDec.invoke(co2)
        }"
    )
}

fun List<List<Int>>.reduceByBit(f: (zeros: Int, ones: Int) -> Int): List<Int> {
    var res = this.toList()
    var index = 0

    while (res.size != 1) {
        val trans = res.trans()
        val zeros = trans[index].count { it == 0 }
        val ones = trans[index].count { it == 1 }

        val v = f(zeros, ones)
        res = res.filter { it[index] == v }

        index++
    }
    return res[0]
}

fun List<Int>.revBitMask(): List<Int> {
    return this.map {
        when (it) {
            0 -> 1
            1 -> 0
            else -> throw IllegalArgumentException("must be 0 or 1")
        }
    }
}