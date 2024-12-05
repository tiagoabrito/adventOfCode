package year2024.day05

import year2024.solveIt


fun main() {
    val day = "05"

    val expectedTest1 = 143
    val expectedTest2 = 123


    fun getRules(input: List<String>) = input.takeWhile { it.isNotEmpty() }.map { it.split("|") }.groupBy({ it[1] }, { it[0] })

    fun getPages(input: List<String>) = input.asSequence()
        .dropWhile { it.isNotEmpty() }.drop(1)
        .map { it.split(",") }

    fun part1(input: List<String>): Int {
        val rules = getRules(input)

        return getPages(input)
            .filter { it.zipWithNext().none { e -> rules[e.first]?.contains(e.second) ?: false } }
            .sumOf { it[it.size / 2].toInt() }
    }


    fun sortIt(pages: List<String>, rules: Map<String, List<String>>): List<String> {
        return pages
            .map { page -> page to (rules[page]?.count { pages.contains(it) } ?: 0) }
            .sortedBy { it.second }
            .map { it.first }
    }

    fun part2(input: List<String>): Int {
        val rules = getRules(input)

        val unOrdered = getPages(input)
            .filter { it.zipWithNext().any { e -> rules[e.first]?.contains(e.second) ?: false } }


        return unOrdered
            .map { sortIt(it, rules) }
            .sumOf { it[it.size / 2].toInt() }
    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}