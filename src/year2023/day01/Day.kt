package year2023.day01

import java.util.HashSet
import readInput


fun main() {
    val day = "01"
    val expectedTest1 = 142
    val expectedTest2 = 281


    fun part1(input: List<String>): Int {
        return input.sumOf { line -> Integer.valueOf(line.first { it in '0'..'9' }.toString() + line.last { it in '0'..'9' }.toString()) }
    }


    fun part2(input: List<String>): Int {
        val possible = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
            "0" to 0,
            "1" to 1,
            "2" to 2,
            "3" to 3,
            "4" to 4,
            "5" to 5,
            "6" to 6,
            "7" to 7,
            "8" to 8,
            "9" to 9
        )
        return input.map { line ->
            println(line)
            possible.map { it to line.indexOf(it.key) }.filter { it.second>=0 }.sortedBy { it.second }.first().first.value * 10 +
                    possible.map { it to line.lastIndexOf(it.key) }.filter { it.second>=0 }.sortedBy { it.second }.asReversed().first().first.value
        }.sum()


    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2023/day$day/test")
    val test2Input = readInput("year2023/day$day/test2")
    val part1Test = part1(testInput)
    check(part1Test == expectedTest1) { "expected $expectedTest1 but was $part1Test" }


    val input = readInput("year2023/day$day/input")
    println(part1(input))

    val part2Test = part2(test2Input)
    check(part2Test == expectedTest2) { "expected $expectedTest2 but was $part2Test" }
    println(part2(input))
}




