package year2022.day02

import java.lang.RuntimeException
import readInput


private const val ROCK = 1
private const val PAPER = 2
private const val SCISSORS = 3

private const val WIN = 3
private const val DRAW = 2
private const val LOOSE = 1

fun main() {
    fun combatA(abc:String, xyz:String): Int{
        val a : Int = abc[0] - 'A' + 1
        val x : Int = xyz[0] - 'X' + 1
        val res: Int = when {
            a == ROCK && x == ROCK -> 3
            a == ROCK && x == PAPER -> 6
            a == ROCK && x == SCISSORS -> 0
            a == PAPER && x == ROCK -> 0
            a == PAPER && x == PAPER -> 3
            a == PAPER && x == SCISSORS -> 6
            a == SCISSORS && x == ROCK -> 6
            a == SCISSORS && x == PAPER -> 0
            a == SCISSORS && x == SCISSORS -> 3
            else -> throw RuntimeException()
        }
        return x + res
    }

    fun combatB(abc:String, xyz:String): Int{
        val a : Int = abc[0] - 'A' + 1
        val x : Int = xyz[0] - 'X' + 1
        val res: Int = when {
            a == ROCK && x == WIN -> 6 + 2
            a == ROCK && x == DRAW -> 3 + 1
            a == ROCK && x == LOOSE -> 0 + 3
            a == PAPER && x == WIN -> 6 + 3
            a == PAPER && x == DRAW -> 3 + 2
            a == PAPER && x == LOOSE -> 0 + 1
            a == SCISSORS && x == WIN -> 6 + 1
            a == SCISSORS && x == DRAW -> 3 + 3
            a == SCISSORS && x == LOOSE -> 0 + 2
            else -> throw RuntimeException()
        }
        return res
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line -> line.split(" ").let { combatA(it[0], it[1]) } }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line -> line.split(" ").let { combatB(it[0], it[1]) } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2022/day02/test")
    check(part1(testInput) == 15)

    val input = readInput("year2022/day02/input")
    println(part1(input))
    println(part2(input))
}
