package day15

import org.jgrapht.Graph
import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.jgrapht.graph.DefaultWeightedEdge
import org.jgrapht.graph.SimpleDirectedWeightedGraph
import utils.Matrix
import utils.adjacent
import utils.get

data class Vertex(val x: Int, val y: Int, val weight: Int) {
    val name = "$x:$y"
}

val input = utils.input("day15/input.txt")
    .readLines()
    .mapIndexed { x, s ->
        s.mapIndexed { y, c ->
            Vertex(x, y, "$c".toInt())
        }
    }

fun main() {
    println(getPathWeight(input))
    println(getPathWeight(extendedInput(input)))
}

fun getPathWeight(input: Matrix<Vertex>): Int {
    val (startVertex, endVertex) = input.first().first() to input.last().last()
    val dijkstra = DijkstraShortestPath(createGraph(input))
    return dijkstra.getPathWeight(startVertex.name, endVertex.name).toInt()
}

private fun createGraph(source: Matrix<Vertex>): Graph<String, DefaultWeightedEdge> {
    val graph = SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge::class.java)
    source.flatten().forEach { graph.addVertex(it.name) }
    source.flatten().forEach { edge ->
        // find adjacent for vertex
        val adj = source.adjacent(edge.x, edge.y)
        adj.forEach {
            graph.addEdge(edge.name, it.second.name)
            graph.setEdgeWeight(edge.name, it.second.name, it.second.weight.toDouble())

            graph.addEdge(it.second.name, edge.name)
            graph.setEdgeWeight(it.second.name, edge.name, edge.weight.toDouble())
        }
    }
    return graph
}

fun extendedInput(source: Matrix<Vertex>): Matrix<Vertex> {
    return (0 until source.size * 5).map { x ->
        (0 until source[0].size * 5).map { y ->
            val sourceVertex = source.get(x % source.size, y % source[0].size)
            val (xPlus, yPlus) = (x / source.size) to (y / source.size)
            val f = { x: Int, i: Int -> if (x + i > 9) x + i - 9 else x + i }

            Vertex(x, y, f(sourceVertex.weight, xPlus + yPlus))
        }
    }
}
