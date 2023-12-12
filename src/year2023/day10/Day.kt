package year2023.day10

import year2023.solveIt


fun main() {
    val day = "10"

    val expectedTest1 = 8L
    val expectedTest2 = 1L

    data class Position(val x:Int, val y:Int)

    val moveMap = mapOf(
        "|01" to (0 to 1),"|0-1" to (0 to -1),
        "-10" to (1 to 0),"--10" to (-1 to 0),
        "J10" to (0 to -1),"J01" to (-1 to 0),
        "710" to (0 to 1),"70-1" to (-1 to 0),
        "L-10" to (0 to -1),"L01" to (1 to 0),
        "F-10" to (0 to 1),"F0-1" to (1 to 0),
    )

    fun navigate(input: List<String>, previous:  Position, current: Position): Position? {
        val dx = current.x - previous.x
        val dy = current.y - previous.y
        val c = input[current.y][current.x]
        val move = "$c$dx$dy"
        return moveMap[move]?.let { Position(current.x + it.first, current.y + it.second) }
            ?.takeIf { it.x in input[0].indices && it.y in input.indices }
    }

    fun getLoop(input: List<String>): Set<Position> {
        val start = input.flatMapIndexed { line, s -> s.mapIndexed { col, c -> c to Position(col, line) }.filter { it.first == 'S' } }.first().second

        val possibleConnections =
            listOf(
                Position(start.x - 1, start.y),
                Position(start.x + 1, start.y),
                Position(start.x, start.y - 1),
                Position(start.x, start.y + 1)
            ).filter { it.x in input[0].indices && it.y in input.indices }

        val firstNotNullOf = possibleConnections.firstNotNullOf { n ->
            var loop = setOf(start, n)
            var count = 1
            var previous = start
            var current = n
            while (true) {
                val next = navigate(input, previous, current) ?: break
                loop = loop + next
                previous = current
                current = next
                count++
            }
            if (current == start) {
                println("cycle $count $current")
                loop
            } else {
                println("deadend $count $current")
                null
            }
        }
        return firstNotNullOf
    }

    fun part1(input: List<String>): Long {
        val loop = getLoop(input)
        return loop.size/2L
    }

    fun part2(input: List<String>): Long {
        val loop = getLoop(input)
        val a = setOf('|','L','J')
        var count = 0L
        for (y in input.indices){
            var inside = false;
            for (x in input[y].indices) {
                val curr = input[y][x]
                if (loop.contains(Position(x, y))){
                    if (a.contains(curr)) {
                        inside = !inside;
                    }
                }
                else if (inside){
                    count++
                }
            }
        }


        return count
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




