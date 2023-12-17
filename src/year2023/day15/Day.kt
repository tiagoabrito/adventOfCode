package year2023.day15

import year2023.solveIt


fun main() {
    val day = "15"

    val expectedTest1 = 1320L
    val expectedTest2 = 145L

    fun hash(s: String): Int {
        return s.asSequence().fold(0) { acc, c ->
            ((acc + c.code) * 17) % 256
        }
    }

    fun part1(input: List<String>): Long {
        return input.flatMap { it.split(",") }.sumOf { hash(it) }.toLong()
    }


    fun combine(key: String, value: String, first: Boolean, accumulator: List<Pair<String, Int>>?): List<Pair<String, Int>> {
        if (first) {
            return listOf(key to value.toInt())
        }
        val kIndex = accumulator!!.indexOfFirst { it.first == key }
        return if (kIndex == -1) {
            accumulator + (key to value.toInt())
        } else {
            accumulator.subList(0, kIndex) + (key to value.toInt()) + accumulator.subList(kIndex+1, accumulator.size)
        }
    }

    fun part2(input: List<String>): Long {
        val boxes = List(256) { listOf<Pair<String, Int>>() }

        val groupBy = input.flatMap { it.split(",") }.groupingBy { hash(it.split('-', '=')[0]) }
            .aggregate { _, accumulator: List<Pair<String, Int>>?, element, first ->
                val (k, value) = element.split('-', '=')
                if (element.endsWith("-")) {
                    (accumulator ?: listOf()).filter { it.first != k }
                } else {
                    combine(k, value, first, accumulator)
                }
            }

        val flatten = groupBy.map { entry -> entry.value.mapIndexed { index, pair -> (entry.key+1) * (index + 1) * pair.second } }.flatten()
        return flatten.sum().toLong()
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




