@file:Suppress("MemberVisibilityCanBePrivate")

package day16

import utils.Mutable
import utils.toLong

val mappings = mapOf(
    '0' to "0000",
    '1' to "0001",
    '2' to "0010",
    '3' to "0011",
    '4' to "0100",
    '5' to "0101",
    '6' to "0110",
    '7' to "0111",
    '8' to "1000",
    '9' to "1001",
    'A' to "1010",
    'B' to "1011",
    'C' to "1100",
    'D' to "1101",
    'E' to "1110",
    'F' to "1111",
)

data class Message(
    val packetVersion: Long,
    val messageType: Long,

    // this is shared between the root-package and all subPackages,
    // and all the subpackages may modify it
    // TODO: it's not the best solution :(
    private val message: Mutable<String>,

    private var literal: Long? = null,
    private var subPackets: MutableList<Message> = mutableListOf(),
    private var length: Long = 0
) {
    constructor(data: Mutable<String>) : this(
        data.get().take(3).toLong(2),
        data.get().drop(3).take(3).toLong(2),
        data,
    ) {
        // remove metadata
        message.modify { it.drop(6) }

        length = when {
            isLiteral() -> createLiteral()
            isOperator() -> createOperators()
            else -> throw IllegalArgumentException()
        }
    }

    private fun createOperators(): Long {
        val lengthType = message.get().take(1).toLong(2)
        message.modify { it.drop(1) }

        val lengthBits = if (lengthType == 0L) 15 else 11
        val requiredLength = message.get().take(lengthBits).toLong(2)
        message.modify { it.drop(lengthBits) }

        val proceedBits = mutableListOf<Long>()

        when (lengthType) {
            0L -> { // bit-size of sub-messages number
                while (proceedBits.sum() != requiredLength) {
                    val next = Message(message)

                    subPackets.add(next)
                    message.modify { next.message.v }
                    proceedBits += next.length
                }
            }
            1L -> { // fixed-size of submessage number
                repeat((0 until requiredLength).count()) {
                    val next = Message(message)

                    subPackets.add(next)
                    message.modify { next.message.v }
                    proceedBits += next.length
                }
            }
        }

        // proceedBits + metadata + lengthType + lengthBits
        return proceedBits.sum() + 6 + 1 + lengthBits
    }

    // return number of used bits (including metadata)
    private fun createLiteral(): Long {
        // chunk to 5-size
        val chunked = message.get().chunked(5)
        val size = chunked.indexOfFirst { it.first() == '0' } + 1
        val toParse = chunked.take(size)

        message.modify { it.drop(size * 5) }
        literal = toParse.joinToString("") { it.drop(1) }
            .toLong(2)

        return 6L + size * 5L
    }

    fun isLiteral() = messageType == 4L
    fun isOperator() = !isLiteral()

    fun versionSum(): Long {
        if (isLiteral()) return packetVersion
        return packetVersion + subPackets.sumOf { it.versionSum() }
    }

    fun decode(): Long {
        return when (this.messageType) {
            0L -> subPackets.sumOf { it.decode() }
            1L -> subPackets.map { it.decode() }.reduce { acc, l -> acc * l }
            2L -> subPackets.minOf { it.decode() }
            3L -> subPackets.maxOf { it.decode() }
            4L -> this.literal ?: throw IllegalArgumentException("must be literal")
            5L -> (subPackets[0].decode() > subPackets[1].decode()).toLong()
            6L -> (subPackets[0].decode() < subPackets[1].decode()).toLong()
            7L -> (subPackets[0].decode() == subPackets[1].decode()).toLong()
            else -> throw IllegalArgumentException("unknown messageType")
        }
    }
}

val input = utils.input("day16/input.txt")
    .readLines()
    .first()
    .map { mappings[it] ?: throw IllegalArgumentException("no mappings found") }
    .joinToString("")

fun main() {
    println(ex1())
    println(ex2())
}

fun ex1(): Long {
    val msg = Message(Mutable(input))
    return msg.versionSum()
}

fun ex2(): Long {
    val msg = Message(Mutable(input))
    return msg.decode()
}
