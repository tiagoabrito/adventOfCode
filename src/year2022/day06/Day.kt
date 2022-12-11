package year2022.day06

import readInput


fun main() {


    fun distinctSequence(sequence: CharSequence, size: Int): Int {
        return sequence.withIndex()
            .windowed(size)
            .first { it.map { it.value }.distinct().size == size }
            .last().index + 1
    }

    fun part1(input: List<CharSequence>): List<Int> {
        return input.map { distinctSequence(it, 4) }
    }


    fun part2(input: List<String>): List<Int> {
        return input.map { distinctSequence(it, 14) }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2022/day06/test")
    check(part1(testInput) == listOf(7,5,6,10,11)) { "expected ${listOf(7,5,6,10,11)} but was ${part1(testInput)}" }


    val input = readInput("year2022/day06/input")
    println(part1(input))

    check(part2(testInput) == listOf(19,23,23,29,26)) { "expected ${listOf(19,23,23,29,26)} but was ${part1(testInput)}" }
    println(part2(input))
}




