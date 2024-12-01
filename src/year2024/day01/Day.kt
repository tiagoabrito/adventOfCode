package year2024.day01

import kotlin.math.abs
import year2024.solveIt


fun main() {
    val day = "01"
    val expectedTest1 = 11L
    val expectedTest2 = 31L


    fun part1(input: List<String>): Long {
        val map = input.map { it.split("   ") }
        val listA = map.map { it[0].toLong() }.sorted()
        val listB = map.map { it[1].toLong() }.sorted()
        return listA.zip(listB).sumOf { (a, b) -> abs(a - b) }
    }


    fun part2(input: List<String>): Long {
        val map = input.map { it.split("   ") }
        val listA = map.map { it[0].toLong() }.sorted()
        val occurrencesCount = map.map { it[1].toLong() }.groupingBy { it }.eachCount()

        return listA.sumOf { it * (occurrencesCount[it] ?: 0) }
    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}




