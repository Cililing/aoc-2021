package day3

import utils.input
import utils.revBitMask
import utils.trans

val input = input("day3/input.txt")
    .readLines()
    .map { line ->
        line.map { it == '1' }
    }

fun main() {
    println(ex1())
    println(ex2())
}

fun ex2(): Int {
    val ox = input.reduceByBit { zeros, ones ->
        when {
            ones > zeros -> true
            zeros > ones -> false
            else -> true
        }
    }
    val co2 = input.reduceByBit { zeros, ones ->
        when {
            ones < zeros -> true
            zeros < ones -> false
            else -> false
        }
    }

    return binaryToDec.invoke(ox) * binaryToDec.invoke(co2)
}

fun ex1(): Int {
    val result = mutableListOf<Boolean>()
    input.trans().forEach { v ->
        val zeros = v.count { !it }
        val ones = v.count { it }
        when {
            zeros == ones -> {
                throw IllegalArgumentException("cannot be equal")
            }
            zeros > ones -> {
                result.add(false)
            }
            else -> {
                result.add(true)
            }
        }
    }

    return binaryToDec.invoke(result) * binaryToDec.invoke(result.revBitMask())
}

fun List<List<Boolean>>.reduceByBit(f: (zeros: Int, ones: Int) -> Boolean): List<Boolean> {
    var res = this.toList()
    var index = 0

    while (res.size != 1) {
        val trans = res.trans()
        val zeros = trans[index].count { !it }
        val ones = trans[index].count { it }

        val v = f(zeros, ones)
        res = res.filter { it[index] == v }

        index++
    }
    return res[0]
}

val binaryToDec = { i: List<Boolean> ->
    i.joinToString(separator = "") {
        it.toString()
    }.toInt(2)
}
