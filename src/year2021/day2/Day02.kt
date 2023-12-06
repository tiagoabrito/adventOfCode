package year2021.day2

import readInput

fun main() {
    fun aaa(a: List<String>?): Int {
        return (a ?: emptyList()).map { it.split(" ") }.map {
            when (it[0]) {
                "up" -> -1
                else -> 1
            } * it[1].toInt()
        }.sum()
    }

    fun part1(input: List<String>): Int {
        val groupBy = input.groupBy(keySelector = { it.first() == 'f' })
        return aaa(groupBy[true]) * aaa(groupBy[false])
    }


    data class Position(val x: Int=0, val y: Int=0, val aim: Int=0)

    fun asPosition(it: List<String>) = when (it[0]) {
        "up" -> Position(aim = -1 * it[1].toInt())
        "down" -> Position(aim = it[1].toInt())
        else -> Position(x = it[1].toInt())
    }

    fun part2(input: List<String>): Int {
        val map = input.map { a -> asPosition(a.split(" ")) }
        return map
            .reduce { acc, b -> Position(acc.x + b.x, acc.y + acc.aim*b.x, acc.aim + b.aim) }
            .let { it.x * it.y }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2021/Day02_test")
    check(part1(testInput) == 150)

    val input = readInput("year2021/Day02")
    println(part1(input))

    check(part2(testInput) == 900)
    println(part2(input))
}
