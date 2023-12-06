package year2023.day02

import readInput


fun main() {
    val day = "02"
    fun i(index: Int) = index

    val expectedTest1 = 8
    val expectedTest2 = 2286

    val minimum = mapOf("red" to 12, "green" to 13, "blue" to 14)


    fun canPlay(s: String): Boolean {
        val map = s.substringAfter(":").split(";").flatMap { it ->
            it.split(",").map {
                val (c, color) = it.trim().split(" ")
                color to c
            }
        }.all { a -> minimum[a.first]!! >= Integer.valueOf(a.second) }
        return map
    }

    fun part1(input: List<String>): Int {
        return input.mapIndexedNotNull { index, s -> when(canPlay(s)){
            true -> index +1
            false-> null
        } }.sum()
    }


    fun cubes(line: String): Int {
        val flatMap = line.substringAfter(":").split(";").flatMap { it ->
            it.split(",").map {
                val (c, color) = it.trim().split(" ")
                color to c
            }
        }.groupBy { it.first }.mapValues { it -> it.value.maxOfOrNull { Integer.valueOf(it.second) } }
        return flatMap.values.filterNotNull().reduceRight{a,b -> a*b}
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { cubes(it) }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2023/day$day/test")
    val part1Test = part1(testInput)
    check(part1Test == expectedTest1) { "expected $expectedTest1 but was $part1Test" }


    val input = readInput("year2023/day$day/input")
    println(part1(input))

    val part2Test = part2(testInput)
    check(part2Test == expectedTest2) { "expected $expectedTest2 but was $part2Test" }
    println(part2(input))
}




