package year2023.day06

import java.util.stream.LongStream
import kotlin.streams.toList
import year2023.solveIt


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

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




