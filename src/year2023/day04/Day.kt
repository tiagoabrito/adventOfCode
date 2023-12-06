package year2023.day04

import kotlin.math.pow
import readInput


fun main() {
    val day = "04"

    val expectedTest1 = 13
    val expectedTest2 = 30


    fun readCard(it: String): List<Set<String>> = it.substring(it.indexOf(":")).split("|").map { Regex("\\d+").findAll(it).map { it.value }.toSet() }

    fun getCardResult(it: String): Int {
        val (card, values) = readCard(it)
        return when (val matching = card.intersect(values).size) {
            0 -> 0
            else -> 2.toDouble().pow(matching - 1).toInt()
        }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { getCardResult(it) }
    }




    fun part2(input: List<String>): Int {
        val receivableCards = input.map {
            val (card, values) = readCard(it)
            card.intersect(values).size
        }

        val received = MutableList(input.size){0}
        for(i in receivableCards.size - 1 downTo 0){
            received[i] = receivableCards[i] + received.subList(i+1, i+1+receivableCards[i]).sum()
        }
        return received.sum()+input.size
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




