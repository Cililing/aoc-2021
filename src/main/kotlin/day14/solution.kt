package day14

import utils.input
import java.math.BigInteger

val input = utils.input("day14/input.txt").readLines().first().map { it.toString() }

val rules = input("day14/input.txt").readLines().drop(2).map { it.split(" -> ") }
    .associate { it.first() to it[0][0] + it[1] + it[0][1] }

fun main() {
    apply2(input, 10)
    println(apply2(input, 40))
}

fun apply2(inn: List<String>, steps: Int): BigInteger {
    val groups =
        inn.zipWithNext().groupBy { it.first + it.second }.mapValues { it.value.count().toBigInteger() }.toMutableMap()

    val letters = inn.groupBy { it }.mapValues { it.value.count().toBigInteger() }.toMutableMap()

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
                // values to repalace in string
                val (p1, p2) = replacement.take(2) to replacement.takeLast(2)
                // inserted letter
                val l = replacement[1].toString()

                groups[it.key] = groups[it.key]!! - it.value
                groups.increaseOrAdd(p1, it.value)
                groups.increaseOrAdd(p2, it.value)
                letters.increaseOrAdd(l, it.value)
            }
        }
    }

    repeat((0 until steps).count()) {
        apply()
    }

    val (min, max) = letters.minOf { it.value } to letters.maxOf { it.value }
    return max - min
}

fun <T> MutableMap<T, BigInteger>.increaseOrAdd(k: T, inc: BigInteger = BigInteger.ONE) {
    if (this.contains(k)) {
        this[k] = this[k]!! + inc
    } else {
        this[k] = inc
    }
}

fun ex1(steps: Int): Int {
    var current = input.joinToString("")
    repeat((0 until steps).count()) {
        current = replace(current)
    }

    val occ = current.groupBy { it }.mapValues { it.value.count() }
    val (m, c) = occ.maxOf { it.value } to occ.minOf { it.value }
    return m - c
}

fun replace(inn: String): String {
    val n = inn.zipWithNext().map { "${it.first}${it.second}" }.map {
        if (rules.contains(it)) rules[it]!! else it
    }

    val includeLastChar = n.last().count() == 3 // if replaced attach the last character
    return (n.map { it.take(2) } + if (includeLastChar) inn.last() else listOf<String>()).joinToString("")
}
