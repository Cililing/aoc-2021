package utils

typealias Coordinates = Pair<Int, Int>

val Coordinates.x: Int
    get() = this.first

val Coordinates.y: Int
    get() = this.second

fun Pair<Coordinates, Coordinates>.isVertical(): Boolean {
    return this.first.second == this.second.second
}

fun Pair<Coordinates, Coordinates>.isHorizontal(): Boolean {
    return this.first.first == this.second.first
}

fun Pair<Coordinates, Coordinates>.isDiagonal(): Boolean {
    val dy: Int = this.first.second - this.second.second
    val dx: Int = this.first.first - this.second.first
    return dx == 0 || dy == 0 || dx == dy || dx == -dy
}

fun Pair<Coordinates, Coordinates>.getDiagonalCoords(): List<Coordinates> {
    val res = mutableListOf<Coordinates>()
    var current = this.first

    // diagonal x increasing, y increasing
    if (second.x > first.x && second.y >= first.y) {
        do {
            res.add(current)
            current = current.copy(current.first + 1, current.second + 1)
        } while (current != this.second)
        res.add(current) // include the last one

        return res
    }

    // diagonal x increasing, y decreasing
    if (second.x > first.x && second.y <= first.y) {
        do {
            res.add(current)
            current = current.copy(current.first + 1, current.second - 1)
        } while (current != this.second)
        res.add(current)

        return res
    }

    // diagonal x decreasing, y increasing
    if (second.x <= first.x && second.y > first.y) {
        do {
            res.add(current)
            current = current.copy(current.first - 1, current.second + 1)
        } while (current != this.second)
        res.add(current)

        return res
    }

    // diagonal x decreasing, y decreasing
    if (second.x <= first.x && second.y < first.y) {
        do {
            res.add(current)
            current = current.copy(current.first - 1, current.second - 1)
        } while (current != this.second)
        res.add(current)

        return res
    }

    return emptyList()
}
