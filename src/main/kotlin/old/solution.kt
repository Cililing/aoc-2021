package old

import java.io.File

data class TicketPlacement(
    val name: String,
    val ranges: List<IntRange>
)

val placements = File("./src/main/kotlin/old/input.txt")
    .readLines()
    .take(20)
    .map {
        val s = it.split(':')

        val range = s[1].split("or")
        val r1 = range[0].trim()
        val r2 = range[1].trim()

        val parser = { i: String ->
            val r = i.split("-")
            IntRange(r[0].toInt(), r[1].toInt())
        }

        TicketPlacement(
            s[0],
            listOf(parser(r1), parser(r2))
        )
    }

val ticket = File("./src/main/kotlin/old/input.txt")
    .readLines()
    .drop(22)
    .take(1)
    .map {
        it.split(",").map { it.toInt() }
    }
    .flatten()

val tickets = File("./src/main/kotlin/old/input.txt")
    .readLines()
    .drop(25)
    .map {
        it.split(",").map { it.toInt() }
    }

data class TicketValue(
    val value: Int,
    val canBe: List<String>
)

fun main() {
    val allRanges = placements.map { it.ranges }.flatten()
    // get invalid values
    tickets.flatten()
        .filter { ticketValue ->
            !allRanges.any { it.contains(ticketValue) }
        }
        //.let { println(it.count()) }
        .sum()
        .let { println("invalid values sum: $it") }


    // ad 2
    val validTickets = tickets.filter { ticket ->
        ticket.all { v -> allRanges.any { it.contains(v) } } // filter
    }.map { ticket ->
        ticket.map { TicketValue(it, listOf()) } // map values to TicketValue
    }.map { ticket ->
        ticket.map { ticketSingleValue ->
            ticketSingleValue.copy(
                canBe = placements.filter { it.isValid(ticketSingleValue.value) }.map { it.name }
            )
        }
    }


    data class Placement(
        val position: Int,
        val placement: MutableSet<String>
    )


    val possiblePlacements = mutableListOf<Placement>()
    (0..19).forEach { i ->
        val allValues = validTickets.map { it[i] }
        possiblePlacements.add(Placement(i, exclusiveCanBe(allValues).toMutableSet()))
    }

    val final = mutableListOf<Placement>()
    while (possiblePlacements.isNotEmpty()) {
        // find the one with only possible placement; if no any the task is broken
        val found = possiblePlacements.find { it.placement.size == 1 }!!
        final.add(found)
        possiblePlacements.remove(found)

        possiblePlacements.forEach {
            it.placement.remove(found.placement.first())
        }
    }

}

fun TicketPlacement.isValid(i: Int): Boolean {
    return this.ranges.any { it.contains(i) }
}

fun exclusiveCanBe(v: List<TicketValue>): Set<String> {
    var startingSet = v[0].canBe.toSet()

    v.drop(1).forEach {
        startingSet = startingSet.intersect(it.canBe)
    }

    return startingSet
}