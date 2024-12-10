package year2024.day10

import year2024.solveIt

fun main() {
    val day = "10"

    val expectedTest1 = 36L
    val expectedTest2 = 81L

    fun navigate(pos: Pair<Int, Int>): Sequence<Pair<Int, Int>> = listOf(
        pos.first - 1 to pos.second, pos.first + 1 to pos.second, pos.first to pos.second - 1, pos.first to pos.second + 1
    ).asSequence()

    fun getNextLevel(groupBy: Map<Int, List<Pair<Int, Int>>>, origin: Pair<Int, Int>, level: Int): Sequence<Pair<Int, Int>> =
        navigate(origin).filter { groupBy[level].orEmpty().contains(it) }

    fun getPositionsByLevel(input: List<String>): Map<Int, List<Pair<Int, Int>>> =
        input.indices.flatMap { y -> input[y].mapIndexed { x: Int, v -> Character.getNumericValue(v) to (y to x) } }
            .groupBy({ it.first }, { it.second })

    fun getReachableTops(positionsByLevel: Map<Int, List<Pair<Int, Int>>>, removeDuplicates: Boolean): List<List<Pair<Int, Int>>> {
        val reachablePositions = positionsByLevel[0].orEmpty().map { start ->
            (1..9).fold(sequenceOf(start)) { acc, level ->
                val nextLevelPositions = acc.flatMap { currentPos -> getNextLevel(positionsByLevel, currentPos, level) }
                when (removeDuplicates) {
                    true -> nextLevelPositions.distinct()
                    false -> nextLevelPositions
                }
            }.toList()
        }
        return reachablePositions
    }

    fun part1(input: List<String>): Long {
        val positionsByLevel: Map<Int, List<Pair<Int, Int>>> = getPositionsByLevel(input)
        return getReachableTops(positionsByLevel, true).sumOf { it.size }.toLong()
    }

    fun part2(input: List<String>): Long {
        val positionsByLevel: Map<Int, List<Pair<Int, Int>>> = getPositionsByLevel(input)
        return getReachableTops(positionsByLevel, false).sumOf { it.size }.toLong()
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}