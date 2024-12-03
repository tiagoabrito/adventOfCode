package year2024.day03

import year2024.solveIt

fun main() {
    val day = "03"

    val expectedTest1 = 161L
    val expectedTest2 = 48L

    fun getNumber(it: MatchResult, idx: Int):Long = it.groups[idx]?.value?.toLong()?:0
    fun removeDisabled(it: String) = it.replace("don't\\(\\).*?do\\(\\)".toRegex(), "")


    fun part1(input: List<String>): Long {
        return input
            .flatMap { Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)").findAll(it) }
            .sumOf { getNumber(it, 1) * getNumber(it, 2) }
    }

    fun part2(input: List<String>): Long {
        val enabledMultiplications = input.map { removeDisabled(it) }
        return part1(enabledMultiplications)
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}