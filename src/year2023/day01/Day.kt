package year2023.day01

import year2023.solveIt


fun main() {
    val day = "01"
    val expectedTest1 = 142L
    val expectedTest2 = 281L


    fun part1(input: List<String>): Long {
        return input.sumOf { line -> Integer.valueOf(line.first { it in '0'..'9' }.toString() + line.last { it in '0'..'9' }.toString()) }.toLong()
    }


    fun part2(input: List<String>): Long {
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
            possible.map { it to line.indexOf(it.key) }.filter { it.second>=0 }.sortedBy { it.second }.first().first.value * 10 +
                    possible.map { it to line.lastIndexOf(it.key) }.filter { it.second>=0 }.sortedBy { it.second }.asReversed().first().first.value
        }.sum().toLong()


    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test2")

}




