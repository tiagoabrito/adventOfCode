package year2022.day10

import kotlin.math.abs
import readInput


fun main() {
    val day = "10"
    val expectedTest1 = 13140
    val expectedTest2 = """
        
        ##..##..##..##..##..##..##..##..##..##..
        ###...###...###...###...###...###...###.
        ####....####....####....####....####....
        #####.....#####.....#####.....#####.....
        ######......######......######......####
        #######.......#######.......#######.....
    """.trimIndent()


    fun asTimedOperations(input: List<String>) = input.flatMap {
        when (it) {
            "noop" -> listOf(0)
            else -> listOf(0, it.split(" ")[1].toInt())
        }
    }

    fun part1(input: List<String>): Int {
        val cycles = asTimedOperations(input)
        return List(10) { 20 + (it * 40) }.takeWhile { it < cycles.size }.sumOf {
            val i = 1 + cycles.take(it - 1).sum()
            i * it
        }
    }


    fun part2(input: List<String>): String {
        val asTimedOperations = asTimedOperations(input)
        return (0..239).map {
            when (abs((it % 40) - (asTimedOperations.take(it).sum() + 1)) < 2) {
                true -> "#"
                else -> "."
            }
        }.chunked(40).joinToString("\n", "\n") { it.joinToString("") }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2022/day$day/test")
    val part1Test = part1(testInput)
    check(part1Test == expectedTest1) { "expected $expectedTest1 but was $part1Test" }


    val input = readInput("year2022/day$day/input")
    println(part1(input))

    val part2Test = part2(testInput)
    check(part2Test == expectedTest2) { "expected $expectedTest2 but was $part2Test" }
    println(part2(input))
}




