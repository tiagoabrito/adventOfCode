package year2022.day01

import java.util.stream.Collectors
import readInput

fun main() {

    fun getNSum(input: List<String>, numberOfElfs: Long) =
        input.joinToString("\n")
             .split("\n\n")
             .map{ it.split("\n").sumOf { s -> s.toInt() } }
             .sortedDescending()
             .stream()
             .limit(numberOfElfs)
             .collect(Collectors.summarizingInt { it }).sum

    fun part1(input: List<String>): Long {
        return getNSum(input, 1)
    }

    fun part2(input: List<String>): Long {
        return getNSum(input, 3)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2022/day01/test")
    check(part1(testInput) == 24000L)

    val input = readInput("year2022/day01/input")
    println(part1(input))
    println(part2(input))
}
