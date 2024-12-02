package year2024

import java.time.Duration
import readInput

fun <T>solveIt(day: String, part1: (List<String>) -> T, expectedTest1: T, part2: (List<String>) -> T, expectedTest2: T, test2File: String = "test") {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2024/day$day/test")
    val test2Input = readInput("year2024/day$day/$test2File")
    val part1Test = part1(testInput)
    check(part1Test == expectedTest1) { "expected $expectedTest1 but was $part1Test" }


    val input = readInput("year2024/day$day/input")
    println(part1(input))

    val part2Test = part2(test2Input)
    check(part2Test == expectedTest2) { "expected $expectedTest2 but was $part2Test" }
    val v = System.currentTimeMillis()
    println(part2(input))
    println(Duration.ofMillis(System.currentTimeMillis() - v))
}