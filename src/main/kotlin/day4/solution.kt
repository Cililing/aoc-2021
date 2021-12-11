package day4

import utils.trans

val input = utils.input("day4/input.txt")
    .readLines()

val order = input.first().split(",")
    .map { it.toInt() }

val boards = input.drop(1)
    .filter { it != "" }
    .map {
        it.split(" ").filter { it != "" }.map { it.toInt() }
    }
    .chunked(5)

// Override typealias
typealias Matrix = utils.Matrix<Int>

fun main() {
    // 1. find winning board
    var winningIndex = 0
    var winningBoard: Matrix? = null

    while (winningBoard == null) {
        for (i in order.indices) {
            val currentNumbers = order.take(i)
            for (b in boards) {
                if (b.isWinning(currentNumbers)) {
                    winningIndex = i
                    winningBoard = b
                    break
                }
            }
            if (winningBoard != null) {
                break
            }
        }
    }

    println(winningBoard.score(order.take(winningIndex)))
    println(order.take(winningIndex))

    // 2. find last winning
    val allBoards = boards.toMutableList()
    var lastWinningBoard: Matrix? = null
    var currentNumbers = listOf<Int>()

    // lets remove all winning boards one by one until only one will left
    for (i in order.indices) {
        currentNumbers = order.take(i)
        val toRemove = allBoards.filter { it.isWinning(currentNumbers) }
        allBoards.removeAll(toRemove)

        // this is ugly xD
        if (allBoards.size == 1) {
            lastWinningBoard = allBoards.last()
        }
        if (allBoards.size == 0) {
            break
        }
    }

    println(lastWinningBoard!!.score(currentNumbers))
    println(currentNumbers)
}

fun Matrix.score(input: List<Int>): Int {
    var score = 0
    this.flatten().forEach {
        if (!input.contains(it)) {
            score += it
        }
    }
    return score
}

fun Matrix.isWinning(input: List<Int>): Boolean {
    // check horizontal
    this.forEach { line ->
        if (line.all { input.contains(it) }) {
            return true
        }
    }
    // check vertical
    this.trans().forEach { line ->
        if (line.all { input.contains(it) }) {
            return true
        }
    }

    return false
}
