package day14

import utils.input
import java.math.BigInteger

val input = input("day14/input.txt")
    .readLines()
    .first()
    .map { it }

val rules = input("day14/input.txt")
    .readLines()
    .drop(2)
    .map { it.split(" -> ") }
    .associate { it.first() to it[0][0] + it[1] + it[0][1] }

fun main() {
    println(ex2(input, 10))
    println(ex2(input, 40))
}

fun ex2(inn: List<Char>, steps: Int): BigInteger {
    val groups = inn.zipWithNext()
        .groupBy { "${it.first}${it.second}" }
        .mapValues { it.value.count().toBigInteger() }
        .toMutableMap()

    val letters = inn.groupBy { it }
        .mapValues { it.value.count().toBigInteger() }
        .toMutableMap()

    // apply rules
    // for example, apply rule NN -> NCN
    // NNCB -> NCNCB
    //  [groups] NN -> -1
    //  [groups] NC -> +1
    //  [groups] CN -> +1
    //  [letters] C -> +1

    val apply = {
        groups.toMap().forEach {
            // group => NN -> count
            val replacement = rules[it.key]

            if (replacement != null) {
                // values to replace in string
                val (p1, p2) = replacement.take(2) to replacement.takeLast(2)
                // inserted letter
                val l = replacement[1]

                groups.putOrAdd(it.key, -it.value)
                groups.putOrAdd(p1, it.value)
                groups.putOrAdd(p2, it.value)
                letters.putOrAdd(l, it.value)
            }
        }
    }

    repeat((0 until steps).count()) {
        apply()
    }

    val (min, max) = letters.minOf { it.value } to letters.maxOf { it.value }
    return max - min
}

fun <T> MutableMap<T, BigInteger>.putOrAdd(k: T, inc: BigInteger = BigInteger.ONE) {
    this[k] = (this[k] ?: BigInteger.ZERO) + inc
}

fun ex1(inn: String, steps: Int): Int {
    var current = input.joinToString("")

    repeat((0 until steps).count()) {
        current = replace(current)
    }

    val occ = current.groupBy { it }.mapValues { it.value.count() }
    val (max, min) = occ.maxOf { it.value } to occ.minOf { it.value }
    return max - min
}

fun replace(inn: String): String {
    val n = inn.zipWithNext()
        .map { "${it.first}${it.second}" }
        .map { rules[it] ?: it }

    val includeLastChar = n.last().count() == 3 // if replaced attach the last character

    return n.map { it.take(2) }
        .plus(if (includeLastChar) inn.last() else listOf<String>())
        .joinToString("")
}
