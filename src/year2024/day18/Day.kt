package year2024.day18

import year2024.solveIt


fun main() {
    val day = "18"

    val expectedTest1 = 296
    val expectedTest2 = "28,44"


    fun getNext(curr: Pair<Int, Int>) =
        listOf(
            curr.first + 1 to curr.second,
            curr.first - 1 to curr.second,
            curr.first to curr.second + 1,
            curr.first to curr.second - 1
        )

    fun getShortestPath(corrupted: Set<Pair<Int, Int>>): Int {
        val start = 0 to 0
        val end = 70 to 70
        val queue = ArrayDeque<Pair<Pair<Int, Int>, Int>>() // Pair of position and path length
        val visited = mutableSetOf(start)
        queue.add(start to 0)

        while (queue.isNotEmpty()) {
            val (currentPos, distance) = queue.removeFirst()
            if (currentPos == end) return distance

            getNext(currentPos)
                .filter { it.first in 0..70 && it.second in 0..70 }
                .filter { !corrupted.contains(it) }
                .filter { !visited.contains(it) }
                .forEach {
                    visited.add(it)
                    queue.add(it to distance + 1)
                }
        }
        return -1
    }

    fun getShortestPath(input: List<String>, corruptLimit: Int): Int {
        val corrupted = input.take(corruptLimit).map { line ->
            val (x, y) = line.split(",").map { it.toInt() }
            y to x
        }.toSet()

        return getShortestPath(corrupted)
    }

    fun part1(input: List<String>): Int {
        return getShortestPath(input, 1024)
    }


    fun part2(input: List<String>): String {

        val first = (1024 .. input.size).first { getShortestPath(input, it) == -1 }
        return input[first-1]
    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}