package year2024.day11

import year2024.solveIt


fun main() {
    val day = "11"
    val expectedTest1 = 55312L
    val expectedTest2 = 65601038650482L

    fun blink(v: Pair<Long, Long>): List<Pair<Long, Long>> {

        val( stone, count) = v
        return when {
            stone == 0L -> listOf(1L to count)
            stone.toString().length % 2 == 0 -> {
                val len = stone.toString().length / 2
                listOf(stone.toString().take(len).toLong() to count, stone.toString().takeLast(len).toLong() to count)
            }
            else -> listOf(stone * 2024 to count)
        }
    }

    fun blinkTimes(input: List<String>, numberOfTimes: Int): Long {
        val map = input[0].split(" ").asSequence().map { it.toLong() to 1L }.toList()

        val fold: List<Pair<Long, Long>> = (1..numberOfTimes).fold(map) { acc, i ->
            acc.flatMap { blink(it) }.groupingBy { it.first }.fold(0L) { tot, element -> tot + element.second }.toList()
        }
        return fold.sumOf { it.second }
    }

    fun part1(input: List<String>): Long {
        return blinkTimes(input, 25)
    }

    fun part2(input: List<String>): Long {
        return blinkTimes(input, 75)
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}