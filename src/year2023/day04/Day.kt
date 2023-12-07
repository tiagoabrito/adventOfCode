package year2023.day04

import kotlin.math.pow
import year2023.solveIt


fun main() {
    val day = "04"

    val expectedTest1 = 13L
    val expectedTest2 = 30L


    fun readCard(it: String): List<Set<String>> = it.substring(it.indexOf(":")).split("|").map { Regex("\\d+").findAll(it).map { it.value }.toSet() }

    fun getCardResult(it: String): Int {
        val (card, values) = readCard(it)
        return when (val matching = card.intersect(values).size) {
            0 -> 0
            else -> 2.toDouble().pow(matching - 1).toInt()
        }
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { getCardResult(it) }.toLong()
    }




    fun part2(input: List<String>): Long {
        val receivableCards = input.map {
            val (card, values) = readCard(it)
            card.intersect(values).size
        }

        val received = MutableList(input.size){0}
        for(i in receivableCards.size - 1 downTo 0){
            received[i] = receivableCards[i] + received.subList(i+1, i+1+receivableCards[i]).sum()
        }
        return received.sum()+input.size.toLong()
    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




