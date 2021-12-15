package day5

import utils.*

val input: List<Pair<Coordinates, Coordinates>> = utils.input("day5/input.txt")
    .readLines()
    .map {
        it.split("->").map { it.trim() }
    }
    .map {
        Pair(it[0], it[1])
    }
    .map {
        val first = it.first.split(",")
        val second = it.second.split(",")

        Pair(
            first[0].toInt() to first[1].toInt(),
            second[0].toInt() to second[1].toInt()
        )
    }

val vertical = input.filter { it.isVertical() }.map {
    if (it.first.first < it.second.first) it else Pair(it.second, it.first)
}

val horizontal = input.filter { it.isHorizontal() }.map {
    if (it.first.second < it.second.second) it else Pair(it.second, it.first)
}

val maxSize = input.map {
    listOf(it.first.first, it.first.second, it.second.first, it.second.second)
}.flatten().maxOrNull()!!

val diagonal = input.filter { it.isDiagonal() && !it.isHorizontal() && !it.isVertical() }

fun main() {
    println(ex1())
    println(ex2())
}

fun ex1(): Int {
    val m = mutableMatrixOf(maxSize + 1, 0)
    applyVertical(m)
    applyHorizontal(m)
    return m.flatten().count { it >= 2 }
}

fun ex2(): Int {
    val m = mutableMatrixOf(maxSize + 1, 0)
    applyVertical(m)
    applyHorizontal(m)
    applyDiagonal(m)
    return m.flatten().count { it >= 2 }
}

fun applyDiagonal(m: MutableMatrix<Int>) {
    diagonal.forEach {
        val cords = it.getDiagonalCoords()
        cords.forEach {
            m[it.second][it.first]++
        }
    }
}

fun applyHorizontal(m: MutableMatrix<Int>) {
    horizontal.forEach {
        val y = it.first.first
        val xStart = it.first.second
        val xEnd = it.second.second

        (xStart..xEnd).forEach {
            m[it][y]++
        }
    }
}

fun applyVertical(m: MutableMatrix<Int>) {
    vertical.forEach {
        // apply only first cord
        val x = it.first.second
        val yStart = it.first.first
        val yEnd = it.second.first

        (yStart..yEnd).forEach {
            m[x][it]++
        }
    }
}
