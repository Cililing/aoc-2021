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

// TODO(day5): the main function should have only invocations of ex1/ex2
fun main() {
    val maxSize = input.map {
        listOf(it.first.first, it.first.second, it.second.first, it.second.second)
    }.flatten().maxOrNull()!!

    val vertical = input.filter { it.isVertical() }.map {
        if (it.first.first < it.second.first) it else Pair(it.second, it.first)
    }
    val horizontal = input.filter { it.isHorizontal() }.map {
        if (it.first.second < it.second.second) it else Pair(it.second, it.first)
    }

    val m = mutableMatrixOf(maxSize + 1, 0)

    // apply vertical
    vertical.forEach {
        // apply only first cord
        val x = it.first.second
        val yStart = it.first.first
        val yEnd = it.second.first

        (yStart..yEnd).forEach {
            m[x][it]++
        }
    }
    horizontal.forEach {
        val y = it.first.first
        val xStart = it.first.second
        val xEnd = it.second.second

        (xStart..xEnd).forEach {
            m[it][y]++
        }
    }

    println(m.flatten().count { it >= 2 })

    // apply also diagonals
    val diagonal = input.filter { it.isDiagonal() && !it.isHorizontal() && !it.isVertical() }
    diagonal.forEach {
        val cords = it.getDiagonalCoords()
        cords.forEach {
            m[it.second][it.first]++
        }
    }

    println(m.flatten().count { it >= 2 })
}
