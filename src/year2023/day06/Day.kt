package year2023.day06

import java.util.stream.LongStream
import kotlin.streams.toList
import readInput


fun main() {
    val day = "06"

    val expectedTest1 = 288L
    val expectedTest2 = 71503L


    fun isNotRecord(v: Long, duration: Long, record: Long) =
        v * (duration - v) <= record

    fun getIt(strings: List<String>): Long {
        val (durations, record) = strings.map { Regex("\\d+").findAll(it).map { foundValues -> foundValues.value.toLong() }.toList() }
        val validVelocities = durations.mapIndexed { raceNumber, duration ->
            LongStream.range(1, duration).dropWhile { v -> isNotRecord(v, duration, record[raceNumber]) }.toList()
                            .asReversed().dropWhile { v -> isNotRecord(v, duration, record[raceNumber]) }
        }
        return validVelocities.map { it.size }.fold(1) { acc, i -> acc * i }
    }

    fun part1(input: List<String>): Long {
        return getIt(input)
    }


    fun part2(input: List<String>): Long {
        return getIt(input.map { it.replace(" ","") })
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2023/day$day/test")
    val part1Test = part1(testInput)
    check(part1Test == expectedTest1) { "expected $expectedTest1 but was $part1Test" }


    val input = readInput("year2023/day$day/input")
    println(part1(input))

    val part2Test = part2(testInput)
    check(part2Test == expectedTest2) { "expected $expectedTest2 but was $part2Test" }
    val v = System.currentTimeMillis()
    println(part2(input))
    println(System.currentTimeMillis()-v)
}




