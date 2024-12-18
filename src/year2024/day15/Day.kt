package year2024.day15

import year2024.solveIt


fun main() {
    val day = "15"

    val expectedTest1 = 10092L
    val expectedTest2 = 0L

    fun move(line: String): String {
        val currIdx = line.indexOf('@')
        val start: Int = line.lastIndexOf('#', currIdx)
        val end: Int = line.indexOf('#', currIdx + 1)

        val toMove = line.substring(start + 1, end)
        val idx = toMove.indexOf('@')
        val moved = ('.' + toMove.takeLast(toMove.length - idx).replaceFirst(".", "")).takeLast(toMove.length - idx)
        return line.take(currIdx) + moved + line.substring(end)
    }

    fun move(direction: Char, map: List<String>): List<String> {
        val pos = map.mapIndexed { index, s -> index to s.indexOf('@') }.first { it.second > -1 }
        return when (direction) {
            '>' -> map.take(pos.first) + move(map[pos.first]) + map.drop(pos.first + 1)
            '<' -> map.take(pos.first) + move(map[pos.first].reversed()).reversed() + map.drop(pos.first + 1)
            'v' -> move(map.map { it[pos.second] }
                .joinToString("")).mapIndexed { index, c -> map[index].take(pos.second) + c + map[index].drop(pos.second + 1) }

            '^' -> move(map.map { it[pos.second] }.joinToString("").reversed()).reversed()
                .mapIndexed { index, c -> map[index].take(pos.second) + c + map[index].drop(pos.second + 1) }

            else -> throw IllegalArgumentException("Unknown direction $direction")
        }
    }


    fun part1(input: List<String>): Long {
        val map = input.takeWhile { it.isNotEmpty() }

        val instructions = input.dropWhile { it.isNotEmpty() }.drop(1)
        val end = instructions.flatMap { it.asSequence() }.fold(map) { acc, c ->
            move(c, acc)
        }

        return end.flatMapIndexed { y, s -> s.mapIndexedNotNull { x, c -> c.takeIf { it == 'O' }?.let { y * 100 + x } } }.sum().toLong()
    }


    fun part2(input: List<String>): Long {
        return 0
    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}