package year2022.day04

import readInput

private const val MIN_1 = 0
private const val MAX_1 = 1
private const val MIN_2 = 2
private const val MAX_2 = 3

fun main() {


    fun isInside(line: List<Int>) = ((line[MIN_1] <= line[MIN_2] && line[MAX_2] <= line[MAX_1]) ||
                                     (line[MIN_2] <= line[MIN_1] && line[MAX_1] <= line[MAX_2]))

    fun isOverlap(line: List<Int>) = (line[MIN_2] <= line[MAX_1] && line[MIN_1] <= line[MIN_2]) ||
                                      (line[MIN_1] <= line[MAX_2] && line[MIN_2] <= line[MIN_1]) ||
                                      (line[MAX_1] >= line[MIN_2] && line[MIN_1] <= line[MIN_2]) ||
                                       (line[MAX_2] >= line[MIN_1] && line[MIN_2] <= line[MIN_1])

    fun part1(input: List<CharSequence>): Int {
        return input.map { isInside(it.split(",","-").map { it.toInt()})}.count { it }
    }


    fun part2(input: List<String>): Int {
        return input.map { isOverlap(it.split(",","-").map { it.toInt()})}.count { it }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2022/day04/test")
    println(part1(testInput))
    check(part1(testInput) == 2) { "expected 2 but was ${part1(testInput)}" }



    val input = readInput("year2022/day04/input")
    println(part1(input))

    check(part2(testInput) == 4) { "expected 4 but was ${part1(testInput)}" }
    println(part2(input))
}




