package year2021.day4

import java.util.stream.IntStream
import readInput

fun main() {


    fun toCard(it: List<String>) {
        TODO("Not yet implemented")
    }

    fun part1(input: List<String>): Int {
        val possible = input[0].split(",").map { it.toInt() }
        val cards = input.drop(2).filterNot { it.isEmpty() }.chunked(5).map { Card(it) }


        for (v in possible) {
            for (card in cards) {
              if(card.bingo(v)){
                  return card.remaining.sum() * v
              }
            }
        }
        return -1
    }


    fun part2(input: List<String>): Int {
        val possible = input[0].split(",").map { it.toInt() }
        val cards = input.drop(2).filterNot { it.isEmpty() }.chunked(5).map { Card(it) }

        var countRemaining = cards.size
        for (v in possible) {
            for (card in cards) {
                if(card.isNotBing() && card.bingo(v)) {
                    if (--countRemaining == 0) {
                        return card.remaining.sum() * v
                    }
                }
            }
        }
        return -1
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2021/day4/test")
    println(part1(testInput))
    check(part1(testInput) == 4512)

    val input = readInput("year2021/day4/input")
    println(part1(input))

    check(part2(testInput) == 1924)
    println(part2(input))
}

class Card {
    val lines: List<MutableSet<Int>>
    val columns: List<MutableSet<Int>>
    val remaining: MutableSet<Int>

    constructor(l: List<String>) {
        val tempLines = l.map { it.split(" ").filterNot { it.isEmpty() }.map { it -> it.toInt() }.toList() }
        lines = tempLines.map { it.toMutableSet() }
        columns = IntRange(0, 4).map { i -> tempLines.map { it -> it[i] }.toMutableSet() }
        remaining = lines.flatten().toMutableSet()
    }

    fun bingo(i: Int): Boolean {
        lines.forEach { it.remove(i) }
        columns.forEach { it.remove(i) }
        remaining.remove(i)
        return lines.any { it.isEmpty() } || columns.any { it.isEmpty() }
    }

    fun isNotBing(): Boolean {
        return lines.all { it.isNotEmpty() } && columns.all { it.isNotEmpty() }
    }
}


