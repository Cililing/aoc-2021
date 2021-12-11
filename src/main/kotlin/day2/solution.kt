package day2

enum class Direction {
    Forward,
    Down,
    Up
}

val input = utils.input("day2/input.txt")
    .readLines()
    .map {
        val split = it.split(" ")
        Pair(
            when (split[0]) {
                "forward" -> Direction.Forward
                "down" -> Direction.Down
                "up" -> Direction.Up
                else -> throw IllegalArgumentException()
            },
            split[1].toInt()
        )
    }

data class Position(var horizontal: Int = 0, var vertical: Int = 0, var aim: Int = 0)

fun main() {
    ex1()
    println("---------")
    ex2()
}

private fun ex2() {
    val position = Position()
    input.forEach {
        when (it.first) {
            Direction.Forward -> {
                position.horizontal += it.second
                position.vertical += position.aim * it.second
            }
            Direction.Up -> position.aim -= it.second
            Direction.Down -> position.aim += it.second
        }
        println(position)
    }
    println("final position is: $position")
    println("product of position is: ${position.horizontal * position.vertical}")
}

private fun ex1() {
    val position = Position()
    input.forEach {
        when (it.first) {
            Direction.Forward -> position.horizontal += it.second
            Direction.Up -> position.vertical -= it.second
            Direction.Down -> position.vertical += it.second
        }
    }

    println("final position is: $position")
    println("product of position is: ${position.horizontal * position.vertical}")
}
