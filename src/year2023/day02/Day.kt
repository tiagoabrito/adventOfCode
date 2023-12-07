package year2023.day02

import year2023.solveIt


fun main() {
    val day = "02"
    fun i(index: Int) = index

    val expectedTest1 = 8L
    val expectedTest2 = 2286L

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

    fun part1(input: List<String>): Long {
        return input.mapIndexedNotNull { index, s -> when(canPlay(s)){
            true -> index +1
            false-> null
        } }.sum().toLong()
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

    fun part2(input: List<String>): Long {
        return input.sumOf { cubes(it) }.toLong()
    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




